package com.yuzhi.fine.module.home.view.fragment;

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
import com.squareup.okhttp.Request;
import com.squareup.picasso.Picasso;
import com.yuzhi.fine.R;
import com.yuzhi.fine.base.http.HttpResponseHandler;
import com.yuzhi.fine.base.ui.listview.LoadMoreListView;
import com.yuzhi.fine.base.ui.listview.ScrollListenerWithPicasso;
import com.yuzhi.fine.base.ui.quickadapter.BaseAdapterHelper;
import com.yuzhi.fine.base.ui.quickadapter.QuickAdapter;
import com.yuzhi.fine.base.utils.DeviceUtil;
import com.yuzhi.fine.module.home.model.SearchParam;
import com.yuzhi.fine.module.home.model.SearchShop;
import com.yuzhi.fine.data.http.RestApiClient;
import com.yuzhi.fine.module.UIHelper;
import com.yuzhi.fine.module.home.presenter.ImFragmentPresenter;
import com.yuzhi.fine.module.home.presenter.ImFragmentPresenterImpl;
import com.yuzhi.fine.module.home.view.activity.MainActivity;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by tiansj on 15/9/4.
 * ButterKnife + ultra-ptr + LoadMoreListView
 */
public class InterestFragment extends Fragment implements ImFragmentView {
    private MainActivity context;

    private SearchParam param;
    ImFragmentPresenter imFragmentPresenter;

    @Bind(R.id.rotate_header_list_view_frame) PtrClassicFrameLayout mPtrFrame;
    @Bind(R.id.listView) LoadMoreListView listView;
    QuickAdapter<SearchShop> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo_ptr, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = (MainActivity) getActivity();
        initData();
        initView();
        imFragmentPresenter = new ImFragmentPresenterImpl(this);
        imFragmentPresenter.httpLoadData(param);
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
        listView.setDrawingCacheEnabled(true);
        listView.setAdapter(adapter);
        listView.setOnScrollListener(new ScrollListenerWithPicasso(context));
        // 加载更多
        listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
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

        // header custom begin
        final StoreHouseHeader header = new StoreHouseHeader(context);
        header.setPadding(0, DeviceUtil.dp2px(context, 15), 0, 0);
        header.initWithString("Fine");
        header.setTextColor(getResources().getColor(R.color.gray));
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        // header custom end

        // 下拉刷新
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initData();
                imFragmentPresenter.httpLoadData(param);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }

    private void initData() {
        param = new SearchParam();
        param.setPno(1);
    }

    @Override
    public void handleSuccess(List<SearchShop> list) {
        listView.updateLoadMoreViewText(list);
        if (param.getPno() == 1) {
            adapter.clear();
        }
        adapter.addAll(list);
        param.setPno(param.getPno() + 1);
    }

    @Override
    public void handleFailure(Exception e) {
        mPtrFrame.refreshComplete();
        listView.setLoadMoreViewTextError();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Picasso.with(context).cancelTag(context);
    }
}
