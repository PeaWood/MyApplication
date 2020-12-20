package com.gc.nfc.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.gc.nfc.R;
import com.gc.nfc.app.AppContext;
import com.gc.nfc.common.NetUrlConstant;
import com.gc.nfc.http.Logger;
import com.gc.nfc.utils.NetUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

public class AmapLocationService extends Service {
    private static final String TAG = "LocationService";
    MyConn conn;

    AMapLocationClient mLocationClient = null;

    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        public void onLocationChanged(AMapLocation param1AMapLocation) {
            if (param1AMapLocation != null && param1AMapLocation.getErrorCode() == 0) {
                double d1 = param1AMapLocation.getLongitude();
                double d2 = param1AMapLocation.getLatitude();
                String str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date(param1AMapLocation.getTime()));
                Message message = new Message();
                Bundle bundle = new Bundle();
                bundle.putDouble("longitude", Double.valueOf(d1).doubleValue());
                bundle.putDouble("latitude", Double.valueOf(d2).doubleValue());
                bundle.putString("timestr", str);
                message.setData(bundle);
                message.what = 1;
                AmapLocationService.this.netHandler.sendMessage(message);
            }
        }
    };

    public AMapLocationClientOption mLocationOption = null;

    MediaPlayer mMediaPlayer = null;

    private PowerManager mPowerManager;

    private PowerManager.WakeLock mWakeLock;

    private AppContext m_appContext;

    private String m_userId;

    Handler netHandler = null;

    Thread netThread = new Thread() {
        @SuppressLint("HandlerLeak")
        public void run() {
            Looper.prepare();
            netHandler = new Handler() {
                public void dispatchMessage(Message param2Message) {
                    String str;
                    double d1;
                    double d2;
                    Bundle bundle = param2Message.getData();
                    switch (param2Message.what) {
                        case 1:
                            d1 = bundle.getDouble("longitude");
                            d2 = bundle.getDouble("latitude");
                            str = bundle.getString("timestr");
                            upDatePosition(Double.valueOf(d1).doubleValue(), Double.valueOf(d2).doubleValue(), str);
                        case 153:
                            mPowerManager = (PowerManager) AmapLocationService.this.getSystemService(Context.POWER_SERVICE);
                            mWakeLock = mPowerManager.newWakeLock(10, getClass().getName());
                            if (AmapLocationService.this.mWakeLock != null)
                                AmapLocationService.this.mWakeLock.acquire();
                            break;
                    }
                }
            };
            Looper.loop();
        }
    };

    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption aMapLocationClientOption = new AMapLocationClientOption();
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        aMapLocationClientOption.setGpsFirst(true);
        aMapLocationClientOption.setHttpTimeOut(30000L);
        aMapLocationClientOption.setNeedAddress(false);
        aMapLocationClientOption.setOnceLocation(false);
        aMapLocationClientOption.setOnceLocationLatest(false);
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);
        aMapLocationClientOption.setSensorEnable(false);
        aMapLocationClientOption.setWifiScan(true);
        aMapLocationClientOption.setLocationCacheEnable(true);
        aMapLocationClientOption.setInterval(20000L);
        return aMapLocationClientOption;
    }

    private void reportLocation(LatLng paramLatLng) {
        try {
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout((HttpParams) basicHttpParams, 5000);
            HttpConnectionParams.setSoTimeout((HttpParams) basicHttpParams, 5000);
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient((HttpParams) basicHttpParams);
            StringBuilder stringBuilder = new StringBuilder();
            String str = stringBuilder.append(NetUrlConstant.BASEURL+"/api/sysusers/position").append("?userId=").append(this.m_userId).toString();
            HttpPost httpPost = new HttpPost(str);
            httpPost.setHeader("Content-Type", "application/json");
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("longitude", paramLatLng.longitude);
            jSONObject2.put("latitude", paramLatLng.latitude);
            StringEntity stringEntity = new StringEntity(jSONObject2.toString());
            stringEntity.setContentType("application/json");
            httpPost.setEntity((HttpEntity) stringEntity);
            if (NetUtil.m_loginCookies != null) {
                StringBuilder stringBuilder1 = new StringBuilder();
                defaultHttpClient.setCookieStore(NetUtil.m_loginCookies);
            }
            HttpResponse httpResponse = defaultHttpClient.execute((HttpUriRequest) httpPost);
            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() == 200)
                Logger.e("send");
        } catch (UnsupportedEncodingException unsupportedEncodingException) {
            unsupportedEncodingException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sysUserKeepAlive() {
        try {
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout((HttpParams) basicHttpParams, 5000);
            HttpConnectionParams.setSoTimeout((HttpParams) basicHttpParams, 5000);
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient((HttpParams) basicHttpParams);
            StringBuilder stringBuilder = new StringBuilder();
            String str = stringBuilder.append("http://www.gasmart.com.cn/api/sysusers/KeepAlive").append("/").append(this.m_userId).toString();
            HttpGet httpGet = new HttpGet(str);
            httpGet.setHeader("Content-Type", "application/json");
            if (NetUtil.m_loginCookies != null) {
                StringBuilder stringBuilder1 = new StringBuilder();
                defaultHttpClient.setCookieStore(NetUtil.m_loginCookies);
            }
            defaultHttpClient.execute((HttpUriRequest) httpGet);
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public void getPosition() {
        this.mLocationClient.setLocationOption(this.mLocationOption);
        this.mLocationClient.startLocation();
    }

    public void onCreate() {
        super.onCreate();
        this.conn = new MyConn();
        this.netThread.start();
        this.mLocationClient = new AMapLocationClient(getApplicationContext());
        this.mLocationClient.setLocationListener(this.mLocationListener);
        this.mLocationOption = getDefaultOption();
        this.netHandler.sendEmptyMessageDelayed(153, 10000L);
        getPosition();
    }

    public void onDestroy() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
        new Bundle();
        this.m_userId = paramIntent.getExtras().getString("userId");
        this.m_appContext = (AppContext) getApplicationContext();
        return Service.START_STICKY;
    }

    public void upDatePosition(double paramDouble1, double paramDouble2, String paramString) {
        LatLng latLng = new LatLng(paramDouble2, paramDouble1);
        if (this.m_appContext != null)
            this.m_appContext.setLocation(latLng);
        reportLocation(latLng);
        sysUserKeepAlive();
    }

    class MyConn implements ServiceConnection {
        public void onServiceConnected(ComponentName param1ComponentName, IBinder param1IBinder) {
        }

        public void onServiceDisconnected(ComponentName param1ComponentName) {
        }
    }
}