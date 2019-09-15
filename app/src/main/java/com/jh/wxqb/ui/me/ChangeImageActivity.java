package com.jh.wxqb.ui.me;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;

import com.jh.wxqb.R;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.CoreKeys;
import com.jh.wxqb.base.MyApplication;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.FinancialDetailsBean;
import com.jh.wxqb.bean.FinancialDetailsTypeBean;
import com.jh.wxqb.bean.MyTeamBean;
import com.jh.wxqb.bean.OrderAddImgBean;
import com.jh.wxqb.bean.UserImage;
import com.jh.wxqb.customview.CircleImageView;
import com.jh.wxqb.customview.PictureSelectorConfig;
import com.jh.wxqb.ui.me.presenter.MePresenter;
import com.jh.wxqb.ui.me.view.MeView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.BitmapUtil;
import com.jh.wxqb.utils.GlideUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.Toasts;
import com.luck.picture.lib.config.PictureConfig;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 更換頭像
 */
public class ChangeImageActivity extends BaseActivity implements MeView {


    @BindView(R.id.iv_user_img)
    CircleImageView ivUserImg;
    @BindView(R.id.tv_sel_img)
    TextView tvSelImg;

    private File mPictureFile;  //拍照保存的临时图片文件
    private File upLoadFile;  //上传用户选择的头像文件
    private RequestBody requestFile;
    private MultipartBody.Part body;
    private MePresenter mePresenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_change_image;
    }

    @Override
    protected void init() {
        mePresenter = new MePresenter(this);
        initView();
    }

    private void initView() {
        if(MyApplication.getUserImg()!=null){
            ivUserImg.setImageBitmap(BitmapUtil.compressImage(BitmapUtil.ratio(BitmapUtil.stringtoBitmap(MyApplication.getUserImg()),302,302)));
        }
    }

    @OnClick(R.id.tv_sel_img)
    public void onViewClicked() {
        if (!isGrantExternalRW(this)) {
            Toast.makeText(this, "请开启存储权限后再打开相册", Toast.LENGTH_LONG).show();
            return;
        }
        selectPic(CoreKeys.NUMBER_ONE);
    }

    /**
     * 添加照片
     *
     * @param num 本次添加图片的最大个数
     */
    private void selectPic(int num) {
        PictureSelectorConfig.initMultiConfig(ChangeImageActivity.this, num);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    //图片选择结果的回调
                    Object result1 = (List) data.getSerializableExtra("extra_result_media");
                    List<OrderAddImgBean> list = GsonUtil.GsonToList(GsonUtil.GsonString(result1), OrderAddImgBean.class);
                    OrderAddImgBean imgBean = list.get(0);
                    String path = imgBean.getPath();
                    upLoadFile = new File(path);
                    requestFile = RequestBody.create(MediaType.parse("image/jpg"), upLoadFile);   //上传参数
                    body = MultipartBody.Part.createFormData("file", upLoadFile.getName(), requestFile);
                    GlideUtil.show_Img(this, path, ivUserImg);
                    LogUtils.e("图片路径===>" + path);
                    showWaitDialog();
                    mePresenter.uploadUserImage(1, body);
                    break;
                case CoreKeys.TAKEPICTURE:
                    //获取拍照图片路径
                    String fileSrc = mPictureFile.getAbsolutePath();
                    upLoadFile = new File(fileSrc);
                    requestFile = RequestBody.create(MediaType.parse("image/jpg"), upLoadFile);
                    body = MultipartBody.Part.createFormData("file", upLoadFile.getName(), requestFile);
                    GlideUtil.show_Img(this, fileSrc, ivUserImg);
                    LogUtils.e("拍照图片路径===>" + fileSrc);
                    showWaitDialog();
                    mePresenter.uploadUserImage(1, body);
                    break;
            }
        }
    }


    @Override
    public void uploadUserImageSuccess(BaseBean result) {
        if(isFinishing()){
            return;
        }
        dismissWaitDialog();
        LogUtils.e("uploadUserImageSuccess==>"+GsonUtil.GsonString(result));
        EventBus.getDefault().post("udpUserImage");
        Toasts.showShort(result.getMessage());
    }

    @Override
    public void financialDetailsTypeSuccess(FinancialDetailsTypeBean result) {

    }

    @Override
    public void getFinancialDetailsSuccess(FinancialDetailsBean result) {

    }

    @Override
    public void getEmailCodeSuccess(BaseBean result) {

    }

    //判断相册权限
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

    @Override
    public void onViewFailureString(int statue, String message) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        Toasts.showShort(message);
        AgainLoginUtil.againLogin(this, statue);
    }

    @Override
    public void onServerFailure(String e, int code) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        Toasts.showShort(e);
    }


    @Override
    public void udpLoginPwdSuccess(BaseBean result) {

    }

    @Override
    public void bandingEmailSuccess(BaseBean result) {

    }

    @Override
    public void myTeamSuccess(MyTeamBean result) {

    }

    @Override
    public void selUserImageSuccess(UserImage result) {

    }

}
