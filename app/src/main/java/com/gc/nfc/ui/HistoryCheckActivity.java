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
import com.gc.nfc.domain.Data_Repair;
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

public class HistoryCheckActivity extends BaseActivity {
    public static JSONArray m_checkOrderListJson;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private Data_Repair data_repair;
    private ImageView imageView;

    private void switchActivity(int paramInt) {
        try {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("check", m_checkOrderListJson.getJSONObject(paramInt).toString());
            intent.setClass(this, CheckDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
        }
    }

    void init() {
        requestWindowFeature(1);
        setContentView(R.layout.activity_my_checks);
        listView = (ListView) findViewById(R.id.listview);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                switchActivity(param1Int);
            }
        });
        imageView = (ImageView) findViewById(R.id.img_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refleshVaildChecks(false);
    }

    public void refleshVaildChecks(boolean isrefresh) {
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
        map.put("processStatus", "PTSolved");
        map.put("pageNo", "1");
        map.put("pageSize", "20");
        map.put("orderBy", "id desc");
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
                            Logger.e("refleshVaildChecks: " + string);
                            Gson gson = new Gson();
                            data_repair = gson.fromJson(string, Data_Repair.class);
                            setData(data_repair);
                        }else{
                            Toast.makeText(HistoryCheckActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(HistoryCheckActivity.this, "未知错误，异常！",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(HistoryCheckActivity.this, "网络未连接！",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, nrc);
    }

    private void setData(Data_Repair data_repair) {
        Gson gson = new Gson();
        try {
            JSONArray array = new JSONArray(gson.toJson(data_repair.getItems()));
            m_checkOrderListJson = array;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList datalist = new ArrayList<>();
        for (int j = 0; j < data_repair.getItems().size(); j++) {
            Data_Repair.ItemsBean itemsBean = data_repair.getItems().get(j);
            HashMap<Object, Object> hashMap = new HashMap();
            StringBuilder stringBuilder2 = new StringBuilder();
            hashMap.put("orderSn", stringBuilder2.append("巡检单号：").append(itemsBean.getRepairSn()).toString());
            stringBuilder2 = new StringBuilder();
            hashMap.put("createTime", stringBuilder2.append("下单时间：").append(itemsBean.getCreateTime()).toString());
            Data_Repair.ItemsBean.RecvAddrBean recvAddr = itemsBean.getRecvAddr();
            StringBuilder stringBuilder3 = new StringBuilder();
            hashMap.put("address", stringBuilder3.append(recvAddr.getCity()).append(recvAddr.getCounty()).append(recvAddr.getDetail()).toString());
            StringBuilder stringBuilder1 = new StringBuilder();
            hashMap.put("userId", stringBuilder1.append("联系人：").append(itemsBean.getRecvName()).toString());
            stringBuilder1 = new StringBuilder();
            hashMap.put("userPhone", stringBuilder1.append("电话：").append(itemsBean.getRecvPhone()).toString());
            hashMap.put("orderStatus", "已处理");
            Data_Repair.ItemsBean.RepairTypeBean repairType = itemsBean.getRepairType();
            if (repairType.getIndex() == 0) {
                hashMap.put("userIcon", R.mipmap.tray);
            } else if (repairType.getIndex() == 1) {
                hashMap.put("userIcon", R.mipmap.tray);
            } else {
                hashMap.put("userIcon", R.mipmap.tray);
            }
            datalist.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(HistoryCheckActivity.this, datalist, R.layout.order_list_items, new String[]{"orderSn", "createTime", "userId", "userPhone", "userIcon", "address", "orderStatus", "urgent"}, new int[]{R.id.items_orderSn, R.id.items_creatTime, R.id.items_userId, R.id.items_userPhone, R.id.items_imageUserIcon, R.id.items_addressStatic, R.id.items_orderStatus, R.id.items_urgent});
        listView.setAdapter(simpleAdapter);
    }
}