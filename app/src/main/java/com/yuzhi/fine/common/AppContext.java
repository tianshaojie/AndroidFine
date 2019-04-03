package com.yuzhi.fine.common;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class AppContext extends Application {

    private static AppContext app;

    public AppContext() {
        app = this;
    }

    //这里为什么要用单例模式？，一个应用只存在一个Application，所以我觉得这里可以不这样写。
    public static synchronized AppContext getInstance() {
        if (app == null) {
            app = new AppContext();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LeakCanary.install(this);
        registerUncaughtExceptionHandler();
    }

    // 注册App异常崩溃处理器
    private void registerUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
    }

}
