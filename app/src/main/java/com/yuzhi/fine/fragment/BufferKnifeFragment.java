package com.yuzhi.fine.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.squareup.picasso.Picasso;
import com.yuzhi.fine.R;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.model.SearchParam;
import com.yuzhi.fine.model.SearchShop;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.ui.pulltorefresh.PullToRefreshBase;
import com.yuzhi.fine.ui.pulltorefresh.PullToRefreshListView;
import com.yuzhi.fine.ui.quickadapter.BaseAdapterHelper;
import com.yuzhi.fine.ui.quickadapter.QuickAdapter;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;


public class BufferKnifeFragment extends Fragment {

    private Activity context;

    private SearchParam param;
    private int pno = 1;
    private boolean isLoadAll;

    @Bind(R.id.listView)
    PullToRefreshListView listView;
    QuickAdapter<SearchShop> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recommend_shop_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = getActivity();
        initData();
        initView();
        loadData();
    }

    void initView() {
        adapter = new QuickAdapter<SearchShop>(context, R.layout.recommend_shop_list_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, SearchShop shop) {
                helper.setText(R.id.name, shop.getName())
                        .setText(R.id.address, shop.getAddr())
                        .setImageUrl(R.id.logo, shop.getLogo()); // 自动异步加载图片
            }
        };

        listView.withLoadMoreView();
        listView.setAdapter(adapter);
        // 下拉刷新
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                initData();
                loadData();
            }
        });
        // 加载更多
        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                loadData();
            }
        });
        // 点击事件
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UIHelper.showHouseDetailActivity(context);
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    Picasso.with(context).pauseTag(context);
                } else {
                    Picasso.with(context).resumeTag(context);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    private void initData() {
        param = new SearchParam();
        pno = 1;
        isLoadAll = false;
    }

    private void loadData() {
        if (isLoadAll) {
            return;
        }
        param.setPno(pno);
        listView.setLoadMoreViewTextLoading();
        HttpClient.getRecommendShops(param, new HttpResponseHandler() {

            @Override
            public void onSuccess(String body) {
                listView.onRefreshComplete();
                JSONObject object = JSON.parseObject(body);
                List<SearchShop> list = JSONArray.parseArray(object.getString("body"), SearchShop.class);
                listView.updateLoadMoreViewText(list);
                isLoadAll = list.size() < HttpClient.PAGE_SIZE;
                if(pno == 1) {
                    adapter.clear();
                }
                adapter.addAll(list);
                pno++;
            }

            @Override
            public void onFailure(Request request, IOException e) {
                listView.onRefreshComplete();
                listView.setLoadMoreViewTextError();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Picasso.with(context).resumeTag(context);
    }

    @Override
    public void onPause() {
        super.onPause();
        Picasso.with(context).pauseTag(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Picasso.with(context).cancelTag(context);
    }
}