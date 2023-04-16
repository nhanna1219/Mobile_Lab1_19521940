package com.example.myapplication.Utils.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myapplication.DAO.UserDao;
import com.example.myapplication.R;
import com.example.myapplication.Utils.Models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context ctx;
    private List<User> users = new ArrayList<>();

    public CustomAdapter(Context ctx) {
        this.inflater = LayoutInflater.from(ctx);
        this.ctx = ctx;
    }

    public void setUsers(List<User> users){
        this.users = users;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (users == null) {
            return 0;
        }
        return users.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.activity_custom_list_view, null);
        User user = users.get(i);
        TextView t = view.findViewById(R.id.text);
        t.setText(user.name + ": " + user.phone);
        return view;
    }
}
