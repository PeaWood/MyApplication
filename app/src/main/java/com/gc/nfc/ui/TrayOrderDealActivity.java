package com.gc.nfc.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.gc.nfc.R;
import com.gc.nfc.app.AppContext;
import com.gc.nfc.common.NetRequestConstant;
import com.gc.nfc.domain.Data_Depleader;
import com.gc.nfc.domain.Data_Order;
import com.gc.nfc.domain.RefoundDetail;
import com.gc.nfc.domain.User;
import com.gc.nfc.http.Logger;
import com.gc.nfc.http.OkHttpUtil;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

public class TrayOrderDealActivity extends BaseActivity implements View.OnClickListener, AbsListView.OnScrollListener {
    public static String m_orderPayStatus;

    final int REQUEST_CODE = 1;

    final int RESULT_CODE = 101;

    String SpecMap;

    private AppContext appContext;

    private String chongzhuang;

    @SuppressLint("HandlerLeak")
    private Handler handlerDelayCommit = new Handler() {
        public void handleMessage(Message param1Message) {
            if (m_depLeader == null) {
                Toast.makeText(TrayOrderDealActivity.this, "所属店长查询失败！", Toast.LENGTH_SHORT).show();
                m_buttonNext.setText("下一步");
                m_buttonNext.setBackgroundColor(getResources().getColor(R.color.textgray));
                m_buttonNext.setEnabled(true);
                return;
            }
            if (chongzhuang == null || chongzhuang.length() == 0) {
                Toast.makeText(TrayOrderDealActivity.this, "钢瓶未填写重量！无法支付", Toast.LENGTH_SHORT).show();
                m_buttonNext.setText("下一步");
                m_buttonNext.setBackgroundColor(getResources().getColor(R.color.textgray));//d到时在改
                m_buttonNext.setEnabled(true);
                return;
            }
            if (!picResult) {
                Toast.makeText(TrayOrderDealActivity.this, "客户未签名！", Toast.LENGTH_SHORT).show();
                m_buttonNext.setText("下一步");
                m_buttonNext.setBackgroundColor(getResources().getColor(R.color.textgray));
                m_buttonNext.setEnabled(true);
                return;
            }
            if (!isPayStatus()) {
                Toast.makeText(TrayOrderDealActivity.this, "残气计算还未完成！无法支付", Toast.LENGTH_SHORT).show();
                m_buttonNext.setText("下一步");
                m_buttonNext.setBackgroundColor(getResources().getColor(R.color.textgray));
                m_buttonNext.setEnabled(true);
                return;
            }
            if (TrayOrderDealActivity.m_orderPayStatus.equals("已支付")) {
                deliverOver();
            } else {
                commonUserPay();
            }
            m_buttonNext.setText("下一步");
            m_buttonNext.setBackgroundColor(getResources().getColor(R.color.textgray));
            m_buttonNext.setEnabled(true);
            super.handleMessage(param1Message);
        }
    };

    private List<Map<String, String>> list_map = new ArrayList<Map<String, String>>();

    private Map<String, RefoundDetail> m_BottlesMapKP;

    private Map<String, String> m_BottlesMapKPL;

    private Map<String, String> m_BottlesMapZPL;

    private JSONObject m_OrderJson;

    private Button m_buttonNext;

    private String m_curUserId;

    private JSONObject m_curUserSettlementType;

    private User m_deliveryUser;

    private String m_depLeader;

    private ImageView m_imageViewPic;

    private ImageView m_imageViewSign;

    private boolean m_isMonthDealUser;

    private ListView m_listView_kp;

    private String m_orderId;

    private Spinner m_spinnerPaytype;

    private String m_taskId;

    private TextView m_textViewCouponAmount;

    private TextView m_textViewOrginalFee;

    private TextView m_textViewPayStatus;

    private TextView m_textViewPaytype;

    private TextView m_textViewResidualGasFee;

    private TextView m_textViewTotalFee;

    private String m_totalFee;

    private String m_yjss;

    private String m_yjys;

    private boolean picResult;

    private SwipeRefreshLayout swipeRefreshLayout;

