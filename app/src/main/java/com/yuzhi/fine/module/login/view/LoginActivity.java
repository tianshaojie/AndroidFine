package com.yuzhi.fine.module.login.view;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yuzhi.fine.R;
import com.yuzhi.fine.base.ui.progress.ProgressDialog;
import com.yuzhi.fine.base.ui.swipebacklayout.SwipeBackActivity;
import com.yuzhi.fine.module.UIHelper;
import com.yuzhi.fine.module.login.presenter.LoginPresenter;
import com.yuzhi.fine.module.login.presenter.LoginPresenterImpl;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by tiansj on 15/7/31.
 */
public class LoginActivity extends SwipeBackActivity implements LoginView {

    @Bind(R.id.textUsername) EditText mTextUsername;
    @Bind(R.id.textPassword) EditText mTextPassword;

    private ProgressDialog progressDialog;
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        loginPresenter = new LoginPresenterImpl(this);
    }

    @OnClick(R.id.btnClose)
    public void close() {
        finish();
    }

    @OnClick(R.id.btnSure)
    public void submit() {
        progressDialog.showWithStatus("登录中...");
        String username = mTextUsername.getText().toString();
        String password = mTextPassword.getText().toString();
        loginPresenter.login(username, password);
    }

    @Override
    public void invalidInput(int resId) {
        progressDialog.dismiss();
        UIHelper.ToastMessage(this, resId);
    }

    @Override
    public void loginSuccess() {
        progressDialog.dismiss();
        // 跳转到指定Activity
        UIHelper.showHome(this);
    }

    @Override
    public void loginFailed(String msg) {
        progressDialog.dismiss();
        // 提示错误信息
        UIHelper.ToastMessage(this, msg);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
