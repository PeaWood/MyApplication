package com.gc.nfc.app;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.gc.nfc.domain.User;
import com.gc.nfc.http.Logger;

public class AppContext extends Application {
  private String groupCode;
  
  private String groupName;
  
  private LatLng location;
  
  private SharedPreferences preferences;
  
  private int screenHeight;
  
  private int screenWidth;
  
  public static User user;
  
  private void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= 26) {
//      NotificationManager notificationManager = (NotificationManager)getSystemService("notification");
//      NotificationChannel notificationChannel = new NotificationChannel("1", "notification channel", 4);
//      notificationChannel.setDescription("notification description");
//      notificationChannel.enableLights(true);
//      notificationChannel.setLightColor(-65536);
//      notificationChannel.enableVibration(true);
//      notificationChannel.setVibrationPattern(new long[] { 100L, 200L, 300L, 400L, 500L, 400L, 300L, 200L, 400L });
//      notificationChannel.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + 2131558405), Notification.AUDIO_ATTRIBUTES_DEFAULT);
//      notificationManager.createNotificationChannel(notificationChannel);
    } 
  }
  
  private void initCloudChannel(Context paramContext) {
    createNotificationChannel();
//    PushServiceFactory.init(paramContext);
//    PushServiceFactory.getCloudPushService().register(paramContext, new CommonCallback() {
//          public void onFailed(String param1String1, String param1String2) {
//            System.out.print("初始化云推送通道：failed");
//          }
//
//          public void onSuccess(String param1String) {
//            System.out.print("初始化云推送通道：ok");
//          }
//        });
//    MiPushRegister.register(paramContext, "XIAOMI_ID", "XIAOMI_KEY");
//    HuaWeiRegister.register(paramContext);
//    GcmRegister.register(paramContext, "send_id", "application_id");
  }
  
  public String getGroupCode() {
    return this.groupCode;
  }
  
  public String getGroupName() {
    return this.groupName;
  }
  
  public LatLng getLocation() {
    return this.location;
  }
  
  public SharedPreferences getPreferences() {
    return this.preferences;
  }
  
  public int getScreenHeight() {
    return this.screenHeight;
  }
  
  public int getScreenWidth() {
    return this.screenWidth;
  }
  
  public User getUser() {
    return user;
  }
  
  public void onCreate() {
    super.onCreate();
    initCloudChannel((Context)this);
  }
  
  public void setGroupCode(String paramString) {
    this.groupCode = paramString;
  }
  
  public void setGroupName(String paramString) {
    this.groupName = paramString;
  }
  
  public void setLocation(LatLng paramLatLng) {
    this.location = paramLatLng;
  }
  
  public void setPreferences(SharedPreferences paramSharedPreferences) {
    Logger.e("setPreferences: " );
    this.preferences = paramSharedPreferences;
  }
  
  public void setScreenHeight(int paramInt) {
    this.screenHeight = paramInt;
  }
  
  public void setScreenWidth(int paramInt) {
    this.screenWidth = paramInt;
  }
  
  public void setUser(User paramUser) {
    Logger.e("<---setUser--->");
    user = paramUser;
    SharedPreferences.Editor editor = this.preferences.edit();
    editor.putString("username", paramUser.getUsername());
    editor.putString("password", paramUser.getPassword());
    editor.commit();
  }

    public void logout() {
      Logger.e("<---logout--->");
      SharedPreferences.Editor editor = this.preferences.edit();
      editor.putString("username", "");
      editor.putString("password", "");
      editor.commit();
    }
}