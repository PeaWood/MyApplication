package com.gc.nfc.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.amap.api.maps.model.LatLng;
import com.gc.nfc.R;
import com.gc.nfc.app.AppContext;
import com.gc.nfc.common.NetRequestConstant;
import com.gc.nfc.domain.User;
import com.gc.nfc.http.Logger;
import com.gc.nfc.interfaces.Netcallback;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDealActivity extends BaseActivity implements View.OnClickListener, AbsListView.OnScrollListener {
    public static String m_orderPayStatus;

    final int REQUEST_CODE = 1;

    final int RESULT_CODE = 101;

    String SpecMap;

    private AppContext appContext;

    private JSONArray detail;

    private SimpleDateFormat formatter;

    private Handler handler = new Handler() {
        public void handleMessage(Message param1Message) {
            super.handleMessage(param1Message);
            switch (param1Message.what) {
                default:
                    return;
                case 257:
                    break;
            }
            if (swipeRefreshLayout.isRefreshing())
                swipeRefreshLayout.setRefreshing(false);
        }
    };

    private Handler handlerDelayCommit = new Handler() {
        public void handleMessage(Message param1Message) {
            if (m_depLeader == null) {
                Toast.makeText(OrderDealActivity.this, "所属店长查询失败！", Toast.LENGTH_SHORT).show();
                return;
            }
            if (OrderDealActivity.m_orderPayStatus.equals("已支付")) {
                deliverOver();
            } else if (m_isTicketUser) {
                if (isTicketsOrCouponsSelectedOK()) {
                    ticketUserPay();
                } else {
                    Toast.makeText(OrderDealActivity.this, "优惠券、气票选择与实际商品数量不匹配或已过期！", Toast.LENGTH_SHORT).show();
                }
            } else {
                UserPay();
            }
            m_buttonNext.setText("下一步");
            m_buttonNext.setBackgroundColor(getResources().getColor(R.color.textgray));
            m_buttonNext.setEnabled(true);
            super.handleMessage(param1Message);
        }
    };

    private List<Map<String, String>> list_map = new ArrayList<Map<String, String>>();

    private Map<String, String> m_BottlesMapKPL;

    private Map<String, String> m_BottlesMapZPL;

    private List<JSONObject> m_CouponList;

    private JSONObject m_OrderJson;

    private List<JSONObject> m_TicketList;

    private JSONArray m_ValidCouponJsonArray;

    private JSONArray m_ValidTicketJsonArray;

    private String m_Yjinfo;

    AlertDialog m_alertDialogCouponSelect;

    AlertDialog m_alertDialogTicketSelect;

    private String m_businessKey;

    private Button m_buttonNext;

    private String m_curUserId;

    private JSONObject m_curUserSettlementType;

    private User m_deliveryUser;

    private String m_depLeader;

    private ImageView m_imageSignpic;

    private ImageView m_imageViewCouponSelect;

    private ImageView m_imageViewPic;

    private ImageView m_imageViewTicketSelect;

    private boolean m_isCommonUser;

    private boolean m_isMonthDealUser;

    private boolean m_isTicketUser;

    private ListView m_listView_coupon;

    private ListView m_listView_ticket;

    private String m_orderId;

    private int m_orderStatus;

    private String m_recvAddr;

    private LatLng m_recvLocation;

    private Spinner m_spinnerPaytype;

    private String m_taskId;

    private TextView m_textViewPayStatus;

    private TextView m_textViewPaytype;

    private TextView m_textViewTotalFee;

    private String m_totalFee;

    private String m_yjss;

    private String m_yjys;

    private boolean picResult;

    private SwipeRefreshLayout swipeRefreshLayout;

    private void GetDepLeader() {
        NetRequestConstant netRequestConstant = new NetRequestConstant();
        netRequestConstant.setType(HttpRequestType.GET);
        netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/sysusers/GetDepLeader";
        netRequestConstant.context = this;
        HashMap hashMap = new HashMap<Object, Object>();
        hashMap.put("userId", m_deliveryUser.getUsername());
        hashMap.put("groupCode", "00005");
        netRequestConstant.setParams(hashMap);
        getServer(new Netcallback() {
            public void preccess(Object param1Object, boolean param1Boolean) {
                if (param1Boolean) {
                    HttpResponse response = (HttpResponse) param1Object;
                    if (response != null) {
                        if (response.getStatusLine().getStatusCode() == 200) {
                            try {
                                JSONObject jSONObject = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                                JSONArray jsonArray = jSONObject.getJSONArray("items");
                                for (byte b = 0; b < jsonArray.length(); b++) {
                                    if (b == 0) {
                                        m_depLeader = jsonArray.getJSONObject(b).getString("userId");
                                    } else {
                                        StringBuilder stringBuilder = new StringBuilder();
                                        m_depLeader = stringBuilder.append(m_depLeader).append(",").append(jsonArray.getJSONObject(b).getString("userId")).toString();
                                    }
                                }
                            } catch (JSONException jSONException) {
                                Toast.makeText(OrderDealActivity.this, "异常" + jSONException.toString() + jSONException.getMessage(), Toast.LENGTH_LONG).show();
                            } catch (IOException iOException) {
                                Toast.makeText(OrderDealActivity.this, "异常" + iOException.toString() + iOException.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            return;
                        }
                        Toast.makeText(OrderDealActivity.this, "订单配送失败", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(OrderDealActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(OrderDealActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
            }
        }, netRequestConstant);
    }

    private void PayOnScan() {
        Logger.e("PayOnScan");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.pay_on_scan, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.items_imageViewScanCode);
        int i = (int) (Double.parseDouble(m_totalFee) * 100.0D);
        String str = "http://www.gasmart.com.cn/api/pay/scan?totalFee=" + i + "&orderIndex=" + m_businessKey + "&userId=" + m_deliveryUser.getUsername();
        try {
            URL uRL = new URL(str);
            imageView.setImageBitmap(BitmapFactory.decodeStream(uRL.openStream()));
            builder.setIcon(R.drawable.icon_logo);
            builder.setTitle("支付码");
            AlertDialog alertDialog = builder.create();
            alertDialog.setView(view);
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                    param1DialogInterface.dismiss();
                }
            };
            alertDialog.setButton(-1, "确定", onClickListener);
            alertDialog.show();
            Window window = alertDialog.getWindow();
            window.setGravity(17);
            window.setLayout(-1, -2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean UserPay() {
        boolean bool = false;
        try {
            NetRequestConstant netRequestConstant = new NetRequestConstant();
            netRequestConstant.setType(HttpRequestType.PUT);
            StringBuilder stringBuilder = new StringBuilder();
            netRequestConstant.requestUrl = stringBuilder.append("http://www.gasmart.com.cn/api/Orders/").append(m_orderId).toString();
            netRequestConstant.context = this;
            HashMap hashMap = new HashMap<Object, Object>();
            if (m_isCommonUser) {
                if (m_curUserSettlementType.get("code").toString().equals("00001")) {
                    int i = m_spinnerPaytype.getSelectedItemPosition();
                    (new String[3])[0] = "PTCash";
                    (new String[3])[1] = "PTDebtCredit";
                    (new String[3])[2] = "PTWxOffLine";
                    hashMap.put("payType", (new String[3])[i]);
                }
                if (m_orderPayStatus.equals("已支付") && m_spinnerPaytype.getSelectedItemPosition() != 0) {
                    Toast.makeText(this, "订单已经支付，确认支付类型为扫码？", Toast.LENGTH_SHORT).show();
                    return bool;
                }
            } else if (m_isTicketUser) {
                hashMap.put("payType", "PTTicket");
            } else if (m_isMonthDealUser) {
                hashMap.put("payType", "PTMonthlyCredit");
            }
            hashMap.put("payStatus", "PSPaied");
            netRequestConstant.setBody(hashMap);
            Netcallback netcallback = new Netcallback() {
                public void preccess(Object param1Object, boolean param1Boolean) {
                    if (param1Boolean) {
                        HttpResponse response = (HttpResponse) param1Object;
                        if (response != null) {
                            if (response.getStatusLine().getStatusCode() == 200) {
                                deliverOver();
                                return;
                            }
                            if (response.getStatusLine().getStatusCode() == 404) {
                                Toast.makeText(OrderDealActivity.this, "订单不存在", Toast.LENGTH_LONG).show();
                                return;
                            }
                            if (response.getStatusLine().getStatusCode() == 401) {
                                Toast.makeText(OrderDealActivity.this, "鉴权失败，请重新登录" + response.getStatusLine().getStatusCode(), Toast.LENGTH_LONG).show();
                                return;
                            }
                            Toast.makeText(OrderDealActivity.this, "支付失败" + response.getStatusLine().getStatusCode(), Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(OrderDealActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(OrderDealActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
                }
            };
            getServer(netcallback, netRequestConstant);
            bool = true;
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
        }
        return bool;
    }

    private void createElectDep() {
        NetRequestConstant netRequestConstant = new NetRequestConstant();
        netRequestConstant.setType(HttpRequestType.POST);
        netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/ElectDeposit";
        netRequestConstant.context = this;
        HashMap hashMap = new HashMap<Object, Object>();
        hashMap.put("customerId", m_curUserId);
        hashMap.put("operId", m_deliveryUser.getUsername());
        hashMap.put("actualAmount", m_yjss);
        hashMap.put("electDepositDetails", detail);
        netRequestConstant.setBody(hashMap);
        getServer(new Netcallback() {
            public void preccess(Object param1Object, boolean param1Boolean) {
                if (param1Boolean) {
                    HttpResponse response = (HttpResponse) param1Object;
                    if (response != null) {
                        if (response.getStatusLine().getStatusCode() == 201) {
                            handlerDelayCommit.sendEmptyMessageDelayed(0, 1000L);
                            return;
                        }
                        Toast.makeText(OrderDealActivity.this, "电子押金单上传失败，" + getResponseMessage((HttpResponse) param1Object) + response.getStatusLine().getStatusCode(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(OrderDealActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(OrderDealActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
            }
        }, netRequestConstant);
    }

    private void deleteCoupon(int paramInt) {
        try {
            JSONObject jSONObject = m_CouponList.get(paramInt);
            StringBuilder stringBuilder2 = new StringBuilder();
            String str = stringBuilder2.append("编号:").append(jSONObject.getString("id")).append("  规格:").append(jSONObject.getString("specName")).toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            StringBuilder stringBuilder1 = new StringBuilder();
            builder.setMessage(stringBuilder1.append("取消使用优惠券:  ").append(str).append("   ?").toString());
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                    m_CouponList.remove(param1Int);
                    refleshCouponList();
                }
            };
            builder.setPositiveButton("确定", onClickListener);
            onClickListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                }
            };
            builder.setNegativeButton("取消", onClickListener);
            builder.show();
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteTicket(int paramInt) {
        try {
            JSONObject jSONObject = m_TicketList.get(paramInt);
            StringBuilder stringBuilder = new StringBuilder();
            String str = stringBuilder.append("编号:").append(jSONObject.getString("ticketSn")).append("  规格:").append(jSONObject.getString("specName")).toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            stringBuilder = new StringBuilder();
            builder.setMessage(stringBuilder.append("取消使用气票:  ").append(str).append("   ?").toString());
            DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                    m_TicketList.remove(param1Int);
                    refleshTicketList();
                }
            };
            builder.setPositiveButton("确定", onClickListener);
            onClickListener = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                }
            };
            builder.setNegativeButton("取消", onClickListener);
            builder.show();
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean deliverOver() {
        NetRequestConstant netRequestConstant = new NetRequestConstant();
        netRequestConstant.setType(HttpRequestType.PUT);
        netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/TaskOrders/PsComplete/" + m_taskId;
        netRequestConstant.context = this;
        HashMap hashMap1 = new HashMap<Object, Object>();
        HashMap hashMap2 = new HashMap<Object, Object>();
        hashMap1.put("businessKey", m_orderId);
        if (m_depLeader == null) {
            Toast.makeText(this, "所属店长查询失败！", Toast.LENGTH_SHORT).show();
            return false;
        }
        hashMap1.put("candiUser", m_depLeader);
        hashMap1.put("orderStatus", Integer.valueOf(2));
        if (m_BottlesMapZPL.size() != 0)
            hashMap2.put("fullGasNumber", m_BottlesMapZPL.toString());
        if (m_BottlesMapKPL.size() != 0)
            hashMap2.put("emptyGasNumber", m_BottlesMapKPL.toString());
        hashMap1.put("psUserId", m_deliveryUser.getUsername());
        hashMap1.put("customerUserId", m_curUserId);
        netRequestConstant.setParams(hashMap1);
        netRequestConstant.setBody(hashMap2);
        netRequestConstant.isBodyJsonArray = false;
        getServer(new Netcallback() {
            public void preccess(Object param1Object, boolean param1Boolean) {
                if (param1Boolean) {
                    HttpResponse response = (HttpResponse) param1Object;
                    if (response != null) {
                        if (response.getStatusLine().getStatusCode() == 200) {
                            Toast.makeText(OrderDealActivity.this, "订单配送成功", Toast.LENGTH_LONG).show();
                            MediaPlayer.create(OrderDealActivity.this, R.raw.finish_order).start();
                            Intent intent = new Intent(getApplicationContext(), MainlyActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("switchTab", 1);
                            intent.putExtras(bundle);
                            startActivity((Intent) param1Object);
                            finish();
                            return;
                        }
                        Toast.makeText(OrderDealActivity.this, "订单配送失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Toast.makeText(OrderDealActivity.this, "请求超时，请检查网络", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(OrderDealActivity.this, "网络未连接！", Toast.LENGTH_SHORT).show();
            }
        }, netRequestConstant);
        return true;
    }

    private void getRecvLocation() {
        try {
            JSONObject jSONObject = m_OrderJson;
            LatLng latLng = new LatLng(jSONObject.getDouble("recvLatitude"), jSONObject.getDouble("recvLongitude"));
            m_recvLocation = latLng;
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private String getResponseMessage(HttpResponse paramHttpResponse) {
        return null;
    }

    private boolean isTicketsOrCouponsSelectedOK() {
        return false;
    }

    private void refleshCouponList() {
        try {
            ArrayList arrayList = new ArrayList();
            for (byte b = 0; b < m_CouponList.size(); b++) {
                JSONObject jSONObject = m_CouponList.get(b);
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                StringBuilder stringBuilder = new StringBuilder();
                hashMap.put("baseInfo", stringBuilder.append("编码:").append(jSONObject.getString("id")).append("规格:").append(jSONObject.getString("specName")).toString());
                stringBuilder = new StringBuilder();
                hashMap.put("validTime", stringBuilder.append("失效时间:").append(jSONObject.getString("endDate")).toString());
                arrayList.add(hashMap);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.coupon_list_items, new String[]{"baseInfo", "validTime"}, new int[]{R.id.items_baseInfo, R.id.items_validTime});
            m_listView_coupon.setAdapter((ListAdapter) simpleAdapter);
            setListViewHeightBasedOnChildren(m_listView_coupon);
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void refleshTicketList() {
        try {
            ArrayList arrayList = new ArrayList();
            for (byte b = 0; b < m_TicketList.size(); b++) {
                JSONObject jSONObject = m_TicketList.get(b);
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                StringBuilder stringBuilder = new StringBuilder();
                hashMap.put("baseInfo", stringBuilder.append("编码:").append(jSONObject.getString("ticketSn")).append("规格:").append(jSONObject.getString("specName")).toString());
                stringBuilder = new StringBuilder();
                hashMap.put("validTime", stringBuilder.append("失效时间:").append(jSONObject.getString("endDate")).toString());
                arrayList.add(hashMap);
            }
            SimpleAdapter simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.coupon_list_items, new String[]{"baseInfo", "validTime"}, new int[]{R.id.items_baseInfo, R.id.items_validTime});
            m_listView_ticket.setAdapter((ListAdapter) simpleAdapter);
            setListViewHeightBasedOnChildren(m_listView_ticket);
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
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
            String str = m_OrderJson.getJSONObject("payStatus").get("name").toString();
            m_textViewPayStatus.setText(str);
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setOrderDetailsInfo() {
        try {
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
                hashMap.put("dealPrice", stringBuilder.append("￥").append(jSONObject1.get("dealPrice").toString()).toString());
                arrayList.add(hashMap);
            }
            new SimpleAdapter(this, arrayList, R.layout.bottle_detail_items, new String[]{"goodName", "goodQuantity", "dealPrice"}, new int[]{R.id.items_goodName, R.id.items_goodQuantity, R.id.items_dealPrice});
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setOrderHeadInfo() {
        try {
            JSONObject jSONObject1 = m_OrderJson;
            m_orderId = jSONObject1.get("orderSn").toString();
            JSONObject jSONObject2 = jSONObject1.getJSONObject("recvAddr");
            StringBuilder stringBuilder = new StringBuilder();
            m_recvAddr = stringBuilder.append("地址：").append(jSONObject2.get("city").toString()).append(jSONObject2.get("county").toString()).append(jSONObject2.get("detail").toString()).toString();
            jSONObject2 = jSONObject1.getJSONObject("customer");
            m_curUserId = jSONObject2.get("userId").toString();
            m_curUserSettlementType = jSONObject2.getJSONObject("settlementType");
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean ticketUserPay() {
        boolean bool = true;
        try {
            NetRequestConstant netRequestConstant = new NetRequestConstant();
            netRequestConstant.setType(HttpRequestType.PUT);
            StringBuilder stringBuilder = new StringBuilder();
            netRequestConstant.requestUrl = stringBuilder.append("http://www.gasmart.com.cn/api/TicketOrders/").append(m_orderId).toString();
            netRequestConstant.context = this;
            HashMap hashMap1 = new HashMap<Object, Object>();
            HashMap hashMap2 = new HashMap<Object, Object>();
            String str2 = "";
            byte b = 0;
            while (b < m_TicketList.size()) {
                String str4 = ((JSONObject) m_TicketList.get(b)).get("ticketSn").toString();
                stringBuilder = new StringBuilder();
                str2 = stringBuilder.append(str2).append(str4).toString();
                String str3 = str2;
                if (b != m_TicketList.size() - 1) {
                    StringBuilder stringBuilder1 = new StringBuilder();
                    str3 = stringBuilder1.append(str2).append(",").toString();
                }
                b++;
                str2 = str3;
            }
            String str1 = "";
            for (b = 0; b < m_CouponList.size(); b++) {
                String str4 = ((JSONObject) m_CouponList.get(b)).get("id").toString();
                StringBuilder stringBuilder1 = new StringBuilder();
                String str3 = stringBuilder1.append(str1).append(str4).toString();
                str1 = str3;
                if (b != m_CouponList.size() - 1) {
                    StringBuilder stringBuilder2 = new StringBuilder();
                    str1 = stringBuilder2.append(str3).append(",").toString();
                }
            }
            hashMap1.put("coupons", str1);
            hashMap1.put("tickets", str2);
            netRequestConstant.setParams(hashMap1);
            netRequestConstant.setBody(hashMap2);
            Netcallback netcallback = new Netcallback() {
                public void preccess(Object param1Object, boolean param1Boolean) {
                    if (param1Boolean) {
                        try {
                            HttpResponse httpResponse = (HttpResponse) param1Object;
                            if (httpResponse != null) {

                                InputStreamReader inputStreamReader = new InputStreamReader(httpResponse.getEntity().getContent());
                                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                                StringBuffer stringBuffer = new StringBuffer();
                                String str = System.getProperty("line.separator");
                                while (true) {
                                    String str3 = bufferedReader.readLine();
                                    if (str3 != null) {
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuffer.append(stringBuilder.append(str3).append(str).toString());
                                        continue;
                                    }
                                    bufferedReader.close();
                                    String str2 = stringBuffer.toString();
                                    param1Object = str2;
                                    if (str2.equals(""))
                                        param1Object = "{\"message\":\"no value\"}";
                                    JSONObject jSONObject = new JSONObject((String) param1Object);
                                    if (httpResponse.getStatusLine().getStatusCode() == 200) {
                                        deliverOver();
                                        return;
                                    }
                                    if (httpResponse.getStatusLine().getStatusCode() == 404) {
                                        String message = jSONObject.get("message").toString();
                                        StringBuilder stringBuilder = new StringBuilder();
                                        Toast.makeText(OrderDealActivity.this, stringBuilder.append("订单不存在").append(message).toString(), Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    String message = jSONObject.get("message").toString();
                                    OrderDealActivity orderDealActivity = OrderDealActivity.this;
                                    StringBuilder builder = new StringBuilder();
                                    Toast.makeText(orderDealActivity, builder.append("支付失败").append(message).toString(), Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        } catch (IOException iOException) {
                            Toast.makeText(OrderDealActivity.this, "异常" + iOException.toString(), Toast.LENGTH_LONG).show();
                            return;
                        } catch (JSONException jSONException) {
                            Toast.makeText(OrderDealActivity.this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
                            return;
                        }
                        Toast.makeText(OrderDealActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Toast.makeText(OrderDealActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
                }
            };
            getServer(netcallback, netRequestConstant);
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
            bool = false;
        }
        return bool;
    }

    public void getCustomerCoupons() {
        NetRequestConstant netRequestConstant = new NetRequestConstant();
        netRequestConstant.setType(HttpRequestType.GET);
        netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/Coupon";
        netRequestConstant.context = this;
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        hashMap.put("customerUserId", m_curUserId);
        hashMap.put("useStatus", Integer.valueOf(0));
        netRequestConstant.setParams(hashMap);
        getServer(new Netcallback() {
            public void preccess(Object param1Object, boolean param1Boolean) {
                if (param1Boolean) {
                    HttpResponse httpResponse = (HttpResponse) param1Object;
                    if (httpResponse != null) {
                        if (httpResponse.getStatusLine().getStatusCode() == 200)
                            try {
                                JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
                                m_ValidCouponJsonArray = jsonObject.getJSONArray("items");
                            } catch (IOException iOException) {
                                Toast.makeText(OrderDealActivity.this, "异常" + iOException.toString(), Toast.LENGTH_LONG).show();
                            } catch (JSONException jSONException) {
                                Toast.makeText(OrderDealActivity.this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
                            }
                        return;
                    }
                    Toast.makeText(OrderDealActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(OrderDealActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
            }
        }, netRequestConstant);
    }

    public void getCustomerTickets() {
        NetRequestConstant netRequestConstant = new NetRequestConstant();
        netRequestConstant.setType(HttpRequestType.GET);
        netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/Ticket";
        netRequestConstant.context = this;
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        hashMap.put("customerUserId", m_curUserId);
        hashMap.put("useStatus", Integer.valueOf(0));
        netRequestConstant.setParams(hashMap);
        getServer(new Netcallback() {
            public void preccess(Object param1Object, boolean param1Boolean) {
                if (param1Boolean) {
                    HttpResponse response = (HttpResponse) param1Object;
                    if (response != null) {
                        if (response.getStatusLine().getStatusCode() == 200)
                            try {
                                JSONObject jSONObject = new JSONObject(EntityUtils.toString(response.getEntity(), "UTF-8"));
                                m_ValidTicketJsonArray = jSONObject.getJSONArray("items");
                            } catch (IOException iOException) {
                                Toast.makeText(OrderDealActivity.this, "异常" + iOException.toString(), Toast.LENGTH_LONG).show();
                            } catch (JSONException jSONException) {
                                Toast.makeText(OrderDealActivity.this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
                            }
                        return;
                    }
                    Toast.makeText(OrderDealActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(OrderDealActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
            }
        }, netRequestConstant);
    }

    void init() {
        Logger.e("普通订单");
        try {
            setContentView(R.layout.activity_order_deal);
            Bundle bundle = getIntent().getExtras();
            String str1 = bundle.getString("order");
            JSONObject jSONObject = new JSONObject(str1);
            m_OrderJson = jSONObject;
            m_taskId = bundle.getString("taskId");
            m_businessKey = bundle.getString("businessKey");
            str1 = bundle.getString("KPCode");
            String str2 = bundle.getString("ZPCode");
            SpecMap = bundle.getString("SpecMap");
            list_map = (List<Map<String, String>>) bundle.getSerializable("MapList");
            String str3 = bundle.getString("ElectDepDetails");
            if (str3 != null && str3.length() != 0) {
                JSONArray jSONArray = new JSONArray();
                detail = jSONArray;
            }
            TypeReference<HashMap<String, String>> typeReference2 = new TypeReference<HashMap<String, String>>() {

            };
            m_BottlesMapKPL = (Map<String, String>) JSON.parseObject(str1, typeReference2, new com.alibaba.fastjson.parser.Feature[0]);
            TypeReference<HashMap<String, String>> typeReference1 = new TypeReference<HashMap<String, String>>() {

            };
            m_BottlesMapZPL = (Map<String, String>) JSON.parseObject(str2, typeReference1, new com.alibaba.fastjson.parser.Feature[0]);
            m_yjys = bundle.getString("YJD_YS");
            m_yjss = bundle.getString("YJD_SS");
            TextView textView3 = (TextView) findViewById(R.id.textview_SSFee);//押金实收（不太确定是不是这个id）
            StringBuilder stringBuilder2 = new StringBuilder();
            textView3.setText(stringBuilder2.append(m_yjss).append("元 (现金或门店二维码收取)").toString());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formatter = simpleDateFormat;
            m_buttonNext = (Button) findViewById(R.id.button_next);
            m_textViewPayStatus = (TextView) findViewById(R.id.textview_payStatus);//支付状态
            m_listView_ticket = (ListView) findViewById(R.id.listview_ticket);//气票列表 item
            m_listView_coupon = (ListView) findViewById(R.id.listview_coupon);//优惠券列表 item
            m_spinnerPaytype = (Spinner) findViewById(R.id.spinner_payType);//支付方式选择
            m_textViewPaytype = (TextView) findViewById(R.id.textview_payType);//支付方式选择后显示的值
            m_textViewTotalFee = (TextView) findViewById(R.id.textview_totalFee);//商品总价
            m_imageViewTicketSelect = (ImageView) findViewById(R.id.imageView_ticketSelect);//气票选择图标
            m_imageViewCouponSelect = (ImageView) findViewById(R.id.imageView_couponSelect);//优惠券选择图标
            m_imageViewTicketSelect.setOnClickListener(this);
            m_imageViewCouponSelect.setOnClickListener(this);
            m_imageViewPic = (ImageView) findViewById(R.id.imageView_pic);//拍照的选择按钮
            m_imageViewPic.setOnClickListener(this);
            m_imageSignpic = (ImageView) findViewById(R.id.imageSign_pic);//电子签名
            m_imageSignpic.setOnClickListener(this);
            m_orderPayStatus = m_OrderJson.getJSONObject("payStatus").get("name").toString();
            m_textViewPayStatus.setText(m_orderPayStatus);
            m_orderId = m_businessKey;
            m_totalFee = m_OrderJson.get("orderAmount").toString();
            TextView textView1 = m_textViewTotalFee;
            StringBuilder stringBuilder3 = new StringBuilder();
            textView1.setText(stringBuilder3.append("￥").append(m_totalFee).toString());
            int i = Integer.parseInt(m_totalFee);
            int j = Integer.parseInt(m_yjss);
            TextView textView2 = (TextView) findViewById(R.id.textview_YJSSFee);//钢瓶其他费用（不太确定是不是这个id）
            StringBuilder stringBuilder1 = new StringBuilder();
            textView2.setText(stringBuilder1.append(i + j).append("元").toString());
            swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_srl);
            swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.blue, R.color.blue, R.color.blue, R.color.blue});
            SwipeRefreshLayout swipeRefreshLayout = this.swipeRefreshLayout;
            SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
                public void onRefresh() {
                    refleshPayStatus();
                }
            };
            swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
            m_buttonNext.setOnClickListener(this);
            m_textViewPaytype.setVisibility(View.INVISIBLE);
            m_spinnerPaytype.setSelection(2, true);
            Spinner spinner = m_spinnerPaytype;
            AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                    switch (param1Int) {
                        case 2:
                            PayOnScan();
                            break;
                    }

                }

                public void onNothingSelected(AdapterView<?> param1AdapterView) {
                }
            };
            spinner.setOnItemSelectedListener(onItemSelectedListener);
            appContext = (AppContext) getApplicationContext();
            m_deliveryUser = appContext.getUser();
            ListView listView1 = m_listView_ticket;
            AdapterView.OnItemLongClickListener onItemLongClickListener2 = new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                    deleteTicket(param1Int);
                    return true;
                }
            };
            listView1.setOnItemLongClickListener(onItemLongClickListener2);
            ListView listView2 = m_listView_coupon;
            AdapterView.OnItemLongClickListener onItemLongClickListener1 = new AdapterView.OnItemLongClickListener() {
                public boolean onItemLongClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                    deleteCoupon(param1Int);
                    return true;
                }
            };
            listView2.setOnItemLongClickListener(onItemLongClickListener1);
            ArrayList<JSONObject> arrayList = new ArrayList();
            m_TicketList = arrayList;
            arrayList = new ArrayList<JSONObject>();
            m_CouponList = arrayList;
            setOrderHeadInfo();
            setOrderDetailsInfo();
            setOrderAppendInfo();
            getRecvLocation();
            GetDepLeader();
            if (!m_curUserSettlementType.get("code").toString().equals("00002")) {
                m_isMonthDealUser = false;
            } else {
                m_isMonthDealUser = true;
            }
            if (!m_curUserSettlementType.get("code").toString().equals("00001")) {
                m_spinnerPaytype.setVisibility(View.INVISIBLE);
                m_textViewPaytype.setVisibility(View.VISIBLE);
                m_textViewPaytype.setText(m_curUserSettlementType.get("name").toString());
                m_isCommonUser = false;
            } else {
                m_isCommonUser = true;
            }
            if (!m_curUserSettlementType.get("code").toString().equals("00003")) {
                (findViewById(R.id.LinearLayout_ticketSelect)).setVisibility(View.INVISIBLE);
                (findViewById(R.id.LinearLayout_couponSelect)).setVisibility(View.INVISIBLE);
                m_isTicketUser = false;
            } else {
                m_isTicketUser = true;
            }
            getCustomerTickets();
            getCustomerCoupons();
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        if (paramInt1 == 1 && paramInt2 == 101)
            picResult = paramIntent.getBooleanExtra("backup", false);
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
    }

    public void onClick(View paramView) {
        Intent intent1;
        Bundle bundle1;
        Bundle bundle2;
        Intent intent2;
        switch (paramView.getId()) {
            case R.id.imageView_pic:
                intent1 = new Intent();
                bundle2 = new Bundle();
                bundle2.putString("fileFolder", "order");
                bundle2.putString("fileNameHeader", m_orderId);
                bundle2.putInt("MaxPicCount", 4);
                intent1.setClass(this, PicSelActivity.class);
                intent1.putExtras(bundle2);
                startActivity(intent1);
                break;
            case R.id.imageSign_pic:
                intent2 = new Intent();
                bundle1 = new Bundle();
                bundle1.putString("order", m_OrderJson.toString());
                bundle1.putString("fileNameHeader", m_orderId);
                bundle1.putString("fileFolder", "signature");
                bundle1.putString("m_orderPayStatus", m_orderPayStatus);
                bundle1.putSerializable("MapList", (Serializable) list_map);
                bundle1.putString("KPCode", JSON.toJSONString(m_BottlesMapKPL));
                bundle1.putString("ZPCode", JSON.toJSONString(m_BottlesMapZPL));
                bundle1.putString("SpecMap", SpecMap);
                intent2.setClass(this, PicSignActivity.class);
                intent2.putExtras(bundle1);
                startActivityForResult(intent2, 1);
                break;
            case R.id.button_next:
                if (!picResult)
                    Toast.makeText(this, "客户未签名！", Toast.LENGTH_LONG).show();
                if (m_depLeader == null) {
                    Toast.makeText(this, "所属店长查询失败！请再次提交！", Toast.LENGTH_LONG).show();
                    GetDepLeader();
                }
                Toast.makeText(this, "正在提交，请稍等。。。", Toast.LENGTH_LONG).show();
                m_buttonNext.setText("正在提交...");
                m_buttonNext.setBackgroundColor(getResources().getColor(R.color.textgray));
                m_buttonNext.setEnabled(false);
                if (detail != null)
                    createElectDep();
                handlerDelayCommit.sendEmptyMessageDelayed(0, 1000L);
                break;
            case R.id.imageView_ticketSelect:
                showTicketSelectAlertDialog(paramView);
                break;
            case R.id.imageView_couponSelect:
                showCouponSelectAlertDialog(paramView);
                break;
        }

    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
    }

    public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3) {
    }

    public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
    }

    public void refleshPayStatus() {
        NetRequestConstant netRequestConstant = new NetRequestConstant();
        netRequestConstant.setType(HttpRequestType.GET);
        netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/Orders";
        netRequestConstant.context = this;
        HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
        hashMap.put("orderSn", m_orderId);
        netRequestConstant.setParams(hashMap);
        getServer(new Netcallback() {
            public void preccess(Object param1Object, boolean param1Boolean) {
                if (param1Boolean) {
                    HttpResponse httpResponse = (HttpResponse) param1Object;
                    if (httpResponse != null) {
                        if (httpResponse.getStatusLine().getStatusCode() == 200)
                            try {
                                JSONObject jsonObject = new JSONObject(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
                                JSONArray jsonArray = jsonObject.getJSONArray("items");
                                if (jsonArray.length() == 1) {
                                    OrderDealActivity.m_orderPayStatus = jsonArray.getJSONObject(0).getJSONObject("payStatus").getString("name");
                                    m_textViewPayStatus.setText(OrderDealActivity.m_orderPayStatus);
                                    Logger.e("OrderDealActivity.m_orderPayStatus = "+OrderDealActivity.m_orderPayStatus);
                                }
                            } catch (IOException iOException) {
                                Toast.makeText(OrderDealActivity.this, "异常" + iOException.toString(), Toast.LENGTH_LONG).show();
                            } catch (JSONException jSONException) {
                                Toast.makeText(OrderDealActivity.this, "异常" + jSONException.toString(), Toast.LENGTH_LONG).show();
                            }
                        return;
                    }
                    Toast.makeText(OrderDealActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(OrderDealActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
            }
        }, netRequestConstant);
        handler.sendEmptyMessage(257);
    }

    public void showCouponSelectAlertDialog(View paramView) {
        try {
            if (m_ValidCouponJsonArray != null) {
                int i = m_ValidCouponJsonArray.length();
                String[] arrayOfString = new String[i];
                boolean[] arrayOfBoolean = new boolean[i];
                byte b;
                for (b = 0; b < i; b++)
                    arrayOfBoolean[b] = false;
                for (b = 0; b < i; b++) {
                    JSONObject jSONObject = m_ValidCouponJsonArray.getJSONObject(b);
                    StringBuilder stringBuilder = new StringBuilder();
                    arrayOfString[b] = stringBuilder.append("序号:").append(b).append("  规格:").append(jSONObject.getString("specName")).toString();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("优惠券选择窗口");
                DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener = new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface param1DialogInterface, int param1Int, boolean param1Boolean) {
                        //              checkedArray[param1Int] = param1Boolean;
                    }
                };
                builder.setMultiChoiceItems((CharSequence[]) arrayOfString, null, onMultiChoiceClickListener);
                DialogInterface.OnClickListener onClickListener2 = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                        try {
                            m_CouponList.clear();
                            for (param1Int = 0; param1Int < m_ValidCouponJsonArray.length(); param1Int++) {
                                //                  if (checkedArray[param1Int])  checkedArray应该是传过来的图片
                                m_CouponList.add(m_ValidCouponJsonArray.getJSONObject(param1Int));
                                refleshCouponList();
                            }
                            m_alertDialogCouponSelect.dismiss();
                        } catch (JSONException jSONException) {
                            Toast.makeText(OrderDealActivity.this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                builder.setPositiveButton("确定", onClickListener2);
                DialogInterface.OnClickListener onClickListener1 = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                        m_alertDialogCouponSelect.dismiss();
                    }
                };
                builder.setNegativeButton("取消", onClickListener1);
                m_alertDialogCouponSelect = builder.create();
                m_alertDialogCouponSelect.show();
            }
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public void showTicketSelectAlertDialog(View paramView) {
        try {
            if (m_ValidCouponJsonArray != null) {
                int i = m_ValidTicketJsonArray.length();
                String[] arrayOfString = new String[i];
                boolean[] arrayOfBoolean = new boolean[i];
                byte b;
                for (b = 0; b < i; b++)
                    arrayOfBoolean[b] = false;
                for (b = 0; b < i; b++) {
                    JSONObject jSONObject = m_ValidTicketJsonArray.getJSONObject(b);
                    StringBuilder stringBuilder = new StringBuilder();
                    arrayOfString[b] = stringBuilder.append("序号:").append(b).append("  规格:").append(jSONObject.getString("specName")).toString();
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("气票选择窗口");
                DialogInterface.OnMultiChoiceClickListener onMultiChoiceClickListener = new DialogInterface.OnMultiChoiceClickListener() {
                    public void onClick(DialogInterface param1DialogInterface, int param1Int, boolean param1Boolean) {
                        arrayOfBoolean[param1Int] = param1Boolean;
                    }
                };
                builder.setMultiChoiceItems((CharSequence[]) arrayOfString, null, onMultiChoiceClickListener);
                DialogInterface.OnClickListener onClickListener2 = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                        try {
                            m_TicketList.clear();
                            for (param1Int = 0; param1Int < m_ValidTicketJsonArray.length(); param1Int++) {
                                if (arrayOfBoolean[param1Int]) {
                                    m_TicketList.add(m_ValidTicketJsonArray.getJSONObject(param1Int));
                                    refleshTicketList();
                                }
                            }
                            m_alertDialogTicketSelect.dismiss();
                        } catch (JSONException jSONException) {
                            Toast.makeText(OrderDealActivity.this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                builder.setPositiveButton("确定", onClickListener2);
                DialogInterface.OnClickListener onClickListener1 = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                        m_alertDialogTicketSelect.dismiss();
                    }
                };
                builder.setNegativeButton("取消", onClickListener1);
                m_alertDialogTicketSelect = builder.create();
                m_alertDialogTicketSelect.show();
            }
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
