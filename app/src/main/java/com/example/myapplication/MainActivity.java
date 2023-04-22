package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.myapplication.Utils.Models.User;
import com.example.myapplication.ViewModels.UserVM;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    UserVM userVM;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        TextInputLayout name_TI = findViewById(R.id.nameWrapper);
        TextInputLayout phone_TI = findViewById(R.id.phoneWrapper);

        userVM = new ViewModelProvider(this).get(UserVM.class);

        Button addLocal_bt = (Button) findViewById(R.id.addLButton);
        Button queryLocal_bt = (Button) findViewById(R.id.queryLButton);

        Button addSQLite_bt = (Button) findViewById(R.id.addSQLiteButton);
        Button querySQLite_bt = (Button) findViewById(R.id.querySQLiteButton);
        addLocal_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Objects.requireNonNull(name_TI.getEditText()).getText().toString();
                String phone = Objects.requireNonNull(phone_TI.getEditText()).getText().toString();
                User user = new User(name, phone);
                userVM.insertUser(user);
                Toast.makeText(MainActivity.this, "Successfully",Toast.LENGTH_SHORT).show();
            }
        });
        queryLocal_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Result.class);
                i.putExtra("queryLocalButton", true);
                i.putExtra("querySQLiteButton", false);
                startActivity(i);
            }
        });

        addSQLite_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Objects.requireNonNull(name_TI.getEditText()).getText().toString();
                String phone = Objects.requireNonNull(phone_TI.getEditText()).getText().toString();
                db = new DatabaseHandler(MainActivity.this);
                db.insertUser(name, phone);
            }
        });
        querySQLite_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Result.class);
                i.putExtra("queryLocalButton", false);
                i.putExtra("querySQLiteButton", true);
                startActivity(i);
            }
        });
    }
}