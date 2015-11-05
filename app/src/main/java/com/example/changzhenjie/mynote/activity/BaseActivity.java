package com.example.changzhenjie.mynote.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by changzhenjie on 11/5/15.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
