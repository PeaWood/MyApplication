package com.gc.nfc.common;

import android.content.Context;
import com.gc.nfc.ui.BaseActivity;
import java.util.Map;

public class NetRequestConstant {
    public Map<String, Object> body;

    public Context context;

    public boolean isBodyJsonArray = false;

    public Map<String, Object> params;

    public String requestUrl;

    private BaseActivity.HttpRequestType type;

    public BaseActivity.HttpRequestType getType() {
        return this.type;
    }

    public void setBody(Map<String, Object> paramMap) {
        this.body = paramMap;
    }

    public void setParams(Map<String, Object> paramMap) {
        this.params = paramMap;
    }

    public void setType(BaseActivity.HttpRequestType paramHttpRequestType) {
        this.type = paramHttpRequestType;
    }
}
