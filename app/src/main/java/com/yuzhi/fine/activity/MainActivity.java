
package com.yuzhi.fine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.yuzhi.fine.R;
import com.yuzhi.fine.fragment.BufferKnifeFragment;
import com.yuzhi.fine.fragment.HomeFragment;
import com.yuzhi.fine.fragment.MainPagerFragment;
import com.yuzhi.fine.fragment.MemberFragment;
import com.yuzhi.fine.ui.UIHelper;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends BaseFragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String CURR_INDEX_KEY = "currIndexKey";
    private static final String FRAGMENT_TAG_LIST = "fragmentTagList";
    private static int currIndex = 0;

    private RadioGroup group;

    private MainPagerFragment homeFragment;
    private BufferKnifeFragment imFragment;
    private BufferKnifeFragment interestFragment;
    private MemberFragment memberFragment;
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> fragmentTagList;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            initData();
            initView();
        } else {
            initFromSavedInstantsState(savedInstanceState);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = fragmentList.get(currIndex);
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURR_INDEX_KEY, currIndex);
        outState.putStringArrayList(FRAGMENT_TAG_LIST, fragmentTagList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        initFromSavedInstantsState(savedInstanceState);
    }

    private void initData() {
        fragmentTagList = new ArrayList<>(Arrays.asList("HomeFragment", "ImFragment", "InterestFragment", "MemberFragment"));
        fragmentList = new ArrayList<>(Arrays.asList(homeFragment, imFragment, interestFragment, memberFragment));
    }

    private void initFromSavedInstantsState(Bundle savedInstanceState) {
        currIndex = savedInstanceState.getInt(CURR_INDEX_KEY);
        fragmentTagList = savedInstanceState.getStringArrayList(FRAGMENT_TAG_LIST);
        for(int i = 0; i < fragmentTagList.size(); i++) {
            Fragment fragment = fragmentManager.findFragmentByTag(fragmentTagList.get(i));
            if(fragment != null) {
                fragmentList.set(i, fragment);
            }
        }
        addFragmentToStack();
    }

    private void initView() {
        group = (RadioGroup) findViewById(R.id.group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home:
                        currIndex = 0;
                        break;
                    case R.id.foot_bar_im:
                        currIndex = 1;
                        break;
                    case R.id.foot_bar_interest:
                        currIndex = 2;
                        break;
                    case R.id.main_footbar_user:
                        currIndex = 3;
                        break;
                }
                addFragmentToStack();
            }
        });
        addFragmentToStack();
    }

    private void addFragmentToStack() {
        if (currIndex == 3) {
            UIHelper.showLogin(MainActivity.this);
        }

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentList.get(currIndex);
        if(fragment == null) {
            fragment = instantFragment(currIndex);
            fragmentList.set(currIndex, fragment);
        }
        for (int i = 0; i < fragmentTagList.size(); i++) {
            Fragment f = fragmentManager.findFragmentByTag(fragmentTagList.get(i));
            if(f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentTagList.get(currIndex));
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();
    }

    // 使用时加载
    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0: return homeFragment = new MainPagerFragment();
            case 1: return imFragment = new BufferKnifeFragment();
            case 2: return interestFragment = new BufferKnifeFragment();
            case 3: return memberFragment = new MemberFragment();
            default: return null;
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
