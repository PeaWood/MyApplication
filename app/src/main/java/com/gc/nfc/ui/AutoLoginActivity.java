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
import com.gc.nfc.common.NetUrlConstant;
import com.gc.nfc.domain.Data_User;
import com.gc.nfc.domain.User;
import com.gc.nfc.interfaces.Netcallback;
import com.gc.nfc.utils.JellyInterpolator;
import com.gc.nfc.utils.NetUtil;
import com.gc.nfc.utils.SharedPreferencesHelper;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class AutoLoginActivity extends BaseActivity {
    private ObjectAnimator m_animator;
    private String name;
    private String password;
    private View progress;

    private void autoLogin() {
        SharedPreferencesHelper.initial(this);
        name = (String) SharedPreferencesHelper.get("username", "");
        password = (String) SharedPreferencesHelper.get("password", "");
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            Toast.makeText(AutoLoginActivity.this, "请检查账号和密码", Toast.LENGTH_LONG).show();
        }
        // get请求
        NetRequestConstant nrc = new NetRequestConstant();
        nrc.setType(HttpRequestType.GET);

        nrc.requestUrl = NetUrlConstant.LOGINURL;
        nrc.context = this;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", name);
        params.put("password", password);
        nrc.setParams(params);
        getServer(new Netcallback() {
            public void preccess(Object res, boolean flag) {
                if (flag) {
                    HttpResponse response = (HttpResponse) res;
                    if (response != null) {
                        if (response.getStatusLine().getStatusCode() == 200) {
                            //设置登录会话的cookies
                            NetUtil.setLoginCookies();
                            Intent data = new Intent();
                            data.putExtra("userId", name);
                            setResult(12, data);
                            SharedPreferencesHelper.put("username", name);
                            SharedPreferencesHelper.put("password", password);
                            MediaPlayer music = MediaPlayer.create(AutoLoginActivity.this, R.raw.start_working);
                            music.start();
                            Gson gson = new Gson();
                            Data_User userdata = gson.fromJson(getString(response), Data_User.class);
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
                            AppContext appContext = (AppContext) AutoLoginActivity.this
                                .getApplicationContext();
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
                                AutoLoginActivity.this.finish();
                                return;
                            }
                            if (user.getGroupCode().equals("00007")) {
                                //DiaoBoActivity
                                Toast.makeText(AutoLoginActivity.this, "更换角色！", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(AutoLoginActivity.this, "账号或密码不正确", Toast.LENGTH_LONG).show();
                        }
                    } else {
//                        Toast.makeText(AutoLoginActivity.this, "未知错误，异常！",
//                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AutoLoginActivity.this, "网络未连接！",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, nrc);
    }

    private void inputAnimator() {
        this.progress.setVisibility(View.INVISIBLE);
        progressAnimator(this.progress);
    }

    @SuppressLint("NewApi")
    private void progressAnimator(View paramView) {
        this.m_animator = ObjectAnimator.ofPropertyValuesHolder(paramView, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat("scaleX", new float[]{0.5F, 1.0F}), PropertyValuesHolder.ofFloat("scaleY", new float[]{0.5F, 1.0F})});
        this.m_animator.setDuration(1000L);
        this.m_animator.setInterpolator((TimeInterpolator) new JellyInterpolator());
        this.m_animator.start();
        this.m_animator.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator param1Animator) {
            }

            public void onAnimationEnd(Animator param1Animator) {
                AutoLoginActivity.this.autoLogin();
                AutoLoginActivity.this.progress.setVisibility(View.GONE);
            }

            public void onAnimationRepeat(Animator param1Animator) {
            }

            public void onAnimationStart(Animator param1Animator) {
            }
        });
    }

    void init() {
        requestWindowFeature(1);
        ((AppContext) getApplicationContext()).setPreferences(getPreferences(0));
        setContentView(R.layout.layout_progress);
        this.progress = findViewById(R.id.progressBar2);
        SharedPreferencesHelper.initial((Context) this);
        inputAnimator();
    }

    @SuppressLint("NewApi")
    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder()).permitAll().build());
    }
}

