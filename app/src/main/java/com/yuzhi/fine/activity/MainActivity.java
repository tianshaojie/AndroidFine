
package com.yuzhi.fine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.yuzhi.fine.R;
import com.yuzhi.fine.fragment.BufferKnifeFragment;
import com.yuzhi.fine.fragment.MainPagerFragment;
import com.yuzhi.fine.fragment.MemberFragment;
import com.yuzhi.fine.ui.UIHelper;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseFragmentActivity {

    private RadioGroup group;

    private Fragment homeFragment = new MainPagerFragment();
    private Fragment imFragment = new BufferKnifeFragment();
    private Fragment interestFragment = new BufferKnifeFragment();
    private Fragment memberFragment = new MemberFragment();
    private List<Fragment> fragmentList = Arrays.asList(homeFragment, imFragment, interestFragment, memberFragment);

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initFootBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int checkedId = group.getCheckedRadioButtonId();
        int cur = 0;
        switch (checkedId) {
            case R.id.foot_bar_home: cur = 0; break;
            case R.id.foot_bar_im: cur = 1; break;
            case R.id.foot_bar_interest: cur = 2; break;
            case R.id.main_footbar_user: cur = 3; break;
        }
        Fragment fragment = fragmentList.get(cur);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initFootBar() {
        group = (RadioGroup) findViewById(R.id.group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home: addFragmentToStack(0);
                        break;
                    case R.id.foot_bar_im:
                        addFragmentToStack(1);
                        break;
                    case R.id.foot_bar_interest:
                        addFragmentToStack(2);
                        break;
                    case R.id.main_footbar_user:
                        addFragmentToStack(3);
                        UIHelper.showLogin(MainActivity.this);
                        break;
                }
            }
        });
        addFragmentToStack(0);
    }

    private void addFragmentToStack(int cur) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentList.get(cur);
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fragment_container, fragment);
        }
        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment f = fragmentList.get(i);
            if (i == cur && f.isAdded()) {
                fragmentTransaction.show(f);
            } else if (f != null && f.isAdded() && f.isVisible()) {
                fragmentTransaction.hide(f);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
