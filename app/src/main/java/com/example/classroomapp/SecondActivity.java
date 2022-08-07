package com.example.classroomapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SecondActivity extends AppCompatActivity {

    private TextView textSemana;
    public ListView ListViewAulas;
    private SQLiteDatabase bancoDados;
    private Context context;
    public ArrayList<String> linhas;
    public Integer alunoIdLogado;

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textSemana = (TextView) findViewById(R.id.textSemana);
        carregaTextSemana();

        ListViewAulas = (ListView) findViewById(R.id.ListViewAulas);

        Intent intent = getIntent();
        Integer alunoIdLogado = intent.getIntExtra("alunoIdLogado", 0);

        criarBancoDados();
        inserirProfessores();
        inserirAulas();
        ListarAulas();

        ListViewAulas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                intent.putExtra("selecionado", linhas.get(position));
                intent.putExtra("alunoIdLogado", alunoIdLogado);

                startActivity(intent);
            }
        });

    }

    private void carregaTextSemana() {
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Format mesAtual = new SimpleDateFormat("MM");

        int semanaAtual = cal.get(Calendar.WEEK_OF_MONTH);

        textSemana.setText("  Mês: " + mesAtual.format((cal.getTime())) + "/Semana: " + Integer.toString(semanaAtual));
    }

    private void criarBancoDados() {
        try{
            bancoDados = openOrCreateDatabase("classRoomApp.db", MODE_PRIVATE, null);

            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS professor(" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "  nome Varchar(100))");

            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS cadastroAulas(" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "diaDaSemana VARCHAR," +
                            "horarioAula Time," +
                            "descAula VARCHAR," +
                            "qtdMaximaAlunos INTEGER," +
                            "idProfessor INTEGER," +
                            "FOREIGN KEY(idProfessor) REFERENCES professor(id))");

            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS checkins(" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "   idAluno INTEGER," +
                    "   idCadastroAulas INTEGER," +
                    "   dataCheckin DATETIME DEFAULT CURRENT_TIMESTAMP, " +
                    "   FOREIGN KEY(idAluno) REFERENCES aluno(id)," +
                    "   FOREIGN KEY(idCadastroAulas) REFERENCES cadastroAulas(id))");

            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void inserirProfessores() {
        try {
            bancoDados = openOrCreateDatabase("classRoomApp.db", MODE_PRIVATE, null);

            Cursor resultado = bancoDados.rawQuery("SELECT * FROM professor", null);

            if (!resultado.moveToFirst()) {
                String sql = "INSERT INTO professor(nome) VALUES (?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);

                stmt.bindString(1, "Sandra");
                stmt.executeInsert();

                stmt.bindString(1, "Regina");
                stmt.executeInsert();
            }
            bancoDados.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void inserirAulas() {
        try {
            bancoDados = openOrCreateDatabase("classRoomApp.db", MODE_PRIVATE, null);

            Cursor resultado = bancoDados.rawQuery("SELECT * FROM cadastroAulas", null);

            if (!resultado.moveToFirst()) {
                String sql = "INSERT INTO cadastroAulas(diaDaSemana, horarioAula, descAula, qtdMaximaAlunos, idProfessor) VALUES (?, ?, ?, ?, ?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);

                stmt.bindString(1,"Segunda");
                stmt.bindString(2, "18:00");
                stmt.bindString(3,"GENERAL ENGLISH");
                stmt.bindString(4, "4");
                stmt.bindString(5, "1");
                stmt.executeInsert();

                stmt.bindString(1,"Segunda");
                stmt.bindString(2, "20:00");
                stmt.bindString(3, "CONVERSATION");
                stmt.bindString(4, "4");
                stmt.bindString(5, "2");
                stmt.executeInsert();

                stmt.bindString(1,"Quarta");
                stmt.bindString(2, "18:00");
                stmt.bindString(3, "BUSINESS ENGLISH");
                stmt.bindString(4, "4");
                stmt.bindString(5, "2");
                stmt.executeInsert();

                stmt.bindString(1,"Quarta");
                stmt.bindString(2, "20:00");
                stmt.bindString(3, "GENERAL ENGLISH");
                stmt.bindString(4, "4");
                stmt.bindString(5, "1");
                stmt.executeInsert();
            }

            bancoDados.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    };

    private void ListarAulas() {
        try{
            bancoDados = openOrCreateDatabase("classRoomApp.db", MODE_PRIVATE, null);

            Cursor cListaAulas =  bancoDados.rawQuery("SELECT a.id, a.diaDaSemana, a.horarioAula, a.descAula, b.nome, a.qtdMaximaAlunos " +
                                                          "FROM cadastroAulas a LEFT JOIN professor b WHERE a.idProfessor = b.id", null);

          linhas = new ArrayList<String>();

            ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                linhas
            );

            ListViewAulas.setAdapter(meuAdapter);

            cListaAulas.moveToFirst();
            while (cListaAulas!=null){
                linhas.add(cListaAulas.getString(0) + " - " +
                           cListaAulas.getString(1) + " - " +
                           cListaAulas.getString(2) + " - " +
                           cListaAulas.getString(3) + " - " +
                           cListaAulas.getString(4) + " - " +
                           "Vagas: " + cListaAulas.getString(5));
                cListaAulas.moveToNext();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}