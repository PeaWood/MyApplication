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
import com.gc.nfc.common.NetUrlConstant;
import com.gc.nfc.domain.User;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.gc.nfc.http.Logger;
import com.gc.nfc.interfaces.Netcallback;

public class MendDetailActivity extends BaseActivity implements View.OnClickListener, INaviInfoCallback{
    private AppContext appContext;
  
    private BleNfcDevice bleNfcDevice;

    //设备操作类回调
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
    private Handler handler = new Handler() {
        public void handleMessage(Message param1Message) {
            msgText.setText(msgBuffer);
            if (bleNfcDevice.isConnection() == 2 || bleNfcDevice.isConnection() == 1) {
                switch (param1Message.what) {
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
                    case 137:
                        String[] arrayOfString = param1Message.obj!=null?param1Message.obj.toString().split(":"):null;
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
  
  private Toast toast = null;
  
  TextView tv;
  
  private void GetUserCard() {
    if (this.m_currentCustomerId == null) {
      showToast("客户资料被删除！");
      return;
    }
    NetRequestConstant netRequestConstant = new NetRequestConstant();
    netRequestConstant.setType(HttpRequestType.GET);
    netRequestConstant.requestUrl = NetUrlConstant.BASEURL+"/api/UserCard";
    netRequestConstant.context = (Context)this;
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
                      m_handedUserCard = (String)null;
                    MendDetailActivity.this.showToast("该用户未绑定用户卡");
                  } catch (JSONException jSONException) {
                    Toast.makeText((Context)MendDetailActivity.this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
                  } catch (IOException iOException) {
                    Toast.makeText((Context)MendDetailActivity.this, "异常" + iOException.toString(), Toast.LENGTH_LONG).show();
                  }
                    return;
                }
                Toast.makeText((Context)MendDetailActivity.this, "用户卡查询失败", Toast.LENGTH_LONG).show();
                return;
              }
              Toast.makeText((Context)MendDetailActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
              return;
            }
            Toast.makeText((Context)MendDetailActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
          }
        },netRequestConstant);
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
  
  private void getOrderOps() {
    if (this.m_user == null) {
      Toast.makeText((Context)this, "未登录！", Toast.LENGTH_LONG).show();
      return;
    } 
    if (this.m_user.getScanType() == 0 || this.m_user.getScanType() == 1) {
      orderServiceQualityShow();
      return;
    } 
    orderServiceQualityUpload(true);
  }
  
  private String getResponseMessage(HttpResponse paramHttpResponse) {
    return null;
  }
  
  private void orderServiceQualityShow() {
    GetUserCard();
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
    View view = View.inflate((Context)this, R.layout.user_evaluate, null);
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
    netRequestConstant.requestUrl = NetUrlConstant.BASEURL+"/api/Mend/" + this.m_businessKey;
    netRequestConstant.context = (Context)this;
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    if (paramBoolean) {
      hashMap.put("processStatus", "PTSolved");
      hashMap.put("resloveInfo", this.m_editTextPs.getText().toString());
      netRequestConstant.setBody(hashMap);
      getServer(new Netcallback() {
            public void preccess(Object param1Object, boolean param1Boolean) {
              if (param1Boolean) {
                  HttpResponse response = (HttpResponse) param1Object;
                if (param1Object != null) {
                  if (response.getStatusLine().getStatusCode() == 200) {
                      Toast toast = Toast.makeText((Context)MendDetailActivity.this, "处理成功！", Toast.LENGTH_LONG);
                      toast.setGravity(17, 0, 0);
                      toast.show();
                      MediaPlayer.create(MendDetailActivity.this, R.raw.finish_order).start();
                      Intent intent = new Intent(MendDetailActivity.this.getApplicationContext(), MainlyActivity.class);
                      Bundle bundle = new Bundle();
                      bundle.putInt("switchTab", 2);
                      intent.putExtras(bundle);
                      MendDetailActivity.this.startActivity(intent);
                      MendDetailActivity.this.finish();
                     return;
                  }
                  if (response.getStatusLine().getStatusCode() == 404) {
                    Toast.makeText((Context)MendDetailActivity.this, "报修处理失败", Toast.LENGTH_LONG).show();
                    return;
                  }
                  if (response.getStatusLine().getStatusCode() == 401) {
                    Toast.makeText((Context)MendDetailActivity.this, "鉴权失败，请重新登录" + response.getStatusLine().getStatusCode(), Toast.LENGTH_LONG).show();
                    return;
                  }
                  Toast.makeText((Context)MendDetailActivity.this, "报修处理失败" + response.getStatusLine().getStatusCode(), Toast.LENGTH_LONG).show();
                  return;
                }
                Toast.makeText((Context)MendDetailActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                return;
              }
              Toast.makeText((Context)MendDetailActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
            }
          },netRequestConstant);
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
                        this.handler.sendMessage(message);
                    }
                }
                return bool;
        }
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
          View view = listAdapter.getView(b, null, (ViewGroup)paramListView);
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
        Logger.e("jSONObject报修详情 = "+jSONObject1);
      this.m_businessKey = this.m_CheckJson.get("mendSn").toString();
      StringBuilder stringBuilder2 = new StringBuilder();
      String str3 = stringBuilder2.append("报修单号：").append(jSONObject1.get("mendSn").toString()).toString();
      this.m_textViewOrderSn.setText(str3);
      StringBuilder stringBuilder1 = new StringBuilder();
      String str2 = stringBuilder1.append("下单时间：").append(jSONObject1.get("createTime").toString()).toString();
      this.m_textViewCreateTime.setText(str2);
      this.m_textViewCheckType.setText(jSONObject1.getJSONObject("mendType").getString("name"));
      this.m_textview_checkDetail.setText(jSONObject1.getString("detail"));
      this.m_textviewReserveTime.setText(jSONObject1.get("updateTime").toString());
      JSONObject jSONObject2 = jSONObject1.getJSONObject("customer");
      String str4 = jSONObject1.get("recvName").toString();
      StringBuilder stringBuilder4 = new StringBuilder();
      String str5 = stringBuilder4.append("电话：").append(jSONObject1.get("recvPhone").toString()).toString();
      this.m_textViewUserId.setText(str4);
      this.m_textViewUserPhone.setText(str5);
      this.m_currentCustomerId = jSONObject2.get("userId").toString();
      this.m_customerPhone = str5;
      jSONObject2 = jSONObject1.getJSONObject("recvAddr");
      StringBuilder stringBuilder3 = new StringBuilder();
      String str1 = stringBuilder3.append(jSONObject2.get("city").toString()).append(jSONObject2.get("county").toString()).append(jSONObject2.get("detail").toString()).toString();
      this.m_textViewAddress.setText(str1);
      this.m_recvAddr = str1;
      this.m_orderStatus = jSONObject1.getJSONObject("processStatus").getInt("index");
      if (this.m_orderStatus == 2) {
        this.m_buttonNext.setVisibility(View.INVISIBLE);
        this.m_editTextPs.setText(jSONObject1.getString("resloveInfo"));
        this.m_editTextPs.setEnabled(false);
        return;
      } 
      this.m_buttonNext.setVisibility(View.VISIBLE);
    } catch (JSONException jSONException) {
      Toast.makeText((Context)this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
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
    if (this.toast == null) {
      this.toast = Toast.makeText((Context)this, null, Toast.LENGTH_SHORT);
      this.toast.setGravity(17, 0, 0);
      LinearLayout linearLayout = (LinearLayout)this.toast.getView();
      WindowManager windowManager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
      DisplayMetrics displayMetrics = new DisplayMetrics();
      windowManager.getDefaultDisplay().getMetrics(displayMetrics);
      this.tv = new TextView((Context)this);
      linearLayout.getBackground().setAlpha(0);
      this.tv.setTextSize(40.0F);
      this.tv.setTextColor(getResources().getColor(R.color.orange));
      linearLayout.setGravity(17);
      LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
      layoutParams.setMargins(0, 0, 0, 180);
      this.tv.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      this.toast.setView((View)linearLayout);
      linearLayout.addView((View)this.tv);
    } 
    this.tv.setText(paramString);
    this.toast.show();
  }
  
  private boolean startAutoSearchCard() throws DeviceNoResponseException {
    for (byte b = 0;; b++) {
      boolean bool = this.bleNfcDevice.startAutoSearchCard((byte)20, (byte)2);
      if (bool || b >= 10) {
        if (!bool) {
          this.msgBuffer.append("不支持自动寻卡！\r\n");
          this.handler.sendEmptyMessage(0);
        } 
        return bool;
      } 
    } 
  }
  
  private void switchNavBar() {
    AppContext appContext = (AppContext)getApplicationContext();
    LatLng latLng2 = appContext.getLocation();
    LatLng latLng1 = appContext.getLocation();
    AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(new Poi("当前位置", latLng2, ""), null, new Poi(this.m_recvAddr, latLng1, ""), AmapNaviType.DRIVER), this);
    AMapNavi.getInstance((Context)this).setUseInnerVoice(true);
  }
  
  public void callPhone(String paramString) {
    Intent intent1;
    if (ActivityCompat.checkSelfPermission((Context)this, "android.permission.CALL_PHONE") != 0) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CALL_PHONE")) {
        Toast.makeText((Context)this, "请授权！", Toast.LENGTH_LONG).show();
        intent1 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent1.setData(Uri.fromParts("package", getPackageName(), null));
        startActivity(intent1);
        return;
      } 
      ActivityCompat.requestPermissions(this, new String[] { "android.permission.CALL_PHONE" }, 1);
      return;
    } 
    Intent intent2 = new Intent("android.intent.action.CALL");
    intent2.setData(Uri.parse("tel:" + paramString));
    startActivity(intent2);
  }
  
  void init() {
    try {
      setContentView(R.layout.activity_mend);
      this.appContext = (AppContext)getApplicationContext();
      this.m_user = this.appContext.getUser();
      if (this.m_user == null) {
        Toast.makeText((Context)this, "登陆会话失效", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, AutoLoginActivity.class);
        startActivity(intent);
        finish();
        return;
      } 
      String str = getIntent().getExtras().getString("check");
      JSONObject jSONObject = new JSONObject(str);
      this.m_CheckJson = jSONObject;
      this.m_buttonNext = (Button)findViewById(R.id.button_next);
      this.m_textViewOrderSn = (TextView)findViewById(R.id.items_orderSn);
      this.m_textViewUserId = (TextView)findViewById(R.id.items_userId);
      this.m_textViewUserPhone = (TextView)findViewById(R.id.items_userPhone);
      this.m_textViewCreateTime = (TextView)findViewById(R.id.items_creatTime);
      this.m_textviewReserveTime = (TextView)findViewById(R.id.textview_reserveTime);
      this.m_textViewAddress = (TextView)findViewById(R.id.items_address);
      this.m_imageViewNav = (ImageView)findViewById(R.id.imageView_nav);
      this.m_imageViewCall = (ImageView)findViewById(R.id.imageView_call);
      this.m_imageViewPic = (ImageView)findViewById(R.id.imageView_pic);
      this.m_editTextPs = (EditText)findViewById(R.id.input_note);
      this.m_textViewCheckType = (TextView)findViewById(R.id.textview_checkType);
      this.m_textview_checkDetail = (TextView)findViewById(R.id.textview_checkDetail);
        imageView = (ImageView) findViewById(R.id.img_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
      this.m_buttonNext.setOnClickListener(this);
      this.m_imageViewNav.setOnClickListener(this);
      this.m_imageViewCall.setOnClickListener(this);
      this.m_imageViewPic.setOnClickListener(this);
      setOrderHeadInfo();
      blueDeviceInitial();
      this.m_orderServiceQualityShowFlag = false;
      GetUserCard();
    } catch (Exception jSONException) {
      Toast.makeText((Context)this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
    } 
  }
  
  public void onArriveDestination(boolean paramBoolean) {}
  
  public void onCalculateRouteFailure(int paramInt) {}
  
  public void onCalculateRouteSuccess(int[] paramArrayOfint) {}

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.button_next:
                if (m_editTextPs.getText().toString().length() > 0 && m_orderStatus == 1){
                    getOrderOps();
                }else{
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
                bundle.putString("fileFolder", "mend");
                bundle.putString("fileNameHeader", m_businessKey);
                bundle.putInt("MaxPicCount", 4);
                intent.setClass((Context) this, PicSelActivity.class);
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
    if (this.readWriteDialog != null)
      this.readWriteDialog.dismiss(); 
    unbindService(this.mServiceConnection);
  }
  
  public void onExitPage(int paramInt) {}

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

    public void onGetNavigationText(String paramString) {}

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    public void onInitNaviFailure() {}
  
//  public void onLocationChange(AMapNaviLocation paramAMapNaviLocation) {}
  
  public void onReCalculateRoute(int paramInt) {}
  
  protected void onResume() {
    super.onResume();
    if (this.mBleNfcDeviceService != null) {
      this.mBleNfcDeviceService.setScannerCallback(this.scannerCallback);
      this.mBleNfcDeviceService.setDeviceManagerCallback(this.deviceManagerCallback);
    } 
  }
  
  public void onStartNavi(int paramInt) {}
  
  public void onStopSpeaking() {}
  
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