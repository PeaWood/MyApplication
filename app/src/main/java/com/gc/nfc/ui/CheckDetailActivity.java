package com.gc.nfc.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.Poi;
import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
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
import com.gc.nfc.domain.User;
import com.gc.nfc.http.Logger;
import com.gc.nfc.interfaces.Netcallback;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CheckDetailActivity extends BaseActivity implements View.OnClickListener, INaviInfoCallback {
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
                    handlerBlue.sendEmptyMessage(3);
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
            handlerBlue.sendEmptyMessage(0);
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
                (new Thread(new Runnable() {
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
                        //                  bleNfcDevice.openBeep(100, 100, 2);
                    }
                })).start();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handlerBlue = new Handler() {
        public void handleMessage(Message param1Message) {
            msgText.setText(msgBuffer);
            if (bleNfcDevice.isConnection() == 2 || bleNfcDevice.isConnection() == 1)
                ;
            switch (param1Message.what) {
                default:
                    return;
                case 3:
                    (new Thread(new Runnable() {
                        public void run() {
                            try {
                                bleNfcDevice.getDeviceVersions();
                                handlerBlue.sendEmptyMessage(0);
                                if (bleNfcDevice.getDeviceBatteryVoltage() < 3.61D) {
                                    msgBuffer.append("(电量低)");
                                } else {
                                    msgBuffer.append("(电量充足)");
                                }
                                handlerBlue.sendEmptyMessage(0);
                                if (bleNfcDevice.androidFastParams(true))
                                    ;
                                handlerBlue.sendEmptyMessage(0);
                                handlerBlue.sendEmptyMessage(0);
                                startAutoSearchCard();
                            } catch (DeviceNoResponseException deviceNoResponseException) {
                                deviceNoResponseException.printStackTrace();
                            }
                        }
                    })).start();
                case 137:
                    break;
            }
            String[] arrayOfString = param1Message.obj.toString().split(":");
            if (arrayOfString.length != 2)
                showToast("无效卡格式！");
            String str1 = arrayOfString[0];
            String str2 = arrayOfString[1];
            if (m_handedUserCard == null)
                showToast("该用户未绑定用户卡");
            if (!str2.equals(m_handedUserCard))
                showToast("非本人卡号！");
            if (str1.equals("Y"))
                orderServiceQualityUpload(true);
            if (str1.equals("N"))
                orderServiceQualityUpload(false);
            showToast("无效卡格式！");
        }
    };

    private int lastRssi = -100;

    BleNfcDeviceService mBleNfcDeviceService;

    private BluetoothDevice mNearestBle = null;

    private Lock mNearestBleLock = new ReentrantLock();

    private Scanner mScanner;

    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName param1ComponentName, IBinder param1IBinder) {
            BleNfcDeviceService bleNfcDeviceService = ((BleNfcDeviceService.LocalBinder) param1IBinder).getService();
            bleNfcDevice = bleNfcDeviceService.bleNfcDevice;
            mScanner = bleNfcDeviceService.scanner;
            bleNfcDeviceService.setDeviceManagerCallback(deviceManagerCallback);
            bleNfcDeviceService.setScannerCallback(scannerCallback);
            searchNearestBleDevice();
        }

        public void onServiceDisconnected(ComponentName param1ComponentName) {
            mBleNfcDeviceService = null;
        }
    };

    private JSONObject m_CheckJson;

    private String m_businessKey;

    private Button m_buttonNext;

    private String m_currentCustomerId;

    private String m_customerPhone;

    private String m_deviceNumber;

    private EditText m_editTextPs;

    private String m_handedUserCard = null;

    private int m_iDeviceType;

    private ImageView m_imageDeviceReflesh;

    private ImageView m_imageViewCall;

    private ImageView m_imageViewDeviceStatus;

    private ImageView m_imageViewNav;

    private ImageView m_imageViewPic;

    private ImageView m_imageViewSearchBlue = null;

    private String m_orderCreateTime;

    private boolean m_orderServiceQualityShowFlag;

    private int m_orderStatus;

    private String m_recvAddr;

    private LatLng m_recvLocation;

    private Spinner m_spinnerCheckType;

    private TextView m_textViewAddress;

    private TextView m_textViewCreateTime;

    private TextView m_textViewDeviceNumber;

    private TextView m_textViewDeviceType;

    private TextView m_textViewOrderSn;

    private TextView m_textViewUserId;

    private TextView m_textViewUserPhone;

    private User m_user;

    private StringBuffer msgBuffer;

    private TextView msgText = null;

    private ProgressDialog readWriteDialog = null;

    private ScannerCallback scannerCallback = new ScannerCallback() {
        public void onReceiveScanDevice(BluetoothDevice device, int param1Int, byte[] param1ArrayOfbyte) {
            super.onReceiveScanDevice(device, param1Int, param1ArrayOfbyte);
            if (Build.VERSION.SDK_INT >= 21)
                System.out.println("Activity搜到设备：" + device.getName() + " 信号强度：" + param1Int + " scanRecord：" + StringTool.byteHexToSting(param1ArrayOfbyte));
            if (param1ArrayOfbyte != null && StringTool.byteHexToSting(param1ArrayOfbyte).contains("017f5450") && param1Int >= -55) {
                handlerBlue.sendEmptyMessage(0);
                if (mNearestBle != null) {
                    if (param1Int > lastRssi) {
                        mNearestBleLock.lock();
                        try {
                            mNearestBle = device;
                            return;
                        } finally {
                            mNearestBleLock.unlock();
                        }
                    }
                    return;
                }
                mNearestBleLock.lock();
                try {
                    mNearestBle = device;
                    mNearestBleLock.unlock();
                    return;
                } finally {
                    mNearestBleLock.unlock();
                }
            }
        }

        public void onScanDeviceStopped() {
            super.onScanDeviceStopped();
        }
    };

    private Timer timer;

    private Toast toast = null;

    TextView tv;

    private void GetDeviceSatus() {
        NetRequestConstant netRequestConstant = new NetRequestConstant();
        netRequestConstant.setType(HttpRequestType.GET);
        netRequestConstant.context = this;
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        if (this.m_iDeviceType == 0 || this.m_iDeviceType == 3) {
            netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/GasCynTray";
            hashMap.put("number", this.m_deviceNumber);
        } else {
            netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/Olfactometer";
            hashMap.put("code", this.m_deviceNumber);
        }
        netRequestConstant.setParams(hashMap);
        getServer(new Netcallback() {
            public void preccess(Object param1Object, boolean param1Boolean) {
                if (param1Boolean) {
                    HttpResponse response = (HttpResponse) param1Object;
                    if (param1Object != null) {
                        if (response.getStatusLine().getStatusCode() == 200) {
                            try {
                                JSONObject jSONObject = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                                Logger.e("GetDeviceSatus : "+jSONObject.toString());
                                JSONArray items = jSONObject.getJSONArray("items");
                                if (items.length() == 1) {
                                    String str = items.getJSONObject(0).getString("updateTime");
                                    if (str.length() == 0) {
                                        m_imageViewDeviceStatus.setImageResource(R.drawable.msg_error);
                                        return;
                                    }
                                    param1Object = new Date();
                                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
                                    Date date = simpleDateFormat.parse(str);
                                    if (getDiffSecond((Date) param1Object, date) > 86400L) {
                                        m_imageViewDeviceStatus.setImageResource(R.drawable.msg_error);
                                        return;
                                    }
                                    m_imageViewDeviceStatus.setImageResource(R.drawable.msg_info);
                                    return;
                                }
                                showToast("设备不存在！");
                            } catch (JSONException jSONException) {
                                Toast.makeText(CheckDetailActivity.this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
                            } catch (IOException iOException) {
                                Toast.makeText(CheckDetailActivity.this, "异常" + iOException.toString(), Toast.LENGTH_LONG).show();
                            } catch (ParseException parseException) {
                                Toast.makeText(CheckDetailActivity.this, "异常" + parseException.toString(), Toast.LENGTH_LONG).show();
                            }
                            return;
                        }
                        Toast.makeText(CheckDetailActivity.this, "用户卡查询失败", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(CheckDetailActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(CheckDetailActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
            }
        }, netRequestConstant);
    }

    private void GetUserCard() {
        if (this.m_currentCustomerId == null) {
            showToast("客户资料被删除！");
            return;
        }
        NetRequestConstant netRequestConstant = new NetRequestConstant();
        netRequestConstant.setType(HttpRequestType.GET);
        netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/UserCard";
        netRequestConstant.context = this;
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        hashMap.put("userId", this.m_currentCustomerId);
        hashMap.put("status", Integer.valueOf(1));
        netRequestConstant.setParams(hashMap);
        getServer(new Netcallback() {
            public void preccess(Object param1Object, boolean param1Boolean) {
                if (param1Boolean) {
                    HttpResponse response = (HttpResponse) param1Object;
                    if (param1Object != null) {
                        if (response.getStatusLine().getStatusCode() == 200) {
                            try {
                                JSONObject jSONObject = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                                JSONArray items = jSONObject.getJSONArray("items");
                                if (items.length() == 1) {
                                    m_handedUserCard = items.getJSONObject(0).getString("number");
                                    return;
                                }
                                m_handedUserCard = (String) null;
                                showToast("该用户未绑定用户卡");
                            } catch (JSONException jSONException) {
                                Toast.makeText(CheckDetailActivity.this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
                            } catch (IOException iOException) {
                                Toast.makeText(CheckDetailActivity.this, "异常" + iOException.toString(), Toast.LENGTH_LONG).show();
                            }
                            return;
                        }
                        Toast.makeText(CheckDetailActivity.this, "用户卡查询失败", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(CheckDetailActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(CheckDetailActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
            }
        }, netRequestConstant);
    }

    private void blueDeviceInitial() {
        this.msgText = (TextView) findViewById(R.id.msgText);
        this.m_imageViewSearchBlue = (ImageView) findViewById(R.id.imageView_search);
        this.m_imageViewSearchBlue.setOnClickListener(new StartSearchButtonListener());
        this.msgBuffer = new StringBuffer();
        bindService(new Intent(this, BleNfcDeviceService.class), this.mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private long getDiffSecond(Date paramDate1, Date paramDate2) {
        return (paramDate1.getTime() - paramDate2.getTime()) / 1000L;
    }

    private void getOrderOps() {
        if (this.m_user == null) {
            Toast.makeText(this, "未登录！", Toast.LENGTH_LONG).show();
            return;
        }
        orderServiceQualityShow();
    }

    private String getResponseMessage(HttpResponse paramHttpResponse) {
        return null;
    }

    private void orderServiceQualityShow() {
        GetUserCard();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.user_evaluate, null);
        builder.setIcon(R.drawable.icon_logo);
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
        NetRequestConstant netRequestConstant = new NetRequestConstant();
        netRequestConstant.setType(HttpRequestType.PUT);
        netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/Repair/" + this.m_businessKey;
        netRequestConstant.context = this;
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        if (paramBoolean) {
            hashMap.put("processStatus", "PTSolved");
            int i = this.m_spinnerCheckType.getSelectedItemPosition();
            (new String[5])[0] = "BadWarnDevice";
            (new String[5])[1] = "BadWarnRestart";
            (new String[5])[2] = "LackWaterWarn";
            (new String[5])[3] = "NouseWarn";
            (new String[5])[4] = "OtherWarn";
            hashMap.put("repairResultType", (new String[5])[i]);
            if (this.m_editTextPs.getText() != null)
                hashMap.put("resloveInfo", this.m_editTextPs.getText().toString());
            netRequestConstant.setBody(hashMap);
            getServer(new Netcallback() {
                public void preccess(Object param1Object, boolean param1Boolean) {
                    if (param1Boolean) {
                        HttpResponse response = (HttpResponse) param1Object;
                        if (param1Object != null) {
                            if (response.getStatusLine().getStatusCode() == 200) {
                                Toast.makeText(CheckDetailActivity.this, "处理成功！", Toast.LENGTH_LONG).show();
                                MediaPlayer.create(CheckDetailActivity.this, 2131558403).start();
                                Intent intent = new Intent(getApplicationContext(), MainlyActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("switchTab", 2);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                                return;
                            }
                            if (response.getStatusLine().getStatusCode() == 404) {
                                Toast.makeText(CheckDetailActivity.this, "巡检失败", Toast.LENGTH_LONG).show();
                                return;
                            }
                            if (response.getStatusLine().getStatusCode() == 401) {
                                Toast.makeText(CheckDetailActivity.this, "鉴权失败，请重新登录" + response.getStatusLine().getStatusCode(), Toast.LENGTH_LONG).show();
                                return;
                            }
                            Toast.makeText(CheckDetailActivity.this, "巡检失败" + response.getStatusLine().getStatusCode(), Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(CheckDetailActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(CheckDetailActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
                }
            }, netRequestConstant);
            return;
        }
        showToast("客户不同意！");
    }

    private boolean readWriteCardDemo(int paramInt) {
        switch (paramInt) {
            default:
                return true;
            case 6:
                break;
        }
        Ntag21x ntag21x = (Ntag21x) this.bleNfcDevice.getCard();
        if (ntag21x != null) {
            boolean bool = true;
            try {
                String str = ntag21x.NdefTextRead();
                Message message = new Message();
                message.obj = str;
                if (this.m_orderServiceQualityShowFlag) {
                    message.what = 137;
                } else {
                    message.what = 136;
                }
                this.handlerBlue.sendMessage(message);
            } catch (CardNoResponseException cardNoResponseException) {
                cardNoResponseException.printStackTrace();
                bool = false;
            }
            return bool;
        }
        return false;
    }

    private void searchNearestBleDevice() {
        this.msgBuffer.delete(0, this.msgBuffer.length());
        this.msgBuffer.append("正在搜索设备...");
        this.handlerBlue.sendEmptyMessage(0);
        if (!this.mScanner.isScanning() && this.bleNfcDevice.isConnection() == 0)
            (new Thread(new Runnable() {
                public void run() {

                }
            })).start();
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
            JSONObject jSONObject1 = this.m_CheckJson;
            JSONObject jSONObject3 = jSONObject1.getJSONObject("repairType");
            if (jSONObject3.getInt("index") == 0) {
                this.m_textViewDeviceType.setText("托盘离线");
                this.m_iDeviceType = 0;
            } else if (jSONObject3.getInt("index") == 1) {
                this.m_textViewDeviceType.setText("报警器离线");
                this.m_iDeviceType = 1;
            } else if (jSONObject3.getInt("index") == 3) {
                this.m_textViewDeviceType.setText("托盘脱离告警");
                this.m_iDeviceType = 3;
            }
            this.m_deviceNumber = this.m_CheckJson.get("number").toString();
            this.m_textViewDeviceNumber.setText(this.m_deviceNumber);
            this.m_spinnerCheckType.setSelection(0, true);
            this.m_businessKey = this.m_CheckJson.get("repairSn").toString();
            StringBuilder stringBuilder2 = new StringBuilder();

            String str3 = stringBuilder2.append("询检单号：").append(jSONObject1.get("repairSn").toString()).toString();
            this.m_textViewOrderSn.setText(str3);
            StringBuilder stringBuilder1 = new StringBuilder();

            String str2 = stringBuilder1.append("下单时间：").append(jSONObject1.get("createTime").toString()).toString();
            m_textViewCreateTime.setText(str2);
            m_orderCreateTime = jSONObject1.get("createTime").toString();
            JSONObject jSONObject4 = jSONObject1.getJSONObject("customer");
            str2 = jSONObject1.get("recvName").toString();
            StringBuilder stringBuilder4 = new StringBuilder();

            String str4 = stringBuilder4.append("电话：").append(jSONObject1.get("recvPhone").toString()).toString();
            m_textViewUserId.setText(str2);
            m_textViewUserPhone.setText(str4);
            m_currentCustomerId = jSONObject4.get("userId").toString();
            m_customerPhone = str4;
            JSONObject jSONObject2 = jSONObject1.getJSONObject("recvAddr");
            StringBuilder stringBuilder3 = new StringBuilder();

            String str1 = stringBuilder3.append(jSONObject2.get("city").toString()).append(jSONObject2.get("county").toString()).append(jSONObject2.get("detail").toString()).toString();
            m_textViewAddress.setText(str1);
            m_recvAddr = str1;
            m_orderStatus = jSONObject1.getJSONObject("processStatus").getInt("index");
            if (m_orderStatus == 2) {
                m_buttonNext.setVisibility(View.INVISIBLE);
                JSONObject jSONObject = jSONObject1.getJSONObject("repairResultType");
                m_spinnerCheckType.setSelection(jSONObject.getInt("index"));
                m_spinnerCheckType.setEnabled(false);
                m_editTextPs.setText(jSONObject1.getString("resloveInfo"));
                m_editTextPs.setEnabled(false);
                return;
            }
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
            return;
        }
        m_buttonNext.setVisibility(View.VISIBLE);
    }

    private void showReadWriteDialog(String paramString, int paramInt) {
        Message message = new Message();
        message.what = 4;
        message.arg1 = paramInt;
        message.obj = paramString;
        handlerBlue.sendMessage(message);
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
            tv.setLayoutParams((ViewGroup.LayoutParams) layoutParams);
            toast.setView((View) linearLayout);
            linearLayout.addView((View) tv);
        }
        tv.setText(paramString);
        toast.show();
    }

    private boolean startAutoSearchCard() throws DeviceNoResponseException {
        for (byte b = 0; ; b++) {
            boolean bool = bleNfcDevice.startAutoSearchCard((byte) 20, (byte) 2);
            if (bool || b >= 10) {
                if (!bool) {
                    msgBuffer.append("不支持自动寻卡！\r\n");
                    handlerBlue.sendEmptyMessage(0);
                }
                return bool;
            }
        }
    }

    private void switchNavBar() {
        LatLng latLng1 = ((AppContext) getApplicationContext()).getLocation();
        LatLng latLng2 = m_recvLocation;
        AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(new Poi("当前位置", latLng1, ""), null, new Poi(m_recvAddr, latLng2, ""), AmapNaviType.DRIVER), this);
        AMapNavi.getInstance(this).setUseInnerVoice(true);
    }

    public void callPhone(String paramString) {
        Intent intent1;
        if (ActivityCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CALL_PHONE")) {
                Toast.makeText(this, "请授权！", Toast.LENGTH_LONG).show();
                intent1 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent1.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent1);
                return;
            }
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CALL_PHONE"}, 1);
            return;
        }
        Intent intent2 = new Intent("android.intent.action.CALL");
        intent2.setData(Uri.parse("tel:" + paramString));
        startActivity(intent2);
    }

    void init() {
        try {
            setContentView(R.layout.activity_check);
            appContext = (AppContext) getApplicationContext();
            m_user = appContext.getUser();
            if (m_user == null) {
                Toast.makeText(this, "登陆会话失效", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, AutoLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
            String str = getIntent().getExtras().getString("check");
            JSONObject jSONObject = new JSONObject(str);
            m_CheckJson = jSONObject;
            m_buttonNext = (Button) findViewById(R.id.button_next);
            m_textViewOrderSn = (TextView) findViewById(R.id.items_orderSn);
            m_textViewUserId = (TextView) findViewById(R.id.items_userId);
            m_textViewUserPhone = (TextView) findViewById(R.id.items_userPhone);
            m_textViewCreateTime = (TextView) findViewById(R.id.items_creatTime);
            m_textViewAddress = (TextView) findViewById(R.id.items_address);
            m_imageViewNav = (ImageView) findViewById(R.id.imageView_nav);
            m_imageViewCall = (ImageView) findViewById(R.id.imageView_call);
            m_imageViewPic = (ImageView) findViewById(R.id.imageView_pic);
            m_textViewAddress = (TextView) findViewById(R.id.items_address);
            m_textViewDeviceType = (TextView) findViewById(R.id.textview_deviceType);
            m_textViewDeviceNumber = (TextView) findViewById(R.id.tv_deviceNumber);
            m_imageViewDeviceStatus = (ImageView) findViewById(R.id.imageView_deviceStatus);
            m_spinnerCheckType = (Spinner) findViewById(R.id.spinner_checkType);
            m_editTextPs = (EditText) findViewById(R.id.input_note);
            m_imageDeviceReflesh = (ImageView) findViewById(R.id.imageView_deviceReflesh);
            m_buttonNext.setOnClickListener(this);
            m_imageViewNav.setOnClickListener(this);
            m_imageViewCall.setOnClickListener(this);
            m_imageDeviceReflesh.setOnClickListener(this);
            m_imageViewPic.setOnClickListener(this);
            setOrderHeadInfo();
            blueDeviceInitial();
            m_orderServiceQualityShowFlag = false;
            GetUserCard();
            GetDeviceSatus();
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.button_next:
                if (m_orderStatus == 0)
                    getOrderOps();
                break;
            case R.id.imageView_nav:
                switchNavBar();
                break;
            case R.id.imageView_call:
                callPhone(m_customerPhone);
                break;
            case R.id.imageView_deviceReflesh:
                GetDeviceSatus();
                break;
            case R.id.imageView_pic:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("fileFolder", "repair");
                bundle.putString("fileNameHeader", m_businessKey);
                bundle.putInt("MaxPicCount", 4);
                intent.setClass(this, PicSelActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }

    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
    }

    public void onDestroy() {
        super.onDestroy();
        if (readWriteDialog != null)
            readWriteDialog.dismiss();
        unbindService(mServiceConnection);
    }

    protected void onResume() {
        super.onResume();
        if (mBleNfcDeviceService != null) {
            mBleNfcDeviceService.setScannerCallback(scannerCallback);
            mBleNfcDeviceService.setDeviceManagerCallback(deviceManagerCallback);
        }
    }

    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onArriveDestination(boolean b) {

    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onStopSpeaking() {

    }

    @Override
    public void onReCalculateRoute(int i) {

    }

    @Override
    public void onExitPage(int i) {

    }

    @Override
    public void onStrategyChanged(int i) {

    }

    @Override
    public View getCustomNaviBottomView() {
        return null;
    }

    @Override
    public View getCustomNaviView() {
        return null;
    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onMapTypeChanged(int i) {

    }

    @Override
    public View getCustomMiddleView() {
        return null;
    }

    @Override
    public void onNaviDirectionChanged(int i) {

    }

    @Override
    public void onDayAndNightModeChanged(int i) {

    }

    @Override
    public void onBroadcastModeChanged(int i) {

    }

    @Override
    public void onScaleAutoChanged(boolean b) {

    }

    private class StartSearchButtonListener implements View.OnClickListener {
        private StartSearchButtonListener() {
        }

        public void onClick(View param1View) {
            if (bleNfcDevice.isConnection() == 2) {
                bleNfcDevice.requestDisConnectDevice();
                return;
            }
            searchNearestBleDevice();
        }
    }
}