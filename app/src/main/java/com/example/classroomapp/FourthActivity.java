package com.example.classroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FourthActivity extends AppCompatActivity {
    private EditText editNome, editLogin,editSenha;
    private Button btnSalvar;
    private AlunoDAO dao;
    boolean isAllFieldsChecked = true;
    boolean isStudentExists = false;
    private SQLiteDatabase bancoDados;

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void salvarAluno(View view){
        isAllFieldsChecked = CheckAllFields();
        isStudentExists = CheckStudentExists();

        if (isAllFieldsChecked) {
            if  (!isStudentExists) {
                Aluno a = new Aluno();
                 a.setNome(editNome.getText().toString());
                 a.setLogin(editLogin.getText().toString());
                 a.setSenha(editSenha.getText().toString());
                long id = dao.inserir(a);

                limpaCampos();
                Toast.makeText(this, "Aluno inserido com sucesso!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void limpaCampos() {
        editNome.setText("");
        editLogin.setText("");
        editSenha.setText("");
    }

    private boolean CheckStudentExists() {
        try {
            bancoDados = openOrCreateDatabase("classRoomApp.db", MODE_PRIVATE, null);

            Cursor resultado = bancoDados.rawQuery("SELECT * FROM aluno where login = " + "'" +  editLogin.getText().toString() +  "'",null);

            if (resultado.moveToFirst()) {
                Toast.makeText(this, "O Aluno já esta cadastrado!", Toast.LENGTH_SHORT).show();
                return true;
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    private boolean CheckAllFields() {
        if (editNome.length() == 0) {
            editNome.setError("Campo Obrigatório");
            return false;
        }

        if (editLogin.length() == 0) {
            editLogin.setError("Campo Obrigatório");
            return false;
        }

        if (editSenha.length() == 0) {
            editSenha.setError("Campo Obrigatório");
            return false;
        }
        return true;
    }
}