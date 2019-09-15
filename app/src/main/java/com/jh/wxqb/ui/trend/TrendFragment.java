package com.jh.wxqb.ui.trend;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jh.wxqb.R;
import com.jh.wxqb.base.BaseFragment;
import com.jh.wxqb.utils.LogUtils;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class TrendFragment extends BaseFragment {

    private View view;
    @BindView(R.id.webView)
    WebView mWebView;
    private String appUrl = "http://120.79.88.151:9988/index.html";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_trend, container, false);
            ButterKnife.bind(this, view);
            initWebView();
        }
        return view;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    //初始化WebView配置
    private void initWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);// 支持js
        mWebView.getSettings().setUseWideViewPort(true); //自适应屏幕
        mWebView.loadUrl(appUrl);  //访问路径
        WebSettings settings = mWebView.getSettings();//获取设置对对象
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        settings.setAllowFileAccess(true); //设置可以访问文件
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        settings.setLoadsImagesAutomatically(true); //支持自动加载图片
        settings.setDefaultTextEncodingName("utf-8");//设置编码格式
        settings.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        settings.setDatabaseEnabled(true);   //开启 database storage API 功能
        settings.setAppCacheEnabled(true);//开启 Application Caches 功能
        mWebView.setWebViewClient(new WebViewClient() {
            //调用加载条
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            //在当前WebView中打开
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.e("url=>" + url);
                view.loadUrl(url);
                return true;
            }

            //隐藏加载条
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView webView, int errorCode, String description, String s1) {
                if (mWebView != null)
                    mWebView.loadUrl("www.baidu.com");
            }
        });
        //创建上面创建类的对象
//        JavaScriptMethod method = new JavaScriptMethod();
        //其实就是告诉js，我提供给哪个对象给你调用，这样js就可以调用对象里面的方法
        //第二个参数就是该类中的字符串常量
//        mWebView.addJavascriptInterface(method, JavaScriptMethod.JAVAINTERFACE);
    }
}
