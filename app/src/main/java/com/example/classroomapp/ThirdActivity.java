package com.example.classroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
    private String selecionado, alunoNomeLogado;
    private SQLiteDatabase bancoDados;
    private Conexao conexao;
    private Integer aulaIdSelecionada;
    private Integer alunoIdLogado;
    private Integer qtdCheckinsAlunoSemana, qtdMaximaAlunos;
    private Funcoes funcoes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Intent intent = getIntent();
        alunoIdLogado = intent.getIntExtra("alunoIdLogado", 0);
        selecionado = intent.getStringExtra("selecionado");
        alunoNomeLogado = intent.getStringExtra("alunoNomeLogado");

        txtVSelecionado = (TextView)findViewById(R.id.txtVSelecionado);
        txtVSelecionado.setText(selecionado);

        btnCheckin = (Button)findViewById(R.id.btnCheckin);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("alunoIdLogado", alunoIdLogado);
        intent.putExtra("alunoNomeLogado", alunoNomeLogado);
        startActivity(intent);
    }

    public void clickCheckin(View view) {
        aulaIdSelecionada = Integer.parseInt(selecionado.substring(0,1));

        if((view == btnCheckin) & (validaCheckin())){
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

    private boolean validaCheckin() {
        Funcoes funcoes = new Funcoes(this);
        qtdCheckinsAlunoSemana = funcoes.CalculaQtdChechins(alunoIdLogado, aulaIdSelecionada);
        qtdMaximaAlunos = funcoes.retornaQtdMaximaAula(aulaIdSelecionada);

        if (qtdCheckinsAlunoSemana >= qtdMaximaAlunos) {
            Toast.makeText(getApplicationContext(),"Não existem vagas disponíveis nesta aula!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}