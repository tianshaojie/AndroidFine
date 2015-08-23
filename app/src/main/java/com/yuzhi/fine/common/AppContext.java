package com.yuzhi.fine.common;

import android.app.Application;

public class AppContext extends Application {

    private static AppContext app;
    public static String currentUserNick = ""; // 当前用户nickname,为了苹果推送不是userid而是昵称

    public AppContext() {
        app = this;
    }

    public static synchronized AppContext getInstance() {
        if (app == null) {
            app = new AppContext();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerUncaughtExceptionHandler();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
//        SDKInitializer.initialize(this);
    }

    // 注册App异常崩溃处理器
    private void registerUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppException.getAppExceptionHandler());
    }

}