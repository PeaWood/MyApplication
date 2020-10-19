package com.gc.nfc.ui;

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
//import com.amap.api.maps.model.LatLng;
//import com.amap.api.maps.model.Poi;
//import com.amap.api.navi.AMapNavi;
//import com.amap.api.navi.AmapNaviPage;
//import com.amap.api.navi.AmapNaviParams;
//import com.amap.api.navi.AmapNaviType;
//import com.amap.api.navi.INaviInfoCallback;
//import com.amap.api.navi.model.AMapNaviLocation;
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
import com.gc.nfc.interfaces.Netcallback;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
//INaviInfoCallback
public class MendDetailActivity extends BaseActivity implements View.OnClickListener{
  private AppContext appContext;
  
  private BleNfcDevice bleNfcDevice;
  
  private DeviceManagerCallback deviceManagerCallback = new DeviceManagerCallback() {
      public void onReceiveButtonEnter(byte param1Byte) {}
      
      public void onReceiveConnectBtDevice(boolean param1Boolean) {
        super.onReceiveConnectBtDevice(param1Boolean);
        if (param1Boolean) {
          System.out.println("Activity设备连接成功");
          MendDetailActivity.this.msgBuffer.delete(0, MendDetailActivity.this.msgBuffer.length());
          MendDetailActivity.this.msgBuffer.append("设备连接成功!");
          if (MendDetailActivity.this.mNearestBle != null);
          try {
            Thread.sleep(500L);
            MendDetailActivity.this.handlerBlue.sendEmptyMessage(3);
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
        MendDetailActivity.this.msgBuffer.delete(0, MendDetailActivity.this.msgBuffer.length());
        MendDetailActivity.this.msgBuffer.append("设备断开链接!");
        MendDetailActivity.this.handlerBlue.sendEmptyMessage(0);
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
                    if (MendDetailActivity.this.bleNfcDevice.isAutoSearchCard()) {
                      MendDetailActivity.this.bleNfcDevice.stoptAutoSearchCard();
                      bool = MendDetailActivity.this.readWriteCardDemo(cardTypeTemp);
                      MendDetailActivity.this.startAutoSearchCard();
                    } else {
                      bool = MendDetailActivity.this.readWriteCardDemo(cardTypeTemp);
                      MendDetailActivity.this.bleNfcDevice.closeRf();
                    } 
                    if (bool) {
                      MendDetailActivity.this.bleNfcDevice.openBeep(50, 50, 3);
                      return;
                    } 
                  } catch (DeviceNoResponseException deviceNoResponseException) {
                    deviceNoResponseException.printStackTrace();
                    return;
                  }
                  try {
                    MendDetailActivity.this.bleNfcDevice.openBeep(100, 100, 2);
                  } catch (DeviceNoResponseException e) {
                    e.printStackTrace();
                  }
                }
              })).start();
        } 
      }
    };
  
  private Handler handlerBlue = new Handler() {
      public void handleMessage(Message param1Message) {
        MendDetailActivity.this.msgText.setText(MendDetailActivity.this.msgBuffer);
        if (MendDetailActivity.this.bleNfcDevice.isConnection() == 2 || MendDetailActivity.this.bleNfcDevice.isConnection() == 1);
        switch (param1Message.what) {
          default:
            return;
          case 3:
            (new Thread(new Runnable() {
                  public void run() {
                    try {
                      MendDetailActivity.this.bleNfcDevice.getDeviceVersions();
                      MendDetailActivity.this.handlerBlue.sendEmptyMessage(0);
                      if (MendDetailActivity.this.bleNfcDevice.getDeviceBatteryVoltage() < 3.61D) {
                        MendDetailActivity.this.msgBuffer.append("(电量低)");
                      } else {
                        MendDetailActivity.this.msgBuffer.append("(电量充足)");
                      } 
                      MendDetailActivity.this.handlerBlue.sendEmptyMessage(0);
                      if (MendDetailActivity.this.bleNfcDevice.androidFastParams(true));
                      MendDetailActivity.this.handlerBlue.sendEmptyMessage(0);
                      MendDetailActivity.this.handlerBlue.sendEmptyMessage(0);
                      MendDetailActivity.this.startAutoSearchCard();
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
      }
    };
  
  private int lastRssi = -100;
  
  BleNfcDeviceService mBleNfcDeviceService;
  
  private BluetoothDevice mNearestBle = null;
  
  private Lock mNearestBleLock = new ReentrantLock();
  
  private Scanner mScanner;
  
  private final ServiceConnection mServiceConnection = new ServiceConnection() {
      public void onServiceConnected(ComponentName param1ComponentName, IBinder param1IBinder) {
        BleNfcDeviceService bleNfcDeviceService = ((BleNfcDeviceService.LocalBinder)param1IBinder).getService();
        bleNfcDevice = mBleNfcDeviceService.bleNfcDevice;
        mScanner = mBleNfcDeviceService.scanner;
        bleNfcDeviceService.setDeviceManagerCallback(MendDetailActivity.this.deviceManagerCallback);
        bleNfcDeviceService.setScannerCallback(MendDetailActivity.this.scannerCallback);
        MendDetailActivity.this.searchNearestBleDevice();
      }
      
      public void onServiceDisconnected(ComponentName param1ComponentName) {
        MendDetailActivity.this.mBleNfcDeviceService = null;
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
      public void onReceiveScanDevice(BluetoothDevice param1BluetoothDevice, int param1Int, byte[] param1ArrayOfbyte) {
        super.onReceiveScanDevice(param1BluetoothDevice, param1Int, param1ArrayOfbyte);
        if (Build.VERSION.SDK_INT >= 21)
          System.out.println("Activity搜到设备：" + param1BluetoothDevice.getName() + " 信号强度：" + param1Int + " scanRecord：" + StringTool.byteHexToSting(param1ArrayOfbyte)); 
        if (param1ArrayOfbyte != null && StringTool.byteHexToSting(param1ArrayOfbyte).contains("017f5450") && param1Int >= -55) {
          MendDetailActivity.this.handlerBlue.sendEmptyMessage(0);
          if (MendDetailActivity.this.mNearestBle != null) {
            if (param1Int > MendDetailActivity.this.lastRssi) {
              MendDetailActivity.this.mNearestBleLock.lock();
              try {
                mNearestBle = param1BluetoothDevice;
              } finally {
                MendDetailActivity.this.mNearestBleLock.unlock();
              } 
            } 
            return;
          } 
          MendDetailActivity.this.mNearestBleLock.lock();
          try {
            mNearestBle = param1BluetoothDevice;
            MendDetailActivity.this.mNearestBleLock.unlock();
          } finally {
            MendDetailActivity.this.mNearestBleLock.unlock();
          } 
        } 
      }
      
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
    this.msgText = (TextView)findViewById(R.id.msgText);
    this.m_imageViewSearchBlue = (ImageView)findViewById(R.id.imageView_search);
    this.m_imageViewSearchBlue.setOnClickListener(new StartSearchButtonListener());
    this.msgBuffer = new StringBuffer();
    bindService(new Intent((Context)this, BleNfcDeviceService.class), this.mServiceConnection, Context.BIND_AUTO_CREATE);
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
        this.handlerBlue.sendMessage(message);
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
    this.handlerBlue.sendEmptyMessage(0);
    if (!this.mScanner.isScanning() && this.bleNfcDevice.isConnection() == 0)
      (new Thread(new Runnable() {
            public void run() {
              // Byte code:
              //   0: aload_0
              //   1: monitorenter
              //   2: aload_0
              //   3: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   6: invokestatic access$200 : (Lcom/gc/nfc/ui/MendDetailActivity;)Lcom/dk/bleNfc/BleManager/Scanner;
              //   9: lconst_0
              //   10: invokevirtual startScan : (J)V
              //   13: aload_0
              //   14: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   17: invokestatic access$900 : (Lcom/gc/nfc/ui/MendDetailActivity;)Ljava/util/concurrent/locks/Lock;
              //   20: invokeinterface lock : ()V
              //   25: aload_0
              //   26: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   29: aconst_null
              //   30: invokestatic access$702 : (Lcom/gc/nfc/ui/MendDetailActivity;Landroid/bluetooth/BluetoothDevice;)Landroid/bluetooth/BluetoothDevice;
              //   33: pop
              //   34: aload_0
              //   35: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   38: invokestatic access$900 : (Lcom/gc/nfc/ui/MendDetailActivity;)Ljava/util/concurrent/locks/Lock;
              //   41: invokeinterface unlock : ()V
              //   46: aload_0
              //   47: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   50: bipush #-100
              //   52: invokestatic access$802 : (Lcom/gc/nfc/ui/MendDetailActivity;I)I
              //   55: pop
              //   56: iconst_0
              //   57: istore_1
              //   58: aload_0
              //   59: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   62: invokestatic access$700 : (Lcom/gc/nfc/ui/MendDetailActivity;)Landroid/bluetooth/BluetoothDevice;
              //   65: ifnonnull -> 141
              //   68: iload_1
              //   69: sipush #20000
              //   72: if_icmpge -> 141
              //   75: aload_0
              //   76: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   79: invokestatic access$200 : (Lcom/gc/nfc/ui/MendDetailActivity;)Lcom/dk/bleNfc/BleManager/Scanner;
              //   82: invokevirtual isScanning : ()Z
              //   85: ifeq -> 141
              //   88: aload_0
              //   89: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   92: invokestatic access$100 : (Lcom/gc/nfc/ui/MendDetailActivity;)Lcom/dk/bleNfc/DeviceManager/BleNfcDevice;
              //   95: invokevirtual isConnection : ()I
              //   98: istore_2
              //   99: iload_2
              //   100: ifne -> 141
              //   103: iinc #1, 1
              //   106: lconst_1
              //   107: invokestatic sleep : (J)V
              //   110: goto -> 58
              //   113: astore_3
              //   114: aload_3
              //   115: invokevirtual printStackTrace : ()V
              //   118: goto -> 58
              //   121: astore_3
              //   122: aload_0
              //   123: monitorexit
              //   124: aload_3
              //   125: athrow
              //   126: astore_3
              //   127: aload_0
              //   128: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   131: invokestatic access$900 : (Lcom/gc/nfc/ui/MendDetailActivity;)Ljava/util/concurrent/locks/Lock;
              //   134: invokeinterface unlock : ()V
              //   139: aload_3
              //   140: athrow
              //   141: aload_0
              //   142: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   145: invokestatic access$200 : (Lcom/gc/nfc/ui/MendDetailActivity;)Lcom/dk/bleNfc/BleManager/Scanner;
              //   148: invokevirtual isScanning : ()Z
              //   151: ifeq -> 372
              //   154: aload_0
              //   155: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   158: invokestatic access$100 : (Lcom/gc/nfc/ui/MendDetailActivity;)Lcom/dk/bleNfc/DeviceManager/BleNfcDevice;
              //   161: invokevirtual isConnection : ()I
              //   164: istore_1
              //   165: iload_1
              //   166: ifne -> 372
              //   169: ldc2_w 500
              //   172: invokestatic sleep : (J)V
              //   175: aload_0
              //   176: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   179: invokestatic access$200 : (Lcom/gc/nfc/ui/MendDetailActivity;)Lcom/dk/bleNfc/BleManager/Scanner;
              //   182: invokevirtual stopScan : ()V
              //   185: aload_0
              //   186: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   189: invokestatic access$900 : (Lcom/gc/nfc/ui/MendDetailActivity;)Ljava/util/concurrent/locks/Lock;
              //   192: invokeinterface lock : ()V
              //   197: aload_0
              //   198: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   201: invokestatic access$700 : (Lcom/gc/nfc/ui/MendDetailActivity;)Landroid/bluetooth/BluetoothDevice;
              //   204: ifnull -> 307
              //   207: aload_0
              //   208: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   211: invokestatic access$200 : (Lcom/gc/nfc/ui/MendDetailActivity;)Lcom/dk/bleNfc/BleManager/Scanner;
              //   214: invokevirtual stopScan : ()V
              //   217: aload_0
              //   218: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   221: invokestatic access$1000 : (Lcom/gc/nfc/ui/MendDetailActivity;)Ljava/lang/StringBuffer;
              //   224: iconst_0
              //   225: aload_0
              //   226: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   229: invokestatic access$1000 : (Lcom/gc/nfc/ui/MendDetailActivity;)Ljava/lang/StringBuffer;
              //   232: invokevirtual length : ()I
              //   235: invokevirtual delete : (II)Ljava/lang/StringBuffer;
              //   238: pop
              //   239: aload_0
              //   240: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   243: invokestatic access$1000 : (Lcom/gc/nfc/ui/MendDetailActivity;)Ljava/lang/StringBuffer;
              //   246: ldc '正在连接设备...'
              //   248: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
              //   251: pop
              //   252: aload_0
              //   253: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   256: invokestatic access$600 : (Lcom/gc/nfc/ui/MendDetailActivity;)Landroid/os/Handler;
              //   259: iconst_0
              //   260: invokevirtual sendEmptyMessage : (I)Z
              //   263: pop
              //   264: aload_0
              //   265: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   268: invokestatic access$100 : (Lcom/gc/nfc/ui/MendDetailActivity;)Lcom/dk/bleNfc/DeviceManager/BleNfcDevice;
              //   271: aload_0
              //   272: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   275: invokestatic access$700 : (Lcom/gc/nfc/ui/MendDetailActivity;)Landroid/bluetooth/BluetoothDevice;
              //   278: invokevirtual getAddress : ()Ljava/lang/String;
              //   281: invokevirtual requestConnectBleDevice : (Ljava/lang/String;)V
              //   284: aload_0
              //   285: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   288: invokestatic access$900 : (Lcom/gc/nfc/ui/MendDetailActivity;)Ljava/util/concurrent/locks/Lock;
              //   291: invokeinterface unlock : ()V
              //   296: aload_0
              //   297: monitorexit
              //   298: return
              //   299: astore_3
              //   300: aload_3
              //   301: invokevirtual printStackTrace : ()V
              //   304: goto -> 175
              //   307: aload_0
              //   308: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   311: invokestatic access$1000 : (Lcom/gc/nfc/ui/MendDetailActivity;)Ljava/lang/StringBuffer;
              //   314: iconst_0
              //   315: aload_0
              //   316: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   319: invokestatic access$1000 : (Lcom/gc/nfc/ui/MendDetailActivity;)Ljava/lang/StringBuffer;
              //   322: invokevirtual length : ()I
              //   325: invokevirtual delete : (II)Ljava/lang/StringBuffer;
              //   328: pop
              //   329: aload_0
              //   330: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   333: invokestatic access$1000 : (Lcom/gc/nfc/ui/MendDetailActivity;)Ljava/lang/StringBuffer;
              //   336: ldc '未找到设备！'
              //   338: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuffer;
              //   341: pop
              //   342: aload_0
              //   343: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   346: invokestatic access$600 : (Lcom/gc/nfc/ui/MendDetailActivity;)Landroid/os/Handler;
              //   349: iconst_0
              //   350: invokevirtual sendEmptyMessage : (I)Z
              //   353: pop
              //   354: goto -> 284
              //   357: astore_3
              //   358: aload_0
              //   359: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   362: invokestatic access$900 : (Lcom/gc/nfc/ui/MendDetailActivity;)Ljava/util/concurrent/locks/Lock;
              //   365: invokeinterface unlock : ()V
              //   370: aload_3
              //   371: athrow
              //   372: aload_0
              //   373: getfield this$0 : Lcom/gc/nfc/ui/MendDetailActivity;
              //   376: invokestatic access$200 : (Lcom/gc/nfc/ui/MendDetailActivity;)Lcom/dk/bleNfc/BleManager/Scanner;
              //   379: invokevirtual stopScan : ()V
              //   382: goto -> 296
              // Exception table:
              //   from	to	target	type
              //   2	25	121	finally
              //   25	34	126	finally
              //   34	56	121	finally
              //   58	68	121	finally
              //   75	99	121	finally
              //   106	110	113	java/lang/InterruptedException
              //   106	110	121	finally
              //   114	118	121	finally
              //   122	124	121	finally
              //   127	141	121	finally
              //   141	165	121	finally
              //   169	175	299	java/lang/InterruptedException
              //   169	175	121	finally
              //   175	197	121	finally
              //   197	284	357	finally
              //   284	296	121	finally
              //   296	298	121	finally
              //   300	304	121	finally
              //   307	354	357	finally
              //   358	372	121	finally
              //   372	382	121	finally
            }
          })).start(); 
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
    this.handlerBlue.sendMessage(message);
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
          this.handlerBlue.sendEmptyMessage(0);
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
    private StartSearchButtonListener() {}
    
    public void onClick(View param1View) {
      if (MendDetailActivity.this.bleNfcDevice.isConnection() == 2) {
        MendDetailActivity.this.bleNfcDevice.requestDisConnectDevice();
        return;
      } 
      MendDetailActivity.this.searchNearestBleDevice();
    }
  }
}