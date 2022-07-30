package com.example.classroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {
    private TextView txtVSelecionado;
    private Button btnCheckin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        txtVSelecionado = (TextView)findViewById(R.id.txtVSelecionado);
        String selecionado = getIntent().getStringExtra("Chave");
        txtVSelecionado.setText(selecionado);

        btnCheckin = (Button)findViewById(R.id.btnCheckin);

    }

    public void clickCheckin(View view) {
        if(view == btnCheckin){
            Toast.makeText(getApplicationContext(),"EM CONSTRUÇÃO", Toast.LENGTH_SHORT).show();
        }
    }
}