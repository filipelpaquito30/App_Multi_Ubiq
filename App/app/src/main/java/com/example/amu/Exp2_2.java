package com.example.amu;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;

public class Exp2_2 extends AppCompatActivity {
    Button backButton;
    TextView stats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exp2_2);
        backButton = findViewById(R.id.buttonsair);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        ArrayList<Date> timeList = (ArrayList<Date>)intent.getSerializableExtra("timelist");
        ArrayList<Float> velList = (ArrayList<Float>)intent.getSerializableExtra("vellist");
        getStats(velList);
        showGraph(timeList,velList);

    }
    public void showGraph(ArrayList<Date> timeList, ArrayList<Float> velList){
        GraphView graph = (GraphView) findViewById(R.id.graph);
        //ArrayList<DataPoint> data = new ArrayList<DataPoint>();
        DataPoint[] data = {};
        for(int x = 0; x < timeList.size();x++){
            DataPoint datapoit = new DataPoint(timeList.get(x),velList.get(x));
            data[x]=datapoit;
        }
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(data);
        graph.addSeries(series);
    }

    public void getStats(ArrayList<Float> array){
        float min = array.get(0);
        float max = array.get(0);
        float avg = 0;
        for(float x : array){
            if(x < min)
                min=x;
            if(x > max)
                max=x;
            avg+=x;
        }
        avg=avg/array.size();
        stats = (TextView) findViewById(R.id.statsText);
        stats.setText("Velocidade Mínima: " + mps_to_kmph(min) + "\n" + "Velocidade Máxima: " + mps_to_kmph(max) + "\n" + "Velocidade Média: " + mps_to_kmph(avg));


    }

    static int mps_to_kmph(float mps)
    {
        return(int) (3.6 * mps);
    }
}
