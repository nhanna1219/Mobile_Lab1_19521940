package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Employee> arrEmp = new ArrayList<Employee>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextInputLayout text = findViewById(R.id.nameWrapper);
        TextInputLayout number = findViewById(R.id.grossSalaryWrapper);
        listView = findViewById(R.id.customListView);
        Button btn = (Button) findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = (text.getEditText().getText()).toString();
                double grossSalary = Double.parseDouble(number.getEditText().getText().toString());
                Employee e = new Employee(name, grossSalary);
                arrEmp.add(e);
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(),arrEmp);
                listView.setAdapter(customAdapter);
            }
        });
    }
}