package com.yuzhi.fine.base.ui.progress;

import android.view.Gravity;

import com.yuzhi.fine.R;


/**
 * Created by Sai on 15/8/16.
 */
public class AnimateUtil {
    private static final int INVALID = -1;
    static int getAnimationResource(int gravity, boolean isInAnimation) {
        switch (gravity) {
            case Gravity.TOP:
                return isInAnimation ? R.anim.progress_slide_in_top : R.anim.progress_slide_out_top;
            case Gravity.BOTTOM:
                return isInAnimation ? R.anim.progress_slide_in_bottom : R.anim.progress_slide_out_bottom;
            case Gravity.CENTER:
                return isInAnimation ? R.anim.progress_fade_in_center : R.anim.progress_fade_out_center;
            default:
                // This case is not implemented because we don't expect any other gravity at the moment
        }
        return INVALID;
    }
}
