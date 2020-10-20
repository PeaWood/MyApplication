package com.gc.nfc.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.nfc.R;
import com.gc.nfc.app.AppContext;
import com.gc.nfc.domain.Data_Mend;
import com.gc.nfc.domain.User;
import com.gc.nfc.http.Logger;
import com.gc.nfc.http.OkHttpUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

//import com.gc.nfc.R;
//import com.gc.nfc.app.AppContext;
//import com.gc.nfc.common.NetRequestConstant;
//import com.gc.nfc.domain.User;
//import com.gc.nfc.interfaces.Netcallback;
//import com.gc.nfc.utils.SharedPreferencesHelper;

public class MineActivity extends BaseActivity implements View.OnClickListener {
    Badge badgeMends;
    Badge badgeSecurity;
    private ImageView imageView_userQRcode;
    private TextView textview_username;
    private User user;

    private void loginOut() {
        AppContext appContext = (AppContext) getApplicationContext();
        appContext.logout();
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.GET(OkHttpUtil.URL + "/sysusers/logout/" + user.getUsername(), null, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(MineActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) {
                if (response.code() == 200) {
                    Intent intent = new Intent(MineActivity.this.getApplicationContext(), LoginActivity.class);
                    MineActivity.this.startActivity(intent);
                    MineActivity.this.finish();
                    return;
                }
                Toast.makeText(MineActivity.this, "退出登录失败", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showIdentification() {
        AlertDialog.Builder builder = new AlertDialog.Builder((Context) this);
        View view = View.inflate(this, R.layout.pay_on_scan, null);
        ImageView imageView = view.findViewById(R.id.items_imageViewScanCode);
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

    void init() {
        setContentView(R.layout.activity_mine);
        LinearLayout lL_myBottle = (LinearLayout) findViewById(R.id.lL_myBottle);
        LinearLayout lL_myMend = (LinearLayout) findViewById(R.id.lL_myMend);
        ImageView mend = (ImageView) findViewById(R.id.Mend);
        ImageView security = (ImageView) findViewById(R.id.Security);
        LinearLayout lL_mySecurity = (LinearLayout) findViewById(R.id.lL_mySecurity);
        LinearLayout lL_myHistoryOrders = (LinearLayout) findViewById(R.id.lL_myHistoryOrders);
        LinearLayout lL_mySetting = (LinearLayout) findViewById(R.id.lL_mySetting);
        LinearLayout lL_myLogout = (LinearLayout) findViewById(R.id.lL_myLogout);
        LinearLayout lL_bottleRecycle = (LinearLayout) findViewById(R.id.lL_recycle);
        LinearLayout lL_mapView = (LinearLayout) findViewById(R.id.lL_mapView);
        LinearLayout lL_myHistoryCheck = (LinearLayout) findViewById(R.id.lL_myHistoryChecks);
        textview_username = (TextView) findViewById(R.id.textview_username);
        imageView_userQRcode = (ImageView) findViewById(R.id.imageView_userQRcode);
        lL_myBottle.setOnClickListener(this);
        lL_myMend.setOnClickListener(this);
        lL_mySecurity.setOnClickListener(this);
        lL_myHistoryOrders.setOnClickListener(this);
        lL_mySetting.setOnClickListener(this);
        lL_myLogout.setOnClickListener(this);
        lL_bottleRecycle.setOnClickListener(this);
        lL_mapView.setOnClickListener(this);
        lL_myHistoryCheck.setOnClickListener(this);
        imageView_userQRcode.setOnClickListener(this);
        badgeMends = (new QBadgeView(this)).bindTarget(mend);
        badgeSecurity = (new QBadgeView(this)).bindTarget(security);
        refleshVaildMends();
        refleshVaildSecuritys();
    }

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.lL_myHistoryOrders:
                //历史订单
                startActivity(new Intent(this, HistoryOrdersActivity.class));
                break;
            case R.id.lL_myHistoryChecks:
                //历史巡检
                startActivity(new Intent(this, HistoryCheckActivity.class));
                break;
            case R.id.lL_myBottle:
                //我的气瓶
                Intent intent = new Intent(this, MybottlesActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("userId", this.user.getUsername());
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.lL_myMend:
                //我的保修
                startActivity(new Intent(this, MyMendActivity.class));
                break;
            case R.id.lL_mySecurity:
                //我的安检
                startActivity(new Intent(this, MySecurityActivity.class));
                break;
            case R.id.lL_mySetting:
                //系统版本
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.lL_myLogout:
                //退出登录
                loginOut();
                break;
            case R.id.imageView_userQRcode:
                //二维码
                showIdentification();
                break;
            case R.id.lL_recycle:
                //退换货
//                startActivity(new Intent(this, BottleRecycleActivity.class));
                break;
            case R.id.lL_mapView:
                //查看地图
                startActivity(new Intent(this, MapViewActivity.class));
                break;
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        AppContext appContext = (AppContext) getApplicationContext();
        user = appContext.getUser();
        if (user == null) {
            Toast.makeText(this, "登陆会话失效", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, AutoLoginActivity.class));
            finish();
            return;
        }
        showUserInfo();
    }

    /**
     * 顶部个人信息
     */
    private void showUserInfo() {
        String name = user.getUsername();
        String userInfo = name + "\n(" + user.getGroupName() + "|" + user.getDepartmentName() + ")";
        textview_username.setText(userInfo);
        String str = "http://www.gasmart.com.cn/api/pay/QRCode?text=" + name;
        try {
            URL uRL = new URL(str);
            Bitmap bitmap = BitmapFactory.decodeStream(uRL.openStream());
            imageView_userQRcode.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示我的报修角标
     */
    public void refleshVaildMends() {
        Logger.e("refleshVaildMends");
        AppContext appContext = (AppContext) getApplicationContext();
        user = appContext.getUser();
        if (user == null) {
            Toast.makeText(this, "登陆会话失效", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, AutoLoginActivity.class));
            finish();
        }
        Map<String, String> map = new HashMap();
        map.put("dealedUserId", user.getUsername());
        map.put("processStatus", "PTHandling");
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.GET(OkHttpUtil.URL + "/Mend", map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(MineActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() != 200) {
                    Toast.makeText(MineActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                    return;
                }
                String string = response.body().string();
                Logger.e("refleshVaildMends: " + string);
                Gson gson = new Gson();
                Data_Mend dataMend = gson.fromJson(string, Data_Mend.class);
                int size = dataMend.getItems().size();
                badgeMends.setBadgeNumber(size);
                badgeMends.setBadgeGravity(Gravity.END | Gravity.TOP);
            }
        });
    }

    /**
     * 显示我的安检角标
     */
    public void refleshVaildSecuritys() {
        User user = ((AppContext) getApplicationContext()).getUser();
        if (user == null) {
            Toast.makeText(this, "登陆会话失效", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, AutoLoginActivity.class));
            finish();
        }
        Map<String, String> map = new HashMap();
        map.put("dealedUserId", user.getUsername());
        map.put("processStatus", "PTHandling");
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.GET(OkHttpUtil.URL + "/Security", map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(MineActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() != 200) {
                    Toast.makeText(MineActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                    return;
                }
                String string = response.body().string();
                Logger.e("refleshVaildSecuritys: " + string);
                Gson gson = new Gson();
                Data_Mend mendData = gson.fromJson(string, Data_Mend.class);
                int size = mendData.getItems().size();
                badgeSecurity.setBadgeNumber(size);
                badgeSecurity.setBadgeGravity(Gravity.END | Gravity.TOP);
            }
        });
    }
}

