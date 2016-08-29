package com.zxy.jsbridge.core;

import android.util.Log;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by zhengxiaoyong on 16/4/19.
 */
public class JsBridgeWebChromeClient extends WebChromeClient {

    @Override
    public final boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        result.confirm();
        Log.d("JsBridgeWebChromeClient", "onJsPrompt");
        JsCallJava.newInstance().call(view,message);
        return true;
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        Log.d("JsBridgeWebChromeClient", "onJsConfirm");
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Log.d("JsBridgeWebChromeClient", "onJsAlert");
        return super.onJsAlert(view, url, message, result);
    }
}
