package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {

    Button login;
    TextView nav_signup;

    TextInputLayout tp_username, tp_password;

    private static final String TAG = "Login";

    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";

    private FirebaseFirestore db;
    private CollectionReference usersRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseFirestore.getInstance();
        usersRef = db.collection("User");

        nav_signup = (TextView) findViewById(R.id.nav_signup);

        nav_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SignUp.class));
            }
        });

        tp_username = findViewById(R.id.username);
        tp_password = findViewById(R.id.password);

        login = (Button) findViewById(R.id.btn_login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
    }

    protected void checkLogin(){
        String username = tp_username.getEditText().getText().toString();
        String password = tp_password.getEditText().getText().toString();

        String hashedPassword = HashUtils.sha256(password);

        Query query = usersRef.whereEqualTo("username", username);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // Check if the password matches
                        if (document.getString("password").equals(hashedPassword)) {
                            // Password is correct
                            Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
                            String fName = document.getString("fullname");
                            Intent i = new Intent(getApplicationContext(), Home.class);
                            i.putExtra("fullname", fName);
                            startActivity(i);
                        } else {
                            // Password is incorrect
                            Toast.makeText(getApplicationContext(), "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }
}