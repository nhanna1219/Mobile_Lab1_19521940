package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.myapplication.Utils.Models.User;
import com.example.myapplication.ViewModels.UserVM;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    List<User> users;
    UserVM userVM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userVM = new ViewModelProvider(this).get(UserVM.class);

        Button addLocal_bt = (Button) findViewById(R.id.addLButton);
        Button addFireStore_bt = (Button) findViewById(R.id.addFSButton);
        Button queryLocal_bt = (Button) findViewById(R.id.queryLButton);
        Button queryFireStore_bt = (Button) findViewById(R.id.queryFSButton);

        addLocal_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputLayout name_TI = findViewById(R.id.nameWrapper);
                String name = Objects.requireNonNull(name_TI.getEditText()).getText().toString();
                TextInputLayout phone_TI = findViewById(R.id.phoneWrapper);
                String phone = Objects.requireNonNull(phone_TI.getEditText()).getText().toString();
                User user = new User(name, phone);
                userVM.insertUser(user);
                Toast.makeText(MainActivity.this, "Successfully",Toast.LENGTH_SHORT).show();
            }
        });
        queryLocal_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Result.class));
            }
        });

        queryFireStore_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Result.class));
            }
        });
    }

}