package com.wzf.baseclasslibrary.animation;

import android.support.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bind content view for activity and android
 * 用来设置当前activity的样式
 *
 * @author wzf
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BindLayout {
    /**
     * Layout resource id
     */
    @LayoutRes int layoutId();

    /**
     * 是否适用butterknife
     */
    boolean bindToButterKnife() default true;

    /**
     * 是否显示状态栏
     * 此时显示状态栏，不显示标题栏
     */
    boolean isShowStarBar() default true;


    /**
     * 暂时没有使用，保留字段
     * 不使用状态栏和标题栏
     * 此时不套用页面 true 显示有title的布局
     * !!为false不能使用butterknife需要自己在外部添加
     */
    boolean isShowTitleLayout() default true;

    /**
     * 暂时没有使用，保留字段，是否显示标题栏
     * 默认不显示
     *
     * @return
     */
    boolean isShowTitle() default false;

    /**
     * 暂时没有使用，保留字段，状态栏颜色，默认色自己设置
     *
     * @return
     */
    int starBarColor() default 0x00000000;

}