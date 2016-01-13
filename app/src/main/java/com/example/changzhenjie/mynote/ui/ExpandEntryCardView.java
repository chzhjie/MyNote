package com.example.changzhenjie.mynote.ui;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;

/**
 * Created by changzhenjie on 12/17/15.
 */
public class ExpandEntryCardView extends CardView{
    public static final String Tag = "ExpandEntryCardView";


    public ExpandEntryCardView(android.content.Context context) {
        super(context);
    }

    public ExpandEntryCardView(android.content.Context context, android.util.AttributeSet attrs) {
        super(context, attrs);
    }

    public ExpandEntryCardView(android.content.Context context, android.util.AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static final class Entry{
        private final int mId = 0;
        private final Drawable mIcon = null;
        private final String mHeader = null;
        private final String mSubheader = null;
        private final String mText = null;


    }
}
