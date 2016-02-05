package com.yuzhi.fine.module.login.presenter;

import com.yuzhi.fine.R;
import com.yuzhi.fine.base.utils.StringUtils;
import com.yuzhi.fine.module.login.view.LoginView;

/**
 * Created by tiansj on 16/2/3.
 */
public class LoginPresenterImpl implements LoginPresenter {

    LoginView loginView;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
    }

    @Override
    public void login(final String username, final String password) {
        if(StringUtils.isEmpty(username)) {
            loginView.invalidInput(R.string.login_username_empty_error);
            return;
        }
        if(StringUtils.isEmpty(password)) {
            loginView.invalidInput(R.string.login_password_empty_error);
            return;
        }
        // 实际使用使用http请求
//        RestApiClient.loginPassword(username, password, new JsonResponseHandler() {
//            @Override
//            public void onSuccess(JSONObject response) {
//                loginView.loginSuccess();
//                saveUserInfo2DB();
//            }
//
//            @Override
//            public void onFailure(Throwable e) {
//                super.onFailure(e);
//                loginView.loginFailed(e.getMessage());
//            }
//        });

        // Test
        if(username.equals("username") && password.equals("password")) {
            loginView.loginSuccess();
            saveUserInfo2DB();
        } else {
            loginView.loginFailed("用户名或密码错误");
        }
    }

    private void saveUserInfo2DB() {
    }


}
