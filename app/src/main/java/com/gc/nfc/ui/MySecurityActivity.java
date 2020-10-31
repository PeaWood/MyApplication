package com.gc.nfc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.gc.nfc.R;
import com.gc.nfc.app.AppContext;
import com.gc.nfc.domain.Data_Mend;
import com.gc.nfc.domain.Data_MySecurity;
import com.gc.nfc.domain.User;
import com.gc.nfc.http.Logger;
import com.gc.nfc.http.OkHttpUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class MySecurityActivity extends BaseActivity implements View.OnClickListener, AbsListView.OnScrollListener {
    public static JSONArray m_checkOrderListJson;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ImageView imageView;

    private void switchActivity(int paramInt) {
        try {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("check", m_checkOrderListJson.getJSONObject(paramInt).toString());
            intent.setClass(this, SecurityDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
        }
    }

    void init() {
        requestWindowFeature(1);
        setContentView(R.layout.activity_my_security);
        listView = (ListView) findViewById(R.id.listview);
        imageView = (ImageView) findViewById(R.id.img_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                switchActivity(param1Int);
            }
        });
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_srl);
        swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorAccent, R.color.colorAccent, R.color.colorAccent});
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                refleshVaildChecks(true);
            }
        });
    }

    public void onClick(View paramView) {
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refleshVaildChecks(false);
    }

    public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3) {
    }

    public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
    }

    public void refleshVaildChecks(boolean isrefresh) {
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
        util.GET(OkHttpUtil.URL + "/Security/", map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                if (isrefresh) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                Toast.makeText(MySecurityActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (isrefresh) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (response.code() != 200) {
                    Toast.makeText(MySecurityActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                    return;
                }
                String string = response.body().string();
                Logger.e("refleshVaildChecks: " + string);
                Gson gson = new Gson();
                setData(gson.fromJson(string, Data_MySecurity.class));
            }

            private void setData(Data_MySecurity data_mend) {
                Gson gson = new Gson();
                String s = gson.toJson(data_mend.getItems());
                try {
                    m_checkOrderListJson = new JSONArray(s);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayList list = new ArrayList();
                for (byte b = 0; b < data_mend.getItems().size(); b++) {
                    Data_MySecurity.ItemsBean itemsBean = data_mend.getItems().get(b);
                    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                    StringBuilder stringBuilder2 = new StringBuilder();
                    hashMap.put("orderSn", stringBuilder2.append("安检单号：").append(itemsBean.getSecuritySn()).toString());
                    stringBuilder2 = new StringBuilder();
                    hashMap.put("createTime", stringBuilder2.append("下单时间：").append(itemsBean.getCreateTime()).toString());
                    StringBuilder stringBuilder3 = new StringBuilder();
                    hashMap.put("address", stringBuilder3.append(itemsBean.getRecvAddr().getCity()).append(itemsBean.getRecvAddr().getCounty()).append(itemsBean.getRecvAddr().getDetail()).toString());
                    StringBuilder stringBuilder1 = new StringBuilder();
                    hashMap.put("userId", stringBuilder1.append("联系人：").append(itemsBean.getRecvName()).toString());
                    stringBuilder1 = new StringBuilder();
                    hashMap.put("userPhone", stringBuilder1.append("电话：").append(itemsBean.getRecvPhone()).toString());
                    hashMap.put("orderStatus", "待处理");
                    hashMap.put("userIcon", R.mipmap.ic_menu_user_on);
                    list.add(hashMap);
                }
                SimpleAdapter simpleAdapter = new SimpleAdapter(MySecurityActivity.this,
                        list,
                        R.layout.order_list_items,
                        new String[]{"orderSn", "createTime", "userId", "userPhone", "userIcon", "address", "orderStatus", "urgent"},
                        new int[]{R.id.items_orderSn, R.id.items_creatTime, R.id.items_userId, R.id.items_userPhone, R.id.items_imageUserIcon, R.id.items_address, R.id.items_orderStatus, R.id.items_urgent});
                listView.setAdapter(simpleAdapter);
            }
        });
    }
}