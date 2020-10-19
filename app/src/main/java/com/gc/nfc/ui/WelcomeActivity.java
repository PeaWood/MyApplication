package com.gc.nfc.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.gc.nfc.R;

public class WelcomeActivity extends Activity {
  @SuppressLint("HandlerLeak")
  private Handler handler = new Handler() {
      public void handleMessage(Message param1Message) {
        WelcomeActivity.this.getHome();
        super.handleMessage(param1Message);
      }
    };
  
  public void getHome() {
    startActivity(new Intent(this, LoginActivity.class));
    finish();
  }
  
  public void onCreate(Bundle paramBundle) {
    if (!isTaskRoot()) {
      finish();
      return;
    } 
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(R.layout.welcome_page);
    this.handler.sendEmptyMessageDelayed(0, 2000L);
  }
}