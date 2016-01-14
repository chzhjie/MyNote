package com.example.changzhenjie.mynote.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by changzhenjie on 1/14/16.
 */
public class ColorSelector extends Dialog {
    int color = Color.BLACK;
    Context context;
    View colorView;
    View view, backView;//background

    OnColorSelectedListener onColorSelectedListener;
    //Slider red, green, blue;

    public ColorSelector(Context context){
        super(context,android.R.style.Theme_Material_Light_NoActionBar_TranslucentDecor);
    }
    @Override
    public void create() {
        super.create();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void addContentView(View view, ViewGroup.LayoutParams params) {
        super.addContentView(view, params);
    }

    // Event that execute when color selector is closed
    public interface OnColorSelectedListener{
        public void onColorSelected(int color);
    }

}
