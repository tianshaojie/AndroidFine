package com.yuzhi.fine.base.ui.listview;

import android.content.Context;
import android.widget.AbsListView;

import com.squareup.picasso.Picasso;

/**
 * Created by tiansj on 15/9/30.
 */
public class ScrollListenerWithPicasso implements AbsListView.OnScrollListener {

    private final Context context;

    public ScrollListenerWithPicasso(Context context) {
        this.context = context;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_TOUCH_SCROLL) {
            Picasso.with(context).resumeTag(context);
        } else {
            Picasso.with(context).pauseTag(context);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }
}