package com.wzf.baseclasslibrary;

import android.app.Application;

import com.wzf.baseclasslibrary.utils.NullUtil;

public class SimpleApplication {
    private static volatile Application application;

    private SimpleApplication() {
    }

    public static void initSimpleApplication(Application application) {
        NullUtil.nullAndThrow(application,"初始化base库");
        if (SimpleApplication.application == null) {
            synchronized (SimpleApplication.class) {
                if (SimpleApplication.application == null) {
                    SimpleApplication.application = application;
                }
            }
        }
    }

    public static Application getApplication() {
        return NullUtil.nullAndThrow(application);
    }
}
