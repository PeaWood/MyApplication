package com.gc.nfc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.gc.nfc.R;
import com.gc.nfc.app.AppContext;
import com.gc.nfc.common.NetRequestConstant;
import com.gc.nfc.common.NetUrlConstant;
import com.gc.nfc.domain.Data_Mend;
import com.gc.nfc.domain.User;
import com.gc.nfc.http.Logger;
import com.gc.nfc.interfaces.Netcallback;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyMendActivity extends BaseActivity implements View.OnClickListener {
    public static JSONArray m_checkOrderListJson;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ImageView image;

    private void switchActivity(int paramInt) {
        try {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("check", m_checkOrderListJson.getJSONObject(paramInt).toString());
            intent.setClass(this, MendDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
        }
    }

    void init() {
        requestWindowFeature(1);
        setContentView(R.layout.activity_my_mend);
        listView = findViewById(R.id.listview);
        image = findViewById(R.id.img_back);
        image.setOnClickListener(backOnClickListener);
        (listView).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                MyMendActivity.this.switchActivity(param1Int);
            }
        });
        this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_srl);
        this.swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorAccent, R.color.colorAccent, R.color.colorAccent});
        this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                refleshMends(true);
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
        refleshMends(false);
    }

    public void refleshMends(boolean isrefresh) {
        User user = ((AppContext) getApplicationContext()).getUser();
        if (user == null) {
            Toast.makeText(this, "登陆会话失效", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, AutoLoginActivity.class));
            finish();
        }
        NetRequestConstant nrc = new NetRequestConstant();
        nrc.setType(HttpRequestType.GET);
        nrc.requestUrl = NetUrlConstant.BASEURL+"/api" + "/Mend/";
        nrc.context = this;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dealedUserId", user.getUsername());
        map.put("processStatus", "PTHandling");
        nrc.setParams(map);
        getServer(new Netcallback() {
            public void preccess(Object res, boolean flag) {
                Logger.e("http success :"+flag);
                if (isrefresh) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                if(flag){
                    HttpResponse response=(HttpResponse)res;
                    if(response!=null){
                        Logger.e("http statuscode :"+response.getStatusLine().getStatusCode());
                        if(response.getStatusLine().getStatusCode()==200){
                            String string = getString(response);
                            Logger.e("refleshMends: " + string);
                            Gson gson = new Gson();
                            setData(gson.fromJson(string, Data_Mend.class));
                        }else{
                            Toast.makeText(MyMendActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                        }
                    }else {
//                        Toast.makeText(MyMendActivity.this, "未知错误，异常！",
//                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MyMendActivity.this, "网络未连接！",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, nrc);
    }

    private void setData(Data_Mend data_mend) {
        Gson gson = new Gson();
        try {
            JSONArray array = new JSONArray(gson.toJson(data_mend.getItems()));
            m_checkOrderListJson = array;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList list = new ArrayList();
        int size = data_mend.getItems().size();
        for (byte b = 0; b < size; b++) {
            Data_Mend.ItemsBean bean = data_mend.getItems().get(b);
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            StringBuilder stringBuilder2 = new StringBuilder();
            hashMap.put("orderSn", stringBuilder2.append("报修单号：").append(bean.getMendSn()).toString());
            stringBuilder2 = new StringBuilder();
            hashMap.put("createTime", stringBuilder2.append("下单时间：").append(bean.getCreateTime()).toString());
            Data_Mend.ItemsBean.RecvAddrBean recvAddr = bean.getRecvAddr();
            StringBuilder stringBuilder3 = new StringBuilder();
            hashMap.put("address", stringBuilder3.append(recvAddr.getCity()).append(recvAddr.getCounty()).append(recvAddr.getDetail()).toString());
            StringBuilder stringBuilder1 = new StringBuilder();
            hashMap.put("userId", stringBuilder1.append("联系人：").append(bean.getRecvName()));
            stringBuilder1 = new StringBuilder();
            hashMap.put("userPhone", stringBuilder1.append("电话：").append(bean.getRecvPhone()).toString());
            hashMap.put("orderStatus", "待处理");
            hashMap.put("userIcon", R.drawable.alert);
            list.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(MyMendActivity.this,
                list,
                R.layout.order_list_items,
                new String[]{"orderSn", "createTime", "userId", "userPhone", "userIcon", "address", "orderStatus", "urgent"},
                new int[]{R.id.items_orderSn, R.id.items_creatTime, R.id.items_userId, R.id.items_userPhone, R.id.items_imageUserIcon, R.id.items_address, R.id.items_orderStatus, R.id.items_urgent});
        listView.setAdapter(simpleAdapter);
    }

    private View.OnClickListener backOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            finish();
        }
    };
}