package com.example.amu;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.os.SystemClock;
import android.widget.Chronometer;

public class Exp2_1 extends AppCompatActivity {
    Button backButton;
    int distancia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int distancia = 2;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp2_1);
        final Chronometer chronometer = (Chronometer)findViewById(R.id.chronometerExample);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (distancia == 2) {
            chronometer.start();
        }
        Button buttonStart = (Button)findViewById(R.id.buttonStartChronometer);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.start();
            }
        });

        Button buttonStop = (Button)findViewById(R.id.buttonStopChronometer);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chronometer.stop();
            }
        });

        Button buttonRestart = (Button)findViewById(R.id.buttonRestartChronometer);
        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long systemCurrTime = SystemClock.elapsedRealtime();
                chronometer.setBase(systemCurrTime);
            }
        });
    }


}
