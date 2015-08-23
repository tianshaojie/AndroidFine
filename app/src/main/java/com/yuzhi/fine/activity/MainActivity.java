
package com.yuzhi.fine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;

import com.yuzhi.fine.R;
import com.yuzhi.fine.fragment.BufferKnifeFragment;
import com.yuzhi.fine.fragment.MainPagerFragment;
import com.yuzhi.fine.fragment.MemberFragment;
import com.yuzhi.fine.ui.UIHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseFragmentActivity {

    public static final int SCROLL_VIEW_HOME = 0;
    public static final int SCROLL_VIEW_IM = 1;
    public static final int SCROLL_VIEW_INTEREST = 2;
    public static final int SCROLL_VIEW_USER = 3;

    public static int mCurSel = -1;

    private RadioButton fbHome;
    private RadioButton fbIm;
    private RadioButton fbInterest;
    private RadioButton fbUser;

    private Fragment homeFragment = new MainPagerFragment();
    private Fragment imFragment = new BufferKnifeFragment();
    private Fragment interestFragment = new BufferKnifeFragment();
    private Fragment memberFragment = new MemberFragment();
    private List<Fragment> fragmentList;

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();

        mCurSel = -1;
        initFragmentList();
        initFootBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = fragmentList.get(mCurSel);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initFragmentList() {
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(homeFragment);
        fragmentList.add(imFragment);
        fragmentList.add(interestFragment);
        fragmentList.add(memberFragment);
    }

    private void initFootBar() {
        fbHome = (RadioButton) findViewById(R.id.foot_bar_home);
        fbIm = (RadioButton) findViewById(R.id.foot_bar_im);
        fbInterest = (RadioButton) findViewById(R.id.foot_bar_interest);
        fbUser = (RadioButton) findViewById(R.id.main_footbar_user);

        fbHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurPoint(SCROLL_VIEW_HOME);
            }
        });

        fbIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurPoint(SCROLL_VIEW_IM);
            }
        });

        fbInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurPoint(SCROLL_VIEW_INTEREST);
            }
        });

        fbUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.showLogin(MainActivity.this);
                setCurPoint(SCROLL_VIEW_USER);
            }
        });

        fbHome.performClick();
    }

    public void setCurPoint(int index) {
        if (mCurSel == index)
            return;
        mCurSel = index;
        addFragmentToStack();
        setFootBtnChecked();
    }

    private void setFootBtnChecked() {
        fbHome.setChecked(mCurSel == SCROLL_VIEW_HOME);
        fbIm.setChecked(mCurSel == SCROLL_VIEW_IM);
        fbInterest.setChecked(mCurSel == SCROLL_VIEW_INTEREST);
        fbUser.setChecked(mCurSel == SCROLL_VIEW_USER);
    }

    private void addFragmentToStack() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (mCurSel == SCROLL_VIEW_HOME) {
            if (!homeFragment.isAdded()) {
                fragmentTransaction.add(R.id.fragment_container, homeFragment);
            }
        } else if (mCurSel == SCROLL_VIEW_IM) {
            if (!imFragment.isAdded()) {
                fragmentTransaction.add(R.id.fragment_container, imFragment);
            }
        } else if (mCurSel == SCROLL_VIEW_INTEREST) {
            if (!interestFragment.isAdded()) {
                fragmentTransaction.add(R.id.fragment_container, interestFragment);
            }
        } else if (mCurSel == SCROLL_VIEW_USER) {
            if (!memberFragment.isAdded()) {
                fragmentTransaction.add(R.id.fragment_container, memberFragment);
            }
        }
        toggleFragment(fragmentTransaction);
        fragmentTransaction.commitAllowingStateLoss();
    }

    private void toggleFragment(FragmentTransaction fragmentTransaction) {
        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment f = fragmentList.get(i);
            if (i == mCurSel && f.isAdded()) {
                fragmentTransaction.show(f);
            } else if (f != null && f.isAdded() && f.isVisible()) {
                fragmentTransaction.hide(f);
            }
        }
    }

    public void switchFragment(int index) {
        if (index == SCROLL_VIEW_HOME) {
            fbHome.performClick();
            fbHome.post(new Runnable() {
                @Override
                public void run() {
                    fbHome.performClick();
                }
            });
        } else if (index == SCROLL_VIEW_IM) {
            fbIm.post(
                    new Runnable() {
                        @Override
                        public void run() {
                            fbIm.performClick();
                        }
                    });
        } else if (index == SCROLL_VIEW_INTEREST) {
            fbInterest.post(new Runnable() {
                @Override
                public void run() {
                    fbInterest.performClick();
                }
            });
        } else if (index == SCROLL_VIEW_USER) {
            fbUser.post(new Runnable() {
                @Override
                public void run() {
                    fbUser.performClick();
                }
            });
        }
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
