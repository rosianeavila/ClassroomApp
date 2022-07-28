package com.example.classroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SecondActivity extends AppCompatActivity {

    private TextView textSemana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textSemana = (TextView) findViewById(R.id.textSemana);

        Calendar cal = Calendar.getInstance();
        cal.set(2022, 07, 27);
        int semanaAtual = cal.get(Calendar.WEEK_OF_MONTH);
        textSemana.setText(" Semana: " + Integer.toString(semanaAtual));


    }
}