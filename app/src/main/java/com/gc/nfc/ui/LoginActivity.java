package com.gc.nfc.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.nfc.R;
import com.gc.nfc.app.AppContext;
import com.gc.nfc.common.NetRequestConstant;
import com.gc.nfc.common.NetUrlConstant;
import com.gc.nfc.domain.Data_User;
import com.gc.nfc.domain.User;
import com.gc.nfc.http.Logger;
import com.gc.nfc.http.OkHttpUtil;
import com.gc.nfc.interfaces.Netcallback;
import com.gc.nfc.utils.JellyInterpolator;
import com.gc.nfc.utils.NetUtil;
import com.gc.nfc.utils.SharedPreferencesHelper;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private TextView mBtnLogin;
    private float mHeight;
    private View mInputLayout;
    private float mWidth;
    private ObjectAnimator m_animator;
    private EditText et_pin;
    private EditText et_name;
    private View progress;
    private String name;
    private String password;

    private void inputAnimator(View paramView, float paramFloat1, float paramFloat2) {
        this.progress.setVisibility(View.VISIBLE);
        progressAnimator(this.progress);
        this.mInputLayout.setVisibility(View.INVISIBLE);
    }

    /**
     * 登录
     */
    private void login() {
        name = et_name.getText().toString();
        password = et_pin.getText().toString();
        name = "psy";
        password = "111111";
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "请检查账号和密码", Toast.LENGTH_LONG).show();
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
                if(flag){
                    HttpResponse response=(HttpResponse)res;
                    if(response!=null){
                        if(response.getStatusLine().getStatusCode()==200){
                            try {
                                //设置登录会话的cookies
                                NetUtil.setLoginCookies();
                                JSONObject userJson = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                                JSONObject groupJson = userJson.getJSONObject("userGroup");
                                JSONObject departmentJson =  userJson.getJSONObject("department");
                                String groupCode = groupJson.optString("code");
                                String groupName = groupJson.optString("name");
                                String departmentCode = departmentJson.optString("code");
                                String departmentName = departmentJson.optString("name");
                                Intent data = new Intent();
                                data.putExtra("userId", name);
                                AppContext appContext = (AppContext) getApplicationContext();
                                User user = new User();
                                user.setUsername(name);
                                user.setPassword(password);
                                user.setDepartmentCode(departmentCode);
                                user.setDepartmentName(departmentName);
                                user.setGroupCode(groupCode);
                                user.setGroupName(groupName);
                                appContext.setUser(user);
                                setResult(12, data);
                                SharedPreferencesHelper.put("username", name);
                                SharedPreferencesHelper.put("password", password);
                                MediaPlayer music = MediaPlayer.create(LoginActivity.this, R.raw.start_working);
                                music.start();
                            }catch (IOException e){
                                Toast.makeText(LoginActivity.this, "未知错误，异常！",
                                        Toast.LENGTH_LONG).show();
                            }catch (JSONException e) {
                                Toast.makeText(LoginActivity.this, "未知错误，异常！",
                                        Toast.LENGTH_LONG).show();
                            }

                        }else{
                            Toast.makeText(LoginActivity.this, "账号或密码不正确", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, "未知错误，异常！",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "网络未连接！",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, nrc);
        Map map = new HashMap();
        map.put("userId", name);
        map.put("password", password);
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.GET(OkHttpUtil.URL + "/sysusers/login", map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(LoginActivity.this, "网络连接错误！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() != 200) {
                    Toast.makeText(LoginActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                    return;
                }
                String string = response.body().string();
                Logger.e("login: " + string);
                Gson gson = new Gson();
                Data_User userdata = gson.fromJson(string, Data_User.class);
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
                AppContext appContext = (AppContext) LoginActivity.this.getApplicationContext();
                appContext.setUser(user);
                MediaPlayer.create(LoginActivity.this, R.raw.start_working).start();
                if (user.getGroupCode().equals("00005") || user.getGroupCode().equals("00006")) {
                    //StockManagerActivity
                    Toast.makeText(LoginActivity.this, "更换角色！", Toast.LENGTH_LONG).show();
                    return;
                }
                if (user.getGroupCode().equals("00003")) {
                    //配送员
                    Intent starter = new Intent(LoginActivity.this, MainlyActivity.class);
                    startActivity(starter);
                    LoginActivity.this.finish();
                    return;
                }
                if (user.getGroupCode().equals("00007")) {
                    //DiaoBoActivity
                    Toast.makeText(LoginActivity.this, "更换角色！", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void progressAnimator(View paramView) {
        this.m_animator = ObjectAnimator.ofPropertyValuesHolder(paramView, new PropertyValuesHolder[]{PropertyValuesHolder.ofFloat("scaleX", new float[]{0.5F, 1.0F}), PropertyValuesHolder.ofFloat("scaleY", new float[]{0.5F, 1.0F})});
        this.m_animator.setDuration(1000L);
        this.m_animator.setInterpolator(new JellyInterpolator());
        this.m_animator.start();
        this.m_animator.addListener(new Animator.AnimatorListener() {
            public void onAnimationCancel(Animator param1Animator) {
            }

            public void onAnimationEnd(Animator param1Animator) {
                LoginActivity.this.login();
                LoginActivity.this.progress.setVisibility(View.INVISIBLE);
                LoginActivity.this.mInputLayout.setVisibility(View.VISIBLE);
            }

            public void onAnimationRepeat(Animator param1Animator) {
            }

            public void onAnimationStart(Animator param1Animator) {
            }
        });
    }

    void init() {
        requestWindowFeature(1);
        ((AppContext) getApplication()).setPreferences(getPreferences(MODE_PRIVATE));
        setContentView(R.layout.activity_login);
        SharedPreferencesHelper.initial((Context)this);
        this.mBtnLogin = (TextView) findViewById(R.id.main_btn_login);
        this.progress = findViewById(R.id.layout_progress);
        this.mInputLayout = findViewById(R.id.input_layout);
        this.et_name = (EditText) findViewById(R.id.input_userId);
        this.et_pin = (EditText) findViewById(R.id.input_password);
        this.mBtnLogin.setOnClickListener(this);
    }

    public void onClick(View paramView) {
        this.mWidth = this.mBtnLogin.getMeasuredWidth();
        this.mHeight = this.mBtnLogin.getMeasuredHeight();
        inputAnimator(this.mInputLayout, this.mWidth, this.mHeight);
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder()).permitAll().build());
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
