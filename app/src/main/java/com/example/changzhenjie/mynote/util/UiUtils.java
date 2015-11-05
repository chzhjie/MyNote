package com.example.changzhenjie.mynote.util;

import android.app.Activity;
import android.view.View;

/**
 * Created by zhenjie on 2015/5/15.
 */
public class UiUtils {
    private static final String TAG = LogUtils.makeLogTag(UiUtils.class);


    public static <T extends View> T getView(Activity parent, int viewID) {
        return (T) checkView(parent.findViewById(viewID));
    }

    public static <T extends View> T getView(View parent, int viewID) {
        return (T) checkView(parent.findViewById(viewID));
    }

    private static View checkView(View v) {
        if (v == null) {
            throw new IllegalArgumentException("View don not exist");
        }
        return v;
    }

}
