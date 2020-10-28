package com.gc.nfc.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.support.v4.content.ContextCompat;

import com.gc.nfc.common.NetRequestConstant;
import com.gc.nfc.http.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NetUtil {
    public static String SESSIONID;

    public static CookieStore m_loginCookies;

    public static CookieStore m_tempCookies = null;

    static {
        m_loginCookies = null;
        SESSIONID = null;
    }

    public static HttpResponse httpGet(NetRequestConstant paramNetRequestConstant) {
        try {
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout((HttpParams) basicHttpParams, 20000);
            HttpConnectionParams.setSoTimeout((HttpParams) basicHttpParams, 20000);
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient((HttpParams) basicHttpParams);
            String str2 = paramNetRequestConstant.requestUrl;
            String str1 = str2;
            if (paramNetRequestConstant.params != null) {
                str1 = str2;
                if (!paramNetRequestConstant.params.isEmpty()) {
                    ArrayList<BasicNameValuePair> arrayList = new ArrayList(paramNetRequestConstant.params.size());
                    for (String str : paramNetRequestConstant.params.keySet()) {
                        BasicNameValuePair basicNameValuePair = new BasicNameValuePair(str, paramNetRequestConstant.params.get(str).toString());
                        arrayList.add(basicNameValuePair);
                    }
                    StringBuilder stringBuilder1 = new StringBuilder();
                    StringBuilder stringBuilder2 = stringBuilder1.append(str2).append("?");
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(arrayList, "UTF-8");
                    str1 = stringBuilder2.append(EntityUtils.toString((HttpEntity) urlEncodedFormEntity)).toString();
                }
            }
            HttpGet httpGet = new HttpGet(str1);
            if (m_loginCookies != null) {
                StringBuilder stringBuilder = new StringBuilder();
                httpGet.setHeader("Cookie", stringBuilder.append("JSESSIONID=").append(SESSIONID).toString());
                defaultHttpClient.setCookieStore(m_loginCookies);
            }
            HttpResponse httpResponse = defaultHttpClient.execute((HttpUriRequest) httpGet);
            m_tempCookies = defaultHttpClient.getCookieStore();
            return (HttpResponse) httpResponse;
        } catch (ClientProtocolException clientProtocolException) {
            return (HttpResponse) clientProtocolException;
        } catch (SocketTimeoutException e) {
            return (HttpResponse) e;
        } catch (ConnectTimeoutException e) {
            return (HttpResponse) e;
        } catch (IOException e) {
            return (HttpResponse) e;
        }

    }

    public static HttpResponse httpPost(NetRequestConstant paramNetRequestConstant) {
        try {
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout((HttpParams) basicHttpParams, 5000);
            HttpConnectionParams.setSoTimeout((HttpParams) basicHttpParams, 5000);
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient((HttpParams) basicHttpParams);
            String str2 = paramNetRequestConstant.requestUrl;
            String str1 = str2;
            if (paramNetRequestConstant.params != null) {
                str1 = str2;
                if (!paramNetRequestConstant.params.isEmpty()) {
                    ArrayList<BasicNameValuePair> arrayList = new ArrayList(paramNetRequestConstant.params.size());
                    for (String str : paramNetRequestConstant.params.keySet()) {
                        BasicNameValuePair basicNameValuePair = new BasicNameValuePair(str, paramNetRequestConstant.params.get(str).toString());
                        arrayList.add(basicNameValuePair);
                    }
                    StringBuilder stringBuilder2 = new StringBuilder();
                    StringBuilder stringBuilder1 = stringBuilder2.append(str2).append("?");
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(arrayList, "UTF-8");
                    str1 = stringBuilder1.append(EntityUtils.toString((HttpEntity) urlEncodedFormEntity)).toString();
                }
            }
            HttpPost httpPost = new HttpPost(str1);
            httpPost.setHeader("Content-Type", "application/json");
            Map map = paramNetRequestConstant.body;
            JSONObject jSONObject = new JSONObject();
            for (Object o : map.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                jSONObject.put((String) entry.getKey(), entry.getValue());
            }
            StringEntity stringEntity = new StringEntity(jSONObject.toString(), "UTF-8");
            httpPost.setEntity((HttpEntity) stringEntity);
            if (m_loginCookies != null) {
                StringBuilder stringBuilder1 = new StringBuilder();
                httpPost.setHeader("Cookie", stringBuilder1.append("JSESSIONID=").append(SESSIONID).toString());
                defaultHttpClient.setCookieStore(m_loginCookies);
            }
            HttpResponse httpResponse2 = defaultHttpClient.execute((HttpUriRequest) httpPost);
            StringBuilder stringBuilder = new StringBuilder();
            Logger.e(stringBuilder.append("NetUtil Code ：").append(paramNetRequestConstant.requestUrl).toString());
            HttpResponse httpResponse1 = httpResponse2;
            return httpResponse1;
        } catch (ClientProtocolException clientProtocolException) {
            return (HttpResponse) clientProtocolException;
        } catch (SocketTimeoutException e) {
            return (HttpResponse) e;
        } catch (ConnectTimeoutException e) {
            return (HttpResponse) e;
        } catch (IOException e) {
            return (HttpResponse) e;
        } catch (JSONException e) {
            return (HttpResponse) e;
        }
    }

    public static HttpResponse httpPut(NetRequestConstant paramNetRequestConstant) {
        try {
            BasicHttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout((HttpParams) basicHttpParams, 5000);
            HttpConnectionParams.setSoTimeout((HttpParams) basicHttpParams, 5000);
            DefaultHttpClient defaultHttpClient = new DefaultHttpClient((HttpParams) basicHttpParams);
            String str2 = paramNetRequestConstant.requestUrl;
            String str1 = str2;
            if (paramNetRequestConstant.params != null) {
                str1 = str2;
                if (!paramNetRequestConstant.params.isEmpty()) {
                    ArrayList<BasicNameValuePair> arrayList = new ArrayList(paramNetRequestConstant.params.size());
                    for (String str : paramNetRequestConstant.params.keySet()) {
                        BasicNameValuePair basicNameValuePair = new BasicNameValuePair(str, paramNetRequestConstant.params.get(str).toString());
                        arrayList.add(basicNameValuePair);
                    }
                    StringBuilder stringBuilder2 = new StringBuilder();
                    StringBuilder stringBuilder1 = stringBuilder2.append(str2).append("?");
                    UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(arrayList, "UTF-8");
                    str1 = stringBuilder1.append(EntityUtils.toString((HttpEntity) urlEncodedFormEntity)).toString();
                }
            }
            HttpPut httpPut = new HttpPut(str1);
            httpPut.setHeader("Content-Type", "application/json; charset=utf-8");
            if (paramNetRequestConstant.body != null) {
                StringEntity stringEntity;
                Map map = paramNetRequestConstant.body;
                if (map.size() == 1 && paramNetRequestConstant.isBodyJsonArray) {
                    JSONArray jSONArray = (JSONArray) map.get("jsonArray");
                    stringEntity = new StringEntity(jSONArray.toString());
                    stringEntity.setContentType("application/json");
                    httpPut.setEntity((HttpEntity) stringEntity);
                } else {
                    JSONObject jSONObject = new JSONObject();
                    for (Object o : map.entrySet()) {
                        Map.Entry entry = (Map.Entry) o;
                        jSONObject.put((String) entry.getKey(), entry.getValue());
                    }
                    stringEntity = new StringEntity(jSONObject.toString(), "UTF-8");
                    httpPut.setEntity((HttpEntity) stringEntity);
                }
            }
            if (m_loginCookies != null) {
                StringBuilder stringBuilder1 = new StringBuilder();
                httpPut.setHeader("Cookie", stringBuilder1.append("JSESSIONID=").append(SESSIONID).toString());
                defaultHttpClient.setCookieStore(m_loginCookies);
            }
            HttpResponse httpResponse2 = defaultHttpClient.execute((HttpUriRequest) httpPut);
            StringBuilder stringBuilder = new StringBuilder();
            Logger.e(stringBuilder.append("NetUtil Code ：").append(paramNetRequestConstant.requestUrl).toString());
            HttpResponse httpResponse1 = httpResponse2;
            return httpResponse1;
        } catch (ClientProtocolException e) {
            return (HttpResponse) e;
        } catch (SocketTimeoutException e) {
            return (HttpResponse) e;
        } catch (ConnectTimeoutException e) {
            return (HttpResponse) e;
        } catch (IOException e) {
            return (HttpResponse) e;
        } catch (JSONException e) {
            return (HttpResponse) e;
        }
    }

    public static boolean isCheckNet(Context paramContext) {
        String[] deniedPermissions = new String[]{Manifest.permission.INTERNET};
        if (ContextCompat.checkSelfPermission(paramContext, deniedPermissions[0]) != PackageManager.PERMISSION_GRANTED) {
            return false;
        } else {
            return true;
        }
    }

    public static void setLoginCookies() {
        m_loginCookies = m_tempCookies;
        List<Cookie> list = m_loginCookies.getCookies();
        for (byte b = 0; ; b++) {
            if (b < list.size()) {
                if ("JSESSIONID".equals(((Cookie) list.get(b)).getName())) {
                    SESSIONID = ((Cookie) list.get(b)).getValue();
                    return;
                }
            } else {
                return;
            }
        }
    }
}