package com.wzf.baseclasslibrary;

import android.app.Activity;
import android.app.Application;

import com.wzf.baseclasslibrary.utils.NullUtil;

/**
 * 使用此类来初始化库，只需要初始化一次
 */
public class InitBaseClass {
    public static void initBaseClass(Application application) {
        SimpleApplication.initSimpleApplication(application);
    }

    public static void initBaseClass(Activity activity) {
        if (NullUtil.isNull(activity)) {
            SimpleApplication.initSimpleApplication(activity.getApplication());
        }
    }
}
