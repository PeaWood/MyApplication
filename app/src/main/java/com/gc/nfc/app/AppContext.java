package com.gc.nfc.app;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
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
  private String TAG = "阿里云推送";

  private void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
      // 通知渠道的id。
      String id = "1";
      // 用户可以看到的通知渠道的名字。
      CharSequence name = "notification channel";
      // 用户可以看到的通知渠道的描述。
      String description = "notification description";
      int importance = NotificationManager.IMPORTANCE_HIGH;
      NotificationChannel mChannel = new NotificationChannel(id, name, importance);
      // 配置通知渠道的属性。
      mChannel.setDescription(description);
      // 设置通知出现时的闪灯（如果Android设备支持的话）。
      mChannel.enableLights(true);
      mChannel.setLightColor(Color.RED);
      // 设置通知出现时的震动（如果Android设备支持的话）。
      mChannel.enableVibration(true);
      mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
      // 最后在notificationmanager中创建该通知渠道。
      mNotificationManager.createNotificationChannel(mChannel);
    }
  }

  /**
   * 初始化云推送通道
   * @param applicationContext
   */
  private void initCloudChannel(Context applicationContext) {
    createNotificationChannel();
    PushServiceFactory.init(applicationContext);
    CloudPushService pushService = PushServiceFactory.getCloudPushService();
    pushService.register(applicationContext, new CommonCallback() {
      @Override
      public void onSuccess(String response) {
        Log.d(TAG, "init cloudchannel success");
      }
      @Override
      public void onFailed(String errorCode, String errorMessage) {
        Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
      }
    });
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
    initCloudChannel(this);
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
    Logger.e("<---AppContext setPreferences--->" );
    this.preferences = paramSharedPreferences;
  }
  
  public void setScreenHeight(int paramInt) {
    this.screenHeight = paramInt;
  }
  
  public void setScreenWidth(int paramInt) {
    this.screenWidth = paramInt;
  }
  
  public void setUser(User paramUser) {
    Logger.e("<---AppContext setUser--->");
    user = paramUser;
    SharedPreferences.Editor editor = this.preferences.edit();
    editor.putString("username", paramUser.getUsername());
    editor.putString("password", paramUser.getPassword());
    editor.commit();
  }

    public void logout() {
      Logger.e("<---AppContext logout--->");
      SharedPreferences.Editor editor = this.preferences.edit();
      editor.putString("username", "");
      editor.putString("password", "");
      editor.commit();
    }
}