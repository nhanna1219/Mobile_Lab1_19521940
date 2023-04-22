package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.myapplication.Utils.Models.User;
import com.example.myapplication.ViewModels.UserVM;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    List<User> users;
    UserVM userVM;
    private static final String TAG = "MainActivity";

    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";

    private TextInputLayout name_TI;
    private TextInputLayout phone_TI;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(false)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        db.useEmulator("10.0.2.2", 8080);
        db.setFirestoreSettings(settings);

        userVM = new ViewModelProvider(this).get(UserVM.class);
        name_TI = findViewById(R.id.nameWrapper);
        phone_TI = findViewById(R.id.phoneWrapper);

        Button addLocal_bt = (Button) findViewById(R.id.addLButton);
        Button addFireStore_bt = (Button) findViewById(R.id.addFSButton);
        Button queryLocal_bt = (Button) findViewById(R.id.queryLButton);
        Button queryFireStore_bt = (Button) findViewById(R.id.queryFSButton);

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
                i.putExtra("localButtonState", true);
                i.putExtra("queryButtonState", false);
                startActivity(i);
            }
        });

        addFireStore_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertUser();
            }
        });
        queryFireStore_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Result.class);
                i.putExtra("queryButtonState", true);
                i.putExtra("localButtonState", false);
                startActivity(i);
            }
        });
    }
    protected void insertUser(){
        String name = Objects.requireNonNull(name_TI.getEditText()).getText().toString();
        String phone = Objects.requireNonNull(phone_TI.getEditText()).getText().toString();
        Map<String, Object> user = new HashMap<>();
        user.put(KEY_NAME, name);
        user.put(KEY_PHONE, phone);

        db.collection("User").add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                    }
                });
    }

}