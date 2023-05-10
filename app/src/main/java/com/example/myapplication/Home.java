package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Home extends AppCompatActivity {

    TextView fName;

    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fName = (TextView) findViewById(R.id.name);

        Intent i = getIntent();
        String fullName = i.getStringExtra("fullname");
        fName.setText(fullName);
        logOut = (Button) findViewById(R.id.btn_logout);

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                Toast.makeText(Home.this, "Log Out Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }
}