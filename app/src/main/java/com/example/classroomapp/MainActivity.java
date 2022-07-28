package com.example.classroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edtLogin,edtSenha;
    private TextView txtLogin,txtSenha;
    private Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtLogin = (TextView) findViewById(R.id.txtLogin);
        txtSenha = (TextView) findViewById(R.id.txtSenha);
        btnGo = (Button) findViewById(R.id.btnGo);
        edtLogin = (EditText) findViewById(R.id.edtLogin);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
    }

    public void clickBtnGo(View view) {
        if(view == btnGo)
            if(edtLogin.getText().toString().contains("Student")&&edtSenha.getText().toString().contains("123")){
                Toast.makeText(getApplicationContext(),"Login efetuado com sucesso", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intent);
            }
            else
                Toast.makeText(getApplicationContext(),"Login inválido", Toast.LENGTH_SHORT).show();

    }
}