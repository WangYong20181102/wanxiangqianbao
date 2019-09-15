package com.jh.wxqb.ui.home;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.android.AutoScannerView;
import com.google.zxing.client.android.BaseCaptureActivity;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.jh.wxqb.R;
import com.jh.wxqb.base.CoreKeys;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.Toasts;

import java.util.Hashtable;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 二维码扫描
 */
public class ScanningCodeActivity extends BaseCaptureActivity {


    @BindView(R.id.preview_view)
    SurfaceView surfaceView;
    @BindView(R.id.autoscanner_view)
    AutoScannerView autoScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning_code);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        autoScannerView.setCameraManager(cameraManager);
    }

    @Override
    public SurfaceView getSurfaceView() {
        return (surfaceView == null) ? (SurfaceView) findViewById(R.id.preview_view) : surfaceView;
    }

    @Override
    public void dealDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        playBeepSoundAndVibrate(true, false);
        tran(rawResult.getText());
    }

    public void tran(String text) {
        LogUtils.e("扫描结果==>" + text);
        if(!isContainChinese(text)){
            Intent intent = new Intent();
            intent.putExtra("data", text);
            setResult(CoreKeys.RESULT_CODE, intent);
            finish();
        }else {
            close();
        }
    }

    public void close(){
        Toasts.showLong("未知二维码");
        finish();
    }

    /**
     * 判断是否包含中文
     *
     * @param str
     * @return
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    @OnClick({R.id.iv_back, R.id.check_gallery})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.check_gallery:
                if (!isGrantExternalRW(this)) {
                    Toast.makeText(this, "请开启存储权限后再打开相册", Toast.LENGTH_LONG).show();
                    return;
                }
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, CoreKeys.RESULT_LOAD_IMAGE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if (requestCode == CoreKeys.RESULT_LOAD_IMAGE && null != data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode != Activity.RESULT_OK) {
                return;
            }
            Log.e("=_=", "onActivityResult 开始解析");
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    Log.e("=_=", "run 解析完成");
                    Uri uri = data.getData();
                    if (uri == null) {
                        Log.e("=_=", "uri is null");
                        showToastInWorkThread(R.string.me_two_dimensional_code_selection_failure);
                        return;
                    }
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                    if (cursor == null) {
                        Log.e("=_=", "cursor is null");
                        showToastInWorkThread(R.string.me_two_dimensional_code_acquisition_failure);
                        return;
                    }
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath = cursor.getString(columnIndex);
                    Log.e("=_=", "path=" + picturePath);
                    cursor.close();
                    Bitmap bitmap = decodeBitmapFromPath(picturePath);
                    if (bitmap == null) {
                        Log.e("=_=", "bitmap is null");
                        showToastInWorkThread(R.string.me_two_dimensional_code_acquisition_failure);
                        return;
                    }
                    Result result = scanQrCode(bitmap);
                    if (result == null) {
                        Log.e("=_=", "result is null");
                        showToastInWorkThread(R.string.me_two_dimensional_code_dimensional_failure);

                    } else {
                        tran(result.getText());
                        Log.e("=_=", "run 解析成功 =>" + result.getText());
                    }
                }
            });
        }
    }

    public Result scanQrCode(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, CoreKeys.CHARACTER_SET);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(binaryBitmap, hints);
        } catch (NotFoundException e) {
        } catch (ChecksumException e) {
        } catch (FormatException e) {
        }
        return null;
    }

    private Bitmap decodeBitmapFromPath(String picturePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(picturePath, options);
        options.inJustDecodeBounds = false;
        int sampleSize = (int) (options.outHeight / 400 * 1f);
        if (sampleSize <= 0) {
            sampleSize = 1;
        }
        options.inSampleSize = sampleSize;
        return BitmapFactory.decodeFile(picturePath, options);
    }


    private void showToastInWorkThread(int text) {
        Looper.prepare();
        showToast(text);
        Looper.loop();
    }

    private void showToast(int text) {
        Toast toast = Toast.makeText(this, text, Toast.LENGTH_LONG);
        toast.show();
    }

    public static boolean isGrantExternalRW(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
            return false;
        }
        return true;
    }

}
