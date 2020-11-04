package com.gc.nfc.ui;

import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Process;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.nfc.R;
import com.gc.nfc.app.AppContext;
import com.gc.nfc.common.NetRequestConstant;
import com.gc.nfc.domain.User;
import com.gc.nfc.http.OkHttpUtil;
import com.gc.nfc.interfaces.Netcallback;
//import com.gc.nfc.utils.AmapLocationService;
//import com.gc.nfc.utils.OnePixelReceiver;
import com.gc.nfc.utils.SharedPreferencesHelper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import okhttp3.Request;
import okhttp3.Response;

public class DiaoBoActivity extends BaseActivity implements View.OnClickListener {
    private AppContext appContext;

    private ImageView imageView_userQRcode;

    private LinearLayout lL_myAppVersion;

    private LinearLayout lL_myBottle;

    private LinearLayout lL_myLogout;

    private JobScheduler mJobScheduler;

    //  private OnePixelReceiver mOnepxReceiver;

    private Intent m_IntentAmapServeice;

    private TextView textview_username;

    private User user;

    private void loginOut() {
        AppContext appContext = (AppContext) DiaoBoActivity.this.getApplicationContext();
        appContext.logout();
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.GET(OkHttpUtil.URL + "/sysusers/logout/" + user.getUsername(), null, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(DiaoBoActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) {
                if (response.code() == 200) {
                    Intent intent = new Intent(DiaoBoActivity.this.getApplicationContext(), LoginActivity.class);
                    DiaoBoActivity.this.startActivity(intent);
                    DiaoBoActivity.this.finish();
                    return;
                }
                Toast.makeText(DiaoBoActivity.this, "退出登录失败", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }

    private void showIdentification() {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context) this);
        View view = View.inflate((Context) this, R.layout.pay_on_scan, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.items_imageViewScanCode);
        String str = "http://www.gasmart.com.cn/api/pay/QRCode?text=" + "psy";
        try {
            URL uRL = new URL(str);
            imageView.setImageBitmap(BitmapFactory.decodeStream(uRL.openStream()));
            builder.setIcon(R.drawable.icon_logo);
            builder.setTitle("身份码");
            AlertDialog alertDialog = builder.create();
            alertDialog.setView(view);
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                    param1DialogInterface.dismiss();
                }
            };
            alertDialog.setButton(-1, "确定", onClickListener);
            alertDialog.show();
            Window window = alertDialog.getWindow();
            window.setGravity(17);
            window.setLayout(-1, -2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
        if (paramKeyEvent.getKeyCode() == 4 && paramKeyEvent.getRepeatCount() == 0 && paramKeyEvent.getAction() == 0) {
            (new AlertDialog.Builder((Context) this)).setTitle("提示").setMessage("确认退出吗？").setIcon(R.drawable.icon_logo).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface param1DialogInterface, int param1Int) {
//                    DiaoBoActivity.this.stopService(DiaoBoActivity.this.m_IntentAmapServeice);
                    Process.killProcess(Process.myPid());
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                }
            }).show();
            return false;
        }
        return super.dispatchKeyEvent(paramKeyEvent);
    }

    void init() {
        setContentView(R.layout.activity_diaobo);
        this.lL_myBottle = (LinearLayout) findViewById(R.id.lL_myBottle);
        this.lL_myAppVersion = (LinearLayout) findViewById(R.id.lL_mySetting);
        this.lL_myLogout = (LinearLayout) findViewById(R.id.lL_myLogout);
        this.imageView_userQRcode = (ImageView) findViewById(R.id.imageView_userQRcode);
        this.textview_username = (TextView) findViewById(R.id.textview_username);
        this.lL_myBottle.setOnClickListener(this);
        this.lL_myLogout.setOnClickListener(this);
        this.lL_myAppVersion.setOnClickListener(this);
        this.imageView_userQRcode.setOnClickListener(this);
    }

    public void isOpenGPS() {
        //    if (!((LocationManager)getSystemService("location")).isProviderEnabled("gps")) {
        //      AlertDialog.Builder builder = new AlertDialog.Builder((Context)this);
        //      builder.setMessage("GPS未打开，本配送程序必须打开!");
        //      builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
        //            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
        //              Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
        //              DiaoBoActivity.this.startActivityForResult(intent, 0);
        //            }
        //          });
        //      builder.show();
        //    }
    }

    public void onClick(View paramView) {
        Bundle bundle;
        Intent intent;
        switch (paramView.getId()) {
            case R.id.lL_myBottle:
                intent = new Intent((Context) this, MybottlesActivity.class);
                bundle = new Bundle();
                bundle.putString("userId", this.user.getUsername());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.lL_myLogout:
                loginOut();
                break;
            case R.id.lL_mySetting:
                startActivity(new Intent((Context) this, AboutActivity.class));
                break;
            case R.id.imageView_userQRcode:
                showIdentification();
                break;
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        this.appContext = (AppContext) getApplicationContext();
        this.user = this.appContext.getUser();
        if (this.user == null) {
            Toast.makeText(this, "登陆会话失效", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, AutoLoginActivity.class));
            finish();
            return;
        }
        String name = this.user.getUsername();
        this.textview_username.setText(name + "\n(" + this.user.getGroupName() + "|" + this.user.getDepartmentName() + ")");
        String str = "http://www.gasmart.com.cn/api/pay/QRCode?text=" + name;
        try {
            URL uRL = new URL(str);
            Bitmap bitmap = BitmapFactory.decodeStream(uRL.openStream());
            this.imageView_userQRcode.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isOpenGPS();
        //    this.mOnepxReceiver = new OnePixelReceiver();
        //    IntentFilter intentFilter = new IntentFilter();
        //    intentFilter.addAction("android.intent.action.SCREEN_OFF");
        //    intentFilter.addAction("android.intent.action.SCREEN_ON");
        //    intentFilter.addAction("android.intent.action.USER_PRESENT");
        //    registerReceiver((BroadcastReceiver)this.mOnepxReceiver, intentFilter);
        //    startJobScheduler(this.user.getUsername());
    }

    public void onDestroy() {
//        stopService(this.m_IntentAmapServeice);
        super.onDestroy();
    }

    @RequiresApi(21)
    public void startJobScheduler(String paramString) {
        this.mJobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        this.mJobScheduler.cancel(55);
        //    JobInfo.Builder builder = new JobInfo.Builder(55, new ComponentName((Context)this, MyJobService.class));
        //    if (Build.VERSION.SDK_INT >= 21) {
        //      builder.setMinimumLatency(5000L);
        //      builder.setOverrideDeadline(6000L);
        //      builder.setBackoffCriteria(30000L, JobInfo.BACKOFF_POLICY_LINEAR);
        //    } else {
        //      builder.setPeriodic(30000L);
        //    }
        //    builder.setPersisted(true);
        //    builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        //    builder.setRequiresCharging(true);
        //    PersistableBundle persistableBundle = new PersistableBundle();
        //    persistableBundle.putString("servicename", AmapLocationService.class.getName());
        //    persistableBundle.putString("userId", paramString);
        //    builder.setExtras(persistableBundle);
        //    JobInfo jobInfo = builder.build();
        //    this.mJobScheduler.schedule(jobInfo);
    }
}
