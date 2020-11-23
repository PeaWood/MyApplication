package com.gc.nfc.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
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
import com.gc.nfc.domain.User;
import com.gc.nfc.http.Logger;
import com.gc.nfc.interfaces.Netcallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SecurityDetailActivity extends BaseActivity implements View.OnClickListener, INaviInfoCallback {
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
                        try {
                            bleNfcDevice.openBeep(100, 100, 2);
                        } catch (DeviceNoResponseException e) {
                            e.printStackTrace();
                        }
                    }
                })).start();
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler handlerBlue = new Handler() {
        public void handleMessage(Message param1Message) {
            msgText.setText(msgBuffer);
            if (bleNfcDevice.isConnection() == 2 || bleNfcDevice.isConnection() == 1) {
                switch (param1Message.what) {
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
                        String[] arrayOfString = param1Message.obj!=null?param1Message.obj.toString().split(":"):null;
                        if(arrayOfString==null){
                            showToast("用户卡无效！");
                            return;
                        }
                        if (arrayOfString.length != 2) {
                            showToast("无效卡格式！");
                            return;
                        }
                        String str1 = arrayOfString[0];
                        String str2 = arrayOfString[1];
                        if (m_handedUserCard == null){
                            showToast("该用户未绑定用户卡");
                            return;
                        }
                        if (!str2.equals(m_handedUserCard)){
                            showToast("非本人卡号！");
                            return;
                        }
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

    private EditText m_editTextPs;

    private String m_handedUserCard = null;

    private ImageView m_imageViewCall;

    private ImageView m_imageViewNav;

    private ImageView m_imageViewPic;

    private ImageView m_imageViewSearchBlue = null;

    private boolean m_orderServiceQualityShowFlag;

    private int m_orderStatus;

    private String m_recvAddr;

    private TextView m_textViewAddress;

    private TextView m_textViewCheckType;

    private TextView m_textViewCreateTime;

    private TextView m_textViewOrderSn;

    private TextView m_textViewUserId;

    private TextView m_textViewUserPhone;

    private TextView m_textviewReserveTime;

    private TextView m_textview_checkDetail;

    private User m_user;

    private StringBuffer msgBuffer;

    private TextView msgText = null;

    private ProgressDialog readWriteDialog = null;
    private ImageView imageView;

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
                handlerBlue.sendEmptyMessage(0);
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

    private Toast toast = null;
    TextView tv;

    private void GetUserCard() {
        if (m_currentCustomerId == null) {
            showToast("客户资料被删除！");
            return;
        }
        NetRequestConstant netRequestConstant = new NetRequestConstant();
        netRequestConstant.setType(HttpRequestType.GET);
        netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/UserCard";
        netRequestConstant.context = this;
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        hashMap.put("userId", m_currentCustomerId);
        hashMap.put("status", Integer.valueOf(1));
        netRequestConstant.setParams(hashMap);
        getServer(new Netcallback() {
            public void preccess(Object res, boolean param1Boolean) {
                if (param1Boolean) {
                    HttpResponse response = (HttpResponse) res;
                    if (response != null) {
                        if (response.getStatusLine().getStatusCode() == 200) {
                            try {
                                JSONObject jSONObject = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                                JSONArray items = jSONObject.getJSONArray("items");
                                if (items.length() == 1) {
                                    m_handedUserCard = items.getJSONObject(0).getString("number");
                                    return;
                                }
                                m_handedUserCard = null;
                                showToast("该用户未绑定用户卡");
                            } catch (JSONException jSONException) {
                                Toast.makeText(SecurityDetailActivity.this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
                            } catch (IOException iOException) {
                                Toast.makeText(SecurityDetailActivity.this, "异常" + iOException.toString(), Toast.LENGTH_LONG).show();
                            }
                            return;
                        }
                        Toast.makeText(SecurityDetailActivity.this, "用户卡查询失败", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(SecurityDetailActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(SecurityDetailActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
            }
        }, netRequestConstant);
    }

    private void blueDeviceInitial() {
        msgText = (TextView) findViewById(R.id.msgText);
        m_imageViewSearchBlue = (ImageView) findViewById(R.id.imageView_search);
        m_imageViewSearchBlue.setOnClickListener(new StartSearchButtonListener());
        msgBuffer = new StringBuffer();
        //ble_nfc服务初始化
        Intent gattServiceIntent = new Intent(this, BleNfcDeviceService.class);
        bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
    }

    private void getOrderOps() {
        if (m_user == null) {
            Toast.makeText(this, "未登录！", Toast.LENGTH_LONG).show();
            return;
        }
        if (m_user.getScanType() == 0 || m_user.getScanType() == 1) {
            orderServiceQualityShow();
            return;
        }
        orderServiceQualityUpload(true);
    }

    private void orderServiceQualityShow() {
        GetUserCard();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.user_evaluate, null);
        builder.setIcon(R.mipmap.icon_ticket_user);
        builder.setTitle("用户卡评价(" + m_handedUserCard + ")");
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
        m_orderServiceQualityShowFlag = true;
    }

    private void orderServiceQualityUpload(boolean paramBoolean) {
        NetRequestConstant netRequestConstant = new NetRequestConstant();
        netRequestConstant.setType(HttpRequestType.PUT);
        netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/Security/" + m_businessKey;
        netRequestConstant.context = this;
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        if (paramBoolean) {
            hashMap.put("processStatus", "PTSolved");
            hashMap.put("resloveInfo", m_editTextPs.getText().toString());
            netRequestConstant.setBody(hashMap);
            getServer(new Netcallback() {
                public void preccess(Object param1Object, boolean param1Boolean) {
                    if (param1Boolean) {
                        HttpResponse response = (HttpResponse) param1Object;
                        if (response != null) {
                            if (response.getStatusLine().getStatusCode() == 200) {
                                Toast toast = Toast.makeText(SecurityDetailActivity.this, "处理成功！", Toast.LENGTH_LONG);
                                toast.setGravity(17, 0, 0);
                                toast.show();
                                MediaPlayer.create(SecurityDetailActivity.this, R.raw.finish_order).start();
                                Intent intent = new Intent(getApplicationContext(), MainlyActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putInt("switchTab", 2);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finish();
                                return;
                            }
                            if (response.getStatusLine().getStatusCode() == 404) {
                                Toast.makeText(SecurityDetailActivity.this, "安检处理失败", Toast.LENGTH_LONG).show();
                                return;
                            }
                            if (response.getStatusLine().getStatusCode() == 401) {
                                Toast.makeText(SecurityDetailActivity.this, "鉴权失败，请重新登录" + response.getStatusLine().getStatusCode(), Toast.LENGTH_LONG).show();
                                return;
                            }
                            Toast.makeText(SecurityDetailActivity.this, "安检处理失败" + response.getStatusLine().getStatusCode(), Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(SecurityDetailActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(SecurityDetailActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
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
                        this.handlerBlue.sendMessage(message);
                    }
                }
                return bool;
        }
    }

    private void searchNearestBleDevice() {
        msgBuffer.delete(0, this.msgBuffer.length());
        msgBuffer.append("正在搜索设备...");
        handlerBlue.sendEmptyMessage(0);
        if (!mScanner.isScanning() && (bleNfcDevice.isConnection() == BleManager.STATE_DISCONNECTED)){
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
                                    handlerBlue.sendEmptyMessage(0);
                                    bleNfcDevice.requestConnectBleDevice(mNearestBle.getAddress());
                                } else {
                                    msgBuffer.delete(0, msgBuffer.length());
                                    msgBuffer.append("未找到设备！");
                                    handlerBlue.sendEmptyMessage(0);
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
            JSONObject jSONObject1 = m_CheckJson;
            m_businessKey = m_CheckJson.get("securitySn").toString();
            StringBuilder stringBuilder3 = new StringBuilder();
            String str3 = stringBuilder3.append("安检单号：").append(jSONObject1.get("securitySn").toString()).toString();
            m_textViewOrderSn.setText(str3);
            StringBuilder stringBuilder2 = new StringBuilder();
            String str2 = stringBuilder2.append("下单时间：").append(jSONObject1.get("createTime").toString()).toString();
            m_textViewCreateTime.setText(str2);
            m_textViewCheckType.setText(jSONObject1.getJSONObject("securityType").getString("name"));
            m_textview_checkDetail.setText(jSONObject1.getString("detail"));
            m_textviewReserveTime.setText(jSONObject1.get("updateTime").toString());
            JSONObject jSONObject2 = jSONObject1.getJSONObject("customer");
            str2 = jSONObject1.get("recvName").toString();
            StringBuilder stringBuilder4 = new StringBuilder();
            String str4 = stringBuilder4.append("电话：").append(jSONObject1.get("recvPhone").toString()).toString();
            m_textViewUserId.setText(str2);
            m_textViewUserPhone.setText(str4);
            m_currentCustomerId = jSONObject2.get("userId").toString();
            m_customerPhone = str4;
            jSONObject2 = jSONObject1.getJSONObject("recvAddr");
            StringBuilder stringBuilder1 = new StringBuilder();
            String str1 = stringBuilder1.append(jSONObject2.get("city").toString()).append(jSONObject2.get("county").toString()).append(jSONObject2.get("detail").toString()).toString();
            m_textViewAddress.setText(str1);
            m_recvAddr = str1;
            m_orderStatus = jSONObject1.getJSONObject("processStatus").getInt("index");
            if (m_orderStatus == 2) {
                m_buttonNext.setVisibility(View.INVISIBLE);
                m_editTextPs.setText(jSONObject1.getString("resloveInfo"));
                m_editTextPs.setEnabled(false);
                return;
            }
            m_buttonNext.setVisibility(View.VISIBLE);
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
        }
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
            WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(displayMetrics);
            tv = new TextView(this);
            linearLayout.getBackground().setAlpha(0);
            tv.setTextSize(40.0F);
            tv.setTextColor(getResources().getColor(R.color.bar_grey));
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
        AppContext appContext = (AppContext) getApplicationContext();
        LatLng latLng2 = appContext.getLocation();
        LatLng latLng1 = appContext.getLocation();
        AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(new Poi("当前位置", latLng2, ""), null, new Poi(m_recvAddr, latLng1, ""), AmapNaviType.DRIVER), this);
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
            setContentView(R.layout.activity_security);
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
            Logger.e("jSONObject = " + str);
            m_CheckJson = jSONObject;
            m_buttonNext = (Button) findViewById(R.id.button_next);
            m_textViewOrderSn = (TextView) findViewById(R.id.items_orderSn);
            m_textViewUserId = (TextView) findViewById(R.id.items_userId);
            m_textViewUserPhone = (TextView) findViewById(R.id.items_userPhone);
            m_textViewCreateTime = (TextView) findViewById(R.id.items_creatTime);
            m_textviewReserveTime = (TextView) findViewById(R.id.textview_reserveTime);
            m_textViewAddress = (TextView) findViewById(R.id.items_address);
            m_imageViewNav = (ImageView) findViewById(R.id.imageView_nav);
            m_imageViewCall = (ImageView) findViewById(R.id.imageView_call);
            m_imageViewPic = (ImageView) findViewById(R.id.imageView_pic);
            m_editTextPs = (EditText) findViewById(R.id.input_note);
            m_textViewCheckType = (TextView) findViewById(R.id.textview_checkType);
            m_textview_checkDetail = (TextView) findViewById(R.id.textview_checkDetail);
            imageView = (ImageView) findViewById(R.id.img_back);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            m_buttonNext.setOnClickListener(this);
            m_imageViewNav.setOnClickListener(this);
            m_imageViewCall.setOnClickListener(this);
            m_imageViewPic.setOnClickListener(this);
            setOrderHeadInfo();
            blueDeviceInitial();
            m_orderServiceQualityShowFlag = false;
            GetUserCard();
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void onArriveDestination(boolean paramBoolean) {
    }

    public void onCalculateRouteFailure(int paramInt) {
    }

    public void onCalculateRouteSuccess(int[] paramArrayOfint) {
    }

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.button_next:
                if (m_editTextPs.getText().toString().length() > 0 && m_orderStatus == 1) {
                    getOrderOps();
                } else {
                    Toast.makeText(this, "请填写处理结果！", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.imageView_nav:
                switchNavBar();
                break;
            case R.id.imageView_call:
                callPhone(m_customerPhone);
                break;
            case R.id.imageView_pic:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("fileFolder", "security");
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

    public void onExitPage(int paramInt) {
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

    public void onGetNavigationText(String paramString) {
    }

    public void onInitNaviFailure() {
    }

    public void onLocationChange(AMapNaviLocation paramAMapNaviLocation) {
    }

    public void onReCalculateRoute(int paramInt) {
    }

    protected void onResume() {
        super.onResume();
        if (mBleNfcDeviceService != null) {
            mBleNfcDeviceService.setScannerCallback(scannerCallback);
            mBleNfcDeviceService.setDeviceManagerCallback(deviceManagerCallback);
        }
    }

    public void onStartNavi(int paramInt) {
    }

    public void onStopSpeaking() {
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