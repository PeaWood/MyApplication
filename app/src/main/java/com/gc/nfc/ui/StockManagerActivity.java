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
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Process;
import android.speech.tts.TextToSpeech;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.gc.nfc.sca.ScaleDevice;
import com.gc.nfc.sca.scalerSDK;
import com.gc.nfc.utils.SharedPreferencesHelper;
import com.gc.nfc.utils.Tools;
//import com.google.zxing.integration.android.IntentIntegrator;
//import com.google.zxing.integration.android.IntentResult;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StockManagerActivity extends BaseActivity implements View.OnClickListener, TextToSpeech.OnInitListener {
  private AppContext appContext;
  
  private BleNfcDevice bleNfcDevice;
  
  private DeviceManagerCallback deviceManagerCallback = new DeviceManagerCallback() {
      public void onReceiveButtonEnter(byte param1Byte) {}
      
      public void onReceiveConnectBtDevice(boolean param1Boolean) {
        super.onReceiveConnectBtDevice(param1Boolean);
        if (param1Boolean) {
          System.out.println("Activity设备连接成功");
          msgBuffer.delete(0, msgBuffer.length());
          msgBuffer.append("设备连接成功!");
          if (mNearestBle != null);
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
  
  private Handler handler = new Handler() {
      public void handleMessage(Message param1Message) {
        msgText.setText(msgBuffer);
        if (bleNfcDevice.isConnection() == 2 || bleNfcDevice.isConnection() == 1);
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
                      if (bleNfcDevice.androidFastParams(true));
                      handler.sendEmptyMessage(0);
                      handler.sendEmptyMessage(0);
                      startAutoSearchCard();
                    } catch (DeviceNoResponseException deviceNoResponseException) {
                      deviceNoResponseException.printStackTrace();
                    } 
                  }
                })).start();
          case 136:
              if (m_takerOverUserId == null)
                  showToast("请扫码获取交接人信息！");
              String str = param1Message.obj.toString();
              if (str == null)
                  showToast("空标签！");
              new String();
              if (m_selected_nfc_model == 0) {
                  String str1 = null;
                  if (m_curLoginUserId.getGroupCode().equals("00005")) {
                      str1 = "2";
                  } else if (m_curLoginUserId.getGroupCode().equals("00006")) {
                      str1 = "1";
                  } else {
                      Toast.makeText((Context)StockManagerActivity.this, "非充气站或门店账户，请退出！", Toast.LENGTH_LONG).show();
                  }
                  judgeBottleScrap(str, str1);
              }
              if (m_selected_nfc_model == 1) {
                  String str1 = "";
                  if (m_curLoginUserId.getGroupCode().equals("00005")) {
                      if (m_takeOverGroupCode.equals("00007")) {
                          str1 = "3";
                      } else if (m_takeOverGroupCode.equals("00003")) {
                          str1 = "4";
                      } else {
                          Toast.makeText((Context)StockManagerActivity.this, "非配送工或调拨车账户，请更换！", Toast.LENGTH_LONG).show();
                      }
                  } else if (m_curLoginUserId.getGroupCode().equals("00006")) {
                      str1 = "3";
                  } else {
                      Toast.makeText((Context)StockManagerActivity.this, "非充气站或门店账户，请退出！", Toast.LENGTH_LONG).show();
                  }
                  bottleTakeOverUnit(str, m_curLoginUserId.getUsername(), m_takerOverUserId, str1, m_curLoginUserId.getDepartmentName() + "|钢瓶出库", false, false);
              }
              break;
        } 

      }
    };
  
  private Handler handler_weightScale = new Handler();
  
  private int lastRssi = -100;
  
  private View layout_inputWeight;
  
  private RadioGroup.OnCheckedChangeListener listen = new RadioGroup.OnCheckedChangeListener() {
      public void onCheckedChanged(RadioGroup param1RadioGroup, int param1Int) {
        param1RadioGroup.getCheckedRadioButtonId();
        switch (param1RadioGroup.getCheckedRadioButtonId()) {
          case R.id.radioButton_kp_id:
              m_selected_nfc_model = 0;
          case R.id.radioButton_zp_id:
              m_selected_nfc_model = 1;
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
        bleNfcDevice =  bleNfcDeviceService.bleNfcDevice;
        mScanner = bleNfcDeviceService.scanner;
        bleNfcDeviceService.setDeviceManagerCallback(deviceManagerCallback);
        bleNfcDeviceService.setScannerCallback(scannerCallback);
        searchNearestBleDevice();
      }
      
      public void onServiceDisconnected(ComponentName param1ComponentName) {
        mBleNfcDeviceService = null;
      }
    };
  
  private List<String> m_BottlesListZP;
  
  private Intent m_IntentAmapServeice;
  
  private EditText m_bottleIdZPEditText;
  
  Bundle m_bundle;
  
  private Button m_buttonNext;
  
  private User m_curLoginUserId;
  
  private TextView m_editTextTakeOverUserId;
  
  private String m_gp_code_head;
  
  private ImageView m_imageAddZPManual;
  
  private ImageView m_imageViewScan;
  
  private ImageView m_imageViewSearchBlue = null;
  
  private ImageView m_imageView_search_weightDevice = null;
  
  private ListView m_listView_zp;
  
  private int m_selected_nfc_model;
  
  private Spinner m_spinnerGPHead;
  
  private String m_takeOverGroupCode;
  
  private String m_takerOverUserId;
  
  private TextView m_textViewTotalCountZP;
  
  private TextView m_textView_Department;
  
  private StringBuffer msgBuffer;
  
  private TextView msgText = null;
  
  private TextView msgText_weightDevice = null;
  
  private RadioButton radioButton_kp;
  
  private RadioButton radioButton_zp;
  
  private RadioGroup radioGroup_nfc = null;
  
  private ProgressDialog readWriteDialog = null;
  
  private scalerSDK scale;
  
  private ScannerCallback scannerCallback = new ScannerCallback() {
      public void onReceiveScanDevice(BluetoothDevice param1BluetoothDevice, int param1Int, byte[] param1ArrayOfbyte) {
        super.onReceiveScanDevice(param1BluetoothDevice, param1Int, param1ArrayOfbyte);
        if (Build.VERSION.SDK_INT >= 21)
          System.out.println("Activity搜到设备：" + param1BluetoothDevice.getName() + " 信号强度：" + param1Int + " scanRecord：" + StringTool.byteHexToSting(param1ArrayOfbyte)); 
        if (param1ArrayOfbyte != null && StringTool.byteHexToSting(param1ArrayOfbyte).contains("017f5450") && param1Int >= -55) {
          handler.sendEmptyMessage(0);
          if (mNearestBle != null) {
            if (param1Int > lastRssi) {
              mNearestBleLock.lock();
              try {
                  mNearestBle = param1BluetoothDevice;
                return;
              } finally {
                mNearestBleLock.unlock();
              } 
            } 
            return;
          } 
          mNearestBleLock.lock();
          try {
              mNearestBle = param1BluetoothDevice;
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
  
  private SwipeRefreshLayout swipeRefreshLayout;
  
  private Runnable task = new Runnable() {
      public void run() {
        handler_weightScale.postDelayed(this, 150L);
        if (scale.bleIsEnabled()) {
          scale.Scan(true);
          scale.updatelist();
          if (scale.getDevicelist().isEmpty() != true) {
            if (scale.getDevicelist().size() != 1) {
              msgText_weightDevice.setText("多台蓝牙秤冲突！");
              return;
            }
            msgText_weightDevice.setText("连接正常！");
            float f = ((ScaleDevice)scale.getDevicelist().get(0)).scalevalue;
            if (layout_inputWeight != null){
                TextView tv = layout_inputWeight.findViewById(R.id.input_bottleWeight);
                tv.setText(String.format("%4.1f", new Object[] { Float.valueOf(f) }));
            }
             return;
          }
          msgText_weightDevice.setText("未连接！");
        }
      }
    };
  
  private Toast toast = null;
  
  private TextToSpeech tts;
  
  TextView tv;
  
  private void addZP(String paramString) {
    boolean bool = false;
    int i = 0;
    while (true) {
      boolean bool1 = bool;
      if (i < m_BottlesListZP.size())
        if (((String)m_BottlesListZP.get(i)).equals(paramString)) {
          bool1 = true;
        } else {
          i++;
          continue;
        }  
      if (!bool1) {
        showToast(paramString);
        m_BottlesListZP.add(paramString);
        refleshBottlesListZP();
        i = paramString.length();
        if (i >= 3) {
          paramString = paramString.substring(i - 3, i);
          tts.speak(toChinese(paramString), 0, null);
          return;
        } 
      } else {
        return;
      } 
      tts.speak(toChinese(paramString), 0, null);
      return;
    } 
  }
  
  private void blueDeviceInitial() {
    msgText = (TextView)findViewById(R.id.msgText);
    m_imageViewSearchBlue = (ImageView)findViewById(R.id.imageView_search);
    m_imageViewSearchBlue.setOnClickListener(new StartSearchButtonListener());
    msgBuffer = new StringBuffer();
    bindService(new Intent((Context)this, BleNfcDeviceService.class), mServiceConnection, Context.BIND_AUTO_CREATE);
  }
  
  private void cleanAll() {
    m_takerOverUserId = null;
    m_BottlesListZP.clear();
    refleshBottlesListZP();
    m_editTextTakeOverUserId.setText("");
  }
  
  private String getResponseMessage(HttpResponse paramHttpResponse) {
    return null;
  }
  
  private void loginOut() {
    SharedPreferencesHelper.put("username", "");
    SharedPreferencesHelper.put("password", "");
    NetRequestConstant netRequestConstant = new NetRequestConstant();
    netRequestConstant.setType(HttpRequestType.GET);
    String str = m_curLoginUserId.getUsername();
    m_curLoginUserId.getPassword();
    netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/sysusers/logout/" + str;
    netRequestConstant.context = (Context)this;
    netRequestConstant.setParams(new HashMap<Object, Object>());
    getServer(new Netcallback() {
          public void preccess(Object param1Object, boolean param1Boolean) {
            if (param1Boolean) {
                HttpResponse response = (HttpResponse) param1Object;
              if (param1Object != null) {
                if (response.getStatusLine().getStatusCode() == 200) {
                  param1Object = new Intent(getApplicationContext(), LoginActivity.class);
                  startActivity((Intent)param1Object);
                  finish();
                  return;
                } 
                Toast.makeText((Context)StockManagerActivity.this, "退出登录失败", Toast.LENGTH_LONG).show();
                return;
              } 
              Toast.makeText((Context)StockManagerActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
              return;
            } 
            Toast.makeText((Context)StockManagerActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
          }
        },netRequestConstant);
  }
  
  private void queryBottleInfo(final String bottleCode, final String nextStatus) {
    NetRequestConstant netRequestConstant = new NetRequestConstant();
    netRequestConstant.setType(HttpRequestType.GET);
    netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/Order/Refound/ByGasNumber";
    netRequestConstant.context = (Context)this;
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("gasNumber", bottleCode);
    netRequestConstant.setParams(hashMap);
    getServer(new Netcallback() {
          public void preccess(Object param1Object, boolean param1Boolean) {
            if (param1Boolean) {
              HttpResponse httpResponse = (HttpResponse)param1Object;
              if (httpResponse != null) {
                StockManagerActivity stockManagerActivity;
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                  try {
                      JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
                      JSONObject jSONObject = jsonObject.getJSONObject("orderTriggerType");
                    String str1 = "";
                    if (jSONObject != null && jSONObject.get("index").toString().equals("1")) {
                      JSONArray jSONArray = jsonObject.getJSONArray("gasRefoundDetailList");
                      boolean bool = false;
                      byte b = 0;
                      while (true) {
                        boolean bool1 = bool;
                        param1Object = str1;
                        if (b < jSONArray.length()) {
                            JSONObject jsonObject1 = jSONArray.getJSONObject(b);
                            if (jsonObject1.getString("gasCynNumber").equals(bottleCode)) {
                            param1Object = jsonObject1.getString("refoundWeight");
                            bool1 = true;
                          } else {
                            b++;
                            continue;
                          } 
                        } 
                        if (bool1) {
                          showBottleInfo(bottleCode, "斤", (String)param1Object, nextStatus);
                          return;
                        } 
                        showToast("信息查询失败");
                        return;
                      } 
                    } 
                    stockManagerActivity = StockManagerActivity.this;
                    String str3 = bottleCode;
                    String str4 = m_takerOverUserId;
                    String str2 = m_curLoginUserId.getUsername();
                    String str5 = nextStatus;
                      StringBuilder stringBuilder = new StringBuilder();
                      stockManagerActivity.bottleTakeOverUnit(str3, str4, str2, str5, stringBuilder.append(m_curLoginUserId.getDepartmentName()).append("|钢瓶入库").toString(), false, true);
                  } catch (IOException iOException) {
                    Toast.makeText((Context)StockManagerActivity.this, "异常" + iOException.toString(), Toast.LENGTH_LONG).show();
                  } catch (JSONException jSONException) {
                    Toast.makeText((Context)StockManagerActivity.this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
                  } 
                  return;
                } 
                if (httpResponse.getStatusLine().getStatusCode() == 404) {
                  showToast("未找到销售行为");
                  bottleTakeOverUnit(bottleCode, m_takerOverUserId, m_curLoginUserId.getUsername(), nextStatus, m_curLoginUserId.getDepartmentName() + "|钢瓶入库", false, true);
                } 
                return;
              } 
              Toast.makeText((Context)StockManagerActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
              return;
            } 
            Toast.makeText((Context)StockManagerActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
          }
        },netRequestConstant);
  }
  
  private boolean readWriteCardDemo(int paramInt) {
    switch (paramInt) {
      default:
        return true;
      case 6:
        break;
    } 
//    Ntag21x ntag21x = (Ntag21x)bleNfcDevice.getCard();
//    if (ntag21x != null) {
//      boolean bool;
//      try {
//        String str = ntag21x.NdefTextRead();
//        Message message = new Message();
//        message.obj = str;
//        message.what = 136;
//        handler.sendMessage(message);
//      } catch (CardNoResponseException cardNoResponseException) {
//        cardNoResponseException.printStackTrace();
//        bool = false;
//      }
//      return bool;
//    }
      return  false;
  }
  
  private void refleshBottlesListZP() {
    m_textViewTotalCountZP.setText(Integer.toString(m_BottlesListZP.size()));
    ArrayList arrayList = new ArrayList();
    for (byte b = 0; b < m_BottlesListZP.size(); b++) {
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      hashMap.put("bottleCode", m_BottlesListZP.get(b));
      arrayList.add(hashMap);
    } //下面这个布局先写上 不确定
    SimpleAdapter simpleAdapter = new SimpleAdapter((Context)this, arrayList, R.layout.bottle_list_items, new String[] { "bottleCode" }, new int[] { R.id.items_spec });
    m_listView_zp.setAdapter((ListAdapter)simpleAdapter);
    setListViewHeightBasedOnChildren(m_listView_zp);
  }
  
  private void searchNearestBleDevice() {
    msgBuffer.delete(0, msgBuffer.length());
    msgBuffer.append("正在搜索设备...");
    handler.sendEmptyMessage(0);
    if (!mScanner.isScanning() && bleNfcDevice.isConnection() == 0)
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
  
  private void showBottleInfo(final String bottleCode, String paramString2, String paramString3, final String nextStatus) {
    final View layout = getLayoutInflater().inflate(R.layout.show_bottle_info, null);
    layout_inputWeight = layout;
    TextView textView1 = (TextView)layout.findViewById(R.id.textView_bottleCode);
    TextView textView2 = (TextView)layout.findViewById(R.id.textView_checkDetail);
    TextView textView3 = (TextView)layout.findViewById(R.id.input_bottleWeight);
    textView1.setText(bottleCode);
    textView2.setText(paramString2);
    textView3.setText(paramString3);
    AlertDialog.Builder builder = (new AlertDialog.Builder((Context)this)).setTitle("返回结果").setIcon(R.drawable.icon_logo).setView(layout).setPositiveButton("确定", null).setNegativeButton("取消", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {}
        }).setNeutralButton("强制", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            Toast.makeText((Context)StockManagerActivity.this, "强制交接！", Toast.LENGTH_LONG).show();
            TextView textView2 = (TextView)layout.findViewById(R.id.textView_checkDetail);//不确定
            TextView textView1 = (TextView)layout.findViewById(R.id.input_bottleWeight);//不确定
            String str2 = textView2.getText().toString();
            String str1 = textView1.getText().toString();
            uploadWeightWarning(bottleCode, m_takerOverUserId, m_curLoginUserId.getUsername(), str1, str2, "6", nextStatus);
            bottleTakeOverUnit(bottleCode, m_takerOverUserId, m_curLoginUserId.getUsername(), nextStatus, m_curLoginUserId.getDepartmentName() + "|钢瓶入库", false, true);
          }
        });
    builder.setCancelable(false);
    final AlertDialog dialog = builder.create();
      dialog.setCanceledOnTouchOutside(false);
      dialog.show();
      dialog.getButton(-1).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            TextView textView2 = (TextView)layout.findViewById(R.id.textView_checkDetail);//不确定
            TextView textView1 = (TextView)layout.findViewById(R.id.input_bottleWeight);//不确定
            String str2 = textView2.getText().toString();
            String str1 = textView1.getText().toString();
            if (str2.length() == 0) {
              Toast.makeText((Context)StockManagerActivity.this, "重量输入有误，请称重！", Toast.LENGTH_LONG).show();
              return;
            } 
            double d = Double.parseDouble(str2);
            if (Math.abs(Double.parseDouble(str1) - d) < 0.5D) {
              bottleTakeOverUnit(bottleCode, m_takerOverUserId, m_curLoginUserId.getUsername(), nextStatus, m_curLoginUserId.getDepartmentName() + "|钢瓶入库", false, true);
              dialog.cancel();
              return;
            } 
            MediaPlayer.create((Context)StockManagerActivity.this, R.raw.alarm).start();
            Toast.makeText(getApplicationContext(), "残气重量报警,误差过大！", Toast.LENGTH_LONG).show();
          }
        });
  }
  
  private void showReadWriteDialog(String paramString, int paramInt) {
    Message message = new Message();
    message.what = 4;
    message.arg1 = paramInt;
    message.obj = paramString;
    handler.sendMessage(message);
  }
  
  private void showScrapBottleInfo(String paramString1, String paramString2) {
    View view = getLayoutInflater().inflate(R.layout.show_scrap_bottle_info, null);
    layout_inputWeight = view;
    TextView textView1 = (TextView)view.findViewById(R.id.textView_bottleCode);
    TextView textView2 = (TextView)view.findViewById(R.id.textView_scrapDate);
    textView1.setText(paramString1);
    textView2.setText(paramString2);
    AlertDialog.Builder builder = (new AlertDialog.Builder((Context)this)).setTitle("报废钢瓶").setIcon(R.drawable.icon_bottle).setView(view).setPositiveButton("确定", null);
    builder.setCancelable(false);
    AlertDialog alertDialog = builder.create();
    alertDialog.setCanceledOnTouchOutside(false);
    alertDialog.show();
  }
  
  private void showToast(String paramString) {
    if (toast == null) {
      toast = Toast.makeText((Context)this, null, Toast.LENGTH_SHORT);
      toast.setGravity(17, 0, 0);
      LinearLayout linearLayout = (LinearLayout)toast.getView();
      WindowManager windowManager = (WindowManager)getSystemService(Service.WINDOW_SERVICE);
      DisplayMetrics displayMetrics = new DisplayMetrics();
      windowManager.getDefaultDisplay().getMetrics(displayMetrics);
      tv = new TextView((Context)this);
      linearLayout.getBackground().setAlpha(0);
      tv.setTextSize(40.0F);
      tv.setTextColor(getResources().getColor(R.color.blue));
      linearLayout.setGravity(17);
      LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
      layoutParams.setMargins(0, 0, 0, 180);
      tv.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
      toast.setView((View)linearLayout);
      linearLayout.addView((View)tv);
    } 
    tv.setText(paramString);
    toast.show();
  }
  
  private boolean startAutoSearchCard() throws DeviceNoResponseException {
    for (byte b = 0;; b++) {
      boolean bool = bleNfcDevice.startAutoSearchCard((byte)20, (byte)2);
      if (bool || b >= 10) {
        if (!bool) {
          msgBuffer.append("不支持自动寻卡！\r\n");
          handler.sendEmptyMessage(0);
        } 
        return bool;
      } 
    } 
  }
  
  private void startScan() {
//    (new IntentIntegrator(this)).setOrientationLocked(false).setCaptureActivity(CustomScanActivity.class).initiateScan();
  }
  
  private void sysUserInfoQuery(String paramString) {
    NetRequestConstant netRequestConstant = new NetRequestConstant();
    netRequestConstant.setType(HttpRequestType.GET);
    netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/sysusers/FindByUserId";
    netRequestConstant.context = (Context)this;
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("userId", paramString);
    netRequestConstant.setParams(hashMap);
    getServer(new Netcallback() {
          public void preccess(Object param1Object, boolean param1Boolean) {
            if (param1Boolean) {
                HttpResponse response = (HttpResponse) param1Object;
              if (param1Object != null) {
                if (response.getStatusLine().getStatusCode() == 200) {
                  try {
                    TextView textView;
                    JSONObject jSONObject1 = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                    JSONObject jSONObject2 = jSONObject1.getJSONObject("userGroup");
                    String str3 = jSONObject2.optString("code");
                    param1Object = jSONObject1.optString("userId");
                    String str1 = jSONObject1.optString("name");
                    String str2 = jSONObject2.optString("name");
//                    StockManagerActivity.access$502(StockManagerActivity.this, str3);
                    if (str3.equals("00007")) {
//                      StockManagerActivity.access$602(StockManagerActivity.this, (String)param1Object);
                      textView = m_editTextTakeOverUserId;
                      StringBuilder stringBuilder = new StringBuilder();
                      textView.setText(stringBuilder.append((String)param1Object).append(" (姓名:").append(str1).append(" | 工作组:").append(str2).append(")").toString());
                      return;
                    } 
                    if (m_editTextTakeOverUserId.equals("00003")) {
//                      StockManagerActivity.access$602(StockManagerActivity.this, (String)param1Object);
                      TextView textView1 = m_editTextTakeOverUserId;
                      StringBuilder stringBuilder = new StringBuilder();
                      textView1.setText(stringBuilder.append((String)param1Object).append(" (姓名:").append(str1).append(" | 工作组:").append(str2).append(")").toString());
                      return;
                    } 
                    Toast.makeText((Context)StockManagerActivity.this, "非配送工或调拨车账户，请更换！", Toast.LENGTH_LONG).show();
                  } catch (IOException iOException) {
                    Toast.makeText((Context)StockManagerActivity.this, "无效账户！", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                  } catch (JSONException jSONException) {
                    Toast.makeText((Context)StockManagerActivity.this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                  } 
                  return;
                } 
                Toast.makeText((Context)StockManagerActivity.this, "请求交接人信息失败", Toast.LENGTH_LONG).show();
                return;
              } 
              Toast.makeText((Context)StockManagerActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
              param1Object = new Intent(getApplicationContext(), LoginActivity.class);
              startActivity((Intent)param1Object);
              finish();
              return;
            } 
            Toast.makeText((Context)StockManagerActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
            param1Object = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity((Intent)param1Object);
            finish();
          }
        },netRequestConstant);
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
          String[] strings = new String[11];
          strings[0] = "十";
          strings[1] = "百";
          strings[2] = "千";
          strings[3] = "万";
          strings[4] = "十";
          strings[5] = "百";
          strings[6] = "千";
          strings[7] = "亿";
          strings[8] = "十";
          strings[9] = "百";
          strings[10] = "千";
          str = str + arrayOfString[j] + strings[i - 2 - b];
      } else {
        str = str + arrayOfString[j];
      } 
    } 
    return str;
  }
  
  public void bottleTakeOverUnit(final String bottleCode, final String srcUserId, final String targetUserId, final String serviceStatus, final String note, boolean paramBoolean1, final boolean isRuKu) {
    if (m_takerOverUserId == null) {
      showToast("请扫码获取交接人信息！");
      return;
    } 
    boolean bool = false;
    byte b = 0;
    while (true) {
      boolean bool1 = bool;
      if (b < m_BottlesListZP.size())
        if (((String)m_BottlesListZP.get(b)).equals(bottleCode)) {
          bool1 = true;
        } else {
          b++;
          continue;
        }  
      if (bool1) {
        showToast("钢瓶号：" + bottleCode + "    请勿重复提交！");
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
                  HttpResponse response = (HttpResponse) param1Object;
                if (param1Object != null) {
                  if (response.getStatusLine().getStatusCode() == 200) {
                    addZP(bottleCode);
                    return;
                  } 
                  MediaPlayer.create((Context)StockManagerActivity.this, R.raw.alarm).start();
                  if (response.getStatusLine().getStatusCode() == 409) {
                    (new AlertDialog.Builder((Context)StockManagerActivity.this)).setTitle("钢瓶异常流转！").setMessage("钢瓶号 :" + bottleCode + "\r\n错误原因:" + getResponseMessage((HttpResponse)param1Object) + "\r\n确认强制交接吗？").setIcon(R.drawable.icon_bottle).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                            if (Tools.isFastClick())
                              bottleTakeOverUnit(bottleCode, srcUserId, targetUserId, serviceStatus, note, true, isRuKu); 
                          }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface param2DialogInterface, int param2Int) {}
                        }).show();
                    return;
                  } 
                  (new AlertDialog.Builder((Context)StockManagerActivity.this)).setTitle("钢瓶异常流转！").setMessage("钢瓶号 :" + bottleCode + "\r\n错误原因:" + getResponseMessage((HttpResponse)param1Object)).setIcon(R.drawable.icon_logo).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface param2DialogInterface, int param2Int) {}
                      }).show();
                  return;
                } 
                Toast.makeText((Context)StockManagerActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                return;
              } 
              Toast.makeText((Context)StockManagerActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
            }
          },netRequestConstant);
      return;
    } 
  }
  
  public void deleteZP(final int position) {
    AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
    builder.setTitle("删除钢瓶");
    builder.setMessage("您确定要删除这只钢瓶吗?");
    builder.setIcon(R.drawable.icon_bottle);
    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            new HashMap<Object, Object>();
            String str = (String)((Map)m_listView_zp.getItemAtPosition(position)).get("bottleCode");
            m_BottlesListZP.remove(str);
            refleshBottlesListZP();
          }
        });
    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {}
        });
    builder.show();
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getKeyCode() == 4 && paramKeyEvent.getRepeatCount() == 0 && paramKeyEvent.getAction() == 0) {
      (new AlertDialog.Builder((Context)this)).setTitle("提示").setMessage("确认退出吗？").setIcon(R.drawable.icon_logo).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
              Process.killProcess(Process.myPid());
            }
          }).setNegativeButton("退出登录", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
              loginOut();
            }
          }).show();
      return false;
    } 
    return super.dispatchKeyEvent(paramKeyEvent);
  }
  
  void init() {
    tts = new TextToSpeech((Context)this, this);
    setContentView(R.layout.activity_stock_manage);
    m_buttonNext = (Button)findViewById(R.id.button_next);
    m_listView_zp = (ListView)findViewById(R.id.listview_zp);
    radioGroup_nfc = (RadioGroup)findViewById(R.id.radioGroup_nfc_id);
    radioButton_kp = (RadioButton)findViewById(R.id.radioButton_kp_id);
    radioButton_zp = (RadioButton)findViewById(R.id.radioButton_zp_id);
    m_bottleIdZPEditText = (EditText)findViewById(R.id.input_bottleIdZP);
    m_imageAddZPManual = (ImageView)findViewById(R.id.imageView_addZPManual);
    m_imageViewScan = (ImageView)findViewById(R.id.imageView_Scan);
    m_textViewTotalCountZP = (TextView)findViewById(R.id.items_totalCountZP);
    msgText_weightDevice = (TextView)findViewById(R.id.msgText_weightDevice);
    m_imageAddZPManual.setOnClickListener(this);
    m_imageViewScan.setOnClickListener(this);
    m_spinnerGPHead = (Spinner)findViewById(R.id.spinner_gp_head);
    m_buttonNext.setOnClickListener(this);
    radioGroup_nfc.setOnCheckedChangeListener(listen);
    radioGroup_nfc.check(radioButton_kp.getId());
    m_BottlesListZP = new ArrayList<String>();
    m_editTextTakeOverUserId = (TextView)findViewById(R.id.input_TakeOverUserId);
    m_textView_Department = (TextView)findViewById(R.id.textView_Department);
    appContext = (AppContext)getApplicationContext();
    m_curLoginUserId = appContext.getUser();
    if (m_curLoginUserId == null) {
      Toast.makeText((Context)this, "登陆会话失效", Toast.LENGTH_LONG).show();
      startActivity(new Intent((Context)this, LoginActivity.class));
      finish();
    } 
    m_listView_zp.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
          public boolean onItemLongClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
            return true;
          }
        });
    m_takerOverUserId = null;
    String str = m_curLoginUserId.getUsername();
    m_textView_Department.setText(str + "(" + m_curLoginUserId.getGroupName() + "|" + m_curLoginUserId.getDepartmentName() + ")");
    blueDeviceInitial();
    scale = new scalerSDK((Context)this);
    handler_weightScale.post(task);
    layout_inputWeight = null;
    m_imageView_search_weightDevice = (ImageView)findViewById(R.id.imageView_search_weightDevice);
    m_imageView_search_weightDevice.setOnClickListener(this);
    m_spinnerGPHead.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
              String[] strs = new String[8];
              strs[0] = "KMA2B";
              strs[1] = "KMA2BA";
              strs[2] = "KMA2BB";
              strs[3] = "KMA2BC";
              strs[4] = "KMA2BD";
              strs[5] = "KMA2BE";
              strs[6] = "KMA2BF";
              strs[7] = "";
            m_gp_code_head =  strs[param1Int];
            SharedPreferencesHelper.put("codeHeadIndex", Integer.valueOf(param1Int));
          }
          
          public void onNothingSelected(AdapterView<?> param1AdapterView) {}
        });
  }
  
  public void isOpenGPS() {
    if (!((LocationManager)getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled("gps")) {
      AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
      builder.setMessage("GPS未打开，本配送程序必须打开!");
      builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
              Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
              startActivityForResult(intent, 0);
            }
          });
      builder.show();
    } 
  }
  
  public void judgeBottleScrap(final String bottleCode, final String nextStatus) {
    NetRequestConstant netRequestConstant = new NetRequestConstant();
    netRequestConstant.setType(HttpRequestType.GET);
    netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/GasCylinder";
    netRequestConstant.context = (Context)this;
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("number", bottleCode);
    netRequestConstant.setParams(hashMap);
    getServer(new Netcallback() {
          public void preccess(Object param1Object, boolean param1Boolean) {
            if (param1Boolean) {
              HttpResponse response = (HttpResponse) param1Object;
              if (param1Object != null) {
                if (response.getStatusLine().getStatusCode() == 200)
                  try {
                    JSONObject jSONObject = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                    JSONObject items = jSONObject.getJSONArray("items").getJSONObject(0);
                    String str1 = items.get("scrapDate").toString();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date2 = simpleDateFormat.parse(str1);
                    Date date1 = new Date();
                    param1Object = items.getJSONObject("serviceStatus");
                    if (date1.after(date2)) {
                      View view = getLayoutInflater().inflate(R.layout.show_scrap_bottle_info, null);
//                      StockManagerActivity.access$3102(StockManagerActivity.this, view);
                      TextView textView1 = (TextView)view.findViewById(R.id.textView_bottleCode);
                      TextView textView2 = (TextView)view.findViewById(R.id.textView_scrapDate);
                      textView1.setText(bottleCode);
                      textView2.setText(str1);
                      AlertDialog.Builder builder = new AlertDialog.Builder(StockManagerActivity.this);
                      builder = builder.setTitle("报废钢瓶").setIcon(R.drawable.icon_bottle).setView(view);
                      DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                          public void onClick(DialogInterface param2DialogInterface, int param2Int) {
                            if (Tools.isFastClick())
                              try {
//                                if (serviceStatusJson.getInt("index") == 6 && nextStatus.equals("2")) {
//                                  queryBottleInfo(bottleCode, nextStatus);
//                                  return;
//                                }
                                StockManagerActivity stockManagerActivity = StockManagerActivity.this;
                                String str1 = bottleCode;
                                String str2 = m_takerOverUserId;
                                String str3 = m_curLoginUserId.getUsername();
                                String str4 = nextStatus;
                                StringBuilder stringBuilder = new StringBuilder();
                                stockManagerActivity.bottleTakeOverUnit(str1, str2, str3, str4, stringBuilder.append(m_curLoginUserId.getDepartmentName()).append("|钢瓶入库").toString(), false, true);
                              } catch (Exception jSONException) {
                                Toast.makeText((Context)StockManagerActivity.this, "查询钢瓶信息异常！", Toast.LENGTH_LONG).show();
                              }
                          }
                        };
//                      super(this, (JSONObject)param1Object);
                      param1Object = builder.setPositiveButton("确定", onClickListener);
//                      param1Object.setCancelable(false);
//                      param1Object = param1Object.create();
//                      param1Object.setCanceledOnTouchOutside(false);
//                      param1Object.show();
                      return;
                    }
//                    if (param1Object.getInt("index") == 6 && nextStatus.equals("2")) {
//                      queryBottleInfo(bottleCode, nextStatus);
//                      return;
//                    }
                    StockManagerActivity stockManagerActivity = StockManagerActivity.this;
                    String str4 = bottleCode;
                    String str2 = m_takerOverUserId;
                    String str3 = m_curLoginUserId.getUsername();
                    String str5 = nextStatus;
                    param1Object = new StringBuilder();
//                    stockManagerActivity.bottleTakeOverUnit(str4, str2, str3, str5, param1Object.append(m_curLoginUserId.getDepartmentName()).append("|钢瓶入库").toString(), false, true);
                  } catch (IOException iOException) {
                    Toast.makeText((Context)StockManagerActivity.this, "查询钢瓶信息异常！", Toast.LENGTH_LONG).show();
                  } catch (JSONException jSONException) {
                    Toast.makeText((Context)StockManagerActivity.this, "查询钢瓶信息异常！", Toast.LENGTH_LONG).show();
                  } catch (ParseException parseException) {
                    Toast.makeText((Context)StockManagerActivity.this, "日期构造异常！", Toast.LENGTH_LONG).show();
                  }
                return;
              } 
              Toast.makeText((Context)StockManagerActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
              return;
            } 
            Toast.makeText((Context)StockManagerActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
          }
        },netRequestConstant);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
//    IntentResult intentResult = IntentIntegrator.parseActivityResult(paramInt1, paramInt2, paramIntent);
//    if (intentResult != null) {
//      if (intentResult.getContents() == null) {
//        Toast.makeText((Context)this, "内容为空", Toast.LENGTH_LONG).show();
//        return;
//      }
//      sysUserInfoQuery(intentResult.getContents());
//      return;
//    }
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onClick(View paramView) {
    String str;
    switch (paramView.getId()) {
      default:
        return;
      case 2131230819:
//        if (scale != null && scale.getDevicelist().size() == 1)
//          scale.bleSend(scale.getDevicelist().get(0), (byte)1);
      case R.id.button_next:
        cleanAll();
        break;
      case 2131230811:
        if (m_takerOverUserId == null)
          showToast("请扫码获取交接人信息！"); 
        str = m_gp_code_head + m_bottleIdZPEditText.getText().toString();
        new String();
        if (m_selected_nfc_model == 0) {
          String str1;
          if (m_curLoginUserId.getGroupCode().equals("00005")) {
            str1 = "2";
          } else if (m_curLoginUserId.getGroupCode().equals("00006")) {
            str1 = "1";
          } else {
            Toast.makeText((Context)this, "非充气站或门店账户，请退出！", Toast.LENGTH_LONG).show();
          } 
//          judgeBottleScrap(str, str1);
          m_bottleIdZPEditText.setText("");
        } 
        if (m_selected_nfc_model == 1) {
          String str1 = "";
          if (m_curLoginUserId.getGroupCode().equals("00005")) {
            if (m_takeOverGroupCode.equals("00007")) {
              str1 = "3";
            } else if (m_takeOverGroupCode.equals("00003")) {
              str1 = "4";
            } else {
              Toast.makeText((Context)this, "非配送工或调拨车账户，请更换！", Toast.LENGTH_LONG).show();
            } 
          } else if (m_curLoginUserId.getGroupCode().equals("00006")) {
            str1 = "3";
          } else {
            Toast.makeText((Context)this, "非充气站或门店账户，请退出！", Toast.LENGTH_LONG).show();
          } 
          bottleTakeOverUnit(str, m_curLoginUserId.getUsername(), m_takerOverUserId, str1, m_curLoginUserId.getDepartmentName() + "|钢瓶出库", false, false);
          m_bottleIdZPEditText.setText("");
        } 
      case 2131230808:
        break;
    } 
    startScan();
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
  
  public void onInit(int paramInt) {
    if (paramInt == 0) {
      paramInt = tts.setLanguage(Locale.CHINESE);
      if (paramInt != -1 && paramInt != -2)
        tts.setLanguage(Locale.US); 
    } 
  }
  
  protected void onResume() {
    super.onResume();
    if (mBleNfcDeviceService != null) {
      mBleNfcDeviceService.setScannerCallback(scannerCallback);
      mBleNfcDeviceService.setDeviceManagerCallback(deviceManagerCallback);
    } 
  }
  
  public void uploadWeightWarning(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7) {
    NetRequestConstant netRequestConstant = new NetRequestConstant();
    netRequestConstant.setType(HttpRequestType.POST);
    netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/GasCylinder/ForceOpWeightWarn";
    netRequestConstant.context = (Context)this;
    HashMap<Object, Object> hashMap1 = new HashMap<Object, Object>();
    HashMap<Object, Object> hashMap2 = new HashMap<Object, Object>();
    hashMap2.put("gasCynNumber", paramString1);
    hashMap2.put("srcWeight", paramString4);
    hashMap2.put("nowWeight", paramString5);
    hashMap2.put("srcUserId", paramString3);
    hashMap2.put("dstUserId", paramString2);
    hashMap2.put("srcCynServiceStatus", paramString6);
    hashMap2.put("dstCynServiceStatus", paramString7);
    netRequestConstant.setParams(hashMap1);
    netRequestConstant.setBody(hashMap2);
    getServer(new Netcallback() {
          public void preccess(Object param1Object, boolean param1Boolean) {
            if (param1Boolean) {
              if ((HttpResponse)param1Object == null)
                Toast.makeText((Context)StockManagerActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
              return;
            } 
            Toast.makeText((Context)StockManagerActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
          }
        },netRequestConstant);
  }
  
  private class StartSearchButtonListener implements View.OnClickListener {
    private StartSearchButtonListener() {}
    
    public void onClick(View param1View) {
      if (bleNfcDevice.isConnection() == 2) {
        bleNfcDevice.requestDisConnectDevice();
        return;
      } 
      searchNearestBleDevice();
    }
  }
}
