package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    private TextView nav_login;

    private List<User> users;

    private Button signUp;
    private TextInputLayout tp_fullName, tp_phone, tp_username, tp_password;

    private static final String TAG = "SignUp";
    private static final String KEY_NAME = "fullname";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
            .build();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db.setFirestoreSettings(settings);

        nav_login = (TextView) findViewById(R.id.nav_login);

        nav_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        tp_fullName = (TextInputLayout) findViewById(R.id.fullname);
        tp_phone = (TextInputLayout) findViewById(R.id.phone);
        tp_username = (TextInputLayout) findViewById(R.id.signup_username);
        tp_password = (TextInputLayout) findViewById(R.id.password);

        signUp = (Button) findViewById(R.id.btn_signup);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertUser();
            }
        });

    }

    protected boolean checkValidation(String username, String password){
        if (username.isEmpty()) {
            tp_username.setError("Required!");
            return false;
        }
        else if (username.length() < 6) {
            tp_username.setError("Username must be at least 6 characters");
            return false;
        }
        tp_username.setError(null);

        if (password.isEmpty()) {
            tp_password.setError("Required");
            return false;
        }
        else if (password.length() < 6) {
            tp_password.setError("Password must be at least 6 characters");
            return false;
        }
        tp_password.setError(null);
        return true;

    }
    protected void insertUser() {

        String fullName = tp_fullName.getEditText().getText().toString();
        String phone = tp_phone.getEditText().getText().toString();
        String userName = tp_username.getEditText().getText().toString();

        String password = tp_password.getEditText().getText().toString();
        String hashedPassword = HashUtils.sha256(password);


        if (!checkValidation(userName, password)) {
            return;
        }
            Map<String, Object> user = new HashMap<>();
            user.put(KEY_NAME, fullName);
            user.put(KEY_PHONE, phone);
            user.put(KEY_USERNAME, userName);
            user.put(KEY_PASSWORD, hashedPassword);

            db.collection("User").add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getApplicationContext(), "Registration Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                    });
    }
}