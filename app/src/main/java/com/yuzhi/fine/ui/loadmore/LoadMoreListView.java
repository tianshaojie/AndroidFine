package com.yuzhi.fine.ui.loadmore;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.http.HttpClient;

import java.util.List;

/**
 * Created by tiansj on 15/9/4.
 */
public class LoadMoreListView extends ListView implements OnScrollListener {

    private RelativeLayout mFooterView;
    private ProgressBar mProgressBar;
    private TextView mTextView;

    private boolean mLastItemVisible;
    private boolean mIsLoadingMore = false;
    private OnScrollListener mOnScrollListener;
    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreListView(Context context) {
        super(context);
        init();
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mFooterView = (RelativeLayout) View.inflate(getContext(), R.layout.layout_load_more, null);
        mTextView = (TextView) mFooterView.findViewById(R.id.text);
        mProgressBar = (ProgressBar) mFooterView.findViewById(R.id.progress);

        removeFooterView(mFooterView);
        setFooterDividersEnabled(false);
        addFooterView(mFooterView);
        super.setOnScrollListener(this);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        /**
         * Set whether the Last Item is Visible. lastVisibleItemIndex is a
         * zero-based index, so we minus one totalItemCount to check
         */
        if (null != mOnLoadMoreListener) {
            mLastItemVisible = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount - 1);
        }

        // Finally call OnScrollListener if we have one
        if (null != mOnScrollListener) {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int state) {
        /**
         * Check that the scrolling has stopped, and that the last item is
         * visible.
         */
        if (!mIsLoadingMore && state == OnScrollListener.SCROLL_STATE_IDLE && mLastItemVisible) {
            mIsLoadingMore = true;
            onLoadMore();
        }

        if (null != mOnScrollListener) {
            mOnScrollListener.onScrollStateChanged(view, state);
        }
    }

    public final void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mOnLoadMoreListener = listener;
    }

    public final void setOnScrollListener(OnScrollListener listener) {
        mOnScrollListener = listener;
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void onLoadMore() {
        if(null != mOnLoadMoreListener ) {
            mOnLoadMoreListener.onLoadMore();
        }
    }

    public void onLoadMoreComplete() {
        mIsLoadingMore = false;
    }

    public void updateLoadMoreViewText(List data) {
        if(getAdapter().getCount() == 0 && data.isEmpty()) {
            setLoadMoreViewTextNoData();
        } else if(data.size() < HttpClient.PAGE_SIZE) {
            setLoadMoreViewTextNoMoreData();
        }
        mIsLoadingMore = false;
    }

    /*public void setLoadMoreViewTextLoading() {
        mTextView.setText(R.string.pull_to_refresh_refreshing_label);
        mProgressBar.setVisibility(VISIBLE);
    }*/

    public void setLoadMoreViewTextError() {
        mTextView.setText(R.string.pull_to_refresh_net_error_label);
        mProgressBar.setVisibility(GONE);
        mIsLoadingMore = false;
    }

    public void setLoadMoreViewTextNoData() {
        mTextView.setText(R.string.pull_to_refresh_no_data_label);
        mProgressBar.setVisibility(GONE);
        mIsLoadingMore = false;
    }

    public void setLoadMoreViewTextNoMoreData() {
        mTextView.setText(R.string.pull_to_refresh_no_more_data_label);
        mProgressBar.setVisibility(GONE);
        mIsLoadingMore = false;
    }

    public void setLoadMoreViewText(String text) {
        mTextView.setText(text);
        mProgressBar.setVisibility(VISIBLE);
    }

    public void setLoadMoreViewText(int resId) {
        mTextView.setText(resId);
        mProgressBar.setVisibility(VISIBLE);
    }
}
