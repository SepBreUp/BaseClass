package com.wzf.baseclasslibrary.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.wzf.baseclasslibrary.SimpleApplication;

import java.lang.reflect.Method;

public class CommonUtils {
    private static long lastClickTime = 0;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 400) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static View loadView(Context context, int res, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(res, parent, false);
    }

    /**
     * 获取状态栏的高度
     *
     * @return
     */
    public static int getStateHeight() {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight1 = 0;
        //获取status_bar_height资源的ID
        int resourceId = SimpleApplication.getApplication().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = SimpleApplication.getApplication().getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight1;
    }


    //获取底部返回键等按键的高度
    public static int getBackButtonHight() {
        int result = 0;
        if (hasNavBar(SimpleApplication.getApplication())) {
            Resources res = SimpleApplication.getApplication().getResources();
            int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId);
            }
        }
        return result;
    }

    //检查是否存在虚拟按键
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static boolean hasNavBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if (resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
// check override flag
            String sNavBarOverride = getNavBarOverride();
            if ("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if ("0".equals(sNavBarOverride)) {
                hasNav = true;
            }
            return hasNav;
        } else { // fallback
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    //检查虚拟按键是否被重写
    private static String getNavBarOverride() {
        String sNavBarOverride = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Class c = Class.forName("android.os.SystemProperties");
                Method m = c.getDeclaredMethod("get", String.class);
                m.setAccessible(true);
                sNavBarOverride = (String) m.invoke(null, "qemu.hw.mainkeys");
            } catch (Throwable e) {
            }
        }
        return sNavBarOverride;
    }


    static Handler handler = new Handler(Looper.getMainLooper());

    /**
     * 再主线程跑
     *
     * @param runnable
     */
    public static void runInMainThread(Runnable runnable) {
        handler.post(runnable);
    }

}
