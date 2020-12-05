package com.gc.nfc.ui;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.view.textclassifier.TextClassification;

import com.gc.nfc.common.NetRequestConstant;
import com.gc.nfc.http.ThreadPool;
import com.gc.nfc.interfaces.Netcallback;
import com.gc.nfc.utils.NetUtil;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by lenovo on 2020/9/23
 */
public abstract class BaseActivity extends Activity {
    private NetRequestConstant nrc;
    private Handler handler;
    public static final int SUCCESS = 10001;
    public static final int FAIL = 10002;
    public static final int ERROR = 10003;
    private int PERMISSION_REQUEST_CODE = 10000;

    abstract void init();
    public enum HttpRequestType{
        GET,POST,PUT,
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        String[] deniedPermissions = new String[]{Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (ContextCompat.checkSelfPermission(this, deniedPermissions[0]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(deniedPermissions, PERMISSION_REQUEST_CODE);
        }
    }



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
                if(nrc.getType()==HttpRequestType.POST){//Post请求
                    res = NetUtil.httpPost(nrc);
                }else if(nrc.getType()==HttpRequestType.GET){//get请求
                    res = NetUtil.httpGet(nrc);
                }else if(nrc.getType()==HttpRequestType.PUT){//put请求
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