package com.example.classroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edtLogin,edtSenha;
    private TextView txtLogin,txtSenha,txtRegister;
    private Button btnGo;
    private SQLiteDatabase bancoDados;
    private String login = null;
    private String senha = null;
    private Conexao conexao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtSenha = (TextView) findViewById(R.id.txtSenha);
        btnGo = (Button) findViewById(R.id.btnGo);
        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        txtRegister = (TextView) findViewById(R.id.txtRegister);
    }

    public void clickBtnGo(View view) {
        if(view == btnGo)
            login = edtLogin.getText().toString();
            senha = edtSenha.getText().toString();
          //  if(edtLogin.getText().toString().contains("student")&&edtSenha.getText().toString().contains("123")){
            if(autenticaUsuario(login, senha)){
                Toast.makeText(getApplicationContext(),"Login efetuado com sucesso", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
            else
                Toast.makeText(getApplicationContext(),"Login inválido", Toast.LENGTH_SHORT).show();
    }

    private boolean autenticaUsuario(String login, String senha) {
        try {
            conexao = new Conexao(this);
            bancoDados = conexao.getWritableDatabase();

            Cursor resultado = bancoDados.rawQuery("SELECT login, senha FROM aluno WHERE login = " + "'" +  login + "'", null);

            while (resultado.moveToNext()) {
                if (login.equals(resultado.getString(0))){
                    if (senha.equals(resultado.getString(1)))
                        return true;
                }
            }
            bancoDados.close();

        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void clickTxtRegister(View view){
        Intent intent = new Intent(getApplicationContext(), FourthActivity.class);
        startActivity(intent);
    }

}