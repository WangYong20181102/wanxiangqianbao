package com.jh.wxqb.ui.home;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;

import com.jh.wxqb.R;
import com.jh.wxqb.api.ServerInterface;
import com.jh.wxqb.base.BaseFragment;
import com.jh.wxqb.base.MainActivity;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.bean.MessageDetailsBean;
import com.jh.wxqb.bean.MyMessageBean;
import com.jh.wxqb.bean.NewsInfoBean;
import com.jh.wxqb.bean.NewsMoreListBean;
import com.jh.wxqb.bean.UserBean;
import com.jh.wxqb.bean.VersionUpdateBean;
import com.jh.wxqb.customview.AppUpdateProgressDialog;
import com.jh.wxqb.ui.home.presenter.HomePresenter;
import com.jh.wxqb.ui.home.view.HomeView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.DownloadService;
import com.jh.wxqb.utils.GetEditionCode;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.SystemUtils;
import com.jh.wxqb.utils.Toasts;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 首页
 */
public class HomeFragment extends BaseFragment implements HomeView {

    private View view;
    @BindView(R.id.webView)
    WebView mWebView;
    private String appUrl = ServerInterface.BASE_URL + "index.html#/home";
    private static int REQUEST_WRITE = 42;
    public static AppUpdateProgressDialog dialog;
    private int mMaxProgress = 100;//百分比
    private HomePresenter presenter;
    private Intent download;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            ButterKnife.bind(this, view);
            EventBus.getDefault().register(this);
            initWebView();
            presenter = new HomePresenter(this);
            presenter.getUserInfo();
            presenter.versionUpdate();
        }
        return view;
    }

    //初始化WebView配置
    private void initWebView() {
        if (MyApplication.getToken() != null) {
            appUrl += "?token=" + MyApplication.getToken().getAccess_token()
                    + "&webpath=" + ServerInterface.BASE_WEB_URL + "&platform=az" + "&lang=" + MyApplication.getLanuage();
            LogUtils.e("首页访问路径==>" + appUrl);
        }
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
//                ((MainActivity) mContext).dismissWaitDialog();
            }

            @Override
            public void onReceivedError(WebView webView, int errorCode, String description, String s1) {
//                if (mWebView != null)
//                    mWebView.loadUrl("www.baidu.com");
            }
        });
        //创建上面创建类的对象

