package com.example.classroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FourthActivity extends AppCompatActivity {
    private EditText editNome, editLogin,editSenha;
    private Button btnSalvar;
    private AlunoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);
        editNome = (EditText) findViewById(R.id.editNome);
        editLogin = (EditText) findViewById(R.id.editLogin);
        editSenha = (EditText) findViewById(R.id.editSenha);
        dao = new AlunoDAO(this);
    }

    public void salvarAluno(View view){
        Aluno a = new Aluno();
        a.setNome(editNome.getText().toString());
        a.setLogin(editLogin.getText().toString());
        a.setSenha(editSenha.getText().toString());
        long id = dao.inserir(a);
        Toast.makeText(this, "Aluno inserido com id: " + id, Toast.LENGTH_SHORT).show();
    }
}