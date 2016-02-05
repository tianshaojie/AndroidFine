package com.yuzhi.fine.module.login.view;

/**
 * Created by tiansj on 16/2/3.
 */
public interface LoginView {

    void invalidInput(int resId);
    void loginSuccess();
    void loginFailed(String msg);

}
