package com.example.cm;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private ArrayList<InformModel> Item;


    public CustomAdapter(Activity activity, ArrayList<InformModel> Items) {
        this.activity = activity;
        this.Item = Items;
    }

    @Override
    public int getCount() {
        return Item.size();
    }

    @Override
    public InformModel getItem(int position) {
        return Item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
        }
//        TextView tvId = (TextView) convertView.findViewById(R.id.tvId);
        TextView tvAssunto = (TextView) convertView.findViewById(R.id.tvAssunto);
        TextView tvLocal = (TextView) convertView.findViewById(R.id.tvLocal);

        InformModel sm = Item.get(position);
        tvAssunto.setText(sm.getAssunto());
        tvLocal.setText(sm.getLocal());
        //     tvId.setText(sm.getId());


        return convertView;
    }
}
