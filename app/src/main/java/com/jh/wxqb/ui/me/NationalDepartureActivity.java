package com.jh.wxqb.ui.me;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;

import com.jh.wxqb.R;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.ui.home.HomeFragment;
import com.jh.wxqb.utils.LogUtils;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;

/**
 * 全民发车
 */
public class NationalDepartureActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView mWebView;
    private static final String appUrl = "http://192.168.101.50:8081/vannex/#/";

    @Override
    protected int getLayout() {
        return R.layout.activity_national_departure;
    }

    @Override
    protected void init() {
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
                //网页加载完成
                mWebView.getSettings().setBlockNetworkImage(false);
//                ((MainActivity) mContext).dismissWaitDialog();
            }

            @Override
            public void onReceivedError(WebView webView, int errorCode, String description, String s1) {
//                if (mWebView != null)
//                    mWebView.loadUrl("www.baidu.com");
            }
        });
        JavaScriptMethod method = new JavaScriptMethod(this, mWebView);
        mWebView.addJavascriptInterface(method, JavaScriptMethod.JAVAINTERFACE);

        mWebView.loadUrl(appUrl);  //访问路径


    }

    public class JavaScriptMethod {
        private Context mContext;
        private WebView mWebView;
        public static final String JAVAINTERFACE = "javaInterface";

        public JavaScriptMethod(Context context, WebView webView) {
            mContext = context;
            mWebView = webView;
        }

        //后退
        @JavascriptInterface
        public void blackApp() {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
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
}
