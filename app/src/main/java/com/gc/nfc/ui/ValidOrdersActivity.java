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
import com.gc.nfc.domain.Data_TaskOrders;
import com.gc.nfc.domain.User;
import com.gc.nfc.http.Logger;
import com.gc.nfc.http.OkHttpUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class ValidOrdersActivity extends BaseActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    private Data_TaskOrders data_taskOrders;
    private ListView lv_order;
    private int[] colorResouce = new int[]{R.color.orange, R.color.orange, R.color.orange, R.color.orange};

    void init() {
        requestWindowFeature(1);
        setContentView(R.layout.activity_valid_orders);
        lv_order = findViewById(R.id.listview);
        swipeRefreshLayout = findViewById(R.id.main_srl);
        lv_order.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                switchActivity(param1Int);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(colorResouce);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                refreshVaildOrders(true);
            }
        });
    }

    protected void onResume() {
        super.onResume();
        refreshVaildOrders(false);
    }

    /**
     * 刷新订单列表
     * @param isRefresh 是否手动刷新
     */
    public void refreshVaildOrders(boolean isRefresh) {
        Logger.e("[refresh Orders]" + " isRefresh" +isRefresh);
        User user = ((AppContext) getApplicationContext()).getUser();
        if (user == null) {
            Toast.makeText(this, "登陆会话失效", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, AutoLoginActivity.class));
            finish();
        }
        Map<String, String> map = new HashMap();
        map.put("orderStatus", String.valueOf(0));
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.GET(OkHttpUtil.URL + "/TaskOrders/" + user.getUsername(), map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                if(isRefresh){
                    swipeRefreshLayout.setRefreshing(false);
                }
                Toast.makeText(ValidOrdersActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if(isRefresh){
                    swipeRefreshLayout.setRefreshing(false);
                }
                if (response.code() != 200) {
                    Toast.makeText(ValidOrdersActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                    return;
                }
                String string = response.body().string();
                Logger.e("refreshVaildOrders: " + string);
                Gson gson = new Gson();
                data_taskOrders = gson.fromJson(string, Data_TaskOrders.class);
                setData();
            }
        });
    }

    /**
     * 设置订单列表数据
     */
    private void setData() {
        ArrayList datalist = new ArrayList<>();
        for (int j = 0; j < data_taskOrders.getItems().size(); j++) {
            Data_TaskOrders.ItemsBean itemsBean = data_taskOrders.getItems().get(j);
            if (itemsBean != null && itemsBean.getObject() != null && itemsBean.getObject().getOrderTriggerType() != null) {

                int index = itemsBean.getObject().getOrderTriggerType().getIndex();
                HashMap<Object, Object> hashMap = new HashMap();
                if (index == 1) {
                    StringBuilder stringBuilder = new StringBuilder();
                    hashMap.put("orderSn", stringBuilder.append("(按斤)订单编号：").append(itemsBean.getObject().getOrderSn()));
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    hashMap.put("orderSn", stringBuilder.append("(普通)订单编号：").append(itemsBean.getObject().getOrderSn()));
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                hashMap.put("createTime", stringBuilder2.append("下单时间：").append(itemsBean.getObject().getCreateTime()));
                StringBuilder stringBuilder3 = new StringBuilder();
                hashMap.put("address", stringBuilder3.append(itemsBean.getObject().getRecvAddr().getCity()).append(itemsBean.getObject().getRecvAddr().getCounty()).append(itemsBean.getObject().getRecvAddr().getDetail()));
                StringBuilder stringBuilder1 = new StringBuilder();
                hashMap.put("userId", stringBuilder1.append("联系人：").append(itemsBean.getObject().getRecvName()));
                stringBuilder1 = new StringBuilder();
                hashMap.put("userPhone", stringBuilder1.append("电话：").append(itemsBean.getObject().getRecvPhone()));
                int orderStatus = itemsBean.getObject().getOrderStatus();
                String[] strings = new String[5];
                strings[0] = "待接单";
                strings[1] = "派送中";
                strings[2] = "待核单";
                strings[3] = "已结束";
                strings[4] = "已作废";
                hashMap.put("orderStatus", strings[orderStatus]);
                if (itemsBean.getObject().getCustomer().getSettlementType().getCode().equals("00003")) {
                    hashMap.put("userIcon", R.mipmap.icon_ticket_user);
                } else if (itemsBean.getObject().getCustomer().getSettlementType().getCode().equals("00002")) {
                    hashMap.put("userIcon", R.mipmap.icon_month_user);
                } else {
                    hashMap.put("userIcon", R.mipmap.icon_common_user);
                }
                if (itemsBean.getObject().isUrgent()) {
                    hashMap.put("urgent", "加急");
                } else {
                    hashMap.put("urgent", "");
                }
                datalist.add(hashMap);
            }
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(ValidOrdersActivity.this, datalist, R.layout.order_list_items, new String[]{"orderSn", "createTime", "userId", "userPhone", "userIcon", "address", "orderStatus", "urgent"}, new int[]{R.id.items_orderSn, R.id.items_creatTime, R.id.items_userId, R.id.items_userPhone, R.id.items_imageUserIcon, R.id.items_addressStatic, R.id.items_orderStatus, R.id.items_urgent});
        lv_order.setAdapter(simpleAdapter);
    }

    /**
     * 跳转到详情页面
     * @param position 订单下标
     */
    private void switchActivity(int position) {
        try {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            String str = data_taskOrders.getItems().get(position).getId();
            Gson gson = new Gson();
            JSONObject jSONObject2 = new JSONObject(gson.toJson(data_taskOrders.getItems().get(position).getObject()));
            bundle.putString("taskId", str);
            bundle.putInt("orderStatus", 0);
            bundle.putString("order", jSONObject2.toString());
            bundle.putString("businessKey", data_taskOrders.getItems().get(position).getProcess().getBuinessKey());
            intent.setClass(this, OrderDetailActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
        }
    }
}

