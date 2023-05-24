package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BackAnimation extends AppCompatActivity {
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_animation);

        back = (Button) findViewById(R.id.back_btn);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BackAnimation.this, MainActivity.class));

                    overridePendingTransition(R.anim.anim_slide_enter, R.anim.anim_slide_exit);
            }
        });

    }
}