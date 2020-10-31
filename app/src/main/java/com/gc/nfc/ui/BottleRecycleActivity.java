package com.gc.nfc.ui;

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
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
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
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
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
import com.gc.nfc.utils.SharedPreferencesHelper;
import com.gc.nfc.utils.Tools;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class BottleRecycleActivity extends BaseActivity implements View.OnClickListener {
  private AppContext appContext;
  
  private BleNfcDevice bleNfcDevice;
  
  private DeviceManagerCallback deviceManagerCallback = new DeviceManagerCallback() {
      public void onReceiveButtonEnter(byte param1Byte) {}
      
      public void onReceiveConnectBtDevice(boolean param1Boolean) {
        super.onReceiveConnectBtDevice(param1Boolean);
        if (param1Boolean) {
          System.out.println("Activity设备连接成功");
          BottleRecycleActivity.this.msgBuffer.delete(0, BottleRecycleActivity.this.msgBuffer.length());
          BottleRecycleActivity.this.msgBuffer.append("设备连接成功!");
          if (BottleRecycleActivity.this.mNearestBle != null);
          try {
            Thread.sleep(500L);
            BottleRecycleActivity.this.handler.sendEmptyMessage(3);
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
        BottleRecycleActivity.this.msgBuffer.delete(0, BottleRecycleActivity.this.msgBuffer.length());
        BottleRecycleActivity.this.msgBuffer.append("设备断开链接!");
        BottleRecycleActivity.this.handler.sendEmptyMessage(0);
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
                    if (BottleRecycleActivity.this.bleNfcDevice.isAutoSearchCard()) {
                      BottleRecycleActivity.this.bleNfcDevice.stoptAutoSearchCard();
                      bool = BottleRecycleActivity.this.readWriteCardDemo(cardTypeTemp);
                      BottleRecycleActivity.this.startAutoSearchCard();
                    } else {
                      bool = BottleRecycleActivity.this.readWriteCardDemo(cardTypeTemp);
                      BottleRecycleActivity.this.bleNfcDevice.closeRf();
                    } 
                    if (bool) {
                      BottleRecycleActivity.this.bleNfcDevice.openBeep(50, 50, 3);
                      return;
                    } 
                  } catch (DeviceNoResponseException deviceNoResponseException) {
                    deviceNoResponseException.printStackTrace();
                    return;
                  }
                  try {
                    BottleRecycleActivity.this.bleNfcDevice.openBeep(100, 100, 2);
                  } catch (DeviceNoResponseException e) {
                    e.printStackTrace();
                  }
                }
              })).start();
        } 
      }
    };
  
  private Handler handler = new Handler() {
      public void handleMessage(Message param1Message) {
        String str2;
        BottleRecycleActivity.this.msgText.setText(BottleRecycleActivity.this.msgBuffer);
        if (BottleRecycleActivity.this.bleNfcDevice.isConnection() == 2 || BottleRecycleActivity.this.bleNfcDevice.isConnection() == 1);
        switch (param1Message.what) {
          case 3:
            (new Thread(new Runnable() {
                  public void run() {
                    try {
                      BottleRecycleActivity.this.bleNfcDevice.getDeviceVersions();
                      BottleRecycleActivity.this.handler.sendEmptyMessage(0);
                      if (BottleRecycleActivity.this.bleNfcDevice.getDeviceBatteryVoltage() < 3.61D) {
                        BottleRecycleActivity.this.msgBuffer.append("(电量低)");
                      } else {
                        BottleRecycleActivity.this.msgBuffer.append("(电量充足)");
                      } 
                      BottleRecycleActivity.this.handler.sendEmptyMessage(0);
                      if (BottleRecycleActivity.this.bleNfcDevice.androidFastParams(true));
                      BottleRecycleActivity.this.handler.sendEmptyMessage(0);
                      BottleRecycleActivity.this.handler.sendEmptyMessage(0);
                      BottleRecycleActivity.this.startAutoSearchCard();
                    } catch (DeviceNoResponseException deviceNoResponseException) {
                      deviceNoResponseException.printStackTrace();
                    } 
                  }
                })).start();
          case 136:
            if (BottleRecycleActivity.this.m_curUserId == null)
              BottleRecycleActivity.this.showToast("请先扫描用户卡"); 
            str2 = param1Message.obj.toString();
            if (str2 == null)
              BottleRecycleActivity.this.showToast("空标签！"); 
            if ((str2.split(":")).length == 2)
              BottleRecycleActivity.this.showToast("无效钢瓶码格式！"); 
            switch (BottleRecycleActivity.this.m_selected_nfc_model) {
              case 0:
                BottleRecycleActivity.this.bottleTakeOverUnit(str2, BottleRecycleActivity.this.m_curUserId, BottleRecycleActivity.this.m_deliveryUser.getUsername(), "6", "退换货流程|空瓶回收", false, true, true, "空瓶回收");
                break;
              case 1:
                BottleRecycleActivity.this.bottleTakeOverUnit(str2, BottleRecycleActivity.this.m_curUserId, BottleRecycleActivity.this.m_deliveryUser.getUsername(), "6", "退换货流程|重瓶回收", false, true, false, "重瓶回收");
                break;
              case 2:
                BottleRecycleActivity.this.bottleTakeOverUnit(str2, BottleRecycleActivity.this.m_deliveryUser.getUsername(), BottleRecycleActivity.this.m_curUserId, "5", "退换货流程|重瓶落户", false, true, true, "重瓶落户");
                break;
            } 

          case 137:
            String[] arrayOfString = param1Message.obj.toString().split(":");
            if (arrayOfString.length != 2)
              BottleRecycleActivity.this.showToast("无效卡格式！");
            String str1 = arrayOfString[1];
            BottleRecycleActivity.this.GetUserCardInfo(str1);
            break;
        } 

      }
    };
  
  private int lastRssi = -100;
  
  private RadioGroup.OnCheckedChangeListener listen = new RadioGroup.OnCheckedChangeListener() {
      public void onCheckedChanged(RadioGroup param1RadioGroup, int param1Int) {
        param1RadioGroup.getCheckedRadioButtonId();
        switch (param1RadioGroup.getCheckedRadioButtonId()) {
          case R.id.radioButton_kp_recyle:
            m_selected_nfc_model = 0;
            break;
          case R.id.radioButton_zp_recyle:
            m_selected_nfc_model = 1;
            break;
          case R.id.radioButton_zp_reput:
            m_selected_nfc_model = 2;
            break;
        } 

      }
    };
  
  BleNfcDeviceService mBleNfcDeviceService;
  
  private BluetoothDevice mNearestBle = null;
  
  private Lock mNearestBleLock = new ReentrantLock();
  
  private Scanner mScanner;
  
  private final ServiceConnection mServiceConnection = new ServiceConnection() {
      public void onServiceConnected(ComponentName param1ComponentName, IBinder param1IBinder) {
        BleNfcDeviceService bleNfcDeviceService = ((BleNfcDeviceService.LocalBinder)param1IBinder).getService();
        bleNfcDevice = bleNfcDeviceService.bleNfcDevice;
        mScanner = bleNfcDeviceService.scanner;
        bleNfcDeviceService.setDeviceManagerCallback(BottleRecycleActivity.this.deviceManagerCallback);
        bleNfcDeviceService.setScannerCallback(BottleRecycleActivity.this.scannerCallback);
        BottleRecycleActivity.this.searchNearestBleDevice();
      }
      
      public void onServiceDisconnected(ComponentName param1ComponentName) {
        BottleRecycleActivity.this.mBleNfcDeviceService = null;
      }
    };
  
  private Map<String, String> m_BottlesMapKP;
  
  private ImageView m_addKPManual;
  
  private EditText m_bottleIdKPEditText;
  
  private EditText m_bottleIdZPEditText;
  
  private Button m_buttonNext;
  
  private EditText m_cstCode;
  
  private String m_curUserId = null;
  
  private String m_curUserInfo = null;
  
  private User m_deliveryUser;
  
  AlertDialog m_dialog;
  
  private String m_gp_code_head_KP;
  
  private ImageView m_imageViewKPEye;
  
  private ImageView m_imageViewSanUserCard;
  
  private ImageView m_imageViewSearchBlue = null;
  
  private ListView m_listView_kp;
  
  private boolean m_orderServiceQualityShowFlag;
  
  private RelativeLayout m_relativeLayout_san;
  
  private int m_selected_nfc_model;
  
  private Spinner m_spinnerGPHeadKP;
  
  private TextView m_textViewTotalCountKP;
  
  private TextView m_textViewUserInfo;
  
  private StringBuffer msgBuffer;
  
  private EditText msgText = null;
  
  private RadioButton radioButton_kp_recyle;
  
  private RadioButton radioButton_zp_recyle;
  
  private RadioButton radioButton_zp_reput;
  
  private RadioGroup radioGroup_nfc = null;
  
  private ProgressDialog readWriteDialog = null;
  
  private ScannerCallback scannerCallback = new ScannerCallback() {
      public void onReceiveScanDevice(BluetoothDevice param1BluetoothDevice, int param1Int, byte[] param1ArrayOfbyte) {
        super.onReceiveScanDevice(param1BluetoothDevice, param1Int, param1ArrayOfbyte);
        if (Build.VERSION.SDK_INT >= 21)
          System.out.println("Activity搜到设备：" + param1BluetoothDevice.getName() + " 信号强度：" + param1Int + " scanRecord：" + StringTool.byteHexToSting(param1ArrayOfbyte)); 
        if (param1ArrayOfbyte != null && StringTool.byteHexToSting(param1ArrayOfbyte).contains("017f5450") && param1Int >= -55) {
          BottleRecycleActivity.this.handler.sendEmptyMessage(0);
          if (BottleRecycleActivity.this.mNearestBle != null) {
            if (param1Int > BottleRecycleActivity.this.lastRssi) {
              BottleRecycleActivity.this.mNearestBleLock.lock();
              try {
                mNearestBle = param1BluetoothDevice;
                return;
              } finally {
                BottleRecycleActivity.this.mNearestBleLock.unlock();
              } 
            } 
            return;
          } 
          BottleRecycleActivity.this.mNearestBleLock.lock();
          try {
            mNearestBle = param1BluetoothDevice;
            BottleRecycleActivity.this.mNearestBleLock.unlock();
            return;
          } finally {
            BottleRecycleActivity.this.mNearestBleLock.unlock();
          } 
        } 
      }
      
      public void onScanDeviceStopped() {
        super.onScanDeviceStopped();
      }
    };
  
  private Toast toast = null;
  
  TextView tv;
  
  private void GetUserCardInfo(String paramString) {
    NetRequestConstant netRequestConstant = new NetRequestConstant();
    netRequestConstant.setType(HttpRequestType.GET);
    netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/UserCard";
    netRequestConstant.context = (Context)this;
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("number", paramString);
    netRequestConstant.setParams(hashMap);
    getServer(new Netcallback() {
          public void preccess(Object param1Object, boolean param1Boolean) {
            if (param1Boolean) {
//              param1Object = param1Object;
              HttpResponse response=(HttpResponse)param1Object;
              if (param1Object != null) {
                if (response.getStatusLine().getStatusCode() == 200) {
                  try {
                    JSONObject jSONObject = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                    JSONArray items = jSONObject.getJSONArray("items");
                    if (items.length() == 1) {
                      JSONObject user = items.getJSONObject(0).getJSONObject("user");
                      m_curUserId = user.getString("userId");
                      m_curUserInfo = user.getString("name");
                      StringBuilder stringBuilder = new StringBuilder();
                      m_textViewUserInfo.setText(stringBuilder.append(BottleRecycleActivity.this.m_curUserId).append("  |  ").append(BottleRecycleActivity.this.m_curUserInfo).toString());
                      BottleRecycleActivity.this.m_dialog.dismiss();
                      return;
                    }
                    m_curUserId = (String)null;
                    m_curUserInfo = (String)null;
                    BottleRecycleActivity.this.showToast("未绑定用户卡");
                    BottleRecycleActivity.this.m_textViewUserInfo.setText("");
                  } catch (JSONException jSONException) {
                    Toast.makeText((Context)BottleRecycleActivity.this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
                  } catch (IOException iOException) {
                    Toast.makeText((Context)BottleRecycleActivity.this, "异常" + iOException.toString(), Toast.LENGTH_LONG).show();
                  } 
                  return;
                } 
                Toast.makeText((Context)BottleRecycleActivity.this, "用户卡查询失败", Toast.LENGTH_LONG).show();
                return;
              } 
              Toast.makeText((Context)BottleRecycleActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
              return;
            } 
            Toast.makeText((Context)BottleRecycleActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
          }
        },netRequestConstant);
  }
  
  private void addKP(String paramString1, String paramString2) {
    if (!this.m_BottlesMapKP.containsKey(paramString1)) {
      this.m_BottlesMapKP.put(paramString1, paramString2);
      refleshBottlesListKP();
    } 
  }
  
  private void blueDeviceInitial() {
    this.msgText = (EditText)findViewById(R.id.msgText);
    this.m_imageViewSearchBlue = (ImageView)findViewById(R.id.imageView_search);
    this.m_imageViewSearchBlue.setOnClickListener(new StartSearchButtonListener());
    this.msgBuffer = new StringBuffer();
    bindService(new Intent((Context)this, BleNfcDeviceService.class), this.mServiceConnection, Context.BIND_AUTO_CREATE);
  }
  
  private String getResponseMessage(HttpResponse paramHttpResponse) {
    return null;
  }
  
  private void orderServiceQualityShow() {
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
    View view = View.inflate((Context)this, R.layout.user_evaluate, null);
    builder.setIcon(R.drawable.icon_logo);
    builder.setTitle("用户卡确认");
    this.m_dialog = builder.create();
    this.m_dialog.setView(view);
    this.m_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
          public void onDismiss(DialogInterface param1DialogInterface) {
            m_orderServiceQualityShowFlag = false;
          }
        });
    this.m_dialog.show();
    Window window = this.m_dialog.getWindow();
    window.setGravity(17);
    window.setLayout(-1, -2);
    this.m_orderServiceQualityShowFlag = true;
  }
  
  private boolean readWriteCardDemo(int paramInt) {
    switch (paramInt) {
      case 6:
        Ntag21x ntag21x = (Ntag21x) this.bleNfcDevice.getCard();
        if (ntag21x != null) {
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
            return false;
          }
        }
        break;
    }
    return true;
  }
  
  private void refleshBottlesListKP() {
    this.m_textViewTotalCountKP.setText(Integer.toString(this.m_BottlesMapKP.size()));
    ArrayList arrayList = new ArrayList();
    for (Map.Entry<String, String> entry : this.m_BottlesMapKP.entrySet()) {
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      hashMap.put("bottleCode", entry.getKey());
      hashMap.put("bottleWeight", entry.getValue());
      arrayList.add(hashMap);
    } 
    SimpleAdapter simpleAdapter = new SimpleAdapter((Context)this, arrayList, R.layout.bottle_list_simple_items, new String[] { "bottleCode", "bottleWeight" }, new int[] { R.id.items_number, R.id.items_weight });
    this.m_listView_kp.setAdapter((ListAdapter)simpleAdapter);
    setListViewHeightBasedOnChildren(this.m_listView_kp);
  }
  
  private void searchCst() {
    String str = this.m_cstCode.getText().toString();
    NetRequestConstant netRequestConstant = new NetRequestConstant();
    netRequestConstant.setType(HttpRequestType.GET);
    netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/customers/findByUserId";
    netRequestConstant.context = (Context)this;
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("userId", str);
    netRequestConstant.setParams(hashMap);
    getServer(new Netcallback() {
          public void preccess(Object param1Object, boolean param1Boolean) {
            if (param1Boolean) {
              HttpResponse response=(HttpResponse)param1Object;
              if (param1Object != null) {
                if (response.getStatusLine().getStatusCode() == 200) {
                  try {
                    JSONObject jSONObject = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                    m_curUserId = jSONObject.getString("userId");
                    m_curUserInfo = jSONObject.getString("name");
                    StringBuilder stringBuilder = new StringBuilder();
                    BottleRecycleActivity.this.m_cstCode.setText(stringBuilder.append(BottleRecycleActivity.this.m_curUserId).append("  |  ").append(BottleRecycleActivity.this.m_curUserInfo).toString());
                  } catch (JSONException jSONException) {
                    Toast.makeText((Context)BottleRecycleActivity.this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
                  } catch (IOException iOException) {
                    Toast.makeText((Context)BottleRecycleActivity.this, "异常" + iOException.toString(),  Toast.LENGTH_LONG).show();
                  } 
                  return;
                } 
                Toast.makeText((Context)BottleRecycleActivity.this, "客户编码输入错误",  Toast.LENGTH_LONG).show();
                return;
              } 
              Toast.makeText((Context)BottleRecycleActivity.this, "请求超时，请检查网络",  Toast.LENGTH_LONG).show();
              return;
            } 
            Toast.makeText((Context)BottleRecycleActivity.this, "网络未连接！",  Toast.LENGTH_LONG).show();
          }
        },netRequestConstant);
  }
  
  private void searchNearestBleDevice() {
    this.msgBuffer.delete(0, this.msgBuffer.length());
    this.msgBuffer.append("正在搜索设备...");
    this.handler.sendEmptyMessage(0);
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
      WindowManager windowManager = (WindowManager)getSystemService(Service.WINDOW_SERVICE);
      DisplayMetrics displayMetrics = new DisplayMetrics();
      windowManager.getDefaultDisplay().getMetrics(displayMetrics);
      this.tv = new TextView((Context)this);
      linearLayout.getBackground().setAlpha(0);
      this.tv.setTextSize(40.0F);
      this.tv.setTextColor(getResources().getColor(R.color.transparent_background));
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
  
  public void bottleTakeOverUnit(final String bottleCode, final String srcUserId, final String targetUserId, final String serviceStatus, final String note, boolean paramBoolean1, final boolean isKP, final boolean isChangeFillingStatus, final String takeReason) {
    boolean bool = false;
    if (this.m_BottlesMapKP.containsKey(bottleCode))
      bool = true; 
    if (bool) {
      Toast.makeText((Context)this, "钢瓶号：" + bottleCode + "    请勿重复提交！", Toast.LENGTH_LONG).show();
      return;
    } 
    NetRequestConstant netRequestConstant = new NetRequestConstant();
    netRequestConstant.setType(HttpRequestType.PUT);
    netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/GasCylinder/TakeOver/" + bottleCode;
    netRequestConstant.context = (Context)this;
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("srcUserId", srcUserId);
    hashMap.put("targetUserId", targetUserId);
    hashMap.put("serviceStatus", serviceStatus);
    hashMap.put("enableForce", Boolean.valueOf(paramBoolean1));
    hashMap.put("note", note);
    netRequestConstant.setParams(hashMap);
    getServer(new Netcallback() {
          public void preccess(Object param1Object, boolean param1Boolean) {
            if (param1Boolean) {
              HttpResponse response=(HttpResponse)param1Object;
              if (param1Object != null) {
                if (response.getStatusLine().getStatusCode() == 200) {
                  MediaPlayer.create((Context)BottleRecycleActivity.this, 2131558407).start();
                  BottleRecycleActivity.this.addKP(bottleCode, takeReason);
                  return;
                } 
                MediaPlayer.create((Context)BottleRecycleActivity.this, 2131558400).start();
                if (response.getStatusLine().getStatusCode() == 409) {
                  (new AlertDialog.Builder((Context)BottleRecycleActivity.this)).setTitle("钢瓶异常流转！").setMessage("钢瓶号 :" + bottleCode + "\r\n错误原因:" + BottleRecycleActivity.this.getResponseMessage((HttpResponse)param1Object) + "\r\n确认强制交接吗？").setIcon(R.drawable.icon_logo).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                          if (Tools.isFastClick())
                            BottleRecycleActivity.this.bottleTakeOverUnit(bottleCode, srcUserId, targetUserId, serviceStatus, note, true, isKP, isChangeFillingStatus, takeReason); 
                        }
                      }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {}
                      }).show();
                  return;
                } 
                (new AlertDialog.Builder((Context)BottleRecycleActivity.this)).setTitle("钢瓶异常流转！").setMessage("钢瓶号 :" + bottleCode + "\r\n错误原因:" + BottleRecycleActivity.this.getResponseMessage((HttpResponse)param1Object)).setIcon(R.drawable.icon_logo).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                      public void onClick(DialogInterface param2DialogInterface, int param2Int) {}
                    }).show();
                return;
              } 
              Toast.makeText((Context)BottleRecycleActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
              return;
            } 
            Toast.makeText((Context)BottleRecycleActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
          }
        },netRequestConstant);
  }
  
  public void deleteKP(final int position) {
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
    builder.setTitle("删除钢瓶");
    builder.setMessage("您确定要删除这只钢瓶吗?");
    builder.setIcon(R.drawable.icon_bottle);
    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            new HashMap<Object, Object>();
            String str = (String)((Map)BottleRecycleActivity.this.m_listView_kp.getItemAtPosition(position)).get("bottleCode");
            BottleRecycleActivity.this.m_BottlesMapKP.remove(str);
            BottleRecycleActivity.this.refleshBottlesListKP();
          }
        });
    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {}
        });
    builder.show();
  }
  
  void init() {
    setContentView(R.layout.activity_bottle_recycle);
    this.m_buttonNext = (Button)findViewById(R.id.button_next);
    this.m_listView_kp = (ListView)findViewById(R.id.listview_kp);
    this.m_imageViewKPEye = (ImageView)findViewById(R.id.imageView_KPEYE);
    this.m_addKPManual = (ImageView)findViewById(R.id.imageView_addKPManual);
    this.m_imageViewSanUserCard = (ImageView)findViewById(R.id.imageView_addKPManual);
    this.m_textViewTotalCountKP = (TextView)findViewById(R.id.items_totalCountKP);
    this.m_textViewUserInfo = (TextView)findViewById(R.id.textView_userInfo);
    this.m_relativeLayout_san = (RelativeLayout)findViewById(R.id.RelativeLayout_san);
    this.m_spinnerGPHeadKP = (Spinner)findViewById(R.id.spinner_gp_head_KP);
    this.radioGroup_nfc = (RadioGroup)findViewById(R.id.radioGroup_nfc_id);
    this.radioButton_kp_recyle = (RadioButton)findViewById(R.id.radioButton_kp_recyle);
    this.radioButton_zp_recyle = (RadioButton)findViewById(R.id.radioButton_zp_recyle);
    this.radioButton_zp_reput = (RadioButton)findViewById(R.id.radioButton_zp_reput);
    this.m_imageViewKPEye.setOnClickListener(this);
    this.m_addKPManual.setOnClickListener(this);
    this.m_buttonNext.setOnClickListener(this);
    this.m_imageViewSanUserCard.setOnClickListener(this);
    this.m_relativeLayout_san.setOnClickListener(this);
    this.m_bottleIdKPEditText = (EditText)findViewById(R.id.input_bottleIdKP);
    this.m_cstCode = (EditText)findViewById(R.id.cstCode);
    this.m_BottlesMapKP = new HashMap<String, String>();
    this.appContext = (AppContext)getApplicationContext();
    this.m_deliveryUser = this.appContext.getUser();
    if (this.m_deliveryUser == null) {
      Toast.makeText((Context)this, "登陆会话失效", Toast.LENGTH_LONG).show();
      startActivity(new Intent((Context)this, LoginActivity.class));
      finish();
    } 
    blueDeviceInitial();
    this.m_orderServiceQualityShowFlag = false;
    this.radioGroup_nfc.setOnCheckedChangeListener(this.listen);
    this.radioGroup_nfc.check(this.radioButton_kp_recyle.getId());
    this.m_spinnerGPHeadKP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
            String[] strings = new String[8];
            strings[0] = "KMA2B";
            strings[1] = "KMA2BA";
            strings[2] = "KMA2BB";
            strings[3] = "KMA2BC";
            strings[4] = "KMA2BD";
            strings[5] = "KMA2BE";
            strings[6] = "KMA2BF";
            strings[7] = "";
            m_gp_code_head_KP = strings[param1Int];
            SharedPreferencesHelper.put("codeHeadIndex", Integer.valueOf(param1Int));
          }
          
          public void onNothingSelected(AdapterView<?> param1AdapterView) {}
        });
  }
  
  public void onClick(View paramView) {
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
  
  protected void onResume() {
    super.onResume();
    if (this.mBleNfcDeviceService != null) {
      this.mBleNfcDeviceService.setScannerCallback(this.scannerCallback);
      this.mBleNfcDeviceService.setDeviceManagerCallback(this.deviceManagerCallback);
    } 
  }
  
  private class StartSearchButtonListener implements View.OnClickListener {
    private StartSearchButtonListener() {}
    
    public void onClick(View param1View) {
      if (BottleRecycleActivity.this.bleNfcDevice.isConnection() == 2) {
        BottleRecycleActivity.this.bleNfcDevice.requestDisConnectDevice();
        return;
      } 
      BottleRecycleActivity.this.searchNearestBleDevice();
    }
  }
}
