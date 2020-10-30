package com.gc.nfc.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.se.omapi.Session;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.amap.api.maps.model.LatLng;
import com.bumptech.glide.Glide;
import com.gc.nfc.R;
import com.gc.nfc.app.AppContext;
import com.gc.nfc.databinding.ActivitySignBinding;
import com.gc.nfc.domain.User;
import com.gc.nfc.utils.NetUtil;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PicSignActivity extends AppCompatActivity {
  public static String m_orderPayStatus;
  
  public static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "qm.png";
  
  public static String path1 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "ls.png";
  
  public static String picPath;
  
  private String SpecMap;
  
  private AppContext appContext;
  
  ActivitySignBinding binding;
  
  private List<Map<String, String>> list_bottle = new ArrayList<Map<String, String>>();
  
  private Map<String, String> m_BottlesMapKPL;
  
  private Map<String, String> m_BottlesMapZPL;
  
  private Map<String, String> m_BottlesSpecMap;
  
  private JSONObject m_OrderJson;
  
  private String m_businessKey;
  
  private Button m_buttonNext;
  
  private String m_currentCustomerId;
  
  private String m_customerPhone;
  
  private String m_fileFolder;
  
  private String m_fileNameHeader;
  
  private ImageView m_imageViewCall;
  
  private ImageView m_imageViewNav;
  
  private ImageView m_imageViewUserIcon;
  
  private boolean m_isTicketUser;
  
  private ListView m_listView;
  
  private ListView m_listView_kp;
  
  private ListView m_listView_yj;
  
  private ListView m_listView_zp;
  
  private String m_orderCreateTime;
  
  private String m_orderId;
  
  private int m_orderStatus;
  
  private String m_recvAddr;
  
  private LatLng m_recvLocation;
  
  private String m_taskId;
  
  private TextView m_textViewAddress;
  
  private TextView m_textViewCallNumber;
  
  private TextView m_textViewCreateTime;
  
  private TextView m_textViewOrderSn;
  
  private TextView m_textViewOrderStatus;
  
  private TextView m_textViewPassedTime;
  
  private TextView m_textViewPayStatus;
  
  private TextView m_textViewPayTypeInfo;
  
  private TextView m_textViewPs;
  
  private TextView m_textViewReserveTime;
  
  private TextView m_textViewTotalAcount;
  
  private TextView m_textViewTotalMountDeal;
  
  private TextView m_textViewTotalMountDealGp;
  
  private TextView m_textViewTotalMountOrignal;
  
  private TextView m_textViewTotalQuantity;
  
  private TextView m_textViewTotalQuantityGp;
  
  private TextView m_textViewUserId;
  
  private TextView m_textViewUserPhone;
  
  private String m_totalFee;
  
  private User m_user;
  
  private String m_yjss;
  
  private Timer timer;
  private static String sessionid;

  private static String generateFileName() {
    return UUID.randomUUID().toString();
  }
  
  public static Bitmap getBitmapByView(ScrollView paramScrollView) {
    int i = 0;
    for (byte b = 0; b < paramScrollView.getChildCount(); b++)
      i += paramScrollView.getChildAt(b).getHeight(); 
    Log.d("TAG", "实际高度:" + i);
    Log.d("TAG", " 高度:" + paramScrollView.getHeight());
    Bitmap bitmap = Bitmap.createBitmap(paramScrollView.getWidth(), i, Bitmap.Config.ARGB_8888);
    paramScrollView.draw(new Canvas(bitmap));
    ScrollView scrollView = null;
    try {
      String str1 = generateFileName();
      StringBuilder stringBuilder = new StringBuilder();
      String str2 = stringBuilder.append("/sdcard/").append(str1).append(".png").toString();
      picPath = str2;
      FileOutputStream fileOutputStream = new FileOutputStream(str2);
      if (fileOutputStream != null){
        paramScrollView = scrollView;
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
      }
    } catch (Exception iOException) {
      iOException.printStackTrace();
    }
    return bitmap;
  }
  
  private void refleshBottlesListKP() {
    this.binding.itemsTotalCountKP.setText(Integer.toString(this.m_BottlesMapKPL.size()));
    ArrayList arrayList = new ArrayList();
    for (Map.Entry<String, String> entry : this.m_BottlesMapKPL.entrySet()) {
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      hashMap.put("bottleSpec", getSpecName(getBottleSpec((String)entry.getKey())));
      hashMap.put("bottleCode", entry.getKey());
      hashMap.put("bottleWeight", (String)entry.getValue() + "公斤");
      arrayList.add(hashMap);
    } 
    SimpleAdapter simpleAdapter = new SimpleAdapter((Context)this, arrayList, R.layout.bottle_list_simple_items, new String[] { "bottleCode", "bottleWeight", "bottleSpec" }, new int[] { R.id.items_number, R.id.items_weight, R.id.items_spec });
    this.m_listView_kp.setAdapter((ListAdapter)simpleAdapter);
    setListViewHeightBasedOnChildren(this.m_listView_kp);
  }
  
  private void refleshBottlesListYJ() {
    int i = 0;
    int j = 0;
    ArrayList arrayList = new ArrayList();
    int k;
    for (k = 0; k < this.list_bottle.size(); k++) {
      new HashMap<Object, Object>();
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      Map map = this.list_bottle.get(k);
      hashMap.put("bottleType", map.get("bottleType"));
      hashMap.put("quantity", "X" + (String)map.get("quantity"));
      hashMap.put("price", "￥" + (String)map.get("price"));
      int m = Integer.parseInt((String)map.get("quantity"));
      int n = Integer.parseInt((String)map.get("price"));
      i += m;
      j += n;
      arrayList.add(hashMap);
    } 
    this.m_textViewTotalQuantityGp.setText("X" + String.valueOf(i));
    this.m_textViewTotalMountDealGp.setText("￥" + String.valueOf(j));
    try {
      k = Integer.parseInt(this.m_OrderJson.getString("orderAmount"));
      TextView textView = this.m_textViewTotalAcount;
      StringBuilder stringBuilder = new StringBuilder();
      textView.setText(stringBuilder.append("￥").append(k + j).toString());
    } catch (JSONException jSONException) {
      Toast.makeText((Context)this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
    } 
    SimpleAdapter simpleAdapter = new SimpleAdapter((Context)this, arrayList, R.layout.bottle_detail_items, new String[] { "bottleType", "quantity", "price" }, new int[] { R.id.items_goodName, R.id.items_goodQuantity, R.id.items_dealPrice });
    this.m_listView_yj.setAdapter((ListAdapter)simpleAdapter);
    setListViewHeightBasedOnChildren(this.m_listView_yj);
  }
  
  private void refleshBottlesListZP() {
    this.binding.itemsTotalCountZP.setText(Integer.toString(this.m_BottlesMapZPL.size()));
    ArrayList arrayList = new ArrayList();
    for (Map.Entry<String, String> entry : this.m_BottlesMapZPL.entrySet()) {
      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
      if (getBottleSpec((String)entry.getKey()) == null) {
        hashMap.put("bottleSpec", "未知");
      } else {
        hashMap.put("bottleSpec", getSpecName(getBottleSpec((String)entry.getKey())));
      } 
      hashMap.put("bottleCode", entry.getKey());
      hashMap.put("bottleWeight", (String)entry.getValue() + "公斤");
      arrayList.add(hashMap);
    } 
    SimpleAdapter simpleAdapter = new SimpleAdapter((Context)this, arrayList, R.layout.bottle_list_simple_items, new String[] { "bottleCode", "bottleWeight", "bottleSpec" }, new int[] {R.id.items_number, R.id.items_weight, R.id.items_spec});
    this.m_listView_zp.setAdapter((ListAdapter)simpleAdapter);
    setListViewHeightBasedOnChildren(this.m_listView_zp);
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
  
  private void setOrderDetailsInfo() {
    int i = 0;
    try {
      TextView textView2 = this.binding.itemsTotalMountOrignal;
      StringBuilder stringBuilder1 = new StringBuilder();
      textView2.setText(stringBuilder1.append("￥").append(this.m_OrderJson.getString("originalAmount")).toString());
      textView2 = this.binding.itemsTotalMountDeal;
      stringBuilder1 = new StringBuilder();
      textView2.setText(stringBuilder1.append("￥").append(this.m_OrderJson.getString("orderAmount")).toString());
      JSONArray jSONArray = this.m_OrderJson.getJSONArray("orderDetailList");
      ArrayList arrayList = new ArrayList();
      for (byte b = 0; b < jSONArray.length(); b++) {
        JSONObject jSONObject1 = jSONArray.getJSONObject(b);
        JSONObject jSONObject2 = jSONObject1.getJSONObject("goods");
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        hashMap.put("goodName", jSONObject2.get("name").toString());
        StringBuilder stringBuilder = new StringBuilder();
        hashMap.put("goodQuantity", stringBuilder.append("X").append(jSONObject1.get("quantity").toString()).toString());
        stringBuilder = new StringBuilder();
        hashMap.put("orignalPrice", stringBuilder.append("￥").append(jSONObject1.get("originalPrice").toString()).toString());
        stringBuilder = new StringBuilder();
        hashMap.put("dealPrice", stringBuilder.append("￥").append(jSONObject1.get("dealPrice").toString()).toString());
        i += Integer.parseInt(jSONObject1.get("quantity").toString());
        arrayList.add(hashMap);
      } 
      TextView textView1 = this.binding.itemsTotalQuantity;
      StringBuilder stringBuilder2 = new StringBuilder();
      textView1.setText(stringBuilder2.append("X").append(Integer.toString(i)).toString());
      SimpleAdapter simpleAdapter = new SimpleAdapter((Context)this, arrayList, R.layout.order_detail_items, new String[] { "goodName", "goodQuantity", "orignalPrice", "dealPrice" }, new int[] {R.id.items_goodName, R.id.items_goodQuantity,R.id.items_orignalPrice, R.id.items_dealPrice  });
      this.m_listView.setAdapter((ListAdapter)simpleAdapter);
      setListViewHeightBasedOnChildren(this.m_listView);
    } catch (JSONException jSONException) {
      Toast.makeText((Context)this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
    } 
  }
  
  private void setOrderHeadInfo() {
    try {
      JSONObject jSONObject1 = this.m_OrderJson;
      StringBuilder stringBuilder3 = new StringBuilder();
      String str2 = stringBuilder3.append("订单编号：").append(jSONObject1.get("orderSn").toString()).toString();
      this.binding.itemsOrderSn.setText(str2);
      StringBuilder stringBuilder2 = new StringBuilder();
      String str1 = stringBuilder2.append("下单时间：").append(jSONObject1.get("createTime").toString()).toString();
      this.binding.itemsCreatTime.setText(str1);
      JSONObject jSONObject2 = jSONObject1.getJSONObject("customer");
      String str3 = jSONObject1.get("recvName").toString();
      StringBuilder stringBuilder4 = new StringBuilder();
      String str4 = stringBuilder4.append("电话：").append(jSONObject1.get("recvPhone").toString()).toString();
      this.binding.itemsUserId.setText(str3);
      this.binding.itemsUserPhone.setText(str4);
      str3 = jSONObject2.getJSONObject("customerCompany").get("name").toString();
      JSONObject jSONObject3 = jSONObject1.getJSONObject("recvAddr");
      StringBuilder stringBuilder1 = new StringBuilder();
      String str5 = stringBuilder1.append(jSONObject3.get("city").toString()).append(jSONObject3.get("county").toString()).append(jSONObject3.get("detail").toString()).toString();
      if (str3.equals("无")) {
        this.binding.itemsAddress.setText(str5);
      } else {
        TextView textView = this.binding.itemsAddress;
        StringBuilder stringBuilder = new StringBuilder();
        textView.setText(stringBuilder.append(str5).append(" (").append(str3).append(")").toString());
      } 
      this.m_recvAddr = str5;
      jSONObject2 = jSONObject2.getJSONObject("settlementType");
      if (jSONObject2.get("code").toString().equals("00003")) {
        this.binding.itemsImageUserIcon.setImageResource(R.mipmap.icon_ticket_user_white);
        this.m_isTicketUser = true;
        return;
      }
      if (jSONObject2.get("code").toString().equals("00002")) {
        this.binding.itemsImageUserIcon.setImageResource(R.mipmap.icon_month_user_white);
        this.m_isTicketUser = false;
        return;
      }
    } catch (JSONException jSONException) {
      Toast.makeText((Context)this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
      return;
    }
    this.binding.itemsImageUserIcon.setImageResource(R.mipmap.icon_common_user_white);
    this.m_isTicketUser = false;
  }
  
  public static boolean uploadFile(String[] paramArrayOfString, String paramString1, String paramString2) {
    String str = UUID.randomUUID().toString();
    try {
      StringBuilder stringBuilder4 = new StringBuilder();
      URL uRL = new URL(stringBuilder4.append("http://www.gasmart.com.cn/api/Untils/file?fileFolder=").append(paramString1).append("&fileNameHeader=").append(paramString2).toString());
      HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
      if (NetUtil.m_loginCookies != null) {
        StringBuilder stringBuilder = new StringBuilder();
        httpURLConnection.setRequestProperty("Cookie", stringBuilder.append("JSESSIONID=").append(sessionid).toString());
      }
      httpURLConnection.setReadTimeout(100000000);
      httpURLConnection.setConnectTimeout(100000000);
      httpURLConnection.setDoInput(true);
      httpURLConnection.setDoOutput(true);
      httpURLConnection.setUseCaches(false);
      httpURLConnection.setRequestMethod("PUT");
      httpURLConnection.setRequestProperty("Charset", "utf-8");
      httpURLConnection.setRequestProperty("connection", "keep-alive");
      StringBuilder stringBuilder2 = new StringBuilder();
      httpURLConnection.setRequestProperty("Content-Type", stringBuilder2.append("multipart/form-data").append(";boundary=").append(str).toString());
      OutputStream outputStream = httpURLConnection.getOutputStream();
      DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
      StringBuffer stringBuffer = new StringBuffer();
      stringBuffer.append("--");
      stringBuffer.append(str);
      stringBuffer.append("\r\n");
      dataOutputStream.write(stringBuffer.toString().getBytes());
      StringBuilder stringBuilder3 = new StringBuilder();
      dataOutputStream.writeBytes(stringBuilder3.append("Content-Disposition: form-data; name=\"").append("userName").append("\"").append("\r\n").toString());
      dataOutputStream.writeBytes("\r\n");
      dataOutputStream.writeBytes("zhangSan");
      dataOutputStream.writeBytes("\r\n");
      int i = 0;
      while (i < paramArrayOfString.length) {
        File file = new File(paramArrayOfString[i]);
        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append("--");
        stringBuffer1.append(str);
        stringBuffer1.append("\r\n");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuffer1.append(stringBuilder.append("Content-Disposition: form-data; name=\"").append(i).append("\"; filename=\"").append(file.getName()).append("\"").append("\r\n").toString());
        stringBuilder = new StringBuilder();
        stringBuffer1.append(stringBuilder.append("Content-Type: application/octet-stream; charset=").append("utf-8").append("\r\n").toString());
        stringBuffer1.append("\r\n");
        dataOutputStream.write(stringBuffer1.toString().getBytes());
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] arrayOfByte = new byte[5242880];
        while (true) {
            int j = fileInputStream.read(arrayOfByte);
            if (j != -1) {
                dataOutputStream.write(arrayOfByte, 0, j);
            }else{
                fileInputStream.close();
                dataOutputStream.write("\r\n".getBytes());
                i++;
                break;
            }
        }
      } 
      StringBuilder stringBuilder1 = new StringBuilder();
      dataOutputStream.write(stringBuilder1.append("--").append(str).append("--").append("\r\n").toString().getBytes());
      dataOutputStream.flush();
      i = httpURLConnection.getResponseCode();
      if (i == 200)
        return true; 
    } catch (MalformedURLException malformedURLException) {
      malformedURLException.printStackTrace();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    return false;
  }
  
  public String getBottleSpec(String paramString) {
    return this.m_BottlesSpecMap.containsKey(paramString) ? this.m_BottlesSpecMap.get(paramString) : null;
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
    Toast.makeText((Context)this, "未知钢瓶规格" + paramString, Toast.LENGTH_SHORT).show();
    return null;
  }
  
  public void init() {
    try {
      Bundle bundle = getIntent().getExtras();
      String str2 = bundle.getString("order");
      JSONObject jSONObject = new JSONObject(str2);
      this.m_OrderJson = jSONObject;
      this.m_fileFolder = bundle.getString("fileFolder");
      this.m_fileNameHeader = bundle.getString("fileNameHeader");
      m_orderPayStatus = bundle.getString("m_orderPayStatus");
      this.SpecMap = bundle.getString("SpecMap");
      this.list_bottle = (List<Map<String, String>>)bundle.getSerializable("MapList");
      String str4 = bundle.getString("KPCode");
      String str1 = bundle.getString("ZPCode");
      TypeReference<HashMap<String, String>> typeReference2 = new TypeReference<HashMap<String, String>>() {
        };
      this.m_BottlesMapKPL = (Map<String, String>)JSON.parseObject(str4, typeReference2, new com.alibaba.fastjson.parser.Feature[0]);
      TypeReference<HashMap<String, String>> typeReference3 = new TypeReference<HashMap<String, String>>() {

        };
      this.m_BottlesMapZPL = (Map<String, String>)JSON.parseObject(str1, typeReference3, new com.alibaba.fastjson.parser.Feature[0]);
      String str3 = this.SpecMap;
      TypeReference<HashMap<String, String>> typeReference1 = new TypeReference<HashMap<String, String>>() {

        };
      this.m_BottlesSpecMap = (Map<String, String>)JSON.parseObject(str3, typeReference1, new com.alibaba.fastjson.parser.Feature[0]);
      setOrderDetailsInfo();
      setOrderHeadInfo();
      refleshBottlesListKP();
      refleshBottlesListZP();
      refleshBottlesListYJ();
    } catch (JSONException jSONException) {
      Toast.makeText((Context)this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
    } 
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
    if (paramInt2 == 100) {
      BitmapFactory.Options options = new BitmapFactory.Options();
      options.inSampleSize = 2;
      Bitmap bitmap = BitmapFactory.decodeFile(path, options);
      this.binding.img1.setImageBitmap(bitmap);
      this.binding.btnUpload.setEnabled(true);
      return;
    } 
    if (paramInt2 == 101)
      Glide.with(this).load(path1 + ".sign").into(this.binding.img1);
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    this.binding = ActivitySignBinding.inflate(getLayoutInflater());
    setContentView(this.binding.getRoot());
    this.binding.btn1.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            PicSignActivity.this.startActivityForResult(new Intent((Context)PicSignActivity.this, QianZiActivity.class), 1);
          }
        });
    if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_DENIED)
      requestPermissions(new String[] { "android.permission.WRITE_EXTERNAL_STORAGE" }, 1365); 
    this.m_textViewTotalQuantity = (TextView)findViewById(R.id.items_totalQuantity);
    this.m_textViewTotalMountDealGp = (TextView)findViewById(R.id.items_totalMountDealGp);
    this.m_textViewTotalMountDeal = (TextView)findViewById(R.id.items_totalMountDealGp);
    this.m_textViewTotalAcount = (TextView)findViewById(R.id.items_totalAcount);
    this.m_textViewTotalQuantityGp = (TextView)findViewById(R.id.items_totalQuantityGp);
    this.m_textViewTotalMountOrignal = (TextView)findViewById(R.id.items_totalMountOrignal);
    this.m_listView = (ListView)findViewById(R.id.listview);
    this.m_listView_kp = (ListView)findViewById(R.id.listview_kp);
    this.m_listView_zp = (ListView)findViewById(R.id.listview_zp);
    this.m_listView_yj = (ListView)findViewById(R.id.yjlistview);
    this.binding.btnUpload.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            PicSignActivity.getBitmapByView(PicSignActivity.this.binding.llyview);
            String str1 = PicSignActivity.picPath;
            String str2 = PicSignActivity.this.m_fileFolder;
            String str3 = PicSignActivity.this.m_fileNameHeader;
            if (PicSignActivity.uploadFile(new String[] { str1 }, str2, str3)) {
              PicSignActivity.this.binding.textviewPicUploadResult.setText("上传成功");
              Toast.makeText((Context)PicSignActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
              Intent intent = new Intent();
              intent.putExtra("backup", true);
              PicSignActivity.this.setResult(101, intent);
              PicSignActivity.this.finish();
              return;
            } 
            PicSignActivity.this.binding.textviewPicUploadResult.setText("上传失败，请重新上传");
            Toast.makeText((Context)PicSignActivity.this, "上传失败，请重新上传", Toast.LENGTH_SHORT).show();
          }
        });
    init();
    this.binding.btn1.setEnabled(false);
    this.binding.btnUpload.setEnabled(false);
    this.binding.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          public void onCheckedChanged(CompoundButton param1CompoundButton, boolean param1Boolean) {
            if (param1Boolean) {
              PicSignActivity.this.binding.btn1.setClickable(true);
              PicSignActivity.this.binding.btn1.setEnabled(true);
              return;
            } 
            PicSignActivity.this.binding.btn1.setClickable(false);
            PicSignActivity.this.binding.btn1.setEnabled(false);
          }
        });
    SharedPreferences share = getSharedPreferences("Session",Context.MODE_PRIVATE);
    sessionid= share.getString("sessionid","null");
  }
  
  public void onRequestPermissionsResult(int paramInt, @NonNull String[] paramArrayOfString, @NonNull int[] paramArrayOfint) {
    super.onRequestPermissionsResult(paramInt, paramArrayOfString, paramArrayOfint);
    if (paramArrayOfint[0] == -1)
      Toast.makeText((Context)this, "您拒绝了读写存储权限！", Toast.LENGTH_SHORT).show();
  }
}

