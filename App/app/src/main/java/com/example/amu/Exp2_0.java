package com.example.amu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Exp2_0 extends AppCompatActivity {
    Button backButton;
    Button comecarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp2_0);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        comecarButton = findViewById(R.id.comecarButton);
        comecarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExperience(1);
            }
        });

    }
    public void openExperience(int ExpId){
        Intent intent = new Intent();
        switch (ExpId) {
            case 1:
                intent = new Intent(this, Exp2_1.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}

