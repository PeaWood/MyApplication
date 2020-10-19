package com.gc.nfc.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.gc.nfc.R;
import com.gc.nfc.app.AppContext;
import com.gc.nfc.common.NetRequestConstant;
import com.gc.nfc.domain.Data_User;
import com.gc.nfc.domain.User;
import com.gc.nfc.http.Logger;
import com.gc.nfc.http.OkHttpUtil;
import com.gc.nfc.interfaces.Netcallback;
import com.gc.nfc.utils.JellyInterpolator;
//import com.gc.nfc.utils.NetUtil;
import com.gc.nfc.utils.SharedPreferencesHelper;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Request;
import okhttp3.Response;

public class AutoLoginActivity extends BaseActivity {
  private ObjectAnimator m_animator;
  
  private View progress;

  private void autoLogin() {
//        final String name = this.et_name.getText().toString();
//        final String password = this.et_pin.getText().toString();
    final String name = "psy";
    final String password = "111111";
    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
      Toast.makeText(AutoLoginActivity.this, "请检查账号和密码", Toast.LENGTH_LONG).show();
    }
    Map map = new HashMap();
    map.put("userId", name);
    map.put("password", password);
    OkHttpUtil util = OkHttpUtil.getInstance(this);
    util.GET(OkHttpUtil.URL + "/sysusers/login", map, new OkHttpUtil.ResultCallback() {
      @Override
      public void onError(Request request, Exception e) {
        Toast.makeText(AutoLoginActivity.this, "网络连接错误！", Toast.LENGTH_LONG).show();
      }

      @Override
      public void onResponse(Response response) throws IOException {
        if (response.code() != 200) {
          Toast.makeText(AutoLoginActivity.this, "无数据！", Toast.LENGTH_LONG).show();
          return;
        }
        String string = response.body().string();
        Logger.e("login: " + string);
        Gson gson = new Gson();
        Data_User userdata = gson.fromJson(string, Data_User.class);
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        user.setDepartmentCode(userdata.getDepartment().getCode());
        user.setDepartmentName(userdata.getDepartment().getName());
        user.setGroupCode(userdata.getUserGroup().getCode());
        user.setGroupName(userdata.getUserGroup().getName());
        if (userdata.getGasTakeOverStatus() != null) {
          user.setScanType(userdata.getGasTakeOverStatus().getIndex());
        } else {
          user.setScanType(0);
        }
        AppContext appContext = (AppContext) AutoLoginActivity.this.getApplicationContext();
        appContext.setUser(user);
        MediaPlayer.create(AutoLoginActivity.this, R.raw.start_working).start();
        if (user.getGroupCode().equals("00005") || user.getGroupCode().equals("00006")) {
          //StockManagerActivity
          Toast.makeText(AutoLoginActivity.this, "更换角色！", Toast.LENGTH_LONG).show();
          return;
        }
        if (user.getGroupCode().equals("00003")) {
          Intent starter = new Intent(AutoLoginActivity.this, MainlyActivity.class);
          startActivity(starter);
          return;
        }
        if (user.getGroupCode().equals("00007")) {
          //DiaoBoActivity
          Toast.makeText(AutoLoginActivity.this, "更换角色！", Toast.LENGTH_LONG).show();
        }
      }
    });
  }
  
  private void inputAnimator() {
    this.progress.setVisibility(View.INVISIBLE);
    progressAnimator(this.progress);
  }
  
  @SuppressLint("NewApi")
  private void progressAnimator(View paramView) {
    this.m_animator = ObjectAnimator.ofPropertyValuesHolder(paramView, new PropertyValuesHolder[] { PropertyValuesHolder.ofFloat("scaleX", new float[] { 0.5F, 1.0F }), PropertyValuesHolder.ofFloat("scaleY", new float[] { 0.5F, 1.0F }) });
    this.m_animator.setDuration(1000L);
    this.m_animator.setInterpolator((TimeInterpolator)new JellyInterpolator());
    this.m_animator.start();
    this.m_animator.addListener(new Animator.AnimatorListener() {
          public void onAnimationCancel(Animator param1Animator) {}
          
          public void onAnimationEnd(Animator param1Animator) {
            AutoLoginActivity.this.autoLogin();
            AutoLoginActivity.this.progress.setVisibility(View.GONE);
          }
          
          public void onAnimationRepeat(Animator param1Animator) {}
          
          public void onAnimationStart(Animator param1Animator) {}
        });
  }
  
  void init() {
    requestWindowFeature(1);
    ((AppContext)getApplicationContext()).setPreferences(getPreferences(0));
    setContentView(R.layout.layout_progress);
    this.progress = findViewById(R.id.progressBar2);
    SharedPreferencesHelper.initial((Context)this);
    inputAnimator();
  }
  
  @SuppressLint("NewApi")
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder()).permitAll().build());
  }
}

