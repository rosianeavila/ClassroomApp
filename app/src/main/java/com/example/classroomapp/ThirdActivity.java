package com.example.classroomapp;

import androidx.appcompat.app.AppCompatActivity;

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
    private String idAulaSelecionada;
    private Integer idAlunoLogado;
    private String selecionado;
    private SQLiteDatabase bancoDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        txtVSelecionado = (TextView)findViewById(R.id.txtVSelecionado);
        selecionado = getIntent().getStringExtra("Chave");
        txtVSelecionado.setText(selecionado);

        btnCheckin = (Button)findViewById(R.id.btnCheckin);

    }

    public void clickCheckin(View view) {

        if(view == btnCheckin){
            idAulaSelecionada = selecionado.substring(0,1);
            Aluno a = new Aluno();
            idAlunoLogado = a.getId();
            Date dataAtual = new Date();


            try
            {
                bancoDados = openOrCreateDatabase("classRoomApp", MODE_PRIVATE, null);

                bancoDados.execSQL("INSERT INTO checkins(idAluno, idCadastroAulas, dataCheckin)" +
                                   " VALUES (idAlunoLogado, idAulaSelecionada, dataAtual)");

                bancoDados.close();

                Toast.makeText(getApplicationContext(),"Checkin realizado com sucesso!", Toast.LENGTH_SHORT).show();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}