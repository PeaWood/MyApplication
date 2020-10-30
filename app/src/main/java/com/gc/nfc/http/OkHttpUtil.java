package com.gc.nfc.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.http.EventHandler;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 网络请求管理
 */
public class OkHttpUtil {
    public static final String URL = "http://www.gasmart.com.cn/api";
    private volatile static OkHttpUtil okHttpUtil;//会被多线程使用，所以使用关键字volatile
    private static Context thiscontext;
    private OkHttpClient client;
    private Handler mHandler;
    private ProgressDialog progressDialog;

    public static void setContext(Context context) {
        thiscontext = context;
    }

    /**
     * 显示进度条
     */
    private void showProgressDialog() {
        progressDialog = new ProgressDialog(thiscontext);
        progressDialog.setMessage("加载中……");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    /**
     * 显示进度条
     */
    private void showProgressDialog(String progressText) {
        progressDialog = new ProgressDialog(thiscontext);
        progressDialog.setMessage(progressText);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    /**
     * 关闭进度条
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    //私有化构造方法
    private OkHttpUtil(Context context) {
        File sdcache = context.getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;//设置缓存大小
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .cache(new Cache(sdcache.getAbsoluteFile(), cacheSize));//设置缓存的路径
        client = builder.build();
        mHandler = new Handler();
    }

    /**
     * 单例模式，全局得到一个OkHttpUtil对象
     */
    public static OkHttpUtil getInstance(Context context) {
        thiscontext = context;
        if (okHttpUtil == null) {
            synchronized (OkHttpUtil.class) {
                if (okHttpUtil == null) {
                    okHttpUtil = new OkHttpUtil(context);
                }
            }
        }
        return okHttpUtil;
    }

    /**
     * 判断接口返回值
     *
     * @param result
     * @return
     */
    public static boolean checkSuccess(String result) {
        try {
            JSONObject object = new JSONObject(result);
            boolean success = object.getBoolean("success");
            return success;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * GET请求
     *
     * @param url      IP
     * @param map      参数
     * @param callback 回调函数
     */
    public void GET(String url, Map<String, String> map, final ResultCallback callback) {
        GET(url, map, callback, false);
    }

    public void GET(String url, Map<String, String> map, final ResultCallback callback, final boolean isshowprogress) {
        if (map != null && !map.isEmpty()) {
            url = url + "?";
            //遍历Map集合
            for (Map.Entry<String, String> entry : map.entrySet()) {
                url = url + entry.getKey() + "=" + entry.getValue() + "&";
            }
        }
        if (isshowprogress) {
            showProgressDialog();
        }
        SharedPreferences share = thiscontext.getSharedPreferences("Session",Context.MODE_PRIVATE);
        String sessionid= share.getString("sessionid","null");
        Logger.e("HTTP GET : " + url);
        Request request = new Request.Builder().get().url(url).addHeader("cookie",sessionid).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedCallback(call, e, callback, isshowprogress);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                saveSession(response);
                sendSuccessCallback(response, callback, isshowprogress);
            }
        });
    }

    private void saveSession(Response response) {
        Headers headers =response.headers();
        List cookies = headers.values("Set-Cookie");
        if(cookies==null||cookies.size()==0){
            return;
        }
        String session = (String) cookies.get(0);
        if(session.contains("JSESSIONID")){
            String sessionid = session.substring(0,session.indexOf(";"));
            SharedPreferences share = thiscontext.getSharedPreferences("Session",Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = share.edit();//编辑文件
            edit.putString("sessionid",sessionid);
            edit.commit();
        }
    }

    public void postForm(String url, final ResultCallback callback) {
        postForm(url, null, callback);
    }

    public void postForm(String url, Map<String, String> map, final ResultCallback callback) {
        postForm(url, map, callback, true);
    }

    public void postForm(String url, Map<String, String> map, final ResultCallback callback, String progressText) {
        postForm(url, map, callback, true, progressText);
    }

    /**
     * 封装上传文件
     */
    public void uploadFild(String url, String fileName, File file, final ResultCallback callback) {
        FormBody.Builder form = new FormBody.Builder();
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"),
                file);
        MultipartBody body = new MultipartBody.Builder()
                .setType(MediaType.parse("multipart/form-data"))
                .addFormDataPart("file", fileName, fileBody)
                .build();
        Request request = new Request.Builder()
                .post(body)
                .url(url)
                .build();
        //发送请求
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedCallback(call, e, callback, false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendSuccessCallback(response, callback, false);
            }
        });
    }

    /**
     * 提交表单数据
     *
     * @param url            请求地址
     * @param map            请求发送的数据
     * @param callback       回调函数
     * @param isshowprogress 是否展示进度框
     */
    public void postForm(String url, Map<String, String> map, final ResultCallback callback, final boolean isshowprogress) {
        FormBody.Builder form = new FormBody.Builder();//表单对象，包含以input开始的对象,以html表单为主
        String log = " " + "\n" + "URL :" + url;
        if (map != null && !map.isEmpty()) {
            log = log + "\n" + "Params : " + map.toString();
            //遍历Map集合
            for (Map.Entry<String, String> entry : map.entrySet()) {
                form.add(entry.getKey(), entry.getValue());
            }
        }
        Logger.e("HTTP Post" + log);
        if (isshowprogress) {
            showProgressDialog();
        }
        RequestBody body = form.build();
        Request request = new Request.Builder().url(url).post(body).build();//采用post提交数据
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedCallback(call, e, callback, isshowprogress);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendSuccessCallback(response, callback, isshowprogress);
            }
        });
    }

    /**
     * 提交表单数据
     *
     * @param url            请求地址
     * @param map            请求发送的数据
     * @param callback       回调函数
     * @param isshowprogress 是否展示进度框
     * @param progressText   进度框内容
     */
    public void postForm(String url, Map<String, String> map, final ResultCallback callback, final boolean isshowprogress, String progressText) {
        FormBody.Builder form = new FormBody.Builder();//表单对象，包含以input开始的对象,以html表单为主
        if (map != null && !map.isEmpty()) {
            Log.e(url, map.toString());
            //遍历Map集合
            for (Map.Entry<String, String> entry : map.entrySet()) {
                form.add(entry.getKey(), entry.getValue());
            }
        }
        if (isshowprogress) {
            showProgressDialog(progressText);
        }
        RequestBody body = form.build();
        Request request = new Request.Builder().url(url).post(body).build();//采用post提交数据
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedCallback(call, e, callback, isshowprogress);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendSuccessCallback(response, callback, isshowprogress);
            }
        });
    }

    /**
     * 当请求失败时，都会调用这个方法
     *
     * @param call
     * @param e
     * @param callback
     */
    private void sendFailedCallback(final Call call, final IOException e, final ResultCallback
            callback, final boolean isshowprogress) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.i("HTTP", "failed：" + Thread.currentThread().getName());
                if (callback != null) {
                    Toast.makeText(thiscontext, "系统维护中，请稍候再试", Toast.LENGTH_LONG)
                            .show();
                    if (isshowprogress)
                        closeProgressDialog();
                    callback.onError(call.request(), e);
                }
            }
        });
    }

    /**
     * 请求成功调用该方法
     *
     * @param response 返回的数据
     * @param callback 回调的接口
     */
    private void sendSuccessCallback(final Response response, final ResultCallback callback, final boolean isshowprogress) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (isshowprogress) {
                    closeProgressDialog();
                }
                Log.i("HTTP", "success：" + Thread.currentThread().getName());
                if (callback != null) {
                    try {
                        callback.onResponse(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void PUT(String url, Map<String, String> map, ResultCallback callback) {
        FormBody.Builder form = new FormBody.Builder();//表单对象，包含以input开始的对象,以html表单为主
        if (map != null && !map.isEmpty()) {
            Log.e("PUT", map.toString());
            //遍历Map集合
            for (Map.Entry<String, String> entry : map.entrySet()) {
                form.add(entry.getKey(), entry.getValue());
            }
        }
        Log.e("PUT", url);
        FormBody body = form.build();
        SharedPreferences share = thiscontext.getSharedPreferences("Session",Context.MODE_PRIVATE);
        String sessionid= share.getString("sessionid","null");
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .addHeader("cookie",sessionid)
                .addHeader("Content-Type", "application/json;charset=UTF-8")
                //.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                .build();//采用put提交数据
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedCallback(call, e, callback, false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                saveSession(response);
                sendSuccessCallback(response, callback, false);
            }
        });
    }

    /**
     * 创建接口，回调给调用者
     */
    public interface ResultCallback {
        void onError(Request request, Exception e);

        void onResponse(Response response) throws IOException;
    }

    public void uploadFiles(String url, List<File> files, final ResultCallback callback) {
        Call call = client.newCall(getRequest(url, files));
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendFailedCallback(call, e, callback, false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                sendSuccessCallback(response, callback, false);
            }
        });

    }

    private Request getRequest(String url, List<File> files) {
        Request.Builder builder = new Request.Builder();
        builder.url(url).post(getRequestBody(files));
        return builder.build();
    }

    private RequestBody getRequestBody(List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (files != null) {
            for (File file : files) {
                Logger.e("上传文件:文件名为:" + file.getName());
                String fileType = getMimeType(file.getName());
                Logger.e("文件类别为:" + fileType + "-----" + MediaType.parse(fileType));
                builder.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse(fileType), file));
            }
        }
        return builder.build();
    }

    private String getMimeType(String filename) {
        FileNameMap filenameMap = URLConnection.getFileNameMap();
        String contentType = filenameMap.getContentTypeFor(filename);
        if (contentType == null) {
            contentType = "application/octet-stream"; //* exe,所有的可执行程序
        }
        return contentType;
    }
}