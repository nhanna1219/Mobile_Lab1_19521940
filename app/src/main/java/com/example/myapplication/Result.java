package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Utils.Adapter.CustomAdapter;
import com.example.myapplication.Utils.Models.User;
import com.example.myapplication.ViewModels.UserVM;

import java.util.List;

public class Result extends AppCompatActivity {
    ListView listView;
    CustomAdapter adapter;
    private UserVM userVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        adapter = new CustomAdapter(getApplicationContext());
        listView = findViewById(R.id.customListView);
        listView.setAdapter(adapter);

        userVM = new ViewModelProvider(this).get(UserVM.class);
        userVM.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
//                Toast.makeText(Result.this, "on changed",Toast.LENGTH_SHORT).show();
                if(!users.isEmpty()) {
                    adapter.setUsers(users);
                }
            }
        });
    }
}