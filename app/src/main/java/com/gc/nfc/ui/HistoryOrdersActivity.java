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
import com.gc.nfc.domain.Data_Order;
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
import java.util.Map;

public class HistoryOrdersActivity extends BaseActivity {
    public static JSONArray m_taskOrderListJson;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private Data_Order data_taskOrders;
    private ImageView imageView;

    private void switchActivity(int paramInt) {
        try {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            Gson gson = new Gson();
            JSONObject jSONObject2 = new JSONObject(gson.toJson(data_taskOrders.getItems().get(paramInt)));
            bundle.putString("order", jSONObject2.toString());
            bundle.putInt("orderStatus", -1);
            intent.setClass(this, OrderDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (Exception jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
        }
    }

    void init() {
        requestWindowFeature(1);
        setContentView(R.layout.activity_history_orders);
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
                refleshVaildOrders(true);
            }
        });
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refleshVaildOrders(false);
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void refleshVaildOrders(boolean isrefresh) {
        User user = ((AppContext) getApplicationContext()).getUser();
        if (user == null) {
            Toast.makeText(this, "登陆会话失效", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, AutoLoginActivity.class));
            finish();
        }
        NetRequestConstant nrc = new NetRequestConstant();
        nrc.setType(HttpRequestType.GET);
        nrc.requestUrl = NetUrlConstant.BASEURL+"/api" + "/Orders/";
        nrc.context = this;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dispatcherId", user.getUsername());
        map.put("orderBy", "id desc");
        map.put("pageNo", "1");
        map.put("pageSize", "10");
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
                            Logger.e("refleshVaildOrders: " + string);
                            Gson gson = new Gson();
                            data_taskOrders = gson.fromJson(string, Data_Order.class);
                            setData(data_taskOrders);
                        }else{
                            Toast.makeText(HistoryOrdersActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                        }
                    }else {
//                        Toast.makeText(HistoryOrdersActivity.this, "未知错误，异常！",
//                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(HistoryOrdersActivity.this, "网络未连接！",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, nrc);
    }

    private void setData(Data_Order data_taskOrders) {
        Gson gson = new Gson();
        try {
            JSONArray array = new JSONArray(gson.toJson(data_taskOrders.getItems()));
            m_taskOrderListJson = array;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList datalist = new ArrayList<>();
        for (int j = 0; j < data_taskOrders.getItems().size(); j++) {
            Data_Order.ItemsBean itemsBean = data_taskOrders.getItems().get(j);
            HashMap<Object, Object> hashMap = new HashMap();
            StringBuilder stringBuilder = new StringBuilder();
            hashMap.put("orderSn", stringBuilder.append("(按斤)订单编号：").append(itemsBean.getOrderSn()));
            StringBuilder stringBuilder2 = new StringBuilder();
            hashMap.put("createTime", stringBuilder2.append("下单时间：").append(itemsBean.getCreateTime()));
            StringBuilder stringBuilder3 = new StringBuilder();
            hashMap.put("address", stringBuilder3.append(itemsBean.getRecvAddr().getCity()).append(itemsBean.getRecvAddr().getCounty()).append(itemsBean.getRecvAddr().getDetail()));
            StringBuilder stringBuilder1 = new StringBuilder();
            hashMap.put("userId", stringBuilder1.append("联系人：").append(itemsBean.getRecvName()));
            stringBuilder1 = new StringBuilder();
            hashMap.put("userPhone", stringBuilder1.append("电话：").append(itemsBean.getRecvPhone()));
            int orderStatus = itemsBean.getOrderStatus();
            String[] strings = new String[5];
            strings[0] = "待接单";
            strings[1] = "派送中";
            strings[2] = "待核单";
            strings[3] = "已结束";
            strings[4] = "已作废";
            hashMap.put("orderStatus", strings[orderStatus]);
            if (itemsBean.getCustomer().getSettlementType().getCode().equals("00003")) {
                hashMap.put("userIcon", R.mipmap.icon_ticket_user);
            } else if (itemsBean.getCustomer().getSettlementType().getCode().equals("00002")) {
                hashMap.put("userIcon", R.mipmap.icon_month_user);
            } else {
                hashMap.put("userIcon", R.mipmap.icon_common_user);
            }
            if (itemsBean.isUrgent()) {
                hashMap.put("urgent", "加急");
            } else {
                hashMap.put("urgent", "");
            }
            datalist.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(HistoryOrdersActivity.this, datalist, R.layout.order_list_items, new String[]{"orderSn", "createTime", "userId", "userPhone", "userIcon", "address", "orderStatus", "urgent"}, new int[]{R.id.items_orderSn, R.id.items_creatTime, R.id.items_userId, R.id.items_userPhone, R.id.items_imageUserIcon, R.id.items_addressStatic, R.id.items_orderStatus, R.id.items_urgent});
        listView.setAdapter(simpleAdapter);
    }
}