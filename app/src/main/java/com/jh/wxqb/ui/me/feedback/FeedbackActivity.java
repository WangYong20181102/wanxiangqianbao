package com.jh.wxqb.ui.me.feedback;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;

import com.jh.wxqb.R;
import com.jh.wxqb.adapter.GridViewAdapter;
import com.jh.wxqb.base.BaseActivity;
import com.jh.wxqb.base.CoreKeys;
import com.jh.wxqb.bean.BaseBean;
import com.jh.wxqb.bean.FeedbackInfoBean;
import com.jh.wxqb.bean.FeedbackListBean;
import com.jh.wxqb.bean.OrderAddImgBean;
import com.jh.wxqb.customview.NoScrollGridView;
import com.jh.wxqb.customview.PictureSelectorConfig;
import com.jh.wxqb.ui.me.feedback.presenter.FeedBackPresenter;
import com.jh.wxqb.ui.me.feedback.view.FeedBackView;
import com.jh.wxqb.utils.AgainLoginUtil;
import com.jh.wxqb.utils.GsonUtil;
import com.jh.wxqb.utils.LogUtils;
import com.jh.wxqb.utils.MyClicker;
import com.jh.wxqb.utils.StringUtil;
import com.jh.wxqb.utils.Toasts;
import com.luck.picture.lib.config.PictureConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 用户反馈
 */
public class FeedbackActivity extends BaseActivity implements MyClicker, FeedBackView {

    @BindView(R.id.ed_content)
    EditText edContent;
    @BindView(R.id.iv_add_img)
    ImageView ivAddImg;
    @BindView(R.id.gridView)
    NoScrollGridView mGridView;
    private ArrayList<String> mPicList = new ArrayList<>(); //上传的图片凭证的数据源
    private GridViewAdapter mGridViewAdapter;
    private File mPictureFile;
    private boolean isViewVisible = false;  //是否显示选择图片
    private List<MultipartBody.Part> uploadList;//上传图片的参数
    private FeedBackPresenter presenter;

    @Override
    protected int getLayout() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void init() {
        presenter = new FeedBackPresenter(this);
        setTitleListener(R.id.tv_title);
        initGridView();
        isViewVisible();
    }

    @Override
    protected void toRight() {
        super.toRight();
        Intent intent = new Intent(this, FeedbackListActivity.class);
        startActivity(intent);
    }

    @OnClick({R.id.iv_add_img, R.id.tv_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_img:
                //添加照片的凭证
                selectPic(CoreKeys.MAX_SELECT_PIV_NUM - mPicList.size());
                break;
            case R.id.tv_commit:
                createFileValue();
                if (edContent.getText().toString().isEmpty()) {
                    Toasts.showShort(R.string.please_input_your_valuable_opinions);
                    return;
                }
                StringUtil.Closekeyboard(this);
                showWaitDialog();
                presenter.saveUserFeedback(uploadList, edContent.getText().toString());
                break;
        }
    }

    //创建上传图片的参数
    public void createFileValue() {
        uploadList = new ArrayList<>();
        for (int i = 0; i < mPicList.size(); i++) {
            LogUtils.e("下标===>"+i);
            LogUtils.e("path==>" + mPicList.get(i));
            File file = new File(mPicList.get(i));
            int index = i + 1;
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("file" +index, file.getName(), requestFile);
            uploadList.add(body);
        }
        LogUtils.e("当前图片集合大小==>" + uploadList.size());
        LogUtils.e("当前图片集合数据==>" + GsonUtil.GsonString(uploadList));
    }

