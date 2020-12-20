package com.gc.nfc.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressLint({"NewApi"})
@TargetApi(3)
public class LocationUtils {
  private static final String TAG = "LocationUtil";
  
  private static LocationListener locationListener;
  
  private static LocationManager locationManager;
  
  private static Activity mActivity;
  
  public static void formListenerGetLocation() {
    locationListener = new LocationListener() {
        public void onLocationChanged(Location param1Location) {}
        
        public void onProviderDisabled(String param1String) {}
        
        public void onProviderEnabled(String param1String) {}
        
        public void onStatusChanged(String param1String, int param1Int, Bundle param1Bundle) {}
      };
    if (ActivityCompat.checkSelfPermission((Context)mActivity, "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS") == 0 || ActivityCompat.checkSelfPermission((Context)mActivity, "android.permission.ACCESS_FINE_LOCATION") != 0);
    locationManager.requestLocationUpdates("gps", 0L, 0.0F, locationListener);
  }
  
  public static int getGpsSatelliteCount() {
    return getGpsStatus().size();
  }
  
  public static void getGpsStatelliteInfo(GpsSatellite paramGpsSatellite) {}
  
  public static List<GpsSatellite> getGpsStatus() {
    String str;
    ArrayList<GpsSatellite> arrayList = new ArrayList();
    try {
      GpsStatus gpsStatus = locationManager.getGpsStatus(null);
      int i = gpsStatus.getMaxSatellites();
      gpsStatus.getTimeToFirstFix();
      Iterator<GpsSatellite> iterator = gpsStatus.getSatellites().iterator();
      byte b = 0;
      while (true) {
        if (iterator.hasNext()) {
          if (b <= i) {
            b++;
            arrayList.add(iterator.next());
            continue;
          } 
        } 
        break;
      } 
    } catch (SecurityException securityException) {
      str = securityException.getMessage();
      System.out.println(str);
      arrayList = null;
    }
    return arrayList;
  }
  
  public static Location getLocationByGps() {
    if (ActivityCompat.checkSelfPermission((Context)mActivity, "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS") == 0 || ActivityCompat.checkSelfPermission((Context)mActivity, "android.permission.ACCESS_FINE_LOCATION") != 0);
    return locationManager.getLastKnownLocation("gps");
  }
  
  public static Location getLocationByNetwork() {
    if (ActivityCompat.checkSelfPermission((Context)mActivity, "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS") == 0 || ActivityCompat.checkSelfPermission((Context)mActivity, "android.permission.ACCESS_FINE_LOCATION") != 0);
    return locationManager.getLastKnownLocation("network");
  }
  
  public static void getStatusListener() {
    GpsStatus.Listener listener = new GpsStatus.Listener() {
        public void onGpsStatusChanged(int param1Int) {
          if (param1Int != 3) {
            if (param1Int == 4) {
              if (ActivityCompat.checkSelfPermission((Context)LocationUtils.mActivity, "android.permission.ACCESS_LOCATION_EXTRA_COMMANDS") == 0 || ActivityCompat.checkSelfPermission((Context)LocationUtils.mActivity, "android.permission.ACCESS_FINE_LOCATION") != 0);
              GpsStatus gpsStatus = LocationUtils.locationManager.getGpsStatus(null);
              int i = gpsStatus.getMaxSatellites();
              Iterator<GpsSatellite> iterator = gpsStatus.getSatellites().iterator();
              param1Int = 0;
              while (true) {
                if (iterator.hasNext() && param1Int <= i) {
                  param1Int++;
                  GpsSatellite gpsSatellite = iterator.next();
                  continue;
                } 
                return;
              } 
            } 
            if (param1Int == 1 || param1Int == 2);
          } 
        }
      };
    locationManager.addGpsStatusListener(listener);
  }
  
  public static void isOpenGPS() {
    if (!locationManager.isProviderEnabled("gps")) {
      Toast.makeText((Context)mActivity, "配送需开GPS，前往开启！", Toast.LENGTH_LONG).show();
      Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
      mActivity.startActivity(intent);
      AlertDialog.Builder builder = new AlertDialog.Builder((Context)mActivity);
      builder.setMessage("GPS未打开，是否打开?");
      builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
              Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
              LocationUtils.mActivity.startActivityForResult(intent, 0);
            }
          });
      builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
              param1DialogInterface.dismiss();
            }
          });
      builder.show();
    } 
  }
  
  public static void setInstance(Activity paramActivity) {
    mActivity = paramActivity;
    locationManager = (LocationManager)mActivity.getSystemService(Context.LOCATION_SERVICE);
  }
}