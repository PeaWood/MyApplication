package com.gc.nfc.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gc.nfc.R;

public class AboutActivity extends Activity {
  private String applicationVersion;
  
  AlertDialog loadingDialog;
  
  TextView tvShowVersion;
  
  public static void actionStart(Context paramContext) {
    paramContext.startActivity(new Intent(paramContext, AboutActivity.class));
  }
  
  public void boot(View paramView) {}
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_about);
  }
  
  public void update(View paramView) {

  }
}
