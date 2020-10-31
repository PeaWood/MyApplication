package com.gc.nfc.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.gc.nfc.R;
import com.gc.nfc.app.AppContext;
import com.gc.nfc.domain.Data_CustomerBottle;
import com.gc.nfc.domain.Data_CustomerCredit;
import com.gc.nfc.domain.Data_TaskOrders;
import com.gc.nfc.domain.User;
import com.gc.nfc.http.Logger;
import com.gc.nfc.http.OkHttpUtil;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import okhttp3.Request;
import okhttp3.Response;

public class OrderDetailActivity extends BaseActivity implements View.OnClickListener, INaviInfoCallback {
    private AppContext appContext;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message param1Message) {
            super.handleMessage(param1Message);
            calculatePassedTime();
            mHandler.sendEmptyMessageDelayed(1, 1000L);
        }
    };

    private JSONObject m_OrderJson;
    private String m_businessKey;
    private Button m_buttonNext;
    private String m_currentCustomerId;
    private String m_customerPhone;
    private ImageView m_imageViewCall;
    private ImageView m_imageViewNav;
    private ImageView m_imageViewUserIcon;
    private boolean m_isTicketUser;
    private ListView m_listView;
    private String m_orderCreateTime;
    private int m_orderStatus;
    private String m_recvAddr;
    private LatLng m_recvLocation;
    private String m_taskId;
    private TextView m_textViewAddress;
    private TextView m_textViewCallNumber;
    private TextView m_textViewCreateTime;
    private TextView m_textViewOrderSn;
    private TextView m_textViewOrderStatus;
    private TextView m_textViewPassedTime;
    private TextView m_textViewPayStatus;
    private TextView m_textViewPayTypeInfo;
    private TextView m_textViewPs;
    private TextView m_textViewReserveTime;
    private TextView m_textViewTotalMountDeal;
    private TextView m_textViewTotalMountOrignal;
    private TextView m_textViewTotalQuantity;
    private TextView m_textViewUserId;
    private TextView m_textViewUserPhone;
    private TextView m_textview_qp;
    private User m_user;
    private String strOrderType;
    private ImageView imageView;

    private void calculatePassedTime() {
        try {
            Date date1 = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
            Date date2 = simpleDateFormat.parse(m_orderCreateTime);
            TextView textView = m_textViewPassedTime;
            StringBuilder stringBuilder = new StringBuilder();
            textView.setText(stringBuilder.append("已过 ").append(getDatePoor(date1, date2)).toString());
        } catch (ParseException parseException) {
            Toast.makeText(this, "异常" + parseException.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void dealOrderOps() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("taskId", m_taskId);
        bundle.putString("order", m_OrderJson.toString());
        bundle.putString("businessKey", m_businessKey);
        intent.setClass(OrderDetailActivity.this, BottleExchangeActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    private boolean getCustomerBottle() {
        Map<String, String> map = new HashMap();
        map.put("userId", m_currentCustomerId);
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.GET(OkHttpUtil.URL + "/CustomerBottle", map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(OrderDetailActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() != 200) {
                    Toast.makeText(OrderDetailActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                    return;
                }
                String string = response.body().string();
                Logger.e("getCustomerBottle: " + string);
                Gson gson = new Gson();
                Data_CustomerBottle customerBottle = gson.fromJson(string, Data_CustomerBottle.class);
                if (customerBottle.getItems().size() == 1) {
                    Data_CustomerBottle.ItemsBean itemsBean = customerBottle.getItems().get(0);
                    StringBuilder str = new StringBuilder();
                    String s = str.append("5kg(个)：").append(itemsBean.getSAmount()).append("，15kg(个)：").append(itemsBean.getMAmount()).append("，50kg(个)：").append(itemsBean.getLAmount()).toString();
                    m_textview_qp.setText(s);
                    return;
                }
                m_textview_qp.setText("当前无欠瓶");
            }
        });
        return true;
    }

    private boolean getCustomerCredit() {
        Map<String, String> map = new HashMap();
        map.put("userId", m_currentCustomerId);
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.GET(OkHttpUtil.URL + "/CustomerCredit", map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(OrderDetailActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() != 200) {
                    Toast.makeText(OrderDetailActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                    return;
                }
                String string = response.body().string();
                Logger.e("getCustomerCredit: " + string);
                Gson gson = new Gson();
                Data_CustomerCredit customerCredit = gson.fromJson(string, Data_CustomerCredit.class);
                if (customerCredit.getItems().size() == 1) {
                    Data_CustomerCredit.ItemsBean itemsBean = customerCredit.getItems().get(0);
                    StringBuilder stringBuilder = new StringBuilder();
                    String str = stringBuilder.append("欠款：￥").append(itemsBean.getAmount()).toString();
                    m_textViewPayTypeInfo.setText(str);
                    return;
                }
                m_textViewPayTypeInfo.setText("欠款：￥0");
            }
        });
        return true;
    }

    private String getDatePoor(Date paramDate1, Date paramDate2) {
        long l1 = paramDate1.getTime() - paramDate2.getTime();
        long l2 = l1 / 86400000L;
        long l3 = l1 % 86400000L / 3600000L;
        long l4 = l1 % 86400000L % 3600000L / 60000L;
        l1 = l1 % 86400000L % 3600000L % 60000L / 1000L;
        return (l3 + 24L * l2) + ":" + l4 + ":" + l1;
    }

    private void getOrderOps() {
        User user = ((AppContext) getApplicationContext()).getUser();
        if (user == null) {
            Toast.makeText(this, "未登录！", Toast.LENGTH_LONG).show();
            return;
        }
        Map<String, String> map = new HashMap();
        map.put("businessKey", m_businessKey);
        map.put("candiUser", m_user.getUsername());
        map.put("orderStatus", String.valueOf(1));
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.GET(OkHttpUtil.URL + "/TaskOrders/Process/" + m_taskId, map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(OrderDetailActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() != 200) {
                    if (response.code() == 406) {
                        Toast.makeText(OrderDetailActivity.this, "权限不足，您不能派送该订单！", Toast.LENGTH_LONG).show();
                    } else if (response.code() == 403) {
                        Toast.makeText(OrderDetailActivity.this, "抢单失败，您已被系统禁止订单！", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(OrderDetailActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                    }
                    return;
                }
                Logger.e("getOrderOps-->" + response.body().string());
                Toast.makeText(OrderDetailActivity.this, "抢单成功！", Toast.LENGTH_LONG).show();
                MediaPlayer.create(OrderDetailActivity.this, R.raw.get_order).start();
                Intent intent = new Intent(getApplicationContext(), MainlyActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("switchTab", 1);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    private void getRecvLocation() {
        try {
            JSONObject jSONObject = this.m_OrderJson;
            LatLng latLng = new LatLng(jSONObject.getDouble("recvLatitude"), jSONObject.getDouble("recvLongitude"));
            this.m_recvLocation = latLng;
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void setListViewHeightBasedOnChildren(ListView paramListView) {
        if (paramListView != null) {
            ListAdapter listAdapter = paramListView.getAdapter();
            if (listAdapter != null) {
                int i = 0;
                for (byte b = 0; b < listAdapter.getCount(); b++) {
                    View view = listAdapter.getView(b, null, (ViewGroup) paramListView);
                    view.measure(0, 0);
                    i += view.getMeasuredHeight();
                }
                ViewGroup.LayoutParams layoutParams = paramListView.getLayoutParams();
                layoutParams.height = paramListView.getDividerHeight() * (listAdapter.getCount() - 1) + i;
                paramListView.setLayoutParams(layoutParams);
            }
        }
    }

    private void setOrderAppendInfo() {
        try {
            m_textViewOrderStatus = (TextView) findViewById(R.id.textview_orderStatus);//订单状态
            m_textViewReserveTime = (TextView) findViewById(R.id.textview_reserveTime);//预约时间
            m_textViewPs = (TextView) findViewById(R.id.textview_ps);//备注
            m_textViewCallNumber = (TextView) findViewById(R.id.textview_CallNumber);//呼入号码
            Gson gson = new Gson();
            Data_TaskOrders.ItemsBean.ObjectBean objectBean = gson.fromJson(m_OrderJson.toString(), Data_TaskOrders.ItemsBean.ObjectBean.class);
            String name = objectBean.getPayStatus().getName();
            m_textViewPayStatus.setText(name);
            int i = objectBean.getOrderStatus();
            String[] strs = new String[5];
            strs[0] = "待派送";
            strs[1] = "派送中";
            strs[2] = "已签收";
            strs[3] = "订单结束";
            strs[4] = "作废";
            String str2 = strs[i];
            m_textViewOrderStatus.setText(str2);
            str2 = objectBean.getReserveTime();
            if (TextUtils.isEmpty(str2)) {
                m_textViewReserveTime.setText("无");
            } else {
                m_textViewReserveTime.setText(str2);
            }
            str2 = objectBean.getComment();
            if (TextUtils.isEmpty(str2)) {
                m_textViewPs.setText("无");
            } else {
                m_textViewPs.setText(str2);
            }
            String str1 = objectBean.getCallInPhone();
            m_textViewCallNumber.setText(str1);
        } catch (Exception jSONException) {
            jSONException.printStackTrace();
            Toast.makeText(OrderDetailActivity.this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setOrderDetailsInfo() {
        int i = 0;
        try {
            StringBuilder stringBuilder3 = new StringBuilder();
            m_textViewTotalMountOrignal.setText(stringBuilder3.append("￥").append(m_OrderJson.getString("originalAmount")).toString());
            StringBuilder stringBuilder1 = new StringBuilder();
            m_textViewTotalMountDeal.setText(stringBuilder1.append("￥").append(m_OrderJson.getString("orderAmount")).toString());
            JSONArray jSONArray = m_OrderJson.getJSONArray("orderDetailList");
            ArrayList arrayList = new ArrayList();
            for (byte b = 0; b < jSONArray.length(); b++) {
                JSONObject jSONObject1 = jSONArray.getJSONObject(b);
                JSONObject jSONObject2 = jSONObject1.getJSONObject("goods");
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put("goodName", jSONObject2.get("name").toString());
                StringBuilder stringBuilder = new StringBuilder();
                hashMap.put("goodQuantity", stringBuilder.append("X").append(jSONObject1.get("quantity").toString()).toString());
                stringBuilder = new StringBuilder();
                hashMap.put("orignalPrice", stringBuilder.append("￥").append(jSONObject1.get("originalPrice").toString()).toString());
                stringBuilder = new StringBuilder();
                hashMap.put("dealPrice", stringBuilder.append("￥").append(jSONObject1.get("dealPrice").toString()).toString());
                i += Integer.parseInt(jSONObject1.get("quantity").toString());
                arrayList.add(hashMap);
            }
            StringBuilder stringBuilder2 = new StringBuilder();
            m_textViewTotalQuantity.setText(stringBuilder2.append("X").append(Integer.toString(i)).toString());
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.order_detail_items, new String[]{"goodName", "goodQuantity", "orignalPrice", "dealPrice"}, new int[]{R.id.items_goodName, R.id.items_goodQuantity, R.id.items_orignalPrice, R.id.items_dealPrice});
            m_listView.setAdapter(simpleAdapter);
            setListViewHeightBasedOnChildren(m_listView);
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setOrderHeadInfo() {
        try {
            JSONObject jSONObject1 = m_OrderJson;
            StringBuilder stringBuilder2 = new StringBuilder();
            String str3 = stringBuilder2.append("订单编号：").append(jSONObject1.get("orderSn").toString()).toString();
            m_textViewOrderSn.setText(str3);
            StringBuilder stringBuilder1 = new StringBuilder();
            String str2 = stringBuilder1.append("下单时间：").append(jSONObject1.get("createTime").toString()).toString();
            m_textViewCreateTime.setText(str2);
            m_orderCreateTime = jSONObject1.get("createTime").toString();
            JSONObject jSONObject2 = jSONObject1.getJSONObject("customer");
            String str4 = jSONObject1.get("recvName").toString();
            StringBuilder stringBuilder4 = new StringBuilder();
            String str5 = stringBuilder4.append("电话：").append(jSONObject1.get("recvPhone").toString()).toString();
            m_textViewUserId.setText(str4);
            m_textViewUserPhone.setText(str5);
            m_currentCustomerId = jSONObject2.get("userId").toString();
            m_customerPhone = str5;
            str4 = jSONObject2.getJSONObject("customerCompany").get("name").toString();
            jSONObject1 = jSONObject1.getJSONObject("recvAddr");
            StringBuilder stringBuilder3 = new StringBuilder();
            String str1 = stringBuilder3.append(jSONObject1.get("city").toString()).append(jSONObject1.get("county").toString()).append(jSONObject1.get("detail").toString()).toString();
            if (str4.equals("无")) {
                m_textViewAddress.setText(str1);
            } else {
                TextView textView = m_textViewAddress;
                StringBuilder stringBuilder = new StringBuilder();

                textView.setText(stringBuilder.append(str1).append(" (").append(str4).append(")").toString());
            }
            m_recvAddr = str1;
            jSONObject2 = jSONObject2.getJSONObject("settlementType");
            if (jSONObject2.get("code").toString().equals("00003")) {
                m_imageViewUserIcon.setImageResource(R.mipmap.icon_ticket_user_white);
                m_isTicketUser = true;
                return;
            }
            if (jSONObject2.get("code").toString().equals("00002")) {
                m_imageViewUserIcon.setImageResource(R.mipmap.icon_month_user_white);
                m_isTicketUser = false;
                return;
            }
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        m_imageViewUserIcon.setImageResource(R.mipmap.icon_common_user_white);
        m_isTicketUser = false;
    }

    private void switchNavBar() {
        Logger.e("switchNavBar");
        LatLng latLng1 = ((AppContext) getApplicationContext()).getLocation();
        LatLng latLng2 = this.m_recvLocation;
        AmapNaviPage.getInstance().showRouteActivity(getApplicationContext(), new AmapNaviParams(new Poi("当前位置", latLng1, ""), null, new Poi(this.m_recvAddr, latLng2, ""), AmapNaviType.DRIVER), this);
        AMapNavi.getInstance( this).setUseInnerVoice(true);
    }

    public void callPhone(String paramString) {
        Intent intent1;
        if (ActivityCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CALL_PHONE")) {
                Toast.makeText(this, "请授权！", Toast.LENGTH_SHORT).show();
                intent1 = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent1.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent1);
                return;
            }
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.CALL_PHONE"}, 1);
            return;
        }
        Intent intent2 = new Intent("android.intent.action.CALL");
        intent2.setData(Uri.parse("tel:" + paramString));
        startActivity(intent2);
    }

    void init() {
        try {
            setContentView(R.layout.activity_order_detail);
            appContext = (AppContext) getApplicationContext();
            m_user = appContext.getUser();
            if (m_user == null) {
                Toast.makeText(this, "登陆会话失效", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, AutoLoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
            Bundle bundle = getIntent().getExtras();
            String str = bundle.getString("order");
            JSONObject jSONObject = new JSONObject(str);
            m_OrderJson = jSONObject;
            m_taskId = bundle.getString("taskId");
            m_businessKey = bundle.getString("businessKey");
            m_orderStatus = bundle.getInt("orderStatus");
            strOrderType = m_OrderJson.get("orderTriggerType").toString();
            m_buttonNext = (Button) findViewById(R.id.button_next);
            m_textViewOrderSn = (TextView) findViewById(R.id.items_orderSn);
            m_textViewUserId = (TextView) findViewById(R.id.items_userId);
            m_textViewUserPhone = (TextView) findViewById(R.id.items_userPhone);
            m_textViewCreateTime = (TextView) findViewById(R.id.items_creatTime);
            m_textViewAddress = (TextView) findViewById(R.id.items_address);
            m_imageViewUserIcon = (ImageView) findViewById(R.id.items_imageUserIcon);
            m_textViewPayTypeInfo = (TextView) findViewById(R.id.items_userCredit);
            m_textview_qp = (TextView) findViewById(R.id.textview_qp);
            m_listView = (ListView) findViewById(R.id.listview);
            m_imageViewNav = (ImageView) findViewById(R.id.imageView_nav);
            m_imageViewCall = (ImageView) findViewById(R.id.imageView_call);
            m_textViewPayStatus = (TextView) findViewById(R.id.textview_payStatus);
            m_textViewOrderStatus = (TextView) findViewById(R.id.textview_orderStatus);
            m_textViewReserveTime = (TextView) findViewById(R.id.textview_reserveTime);
            m_textViewPs = (TextView) findViewById(R.id.textview_ps);
            m_textViewCallNumber = (TextView) findViewById(R.id.textview_CallNumber);
            m_textViewTotalQuantity = (TextView) findViewById(R.id.items_totalQuantity);
            m_textViewTotalMountOrignal = (TextView) findViewById(R.id.items_totalMountOrignal);
            m_textViewTotalMountDeal = (TextView) findViewById(R.id.items_totalMountDeal);
            m_textViewPassedTime = (TextView) findViewById(R.id.items_passedTime);
            imageView = (ImageView) findViewById(R.id.img_back);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            m_buttonNext.setOnClickListener(this);
            m_imageViewNav.setOnClickListener(this);
            m_imageViewCall.setOnClickListener(this);
            setOrderHeadInfo();
            setOrderDetailsInfo();
            setOrderAppendInfo();
            getRecvLocation();
            getCustomerCredit();
            getCustomerBottle();
            switch (m_orderStatus) {
                default:
                    m_buttonNext.setVisibility(View.GONE);
                    return;
                case 0:
                    m_buttonNext.setText("立即抢单");
                    return;
                case 1:
                    m_buttonNext.setText("下一步");
                    return;
                case -1:
                    break;
            }
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        m_buttonNext.setVisibility(View.GONE);
    }

    public void onArriveDestination(boolean paramBoolean) {
    }

    public void onCalculateRouteFailure(int paramInt) {
    }

    public void onCalculateRouteSuccess(int[] paramArrayOfint) {
    }

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            case R.id.button_next:
                if (m_orderStatus == 0)
                    //抢单
                    getOrderOps();
                if (m_orderStatus == 1)
                    //下一步
                    dealOrderOps();
                break;
            case R.id.imageView_nav:
                switchNavBar();
                break;
            case R.id.imageView_call:
                callPhone(m_customerPhone);
                break;
        }
    }

    public void onExitPage(int paramInt) {
    }

    @Override
    public void onStrategyChanged(int i) {

    }

    @Override
    public View getCustomNaviBottomView() {
        return null;
    }

    @Override
    public View getCustomNaviView() {
        return null;
    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onMapTypeChanged(int i) {

    }

    @Override
    public View getCustomMiddleView() {
        return null;
    }

    @Override
    public void onNaviDirectionChanged(int i) {

    }

    @Override
    public void onDayAndNightModeChanged(int i) {

    }

    @Override
    public void onBroadcastModeChanged(int i) {

    }

    @Override
    public void onScaleAutoChanged(boolean b) {

    }

    public void onGetNavigationText(String paramString) {
    }

    public void onInitNaviFailure() {
    }

    public void onLocationChange(AMapNaviLocation paramAMapNaviLocation) {
    }

    public void onReCalculateRoute(int paramInt) {
    }

    public void onStartNavi(int paramInt) {
    }

    public void onStopSpeaking() {
    }
}