    private String DoubleCast(Double paramDouble) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setRoundingMode(RoundingMode.HALF_UP);
        return numberFormat.format(paramDouble);
    }

    private void GetDepLeader() {
        Map map = new HashMap();
        map.put("userId", m_deliveryUser.getUsername());
        map.put("groupCode", "00005");
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.GET(OkHttpUtil.URL + "/sysusers/GetDepLeader/", map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(TrayOrderDealActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() != 200) {
                    Toast.makeText(TrayOrderDealActivity.this, "网络未连接！", Toast.LENGTH_LONG).show();
                }
                String string = response.body().string();
                Logger.e("GetDepLeader : " + string);
                Gson gson = new Gson();
                Data_Depleader depleader = gson.fromJson(string, Data_Depleader.class);
                try {
                    for (byte b = 0; b < depleader.getItems().size(); b++) {
                        if (b == 0) {
                            m_depLeader = depleader.getItems().get(b).getUserId();
                        } else {
                            StringBuilder stringBuilder = new StringBuilder();
                            m_depLeader = stringBuilder.append(m_depLeader).append(",").append(depleader.getItems().get(b).getUserId()).toString();
                        }
                    }
                } catch (Exception jSONException) {
                    Toast.makeText(TrayOrderDealActivity.this, "异常" + jSONException.toString() + jSONException.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void OrderCalculate() {
        Map map = new HashMap();
        map.put("customerId", m_curUserId);
        JSONArray jSONArray = new JSONArray();
        try {
            Iterator iterator = m_BottlesMapKP.entrySet().iterator();
            while (iterator.hasNext()) {
                RefoundDetail refoundDetail = (RefoundDetail) ((Map.Entry) iterator.next()).getValue();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("gasCynNumber", refoundDetail.code);
                jSONObject.put("refoundWeight", refoundDetail.kp_weight);
                if (refoundDetail.isFirst) {
                    jSONObject.put("forceCaculate", false);
                    refoundDetail.isFirst = false;
                } else if (refoundDetail.isOK) {
                    if (refoundDetail.forceCaculate) {
                        jSONObject.put("forceCaculate", true);
                        jSONObject.put("standWeight", refoundDetail.chongzhuang_weight);
                        jSONObject.put("dealPrice", refoundDetail.original_price);
                    } else {
                        jSONObject.put("forceCaculate", false);
                    }
                } else {
                    jSONObject.put("forceCaculate", true);
                    jSONObject.put("standWeight", refoundDetail.chongzhuang_weight);
                    jSONObject.put("dealPrice", refoundDetail.original_price);
                }
                jSONArray.put(jSONObject);
            }
        } catch (JSONException jSONException) {
            Toast.makeText(this, "订单残气计算请求构建失败！" + jSONException.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        map.put("jsonArray", jSONArray.toString());
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.PUT(OkHttpUtil.URL + "/Orders/Caculate/" + m_orderId, map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(TrayOrderDealActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Logger.e("OrderCalculate: " + response.code());
                Logger.e("OrderCalculate: " + response.message());
                Logger.e("OrderCalculate: " + response.body().string());
                if (response.code() != 200) {
                    //                    refleshBottlesMapKP();
                    //                    refleshPayStatus();
                    Toast.makeText(TrayOrderDealActivity.this, response.message(), Toast.LENGTH_LONG).show();
                    //                    return;
                }
                //                Logger.e("OrderCalculate: "+response.body().string());
                //                Gson gson = new Gson();
                //                Data_Caculate data_caculate = gson.fromJson(response.body().string(), Data_Caculate.class);
                //                for (byte b = 0; b < data_caculate.getItems().size(); b++) {
                //                    Data_Caculate.ItemsBean itemsBean = data_caculate.getItems().get(b);
                //                    param1Boolean = jSONObject1.getBoolean("success");
                //                    String str = jSONObject1.getString("gasCynNumber");
                //                    param1Object = m_BottlesMapKP.get(str);
                //                    if (((RefoundDetail)param1Object).note == null) {
                //                      String str1 = new String();
                //                      this();
                //                      ((RefoundDetail)param1Object).note = str1;
                //                    }
                //                    ((RefoundDetail)param1Object).note = jSONObject1.getString("note");
                //                    if (param1Boolean) {
                //                      JSONObject jSONObject2 = jSONObject1.getJSONObject("gasRefoundDetail");
                //                      jSONObject2.getString("orderSn");
                //                      DoubleCast(Double.valueOf(jSONObject2.getDouble("dealPrice")));
                //                      jSONObject2.getString("prevOrder");
                //                      jSONObject2.getString("prevGoodsCode");
                //                      ((RefoundDetail)param1Object).isOK = true;
                //                      ((RefoundDetail)param1Object).kp_weight = DoubleCast(Double.valueOf(jSONObject2.getDouble("refoundWeight")));
                //                      ((RefoundDetail)param1Object).tare_weight = DoubleCast(Double.valueOf(jSONObject2.getDouble("tareWeight")));
                //                      ((RefoundDetail)param1Object).canqi_weight = DoubleCast(Double.valueOf(jSONObject2.getDouble("remainGas")));
                //                      ((RefoundDetail)param1Object).chongzhuang_weight = DoubleCast(Double.valueOf(jSONObject2.getDouble("standWeight")));
                //                      ((RefoundDetail)param1Object).original_price = DoubleCast(Double.valueOf(jSONObject2.getDouble("dealPrice")));
                //                      ((RefoundDetail)param1Object).gas_price = DoubleCast(Double.valueOf(jSONObject2.getDouble("unitPrice")));
                //                      ((RefoundDetail)param1Object).canqi_price = DoubleCast(Double.valueOf(jSONObject2.getDouble("refoundSum")));
                //                      ((RefoundDetail)param1Object).forceCaculate = jSONObject2.getBoolean("forceCaculate");
                //                    } else {
                //                      ((RefoundDetail)param1Object).isOK = false;
                //                    }
                //                    m_BottlesMapKP.put(str, param1Object);
                //                }
                //try {
                //  JSONObject jSONObject = new JSONObject();
                //  this(EntityUtils.toString(param1Object.getEntity(), "UTF-8"));
                //  JSONArray jSONArray = jSONObject.getJSONArray("items");
                //  for (byte b = 0; b < jSONArray.length(); b++) {
                //    JSONObject jSONObject1 = jSONArray.getJSONObject(b);

                //  }
                //  refleshBottlesMapKP();
                //  refleshPayStatus();
                //} catch (JSONException jSONException) {
                //  Toast.makeText(TrayOrderDealActivity.this, "异常" + jSONException.toString() + jSONException.getMessage(), 1).show();
                //} catch (IOException iOException) {
                //  Toast.makeText(TrayOrderDealActivity.this, "异常" + iOException.toString() + iOException.getMessage(), 1).show();
                //}
            }
        });
    }

    private void PayOnScan() {
        if (!isPayStatus()) {
            Toast.makeText(this, "残气计算还未完成！无法扫码支付", Toast.LENGTH_SHORT).show();
            return;
        }
        if (m_totalFee.length() == 0) {
            Toast.makeText(this, "计价失败！无法扫码支付", Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = View.inflate(this, R.layout.pay_on_scan, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.items_imageViewScanCode);
        int i = (int) (Double.parseDouble(m_totalFee) * 100.0D);
        String str = "http://www.gasmart.com.cn/api/pay/scan?totalFee=" + i + "&orderIndex=" + m_orderId + "&userId=" + m_deliveryUser.getUsername();
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
        } catch (Exception iOException) {
        }
    }

    private boolean commonUserPay() {
        NetRequestConstant netRequestConstant = new NetRequestConstant();
        netRequestConstant.setType(HttpRequestType.PUT);
        netRequestConstant.requestUrl = "http://www.gasmart.com.cn/api/Orders/" + m_orderId;
        netRequestConstant.context = this;
        HashMap hashMap = new HashMap<Object, Object>();
        if (!m_isMonthDealUser) {
            int i = m_spinnerPaytype.getSelectedItemPosition();
            String[] strings = new String[3];
            strings[0] = "PTCash";
            strings[1] = "PTDebtCredit";
            strings[2] = "PTWxOffLine";
            hashMap.put("payType", strings[i]);
        } else {
            hashMap.put("payType", "PTMonthlyCredit");
        }
        if (m_orderPayStatus.equals("已支付") && m_spinnerPaytype.getSelectedItemPosition() != 0) {
            Toast.makeText(this, "订单已经支付，确认支付类型为扫码？", Toast.LENGTH_SHORT).show();
            return false;
        }
        hashMap.put("payStatus", "PSPaied");
        netRequestConstant.setBody(hashMap);
        //    getServer(new Netcallback() {
        //          public void preccess(Object param1Object, boolean param1Boolean) {
        //            if (param1Boolean) {
        //              param1Object = param1Object;
        //              if (param1Object != null) {
        //                if (param1Object.getStatusLine().getStatusCode() == 200) {
        //                  deliverOver();
        //                  return;
        //                }
        //                if (param1Object.getStatusLine().getStatusCode() == 404) {
        //                  Toast.makeText(TrayOrderDealActivity.this, "订单不存在", 1).show();
        //                  return;
        //                }
        //                if (param1Object.getStatusLine().getStatusCode() == 401) {
        //                  Toast.makeText(TrayOrderDealActivity.this, "鉴权失败，请重新登录" + param1Object.getStatusLine().getStatusCode(), 1).show();
        //                  return;
        //                }
        //                Toast.makeText(TrayOrderDealActivity.this, "支付失败" + param1Object.getStatusLine().getStatusCode(), 1).show();
        //                return;
        //              }
        //              Toast.makeText(TrayOrderDealActivity.this, "请求超时，请检查网络", 1).show();
        //              return;
        //            }
        //            Toast.makeText(TrayOrderDealActivity.this, "网络未连接！", 1).show();
        //          }
        //        }netRequestConstant);
        return true;
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
        //    getServer(new Netcallback() {
        //          public void preccess(Object param1Object, boolean param1Boolean) {
        //            if (param1Boolean) {
        //              param1Object = param1Object;
        //              if (param1Object != null) {
        //                if (param1Object.getStatusLine().getStatusCode() == 200) {
        //                  Toast.makeText(TrayOrderDealActivity.this, "订单配送成功", 1).show();
        //                  MediaPlayer.create(TrayOrderDealActivity.this, 2131558402).start();
        //                  param1Object = new Intent(getApplicationContext(), MainlyActivity.class);
        //                  Bundle bundle = new Bundle();
        //                  bundle.putInt("switchTab", 1);
        //                  param1Object.putExtras(bundle);
        //                  startActivity((Intent)param1Object);
        //                  finish();
        //                  return;
        //                }
        //                Toast.makeText(TrayOrderDealActivity.this, "订单配送失败", 1).show();
        //                return;
        //              }
        //              Toast.makeText(TrayOrderDealActivity.this, "请求超时，请检查网络", 1).show();
        //              return;
        //            }
        //            Toast.makeText(TrayOrderDealActivity.this, "网络未连接！", 1).show();
        //          }
        //        }netRequestConstant);
        return true;
    }

    private void getRefoundNeededParams(final String bottleCodeTemp) {
        //    final View layout = getLayoutInflater().inflate(2131361918, null);
        //    AlertDialog.Builder builder = (new AlertDialog.Builder(this)).setTitle("请输入").setIcon(2131165392).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
        //          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
        //            EditText editText1 = (EditText)layout.findViewById(2131230847);
        //            EditText editText2 = (EditText)layout.findViewById(2131230837);
        //            String str1 = editText1.getText().toString();
        //            String str2 = editText2.getText().toString();
        //            TrayOrderDealActivity.access$902(TrayOrderDealActivity.this, str2);
        //            if (str1.length() == 0 || str2.length() == 0) {
        //              Toast.makeText(TrayOrderDealActivity.this, "输入有误，请重新输入！", 1).show();
        //              return;
        //            }
        //            RefoundDetail refoundDetail = (RefoundDetail)m_BottlesMapKP.get(bottleCodeTemp);
        //            refoundDetail.original_price = str1;
        //            refoundDetail.chongzhuang_weight = str2;
        //            m_BottlesMapKP.put(bottleCodeTemp, refoundDetail);
        //            refleshBottlesMapKP();
        //          }
        //        });
        //    builder.setCancelable(false);
        //    builder.show();
    }

    private String getResponseMessage(HttpResponse paramHttpResponse) {
        return null;
    }

    private boolean isPayStatus() {
        boolean bool2;
        boolean bool1 = true;
        Iterator iterator = m_BottlesMapKP.entrySet().iterator();
        while (true) {
            bool2 = bool1;
            if (iterator.hasNext()) {
                if (!((RefoundDetail) ((Map.Entry) iterator.next()).getValue()).isOK) {
                    bool2 = false;
                    break;
                }
                continue;
            }
            break;
        }
        return bool2;
    }

    private void refleshBottlesMapKP() {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<String, RefoundDetail> entry : m_BottlesMapKP.entrySet()) {
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            RefoundDetail refoundDetail = (RefoundDetail) entry.getValue();
            if (refoundDetail.forceCaculate) {
                hashMap.put("note", "（干预瓶）:" + refoundDetail.note);
            } else {
                hashMap.put("note", "(正常瓶）:" + refoundDetail.note);
            }
            hashMap.put("code", refoundDetail.code);
            hashMap.put("kp_weight", "空瓶重:" + refoundDetail.kp_weight);
            hashMap.put("tare_weight", "瓶皮重:" + refoundDetail.tare_weight);
            hashMap.put("canqi_weight", "残气量:" + refoundDetail.canqi_weight);
            hashMap.put("chongzhuang_weight", "充装量:" + refoundDetail.chongzhuang_weight);
            hashMap.put("original_price", "购买价:" + refoundDetail.original_price);
            hashMap.put("gas_price", "均气价:" + refoundDetail.gas_price);
            hashMap.put("canqi_price", "退残额:" + refoundDetail.canqi_price);
            if (refoundDetail.isOK) {
                hashMap.put("icon", Integer.valueOf(R.drawable.warning));//到时替换图片
            } else {
                hashMap.put("icon", Integer.valueOf(R.drawable.warning));
            }
            arrayList.add(hashMap);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, arrayList, R.layout.bottle_list_tray_pay_items, new String[]{"note", "icon", "code", "kp_weight", "tare_weight", "canqi_weight", "chongzhuang_weight", "original_price", "gas_price", "canqi_price"}, new int[]{R.id.items_note, R.id.items_imageViewIsOK, R.id.items_number, R.id.items_kpweight, R.id.items_tareweight, R.id.items_canqiweight, R.id.items_chongweight, R.id.items_originalPrice, R.id.items_gasPrice, R.id.items_canqiPrice});
        m_listView_kp.setAdapter(simpleAdapter);
        setListViewHeightBasedOnChildren(m_listView_kp);
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

    private void setOrderAppendInfo(JSONObject paramJSONObject) {
        try {
            String str = paramJSONObject.getJSONObject("payStatus").get("name").toString();
            m_textViewPayStatus.setText(str);
            Double double_2 = Double.valueOf(paramJSONObject.getDouble("refoundSum"));
            Double double_3 = Double.valueOf(paramJSONObject.getDouble("orderAmount"));
            Double double_1 = Double.valueOf(paramJSONObject.getDouble("originalAmount"));
            double d1 = double_1.doubleValue();
            double d2 = double_3.doubleValue();
            double d3 = double_2.doubleValue();
            TextView textView2 = m_textViewCouponAmount;
            StringBuilder stringBuilder2 = new StringBuilder();
            textView2.setText(stringBuilder2.append("￥").append(String.format("%.2f", new Object[]{Double.valueOf(d1 - d2 - d3)})).toString());
            TextView textView3 = m_textViewTotalFee;
            StringBuilder stringBuilder1 = new StringBuilder();
            textView3.setText(stringBuilder1.append("￥").append(String.format("%.2f", new Object[]{double_3})).toString());
            textView3 = m_textViewOrginalFee;
            stringBuilder1 = new StringBuilder();
            textView3.setText(stringBuilder1.append("￥").append(String.format("%.2f", new Object[]{double_1})).toString());
            TextView textView1 = m_textViewResidualGasFee;
            stringBuilder1 = new StringBuilder();
            textView1.setText(stringBuilder1.append("￥").append(String.format("%.2f", new Object[] { double_2 })).toString());
            m_totalFee = String.format("%.2f", new Object[]{double_3});
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    void init() {
        try {
            setContentView(R.layout.activity_tray_order_deal);
            HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
            this.m_BottlesMapKP = (Map) hashMap;
            Bundle bundle = getIntent().getExtras();
            String str1 = bundle.getString("order");
            JSONObject jSONObject2 = new JSONObject(str1);
            this.m_OrderJson = jSONObject2;
            this.m_taskId = bundle.getString("taskId");
            this.m_orderId = bundle.getString("businessKey");
            String str2 = bundle.getString("KPCode");
            str1 = bundle.getString("ZPCode");
            this.SpecMap = bundle.getString("SpecMap");
            this.list_map = (List<Map<String, String>>) bundle.getSerializable("MapList");
            this.m_yjys = bundle.getString("YJD_YS");
            this.m_yjss = bundle.getString("YJD_SS");
            TextView textView = (TextView) findViewById(R.id.textview_YJSSFee);//这个不太确定
            StringBuilder stringBuilder = new StringBuilder();
            textView.setText(stringBuilder.append(m_yjss).append("元 (现金或门店二维码收取)").toString());
            TypeReference<HashMap<String, String>> typeReference1 = new TypeReference<HashMap<String, String>>() {
            };
            m_BottlesMapKPL = JSON.parseObject(str2, typeReference1);
            TypeReference<HashMap<String, String>> typeReference2 = new TypeReference<HashMap<String, String>>() {

            };
            m_BottlesMapZPL = JSON.parseObject(str1, typeReference2);
            m_totalFee = "";
            m_buttonNext = (Button) findViewById(R.id.button_next);//提交订单按钮
            m_textViewPayStatus = (TextView) findViewById(R.id.textview_payStatus);//支付状态
            m_spinnerPaytype = (Spinner) findViewById(R.id.spinner_payType);//支付方式下拉列表
            m_textViewPaytype = (TextView) findViewById(R.id.textview_payType);//支付方式显示
            m_textViewTotalFee = (TextView) findViewById(R.id.textview_totalFee);//应收金额
            m_textViewOrginalFee = (TextView) findViewById(R.id.textview_orginalFee);//原本价格
            m_textViewCouponAmount = (TextView) findViewById(R.id.textview_couponAmount);//优惠金额
            m_imageViewPic = (ImageView) findViewById(R.id.imageView_pic);//拍照上传
            m_imageViewSign = (ImageView) findViewById(R.id.imageSign_pic);//电子签名  这个有问题 原来没有定义这个图片
            m_imageViewPic.setOnClickListener(this);
            m_imageViewSign.setOnClickListener(this);
            m_textViewResidualGasFee = (TextView) findViewById(R.id.textview_residualGasFee);//残气抵扣
            m_listView_kp = (ListView) findViewById(R.id.listview_kp);//抵扣详情
            JSONObject jSONObject1 = m_OrderJson.getJSONObject("customer");
            m_curUserId = jSONObject1.get("userId").toString();
            m_curUserSettlementType = jSONObject1.getJSONObject("settlementType");
            if (!m_curUserSettlementType.get("code").toString().equals("00002")) {
                m_isMonthDealUser = false;
            } else {
                m_isMonthDealUser = true;
            }
            m_textViewPaytype.setVisibility(View.INVISIBLE);
            if (m_isMonthDealUser) {
                m_spinnerPaytype.setVisibility(View.INVISIBLE);
                m_textViewPaytype.setVisibility(View.VISIBLE);
                m_textViewPaytype.setText(m_curUserSettlementType.get("name").toString());
            }
            for (Map.Entry<String, String> entry : m_BottlesMapKPL.entrySet()) {
                RefoundDetail refoundDetail = new RefoundDetail();
                refoundDetail.kp_weight = (String) entry.getValue();
                refoundDetail.forceCaculate = false;
                refoundDetail.isOK = false;
                refoundDetail.isFirst = true;
                refoundDetail.code = (String) entry.getKey();
                m_BottlesMapKP.put(refoundDetail.code, refoundDetail);
            }
        } catch (JSONException jSONException) {
            Toast.makeText(this, "异常" + jSONException.toString(), Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            m_orderPayStatus = m_OrderJson.getJSONObject("payStatus").get("name").toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        m_textViewPayStatus.setText(m_orderPayStatus);
        swipeRefreshLayout = findViewById(R.id.main_srl);
        swipeRefreshLayout.setColorSchemeResources(new int[]{R.color.blue, R.color.blue, R.color.blue, R.color.blue});
        SwipeRefreshLayout swipeRefreshLayout = this.swipeRefreshLayout;
        SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                if (m_BottlesMapKP.size() != 0) {
                    OrderCalculate();
                    return;
                }
                refleshPayStatus();
            }
        };
        swipeRefreshLayout.setOnRefreshListener(onRefreshListener);
        m_buttonNext.setOnClickListener(this);
        m_spinnerPaytype.setSelection(2, true);
        Spinner spinner = m_spinnerPaytype;
        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                switch (param1Int) {
                    case 0:
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
        GetDepLeader();
        Logger.e("size = " + m_BottlesMapKP.size());
        if (m_BottlesMapKP.size() != 0) {
            OrderCalculate();
        } else {
            setOrderAppendInfo(m_OrderJson);
        }
        ListView listView = m_listView_kp;
        AdapterView.OnItemLongClickListener onItemLongClickListener = new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                String str = ((TextView) param1View.findViewById(R.id.textview_SSFee)).getText().toString();//这个id不太确定
                if ((m_BottlesMapKP.get(str)).isOK) {
                    Toast.makeText(TrayOrderDealActivity.this, "计算完成，无需干预！", Toast.LENGTH_SHORT).show();
                    return true;
                }
                getRefoundNeededParams(str);
                return true;
            }
        };
        listView.setOnItemLongClickListener(onItemLongClickListener);
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        if (paramInt1 == 1 && paramInt2 == 101)
            picResult = paramIntent.getBooleanExtra("backup", false);
        super.onActivityResult(paramInt1, paramInt2, paramIntent);
    }

    public void onClick(View paramView) {
        Bundle bundle;
        Intent intent;
        switch (paramView.getId()) {
            case R.id.imageView_pic:
                intent = new Intent();
                bundle = new Bundle();
                bundle.putString("fileFolder", "order");
                bundle.putString("fileNameHeader", m_orderId);
                bundle.putInt("MaxPicCount", 4);
                intent.setClass(this, PicSelActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.imageSign_pic:
                intent = new Intent();
                bundle = new Bundle();
                bundle.putString("order", m_OrderJson.toString());
                bundle.putString("fileNameHeader", m_orderId);
                bundle.putString("fileFolder", "signature");
                bundle.putString("m_orderPayStatus", m_orderPayStatus);
                bundle.putSerializable("MapList", (Serializable) list_map);
                bundle.putString("KPCode", JSON.toJSONString(m_BottlesMapKPL));
                bundle.putString("ZPCode", JSON.toJSONString(m_BottlesMapZPL));
                bundle.putString("SpecMap", SpecMap);
                intent.setClass(this, PicSignActivity.class);
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
                break;
            case R.id.button_next:
                if (m_depLeader == null) {
                    Toast.makeText(this, "所属店长查询失败！请再次提交！", Toast.LENGTH_SHORT).show();
                    GetDepLeader();
                }
                Toast.makeText(this, "正在提交，请稍等。。。", Toast.LENGTH_SHORT).show();
                m_buttonNext.setText("正在提交...");
                m_buttonNext.setBackgroundColor(getResources().getColor(R.color.textgray));
                m_buttonNext.setEnabled(false);
                handlerDelayCommit.sendEmptyMessageDelayed(0, 1000L);
                break;
        }
    }

    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
    }

    public void onPointerCaptureChanged(boolean paramBoolean) {
    }

    public void onScroll(AbsListView paramAbsListView, int paramInt1, int paramInt2, int paramInt3) {
    }

    public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
    }

    public void refleshPayStatus() {
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        HashMap map = new HashMap<Object, Object>();
        map.put("orderSn", m_orderId);
        OkHttpUtil util = OkHttpUtil.getInstance(this);
        util.GET(OkHttpUtil.URL + "/Orders", map, new OkHttpUtil.ResultCallback() {
            @Override
            public void onError(Request request, Exception e) {
                Toast.makeText(TrayOrderDealActivity.this, "无数据！", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.code() != 200) {
                    Toast.makeText(TrayOrderDealActivity.this, "无数据！", Toast.LENGTH_LONG).show();
                }
                String string = response.body().string();
                Logger.e("refleshPayStatus: "+ string);
                Gson gson = new Gson();
                Data_Order data_order = gson.fromJson(string, Data_Order.class);
                if (data_order.getItems().size() == 1) {
                    try {
                        setOrderAppendInfo(new JSONObject(gson.toJson( data_order.getItems().get(0))));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
