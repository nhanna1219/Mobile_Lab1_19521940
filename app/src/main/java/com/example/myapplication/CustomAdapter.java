package com.example.myapplication;

import android.content.ClipData;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Employee> empS;
    LayoutInflater inflater;

    public CustomAdapter(Context ctx, ArrayList<Employee> e) {
        this.context = ctx;
        this.empS = e;
        inflater = LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return empS.size();
    }

    @Override
    public Object getItem(int i) {
        return empS.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_custom_list_view,null);
        TextView text = view.findViewById(R.id.nameT);
        text.setText(empS.get(i).getFullName() + ": " + String.format("%.2f",empS.get(i).getNSalary()));
        return view;
    }
}
