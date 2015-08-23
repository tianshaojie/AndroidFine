package com.yuzhi.fine.ui;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.http.HttpClient;

public class ProgressBarHelper {
    public interface ProgressBarClickListener {
        void clickRefresh();
    }

    public View loading;
    TextView tv;
    ProgressBar progressBar;
    ProgressBarClickListener pcl;
    public boolean isLoading = false;
    public Activity context;

    public void setProgressBarClickListener(ProgressBarClickListener pcl) {
        this.pcl = pcl;
    }

    public ProgressBarHelper(Activity context, View inView) {
        this.context = context;
        if (inView != null) {
            loading = inView;
        } else {
            if (context == null) return;
            loading = context.findViewById(R.id.ll_data_loading);
        }
        if (loading == null) return;
        tv = (TextView) loading.findViewById(R.id.loading_tip_txt);
        progressBar = (ProgressBar) loading.findViewById(R.id.loading_progress_bar);
    }

    public ProgressBarHelper(Activity context, View inView, String str) {
        this.context = context;
        if (inView != null) {
            loading = inView;
        } else {
            if (context == null) return;
            loading = context.findViewById(R.id.ll_data_loading);
        }
        if (loading == null) return;
        tv = (TextView) loading.findViewById(R.id.loading_tip_txt);
        // image = (ImageView) loading.findViewById(R.id.loading_image);
        progressBar = (ProgressBar) loading.findViewById(R.id.loading_progress_bar);

        // if(str.equals("GONE")){
        // image.setVisibility(View.GONE);
        // }

    }

    public void showNetError() {
        context.runOnUiThread(new Runnable() {
            public void run() {
                isLoading = false;
                if (tv != null) {
                    tv.setText(R.string.progress_load_error);
                }
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                loading.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pcl != null && !progressBar.isShown() && progressBar.getVisibility() == View.GONE) {
                            if (HttpClient.isNetworkAvailable()) {
                                progressBar.setVisibility(View.VISIBLE);
                                pcl.clickRefresh();
                            }
                        }
                    }
                });
            }
        });
    }

    //暂无数据
    public void showNoData() {
        context.runOnUiThread(new Runnable() {
            public void run() {
                isLoading = false;
                if (tv != null) {
                    tv.setText(R.string.progress_load_no_data);
                }
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                loading.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (pcl != null && !progressBar.isShown() && progressBar.getVisibility() == View.GONE) {
                            if (HttpClient.isNetworkAvailable()) {
                                progressBar.setVisibility(View.VISIBLE);
                                pcl.clickRefresh();
                            }
                        }
                    }
                });
            }
        });
    }

    public void goneLoading() {
        context.runOnUiThread(new Runnable() {
            public void run() {
                isLoading = false;
                if (loading != null) {
                    loading.setVisibility(View.GONE);
                }
            }
        });
    }

    public void showLoading() {
        context.runOnUiThread(new Runnable() {
            public void run() {
                isLoading = true;
                if (tv != null) tv.setText(R.string.progress_loading);
                if (loading != null) {
                    loading.setVisibility(View.VISIBLE);
                    if (progressBar != null && !progressBar.isShown()) progressBar.setVisibility(View.VISIBLE);
                }
            }

        });
    }

    public boolean isAllGone() {
        if (loading.getVisibility() == View.GONE) return true;
        return false;
    }

    public View getLoadingView() {
        return loading;
    }
}
