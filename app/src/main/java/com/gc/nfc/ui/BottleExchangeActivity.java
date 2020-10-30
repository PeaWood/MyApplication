package com.gc.nfc.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dk.bleNfc.BleManager.BleManager;
import com.dk.bleNfc.BleManager.Scanner;
import com.dk.bleNfc.BleManager.ScannerCallback;
import com.dk.bleNfc.BleNfcDeviceService;
import com.dk.bleNfc.DeviceManager.BleNfcDevice;
import com.dk.bleNfc.DeviceManager.DeviceManagerCallback;
import com.dk.bleNfc.Exception.CardNoResponseException;
import com.dk.bleNfc.Exception.DeviceNoResponseException;
import com.dk.bleNfc.Tool.StringTool;
import com.dk.bleNfc.card.Ntag21x;

import com.gc.nfc.R;
import com.gc.nfc.app.AppContext;
import com.gc.nfc.common.NetRequestConstant;
import com.gc.nfc.domain.Data_UpdateBottleSpec;
import com.gc.nfc.domain.Data_UserBottles;
import com.gc.nfc.domain.Data_UserCard;
import com.gc.nfc.domain.User;
import com.gc.nfc.http.Logger;
import com.gc.nfc.http.OkHttpUtil;
import com.gc.nfc.utils.SharedPreferencesHelper;
import com.gc.nfc.utils.Tools;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import okhttp3.Request;
import okhttp3.Response;

//import com.alibaba.fastjson.JSON;
//import com.dk.weightScale.ScaleDevice;
//import com.dk.weightScale.scalerSDK;
//import com.gc.nfc.utils.Tools;

public class BottleExchangeActivity extends BaseActivity implements View.OnClickListener, TextToSpeech.OnInitListener {
    private AppContext appContext;
    private BleNfcDevice bleNfcDevice;

