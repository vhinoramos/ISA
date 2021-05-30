package com.mobdeve.ramos.isa;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.util.Strings;

import java.util.ArrayList;

public class TextListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Text> textList;

    public TextListAdapter(Context context, int layout, ArrayList<Text> textList) {
        this.context = context;
        this.layout = layout;
        this.textList = textList;
    }

    @Override
    public int getCount() {
        return textList.size();
    }

    @Override
    public Object getItem(int position) {
        return textList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        TextView text_show;
        TextView date_show;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        View row = view;
        ViewHolder holder = new ViewHolder();

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layout, null);

            holder.text_show = (TextView) row.findViewById(R.id.text_show);
            holder.date_show = (TextView) row.findViewById(R.id.date_show);
            row.setTag(holder);
        }
        else{
            holder = (ViewHolder) row.getTag();
        }


        Text text_s = textList.get(position);
        holder.text_show.setText(text_s.getText_v());
        holder.date_show.setText(text_s.getDate_v());



        return row;
    }

}
