package com.yuzhi.fine.ui.loopviewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;

/**
 * Created by tiansj on 15/8/7.
 * 继承LoopViewPager，增加handle触发自动循环，并对事件进行处理，按下停止播放，抬起开始播放
 */
public class AutoLoopViewPager extends LoopViewPager {

    public static final int DEFAULT_INTERVAL = 4000;
    public static final int SCROLL_WHAT = 0;
    private long interval = DEFAULT_INTERVAL;
    private Handler handler;

    public AutoLoopViewPager(Context context) {
        super(context);
        init();
    }

    public AutoLoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        handler = new MyHandler(this);
        startAutoScroll();
    }

    private static class MyHandler extends Handler {

        private final WeakReference<AutoLoopViewPager> autoScrollViewPager;

        public MyHandler(AutoLoopViewPager autoScrollViewPager) {
            this.autoScrollViewPager = new WeakReference<AutoLoopViewPager>(autoScrollViewPager);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case SCROLL_WHAT:
                    AutoLoopViewPager pager = this.autoScrollViewPager.get();
                    if (pager != null) {
                        pager.scrollOnce();
                        pager.sendScrollMessage(pager.interval);
                    }
                default:
                    break;
            }
        }
    }

    /**
     * <ul>
     * if stopScrollWhenTouch is true
     * <li>if event is down, stop auto scroll.</li>
     * <li>if event is up, start auto scroll again.</li>
     * </ul>
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = MotionEventCompat.getActionMasked(ev);
        if (action == MotionEvent.ACTION_DOWN) {
            stopAutoScroll();
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            startAutoScroll();
        }
        getParent().requestDisallowInterceptTouchEvent(true);

        return super.dispatchTouchEvent(ev);
    }

    /**
     * scroll only once
     */
    public void scrollOnce() {
        PagerAdapter adapter = getAdapter();
        int currentItem = getCurrentItem();
        int totalCount;
        if (adapter == null || (totalCount = adapter.getCount()) <= 1) {
            return;
        }

        int nextItem = ++currentItem;
        if (nextItem < 0) {
            setCurrentItem(totalCount - 1);
        } else if (nextItem == totalCount) {
            setCurrentItem(0);
        } else {
            setCurrentItem(nextItem);
        }
    }

    /**
     * start auto scroll, first scroll delay time is 4s
     */
    public void startAutoScroll() {
        sendScrollMessage(interval);
    }

    /**
     * start auto scroll
     *
     * @param delayTimeInMills first scroll delay time
     */
    public void startAutoScroll(int delayTimeInMills) {
        sendScrollMessage(delayTimeInMills);
    }

    /**
     * stop auto scroll
     */
    public void stopAutoScroll() {
        handler.removeMessages(SCROLL_WHAT);
    }

    private void sendScrollMessage(long delayTimeInMills) {
        /** remove messages before, keeps one message is running at most **/
        handler.removeMessages(SCROLL_WHAT);
        handler.sendEmptyMessageDelayed(SCROLL_WHAT, delayTimeInMills);
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
