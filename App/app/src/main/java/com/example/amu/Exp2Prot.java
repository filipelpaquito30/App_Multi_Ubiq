package com.example.amu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.TextView;

public class Exp2Prot extends AppCompatActivity {
    Button backButton;
    Button iniciarButton;
    //Button estatisticasButton;
    TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp_prot2);
        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textview = (TextView) findViewById(R.id.title);
        textview.setText("Perfil de Condução");

        textview = (TextView) findViewById(R.id.protocolo);
        textview.setText("1)Mantenha o GPS ligado bem como uma ligação estável à internet;\n 2) Após clicar em ”Iniciar” será lhe pedido para seleccionar um destino, aguarde a indicação de que o gps está activo; \n 3) Para  uma  melhor  precisão  dos  resultados  recomendamos  que  efetue  esta  ex" +
                "periência numa viagem superior a 5 minutos.");
        textview.setMovementMethod(new ScrollingMovementMethod());

        iniciarButton = findViewById(R.id.iniciarButton);
        iniciarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExperience(1);
            }
        });

        /*estatisticasButton = findViewById(R.id.estatisticasButton);
        estatisticasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExperience(2);
            }
        });*/


    }

    public void openExperience(int ExpId){
        Intent intent = new Intent();
        switch (ExpId) {
            case 1:
                intent = new Intent(this, MapsActivity2.class);
                break;
            case 2:
                intent = new Intent(this, Exp2_2.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
