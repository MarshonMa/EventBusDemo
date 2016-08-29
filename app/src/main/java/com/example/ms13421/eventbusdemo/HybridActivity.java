package com.example.ms13421.eventbusdemo;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.zxy.jsbridge.RainbowBridge;
import com.zxy.jsbridge.core.JsBridgeWebChromeClient;

public class HybridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hybrid);
        WebView webView = (WebView) findViewById(R.id.webView);
        WebSettings websettings = webView.getSettings();

        websettings.setJavaScriptEnabled(true); // 支持JS
        websettings.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过js打开新的窗口
        websettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//提高渲染等级
        websettings.setDomStorageEnabled(true);//使用localStorage则必须打开
        websettings.setBlockNetworkImage(true);// 首先阻塞图片，让图片不显示
        websettings.setBlockNetworkImage(false);//  页面加载好以后，在放开图片：
        websettings.setSupportMultipleWindows(false);// 设置同一个界面
        websettings.setBlockNetworkImage(false);

        //设置自适应屏幕，两者合用
        websettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        websettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        websettings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        websettings.setBuiltInZoomControls(true); //设置内置的缩放控件。
        //若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。

        websettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        websettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        websettings.supportMultipleWindows();  //多窗口
        websettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        websettings.setAllowFileAccess(true);  //设置可以访问文件
        websettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        websettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        websettings.setLoadsImagesAutomatically(true);  //支持自动加载图片

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //页面开始加载时
                super.onPageStarted(view, url, favicon);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                //页面加载结束时
                super.onPageFinished(view, url);
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 这里进行无网络或错误处理，具体可以根据errorCode的值进行判断，
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                /**
                 * 网页跳转：
                 * 1.在当前的webview跳转到新连接
                 * view.loadUrl(url);
                 * 2.调用系统浏览器跳转到新网页
                 * Intent i = new Intent(Intent.ACTION_VIEW);
                 * i.setData(Uri.parse(url));
                 * startActivity(i);
                 */
                return true;
            }
        });

        webView.setWebChromeClient(new JsBridgeWebChromeClient());
        RainbowBridge.getInstance()
                .clazz(JsInvokeJavaScope.class)
                .inject();

        webView.loadUrl("file:///android_asset/test.html");
    }
}
