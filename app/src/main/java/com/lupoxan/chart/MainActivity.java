package com.lupoxan.chart;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LineChart chart;

    ArrayList<String> xAxes = new ArrayList<>();
    ArrayList<Entry> yAxesExt = new ArrayList<>();
    ArrayList<Entry> yAxesInt = new ArrayList<>();

    LineDataSet dataSet;
    LineDataSet dataSetInt;

    private boolean status = true;
    public float count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chart = findViewById(R.id.graph);
        new DatosRandom();
    }

    private void generarGrafica(ArrayList<String> fecha, ArrayList<Float> incX, ArrayList<Float> incY) {
        //Añadiendo las horas
        xAxes.addAll(fecha);

        for(Float x : incX){
            yAxesExt.add(new Entry(x, xAxes.size()));
        }

        for(Float y : incY){
            yAxesInt.add(new Entry(y, xAxes.size()));
        }

        dataSet = new LineDataSet(yAxesExt, getResources().getString(R.string.incx));
        dataSetInt = new LineDataSet(yAxesInt, getResources().getString(R.string.incy));

        dataSet.setDrawCircles(true);
        dataSet.setDrawFilled(true);
        dataSet.setColor(Color.BLUE);
        dataSet.setCircleColor(Color.BLUE);
        dataSet.setFillColor(Color.BLUE);

        dataSetInt.setDrawCircles(true);
        dataSetInt.setDrawFilled(true);
        dataSetInt.setCircleColor(Color.RED);
        dataSetInt.setColor(Color.RED);
        dataSetInt.setFillColor(Color.RED);

        ArrayList<ILineDataSet> lineDataSet = new ArrayList<>();//Crear uno nuevo para no repetir las leyendas

        lineDataSet.add(dataSet);//Añadimos los valores de la temperatura exterior
        lineDataSet.add(dataSetInt);//Añadimos los valores de la temperatura interios

        chart.setData(new LineData(xAxes, lineDataSet));//Añadimos esos valores al gráfico

        chart.setVisibleXRangeMaximum(count);

        chart.setDescription(getResources().getString(R.string.inclinacion));
        chart.setDescriptionColor(Color.BLACK);

        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);
        chart.setEnabled(true);
        chart.postInvalidate();
    }

    private class DatosRandom extends Thread{

        ArrayList<String> fechas = new ArrayList<>();
        ArrayList<Float> incX = new ArrayList<>();
        ArrayList<Float> incY = new ArrayList<>();

        DatosRandom(){
            start();
        }

        @SuppressLint("SimpleDateFormat")
        @Override
        public void run() {
            while(status){
                try {
                    fechas.clear();
                    incX.clear();
                    incY.clear();

                    fechas.add(new SimpleDateFormat("HH:mm:ss").format(new Date()));

                    incX.add(new Random(new Date().getTime()).nextFloat());
                    incY.add(new Random(new Date().getTime()).nextFloat() + 10);

                    count++;

                    generarGrafica(fechas, incX, incY);

                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        status = false;
    }
}
