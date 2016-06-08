package com.yuzhi.fine.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.yuzhi.fine.R;
import com.yuzhi.fine.ui.UIHelper;
import com.yuzhi.fine.ui.viewpagerindicator.CirclePageIndicator;
import com.yuzhi.fine.utils.SharedPreferences;

/**
 * Created by tiansj on 15/7/29.
 */
public class SplashActivity extends FragmentActivity {

    private Button btnHome;
    private CirclePageIndicator indicator;
    private ViewPager pager;
    private GalleryPagerAdapter adapter;
    private int[] images = {
            R.drawable.newer01,
            R.drawable.newer02,
            R.drawable.newer03,
            R.drawable.newer04
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        final boolean firstTimeUse = SharedPreferences.getInstance().getBoolean("first-time-use", true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(firstTimeUse) {
                    Animation fadeOut = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.fadeout);
                    fadeOut.setFillAfter(true);
                    findViewById(R.id.guideImage).startAnimation(fadeOut);
                    initGuideGallery();
                } else {
                    UIHelper.showHome(SplashActivity.this);
                }
            }
        }, 2000);
    }

    private void initGuideGallery() {
        final Animation fadeIn= AnimationUtils.loadAnimation(this, R.anim.fadein);
        btnHome = (Button) findViewById(R.id.btnHome);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.getInstance().putBoolean("first-time-use", false);
                UIHelper.showHome(SplashActivity.this);
            }
        });

        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        indicator.setVisibility(View.VISIBLE);
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setVisibility(View.VISIBLE);

        adapter = new GalleryPagerAdapter();
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == images.length - 1) {
                    btnHome.setVisibility(View.VISIBLE);
                    btnHome.startAnimation(fadeIn);
                } else {
                    btnHome.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView item = new ImageView(SplashActivity.this);
            item.setScaleType(ImageView.ScaleType.CENTER_CROP);
            item.setImageResource(images[position]);
            container.addView(item);
            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }

}
