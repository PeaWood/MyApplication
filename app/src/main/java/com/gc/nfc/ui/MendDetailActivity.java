package com.gc.nfc.ui;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
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
import com.gc.nfc.domain.User;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//import com.amap.api.maps.model.LatLng;
//import com.amap.api.maps.model.Poi;
//import com.amap.api.navi.AMapNavi;
//import com.amap.api.navi.AmapNaviPage;
//import com.amap.api.navi.AmapNaviParams;
//import com.amap.api.navi.AmapNaviType;
//import com.amap.api.navi.INaviInfoCallback;
//import com.amap.api.navi.model.AMapNaviLocation;
//INaviInfoCallback
public class MendDetailActivity extends BaseActivity implements View.OnClickListener{
    private AppContext appContext;
  
    private BleNfcDevice bleNfcDevice;

    //设备操作类回调
    private DeviceManagerCallback deviceManagerCallback = new DeviceManagerCallback() {
        @Override
        public void onReceiveConnectBtDevice(boolean blnIsConnectSuc) {
            super.onReceiveConnectBtDevice(blnIsConnectSuc);
            if (blnIsConnectSuc) {
                System.out.println("Activity设备连接成功");
                msgBuffer.delete(0, msgBuffer.length());
                msgBuffer.append("设备连接成功!");
                if (mNearestBle != null) {
                    //msgBuffer.append("设备名称：").append(bleNfcDevice.getDeviceName()).append("\r\n");
                }
                //msgBuffer.append("信号强度：").append(lastRssi).append("dB\r\n");
                //msgBuffer.append("SDK版本：" + BleNfcDevice.SDK_VERSIONS + "\r\n");

                //连接上后延时500ms后再开始发指令
                try {
                    Thread.sleep(500L);
                    handler.sendEmptyMessage(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onReceiveDisConnectDevice(boolean blnIsDisConnectDevice) {
            super.onReceiveDisConnectDevice(blnIsDisConnectDevice);
            System.out.println("Activity设备断开链接");
            msgBuffer.delete(0, msgBuffer.length());
            msgBuffer.append("设备断开链接!");
            handler.sendEmptyMessage(0);
        }

        @Override
        public void onReceiveConnectionStatus(boolean blnIsConnection) {
            super.onReceiveConnectionStatus(blnIsConnection);
            System.out.println("Activity设备链接状态回调");
        }

        @Override
        public void onReceiveInitCiphy(boolean blnIsInitSuc) {
            super.onReceiveInitCiphy(blnIsInitSuc);
        }

        @Override
        public void onReceiveDeviceAuth(byte[] authData) {
            super.onReceiveDeviceAuth(authData);
        }

        @Override
        //寻到卡片回调
        public void onReceiveRfnSearchCard(boolean blnIsSus, int cardType, byte[] bytCardSn, byte[] bytCarATS) {
            super.onReceiveRfnSearchCard(blnIsSus, cardType, bytCardSn, bytCarATS);
            if (!blnIsSus || cardType == BleNfcDevice.CARD_TYPE_NO_DEFINE) {
                return;
            }
            System.out.println("Activity接收到激活卡片回调：UID->" + StringTool.byteHexToSting(bytCardSn) + " ATS->" + StringTool.byteHexToSting(bytCarATS));

            final int cardTypeTemp = cardType;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    boolean isReadWriteCardSuc;
                    try {
                        if (bleNfcDevice.isAutoSearchCard()) {
                            //如果是自动寻卡的，寻到卡后，先关闭自动寻卡
                            bleNfcDevice.stoptAutoSearchCard();
                            isReadWriteCardSuc = readWriteCardDemo(cardTypeTemp);
//                            isReadWriteCardSuc=true;
                            //读卡结束，重新打开自动寻卡
                            startAutoSearchCard();
                        }
                        else {
                            isReadWriteCardSuc = readWriteCardDemo(cardTypeTemp);
//                            isReadWriteCardSuc=true;
                            //如果不是自动寻卡，读卡结束,关闭天线
                            bleNfcDevice.closeRf();
                        }

                        //打开蜂鸣器提示读卡完成
                        if (isReadWriteCardSuc) {
                            bleNfcDevice.openBeep(50, 50, 3);  //读写卡成功快响3声
                        }
                        else {
                            bleNfcDevice.openBeep(100, 100, 2); //读写卡失败慢响2声
                        }
                    } catch (DeviceNoResponseException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        public void onReceiveRfmSentApduCmd(byte[] bytApduRtnData) {
            super.onReceiveRfmSentApduCmd(bytApduRtnData);
            System.out.println("Activity接收到APDU回调：" + StringTool.byteHexToSting(bytApduRtnData));
        }

        @Override
        public void onReceiveRfmClose(boolean blnIsCloseSuc) {
            super.onReceiveRfmClose(blnIsCloseSuc);
        }
        @Override
        //按键返回回调
        public void onReceiveButtonEnter(byte keyValue) {}
    };
  
    private Handler handler = new Handler() {
      public void handleMessage(Message param1Message) {
        msgText.setText(msgBuffer);
        //if (MendDetailActivity.this.bleNfcDevice.isConnection() == 2 || MendDetailActivity.this.bleNfcDevice.isConnection() == 1);
        switch (param1Message.what) {
          default:
            return;
          case 3:
            (new Thread(new Runnable() {
                  public void run() {
                    try {
                      MendDetailActivity.this.bleNfcDevice.getDeviceVersions();
                      MendDetailActivity.this.handler.sendEmptyMessage(0);
                      if (MendDetailActivity.this.bleNfcDevice.getDeviceBatteryVoltage() < 3.61D) {
                        MendDetailActivity.this.msgBuffer.append("(电量低)");
                      } else {
                        MendDetailActivity.this.msgBuffer.append("(电量充足)");
                      } 
                      MendDetailActivity.this.handler.sendEmptyMessage(0);
                      if (MendDetailActivity.this.bleNfcDevice.androidFastParams(true));
                      MendDetailActivity.this.handler.sendEmptyMessage(0);
                      MendDetailActivity.this.handler.sendEmptyMessage(0);
                      MendDetailActivity.this.startAutoSearchCard();
                    } catch (DeviceNoResponseException deviceNoResponseException) {
                      deviceNoResponseException.printStackTrace();
                    } 
                  }
                })).start();
          case 137:
            break;
        }
        try {
            String[] arrayOfString = param1Message.obj.toString().split(":");
            if (arrayOfString.length != 2)
                MendDetailActivity.this.showToast("无效卡格式！");
            String str1 = arrayOfString[0];
            String str2 = arrayOfString[1];
            if (MendDetailActivity.this.m_handedUserCard == null)
                MendDetailActivity.this.showToast("该用户未绑定用户卡");
            if (!str2.equals(MendDetailActivity.this.m_handedUserCard))
                MendDetailActivity.this.showToast("非本人卡号！");
            if (str1.equals("Y"))
                MendDetailActivity.this.orderServiceQualityUpload(true);
            if (str1.equals("N"))
                MendDetailActivity.this.orderServiceQualityUpload(false);
            MendDetailActivity.this.showToast("无效卡格式！");
        }catch (Exception e){
            e.printStackTrace();
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
//    if (this.m_currentCustomerId == null) {
//      showToast("客户资料被删除！");
//      return;
//    }
//    NetRequestConstant netRequestConstant = new NetRequestConstant();
//    netRequestConstant.setType(HttpRequestType.GET);
//    netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/UserCard";
//    netRequestConstant.context = (Context)this;
//    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
//    hashMap.put("userId", this.m_currentCustomerId);
//    hashMap.put("status", Integer.valueOf(1));
//    netRequestConstant.setParams(hashMap);
//    getServer(new Netcallback() {
//          public void preccess(Object param1Object, boolean param1Boolean) {
//            if (param1Boolean) {
//              param1Object = param1Object;
//              if (param1Object != null) {
//                if (param1Object.getStatusLine().getStatusCode() == 200) {
//                  try {
//                    JSONObject jSONObject = new JSONObject();
//                    this(EntityUtils.toString(param1Object.getEntity(), "UTF-8"));
//                    param1Object = jSONObject.getJSONArray("items");
//                    if (param1Object.length() == 1) {
//                      MendDetailActivity.access$1502(MendDetailActivity.this, param1Object.getJSONObject(0).getString("number"));
//                      return;
//                    }
//                    MendDetailActivity.access$1502(MendDetailActivity.this, (String)null);
//                    MendDetailActivity.this.showToast("该用户未绑定用户卡");
//                  } catch (JSONException jSONException) {
//                    Toast.makeText((Context)MendDetailActivity.this, "异常" + jSONException.toString(), 1).show();
//                  } catch (IOException iOException) {
//                    Toast.makeText((Context)MendDetailActivity.this, "异常" + iOException.toString(), 1).show();
//                  }
//                  return;
//                }
//                Toast.makeText((Context)MendDetailActivity.this, "用户卡查询失败", 1).show();
//                return;
//              }
//              Toast.makeText((Context)MendDetailActivity.this, "请求超时，请检查网络", 1).show();
//              return;
//            }
//            Toast.makeText((Context)MendDetailActivity.this, "网络未连接！", 1).show();
//          }
//        }netRequestConstant);
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
//    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
//    View view = View.inflate((Context)this, 2131361920, null);
//    builder.setIcon(2131165405);
//    builder.setTitle("用户卡评价(" + this.m_handedUserCard + ")");
//    AlertDialog alertDialog = builder.create();
//    alertDialog.setView(view);
//    alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//          public void onDismiss(DialogInterface param1DialogInterface) {
//            MendDetailActivity.access$1702(MendDetailActivity.this, false);
//          }
//        });
//    alertDialog.show();
//    Window window = alertDialog.getWindow();
//    window.setGravity(17);
//    window.setLayout(-1, -2);
//    this.m_orderServiceQualityShowFlag = true;
  }
  
  private void orderServiceQualityUpload(boolean paramBoolean) {
//    NetRequestConstant netRequestConstant = new NetRequestConstant();
//    netRequestConstant.setType(HttpRequestType.PUT);
//    netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/Mend/" + this.m_businessKey;
//    netRequestConstant.context = (Context)this;
//    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
//    if (paramBoolean) {
//      hashMap.put("processStatus", "PTSolved");
//      hashMap.put("resloveInfo", this.m_editTextPs.getText().toString());
//      netRequestConstant.setBody(hashMap);
//      getServer(new Netcallback() {
//            public void preccess(Object param1Object, boolean param1Boolean) {
//              if (param1Boolean) {
//                param1Object = param1Object;
//                if (param1Object != null) {
//                  if (param1Object.getStatusLine().getStatusCode() == 200) {
//                    param1Object = Toast.makeText((Context)MendDetailActivity.this, "处理成功！", 1);
//                    param1Object.setGravity(17, 0, 0);
//                    param1Object.show();
//                    MediaPlayer.create((Context)MendDetailActivity.this, 2131558403).start();
//                    param1Object = new Intent(MendDetailActivity.this.getApplicationContext(), MainlyActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("switchTab", 2);
//                    param1Object.putExtras(bundle);
//                    MendDetailActivity.this.startActivity((Intent)param1Object);
//                    MendDetailActivity.this.finish();
//                    return;
//                  }
//                  if (param1Object.getStatusLine().getStatusCode() == 404) {
//                    Toast.makeText((Context)MendDetailActivity.this, "报修处理失败", 1).show();
//                    return;
//                  }
//                  if (param1Object.getStatusLine().getStatusCode() == 401) {
//                    Toast.makeText((Context)MendDetailActivity.this, "鉴权失败，请重新登录" + param1Object.getStatusLine().getStatusCode(), 1).show();
//                    return;
//                  }
//                  Toast.makeText((Context)MendDetailActivity.this, "报修处理失败" + param1Object.getStatusLine().getStatusCode(), 1).show();
//                  return;
//                }
//                Toast.makeText((Context)MendDetailActivity.this, "请求超时，请检查网络", 1).show();
//                return;
//              }
//              Toast.makeText((Context)MendDetailActivity.this, "网络未连接！", 1).show();
//            }
//          }netRequestConstant);
//      return;
//    }
//    showToast("客户不同意！");
  }
  
  private boolean readWriteCardDemo(int paramInt) {
    switch (paramInt) {
      default:
        return true;
      case 6:
        break;
    } 
    Ntag21x ntag21x = (Ntag21x)this.bleNfcDevice.getCard();
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
        this.handler.sendMessage(message);
      } catch (CardNoResponseException cardNoResponseException) {
        cardNoResponseException.printStackTrace();
        bool = false;
      } 
      return bool;
    }
    return true;
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
      this.m_businessKey = this.m_CheckJson.get("mendSn").toString();
      StringBuilder stringBuilder2 = new StringBuilder();
      String str3 = stringBuilder2.append("报修单号：").append(jSONObject1.get("mendSn").toString()).toString();
      this.m_textViewOrderSn.setText(str3);
      StringBuilder stringBuilder1 = new StringBuilder();
      String str2 = stringBuilder1.append("下单时间：").append(jSONObject1.get("createTime").toString()).toString();
      this.m_textViewCreateTime.setText(str2);
      this.m_textViewCheckType.setText(jSONObject1.getJSONObject("mendType").getString("name"));
      this.m_textview_checkDetail.setText(jSONObject1.getString("detail"));
      this.m_textviewReserveTime.setText(jSONObject1.get("reserveTime").toString());
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
//    AppContext appContext = (AppContext)getApplicationContext();
//    LatLng latLng2 = appContext.getLocation();
//    LatLng latLng1 = appContext.getLocation();
//    AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(new Poi("当前位置", latLng2, ""), null, new Poi(this.m_recvAddr, latLng1, ""), AmapNaviType.DRIVER), this);
//    AMapNavi.getInstance((Context)this).setUseInnerVoice(true);
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
      JSONObject jSONObject = new JSONObject();
      this.m_CheckJson = jSONObject;
//      this.m_buttonNext = (Button)findViewById(2131230765);
//      this.m_textViewOrderSn = (TextView)findViewById(2131230871);
//      this.m_textViewUserId = (TextView)findViewById(2131230889);
//      this.m_textViewUserPhone = (TextView)findViewById(2131230890);
//      this.m_textViewCreateTime = (TextView)findViewById(2131230858);
//      this.m_textviewReserveTime = (TextView)findViewById(2131231107);
//      this.m_textViewAddress = (TextView)findViewById(2131230852);
//      this.m_imageViewNav = (ImageView)findViewById(2131230816);
//      this.m_imageViewCall = (ImageView)findViewById(2131230812);
//      this.m_imageViewPic = (ImageView)findViewById(2131230817);
//      this.m_textViewAddress = (TextView)findViewById(2131230852);
//      this.m_editTextPs = (EditText)findViewById(2131230846);
//      this.m_textViewCheckType = (TextView)findViewById(2131231096);
//      this.m_textview_checkDetail = (TextView)findViewById(2131231095);
      //this.m_buttonNext.setOnClickListener(this);
      //this.m_imageViewNav.setOnClickListener(this);
      //this.m_imageViewCall.setOnClickListener(this);
      //this.m_imageViewPic.setOnClickListener(this);
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
      default:
        return;
      case 2131230765:
        if (this.m_editTextPs.getText().toString().length() > 0 && this.m_orderStatus == 1)
          getOrderOps(); 
        Toast.makeText((Context)this, "请填写处理结果！", Toast.LENGTH_LONG).show();
      case 2131230816:
        switchNavBar();
      case 2131230812:
        callPhone(this.m_customerPhone);
      case 2131230817:
        break;
    } 
//    Intent intent = new Intent();
//    Bundle bundle = new Bundle();
//    bundle.putString("fileFolder", "mend");
//    bundle.putString("fileNameHeader", this.m_businessKey);
//    bundle.putInt("MaxPicCount", 4);
//    intent.setClass((Context)this, PicSelActivity.class);
//    intent.putExtras(bundle);
//    startActivity(intent);
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
  
  public void onGetNavigationText(String paramString) {}
  
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