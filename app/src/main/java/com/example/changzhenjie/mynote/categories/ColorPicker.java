package com.example.changzhenjie.mynote.categories;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.changzhenjie.mynote.R;
import com.example.changzhenjie.mynote.util.UiUtils;

public class ColorPicker extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static final String Tag = "ColorPicker";
    TypedArray colors;
    GridView coloPickerGrid;
    private static int sDefaultColor;
    LayoutInflater inflater;
    ColorPickerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        colors = getResources().obtainTypedArray(R.array.categories_type_colors);
        sDefaultColor = getResources().getColor(R.color.colorPrimaryDark);
        coloPickerGrid = UiUtils.getView(this, R.id.color_pick_grid);
        inflater = LayoutInflater.from(this);
        adapter = new ColorPickerAdapter();
        coloPickerGrid.setAdapter(adapter);
        coloPickerGrid.setOnItemClickListener(this);

    }
    class ColorPickerAdapter extends BaseAdapter{
        @Override
        public boolean areAllItemsEnabled() {
            return super.areAllItemsEnabled();
        }

        @Override
        public int getCount() {
            return colors.length();
        }

        @Override
        public Object getItem(int position) {
            return colors.getColor(position,sDefaultColor);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.color_picker_item,null);
                holder.colorItem = UiUtils.getView(convertView,R.id.color_item);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            Log.d(Tag, "color:" + colors.getColor(position, sDefaultColor));
            holder.colorItem.setBackgroundColor(colors.getColor(position, sDefaultColor));
            return convertView;
        }
        class ViewHolder{
            ImageView colorItem;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        intent.putExtra(AddCategoryActivity.COLOR_RESULT,colors.getColor(position,sDefaultColor));
        setResult(AddCategoryActivity.COLOR_PICKER_REQ_CODE,intent);
        finish();
    }
}
