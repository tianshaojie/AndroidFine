package com.yuzhi.fine.module.home.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;
import com.yuzhi.fine.R;
import com.yuzhi.fine.base.ui.listview.ScrollListenerWithPicasso;
import com.yuzhi.fine.base.ui.pulltorefresh.PullToRefreshBase;
import com.yuzhi.fine.base.ui.pulltorefresh.PullToRefreshListView;
import com.yuzhi.fine.base.ui.quickadapter.BaseAdapterHelper;
import com.yuzhi.fine.base.ui.quickadapter.QuickAdapter;
import com.yuzhi.fine.data.http.RestApiClient;
import com.yuzhi.fine.module.UIHelper;
import com.yuzhi.fine.module.home.model.SearchParam;
import com.yuzhi.fine.module.home.model.SearchShop;
import com.yuzhi.fine.module.home.presenter.ImFragmentPresenter;
import com.yuzhi.fine.module.home.presenter.ImFragmentPresenterImpl;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by tiansj on 15/9/4.
 * ButterKnife + PullToRefreshListView
 */
public class ImFragment extends Fragment implements ImFragmentView {

    private Activity context;
    private SearchParam param;
    private ImFragmentPresenter imFragmentPresenter;

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
        imFragmentPresenter = new ImFragmentPresenterImpl(this);
        imFragmentPresenter.httpLoadData(param);
    }

    private void initData() {
        param = new SearchParam();
        param.setPno(1);
    }

    private void initView() {
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
        listView.setOnScrollListener(new ScrollListenerWithPicasso(context));
        // 下拉刷新
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                initData();
                imFragmentPresenter.httpLoadData(param);
            }
        });
        // 加载更多
        listView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                imFragmentPresenter.httpLoadData(param);
            }
        });
        // 点击事件
        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UIHelper.showHouseDetailActivity(context);
            }
        });
    }

    @Override
    public void handleSuccess(List<SearchShop> list) {
        listView.onRefreshComplete();
        listView.updateLoadMoreViewText(list);
        if (param.getPno() == 1) {
            adapter.clear();
        }
        adapter.addAll(list);
        param.setPno(param.getPno() + 1);
    }

    @Override
    public void handleFailure(Exception e) {
        listView.onRefreshComplete();
        listView.setLoadMoreViewTextError();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Picasso.with(context).cancelTag(context);
    }
}