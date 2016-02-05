package com.yuzhi.fine.module.login.presenter;

import com.yuzhi.fine.R;
import com.yuzhi.fine.module.login.view.LoginView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Created by tiansj on 16/2/4.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    LoginView loginView;
    LoginPresenter loginPresenter;

    @Before
    public void setUp() throws Exception {
        loginPresenter = new LoginPresenterImpl(loginView);
    }

    @Test
    public void testLoginUsernameEmpty() throws Exception {
        String username = "";
        String password = "password";
        loginPresenter.login(username, password);

        Mockito.verify(loginView).invalidInput(R.string.login_username_empty_error);
    }

    @Test
    public void testLoginPasswordEmpty() throws Exception {
        String username = "username";
        String password = "";
        loginPresenter.login(username, password);

        Mockito.verify(loginView).invalidInput(R.string.login_password_empty_error);
    }
}