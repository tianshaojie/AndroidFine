package com.yuzhi.fine.fragment;

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
import com.yuzhi.fine.activity.MainActivity;
import com.yuzhi.fine.http.HttpClient;
import com.yuzhi.fine.http.HttpResponseHandler;
import com.yuzhi.fine.model.SearchParam;
import com.yuzhi.fine.model.SearchShop;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.ui.loadmore.LoadMoreListView;
import com.yuzhi.fine.ui.quickadapter.BaseAdapterHelper;
import com.yuzhi.fine.ui.quickadapter.QuickAdapter;
import com.yuzhi.fine.utils.DeviceUtil;

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
 */
public class DemoPtrFragment extends Fragment {
    private MainActivity context;

    private SearchParam param;
    private int pno = 1;
    private boolean isLoadAll;

    @Bind(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout mPtrFrame;
    @Bind(R.id.listView)
    LoadMoreListView listView;
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
        listView.setDrawingCacheEnabled(true);
        listView.setAdapter(adapter);

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
                loadData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });

        // 加载更多
        listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
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
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // getLastVisibleItemBitmap(firstVisibleItem+visibleItemCount);
//                takeScreenShot(context);
            }
        });

    }

//    public void takeScreenShot(Activity activity) {
//        // View是你需要截图的View
//        View view = activity.getWindow().getDecorView();
//        view.setDrawingCacheEnabled(true);
//        view.buildDrawingCache();
//        Bitmap b1 = view.getDrawingCache();
//
//        // 获取状态栏高度
//        Rect frame = new Rect();
//        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
//        int statusBarHeight = frame.top;
//        System.out.println(statusBarHeight);
//
//        // 获取屏幕长和高
//        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
//        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
//        // 去掉标题栏
////         Bitmap b = Bitmap.createBitmap(b1, 0, height-120, width, 120);
//        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height
//                - statusBarHeight);
//        view.destroyDrawingCache();
//        context.blurFooterBar(b);
////        return b;
//    }
//
//    private void getLastVisibleItemBitmap(int pos) {
//        View childView = adapter.getView(pos, null, listView);
//        if(childView == null) {
//            return;
//        }
//        childView.measure(View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//
//        childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
//        childView.setDrawingCacheEnabled(true);
//        childView.buildDrawingCache();
////        return childView.getDrawingCache();
//        context.blurFooterBar(childView.getDrawingCache());
//    }
//
//    public Bitmap getWholeListViewItemsToBitmap() {
//        int itemscount       = adapter.getCount();
//        int allitemsheight   = 0;
//        List<Bitmap> bmps    = new ArrayList<Bitmap>();
//
//        for (int i = 0; i < itemscount; i++) {
//
//            View childView      = adapter.getView(i, null, listView);
//            childView.measure(View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.EXACTLY),
//                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//
//            childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
//            childView.setDrawingCacheEnabled(true);
//            childView.buildDrawingCache();
//            bmps.add(childView.getDrawingCache());
//            allitemsheight+=childView.getMeasuredHeight();
//        }
//
//        Bitmap bigbitmap    = Bitmap.createBitmap(listView.getMeasuredWidth(), allitemsheight, Bitmap.Config.ARGB_8888);
//        Canvas bigcanvas    = new Canvas(bigbitmap);
//
//        Paint paint = new Paint();
//        int iHeight = 0;
//
//        for (int i = 0; i < bmps.size(); i++) {
//            Bitmap bmp = bmps.get(i);
//            bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
//            iHeight+=bmp.getHeight();
//
//            bmp.recycle();
//            bmp=null;
//        }
//
//
//        return bigbitmap;
//    }
//
//
//    private void applyBlur() {
//        listView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                listView.getViewTreeObserver().removeOnPreDrawListener(this);
//                listView.buildDrawingCache();
//
//                Bitmap bmp = listView.getDrawingCache();
//                context.blurFooterBar(bmp);
//                return true;
//            }
//        });
//    }

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
        HttpClient.getRecommendShops(param, new HttpResponseHandler() {
            @Override
            public void onSuccess(String body) {
                mPtrFrame.refreshComplete();
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
                mPtrFrame.refreshComplete();
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
