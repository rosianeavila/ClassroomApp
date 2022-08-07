package com.example.classroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

public class ThirdActivity extends AppCompatActivity {
    private TextView txtVSelecionado;
    private Button btnCheckin;
    private String selecionado;
    private SQLiteDatabase bancoDados;
    private Conexao conexao;
    private Integer aulaIdSelecionada;
    private Integer alunoIdLogado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Intent intent = getIntent();
        alunoIdLogado = intent.getIntExtra("alunoIdLogado", 0);
        selecionado = intent.getStringExtra("selecionado");

        txtVSelecionado = (TextView)findViewById(R.id.txtVSelecionado);
        txtVSelecionado.setText(selecionado);

        btnCheckin = (Button)findViewById(R.id.btnCheckin);
    }

    public void clickCheckin(View view) {

        if(view == btnCheckin){
            aulaIdSelecionada = Integer.parseInt(selecionado.substring(0,1));

            try
            {
                conexao = new Conexao(this);
                bancoDados = conexao.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put("idAluno", alunoIdLogado);
                values.put("idCadastroAulas", aulaIdSelecionada);
                long newRowId = bancoDados.insert("checkins", null, values);

                Toast.makeText(getApplicationContext(),"Checkin realizado com sucesso!", Toast.LENGTH_SHORT).show();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}