package com.trivediinfoway.fruitpickingapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by TI A1 on 02-05-2018.
 */

public class CustomAdapter extends BaseAdapter {

    ArrayList<DataClass> myList;
    Activity activity;
    LayoutInflater inflater;

    public CustomAdapter(ArrayList<DataClass> myList, Activity activity) {
        this.myList = myList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @Override
    public Object getItem(int i) {
        return myList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater == null)
        {
            inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view == null)
        {
            view = inflater.inflate(R.layout.row,null);
        }
        TextView tvname = (TextView)view.findViewById(R.id.tvname);
        TextView tvfruit = (TextView)view.findViewById(R.id.tvfruit);
        TextView tvnumber = (TextView)view.findViewById(R.id.tvnumber);
        TextView tvweight = (TextView)view.findViewById(R.id.tvweight);

        DataClass channel = myList.get(i);
        tvname.setText(channel.getWorker_name()+"");
        tvfruit.setText(channel.getFruit()+"");
        tvnumber.setText(channel.getNumber()+"");
        tvweight.setText(channel.getWeight()+"");

        return view;
    }
}
