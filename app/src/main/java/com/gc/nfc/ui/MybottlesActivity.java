package com.gc.nfc.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.nfc.R;
import com.gc.nfc.app.AppContext;
import com.gc.nfc.common.NetRequestConstant;
import com.gc.nfc.common.NetUrlConstant;
import com.gc.nfc.domain.Data_UserBottles;
import com.gc.nfc.domain.User;
import com.gc.nfc.http.Logger;
import com.gc.nfc.interfaces.Netcallback;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MybottlesActivity extends BaseActivity implements View.OnClickListener {
    private TextView m_totalCountTextView;
    private String m_userId;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ImageView imageView;

    void init() {
        requestWindowFeature(1);
        setContentView(R.layout.activity_my_bottles);
        listView = findViewById(R.id.listview);
        imageView = (ImageView) findViewById(R.id.img_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        m_totalCountTextView = (TextView) findViewById(R.id.textview_totalCount);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_srl);
        swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.colorAccent, R.color.colorAccent, R.color.colorAccent, R.color.colorAccent});
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                refleshVaildBottles(true);
            }
        });
        this.m_userId = getIntent().getExtras().getString("userId");
        refleshVaildBottles(false);
    }

    public void onClick(View paramView) {
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
    }

    public void refleshVaildBottles(boolean isrefresh) {
        User user = ((AppContext)getApplicationContext()).getUser();
        if (user == null) {
            Toast.makeText(this, "登陆会话失效", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, AutoLoginActivity.class));
            finish();
        }
        NetRequestConstant nrc = new NetRequestConstant();
        nrc.setType(HttpRequestType.GET);
        nrc.requestUrl = NetUrlConstant.BASEURL+"/api" + "/GasCylinder/";
        nrc.context = this;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("liableUserId", this.m_userId);
        map.put("pageSize", String.valueOf(500));
        map.put("pageNo", String.valueOf(1));
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
                            Logger.e("refleshVaildBottles: " + string);
                            Gson gson = new Gson();
                            setData(gson.fromJson(string, Data_UserBottles.class));
                        }else{
                            Toast.makeText(MybottlesActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                        }
                    }else {
//                        Toast.makeText(MybottlesActivity.this, "未知错误，异常！",
//                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MybottlesActivity.this, "网络未连接！",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, nrc);
    }

    private void setData(Data_UserBottles data_userBottles) {
        ArrayList list = new ArrayList();
        int total= 0;
        int size = data_userBottles.getItems().size();
        for (byte b = 0; b < size; b++) {
            Data_UserBottles.ItemsBean bean = data_userBottles.getItems().get(b);
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            StringBuilder stringBuilder2 = new StringBuilder();
            hashMap.put("number", stringBuilder2.append("钢瓶编号：").append(bean.getNumber()).toString());
            StringBuilder stringBuilder3 = new StringBuilder();
            hashMap.put("spec", stringBuilder3.append("钢瓶规格：").append(bean.getSpec().getName()).toString());
            StringBuilder stringBuilder1 = new StringBuilder();
            hashMap.put("serviceStatus", stringBuilder1.append("钢瓶状态：").append(bean.getServiceStatus().getName()).toString());
            list.add(hashMap);
            total = total+1;
        }
        m_totalCountTextView.setText(total+"");
        SimpleAdapter simpleAdapter = new SimpleAdapter(MybottlesActivity.this, list, R.layout.bottle_list_items, new String[]{"number", "spec", "serviceStatus"}, new int[]{R.id.items_number, R.id.items_spec, R.id.items_serviceStatus});
        listView.setAdapter(simpleAdapter);
    }
}
