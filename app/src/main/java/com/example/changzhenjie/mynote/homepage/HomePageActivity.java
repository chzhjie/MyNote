package com.example.changzhenjie.mynote.homepage;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.changzhenjie.mynote.R;
import com.example.changzhenjie.mynote.activity.BaseActivity;
import com.example.changzhenjie.mynote.ui.ContactsDrawable;
import com.example.changzhenjie.mynote.util.LogUtils;
import com.example.changzhenjie.mynote.util.UiUtils;

public class HomePageActivity extends BaseActivity {
    private static final String TAG = LogUtils.makeLogTag(HomePageActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        overridePendingTransition(0, 0);
        ImageView imageView = UiUtils.getView(this,R.id.titlecontact);
        ContactsDrawable contactsDrawable = new ContactsDrawable(getResources());
        contactsDrawable.bind("振杰", Color.GRAY);
        imageView.setImageDrawable(contactsDrawable);

        SpannableStringBuilder stringBuilder = new SpannableStringBuilder("aaaaaa");
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(2f);
        stringBuilder.setSpan(styleSpan,0,5, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getSelfNaviDrawerItem() {
        return R.id.nav_home;
    }

}