    //动态更改布局显示
    public void isViewVisible() {
        if (isViewVisible) {
            mGridView.setVisibility(View.VISIBLE);
            ivAddImg.setVisibility(View.GONE);
        } else {
            mGridView.setVisibility(View.GONE);
            ivAddImg.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 添加照片
     * <p>
     * // @param num 本次添加图片的最大个数
     */
    private void selectPic(int num) {
        PictureSelectorConfig.initMultiConfig(FeedbackActivity.this, num);
    }

    /**
     * 初始化GridView
     */
    private void initGridView() {
        mGridViewAdapter = new GridViewAdapter(FeedbackActivity.this, mPicList, this);
        mGridView.setAdapter(mGridViewAdapter);
        //设置GridView的条目的点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == parent.getChildCount() - 1) {
                    //如果添加按钮是最后一张 并且添加图片的数量不超过9张
                    if (mPicList.size() == CoreKeys.MAX_SELECT_PIV_NUM) {
                        //最多添加5张照片
                        viewPluImg(position);
                    } else {
                        //添加照片的凭证
                        selectPic(CoreKeys.MAX_SELECT_PIV_NUM - mPicList.size());
                    }
                } else {
                    viewPluImg(position);
                }
            }
        });
    }

    /**
     * 查看大图
     *
     * @param position
     */
    private void viewPluImg(int position) {
        Intent intent = new Intent(FeedbackActivity.this, PlusImageActivity.class);
        intent.putStringArrayListExtra(CoreKeys.IMG_LIST, mPicList);
        intent.putExtra(CoreKeys.POSITION, position);
        startActivityForResult(intent, CoreKeys.REQUEST_CODE_MAIN);
    }

    @Override
    public void myClick(View view, int type) {
        int position = (int) view.getTag();
        if (mPicList.size() > 0) {
            //从数据源移除要删除的图片
            mPicList.remove(position);
            mGridViewAdapter.notifyDataSetChanged();
        }
        if (mPicList.size() == 0) {
            isViewVisible = false;
            isViewVisible();
        }
        LogUtils.e("当前剩余图片===>" + mPicList.size());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    isViewVisible = true;
                    isViewVisible();
                    //图片选择结果的回调
                    Object result1 = (List) data.getSerializableExtra("extra_result_media");

                    List<OrderAddImgBean> list = GsonUtil.GsonToList(GsonUtil.GsonString(result1), OrderAddImgBean.class);
                    for (OrderAddImgBean bean : list) {
                        mPicList.add(bean.getPath());
                    }
                    mGridViewAdapter.notifyDataSetChanged();
                    //例如 LocalMedia里面返回了三种path
                    //1、media.getPath() 为原图的path
                    //2、media.getCutPath() 为裁剪后的path 需要判断media.isCut() 是否为true
                    //3、media.getCompressPath() 为压缩后的path 徐判断media.isCompressed() 是否weitrue
                    //如果裁剪并压缩了 则以取到的压缩路径为准 因为是先裁剪后压缩的
                    break;
                case CoreKeys.TAKEPICTURE:
                    isViewVisible = true;
                    isViewVisible();
                    //获取拍照图片路径
                    String fileSrc = mPictureFile.getAbsolutePath();
                    mPicList.add(fileSrc);
                    mGridViewAdapter.notifyDataSetChanged();
                    LogUtils.e("图片路径===>" + fileSrc);
                    break;
            }
        }
        if (requestCode == CoreKeys.REQUEST_CODE_MAIN && resultCode == CoreKeys.RESULT_CODE_VIEW_IMG) {
            //查看了大图界面删除了图片
            ArrayList<String> toDeletePicList = data.getStringArrayListExtra(CoreKeys.IMG_LIST);
            mPicList.clear();
            mPicList.addAll(toDeletePicList);
            mGridViewAdapter.notifyDataSetChanged();
            if (mPicList.size() == 0) {
                isViewVisible = false;
                isViewVisible();
            }
        }
    }


    @Override
    public void saveUserFeedbackSuccess(BaseBean result) {
        if (isFinishing()) {
            return;
        }
        dismissWaitDialog();
        LogUtils.e("saveUserFeedbackSuccess==>" + GsonUtil.GsonString(result));
        edContent.setText(null);
        mPicList.clear();
        mGridViewAdapter.notifyDataSetChanged();
        isViewVisible = false;  //显示添加按钮
        isViewVisible();  //刷新布局
        Toasts.showShort(result.getMessage());
    }

    @Override
    public void userFeedbackListSuccess(FeedbackListBean result) {

    }

    @Override
    public void queryUserFeedbackSuccess(FeedbackInfoBean result) {

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

}
