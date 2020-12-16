package com.gc.nfc.ui;

import android.app.AlertDialog;
import android.app.TabActivity;
import android.app.job.JobScheduler;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.nfc.R;
import com.gc.nfc.app.AppContext;
import com.gc.nfc.common.NetRequestConstant;
import com.gc.nfc.common.NetUrlConstant;
import com.gc.nfc.domain.Data_TaskOrders;
import com.gc.nfc.domain.User;
import com.gc.nfc.http.Logger;
import com.gc.nfc.http.ThreadPool;
import com.gc.nfc.interfaces.Netcallback;
import com.gc.nfc.utils.NetUtil;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainlyActivity extends TabActivity implements View.OnClickListener {
    private static final String PROCESS_NAME = "com.gc.nfc";
    Badge badgeGrab;
    Badge badgeOrder;
    Badge badgeRepair;
    private long exitTime = 0L;
    private TabHost host;
    private ImageView img_mine;
    private ImageView img_mycheck;
    private ImageView img_myorders;
    private ImageView img_validorders;
    private LinearLayout linearlayout_mine;
    private LinearLayout linearlayout_mycheck;
    private LinearLayout linearlayout_myorders;
    private LinearLayout linearlayout_validorders;
    private JobScheduler mJobScheduler;
    private Intent m_IntentAmapServeice;
    private TextView text_mine;
    private TextView text_mycheck;
    private TextView text_myorders;
    private TextView text_validorders;

    private void addAlias(String paramString) {
    }

    private void getScreenDisplay() {
        Display display = getWindowManager().getDefaultDisplay();
        int i = display.getWidth();
        int j = display.getHeight();
        AppContext appContext = (AppContext) getApplicationContext();
        appContext.setScreenWidth(i);
        appContext.setScreenHeight(j);
    }

    private void initialCloudPushService() {
        turnOnPush();
        setCusNotifSound();
    }

    private void setCusNotifSound() {
        int i = getResources().getIdentifier("new_order_notify", "raw", getPackageName());
        if (i != 0) {
            String str = "android.resource://" + getPackageName() + "/" + i;
            Log.i("setting_notification", "Set notification sound res id to R.raw.new_order_notify");
            return;
        }
        Log.e("setting_notification", "Set notification sound path error, R.raw.new_order_notify not found.");
    }

    /**
     * 我的巡检
     */
    private void setMyCheckTab() {
        TabHost.TabSpec tabSpec = this.host.newTabSpec("MYCHECK_STRING");
        tabSpec.setIndicator("MYCHECK_STRING");
        tabSpec.setContent(new Intent(this, MyCheckActivity.class));
        this.host.addTab(tabSpec);
    }

    /**
     * 我的订单
     */
    private void setMyOrdersTab() {
        TabHost.TabSpec tabSpec = this.host.newTabSpec("MYORDERS_STRING");
        tabSpec.setIndicator("MYORDERS_STRING");
        tabSpec.setContent(new Intent(this, MyOrdersActivity.class));
        this.host.addTab(tabSpec);
    }

    /**
     * 我的信息
     */
    private void setMyselfTab() {
        TabHost.TabSpec tabSpec = this.host.newTabSpec("MYSELF_STRING");
        tabSpec.setIndicator("MYSELF_STRING");
        tabSpec.setContent(new Intent(this, MineActivity.class));
        this.host.addTab(tabSpec);
    }

    /**
     * 待抢订单
     */
    private void setValidOrdersTab() {
        TabHost.TabSpec tabSpec = this.host.newTabSpec("VALIDORDERS_STRING");
        tabSpec.setIndicator("VALIDORDERS_STRING");
        tabSpec.setContent(new Intent(this, ValidOrdersActivity.class));
        this.host.addTab(tabSpec);
    }

    private void turnOffPush() {
    }

    private void turnOnPush() {
    }

    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
        if (paramKeyEvent.getKeyCode() == 4 && paramKeyEvent.getRepeatCount() == 0 && paramKeyEvent.getAction() == 0) {
            (new AlertDialog.Builder(this)).setTitle("提示").setMessage("确认退出吗？").setIcon(R.mipmap.ic_launcher).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                    MainlyActivity.this.stopService(MainlyActivity.this.m_IntentAmapServeice);
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

    public void initView() {
        this.img_validorders = (ImageView) findViewById(R.id.img_vaildorders);
        this.img_myorders = (ImageView) findViewById(R.id.img_myorders);
        this.img_mine = (ImageView) findViewById(R.id.img_mine);
        this.img_mycheck = (ImageView) findViewById(R.id.img_mycheck);
        this.text_validorders = (TextView) findViewById(R.id.text_vaildorders);
        this.text_myorders = (TextView) findViewById(R.id.text_myorders);
        this.text_mine = (TextView) findViewById(R.id.text_mine);
        this.text_mycheck = (TextView) findViewById(R.id.text_mycheck);
        this.linearlayout_validorders = (LinearLayout) findViewById(R.id.linearlayout_vaildorders);
        this.linearlayout_myorders = (LinearLayout) findViewById(R.id.linearlayout_myorders);
        this.linearlayout_mine = (LinearLayout) findViewById(R.id.linearlayout_mine);
        this.linearlayout_mycheck = (LinearLayout) findViewById(R.id.linearlayout_mycheck);
        this.linearlayout_validorders.setOnClickListener(this);
        this.linearlayout_myorders.setOnClickListener(this);
        this.linearlayout_mine.setOnClickListener(this);
        this.linearlayout_mycheck.setOnClickListener(this);
        this.badgeOrder = (new QBadgeView((Context) this)).bindTarget((View) this.img_myorders);
        this.badgeRepair = (new QBadgeView((Context) this)).bindTarget((View) this.img_mycheck);
        this.badgeGrab = (new QBadgeView((Context) this)).bindTarget((View) this.img_validorders);
    }

    public void isOpenGPS() {
        if (!((LocationManager) getSystemService(Context.LOCATION_SERVICE)).isProviderEnabled("gps")) {
            AlertDialog.Builder builder = new AlertDialog.Builder((Context) this);
            builder.setMessage("GPS未打开，本配送程序必须打开!");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                    Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                    MainlyActivity.this.startActivityForResult(intent, 0);
                }
            });
            builder.show();
        }
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
        if (paramInt1 == 0)
            isOpenGPS();
    }

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.linearlayout_vaildorders:
                this.host.setCurrentTabByTag("VALIDORDERS_STRING");
                this.img_validorders.setImageResource(R.mipmap.ic_menu_deal_on);
                this.text_validorders.setTextColor(getResources().getColor(R.color.textColorPrimary));
                this.img_myorders.setImageResource(R.mipmap.ic_menu_poi_off);
                this.text_myorders.setTextColor(getResources().getColor(R.color.textgray));
                this.img_mine.setImageResource(R.mipmap.ic_menu_user_off);
                this.text_mine.setTextColor(getResources().getColor(R.color.textgray));
                this.img_mycheck.setImageResource(R.mipmap.ic_menu_more_off);
                this.text_mycheck.setTextColor(getResources().getColor(R.color.textgray));
                break;
            case R.id.linearlayout_myorders:
                this.host.setCurrentTabByTag("MYORDERS_STRING");
                this.img_validorders.setImageResource(R.mipmap.ic_menu_deal_off);
                this.text_validorders.setTextColor(getResources().getColor(R.color.textgray));
                this.img_myorders.setImageResource(R.mipmap.ic_menu_poi_on);
                this.text_myorders.setTextColor(getResources().getColor(R.color.textColorPrimary));
                this.img_mine.setImageResource(R.mipmap.ic_menu_user_off);
                this.text_mine.setTextColor(getResources().getColor(R.color.textgray));
                this.img_mycheck.setImageResource(R.mipmap.ic_menu_more_off);
                this.text_mycheck.setTextColor(getResources().getColor(R.color.textgray));
                break;
            case R.id.linearlayout_mine:
                this.host.setCurrentTabByTag("MYSELF_STRING");
                this.img_validorders.setImageResource(R.mipmap.ic_menu_deal_off);
                this.text_validorders.setTextColor(getResources().getColor(R.color.textgray));
                this.img_myorders.setImageResource(R.mipmap.ic_menu_poi_off);
                this.text_myorders.setTextColor(getResources().getColor(R.color.textgray));
                this.img_mine.setImageResource(R.mipmap.ic_menu_user_on);
                this.text_mine.setTextColor(getResources().getColor(R.color.textColorPrimary));
                this.img_mycheck.setImageResource(R.mipmap.ic_menu_more_off);
                this.text_mycheck.setTextColor(getResources().getColor(R.color.textgray));
                break;
            case R.id.linearlayout_mycheck:
                this.host.setCurrentTabByTag("MYCHECK_STRING");
                this.img_validorders.setImageResource(R.mipmap.ic_menu_deal_off);
                this.text_validorders.setTextColor(getResources().getColor(R.color.textgray));
                this.img_myorders.setImageResource(R.mipmap.ic_menu_poi_off);
                this.text_myorders.setTextColor(getResources().getColor(R.color.textgray));
                this.img_mine.setImageResource(R.mipmap.ic_menu_user_off);
                this.text_mine.setTextColor(getResources().getColor(R.color.textgray));
                this.img_mycheck.setImageResource(R.mipmap.ic_menu_more_on);
                this.text_mycheck.setTextColor(getResources().getColor(R.color.textColorPrimary));
                break;
        }
    }

    @RequiresApi(api = 21)
    public void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        Logger.e("MainlyActivity");
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        getScreenDisplay();
        initView();
        isOpenGPS();
        this.host = getTabHost();
        this.host.setup();
        setMyselfTab();
        setValidOrdersTab();
        setMyOrdersTab();
        setMyCheckTab();
        paramBundle = getIntent().getExtras();
        if (paramBundle == null) {
            this.host.setCurrentTabByTag("MYSELF_STRING");
        } else {
            int i = paramBundle.getInt("switchTab");
            if (i == 1)
                this.host.setCurrentTabByTag("MYORDERS_STRING");
            if (i == 2) {
                this.host.setCurrentTabByTag("MYCHECK_STRING");
            } else {
                this.host.setCurrentTabByTag("MYSELF_STRING");
            }
        }
        initialCloudPushService();
    }

    public void onDestroy() {
        super.onDestroy();
    }

    protected void onRestart() {
        super.onRestart();
    }

    protected void onResume() {
        super.onResume();
        orderRemind();
        repairRemind();
        grabRemind();
    }

    public void orderRemind() {
        User user = ((AppContext) getApplicationContext()).getUser();
        NetRequestConstant nrc = new NetRequestConstant();
        nrc.setType(BaseActivity.HttpRequestType.GET);
        nrc.requestUrl = NetUrlConstant.BASEURL+"/api" + "/TaskOrders/" + user.getUsername();
        nrc.context = this;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderStatus", String.valueOf(1));
        nrc.setParams(map);
        getServer(new Netcallback() {
            public void preccess(Object res, boolean flag) {
                Logger.e("http success :"+flag);
                if(flag){
                    HttpResponse response=(HttpResponse)res;
                    if(response!=null){
                        Logger.e("http statuscode :"+response.getStatusLine().getStatusCode());
                        if(response.getStatusLine().getStatusCode()==200){
                            String string = getString(response);
                            Logger.e("orderRemind: " + string);
                            Gson gson = new Gson();
                            Data_TaskOrders dataTaskOrders = gson.fromJson(string, Data_TaskOrders.class);
                            int size = dataTaskOrders.getItems().size();
                            badgeOrder.setBadgeNumber(size);
                            badgeOrder.setBadgeGravity(Gravity.END | Gravity.TOP);
                            badgeOrder.setGravityOffset(0.0F, -2.0F, true);
                        }else{
                            Toast.makeText(MainlyActivity.this, "网络连接失败！", Toast.LENGTH_LONG).show();
                        }
                    }else {
//                        Toast.makeText(MainlyActivity.this, "未知错误，异常2！",
//                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainlyActivity.this, "网络未连接！",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, nrc);
    }

    public void repairRemind() {
        User user = ((AppContext) getApplicationContext()).getUser();
        NetRequestConstant nrc = new NetRequestConstant();
        nrc.setType(BaseActivity.HttpRequestType.GET);
        nrc.requestUrl = NetUrlConstant.BASEURL+"/api" + "/Repair";
        nrc.context = this;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dealedUserId", user.getUsername());
        map.put("processStatus", "PTSuspending");
        nrc.setParams(map);
        getServer(new Netcallback() {
            public void preccess(Object res, boolean flag) {
                Logger.e("http success :"+flag);
                if(flag){
                    HttpResponse response=(HttpResponse)res;
                    if(response!=null){
                        Logger.e("http statuscode :"+response.getStatusLine().getStatusCode());
                        if(response.getStatusLine().getStatusCode()==200){
                            String string = getString(response);
                            Logger.e("repairRemind: " + string);
                            Gson gson = new Gson();
                            Data_TaskOrders dataTaskOrders = gson.fromJson(string, Data_TaskOrders.class);
                            int size = dataTaskOrders.getItems().size();
                            badgeRepair.setBadgeNumber(size);
                            badgeRepair.setBadgeGravity(Gravity.END | Gravity.TOP);
                            badgeRepair.setGravityOffset(0.0F, -2.0F, true);
                        }else{
                            Toast.makeText(MainlyActivity.this, "网络连接失败！", Toast.LENGTH_LONG).show();
                        }
                    }else {
//                        Toast.makeText(MainlyActivity.this, "未知错误，异常3！",
//                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainlyActivity.this, "网络未连接！",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, nrc);
    }

    public void grabRemind() {
        User user = ((AppContext) getApplicationContext()).getUser();
        NetRequestConstant nrc = new NetRequestConstant();
        nrc.setType(BaseActivity.HttpRequestType.GET);
        nrc.requestUrl = NetUrlConstant.BASEURL+"/api" + "/TaskOrders/" + user.getUsername();
        nrc.context = this;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderStatus", String.valueOf(0));
        nrc.setParams(map);
        getServer(new Netcallback() {
            public void preccess(Object res, boolean flag) {
                Logger.e("http success :"+flag);
                if(flag){
                    HttpResponse response=(HttpResponse)res;
                    if(response!=null){
                        Logger.e("http statuscode :"+response.getStatusLine().getStatusCode());
                        if(response.getStatusLine().getStatusCode()==200){
                            String string = getString(response);
                            Logger.e("grabRemind: " + string);
                            Gson gson = new Gson();
                            Data_TaskOrders dataTaskOrders = gson.fromJson(string, Data_TaskOrders.class);
                            int size = dataTaskOrders.getItems().size();
                            badgeGrab.setBadgeNumber(size);
                            badgeGrab.setBadgeGravity(Gravity.END | Gravity.TOP);
                            badgeGrab.setGravityOffset(0.0F, -2.0F, true);
                        }else{
                            Toast.makeText(MainlyActivity.this, "网络连接失败！", Toast.LENGTH_LONG).show();
                        }
                    }else {
//                        Toast.makeText(MainlyActivity.this, "未知错误，异常1！",
//                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainlyActivity.this, "网络未连接！",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, nrc);
    }

    public static final int SUCCESS = 10001;
    public static final int FAIL = 10002;
    public static final int ERROR = 10003;
    private int PERMISSION_REQUEST_CODE = 10000;
    class RunnableTask implements Runnable{
        private NetRequestConstant nrc;
        private Handler handler;
        public RunnableTask(NetRequestConstant nrc , Handler handler) {
            this.nrc = nrc;
            this.handler = handler;
        }

        public void run() {
            HttpResponse res = null;
            if(NetUtil.isCheckNet(getApplicationContext())){
                if(nrc.getType()==BaseActivity.HttpRequestType.POST){//Post请求
                    res = NetUtil.httpPost(nrc);
                }else if(nrc.getType()==BaseActivity.HttpRequestType.GET){//get请求
                    res = NetUtil.httpGet(nrc);
                }else if(nrc.getType()==BaseActivity.HttpRequestType.PUT){//put请求
                    res = NetUtil.httpPut(nrc);
                }
                Message message = Message.obtain();
                message.obj = res;
                message.what = SUCCESS;
                handler.sendMessage(message);
            }else{
                Message message = Message.obtain();
                message.what = ERROR;
                handler.sendMessage(message);
            }
        }
    }

    class BaseHandler extends Handler{
        private Netcallback callBack;

        public BaseHandler(Netcallback callBack) {
            this.callBack = callBack;
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case SUCCESS:// 网络请求成功后回调
                    callBack.preccess(msg.obj, true);
                    break;
                case FAIL:// 网络请求失败后回调
                case ERROR:
                    callBack.preccess(msg.obj, false);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    }

    protected void getServer(Netcallback callBack ,NetRequestConstant nrc){
        Handler handler = new BaseHandler(callBack);
        RunnableTask task = new RunnableTask(nrc, handler);
        ThreadPool.getInstance().addTask(task);
    }

    public String getString(HttpResponse response){
        try {
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            return null;
        }
    }
}