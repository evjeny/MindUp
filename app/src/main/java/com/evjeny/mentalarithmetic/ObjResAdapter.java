package com.evjeny.mentalarithmetic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evjeny on 08.04.2017.
 */

public class ObjResAdapter extends BaseAdapter {
    Context c;
    LayoutInflater li;
    ArrayList<ArrayList<Integer>> objects;

    ObjResAdapter(Context context, ArrayList<ArrayList<Integer>> items) {
        c = context;
        objects = items;
        li = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view==null) {
            view = li.inflate(R.layout.obj_result_item, null);
        }
        ArrayList<Integer> list = getLA(position);

        TextView title = (TextView) view.findViewById(R.id.title);
        TextView true_val = (TextView) view.findViewById(R.id.true_val);
        TextView false_val = (TextView) view.findViewById(R.id.false_val);

        title.setText(c.getString(list.get(0)));
        true_val.setText(c.getString(R.string.tru)+": "+list.get(1));
        false_val.setText(c.getString(R.string.fals)+": "+list.get(2));
        return view;
    }
    ArrayList<Integer> getLA(int position) {
        return ((ArrayList<Integer>) getItem(position));
    }
}
