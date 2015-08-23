package com.yuzhi.fine.activity;

import android.os.Bundle;
import android.view.View;

import com.yuzhi.fine.R;
import com.yuzhi.fine.ui.swipebacklayout.SwipeBackActivity;


/**
 * Created by tiansj on 15/7/31.
 */
public class LoginActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
