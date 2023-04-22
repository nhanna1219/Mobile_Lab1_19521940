package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myapplication.Utils.Adapter.CustomAdapter;
import com.example.myapplication.Utils.Models.User;
import com.example.myapplication.ViewModels.UserVM;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.View;

import java.util.ArrayList;
import java.util.List;

public class Result extends AppCompatActivity {
    ListView listView;
    CustomAdapter adapter;
    private UserVM userVM;
    private List<User> users;
    private FirebaseFirestore db;
    private CollectionReference userRef;
    private static final String TAG = "Result";

    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        users = new ArrayList<>();

        adapter = new CustomAdapter(getApplicationContext());
        listView = findViewById(R.id.customListView);
        listView.setAdapter(adapter);

        boolean queryButtonState = getIntent().getBooleanExtra("queryButtonState", false);
        boolean localButtonState = getIntent().getBooleanExtra("localButtonState", false);

        if (queryButtonState) {
            db = FirebaseFirestore.getInstance();
            userRef = db.collection("User");
            loadUser();
        } else if (localButtonState) {
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
        }
        ;
    }

    public void loadUser() {
        userRef.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                String name = doc.getString(KEY_NAME);
                                String phone = doc.getString(KEY_PHONE);
                                users.add(new User(name,phone));
                            }
                            adapter.setUsers(users);
                        }else {
                            Toast.makeText(Result.this, "Error!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG,"Error: ", task.getException());
                        }
                    }
                });

    }
}