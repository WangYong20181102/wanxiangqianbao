package com.jh.wxqb.ui.me;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gyf.immersionbar.ImmersionBar;
import com.jh.wxqb.R;
import com.jh.wxqb.api.ServerInterface;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.utils.LogUtils;

import butterknife.BindView;

/**
 * 全民发车
 */
public class NationalDepartureActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView mWebView;
    //全民发车url
    private String appUrl = ServerInterface.BASE_URL + "start/index.html#/";
//    private String appUrl = "http://192.168.101.71:8080/start#/";

    @Override
    protected int getLayout() {
        return R.layout.activity_national_departure;
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    @Override
    protected void init() {
        //设置沉浸式状态栏
        ImmersionBar.with(this).statusBarDarkFont(false).init();

        //参数拼接
        if (MyApplication.getToken() != null) {
            appUrl += "?token=" + MyApplication.getToken().getAccess_token() + "&webpath=" + ServerInterface.BASE_WEB_URL + "&platform=az" + "&lang=" + MyApplication.getLanuage();
            LogUtils.e("全民发车=====>" + appUrl);
        }


        mWebView.getSettings().setJavaScriptEnabled(true);// 支持js
        mWebView.getSettings().setUseWideViewPort(true); //自适应屏幕
        mWebView.setDrawingCacheEnabled(false);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setBlockNetworkImage(false);
        WebSettings settings = mWebView.getSettings();//获取设置对对象
        settings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        settings.setBuiltInZoomControls(false); //设置内置的缩放控件。若为false，则该WebView不可缩放
        settings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        settings.setAllowFileAccess(true); //设置可以访问文件
        settings.setTextZoom(100);
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
                showWaitDialog();
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
                dismissWaitDialog();
                //网页加载完成
                if (mWebView != null) {
                    mWebView.getSettings().setBlockNetworkImage(false);
                }
            }

            @Override
            public void onReceivedError(WebView webView, int errorCode, String description, String s1) {
            }
        });
        JavaScriptMethod method = new JavaScriptMethod(this, mWebView);
        mWebView.addJavascriptInterface(method, JavaScriptMethod.JAVAINTERFACE);

        mWebView.loadUrl(appUrl);  //访问路径


    }

    public class JavaScriptMethod {
        private Context mContext;
        private WebView mWebView;
        private static final String JAVAINTERFACE = "javaInterface";

        private JavaScriptMethod(Context context, WebView webView) {
            mContext = context;
            mWebView = webView;
        }

        //后退
        @JavascriptInterface
        public void blackApp() {
            finish();
        }

        /**
         * 设置支付密码
         */
        @JavascriptInterface
        public void setPayPassword() {
            Intent intent = new Intent(mContext, UdpPwdActivity.class);
            intent.putExtra("type", "pay");
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
            return false;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mWebView != null) {
            mWebView.clearCache(true);
            mWebView.clearHistory();
            mWebView.destroy();
            mWebView = null;
        }
    }
}
