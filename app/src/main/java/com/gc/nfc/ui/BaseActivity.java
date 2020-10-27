package com.gc.nfc.ui;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.gc.nfc.common.NetRequestConstant;
import com.gc.nfc.http.ThreadPool;
import com.gc.nfc.interfaces.Netcallback;
import com.gc.nfc.utils.NetUtil;

import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

    private String getResponseMessage(HttpResponse paramHttpResponse) {
        IOException iOException1;
        IOException iOException2 = null;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(paramHttpResponse.getEntity().getContent());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            String str = System.getProperty("line.separator");
            while (true) {
                String str2 = bufferedReader.readLine();
                if (str2 != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuffer.append(stringBuilder.append(str2).append(str).toString());
                    continue;
                }
                bufferedReader.close();
                String str1 = stringBuffer.toString();
                if (str1.equals(""))
                    str1 = "{\"message\":\"no value\"}";
                JSONObject jSONObject = new JSONObject();
                return jSONObject.get("message").toString();
            }
        } catch (IOException e) {
            return e.toString();
        } catch (JSONException e) {
            return e.toString();
        }
    }

    class BaseHandler extends Handler {
        private Netcallback callBack;

        public BaseHandler(Netcallback param1Netcallback) {
            this.callBack = param1Netcallback;
        }

        public void handleMessage(Message param1Message) {
            HttpResponse httpResponse = (HttpResponse)param1Message.obj;
            if (httpResponse != null && httpResponse.getStatusLine().getStatusCode() >= 400) {
                String str = BaseActivity.this.getResponseMessage(httpResponse);
                if (str == null) {
                    Toast.makeText((Context)BaseActivity.this, String.valueOf(httpResponse.getStatusLine().getStatusCode()), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText((Context)BaseActivity.this, String.valueOf(httpResponse.getStatusLine().getStatusCode()) + str, Toast.LENGTH_LONG).show();
                }
            }
            switch (param1Message.what) {
                default:
                    super.handleMessage(param1Message);
                    return;
                case 10001:
                    this.callBack.preccess(param1Message.obj, true);
                case 10002:
                case 10003:
                    break;
            }
            this.callBack.preccess(param1Message.obj, false);
        }
    }

    protected void getServer(Netcallback paramNetcallback, NetRequestConstant paramNetRequestConstant) {
        RunnableTask runnableTask = new RunnableTask(paramNetRequestConstant, new BaseHandler(paramNetcallback));
        ThreadPool.getInstance().addTask(runnableTask);
    }
    class RunnableTask implements Runnable {
        private Handler handler;

        private NetRequestConstant nrc;

        public RunnableTask(NetRequestConstant param1NetRequestConstant, Handler param1Handler) {
            this.nrc = param1NetRequestConstant;
            this.handler = param1Handler;
        }

        public void run() {
            HttpResponse httpResponse = null;
            if (NetUtil.isCheckNet(BaseActivity.this.getApplicationContext())) {
                if (this.nrc.getType() == BaseActivity.HttpRequestType.POST) {
                    httpResponse = NetUtil.httpPost(this.nrc);
                } else if (this.nrc.getType() == BaseActivity.HttpRequestType.GET) {
                    httpResponse = NetUtil.httpGet(this.nrc);
                } else if (this.nrc.getType() == BaseActivity.HttpRequestType.PUT) {
                    httpResponse = NetUtil.httpPut(this.nrc);
                }
                Message message1 = Message.obtain();
                message1.obj = httpResponse;
                message1.what = 10001;
                this.handler.sendMessage(message1);
                return;
            }
            Message message = Message.obtain();
            message.what = 10003;
            this.handler.sendMessage(message);
        }
    }
}