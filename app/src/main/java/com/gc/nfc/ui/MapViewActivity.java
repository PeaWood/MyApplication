package com.gc.nfc.ui;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
//import com.amap.api.maps2d.AMap;
//import com.amap.api.maps2d.MapView;
//import com.amap.api.maps2d.MapsInitializer;
//import com.amap.api.maps2d.UiSettings;
//import com.amap.api.maps2d.model.BitmapDescriptorFactory;
//import com.amap.api.maps2d.model.CameraPosition;
//import com.amap.api.maps2d.model.LatLng;
//import com.amap.api.maps2d.model.MarkerOptions;
//import com.amap.api.maps2d.model.MyLocationStyle;
import com.gc.nfc.common.NetRequestConstant;
import com.gc.nfc.interfaces.Netcallback;
import java.io.IOException;
import java.util.HashMap;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class MapViewActivity extends BaseActivity {
//  private AMap aMap;
  
  Handler mHandler = new Handler();

//  private UiSettings mUiSettings;

//  private MapView mapView;
  
  Runnable r = new Runnable() {
      public void run() {
        MapViewActivity.this.get_ps_location();
        MapViewActivity.this.mHandler.postDelayed(this, 5000L);
      }
    };
  
  private void get_ps_location() {
    NetRequestConstant netRequestConstant = new NetRequestConstant();
    netRequestConstant.setType(HttpRequestType.GET);
    netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/sysusers";
    netRequestConstant.context = (Context)this;
    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
    hashMap.put("aliveStatus", "1");
    hashMap.put("groupCode", "00003");
    hashMap.put("pageSize", "150");
    hashMap.put("pageNo", "1");
//    netRequestConstant.setParams(hashMap);
//    getServer(new Netcallback() {
//          public void preccess(Object param1Object, boolean param1Boolean) {
//            if (param1Boolean) {
//              param1Object = param1Object;
//              if (param1Object != null) {
//                if (param1Object.getStatusLine().getStatusCode() == 200) {
//                  MapViewActivity.this.clean_marksers();
//                  try {
//                    JSONObject jSONObject = new JSONObject();
//                    this(EntityUtils.toString(param1Object.getEntity(), "UTF-8"));
//                    param1Object = jSONObject.getJSONArray("items");
//                    for (byte b = 0; b < param1Object.length(); b++) {
//                      String str1 = param1Object.getJSONObject(b).getString("userId");
//                      String str2 = param1Object.getJSONObject(b).getString("name");
//                      JSONObject jSONObject1 = param1Object.getJSONObject(b).getJSONObject("userPosition");
//                      double d1 = jSONObject1.getDouble("longitude");
//                      double d2 = jSONObject1.getDouble("latitude");
//                      MapViewActivity.this.reflesh_makers(str1, str2, Double.valueOf(d1), Double.valueOf(d2));
//                    }
//                  } catch (JSONException jSONException) {
//
//                  } catch (IOException iOException) {
//                    Toast.makeText((Context)MapViewActivity.this, "异常" + iOException.toString(), 1).show();
//                  }
//                  return;
//                }
//                Toast.makeText((Context)MapViewActivity.this, "查询配送工位置失败", 1).show();
//                return;
//              }
//              Toast.makeText((Context)MapViewActivity.this, "请求超时，请检查网络", 1).show();
//              return;
//            }
//            Toast.makeText((Context)MapViewActivity.this, "网络未连接！", 1).show();
//          }
//        }netRequestConstant);
  }
  
  private void setUpMap() {
//    this.aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
//          public void onCameraChange(CameraPosition param1CameraPosition) {}
//
//          public void onCameraChangeFinish(CameraPosition param1CameraPosition) {}
//        });
  }
  
  protected void clean_marksers() {

//    this.aMap.clear();
  }
  
  public void init() {}
  
  public void mapinit() {
//    if (this.aMap == null) {
//      this.aMap = this.mapView.getMap();
//      setUpMap();
//      MyLocationStyle myLocationStyle = new MyLocationStyle();
//      myLocationStyle.interval(2000L);
//      this.aMap.setMyLocationStyle(myLocationStyle);
//      this.aMap.setMyLocationEnabled(true);
//      this.mHandler.postDelayed(this.r, 1000L);
//      this.mUiSettings = this.aMap.getUiSettings();
//      this.mUiSettings.setMyLocationButtonEnabled(true);
//      this.aMap.setMyLocationEnabled(true);
//    }
  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
//    setContentView(2131361830);
//    this.mapView = (MapView)findViewById(2131230947);
//    this.mapView.onCreate(paramBundle);
//    mapinit();
//    MapsInitializer.loadWorldGridMap(true);
  }
  
  protected void onDestroy() {
    super.onDestroy();
//    this.mapView.onDestroy();
  }
  
  protected void onPause() {
    super.onPause();
//    this.mapView.onPause();
  }
  
  protected void onResume() {
    super.onResume();
//    this.mapView.onResume();
  }
  
  protected void onSaveInstanceState(Bundle paramBundle) {
    super.onSaveInstanceState(paramBundle);
//    this.mapView.onSaveInstanceState(paramBundle);
  }
  
  protected void reflesh_makers(String paramString1, String paramString2, Double paramDouble1, Double paramDouble2) {
//    MarkerOptions markerOptions = new MarkerOptions();
//    markerOptions.position(new LatLng(paramDouble2.doubleValue(), paramDouble1.doubleValue()));
//    markerOptions.title(paramString1).snippet(paramString2);
//    markerOptions.draggable(false);
//    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), 2131165515)));
//    this.aMap.addMarker(markerOptions).setVisible(true);
  }
}


/* Location:              D:\智慧配送\classes2-dex2jar.jar!\com\gc\nf\\ui\MapViewActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */