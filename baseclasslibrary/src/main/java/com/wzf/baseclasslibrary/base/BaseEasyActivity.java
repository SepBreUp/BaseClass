package com.wzf.baseclasslibrary.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wzf.baseclasslibrary.R;
import com.wzf.baseclasslibrary.animation.BindLayout;
import com.wzf.baseclasslibrary.utils.CommonUtils;


/**
 * @author wzf 2018-6-25
 * 用来使用的activity的基类
 */
public class BaseEasyActivity extends AppCompatActivity {
    //3: >=6.0； 2:魅族； 1：小米；0：小于6.0且非小米魅族 以及小于4.4
    public int mSystemType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        mSystemType = StarteBarUtils.statusBarLightMode(this);
        getBindLayout();
    }

    private void showWaitDialog() {
        if (loading == null) {
            loading = ProgressHUD.show(this, "正在加载，请稍后...", true, true, null);
        } else {
            loading.show();
        }
    }


    public void getBindLayout() {
        BindLayout annotation = getClass().getAnnotation(BindLayout.class);
        if (annotation != null) {
//            if (annotation.isShowTitleLayout()) {
            showTitleLayout(annotation.layoutId(), annotation);
//            } else {
//                super.setContentView(annotation.layoutId());
//                setConfig(annotation);
//            }
        } else {
            throw new RuntimeException("需要使用注解BindLayout 添加layout");
        }
    }

    /**
     * 显示有title的布局
     */
    public void showTitleLayout(@LayoutRes int layoutResID, @NonNull BindLayout annotation) {
        View view = CommonUtils.loadView(this, R.layout.activity_base, null);
        if (layoutResID != -1 && layoutResID != 0) {
            FrameLayout fl_base_content = view.findViewById(R.id.fl_base_content);
            fl_base_content.addView(CommonUtils.loadView(this, layoutResID, fl_base_content));
        }
        super.setContentView(view);
        setConfig(annotation);
    }

    public void setConfig(@NonNull BindLayout annotation) {
        if (annotation.bindToButterKnife()) {
            ButterKnife.bind(this);
        }

        //默认显示状态栏高度，头部默认不显示，
        if (annotation.isShowStarBar()) {
            showStateBar();
        }
        initViewByMySelf();
        initDataByMySelf();
        //设置沉浸式状态栏
        setWindowImmersive();
    }

    /**
     * 設置右侧文字
     *
     * @param str
     */
    public void setRightText(String str) {
        showRightText(true);
        tvTitleRight.setText(str);
    }

    /**
     * 设置右侧文字显示
     *
     * @param show
     */
    public void showRightText(boolean show) {
        CommonUtils.showView(tvTitleRight, show);
    }

    /**
     * 显示隐藏与下边的分割线
     */
    public void showLine(boolean show) {
        if (show && vTitleLine.getVisibility() != View.VISIBLE) {
            vTitleLine.setVisibility(View.VISIBLE);
        } else if (!show && vTitleLine.getVisibility() != View.INVISIBLE) {
            vTitleLine.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        CommonUtils.showView(tvTitleTitle, true);
        tvTitleTitle.setText(title);
    }

    /**
     * 隐藏或者显示返回键右边的按钮
     * 默认不现实
     *
     * @param show
     */
    public void showLeftBackRight(boolean show) {
        CommonUtils.showView(iv_title_left_back_right, show);
    }

    /**
     * 设置左边返回键样式
     *
     * @param res
     */
    public void setLeftImage(@DrawableRes int res) {
        CommonUtils.showView(ibTitleLeftBack, true);
        ibTitleLeftBack.setImageResource(res);
    }

    public void initDataByMySelf() {

    }

    public void initViewByMySelf() {

    }


    @Override
    protected void onDestroy() {
        MyApplication.getInstance().removeActivty(this);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
//        overridePendingTransition(0, 0);
    }

    @Override
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
    }

    @OnClick({R.id.ib_title_left_back, R.id.tv_title_right, R.id.iv_title_right_icon, R.id.iv_title_left_back_right})
    public void onBaseViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_title_left_back:
                leftBack();
                break;
            case R.id.tv_title_right:
                rightText();
                break;
            case R.id.iv_title_right_icon:
                rightIcon();
                break;
            case R.id.iv_title_left_back_right:
                leftBackRight();
                break;
        }
    }

    protected void leftBackRight() {

    }

    /**
     * 是否显示左边返回按钮
     *
     * @param show
     */
    public void showLeftBack(boolean show) {
        CommonUtils.showView(ibTitleLeftBack, show);
    }


    /**
     * 右边图片的点击事件
     */
    public void rightIcon() {

    }

    /**
     * 右边文字的点击事件
     */
    public void rightText() {

    }


    /**
     * 左边图片的点击事件
     */
    public void leftBack() {
        finish();
    }

    public void loading() {
        if (hadDestory) {
            return;
        }
        showWaitDialog();
    }

    public void endLoading() {
        if (hadDestory) {
            return;
        }
        if (loading != null && loading.isShowing()) {
            loading.dismiss();
        }
    }

    /**
     * 是否显示头部
     *
     * @param show
     */
    public void showTitle(boolean show) {
        if (show && titleLayout != null && titleLayout.getVisibility() != View.VISIBLE) {
            titleLayout.setVisibility(View.VISIBLE);
            return;
        }
        if (!show && titleLayout != null && titleLayout.getVisibility() != View.GONE) {
            titleLayout.setVisibility(View.GONE);
        }
        showLeftBack(show);
    }

    /**
     * 返回头部view
     *
     * @return
     */
    public RelativeLayout getTitleLayout() {
        return titleLayout;
    }

    //全透明状态栏
    private void initWindow() {
        //设置状态栏透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//>=5.0
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            getWindow().setStatusBarColor(getResources().getColor(R.color.common_transparent));
        } else {
//            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    //设置4.4以上沉浸式菜单
    public void setWindowImmersive() {
        //这个属性4 .4 算是全透明（有的机子是过渡形式的透明），5.0 就是半透明了 我的模拟器、真机都是半透明，
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            int top = CommonUtils.getStateHeight();
            if (top > 0 && llShowAll != null) {
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) llShowAll.getLayoutParams();
                layoutParams.topMargin = 0 - top;
                llShowAll.requestLayout();
            }
        }
    }

    /**
     * 显示状态栏，隐藏title
     */
    public void showStateBar() {
//        if (vBaseStateColor.getVisibility() != View.VISIBLE) {
//            vBaseStateColor.setVisibility(View.VISIBLE);
//        }
        CommonUtils.showView(vBaseStateColor, true);
        ViewGroup.LayoutParams layoutParams = vBaseStateColor.getLayoutParams();
        layoutParams.height = CommonUtils.getStateHeight();
        vBaseStateColor.requestLayout();
    }

    public void hideStateBar() {
        if (vBaseStateColor.getVisibility() != View.GONE) {
            vBaseStateColor.setVisibility(View.GONE);
        }
    }

    /**
     * 设置默认显示还是隐藏状态栏高度
     *
     * @return true 显示 false 不显示状态栏高度
     */
    public boolean hideOrShow() {
        return true;
    }

    public void setStateBarColor(@ColorRes int color) {
        showStateBar();
        vBaseStateColor.setBackgroundColor(MyApplication.getInstance().getResources().getColor(color));
    }

    /**
     * 设置右上角图片
     *
     * @param res
     */
    public void setIvRight(@DrawableRes int res) {
        CommonUtils.showView(ivTitleRightIcon, true);
        ivTitleRightIcon.setImageResource(res);
    }

    /**
     * 控制右上角图片显示隐藏
     *
     * @param show
     */
    public void showIvRight(boolean show) {
        CommonUtils.showView(ivTitleRightIcon, show);
    }

    /**
     * 獲取右上角圖片
     *
     * @return
     */
    public ImageView getIvRight() {
        return ivTitleRightIcon;
    }

    /**
     * 获取显示view
     *
     * @return
     */
    public FrameLayout getFlBaseContent() {
        return flBaseContent;
    }

    /**
     * 是否启动动画
     *
     * @return
     */
    public boolean showAnimation() {
        return true;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (showAnimation()) {
            overridePendingTransition(R.anim.view_in_in, R.anim.view_in_out);
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (showAnimation()) {
            overridePendingTransition(R.anim.view_in, R.anim.view_out);
        }
    }

    /**
     * 显示联网失败页面
     */
    public void showError() {

    }

    /**
     * 显示未登录状态
     */
    public void showLogin() {

    }
}
