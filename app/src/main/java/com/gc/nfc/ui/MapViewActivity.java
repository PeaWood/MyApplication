package com.gc.nfc.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.MapsInitializer;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.gc.nfc.R;
import com.gc.nfc.domain.Data_SysUsers;
import com.gc.nfc.http.Logger;
import com.gc.nfc.http.OkHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class MapViewActivity extends BaseActivity {
    private AMap aMap;

    Handler mHandler = new Handler();

    private UiSettings mUiSettings;

    private MapView mapView;
    private ImageView imageView;
    Runnable r = new Runnable() {
        public void run() {
            MapViewActivity.this.get_ps_location();
            MapViewActivity.this.mHandler.postDelayed(this, 5000L);
        }
    };

    private void get_ps_location() {
        Map<String, String> hashMap = new HashMap();
        hashMap.put("aliveStatus", "1");
        hashMap.put("groupCode", "00003");
        hashMap.put("pageSize", "150");
        hashMap.put("pageNo", "1");
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.GET(OkHttpUtil.URL + "/sysusers/", hashMap, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(MapViewActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() != 200) {
                    Toast.makeText(MapViewActivity.this, "查询配送工位置失败", Toast.LENGTH_LONG).show();
                    return;
                }
                String string = response.body().string();
                Logger.e("get_ps_location: " + string);
                MapViewActivity.this.clean_marksers();
                Gson gson = new Gson();
                Data_SysUsers data_sysUsers = gson.fromJson(string, Data_SysUsers.class);
                for (byte b = 0; b < data_sysUsers.getItems().size(); b++) {
                    Data_SysUsers.ItemsBean itemsBean = data_sysUsers.getItems().get(b);
                    String str1 = itemsBean.getUserId();
                    String str2 = itemsBean.getName();
                    Data_SysUsers.ItemsBean.UserPositionBean userPosition = itemsBean.getUserPosition();
                    if (userPosition != null) {
                        double d1 = userPosition.getLongitude();
                        double d2 = userPosition.getLatitude();
                        MapViewActivity.this.reflesh_makers(str1, str2, d1, d2);
                    }
                }
            }
        });
    }

    private void setUpMap() {
        this.aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            public void onCameraChange(CameraPosition param1CameraPosition) {
            }

            public void onCameraChangeFinish(CameraPosition param1CameraPosition) {
            }
        });
    }

    protected void clean_marksers() {
        this.aMap.clear();
    }

    public void init() {
    }

    public void mapinit() {
        if (this.aMap == null) {
            this.aMap = this.mapView.getMap();
            setUpMap();
            MyLocationStyle myLocationStyle = new MyLocationStyle();
            myLocationStyle.interval(2000L);
            this.aMap.setMyLocationStyle(myLocationStyle);
            this.aMap.setMyLocationEnabled(true);
            this.mHandler.postDelayed(this.r, 1000L);
            this.mUiSettings = this.aMap.getUiSettings();
            this.mUiSettings.setMyLocationButtonEnabled(true);
            this.aMap.setMyLocationEnabled(true);
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_map_view);
        this.mapView = (MapView) findViewById(R.id.map);
        imageView = (ImageView) findViewById(R.id.img_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        this.mapView.onCreate(paramBundle);
        mapinit();
        MapsInitializer.loadWorldGridMap(true);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.mapView.onDestroy();
    }

    protected void onPause() {
        super.onPause();
        this.mapView.onPause();
    }

    protected void onResume() {
        super.onResume();
        this.mapView.onResume();
    }

    protected void onSaveInstanceState(Bundle paramBundle) {
        super.onSaveInstanceState(paramBundle);
        this.mapView.onSaveInstanceState(paramBundle);
    }

    protected void reflesh_makers(String paramString1, String paramString2, Double paramDouble1, Double paramDouble2) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(paramDouble2.doubleValue(), paramDouble1.doubleValue()));
        markerOptions.title(paramString1).snippet(paramString2);
        markerOptions.draggable(false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.worker)));
        aMap.addMarker(markerOptions).setVisible(true);
    }
}