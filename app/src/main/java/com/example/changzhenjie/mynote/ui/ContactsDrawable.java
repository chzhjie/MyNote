package com.example.changzhenjie.mynote.ui;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;

import com.example.changzhenjie.mynote.R;

/**
 * Created by changzhenjie on 11/9/15.
 */
public class ContactsDrawable extends Drawable {

    private Paint mPaint;
    private Paint mBorderPaint;

    private int mColor = 0;
    private String mName;
    private int titleDefaultColor;
    private int backgroundDefaultColor;

    private float titleSize;
    private float mBorderWidth;


    private static final Paint sPaint = new Paint();
    private static final Rect sRect = new Rect();
    private static final char[] sFirstChar = new char[1];


    public ContactsDrawable(final Resources resources) {
        mPaint = new Paint();
        mPaint.setFilterBitmap(true);
        mPaint.setDither(true);

        sPaint.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
        sPaint.setTextAlign(Paint.Align.CENTER);
        sPaint.setAntiAlias(true);

        mBorderPaint = new Paint();
        mBorderPaint.setColor(Color.TRANSPARENT);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        mBorderPaint.setAntiAlias(true);

        mBorderWidth = resources.getDimensionPixelSize(R.dimen.avatar_border_width);
        titleSize = resources.getDimensionPixelSize(R.dimen.tile_letter_font_size_small);
        titleDefaultColor = resources.getColor(R.color.letter_title_default_color);
        backgroundDefaultColor = resources.getColor(R.color.contacts_bg_default_color);
    }

    @Override
    public void draw(Canvas canvas) {
        final Rect bounds = getBounds();
        if (!isVisible() || bounds.isEmpty()) {
            return;
        }
        drawLetterTitle(canvas);

    }

    private void drawLetterTitle(final Canvas canvas) {
        final Rect bounds = getBounds();
        int bgColor = mColor != 0 ? mColor : backgroundDefaultColor;
        sPaint.setColor(bgColor);
        sPaint.setAlpha(mPaint.getAlpha());
        drawCircle(canvas, bounds, sPaint);
        char firstchar = ' ';
        if (!TextUtils.isEmpty(mName)) {
            firstchar = mName.charAt(0);
        }
        if (isEnglishLetterOrDigit(firstchar)) {
            sFirstChar[0] = Character.toUpperCase(firstchar);
        } else {
            sFirstChar[0] = firstchar;
        }
        sPaint.setTextSize(titleSize);
        sPaint.getTextBounds(sFirstChar, 0, 1, sRect);
        sPaint.setColor(Color.WHITE);
        canvas.drawText(sFirstChar, 0, 1, bounds.centerX(), bounds.centerY() + sRect.height() / 2, sPaint);
        Log.d("zhenjie", "char : " + sFirstChar[0] + "titleSize :" + titleSize);
    }

    private static boolean isEnglishLetterOrDigit(final char c) {
        return ('A' <= c && c <= 'Z') || ('a' <= c && c <= 'z') || ('0' <= c && c <= '9');
    }

    /**
     * Draws the largest circle that fits within the given <code>bounds</code>.
     *
     * @param canvas the canvas on which to draw
     * @param bounds the bounding box of the circle
     * @param paint  the paint with which to draw
     */
    private static void drawCircle(Canvas canvas, Rect bounds, Paint paint) {
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), bounds.width() / 2, paint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return 0;
    }

    public void bind(String name) {
        mName = name;
        invalidateSelf();
    }

    public void bind(String name,int color) {
        mName = name;
        mColor = color;
        invalidateSelf();
    }
    public void bind(int color) {
        mColor = color;
        invalidateSelf();
    }

}
