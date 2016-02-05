package com.yuzhi.fine.module.home.presenter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.squareup.okhttp.Request;
import com.yuzhi.fine.base.http.HttpResponseHandler;
import com.yuzhi.fine.data.http.RestApiClient;
import com.yuzhi.fine.module.home.model.SearchParam;
import com.yuzhi.fine.module.home.model.SearchShop;
import com.yuzhi.fine.module.home.view.fragment.ImFragmentView;

import java.io.IOException;
import java.util.List;

/**
 * Created by tiansj on 16/2/4.
 */
public class ImFragmentPresenterImpl implements ImFragmentPresenter {

    private boolean isLoadAll;
    ImFragmentView imFragmentView;

    public ImFragmentPresenterImpl(ImFragmentView imFragmentView) {
        this.imFragmentView = imFragmentView;
    }

    @Override
    public void httpLoadData(SearchParam param) {
        if(isLoadAll) {
            return;
        }
        RestApiClient.getRecommendShops(param, new HttpResponseHandler() {
            @Override
            public void onSuccess(String body) {
                JSONObject object = JSON.parseObject(body);
                List<SearchShop> list = JSONArray.parseArray(object.getString("body"), SearchShop.class);
                isLoadAll = list.size() < RestApiClient.PAGE_SIZE;
                imFragmentView.handleSuccess(list);
            }

            @Override
            public void onFailure(Request request, IOException e) {
                imFragmentView.handleFailure(e);
            }
        });
    }
}
