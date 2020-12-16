package com.gc.nfc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.gc.nfc.R;
import com.gc.nfc.app.AppContext;
import com.gc.nfc.common.NetRequestConstant;
import com.gc.nfc.common.NetUrlConstant;
import com.gc.nfc.domain.Data_Repair;
import com.gc.nfc.domain.User;
import com.gc.nfc.http.Logger;
import com.gc.nfc.interfaces.Netcallback;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCheckActivity extends BaseActivity {
    public static JSONArray m_checkOrderListJson;
    private int[] colorResouce = new int[]{R.color.orange, R.color.orange, R.color.orange, R.color.orange};
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView lv_check;
    private Data_Repair data_repair;

    private void switchActivity(int paramInt) {
        try {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("check", m_checkOrderListJson.getJSONObject(paramInt).toString());
            intent.setClass(this, CheckDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
        }
    }

    void init() {
        requestWindowFeature(1);
        setContentView(R.layout.activity_my_checks);
        lv_check = findViewById(R.id.listview);
        swipeRefreshLayout = findViewById(R.id.main_srl);
        lv_check.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                switchActivity(param1Int);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(colorResouce);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                refleshVaildChecks(true);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refleshVaildChecks(false);
    }

    /**
     * 刷新巡检列表
     *
     * @param isRefresh 是否手动刷新
     */
    public void refleshVaildChecks(boolean isRefresh) {
        User user = ((AppContext) getApplicationContext()).getUser();
        if (user == null) {
            Toast.makeText(this, "登陆会话失效", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, AutoLoginActivity.class));
            finish();
        }
        NetRequestConstant nrc = new NetRequestConstant();
        nrc.setType(HttpRequestType.GET);
        nrc.requestUrl = NetUrlConstant.BASEURL+"/api" + "/Repair/";
        nrc.context = this;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dealedUserId", user.getUsername());
        map.put("processStatus", "PTSuspending");
        nrc.setParams(map);
        getServer(new Netcallback() {
            public void preccess(Object res, boolean flag) {
                if (isRefresh) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                Logger.e("http success :"+flag);
                if(flag){
                    HttpResponse response=(HttpResponse)res;
                    if(response!=null){
                        Logger.e("http statuscode :"+response.getStatusLine().getStatusCode());
                        if(response.getStatusLine().getStatusCode()==200){
                            String string = getString(response);
                            Logger.e("refleshMends: " + string);
                            Gson gson = new Gson();
                            data_repair = gson.fromJson(string, Data_Repair.class);
                            try {
                                setData();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(MyCheckActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                        }
                    }else {
//                        Toast.makeText(MyCheckActivity.this, "未知错误，异常！",
//                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MyCheckActivity.this, "网络未连接！",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, nrc);
    }

    /**
     * 设置列表数据
     */
    private void setData() throws JSONException {
        List<Data_Repair.ItemsBean> items = data_repair.getItems();
        Gson gson = new Gson();
        String s = gson.toJson(data_repair);
        JSONObject object = new JSONObject(s);
        m_checkOrderListJson = object.getJSONArray("items");
        ArrayList datalist = new ArrayList<>();
        for (int b = 0; b < items.size(); b++) {
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            StringBuilder stringBuilder2 = new StringBuilder();
            hashMap.put("orderSn", stringBuilder2.append("巡检单号：").append(items.get(b).getRepairSn()));
            stringBuilder2 = new StringBuilder();
            hashMap.put("createTime", stringBuilder2.append("下单时间：").append(items.get(b).getCreateTime()));
            StringBuilder stringBuilder3 = new StringBuilder();
            hashMap.put("address", stringBuilder3.append(items.get(b).getRecvAddr().getCity()).append(items.get(b).getRecvAddr().getCounty()).append(items.get(b).getRecvAddr().getDetail()));
            StringBuilder stringBuilder1 = new StringBuilder();
            hashMap.put("userId", stringBuilder1.append("联系人：").append(items.get(b).getRecvName()));
            stringBuilder1 = new StringBuilder();
            hashMap.put("userPhone", stringBuilder1.append("电话：").append(items.get(b).getRecvPhone()));
            hashMap.put("orderStatus", "待处理");
            Data_Repair.ItemsBean.RepairTypeBean repairType = items.get(b).getRepairType();
            if (repairType.getIndex() == 0) {
                hashMap.put("userIcon", R.mipmap.tray);
            } else if (repairType.getIndex() == 1) {
                hashMap.put("userIcon", R.mipmap.tray);
            } else {
                hashMap.put("userIcon", R.mipmap.tray);
            }
            datalist.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(MyCheckActivity.this, datalist, R.layout.order_list_items, new String[]{"orderSn", "createTime", "userId", "userPhone", "userIcon", "address", "orderStatus", "urgent"}, new int[]{R.id.items_orderSn, R.id.items_creatTime, R.id.items_userId, R.id.items_userPhone, R.id.items_imageUserIcon, R.id.items_addressStatic, R.id.items_orderStatus, R.id.items_urgent});
        lv_check.setAdapter(simpleAdapter);
    }
}

