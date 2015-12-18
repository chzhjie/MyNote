package com.example.changzhenjie.mynote.categories;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.changzhenjie.mynote.R;
import com.example.changzhenjie.mynote.entity.BillNoteType;
import com.example.changzhenjie.mynote.ui.ContactsDrawable;
import com.example.changzhenjie.mynote.util.UiUtils;

public class AddCategoryActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    public static final String Tag = "AddCategoryActivity";
    public static final int COLOR_PICKER_REQ_CODE = 0100;
    public static final String COLOR_RESULT = "color_result";

    private EditText categoryName;
    private EditText parentCategoryName;
    private RadioGroup inOrOutGroup;
    private ImageView categoryColorImage;
    private boolean isMainType = true;
    private int inOrOut = 0;
    private int categoryColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_save_category) {
            saveCategory();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_category, menu);
        return true;
    }

    private void initView() {
        categoryName = UiUtils.getView(this, R.id.category_name);
        parentCategoryName = UiUtils.getView(this, R.id.category_parent_name);
        inOrOutGroup = UiUtils.getView(this, R.id.category_inorout);
        categoryColorImage = UiUtils.getView(this, R.id.category_color);
        parentCategoryName.setOnClickListener(this);
        categoryColorImage.setOnClickListener(this);
        inOrOutGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.category_color) {
            Intent intent = new Intent(AddCategoryActivity.this, ColorPicker.class);
            startActivityForResult(intent, COLOR_PICKER_REQ_CODE, null);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Log.d(Tag, "checkedID = " + checkedId);
        if (checkedId == R.id.category_out) {
            inOrOut = 0;
        } else {
            inOrOut = 1;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == COLOR_PICKER_REQ_CODE) {
            ContactsDrawable contactsDrawable = new ContactsDrawable(getResources());
            categoryColor = data.getIntExtra(COLOR_RESULT, Color.GRAY);
            contactsDrawable.bind(categoryColor);
            categoryColorImage.setImageDrawable(contactsDrawable);
        }
    }

    private void saveCategory() {
        BillNoteType category = new BillNoteType();
        String parentname = parentCategoryName.getText().toString();
        if(TextUtils.isEmpty(parentname)){
            category.noteTypeCode = CategoryUtils.generateMainTypeCode(this);
        }
        category.noteTypeName = categoryName.getText().toString().trim();
        category.noteTypeColor = categoryColor;
        category.isMainType = isMainType;
        category.inOrOut = inOrOut;

        if(!CategoryUtils.checkNoteTypeIsValid(category,this)){
            Toast.makeText(this,"这种类型已经存在",Toast.LENGTH_SHORT).show();
            return;
        }
        category.save(this);
        finish();
    }
}
