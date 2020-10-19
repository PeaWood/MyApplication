package com.gc.nfc.ui;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;

/**
 * Created by lenovo on 2020/9/23
 */
public abstract class BaseActivity extends Activity {
    private int PERMISSION_REQUEST_CODE = 10000;

    abstract void init();

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        requestPermissions();
        init();
    }

    /**
     * 开始请求权限。
     * 方法内部已经对Android M 或以上版本进行了判断，外部使用不再需要重复判断。
     * 如果设备还不是M或以上版本，则也会回调到requestPermissionsSuccess方法。
     */
    @SuppressLint("NewApi")
    public void requestPermissions() {
        String[] deniedPermissions = new String[]{Manifest.permission.INTERNET};
        if (ContextCompat.checkSelfPermission(this, deniedPermissions[0]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(deniedPermissions, PERMISSION_REQUEST_CODE);
        }
    }

    public enum HttpRequestType {
        GET, POST, PUT;

        static {

        }
    }
}