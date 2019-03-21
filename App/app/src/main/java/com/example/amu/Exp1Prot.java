package com.example.amu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

public class Exp1Prot extends AppCompatActivity {
    Button backButton;
    Button comecarButton;
    Button estatisticasButton;
    TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp1_prot);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textview = (TextView) findViewById(R.id.textView);
        textview.setMovementMethod(new ScrollingMovementMethod());


        comecarButton = findViewById(R.id.comecarButton);
        comecarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExperience(1);
            }
        });

        estatisticasButton = findViewById(R.id.estatisticasButton);
        estatisticasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExperience(2);
            }
        });



    }

    public void openExperience(int ExpId){
        Intent intent = new Intent();
        switch (ExpId) {
            case 1:
                intent = new Intent(this, Exp1_1.class);
                break;
            case 2:
                intent = new Intent(this, Exp1_2.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
