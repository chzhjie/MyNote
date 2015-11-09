package com.example.changzhenjie.mynote.activity;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.example.changzhenjie.mynote.homepage.HomePageActivity;
import com.example.changzhenjie.mynote.moneymanager.MoneyManager;
import com.example.changzhenjie.mynote.paymentbalance.PaymentsActivity;
import com.example.changzhenjie.mynote.R;
import com.example.changzhenjie.mynote.categories.CategoriesActivity;
import com.example.changzhenjie.mynote.settings.SettingsActivity;
import com.example.changzhenjie.mynote.util.LogUtils;

/**
 * Created by changzhenjie on 11/5/15.
 */
public abstract class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = LogUtils.makeLogTag(BaseActivity.class);

    public static final int NAVI_DRAWER_ITEM_INVALID = -1;


    // fade in and fade out durations for the main content when switching between
    // different Activities of the app through the Nav Drawer
    private static final int MAIN_CONTENT_FADEOUT_DURATION = 150;

    private static final int MAIN_CONTENT_FADEIN_DURATION = 250;
    // delay to launch nav drawer item, to allow close animation to play
    private static final int NAVDRAWER_LAUNCH_DELAY = 250;

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        View mainContent = findViewById(R.id.main_content);
        //it is used for switch the activity fade in animation
//        if(mainContent != null){
//            mainContent.setAlpha(0);
//            mainContent.animate().alpha(1).setDuration(MAIN_CONTENT_FADEIN_DURATION);
//        }else{
//            LogUtils.LOGD(TAG,"No main content to fade in");
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        setupToolBarAndNaviDrawer();
    }

    private void setupToolBarAndNaviDrawer() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setCheckedItem(getSelfNaviDrawerItem());
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if (getSelfNaviDrawerItem() == id) {
            // if this page is already the dest page,we don't do anything
            return true;
        }

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoNaviDrawerItem(id);
            }
        },NAVDRAWER_LAUNCH_DELAY);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void gotoNaviDrawerItem(final int id) {
        if (id == R.id.nav_home) {
            Intent intent = new Intent(BaseActivity.this, HomePageActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_payments) {
            Intent intent = new Intent(BaseActivity.this, PaymentsActivity.class);
            createBackStack(intent);
        } else if (id == R.id.nav_wallet) {
            Intent intent = new Intent(BaseActivity.this, MoneyManager.class);
            createBackStack(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(BaseActivity.this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_categories) {
            Intent intent = new Intent(BaseActivity.this, CategoriesActivity.class);
            createBackStack(intent);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(getSelfNaviDrawerItem() == R.id.nav_home){
                finish();
            }
            super.onBackPressed();
        }
    }

    private void createBackStack(Intent intent) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN) {
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntentWithParentStack(intent);
            stackBuilder.startActivities();
        } else {
            startActivity(intent);
            finish();
        }
    }

    protected int getSelfNaviDrawerItem() {
        return NAVI_DRAWER_ITEM_INVALID;
    }
}
