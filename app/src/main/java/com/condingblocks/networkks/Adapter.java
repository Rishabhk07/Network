package com.condingblocks.networkks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rishabhkhanna on 26/03/17.
 */

public class Adapter extends BaseAdapter {

    ArrayList<model> model = new ArrayList<>();
    Context context;

    public Adapter(ArrayList<model> model , Context context) {
        this.model = model;
        this.context = context;
    }


    @Override
    public int getCount() {
        return model.size();
    }

    @Override
    public Object getItem(int position) {
        return model.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = li.inflate(R.layout.layout, null);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvSummary = (TextView) convertView.findViewById(R.id.tvSum);

        model thisModel = model.get(position);

        tvTitle.setText(thisModel.getTitle());
        tvSummary.setText(thisModel.getBody());

        return convertView;
    }
}
