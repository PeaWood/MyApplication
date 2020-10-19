package com.gc.nfc.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.gc.nfc.R;
import com.gc.nfc.app.AppContext;
import com.gc.nfc.common.NetRequestConstant;
import com.gc.nfc.domain.User;
import com.gc.nfc.interfaces.Netcallback;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HistoryOrdersActivity extends BaseActivity implements View.OnClickListener, AbsListView.OnScrollListener {
  public static JSONArray m_taskOrderListJson;
  
  private Handler handler = new Handler() {
      public void handleMessage(Message param1Message) {
        super.handleMessage(param1Message);
        switch (param1Message.what) {
          default:
            return;
          case 257:
            break;
        } 
        if (HistoryOrdersActivity.this.swipeRefreshLayout.isRefreshing())
          HistoryOrdersActivity.this.swipeRefreshLayout.setRefreshing(false); 
      }
    };
  
  private SwipeRefreshLayout swipeRefreshLayout;
  
  private void switchActivity(int paramInt) {
    try {
//      Intent intent = new Intent();
//      Bundle bundle = new Bundle();
//      bundle.putString("order", m_taskOrderListJson.getJSONObject(paramInt).toString());
//      bundle.putInt("orderStatus", -1);
//      intent.setClass((Context)this, OrderDetailActivity.class);
//      intent.putExtras(bundle);
//      startActivity(intent);
    } catch (Exception jSONException) {
      Toast.makeText((Context)this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
    } 
  }
  
  void init() {
    requestWindowFeature(1);
    setContentView(R.layout.activity_history_orders);
    ((ListView)findViewById(R.id.listview)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
            HistoryOrdersActivity.this.switchActivity(param1Int);
          }
        });
    this.swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.main_srl);
    this.swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorAccent, R.color.colorAccent, R.color.colorAccent});
    this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
          public void onRefresh() {
            HistoryOrdersActivity.this.refleshVaildOrders();
          }
        });
    refleshVaildOrders();
  }
  
  public void onClick(View paramView) {}
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
  }
  
  protected void onDestroy() {
    super.onDestroy();
  }
  
  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3) {}
  
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {}
  
  public void refleshVaildOrders() {
//    User user = ((AppContext)getApplicationContext()).getUser();
//    if (user == null) {
//      Toast.makeText((Context)this, "登陆会话失效", Toast.LENGTH_LONG).show();
//      startActivity(new Intent((Context)this, AutoLoginActivity.class));
//      finish();
//      return;
//    }
//    NetRequestConstant netRequestConstant = new NetRequestConstant();
//    netRequestConstant.setType(HttpRequestType.GET);
//    netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/Orders";
//    netRequestConstant.context = (Context)this;
//    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
//    hashMap.put("dispatcherId", user.getUsername());
//    hashMap.put("orderBy", "id desc");
//    hashMap.put("pageNo", "1");
//    hashMap.put("pageSize", "10");
//    netRequestConstant.setParams(hashMap);
//    getServer(new Netcallback() {
//          public void preccess(Object param1Object, boolean param1Boolean) {
//            if (param1Boolean) {
//              HttpResponse httpResponse = (HttpResponse)param1Object;
//              if (httpResponse != null) {
//                if (httpResponse.getStatusLine().getStatusCode() == 200) {
//                  try {
//                    param1Object = new ArrayList();
//                    super();
//                    JSONObject jSONObject = new JSONObject();
//                    this(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
//                    JSONArray jSONArray = jSONObject.getJSONArray("items");
//                    HistoryOrdersActivity.m_taskOrderListJson = jSONArray;
//                    for (byte b = 0; b < jSONArray.length(); b++) {
//                      JSONObject jSONObject1 = jSONArray.getJSONObject(b);
//                      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
//                      this();
//                      StringBuilder stringBuilder = new StringBuilder();
//                      this();
//                      hashMap.put("orderSn", stringBuilder.append("订单编号：").append(jSONObject1.get("orderSn").toString()).toString());
//                      stringBuilder = new StringBuilder();
//                      this();
//                      hashMap.put("createTime", stringBuilder.append("下单时间：").append(jSONObject1.get("createTime").toString()).toString());
//                      JSONObject jSONObject2 = jSONObject1.getJSONObject("recvAddr");
//                      stringBuilder = new StringBuilder();
//                      this();
//                      hashMap.put("address", stringBuilder.append(jSONObject2.get("city").toString()).append(jSONObject2.get("county").toString()).append(jSONObject2.get("detail").toString()).toString());
//                      stringBuilder = new StringBuilder();
//                      this();
//                      hashMap.put("userId", stringBuilder.append("联系人：").append(jSONObject1.get("recvName")).toString());
//                      stringBuilder = new StringBuilder();
//                      this();
//                      hashMap.put("userPhone", stringBuilder.append("电话：").append(jSONObject1.get("recvPhone").toString()).toString());
//                      int i = Integer.parseInt(jSONObject1.get("orderStatus").toString());
//                      (new String[5])[0] = "待接单";
//                      (new String[5])[1] = "派送中";
//                      (new String[5])[2] = "待核单";
//                      (new String[5])[3] = "已结束";
//                      (new String[5])[4] = "已作废";
//                      hashMap.put("orderStatus", (new String[5])[i]);
//                      if (!jSONObject1.isNull("customer")) {
//                        JSONObject jSONObject3 = jSONObject1.getJSONObject("customer").getJSONObject("settlementType");
//                        if (jSONObject3.get("code").toString().equals("00003")) {
//                          hashMap.put("userIcon", Integer.valueOf(2131165421));
//                        } else if (jSONObject3.get("code").toString().equals("00002")) {
//                          hashMap.put("userIcon", Integer.valueOf(2131165408));
//                        } else {
//                          hashMap.put("userIcon", Integer.valueOf(2131165398));
//                        }
//                      }
//                      if (jSONObject1.getBoolean("urgent")) {
//                        hashMap.put("urgent", "加急");
//                      } else {
//                        hashMap.put("urgent", "");
//                      }
//                      param1Object.add(hashMap);
//                    }
//                  } catch (IOException iOException) {
//                    Toast.makeText((Context)HistoryOrdersActivity.this, "异常" + iOException.toString(), 1).show();
//                    return;
//                  } catch (JSONException jSONException) {
//                    Toast.makeText((Context)HistoryOrdersActivity.this, "异常" + jSONException.toString(), 1).show();
//                    return;
//                  }
//                } else {
//                  return;
//                }
//                SimpleAdapter simpleAdapter = new SimpleAdapter();
//                this((List)jSONException, 2131361883, new String[] { "orderSn", "createTime", "userId", "userPhone", "userIcon", "address", "orderStatus", "urgent" }, new int[] { 2131230871, 2131230858, 2131230889, 2131230890, 2131230863, 2131230852, 2131230872, 2131230887 });
//                ((ListView)HistoryOrdersActivity.this.findViewById(2131230927)).setAdapter((ListAdapter)simpleAdapter);
//                return;
//              }
//              Toast.makeText((Context)HistoryOrdersActivity.this, "请求超时，请检查网络", 1).show();
//              return;
//            }
//            Toast.makeText((Context)HistoryOrdersActivity.this, "网络未连接！", 1).show();
//          }
//        }netRequestConstant);
//    this.handler.sendEmptyMessage(257);
  }
}