//        JavaScriptMethod method = new JavaScriptMethod();
//        其实就是告诉js，我提供给哪个对象给你调用，这样js就可以调用对象里面的方法
//        第二个参数就是该类中的字符串常量
        HomeFragment.JavaScriptMethod method = new HomeFragment.JavaScriptMethod(mContext, mWebView);
        mWebView.addJavascriptInterface(method, JavaScriptMethod.JAVAINTERFACE);
    }

    public class JavaScriptMethod {
        private Context mContext;
        private WebView mWebView;
        public static final String JAVAINTERFACE = "javaInterface";

        public JavaScriptMethod(Context context, WebView webView) {
            mContext = context;
            mWebView = webView;
        }

        //查看更多新闻
        @JavascriptInterface
        public void selMoreNews() {
            Intent intent = new Intent(mContext, NewsListActivity.class);
            startActivity(intent);
        }

        //查看新闻详情
        @JavascriptInterface
        public void selNewsInfo(String id) {
            Intent intent = new Intent(mContext, NewsInfoActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }

    }

    @Subscribe
    public void udpHome(String udpHome) {
        switch (udpHome) {
            case "udpHome":
                presenter.getUserInfo();
                break;
            case "udpHomeWenView":
                mWebView.reload();
                break;
        }
    }

    @OnClick(R.id.ll_message)
    public void onViewClicked() {
        Intent intent = new Intent(mContext, MyMessageActivity.class);
        startActivity(intent);
    }

    @Override
    public void getUserInfoSuccess(UserBean result) {
        if (((MainActivity) mContext).isFinishing()) {
            return;
        }
        LogUtils.e("getUserInfoSuccess==>" + GsonUtil.GsonString(result));
        if (result.getData() != null) {
            MyApplication.setUserBean(null);
            MyApplication.setUserBean(result.getData().getInfo());
            LogUtils.e("本地用户信息==>" + GsonUtil.GsonString(MyApplication.getUserBean()));
            EventBus.getDefault().post("udpMe");
            EventBus.getDefault().post("udpWithdrawMoney");
            EventBus.getDefault().post("udpTurnOut");
            EventBus.getDefault().post("udpPurchaseOrder");
            EventBus.getDefault().post("udpAssestNum");
        }
    }

    @Override
    public void versionUpdateSuccess(VersionUpdateBean result) {
        if (((MainActivity) mContext).isFinishing()) {
            return;
        }
        LogUtils.e("versionUpdateSuccess==>" + GsonUtil.GsonString(result));
        upDataApp(result);
    }

    /**
     * 版本更新
     */
    private void upDataApp(VersionUpdateBean result) {
        String versionNameCode = GetEditionCode.getVersionNameCode(mContext);
        LogUtils.e("本地版本号versionNameCode==>" + versionNameCode);
        LogUtils.e("服务端版本号versionNameCode==>" + result.getData().getApp().getVers());
        BigDecimal localOverCode = new BigDecimal(Double.parseDouble(versionNameCode));
        BigDecimal serverVer = new BigDecimal(Double.valueOf(result.getData().getApp().getVers()));
        int comparisonResult = localOverCode.compareTo(serverVer);
        LogUtils.e("比较结果==>" + comparisonResult);
        if (comparisonResult < 0) {
            //进入更新弹窗
            switch (result.getData().getApp().getVersion()) {
                case 1: //非重要版本
                    updAppDialog(mContext, result.getData().getApp().getComment(), false, result.getData().getApp().getUrl(), String.valueOf(result.getData().getApp().getVers()));
                    break;
                case 2:  //重要版本
                    updAppDialog(mContext, result.getData().getApp().getComment(), true, result.getData().getApp().getUrl(), String.valueOf(result.getData().getApp().getVers()));
                    break;
            }
        }
    }

    /**
     * 更新APP弹窗
     */
    public void updAppDialog(Context context, String content, final boolean isShow, final String url, String version) {
        dialog = new AppUpdateProgressDialog(context, content, isShow, version) {
            @Override
            public void udp() {
                super.udp();
                LogUtils.e("url=======>" + url);
                DownloadService.isShow = isShow;
                if (isShow) {
                    download = new Intent(mContext, DownloadService.class);
                    download.putExtra("URL", url);
                    mContext.startService(download);
                } else {
                    download = new Intent(mContext, DownloadService.class);
                    download.putExtra("URL", url);
                    mContext.startService(download);
                }
                showDialog();
            }

            @Override
            public void close() {
                super.close();
                presenter.getUserInfo();
//                EventBus.getDefault().post("selAssets");
            }
        };
        dialog.show();
    }

    /**
     * 更新弹窗
     */
    private void showDialog() {

        //正在下载时，不可关闭dialog
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    Toasts.showShort("正在下载,请稍后...");
                    return true;
                } else {
                    return false;
                }
            }
        });
        dialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (DownloadService.progress < mMaxProgress) {
                    Message msg = myHandler.obtainMessage();
                    msg.what = 100;
                    myHandler.sendMessage(msg);
                }
            }
        }).start();
    }

    //设置更新界面进度
    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    dialog.setProgress(DownloadService.progress);
                    break;
            }
        }
    };

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    @Override
    protected void onFragmentFirstVisible() {

    }

    @Override
    public void newsInfoSuccess(NewsInfoBean result) {

    }

    @Override
    public void newsListSuccess(NewsMoreListBean result) {

    }

    @Override
    public void myMessageSuccess(MyMessageBean result) {

    }

    @Override
    public void myMessageDetailsSuccess(MessageDetailsBean result) {

    }

    @Override
    public void onViewFailureString(int statue, String message) {
        if (((MainActivity) mContext).isFinishing()) {
            return;
        }

        ((MainActivity) mContext).dismissWaitDialog();
        AgainLoginUtil.againLogin(mContext, statue);
        Toasts.showShort(message);

    }

    @Override
    public void onServerFailure(String e, int code) {
        if (((MainActivity) mContext).isFinishing()) {
            return;
        }
        ((MainActivity) mContext).dismissWaitDialog();
        Toasts.showShort(e);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        boolean serviceRunning = SystemUtils.isServiceRunning(getContext(), DownloadService.class.getName());
        if (serviceRunning) {
            mContext.stopService(download);
        }
        EventBus.getDefault().unregister(this);
    }

}
