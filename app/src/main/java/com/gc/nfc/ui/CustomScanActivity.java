package com.gc.nfc.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.gc.nfc.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lenovo on 2020/11/1
 */
//public class CustomScanActivity extends AppCompatActivity implements DecoratedBarcodeView.TorchListener {
//  private CaptureManager captureManager;
//
//  private boolean isLightOn = false;
//
//  @BindView(R.id.dbv_custom)
//  DecoratedBarcodeView mDBV;
//
//  @BindView(R.id.btn_switch)
//  Button swichLight;
//
//  private boolean hasFlash() {
//    return getApplicationContext().getPackageManager().hasSystemFeature("android.hardware.camera.flash");
//  }
//
//  protected void onCreate(Bundle paramBundle) {
//    super.onCreate(paramBundle);
//    setContentView(R.layout.activity_custom_scan);
//    ButterKnife.bind((Activity)this);
//    this.mDBV.setTorchListener(this);
//    if (!hasFlash())
//      this.swichLight.setVisibility(View.GONE);
//    this.captureManager = new CaptureManager((Activity)this, this.mDBV);
//    this.captureManager.initializeFromIntent(getIntent(), paramBundle);
//    this.captureManager.decode();
//  }
//
//  protected void onDestroy() {
//    super.onDestroy();
//    this.captureManager.onDestroy();
//  }
//
//  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
//    return (this.mDBV.onKeyDown(paramInt, paramKeyEvent) || super.onKeyDown(paramInt, paramKeyEvent));
//  }
//
//  protected void onPause() {
//    super.onPause();
//    this.captureManager.onPause();
//  }
//
//  protected void onResume() {
//    super.onResume();
//    this.captureManager.onResume();
//  }
//
//  public void onSaveInstanceState(Bundle paramBundle, PersistableBundle paramPersistableBundle) {
//    super.onSaveInstanceState(paramBundle, paramPersistableBundle);
//    this.captureManager.onSaveInstanceState(paramBundle);
//  }
//
//  public void onTorchOff() {
//    this.isLightOn = false;
//  }
//
//  public void onTorchOn() {
//    this.isLightOn = true;
//  }
//
//  @OnClick({2131230762})
//  public void swichLight() {
//    if (this.isLightOn) {
//      this.mDBV.setTorchOff();
//      return;
//    }
//    this.mDBV.setTorchOn();
//  }
//}
