package com.u.tallerify.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created by saguilera on 3/12/17.
 */

public final class MetricsUtils {

    private MetricsUtils() {
    }

    public static float convertPixelsToDp(float px){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return Math.round(dp);
    }

    public static float convertDpToPixel(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

}
