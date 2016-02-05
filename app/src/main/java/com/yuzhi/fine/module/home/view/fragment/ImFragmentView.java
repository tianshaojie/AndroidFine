package com.yuzhi.fine.module.home.view.fragment;

import com.yuzhi.fine.module.home.model.SearchShop;

import java.util.List;

/**
 * Created by tiansj on 16/2/4.
 */
public interface ImFragmentView {

    void handleSuccess(List<SearchShop> list);
    void handleFailure(Exception e);

}
