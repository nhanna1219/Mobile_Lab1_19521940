package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Utils.Adapter.CustomAdapter;
import com.example.myapplication.Utils.Models.User;
import com.example.myapplication.ViewModels.UserVM;

import java.util.ArrayList;
import java.util.List;

public class Result extends AppCompatActivity {
    ListView listView;
    CustomAdapter adapter;
    private UserVM userVM;
    DatabaseHandler db;

    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        users = new ArrayList<>();

        adapter = new CustomAdapter(getApplicationContext());
        listView = findViewById(R.id.customListView);
        listView.setAdapter(adapter);

        boolean queryLocalButton = getIntent().getBooleanExtra("queryLocalButton", false);
        boolean querySQLiteButton = getIntent().getBooleanExtra("querySQLiteButton", false);

        if (queryLocalButton) {
            userVM = new ViewModelProvider(this).get(UserVM.class);
            userVM.getAllUsers().observe(this, new Observer<List<User>>() {
                @Override
                public void onChanged(List<User> users) {
//                Toast.makeText(Result.this, "on changed",Toast.LENGTH_SHORT).show();
                    if (!users.isEmpty()) {
                        adapter.setUsers(users);
                    }
                }
            });
        } else if (querySQLiteButton) {
            db = new DatabaseHandler(Result.this);
            displayUser();
        }
    }
    public void displayUser(){
        Cursor cursor = db.getUsers();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
        } else {

            while (cursor.moveToNext()){
                users.add(new User(cursor.getString(1), cursor.getString(2)));
                adapter.setUsers(users);
            }
        }
    }
}