    private DeviceManagerCallback deviceManagerCallback = new DeviceManagerCallback() {
        public void onReceiveButtonEnter(byte param1Byte) {
        }

        public void onReceiveConnectBtDevice(boolean param1Boolean) {
            super.onReceiveConnectBtDevice(param1Boolean);
            if (param1Boolean) {
                System.out.println("Activity设备连接成功");
                msgBuffer.delete(0, msgBuffer.length());
                msgBuffer.append("设备连接成功!");
                if (mNearestBle != null)
                    ;
                try {
                    Thread.sleep(500L);
                    handler.sendEmptyMessage(3);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
            }
        }

        public void onReceiveConnectionStatus(boolean param1Boolean) {
            super.onReceiveConnectionStatus(param1Boolean);
            System.out.println("Activity设备链接状态回调");
        }

        public void onReceiveDeviceAuth(byte[] param1ArrayOfbyte) {
            super.onReceiveDeviceAuth(param1ArrayOfbyte);
        }

        public void onReceiveDisConnectDevice(boolean param1Boolean) {
            super.onReceiveDisConnectDevice(param1Boolean);
            System.out.println("Activity设备断开链接");
            msgBuffer.delete(0, msgBuffer.length());
            msgBuffer.append("设备断开链接!");
            handler.sendEmptyMessage(0);
        }

        public void onReceiveInitCiphy(boolean param1Boolean) {
            super.onReceiveInitCiphy(param1Boolean);
        }

        public void onReceiveRfmClose(boolean param1Boolean) {
            super.onReceiveRfmClose(param1Boolean);
        }

        public void onReceiveRfmSentApduCmd(byte[] param1ArrayOfbyte) {
            super.onReceiveRfmSentApduCmd(param1ArrayOfbyte);
            System.out.println("Activity接收到APDU回调：" + StringTool.byteHexToSting(param1ArrayOfbyte));
        }

        public void onReceiveRfnSearchCard(boolean param1Boolean, final int cardTypeTemp, byte[] param1ArrayOfbyte1, byte[] param1ArrayOfbyte2) {
            super.onReceiveRfnSearchCard(param1Boolean, cardTypeTemp, param1ArrayOfbyte1, param1ArrayOfbyte2);
            if (param1Boolean && cardTypeTemp != 0) {
                System.out.println("Activity接收到激活卡片回调：UID->" + StringTool.byteHexToSting(param1ArrayOfbyte1) + " ATS->" + StringTool.byteHexToSting(param1ArrayOfbyte2));
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            boolean bool;
                            if (bleNfcDevice.isAutoSearchCard()) {
                                bleNfcDevice.stoptAutoSearchCard();
                                bool = readWriteCardDemo(cardTypeTemp);
                                startAutoSearchCard();
                            } else {
                                bool = readWriteCardDemo(cardTypeTemp);
                                bleNfcDevice.closeRf();
                            }
                            if (bool) {
                                bleNfcDevice.openBeep(50, 50, 3);
                                return;
                            }
                        } catch (DeviceNoResponseException deviceNoResponseException) {
                            deviceNoResponseException.printStackTrace();
                            return;
                        }
                        try {
                            bleNfcDevice.openBeep(100, 100, 2);
                        } catch (DeviceNoResponseException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message param1Message) {
            msgText.setText(msgBuffer);
            if (bleNfcDevice.isConnection() == 2 || bleNfcDevice.isConnection() == 1) {
                switch (param1Message.what) {
                    default:
                        return;
                    case 3:
                        (new Thread(new Runnable() {
                            public void run() {
                                try {
                                    bleNfcDevice.getDeviceVersions();
                                    handler.sendEmptyMessage(0);
                                    if (bleNfcDevice.getDeviceBatteryVoltage() < 3.61D) {
                                        msgBuffer.append("(电量低)");
                                    } else {
                                        msgBuffer.append("(电量充足)");
                                    }
                                    handler.sendEmptyMessage(0);
                                    if (bleNfcDevice.androidFastParams(true))
                                        ;
                                    handler.sendEmptyMessage(0);
                                    handler.sendEmptyMessage(0);
                                    startAutoSearchCard();
                                } catch (DeviceNoResponseException deviceNoResponseException) {
                                    deviceNoResponseException.printStackTrace();
                                }
                            }
                        })).start();
                        break;
                    case 136:
                        String str1 = param1Message.obj!=null?param1Message.obj.toString():null;
                        if (str1 == null)
                            showToast("空标签！");
                        if (m_selected_nfc_model == 0)
                            bottleTakeOverUnit(str1, m_curUserId, m_deliveryUser.getUsername(), "6", m_customerAddress + "空瓶回收", false, true);
                        if (m_selected_nfc_model == 1)
                            bottleTakeOverUnit(str1, m_deliveryUser.getUsername(), m_curUserId, "5", m_customerAddress + "重瓶落户", false, false);
                        break;
                    case 137:
                        String[] arrayOfString = param1Message.obj!=null?param1Message.obj.toString().split(":"):null;
                        if (arrayOfString.length != 2) {
                            showToast("无效卡格式！");
                        }
                        str1 = arrayOfString[0];
                        String str2 = arrayOfString[1];
                        if (m_handedUserCard == null)
                            showToast("该用户未绑定用户卡");
                        if (!str2.equals(m_handedUserCard))
                            showToast("非本人卡号！");
                        if (str1.equals("Y")) {
                            showToast("满意！");
                            orderServiceQualityUpload(true);
                        }
                        if (str1.equals("N")) {
                            showToast("不满意！");
                            orderServiceQualityUpload(false);
                        }
                        break;
                }
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handler_old = new Handler() {
        public void handleMessage(Message param1Message) {
            super.handleMessage(param1Message);
            jumpToOrderDeal();
        }
    };

    private Handler handler_weightScale = new Handler();
    private boolean isSpecialOrder;
    private int lastRssi = -100;
    private View layout_inputWeight;

    private RadioGroup.OnCheckedChangeListener listen = new RadioGroup.OnCheckedChangeListener() {
        public void onCheckedChanged(RadioGroup param1RadioGroup, int param1Int) {
            param1RadioGroup.getCheckedRadioButtonId();
            switch (param1RadioGroup.getCheckedRadioButtonId()) {
                case R.id.radioButton_kp_id:
                    m_selected_nfc_model = 0;
                    break;
                case R.id.radioButton_zp_id:
                    m_selected_nfc_model = 1;
                    break;
            }
        }
    };

    BleNfcDeviceService mBleNfcDeviceService;
    private BluetoothDevice mNearestBle = null;
    private Lock mNearestBleLock = new ReentrantLock();
    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private Scanner mScanner;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName param1ComponentName, IBinder service) {
            BleNfcDeviceService mBleNfcDeviceService = ((BleNfcDeviceService.LocalBinder) service).getService();
            bleNfcDevice = mBleNfcDeviceService.bleNfcDevice;
            mScanner = mBleNfcDeviceService.scanner;
            mBleNfcDeviceService.setDeviceManagerCallback(deviceManagerCallback);
            mBleNfcDeviceService.setScannerCallback(scannerCallback);
            //开始搜索设备
            searchNearestBleDevice();
        }

        public void onServiceDisconnected(ComponentName param1ComponentName) {
            mBleNfcDeviceService = null;
        }
    };

    private Map<String, String> m_BottlesMapKP;
    private Map<String, String> m_BottlesMapZP;
    private Map<String, String> m_BottlesSpecMap;
    private JSONObject m_OrderJson;
    private int m_bfp_price_15kg;
    private int m_bfp_price_50kg;
    private int m_bfp_price_5kg;
    private int m_bfp_quantity_15kg;
    private int m_bfp_quantity_50kg;
    private int m_bfp_quantity_5kg;
    private EditText m_bottleIdKPEditText;
    private EditText m_bottleIdZPEditText;
    Bundle m_bundle;
    private Button m_buttonNext;
    private String m_curUserId;
    private JSONObject m_curUserSettlementType;
    private String m_customerAddress;
    private User m_deliveryUser;
    private int m_gjp_price_15kg;
    private int m_gjp_price_50kg;
    private int m_gjp_price_5kg;
    private int m_gjp_quantity_15kg;
    private int m_gjp_quantity_50kg;
    private int m_gjp_quantity_5kg;
    private String m_gp_code_head_KP = "";
    private String m_gp_code_head_ZP = "";
    private String m_handedUserCard = null;
    private int m_hp_quantity_15kg;
    private int m_hp_quantity_50kg;
    private int m_hp_quantity_5kg;
    private ImageView m_imageAddKPManual;
    private ImageView m_imageAddZPManual;
    private ImageView m_imageViewKPEye;
    private ImageView m_imageViewSearchBlue = null;
    private ImageView m_imageViewZPEye;
    private ImageView m_imageView_search_weightDevice = null;
    private ListView m_listView_kp;
    private ListView m_listView_zp;
    private Map<String, JSONObject> m_myBottlesMap;
    private String m_orderId;
    private boolean m_orderServiceQualityShowFlag;
    private int m_ptp_quantity_15kg;
    private int m_ptp_quantity_50kg;
    private int m_ptp_quantity_5kg;
    private int m_qp_quantity_15kg;
    private int m_qp_quantity_50kg;
    private int m_qp_quantity_5kg;
    private int m_selected_nfc_model;
    private Spinner m_spinnerGPHeadKP;
    private Spinner m_spinnerGPHeadZP;
    private int m_takeOverCount = 0;
    private TextView m_textViewTotalCountKP;
    private TextView m_textViewTotalCountZP;
    private Map<String, JSONObject> m_userBottlesMap;
    private int m_yjp_price_15kg;
    private int m_yjp_price_50kg;
    private int m_yjp_price_5kg;
    private int m_yjp_quantity_15kg;
    private int m_yjp_quantity_50kg;
    private int m_yjp_quantity_5kg;
    private String m_yjp_ss_total;
    private String m_yjp_ys_total;
    private StringBuffer msgBuffer;
    private TextView msgText = null;
    private TextView msgText_weightDevice = null;
    private RadioButton radioButton_kp;
    private RadioButton radioButton_zp;
    private RadioGroup radioGroup_nfc = null;
    private ProgressDialog readWriteDialog = null;
    //  private scalerSDK scale;

    private ScannerCallback scannerCallback = new ScannerCallback() {
        @Override
        public void onReceiveScanDevice(BluetoothDevice device, int rssi, byte[] scanRecord) {
            super.onReceiveScanDevice(device, rssi, scanRecord);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //StringTool.byteHexToSting(scanRecord.getBytes())
                System.out.println("Activity搜到设备：" + device.getName()
                        + " 信号强度：" + rssi
                        + " scanRecord：" + StringTool.byteHexToSting(scanRecord));
            }

            //搜索蓝牙设备并记录信号强度最强的设备
            if ( (scanRecord != null) && (StringTool.byteHexToSting(scanRecord).contains("017f5450"))) {  //从广播数据中过滤掉其它蓝牙设备
                if (rssi < -55) {
                    return;
                }
                //msgBuffer.append("搜到设备：").append(device.getName()).append(" 信号强度：").append(rssi).append("\r\n");
                handler.sendEmptyMessage(0);
                if (mNearestBle != null) {
                    if (rssi > lastRssi) {
                        mNearestBleLock.lock();
                        try {
                            mNearestBle = device;
                        }finally {
                            mNearestBleLock.unlock();
                        }
                    }
                }
                else {
                    mNearestBleLock.lock();
                    try {
                        mNearestBle = device;
                    }finally {
                        mNearestBleLock.unlock();
                    }
                    lastRssi = rssi;
                }
            }
        }

        @Override
        public void onScanDeviceStopped() {
            super.onScanDeviceStopped();
        }
    };

    private SwipeRefreshLayout swipeRefreshLayout;

    private Runnable task = new Runnable() {
        public void run() {
            //            handler_weightScale.postDelayed(this, 150L);
            //            if (scale.bleIsEnabled()) {
            //              scale.Scan(true);
            //              scale.updatelist();
            //              if (scale.getDevicelist().isEmpty() != true) {
            //                if (scale.getDevicelist().size() != 1) {
            //                  msgText_weightDevice.setText("多台蓝牙秤冲突！");
            //                  return;
            //                }
            //                msgText_weightDevice.setText("连接正常！");
            //                float f = ((ScaleDevice)scale.getDevicelist().get(0)).scalevalue;
            //                if (layout_inputWeight != null)
            //                  ((EditText)layout_inputWeight.findViewById(2131230836)).setText(String.format("%4.1f", new Object[] { Float.valueOf(f) }));
            //                return;
            //              }
            //              msgText_weightDevice.setText("未连接！");
            //            }
        }
    };

    private Toast toast = null;

    private TextToSpeech tts;

    TextView tv;

    private void GetUserCard() {
        Map<String, String> map = new HashMap();
        map.put("userId", this.m_curUserId);
        map.put("status", String.valueOf(1));
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.GET(OkHttpUtil.URL + "/UserCard/", map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(BottleExchangeActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() != 200) {
                    Toast.makeText(BottleExchangeActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                    return;
                }
                String string = response.body().string();
                Logger.e("GetUserCard: " + string);
                Gson gson = new Gson();
                setData(gson.fromJson(string, Data_UserCard.class));
            }

            private void setData(Data_UserCard userCard) {
                int size = userCard.getItems().size();
                if (size == 1) {
                    m_handedUserCard = userCard.getItems().get(0).getNumber();
                    return;
                }
                m_handedUserCard = null;
                showToast("该用户未绑定用户卡");
            }
        });
    }

    private void OrdersBindGasCynNumber() {
        if (this.m_BottlesMapZP.size() != 0) {
            OkHttpUtil okHttpUtil = OkHttpUtil.getInstance(this);
            Map hashMap = new HashMap();
            hashMap.put("orderSn", this.m_orderId);
            String str = "";
            boolean bool = true;
            for (Map.Entry<String, String> entry : this.m_BottlesMapZP.entrySet()) {
                if (bool) {
                    bool = false;
                } else {
                    str = str + ",";
                }
                str = str + entry.getKey();
            }
            hashMap.put("gasCynNumbers", str);
            okHttpUtil.postForm(OkHttpUtil.URL + "Orders/Bind/GasCynNumber", new OkHttpUtil.ResultCallback() {
                @Override
                public void onError(Request request, Exception e) {
                    Toast.makeText(BottleExchangeActivity.this, "无数据", Toast.LENGTH_LONG).show();
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    if (response.code() != 200) {
                        Toast.makeText(BottleExchangeActivity.this, "重瓶绑定订单失败" + response.code(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    String string = response.body().string();
                    Logger.e("refleshVaildBottles: " + string);
                    Toast.makeText(BottleExchangeActivity.this, "重瓶绑定订单失败" + response.code(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void addEditViewChanged(EditText paramEditText, final View layout) {
        paramEditText.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable param1Editable) {
                if (!param1Editable.toString().equals(""))
                    Integer.parseInt(param1Editable.toString()); //这块应该是 押金瓶，钢检瓶 ，报废瓶的金额
                EditText editText2 = layout.findViewById(R.id.textView_yjp_15kg_ys);
                EditText editText3 = layout.findViewById(R.id.textView_yjp_50kg_ys);
                EditText editText4 = layout.findViewById(R.id.textView_gjp_5kg_ys);
                EditText editText1 = layout.findViewById(R.id.textView_yjp_5kg_ys);
                EditText editText5 = layout.findViewById(R.id.textView_gjp_15kg_ys);
                EditText editText6 = layout.findViewById(R.id.textView_gjp_50kg_ys);
                EditText editText7 = layout.findViewById(R.id.textView_bfp_5kg_quantity);
                EditText editText8 = layout.findViewById(R.id.textView_bfp_15kg_quantity);
                EditText editText9 = layout.findViewById(R.id.textView_bfp_50kg_quantity);
                int i = getEditTextToInt(editText2);
                int j = getEditTextToInt(editText3);
                int k = getEditTextToInt(editText4);
                int m = getEditTextToInt(editText1);
                int n = getEditTextToInt(editText5);
                int i1 = getEditTextToInt(editText6);
                int i2 = getEditTextToInt(editText7);
                int i3 = getEditTextToInt(editText8);
                int i4 = getEditTextToInt(editText9);
                ((TextView) layout.findViewById(R.id.textView_ys)).setText(String.valueOf(i + j + k + m + n + i1 + i2 + i3 + i4));
            }

            public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
            }

            public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {
            }
        });
    }

    private void addKP(String paramString) {
        int i;
        updateBottleSpec(paramString);
        if (!this.m_BottlesMapKP.containsKey(paramString)) {
            this.m_BottlesMapKP.put(paramString, "0");
            refleshBottlesListKP();
            i = paramString.length();
            if (i <= 9) {
                showToast("钢瓶码长度不够！");
                return;
            }
        } else {
            return;
        }
        if (i >= 3) {
            paramString = paramString.substring(i - 3, i);
            this.tts.speak(toChinese(paramString), 0, null);
            return;
        }
        this.tts.speak(toChinese(paramString), 0, null);
    }

    private void addZP(String paramString) {
        int i;
        updateBottleSpec(paramString);
        if (!this.m_BottlesMapZP.containsKey(paramString)) {
            this.m_BottlesMapZP.put(paramString, "0");
            refleshBottlesListZP();
            i = paramString.length();
            if (i <= 9) {
                showToast("钢瓶码长度不够！");
                return;
            }
        } else {
            return;
        }
        if (i >= 3) {
            paramString = paramString.substring(i - 3, i);
            this.tts.speak(toChinese(paramString), 0, null);
            return;
        }
        this.tts.speak(toChinese(paramString), 0, null);
    }

    private void blueDeviceInitial() {
        msgText = (TextView)findViewById(R.id.msgText);
        m_imageViewSearchBlue= (ImageView) findViewById(R.id.imageView_search);
        m_imageViewSearchBlue.setOnClickListener(new StartSearchButtonListener());
        msgBuffer = new StringBuffer();
        //ble_nfc服务初始化
        Intent gattServiceIntent = new Intent(this, BleNfcDeviceService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void createElectDep() {
        Map map = new HashMap();
        map.put("customerId", this.m_curUserId);
        map.put("operId", this.m_deliveryUser.getUsername());
        map.put("actualAmount", this.m_yjp_ss_total);
        JSONArray jSONArray = createElectDepDetails();
        if (jSONArray == null) {
            jumpToOrderDeal();
            return;
        }
        map.put("electDepositDetails", jSONArray);
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.postForm(OkHttpUtil.URL + "/ElectDeposit/", map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(BottleExchangeActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() != 201) {
                    Toast.makeText(BottleExchangeActivity.this, "电子押金单上传失败，" + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                jumpToOrderDeal();
            }
        });
    }

    private JSONArray createElectDepDetails() {
        try {
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject1 = new JSONObject();
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            jSONObject1.put("code", "0001");
            jSONObject2.put("code", "0002");
            jSONObject3.put("code", "0003");
            if (this.m_ptp_quantity_5kg != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("electDepositType", "EBottleChanging");
                jSONObject.put("gasCylinderSpec", jSONObject1);
                jSONObject.put("quantity", this.m_ptp_quantity_5kg);
                jSONArray.put(jSONObject);
            }
            if (this.m_ptp_quantity_15kg != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("electDepositType", "EBottleChanging");
                jSONObject.put("gasCylinderSpec", jSONObject2);
                jSONObject.put("quantity", this.m_ptp_quantity_15kg);
                jSONArray.put(jSONObject);
            }
            if (this.m_ptp_quantity_50kg != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("electDepositType", "EBottleChanging");
                jSONObject.put("gasCylinderSpec", jSONObject3);
                jSONObject.put("quantity", this.m_ptp_quantity_50kg);
                jSONArray.put(jSONObject);
            }
            if (this.m_yjp_quantity_5kg != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("electDepositType", "EDepositBottle");
                jSONObject.put("gasCylinderSpec", jSONObject1);
                jSONObject.put("quantity", this.m_yjp_quantity_5kg);
                jSONArray.put(jSONObject);
            }
            if (this.m_yjp_quantity_15kg != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("electDepositType", "EDepositBottle");
                jSONObject.put("gasCylinderSpec", jSONObject2);
                jSONObject.put("quantity", this.m_yjp_quantity_15kg);
                jSONArray.put(jSONObject);
            }
            if (this.m_yjp_quantity_50kg != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("electDepositType", "EDepositBottle");
                jSONObject.put("gasCylinderSpec", jSONObject3);
                jSONObject.put("quantity", this.m_yjp_quantity_50kg);
                jSONArray.put(jSONObject);
            }
            if (this.m_qp_quantity_5kg != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("electDepositType", "EOweBottle");
                jSONObject.put("gasCylinderSpec", jSONObject1);
                jSONObject.put("quantity", this.m_qp_quantity_5kg);
                jSONArray.put(jSONObject);
            }
            if (this.m_qp_quantity_15kg != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("electDepositType", "EOweBottle");
                jSONObject.put("gasCylinderSpec", jSONObject2);
                jSONObject.put("quantity", this.m_qp_quantity_15kg);
                jSONArray.put(jSONObject);
            }
            if (this.m_hp_quantity_50kg != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("electDepositType", "EOweBottle");
                jSONObject.put("gasCylinderSpec", jSONObject3);
                jSONObject.put("quantity", this.m_hp_quantity_50kg);
                jSONArray.put(jSONObject);
            }
            if (this.m_hp_quantity_5kg != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("electDepositType", "EReturnBottle");
                jSONObject.put("gasCylinderSpec", jSONObject1);
                jSONObject.put("quantity", this.m_hp_quantity_5kg);
                jSONArray.put(jSONObject);
            }
            if (this.m_hp_quantity_15kg != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("electDepositType", "EReturnBottle");
                jSONObject.put("gasCylinderSpec", jSONObject2);
                jSONObject.put("quantity", this.m_hp_quantity_15kg);
                jSONArray.put(jSONObject);
            }
            if (this.m_hp_quantity_50kg != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("electDepositType", "EReturnBottle");
                jSONObject.put("gasCylinderSpec", jSONObject3);
                jSONObject.put("quantity", this.m_hp_quantity_50kg);
                jSONArray.put(jSONObject);
            }
            if (this.m_gjp_quantity_5kg != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("electDepositType", "EGasBottle");
                jSONObject.put("gasCylinderSpec", jSONObject1);
                jSONObject.put("quantity", this.m_gjp_quantity_5kg);
                jSONArray.put(jSONObject);
            }
            if (this.m_gjp_quantity_15kg != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("electDepositType", "EGasBottle");
                jSONObject.put("gasCylinderSpec", jSONObject2);
                jSONObject.put("quantity", this.m_gjp_quantity_15kg);
                jSONArray.put(jSONObject);
            }
            if (this.m_gjp_quantity_50kg != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("electDepositType", "EGasBottle");
                jSONObject.put("gasCylinderSpec", jSONObject3);
                jSONObject.put("quantity", this.m_gjp_quantity_50kg);
                jSONArray.put(jSONObject);
            }
            if (this.m_bfp_quantity_5kg != 0) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("electDepositType", "EBaoFeiBottle");
                jSONObject.put("gasCylinderSpec", jSONObject1);
                jSONObject.put("quantity", this.m_bfp_quantity_5kg);
                jSONArray.put(jSONObject);
            }
            if (this.m_bfp_quantity_15kg != 0) {
                jSONObject1 = new JSONObject();
                jSONObject1.put("electDepositType", "EBaoFeiBottle");
                jSONObject1.put("gasCylinderSpec", jSONObject2);
                jSONObject1.put("quantity", this.m_bfp_quantity_15kg);
                jSONArray.put(jSONObject1);
            }
            if (this.m_bfp_quantity_50kg != 0) {
                jSONObject2 = new JSONObject();
                jSONObject2.put("electDepositType", "EBaoFeiBottle");
                jSONObject2.put("gasCylinderSpec", jSONObject3);
                jSONObject2.put("quantity", this.m_bfp_quantity_50kg);
                jSONArray.put(jSONObject2);
            }
            int i = jSONArray.length();
            if (i == 0) {
                jSONArray = null;
            }
            return jSONArray;
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
            return null;
        }
        //        JSONArray jSONArray = new JSONArray();//我自己给加上的  防止报错
    }

    private void getBottleWeight(final String bottleCodeTemp, final boolean isZP) {
        final View layout = getLayoutInflater().inflate(R.layout.upload_weight, null);//先随便用一个
        this.layout_inputWeight = layout;//这里原来是view  我先改为layout
        AlertDialog.Builder builder = (new AlertDialog.Builder(this)).setTitle("请输入").setIcon(R.drawable.icon_bottle).setView(layout).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                String str = ((EditText) layout.findViewById(R.id.input_bottleWeight2)).getText().toString();
                if (str.length() == 0) {
                    Toast.makeText(BottleExchangeActivity.this, "重量输入有误，请重新输入！", Toast.LENGTH_SHORT).show();
                } else if (isZP) {
                    m_BottlesMapZP.put(bottleCodeTemp, str);
                    refleshBottlesListZP();
                } else {
                    m_BottlesMapKP.put(bottleCodeTemp, str);
                    refleshBottlesListKP();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private int getEditTextToInt(EditText paramEditText) {
        return paramEditText.getText().toString().equals("") ? 0 : Integer.parseInt(paramEditText.getText().toString());
    }

    private String getResponseMessage(HttpResponse paramHttpResponse) {
        return null;
    }

    private String getTextViewToString(TextView paramTextView) {
        return paramTextView.getText().toString().equals("") ? "0" : paramTextView.getText().toString();
    }

    private boolean isBottlesQuantityOK() {
        HashMap<String, Object> hashMap;
        try {
            JSONArray jSONArray = this.m_OrderJson.getJSONArray("orderDetailList");
            hashMap = new HashMap();
            for (byte b = 0; b < jSONArray.length(); b++) {
                JSONObject jSONObject = jSONArray.getJSONObject(b);
                String str = jSONObject.getJSONObject("goods").getJSONObject("gasCylinderSpec").get("code").toString();
                int j = Integer.parseInt(jSONObject.get("quantity").toString());
                if (hashMap.containsKey(str)) {
                    int k = (int) hashMap.get(str);
                    hashMap.remove(str);
                    hashMap.put(str, k + j);
                } else {
                    hashMap.put(str, j);
                }
            }
        } catch (JSONException jSONException) {
            Toast.makeText(this, jSONException.toString(), Toast.LENGTH_LONG).show();
            return false;
        }
        for (String str : hashMap.keySet()) {
            Integer integer2 = (Integer) hashMap.get(str);
            Integer integer1 = 0;
            for (String str1 : this.m_BottlesMapZP.keySet()) {
                StringBuilder stringBuilder = new StringBuilder();
                ;
                String str2 = getBottleSpec(str1);
                if (str2 == null) {
                    updateBottleSpec(str1);
                    Toast.makeText(this, stringBuilder.append(str1).append("规格请求失败，1秒后再次提交！").toString(), Toast.LENGTH_LONG).show();
                    return false;
                }
                if (str2.equals(str))
                    integer1 = Integer.parseInt(stringBuilder.toString()) + 1;
            }
            if (!integer1.equals(integer2)) {
                StringBuilder stringBuilder = new StringBuilder();
                Toast.makeText(this, stringBuilder.append(getSpecName(str)).append("校验，重瓶交接数量与订单不符！").toString(), Toast.LENGTH_LONG).show();
                return false;
            }
        }
        int i = 0;
        Iterator<String> iterator = hashMap.keySet().iterator();
        while (iterator.hasNext())
            i += (Integer) hashMap.get(iterator.next());
        if (i != this.m_BottlesMapZP.size()) {
            Toast.makeText(this, "重瓶交接数量与订单不符！", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void jumpToOrderDeal() {
        Intent intent = new Intent();
        if (this.isSpecialOrder) {
            intent.setClass(this, TrayOrderDealActivity.class);
        } else {
            intent.setClass(this, OrderDealActivity.class);
        }
        JSONArray jSONArray = createElectDepDetails();
        this.m_bundle.putString("YJD_YS", this.m_yjp_ys_total);
        this.m_bundle.putString("YJD_SS", this.m_yjp_ss_total);
        this.m_bundle.putString("KPCode", JSON.toJSONString(this.m_BottlesMapKP));
        this.m_bundle.putString("ZPCode", JSON.toJSONString(this.m_BottlesMapZP));
        String str = "";
        if (jSONArray != null)
            str = String.valueOf(jSONArray);
        this.m_bundle.putString("ElectDepDetails", str);
        ArrayList<HashMap<Object, Object>> arrayList = new ArrayList();
        if (this.m_yjp_quantity_5kg != 0) {
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            hashMap.put("bottleType", "5kg押金瓶");
            hashMap.put("quantity", String.valueOf(this.m_yjp_quantity_5kg));
            hashMap.put("price", String.valueOf(this.m_yjp_price_5kg));
            arrayList.add(hashMap);
        }
        if (this.m_yjp_quantity_15kg != 0) {
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            hashMap.put("bottleType", "15kg押金瓶");
            hashMap.put("quantity", String.valueOf(this.m_yjp_quantity_15kg));
            hashMap.put("price", String.valueOf(this.m_yjp_price_15kg));
            arrayList.add(hashMap);
        }
        if (this.m_yjp_quantity_50kg != 0) {
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            hashMap.put("bottleType", "50kg押金瓶");
            hashMap.put("quantity", String.valueOf(this.m_yjp_quantity_50kg));
            hashMap.put("price", String.valueOf(this.m_yjp_price_50kg));
            arrayList.add(hashMap);
        }
        if (this.m_gjp_quantity_5kg != 0) {
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            hashMap.put("bottleType", "5kg钢检瓶");
            hashMap.put("quantity", String.valueOf(this.m_gjp_quantity_5kg));
            hashMap.put("price", String.valueOf(this.m_gjp_price_5kg));
            arrayList.add(hashMap);
        }
        if (this.m_gjp_quantity_15kg != 0) {
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            hashMap.put("bottleType", "15kg钢检瓶");
            hashMap.put("quantity", String.valueOf(this.m_gjp_quantity_15kg));
            hashMap.put("price", String.valueOf(this.m_gjp_price_15kg));
            arrayList.add(hashMap);
        }
        if (this.m_gjp_quantity_50kg != 0) {
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            hashMap.put("bottleType", "50kg钢检瓶");
            hashMap.put("quantity", String.valueOf(this.m_gjp_quantity_50kg));
            hashMap.put("price", String.valueOf(this.m_gjp_price_50kg));
            arrayList.add(hashMap);
        }
        if (this.m_bfp_quantity_5kg != 0) {
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            hashMap.put("bottleType", "5kg报废瓶");
            hashMap.put("quantity", String.valueOf(this.m_bfp_quantity_5kg));
            hashMap.put("price", String.valueOf(this.m_bfp_price_5kg));
            arrayList.add(hashMap);
        }
        if (this.m_bfp_quantity_15kg != 0) {
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            hashMap.put("bottleType", "15kg报废瓶");
            hashMap.put("quantity", String.valueOf(this.m_bfp_quantity_15kg));
            hashMap.put("price", String.valueOf(this.m_bfp_price_15kg));
            arrayList.add(hashMap);
        }
        if (this.m_bfp_quantity_50kg != 0) {
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            hashMap.put("bottleType", "50kg报废瓶");
            hashMap.put("quantity", String.valueOf(this.m_bfp_quantity_50kg));
            hashMap.put("price", String.valueOf(this.m_bfp_price_50kg));
            arrayList.add(hashMap);
        }
        this.m_bundle.putSerializable("MapList", arrayList);
        this.m_bundle.putString("SpecMap", new JSONObject(this.m_BottlesSpecMap).toString());
        intent.putExtras(this.m_bundle);
        startActivity(intent);
    }

    private void orderServiceQualityShow() {
        GetUserCard();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.user_evaluate, null);
        builder.setIcon(R.mipmap.icon_ticket_user);
        builder.setTitle("用户卡评价(" + this.m_handedUserCard + ")");
        AlertDialog alertDialog = builder.create();
        alertDialog.setView(view);
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            public void onDismiss(DialogInterface param1DialogInterface) {
                m_orderServiceQualityShowFlag = false;
            }
        });
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setGravity(17);
        window.setLayout(-1, -2);
        this.m_orderServiceQualityShowFlag = true;
    }

    private void orderServiceQualityUpload(boolean paramBoolean) {
        Map<String, String> map = new HashMap();
        if (paramBoolean) {
            map.put("orderServiceQuality", "OSQNegative");
        } else {
            map.put("orderServiceQuality", "OSQPositive");
        }
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.PUT(OkHttpUtil.URL + "/Orders/" + this.m_orderId, map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(BottleExchangeActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Logger.e("orderServiceQualityUpload: " + response.code());
                Logger.e("orderServiceQualityUpload: "+response.message());
                Logger.e("orderServiceQualityUpload: "+response.body().string());
                if (response.code() == 200) {
                    handler_old.sendEmptyMessageDelayed(0, 500L);
                    return;
                }
                if (response.code() == 404) {
                    Toast.makeText(BottleExchangeActivity.this, "订单不存在", Toast.LENGTH_LONG).show();
                    return;
                }
                if (response.code() == 401) {
                    Toast.makeText(BottleExchangeActivity.this, "鉴权失败，请重新登录" + response.code(), Toast.LENGTH_LONG).show();
                    return;
                }
                //测试
                jumpToOrderDeal();
                Toast.makeText(BottleExchangeActivity.this, "支付失败" + response.code(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private boolean readWriteCardDemo(int paramInt) {
        switch (paramInt) {
            default:
                return true;
            case 6:
                boolean bool = true;
                boolean hasSecondField=false;
                boolean hasFirstField=false;
                Ntag21x ntag21x = (Ntag21x) this.bleNfcDevice.getCard();
                if (ntag21x != null) {
                    try {
                        hasFirstField = ntag21x.HasFirstField();
                        hasSecondField = ntag21x.HasSecondField();
                    } catch (CardNoResponseException cardNoResponseException) {
                        cardNoResponseException.printStackTrace();
                        bool = false;
                    }
                    String str=null;
                    if(hasSecondField) {
                        try {
                            str = ntag21x.NdefTextReadSec();
                        } catch (CardNoResponseException cardNoResponseException) {
                            cardNoResponseException.printStackTrace();
                        }
                    }else{
                        try {
                            str = ntag21x.NdefTextRead();
                        } catch (CardNoResponseException cardNoResponseException) {
                            cardNoResponseException.printStackTrace();
                        }
                    }
                    if(str!=null) {
                        Message message = new Message();
                        message.obj = str;
                        if (this.m_orderServiceQualityShowFlag) {
                            message.what = 137;
                        } else {
                            message.what = 136;
                        }
                        this.handler.sendMessage(message);
                    }

                }
                return bool;
        }
    }

    private void refleshBottlesListKP() {
        this.m_textViewTotalCountKP.setText(this.m_BottlesMapKP.size() + "");
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, String> entry : this.m_BottlesMapKP.entrySet()) {
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            hashMap.put("bottleSpec", getSpecName(getBottleSpec(entry.getKey())));
            hashMap.put("bottleCode", entry.getKey());
            hashMap.put("bottleWeight", entry.getValue() + "公斤");
            arrayList.add(hashMap);
        } //下面这个暂定这个布局  不太确定
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.bottle_list_simple_items, new String[]{"bottleCode", "bottleWeight", "bottleSpec"}, new int[]{R.id.items_number, R.id.items_weight, R.id.items_spec});
        this.m_listView_kp.setAdapter(simpleAdapter);
        setListViewHeightBasedOnChildren(this.m_listView_kp);
    }

    private void refleshBottlesListZP() {
        this.m_textViewTotalCountZP.setText(this.m_BottlesMapZP.size() + "");
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, String> entry : this.m_BottlesMapZP.entrySet()) {
            HashMap<Object, Object> hashMap = new HashMap();
            if (getBottleSpec(entry.getKey()) == null) {
                hashMap.put("bottleSpec", "未知");
            } else {
                hashMap.put("bottleSpec", getSpecName(getBottleSpec(entry.getKey())));
            }
            hashMap.put("bottleCode", entry.getKey());
            hashMap.put("bottleWeight", entry.getValue() + "公斤");
            arrayList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.bottle_list_simple_items, new String[]{"bottleCode", "bottleWeight", "bottleSpec"}, new int[]{R.id.items_number, R.id.items_weight, R.id.items_spec});
        this.m_listView_zp.setAdapter(simpleAdapter);
        setListViewHeightBasedOnChildren(this.m_listView_zp);
    }

    private void searchNearestBleDevice() {
        this.msgBuffer.delete(0, this.msgBuffer.length());
        this.msgBuffer.append("正在搜索设备...");
        this.handler.sendEmptyMessage(0);
        if (!mScanner.isScanning() && (bleNfcDevice.isConnection() == BleManager.STATE_DISCONNECTED)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        mScanner.startScan(0);
                        mNearestBleLock.lock();
                        try {
                            mNearestBle = null;
                        }finally {
                            mNearestBleLock.unlock();
                        }
                        lastRssi = -100;

                        int searchCnt = 0;
                        while ((mNearestBle == null)
                                && (searchCnt < 20000)
                                && (mScanner.isScanning())
                                && (bleNfcDevice.isConnection() == BleManager.STATE_DISCONNECTED)) {
                            searchCnt++;
                            try {
                                Thread.sleep(1);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        if (mScanner.isScanning() && (bleNfcDevice.isConnection() == BleManager.STATE_DISCONNECTED)) {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            mScanner.stopScan();
                            mNearestBleLock.lock();
                            try {
                                if (mNearestBle != null) {
                                    mScanner.stopScan();
                                    msgBuffer.delete(0, msgBuffer.length());
                                    msgBuffer.append("正在连接设备...");
                                    handler.sendEmptyMessage(0);
                                    bleNfcDevice.requestConnectBleDevice(mNearestBle.getAddress());
                                } else {
                                    msgBuffer.delete(0, msgBuffer.length());
                                    msgBuffer.append("未找到设备！");
                                    handler.sendEmptyMessage(0);
                                }
                            }finally {
                                mNearestBleLock.unlock();
                            }
                        } else {
                            mScanner.stopScan();
                        }
                    }
                }
            }).start();
        }
    }

    private void setListViewHeightBasedOnChildren(ListView paramListView) {
        if (paramListView != null) {
            ListAdapter listAdapter = paramListView.getAdapter();
            if (listAdapter != null) {
                int i = 0;
                for (byte b = 0; b < listAdapter.getCount(); b++) {
                    View view = listAdapter.getView(b, null, (ViewGroup) paramListView);
                    view.measure(0, 0);
                    i += view.getMeasuredHeight();
                }
                ViewGroup.LayoutParams layoutParams = paramListView.getLayoutParams();
                layoutParams.height = paramListView.getDividerHeight() * (listAdapter.getCount() - 1) + i;
                paramListView.setLayoutParams(layoutParams);
            }
        }
    }

    private void setOrderHeadInfo() {
        try {
            m_curUserId = m_OrderJson.getJSONObject("customer").get("userId").toString();
            m_orderId = this.m_OrderJson.get("orderSn").toString();
            JSONObject jSONObject1 = m_OrderJson.getJSONObject("recvAddr");
            StringBuilder stringBuilder = new StringBuilder();
            m_customerAddress = stringBuilder.append(jSONObject1.get("city").toString()).append(jSONObject1.get("county").toString()).append(jSONObject1.get("detail").toString()).toString();
            JSONObject jSONObject2 = m_OrderJson.getJSONObject("orderTriggerType");
            isSpecialOrder = false;
            if (jSONObject2 != null && jSONObject2.get("index").toString().equals("1"))
                isSpecialOrder = true;
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showReadWriteDialog(String paramString, int paramInt) {
        Message message = new Message();
        message.what = 4;
        message.arg1 = paramInt;
        message.obj = paramString;
        this.handler.sendMessage(message);
    }

    private void showToast(String paramString) {
        if (toast == null) {
            toast = Toast.makeText(this, null, Toast.LENGTH_SHORT);
            toast.setGravity(17, 0, 0);
            LinearLayout linearLayout = (LinearLayout) toast.getView();
            WindowManager windowManager = (WindowManager) getSystemService(Service.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            tv = new TextView(this);
            linearLayout.getBackground().setAlpha(0);
            tv.setTextSize(40.0F);
            tv.setTextColor(getResources().getColor(R.color.blue));
            linearLayout.setGravity(17);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.setMargins(0, 0, 0, 180);
            tv.setLayoutParams(layoutParams);
            toast.setView(linearLayout);
            linearLayout.addView(tv);
        }
        tv.setText(paramString);
        toast.show();
    }

    private void show_deposit_slip() {
        final View view = getLayoutInflater().inflate(R.layout.show_deposit_slip, null); //这里原来是layout
        EditText editText1 = view.findViewById(R.id.textView_yjp_5kg_ys);
        EditText editText2 = view.findViewById(R.id.textView_yjp_15kg_ys);
        EditText editText3 = view.findViewById(R.id.textView_yjp_50kg_ys);
        EditText editText4 = view.findViewById(R.id.textView_gjp_5kg_ys);
        EditText editText5 = view.findViewById(R.id.textView_gjp_15kg_ys);
        EditText editText6 = view.findViewById(R.id.textView_gjp_50kg_ys);
        EditText editText7 = view.findViewById(R.id.textView_bfp_5kg_ys);
        EditText editText8 = view.findViewById(R.id.textView_bfp_15kg_ys);
        EditText editText9 = view.findViewById(R.id.textView_bfp_50kg_ys);
        addEditViewChanged(editText2, view);
        addEditViewChanged(editText3, view);
        addEditViewChanged(editText4, view);
        addEditViewChanged(editText5, view);
        addEditViewChanged(editText6, view);
        addEditViewChanged(editText1, view);
        addEditViewChanged(editText7, view);
        addEditViewChanged(editText8, view);
        addEditViewChanged(editText9, view);
        AlertDialog.Builder builder = (new AlertDialog.Builder(this)).setTitle("电子押金单").setIcon(R.drawable.icon_logo).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                m_ptp_quantity_5kg = getEditTextToInt(view.findViewById(R.id.textView_ptp_5kg_quantity));
                m_ptp_quantity_15kg = getEditTextToInt(view.findViewById(R.id.textView_ptp_15kg_quantity));
                m_ptp_quantity_50kg = getEditTextToInt(view.findViewById(R.id.textView_ptp_50kg_quantity));
                m_qp_quantity_5kg = getEditTextToInt(view.findViewById(R.id.textView_qp_5kg_quantity));
                m_qp_quantity_15kg = getEditTextToInt(view.findViewById(R.id.textView_qp_15kg_quantity));
                m_qp_quantity_50kg = getEditTextToInt(view.findViewById(R.id.textView_qp_50kg_quantity));
                m_hp_quantity_5kg = getEditTextToInt(view.findViewById(R.id.textView_hp_5kg_quantity));
                m_hp_quantity_15kg = getEditTextToInt(view.findViewById(R.id.textView_hp_15kg_quantity));
                m_hp_quantity_50kg = getEditTextToInt(view.findViewById(R.id.textView_hp_50kg_quantity));
                m_yjp_quantity_5kg = getEditTextToInt(view.findViewById(R.id.textView_yjp_5kg_quantity));
                m_yjp_quantity_15kg = getEditTextToInt(view.findViewById(R.id.textView_yjp_15kg_quantity));
                m_yjp_quantity_50kg = getEditTextToInt(view.findViewById(R.id.textView_yjp_50kg_quantity));
                m_gjp_quantity_5kg = getEditTextToInt(view.findViewById(R.id.textView_gjp_5kg_quantity));
                m_gjp_quantity_15kg = getEditTextToInt(view.findViewById(R.id.textView_gjp_15kg_quantity));
                m_gjp_quantity_50kg = getEditTextToInt(view.findViewById(R.id.textView_gjp_50kg_quantity));
                m_bfp_quantity_5kg = getEditTextToInt(view.findViewById(R.id.textView_bfp_5kg_quantity));
                m_bfp_quantity_15kg = getEditTextToInt(view.findViewById(R.id.textView_bfp_15kg_quantity));
                m_bfp_quantity_50kg = getEditTextToInt(view.findViewById(R.id.textView_bfp_50kg_quantity));
                m_yjp_price_5kg = getEditTextToInt(view.findViewById(R.id.textView_yjp_5kg_ys));
                m_yjp_price_15kg = getEditTextToInt(view.findViewById(R.id.textView_yjp_15kg_ys));
                m_yjp_price_50kg = getEditTextToInt(view.findViewById(R.id.textView_yjp_50kg_ys));
                m_gjp_price_5kg = getEditTextToInt(view.findViewById(R.id.textView_gjp_5kg_ys));
                m_gjp_price_15kg = getEditTextToInt(view.findViewById(R.id.textView_gjp_15kg_ys));
                m_gjp_price_50kg = getEditTextToInt(view.findViewById(R.id.textView_gjp_50kg_ys));
                m_bfp_price_5kg = getEditTextToInt(view.findViewById(R.id.textView_bfp_5kg_ys));
                m_bfp_price_15kg = getEditTextToInt(view.findViewById(R.id.textView_bfp_15kg_ys));
                m_bfp_price_50kg = getEditTextToInt(view.findViewById(R.id.textView_bfp_50kg_ys));
                m_yjp_ys_total = getTextViewToString(view.findViewById(R.id.textView_ys));
                m_yjp_ss_total = getTextViewToString(view.findViewById(R.id.textView_ys));
                if (m_deliveryUser.getScanType() == 2 || m_deliveryUser.getScanType() == 3) {
                    orderServiceQualityUpload(true);
                    return;
                }
                if (m_deliveryUser.getScanType() == 0 || m_deliveryUser.getScanType() == 1) {
                    orderServiceQualityShow();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private boolean startAutoSearchCard() throws DeviceNoResponseException {
        for (byte b = 0; ; b++) {
            boolean bool = bleNfcDevice.startAutoSearchCard((byte) 20, (byte) 2);
            if (bool || b >= 10) {
                if (!bool) {
                    msgBuffer.append("不支持自动寻卡！\r\n");
                    handler.sendEmptyMessage(0);
                }
                return bool;
            }
        }
    }

    private String toChinese(String paramString) {
        String[] arrayOfString = new String[10];
        arrayOfString[0] = "零";
        arrayOfString[1] = "一";
        arrayOfString[2] = "二";
        arrayOfString[3] = "三";
        arrayOfString[4] = "四";
        arrayOfString[5] = "五";
        arrayOfString[6] = "六";
        arrayOfString[7] = "七";
        arrayOfString[8] = "八";
        arrayOfString[9] = "九";
        String str = "";
        int i = paramString.length();
        for (byte b = 0; b < i; b++) {
            int j = paramString.charAt(b) - 48;
            if (b != i - 1 && j != 0) {
                (new String[11])[0] = "十";
                (new String[11])[1] = "百";
                (new String[11])[2] = "千";
                (new String[11])[3] = "万";
                (new String[11])[4] = "十";
                (new String[11])[5] = "百";
                (new String[11])[6] = "千";
                (new String[11])[7] = "亿";
                (new String[11])[8] = "十";
                (new String[11])[9] = "百";
                (new String[11])[10] = "千";
                str = str + arrayOfString[j] + (new String[11])[i - 2 - b];
            } else {
                str = str + arrayOfString[j];
            }
        }
        return str;
    }

    private boolean upLoadGasCylinder() {
        Map map = new HashMap();
        map.put("recycleGasCylinder", this.m_BottlesMapKP.toString());
        map.put("deliveryGasCylinder", this.m_BottlesMapZP.toString());
        if (this.m_BottlesMapKP.size() == 0 && this.m_BottlesMapZP.size() == 0)
            return false;
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.postForm(OkHttpUtil.URL + "/Orders/" + this.m_orderId, map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(BottleExchangeActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() != 200) {
                    if (response.code() == 404) {
                        Toast.makeText(BottleExchangeActivity.this, "订单不存在", Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(BottleExchangeActivity.this, "瓶号上传失败，" + response.code(), Toast.LENGTH_LONG).show();
                }
            }
        });
        return true;
    }

    public void bottleTakeOverUnit(final String bottleCode, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean1, final boolean isKP) {
        //测试
        /*
        if (isKP) {
            addKP(bottleCode);
            return;
        }
        if (!isKP) {
            addZP(bottleCode);
            return;
        }
        */
        boolean bool = false;
        if (this.m_BottlesMapKP.containsKey(bottleCode))
            bool = true;
        if (this.m_BottlesMapZP.containsKey(bottleCode))
            bool = true;
        if (bool) {
            Toast.makeText(this, "钢瓶号：" + bottleCode + "    请勿重复提交！", Toast.LENGTH_SHORT).show();
            return;
        }

        Map map = new HashMap();

        String url=OkHttpUtil.URL + "/GasCylinder/check/" + bottleCode+"?srcUserId="+paramString2+"&targetUserId="+paramString3+"&serviceStatus="+paramString4+"&enableForce="+String.valueOf(paramBoolean1)+"&note="+paramString5;
        Logger.e("URL: " + url);
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.PUT(url, map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(BottleExchangeActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Logger.e("bottleTakeOverUnit: " + response.code());
                Logger.e("bottleTakeOverUnit: "+response.message());
                Logger.e("bottleTakeOverUnit: "+response.body().string());
                if (response.code() == 200) {
                    if (isKP) {
                        addKP(bottleCode);
                        return;
                    }
                    addZP(bottleCode);
                    return;
                }
                MediaPlayer.create(BottleExchangeActivity.this, R.raw.alarm).start();
                if (response.code() == 409) {
                    (new AlertDialog.Builder(BottleExchangeActivity.this)).setTitle("钢瓶异常流转！").
                            setMessage("钢瓶号 :" + bottleCode + "\r\n错误原因:" + response.message() + "\r\n确认强制交接吗？").setIcon(R.mipmap.icon_common_user).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                            if (Tools.isFastClick()) {
                                MediaPlayer.create((Context) BottleExchangeActivity.this, 2131558407).start();
                                if (isKP) {
                                    addKP(bottleCode);
                                    return;
                                }
                            } else {
                                addZP(bottleCode);
                                return;
                            }
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                        }
                    }).show();
                    return;
                }
                (new AlertDialog.Builder(BottleExchangeActivity.this)).setTitle("钢瓶异常流转！").setMessage("钢瓶号 :" + bottleCode + "\r\n错误原因:" + response.message()).setIcon(R.mipmap.icon_common_user).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                    }
                }).show();
            }
        });
    }

    public void deleteKP(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除钢瓶");
        builder.setMessage("您确定要删除这只钢瓶吗?");
        builder.setIcon(R.drawable.icon_bottle);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                new HashMap<Object, Object>();
                String str = (String) ((Map) m_listView_kp.getItemAtPosition(position)).get("bottleCode");
                m_BottlesMapKP.remove(str);
                refleshBottlesListKP();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            }
        });
        builder.show();
    }

    public void deleteZP(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("删除钢瓶");
        builder.setMessage("您确定要删除这只钢瓶吗?");
        builder.setIcon(R.drawable.icon_bottle);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                new HashMap<Object, Object>();
                String str = (String) ((Map) m_listView_zp.getItemAtPosition(position)).get("bottleCode");
                m_BottlesMapZP.remove(str);
                refleshBottlesListZP();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            }
        });
        builder.show();
    }

    public String getBottleSpec(String paramString) {
        return this.m_BottlesSpecMap.containsKey(paramString) ? this.m_BottlesSpecMap.get(paramString) : null;
    }

    public void getMyBottles() {
        Map<String, String> map = new HashMap();
        map.put("liableUserId", this.m_deliveryUser.getUsername());
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.GET(OkHttpUtil.URL + "/GasCylinder/", map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(BottleExchangeActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() != 200) {
                    Toast.makeText(BottleExchangeActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                    return;
                }
                String string = response.body().string();
                Logger.e("getMyBottles: " + string);
                Gson gson = new Gson();
                setData(gson.fromJson(string, Data_UserBottles.class));
            }

            private void setData(Data_UserBottles userBottles) {
                try {
                    m_myBottlesMap.clear();
                    int size = userBottles.getItems().size();
                    for (byte b = 0; b < size; b++) {
                        Gson gson = new Gson();
                        JSONObject jSONObject1 = new JSONObject(gson.toJson(userBottles.getItems().get(b)));
                        String str = userBottles.getItems().get(b).getNumber();
                        m_myBottlesMap.put(str, jSONObject1);
                    }
                } catch (JSONException e) {
                    Toast.makeText(BottleExchangeActivity.this, "异常" + e.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public String getSpecName(String paramString) {
        if (paramString == null)
            return "";
        if (paramString.equals("0001"))
            return "5公斤";
        if (paramString.equals("0002"))
            return "15公斤";
        if (paramString.equals("0003"))
            return "50公斤";
        Toast.makeText(this, "未知钢瓶规格" + paramString, Toast.LENGTH_SHORT).show();
        return null;
    }

    public void getUserBottles() {
        Map<String, String> map = new HashMap();
        map.put("liableUserId", this.m_curUserId);
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.GET(OkHttpUtil.URL + "/GasCylinder/", map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(BottleExchangeActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() != 200) {
                    Toast.makeText(BottleExchangeActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                    return;
                }
                String string = response.body().string();
                Logger.e("getUserBottles: " + string);
                Gson gson = new Gson();
                setData(gson.fromJson(string, Data_UserBottles.class));
            }

            private void setData(Data_UserBottles userBottles) {
                int size = userBottles.getItems().size();
                for (int b = 0; b < size; b++) {
                    Data_UserBottles.ItemsBean bean = userBottles.getItems().get(b);
                    String number = bean.getNumber();
                    Gson gson = new Gson();
                    try {
                        m_userBottlesMap.put(number, new JSONObject(gson.toJson(bean)));
                    } catch (JSONException e) {
                        Toast.makeText(BottleExchangeActivity.this, "异常" + e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    void init() {
        Logger.e("BottleExchangeActivity");
        try {
            TextToSpeech textToSpeech = new TextToSpeech(this, this);//将文字快速转化为语音进行播放或者保存为音频文件
            tts = textToSpeech;
            setContentView(R.layout.activity_bottle_exchange);
            Bundle bundle = getIntent().getExtras();
            m_bundle = bundle;
            String str = bundle.getString("order");
            JSONObject jSONObject = new JSONObject(str);
            m_OrderJson = jSONObject;
            m_spinnerGPHeadKP = (Spinner) findViewById(R.id.spinner_gp_head_KP);
            m_spinnerGPHeadZP = (Spinner) findViewById(R.id.spinner_gp_head_ZP);
            m_buttonNext = (Button) findViewById(R.id.button_next);//用户确认
            m_listView_kp = (ListView) findViewById(R.id.listview_kp);//空瓶回收
            m_listView_zp = (ListView) findViewById(R.id.listview_zp);//重瓶录入
            radioGroup_nfc = (RadioGroup) findViewById(R.id.radioGroup_nfc_id);//选项
            radioButton_kp = (RadioButton) findViewById(R.id.radioButton_kp_id);
            radioButton_zp = (RadioButton) findViewById(R.id.radioButton_zp_id);
            m_imageViewKPEye = (ImageView) findViewById(R.id.imageView_KPEYE);
            m_imageViewZPEye = (ImageView) findViewById(R.id.imageView_ZPEYE);
            m_textViewTotalCountKP = (TextView) findViewById(R.id.items_totalCountKP);
            m_textViewTotalCountZP = (TextView) findViewById(R.id.items_totalCountZP);
            m_bottleIdKPEditText = (EditText) findViewById(R.id.input_bottleIdKP);//手动输入空瓶号
            m_bottleIdZPEditText = (EditText) findViewById(R.id.input_bottleIdZP);
            m_imageAddKPManual = (ImageView) findViewById(R.id.imageView_addKPManual);
            m_imageAddZPManual = (ImageView) findViewById(R.id.imageView_addZPManual);
            msgText_weightDevice = (TextView) findViewById(R.id.msgText_weightDevice);
            m_imageView_search_weightDevice = (ImageView) findViewById(R.id.imageView_search_weightDevice);
            m_imageView_search_weightDevice.setOnClickListener(this);
            m_imageViewZPEye.setOnClickListener(this);
            m_imageViewKPEye.setOnClickListener(this);
            m_imageAddKPManual.setOnClickListener(this);
            m_imageAddZPManual.setOnClickListener(this);
            m_buttonNext.setOnClickListener(this);
            radioGroup_nfc.setOnCheckedChangeListener(this.listen);
            radioGroup_nfc.check(this.radioButton_kp.getId());
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            m_userBottlesMap = (Map) hashMap;
            hashMap = new HashMap<Object, Object>();
            m_myBottlesMap = (Map) hashMap;
            hashMap = new HashMap<Object, Object>();
            m_BottlesMapKP = (Map) hashMap;
            hashMap = new HashMap<Object, Object>();
            m_BottlesMapZP = (Map) hashMap;
            hashMap = new HashMap<Object, Object>();
            m_BottlesSpecMap = (Map) hashMap;
            appContext = (AppContext) getApplicationContext();
            m_deliveryUser = this.appContext.getUser();
            if (this.m_deliveryUser == null) {
                Toast.makeText(this, "登陆会话失效", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
            ListView listView4 = this.m_listView_kp;
            AdapterView.OnItemLongClickListener onItemLongClickListener1 = new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                    String str = ((TextView) param1View.findViewById(R.id.items_number)).getText().toString();//暂定这个
                    Logger.e("code : "+str);
                    getBottleWeight(str, false);
                    return true;
                }
            };
            listView4.setOnItemLongClickListener(onItemLongClickListener1);
            ListView listView2 = this.m_listView_kp;
            AdapterView.OnItemClickListener onItemClickListener2 = new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                    deleteKP(param1Int);
                }
            };
            listView2.setOnItemClickListener(onItemClickListener2);
            ListView listView3 = this.m_listView_zp;
            AdapterView.OnItemClickListener onItemClickListener1 = new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                    deleteZP(param1Int);
                }
            };
            listView3.setOnItemClickListener(onItemClickListener1);
            ListView listView1 = this.m_listView_zp;
            AdapterView.OnItemLongClickListener onItemLongClickListener2 = new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                    String str = ((TextView) param1View.findViewById(R.id.items_number)).getText().toString();

                    getBottleWeight(str, true);
                    return true;
                }
            };
            listView1.setOnItemLongClickListener(onItemLongClickListener2);
            Spinner spinner2 = this.m_spinnerGPHeadKP;
            String[] strs = new String[8];
            strs[0] = "KMA2B";
            strs[1] = "KMA2BA";
            strs[2] = "KMA2BB";
            strs[3] = "KMA2BC";
            strs[4] = "KMA2BD";
            strs[5] = "KMA2BE";
            strs[6] = "KMA2BF";
            strs[7] = "";
            AdapterView.OnItemSelectedListener onItemSelectedListener1 = new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                    m_gp_code_head_KP = strs[param1Int];
                    Logger.e(""+m_gp_code_head_KP);
                    SharedPreferencesHelper.initial(BottleExchangeActivity.this);
                    SharedPreferencesHelper.put("codeHeadIndex", param1Int);
                }

                public void onNothingSelected(AdapterView<?> param1AdapterView) {
                }
            };
            spinner2.setOnItemSelectedListener(onItemSelectedListener1);
            Spinner spinner1 = this.m_spinnerGPHeadZP;
            m_gp_code_head_ZP = "KMA2B";
            AdapterView.OnItemSelectedListener onItemSelectedListener2 = new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                    m_gp_code_head_ZP = strs[param1Int];
                    SharedPreferencesHelper.put("codeHeadIndex", Integer.valueOf(param1Int));
                }

                public void onNothingSelected(AdapterView<?> param1AdapterView) {
                }
            };
            spinner1.setOnItemSelectedListener(onItemSelectedListener2);
            // scalerSDK scalerSDK1 = new scalerSDK();
            // this((Context)this);
            // this.scale = scalerSDK1;
            this.handler_weightScale.post(this.task);
            this.layout_inputWeight = null;
            this.m_orderServiceQualityShowFlag = false;
            setOrderHeadInfo();
            getUserBottles();
            getMyBottles();
            blueDeviceInitial();
            GetUserCard();
            this.m_ptp_quantity_5kg = 0;
            this.m_ptp_quantity_15kg = 0;
            this.m_ptp_quantity_50kg = 0;
            this.m_yjp_quantity_5kg = 0;
            this.m_yjp_quantity_15kg = 0;
            this.m_yjp_quantity_50kg = 0;
            this.m_yjp_ys_total = "0";
            this.m_yjp_ss_total = "0";
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick(View paramView) {
        Intent intent;
        Bundle bundle;
        switch (paramView.getId()) {
            //case 2131230819:
            //    if (this.scale != null && this.scale.getDevicelist().size() == 1)
            //        this.scale.bleSend(this.scale.getDevicelist().get(0), (byte) 1);
            case R.id.button_next:
                if (isSpecialOrder) {
                    Iterator<Map.Entry<String, String>> iterator = this.m_BottlesMapKP.entrySet().iterator();
                    while (iterator.hasNext()) {
                        String str = (String) ((Map.Entry) iterator.next()).getValue();
                        if (str.length() == 0 || Double.parseDouble(str) < 4.0D) {
                            Toast.makeText(this, "所有空瓶必须称重，重量错误!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                    iterator = this.m_BottlesMapZP.entrySet().iterator();
                    while (iterator.hasNext()) {
                        String str = (String) ((Map.Entry) iterator.next()).getValue();
                        if (str.length() == 0 || Double.parseDouble(str) < 4.0D) {
                            Toast.makeText(this, "所有重瓶必须称重，重量错误!", Toast.LENGTH_LONG).show();
                            return;
                        }
                    }
                }
                if (m_deliveryUser.getScanType() == 0 || m_deliveryUser.getScanType() == 3) {
                    Logger.e(isBottlesQuantityOK());
                    if (!isBottlesQuantityOK()) {
                        return;
                    }
                }
                show_deposit_slip();
                break;
            case R.id.imageView_KPEYE:
                intent = new Intent();
                bundle = new Bundle();
                bundle.putString("userId", this.m_curUserId);
                intent.setClass(this, MybottlesActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.imageView_ZPEYE:
                intent = new Intent();
                bundle = new Bundle();
                bundle.putString("userId", this.m_deliveryUser.getUsername());
                intent.setClass(this, MybottlesActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.imageView_addKPManual:
                if (Tools.isFastClick()) {
                    if (TextUtils.isEmpty(this.m_gp_code_head_KP)) {
                        this.m_gp_code_head_KP = "";
                    }
                    String s = this.m_gp_code_head_KP + this.m_bottleIdKPEditText.getText().toString();
                    Logger.e(s);
                    bottleTakeOverUnit(s, this.m_curUserId, this.m_deliveryUser.getUsername(), "6", this.m_customerAddress + "空瓶回收", false, true);
                    this.m_bottleIdKPEditText.setText("");
                }
                break;
            case R.id.imageView_addZPManual:
                if (Tools.isFastClick()) {
                    String s = this.m_gp_code_head_ZP + this.m_bottleIdZPEditText.getText().toString();
                    if (TextUtils.isEmpty(s)) {
                        s = "";
                    }
                    bottleTakeOverUnit(s, this.m_deliveryUser.getUsername(), this.m_curUserId, "5", this.m_customerAddress + "重瓶落户", false, false);
                    this.m_bottleIdZPEditText.setText("");
                }
                break;
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.readWriteDialog != null)
            this.readWriteDialog.dismiss();
        //        unbindService(this.mServiceConnection);
    }

    public void onInit(int paramInt) {
        if (paramInt == 0) {
            paramInt = this.tts.setLanguage(Locale.CHINESE);
            if (paramInt != -1 && paramInt != -2)
                this.tts.setLanguage(Locale.US);
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.mBleNfcDeviceService != null) {
            this.mBleNfcDeviceService.setScannerCallback(this.scannerCallback);
            this.mBleNfcDeviceService.setDeviceManagerCallback(this.deviceManagerCallback);
        }
    }

    public void updateBottleSpec(final String bottleCode) {
        Map map = new HashMap();
        map.put("number", bottleCode);
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.GET(OkHttpUtil.URL + "/GasCylinder/", map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(BottleExchangeActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() != 200) {
                    Toast.makeText(BottleExchangeActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
                }
                String s = response.body().string();
                Logger.e("updateBottleSpec: "+s);
                Gson gson = new Gson();
                Data_UpdateBottleSpec data_updateBottleSpec = gson.fromJson(JSONTokener(s), Data_UpdateBottleSpec.class);
                List<Data_UpdateBottleSpec.ItemsBean> items = data_updateBottleSpec.getItems();
                for (byte b = 0; b< items.size(); b++) {
                    if (b < items.size()) {
                        Data_UpdateBottleSpec.ItemsBean itemsBean = items.get(b);
                        if (itemsBean.getNumber().equals(bottleCode)) {
                            Logger.e("getSpec code : "+itemsBean.getSpec().getCode());
                            m_BottlesSpecMap.put(bottleCode, itemsBean.getSpec().getCode());
                            refleshBottlesListZP();
                            refleshBottlesListKP();
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
        });
    }

    public String JSONTokener(String in) {
        // consume an optional byte order mark (BOM) if it exists
        if (in != null && in.startsWith("\ufeff")) {
            in = in.substring(1);
        }
        return in;
    }

    private class StartSearchButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if ( (bleNfcDevice.isConnection() == BleManager.STATE_CONNECTED) ) {
                bleNfcDevice.requestDisConnectDevice();
                return;
            }
            searchNearestBleDevice();
        }
    }
}
