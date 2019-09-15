package com.jh.wxqb.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jh.wxqb.R;
import com.jh.wxqb.customview.CustomTitle;
import com.jh.wxqb.utils.Toasts;

import java.util.Map;

/**
 * 项目里面所有Fragment的基类
 */
public abstract class BaseFragment extends Fragment {

    public Map<String, String> map;     //用于子类传参使用
    protected Context mContext;                 //向子类提供Context参数（提高复用效率）
    private boolean isFragmentVisible;      //Fragment状态是否可见（默认不可见，当前Fragment为选中状态时，加载数据，并设置状态为可见状态）
    private boolean isReuseView;          //是否复用View  （默认：复用）
    private boolean isFirstVisible;     //是否第一次加载
    private View mRootView;             //当前Fragment是否已创建（第一次默认为空）
    //自定义标题栏
    private CustomTitle title;
    private static final String TAG = BaseFragment.class.getSimpleName();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    //setUserVisibleHint()在Fragment创建时会先被调用一次，传入isVisibleToUser = false
    //如果当前Fragment可见，那么setUserVisibleHint()会再次被调用一次，传入isVisibleToUser = true
    //如果Fragment从可见->不可见，那么setUserVisibleHint()也会被调用，传入isVisibleToUser = false
    //总结：setUserVisibleHint()除了Fragment的可见状态发生变化时会被回调外，在new Fragment()时也会被回调
    //如果我们需要在 Fragment 可见与不可见时干点事，用这个的话就会有多余的回调了，那么就需要重新封装一个
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (mRootView == null) {
            return;
        }
        if (isFirstVisible && isVisibleToUser) {
            onFragmentFirstVisible();
            isFirstVisible = false;
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onFragmentVisibleChange(false);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //如果setUserVisibleHint()在rootView创建前调用时，那么
        //就等到rootView创建完后才回调onFragmentVisibleChange(true)
        //保证onFragmentVisibleChange()的回调发生在rootView创建完成之后，以便支持ui操作
        if (mRootView == null) {
            mRootView = view;
            if (getUserVisibleHint()) {
                if (isFirstVisible) {
                    onFragmentFirstVisible();
                    isFirstVisible = false;
                }
                onFragmentVisibleChange(true);
                isFragmentVisible = true;
            }
        }
        super.onViewCreated(isReuseView ? mRootView : view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        initVariable();
    }

    private void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
        mRootView = null;
        isReuseView = true;
    }

    /**
     * 设置是否使用 view 的复用，默认开启
     * view 的复用是指，ViewPager 在销毁和重建 Fragment 时会不断调用 onCreateView() -> onDestroyView()
     * 之间的生命函数，这样可能会出现重复创建 view 的情况，导致界面上显示多个相同的 Fragment
     * view 的复用其实就是指保存第一次创建的 view，后面再 onCreateView() 时直接返回第一次创建的 view
     *
     * @param isReuse
     */
    protected void reuseView(boolean isReuse) {
        isReuseView = isReuse;
    }

    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     * <p>
     * 可在该回调方法里进行一些ui显示与隐藏，比如加载框的显示和隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected abstract void onFragmentVisibleChange(boolean isVisible);

    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     * 这样就可以防止每次进入都重复加载数据
     * 该方法会在 onFragmentVisibleChange() 之前调用，所以第一次打开时，可以用一个全局变量表示数据下载状态，
     * 然后在该方法内将状态设置为下载状态，接着去执行下载的任务
     * 最后在 onFragmentVisibleChange() 里根据数据下载状态来控制下载进度ui控件的显示与隐藏
     */
    protected abstract void onFragmentFirstVisible();


    protected boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    /**
     * 没网的情况下，网络请求失败的提示
     */
    public void netError() {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toasts.showShort(R.string.network_connection_timeout);
            }
        });
    }

    /**
     * 给标题栏添加监听，若不设置，默认只有左上角返回键有响应
     */
    public void setTitleListener(int titleId) {

        //初始化标题栏控件
        title = (CustomTitle) mRootView.findViewById(titleId);

        //标题栏左边返回键点击事件
        //如果在子界面不重写本方法 则默认关闭当前界面
        title.setBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toBack();
            }
        });

        //标题栏右边图片点击事情
        title.setMoreImgAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRight();
            }
        });

        //标题栏右边文字点击事件
        title.setMoreTextAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRight();
            }
        });
    }

    /**
     * 点击标题栏右上角
     */
    protected void toRight() {

    }

    /**
     * 点击标题栏左上角
     */
    protected void toBack() {

    }

}
