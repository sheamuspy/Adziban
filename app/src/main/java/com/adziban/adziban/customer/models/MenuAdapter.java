package com.adziban.adziban.customer.models;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.adziban.adziban.R;

import java.util.ArrayList;

/**
 * Created by sheamus on 24/11/2015.
 */
public class MenuAdapter extends ArrayAdapter<MenuItem> {
    public MenuAdapter(Context context, ArrayList<MenuItem> menuItems){
        super(context,0,menuItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        MenuItem menuItem = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_list_template,parent,false);
        }

        TextView foodPortion = (TextView) convertView.findViewById(R.id.foodportion);
        TextView foodName = (TextView) convertView.findViewById(R.id.foodname);
        TextView foodPrice = (TextView) convertView.findViewById(R.id.foodprice);

        return convertView;

    }
}
