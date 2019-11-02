package com.jh.wxqb.utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.jh.wxqb.R;
import com.jh.wxqb.base.CoreKeys;
import com.jh.wxqb.ui.home.HomePageFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadService extends Service {
    private static final int NOTIFY_DOW_ID = 0;
    private static final int NOTIFY_OK_ID = 1;

    private Context mContext = this;
    private boolean cancelled;
    public static int progress;
    private NotificationManager mNotificationManager;
    private Notification mNotification;

    private String dowUrl = "";

    private int fileSize;
    private int readSize;
    private int downSize;
    private File downFile;
    public static boolean isShow = false;

//    private static AsyncHttpClientHelper asyClient;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    RemoteViews contentView = mNotification.contentView;
                    contentView.setTextViewText(R.id.rate, (readSize < 0 ? 0 : readSize) + "b/s   " + msg.arg1 + "%");
                    contentView.setProgressBar(R.id.down_progress, 100, msg.arg1, false);

//                    AppLogger.e("DOWNLOAD PROGRESS:" + msg.arg1);
                    mNotificationManager.notify(NOTIFY_DOW_ID, mNotification);
                    break;
                case 1:
                    if (isShow) {
                        HomePageFragment.dialog.dismiss();//关闭弹窗
                    }
                    LogUtils.e("弹窗已关闭");
                    mNotificationManager.cancel(NOTIFY_DOW_ID);

                    openFile(downFile);
                    break;
                case 2:
                    mNotificationManager.cancel(NOTIFY_DOW_ID);
                    LogUtils.e("停止服务");
                    stopSelf();// 停掉服务自身
                    break;
            }
        }

        ;
    };

    @SuppressLint("HandlerLeak")
    private Handler handMessage = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(mContext, "服务器连接失败，请稍后再试！", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(mContext, "服务器端文件不存在，下载失败！", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
//                    String activityName = BaseActivity.GetTopActivity().getLocalClassName();
//                    AppLogger.e("xgd activityName", activityName);
//                    if (activityName.equals("com.shuabei.setting.VersionActivity")) {
                    Toast.makeText(mContext, "已是最新版本，不需要更新",
                            Toast.LENGTH_SHORT).show();
//                    }
            }
            handler.sendEmptyMessage(2);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        asyClient = AsyncHttpClientHelper.getInstance(getApplicationContext());
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        try {
            dowUrl = intent.getStringExtra("URL");
            LogUtils.e("dowUrl=>" + dowUrl);
            if (dowUrl.contains("https")) {
                dowUrl = dowUrl.replace("https", "http");
            }
            LogUtils.e("替换后=>" + dowUrl);
            createNotification(NOTIFY_DOW_ID);
            new Thread(startDownload).start();
        } catch (Exception e) {
//            AppLogger.e(e.getMessage());
            stopSelf();
            Toast.makeText(DownloadService.this, "下载错误", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNotificationManager.cancel(NOTIFY_DOW_ID);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 创建通知
     */
    private void createNotification(int notifyId) {
        switch (notifyId) {
            case NOTIFY_DOW_ID:
                int icon = R.mipmap.ic_launcher;
                CharSequence tickerText = "开始下载";
                long when = System.currentTimeMillis();
                mNotification = new Notification(icon, tickerText, when);

                // 放置在"正在运行"栏目中
                mNotification.flags = Notification.FLAG_ONGOING_EVENT;
                RemoteViews contentView = new RemoteViews(mContext.getPackageName(), R.layout.download_notification_layout);
                contentView.setTextViewText(R.id.fileName, "正在下载：vanNex.apk");
                mNotification.contentView = contentView;

                break;
            case NOTIFY_OK_ID:
                Toast.makeText(DownloadService.this, "下载完成", Toast.LENGTH_SHORT).show();
                stopSelf();// 停掉服务自身
                break;
        }

        // 最后别忘了通知一下,否则不会更新
        mNotificationManager.notify(notifyId, mNotification);
    }

    /**
     * 下载模块
     */
    private Runnable startDownload = new Runnable() {

        @Override
        public void run() {
            fileSize = 0;
            readSize = 0;
            downSize = 0;
            progress = 0;

            InputStream is = null;
            FileOutputStream fos = null;
            Log.e("downUrl", dowUrl);
            try {
                URL myURL = new URL(dowUrl);
                URLConnection conn = myURL.openConnection();
                conn.connect();
                fileSize = conn.getContentLength();
                is = conn.getInputStream();

                if (is == null) {
                    Log.d("tag", "error");
                    throw new RuntimeException("stream is null");
                }

                File dir = new File(CoreKeys.down_file);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                downFile = new File(CoreKeys.down_file + "paixin.apk");

                fos = new FileOutputStream(downFile);
                byte buf[] = new byte[1024 * 1024];
                while ((readSize = is.read(buf)) > 0) {
                    fos.write(buf, 0, readSize);
                    downSize += readSize;

                    sendMessage(0);
                }


                handler.sendEmptyMessage(1);

            } catch (MalformedURLException e) {
                handMessage.sendEmptyMessage(0);
            } catch (IOException e) {
                handMessage.sendEmptyMessage(1);
            } catch (Exception e) {
                handMessage.sendEmptyMessage(0);
            } finally {
                try {
                    if (null != fos) fos.close();
                    if (null != is) is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    };

    public void sendMessage(int what) {
        int num = (int) ((double) downSize / (double) fileSize * 100);

        if (num > progress + 1) {
            progress = num;

            Message msg0 = handler.obtainMessage();
            msg0.what = what;
            msg0.arg1 = progress;
            handler.sendMessage(msg0);
        }
    }

    private void openFile(File f) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri = FileProvider.getUriForFile(mContext, "com.jh.wxqb.fileprovider", f);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(f),
                    "application/vnd.android.package-archive");
        }
        mContext.startActivity(intent);
        System.exit(0);
    }


    /**
     * 获取进度
     */
    public int getProgress() {
        return progress;
    }
}
