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

public class HistoryCheckActivity extends BaseActivity implements View.OnClickListener, AbsListView.OnScrollListener {
  public static JSONArray m_checkOrderListJson;
  
  private Handler handler = new Handler() {
      public void handleMessage(Message param1Message) {
        super.handleMessage(param1Message);
        switch (param1Message.what) {
          default:
            return;
          case 257:
            break;
        } 
        if (HistoryCheckActivity.this.swipeRefreshLayout.isRefreshing())
          HistoryCheckActivity.this.swipeRefreshLayout.setRefreshing(false); 
      }
    };
  
  private SwipeRefreshLayout swipeRefreshLayout;
  
  private void switchActivity(int paramInt) {
    try {
//      Intent intent = new Intent();
//      Bundle bundle = new Bundle();
//      bundle.putString("check", m_checkOrderListJson.getJSONObject(paramInt).toString());
//      intent.setClass((Context)this, CheckDetailActivity.class);
//      intent.putExtras(bundle);
//      startActivity(intent);
    } catch (Exception jSONException) {
      Toast.makeText((Context)this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
    } 
  }
  
  void init() {
    requestWindowFeature(1);
    setContentView(R.layout.activity_my_checks);
    ((ListView)findViewById(R.id.listview)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
          public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
            HistoryCheckActivity.this.switchActivity(param1Int);
          }
        });
    this.swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.main_srl);
    this.swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorAccent, R.color.colorAccent, R.color.colorAccent});
    this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
          public void onRefresh() {
            HistoryCheckActivity.this.refleshVaildChecks();
          }
        });
    refleshVaildChecks();
  }
  
  public void onClick(View paramView) {}
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
  }
  
  protected void onRestart() {
    super.onRestart();
    refleshVaildChecks();
  }
  
  public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3) {}
  
  public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {}
  
  protected void onStart() {
    super.onStart();
  }
  
  public void refleshVaildChecks() {
//    User user = ((AppContext)getApplicationContext()).getUser();
//    if (user == null) {
//      Toast.makeText((Context)this, "登陆会话失效", 1).show();
//      startActivity(new Intent((Context)this, AutoLoginActivity.class));
//      finish();
//    }
//    NetRequestConstant netRequestConstant = new NetRequestConstant();
//    netRequestConstant.setType(HttpRequestType.GET);
//    netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/Repair";
//    netRequestConstant.context = (Context)this;
//    HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
//    hashMap.put("dealedUserId", user.getUsername());
//    hashMap.put("processStatus", "PTSolved");
//    hashMap.put("pageNo", "1");
//    hashMap.put("pageSize", "20");
//    hashMap.put("orderBy", "id desc");
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
//                    HistoryCheckActivity.m_checkOrderListJson = jSONArray;
//                    for (byte b = 0; b < jSONArray.length(); b++) {
//                      JSONObject jSONObject1 = jSONArray.getJSONObject(b);
//                      HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
//                      this();
//                      StringBuilder stringBuilder2 = new StringBuilder();
//                      this();
//                      hashMap.put("orderSn", stringBuilder2.append("巡检单号：").append(jSONObject1.get("repairSn").toString()).toString());
//                      stringBuilder2 = new StringBuilder();
//                      this();
//                      hashMap.put("createTime", stringBuilder2.append("下单时间：").append(jSONObject1.get("createTime").toString()).toString());
//                      JSONObject jSONObject2 = jSONObject1.getJSONObject("recvAddr");
//                      StringBuilder stringBuilder3 = new StringBuilder();
//                      this();
//                      hashMap.put("address", stringBuilder3.append(jSONObject2.get("city").toString()).append(jSONObject2.get("county").toString()).append(jSONObject2.get("detail").toString()).toString());
//                      StringBuilder stringBuilder1 = new StringBuilder();
//                      this();
//                      hashMap.put("userId", stringBuilder1.append("联系人：").append(jSONObject1.get("recvName")).toString());
//                      stringBuilder1 = new StringBuilder();
//                      this();
//                      hashMap.put("userPhone", stringBuilder1.append("电话：").append(jSONObject1.get("recvPhone").toString()).toString());
//                      hashMap.put("orderStatus", "已处理");
//                      jSONObject1 = jSONObject1.getJSONObject("repairType");
//                      if (jSONObject1.getInt("index") == 0) {
//                        hashMap.put("userIcon", Integer.valueOf(2131165490));
//                      } else if (jSONObject1.getInt("index") == 1) {
//                        hashMap.put("userIcon", Integer.valueOf(2131165281));
//                      } else {
//                        hashMap.put("userIcon", Integer.valueOf(2131165491));
//                      }
//                      param1Object.add(hashMap);
//                    }
//                  } catch (IOException iOException) {
//                    Toast.makeText((Context)HistoryCheckActivity.this, "异常" + iOException.toString(), 1).show();
//                    return;
//                  } catch (JSONException jSONException) {
//                    Toast.makeText((Context)HistoryCheckActivity.this, "异常" + jSONException.toString(), 1).show();
//                    return;
//                  }
//                } else {
//                  return;
//                }
//                SimpleAdapter simpleAdapter = new SimpleAdapter();
//                this((List)jSONException, 2131361883, new String[] { "orderSn", "createTime", "userId", "userPhone", "userIcon", "address", "orderStatus", "urgent" }, new int[] { 2131230871, 2131230858, 2131230889, 2131230890, 2131230863, 2131230852, 2131230872, 2131230887 });
//                ((ListView)HistoryCheckActivity.this.findViewById(2131230927)).setAdapter((ListAdapter)simpleAdapter);
//                return;
//              }
//              Toast.makeText((Context)HistoryCheckActivity.this, "请求超时，请检查网络", 1).show();
//              return;
//            }
//            Toast.makeText((Context)HistoryCheckActivity.this, "网络未连接！", 1).show();
//          }
//        }netRequestConstant);
//    this.handler.sendEmptyMessage(257);
  }
}