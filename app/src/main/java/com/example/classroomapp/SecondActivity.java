package com.example.classroomapp;

import androidx.appcompat.app.AppCompatActivity;

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

 /*       try {
            bancoDados = openOrCreateDatabase("classRoomApp", MODE_PRIVATE, null);

            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS Professor(CPF Varchar(11),\n" +
                    "  nome Varchar(100),\n" +
                    "  dataNascimento Date,\n" +
                    "  PRIMARY KEY (CPF))");

            bancoDados.execSQL("INSERT INTO Professor(CPF, nome, dataNascimento) VALUES ('55448384102', 'Ana','02/02/1988')");

            Cursor cProf = bancoDados.rawQuery("SELECT CPF FROM Professor", null);

            int incideCPF = cProf.getColumnIndex("CPF");
            //   int indiceNome = cProf.getColumnIndex("nome");

            cProf.moveToFirst();

            while (cProf != null) {
                // Log.i("Resultado - CPF: ", cProf.getString(incideCPF));
                textSemana.append("Aqui: " + cProf.getString(incideCPF));

                cProf.moveToNext();
            }

            //  bancoDados.execSQL("INSERT INTO Professor(CPF, nome, dataNascimento) VALUES ('12312153726', 'Sabrina','01/01/1988')");
            //  bancoDados.execSQL("INSERT INTO Professor(CPF, nome, dataNascimento) VALUES ('55448384102', 'Ana','02/02/1988')");
            //  bancoDados.execSQL("INSERT INTO Professor(CPF, nome, dataNascimento) VALUES ('78591344383', 'Francine','03/03/1988')");

            //bancoDados.execSQL("INSERT INTO CadastroAulas()" */

            /*        bancoDados.execSQL("CREATE TABLE IF NOT EXISTS CadastroAulas(codCadastroAulas Integer,\n" +
                    "  CPFProfessor Varchar(11),\n" +
                    "  diaDaSemana Varchar(100),\n" +
                    "  descAula Varchar(100),\n" +
                    "  horarioAula Time,\n" +
                    "  qtdMaximaAlunos Integer,\n" +
                    "  PRIMARY KEY (codCadastroAulas),\n" +
                    "  CONSTRAINT CPFProfessor FOREIGN KEY (CPFProfessor) REFERENCES Professor(CPF))");

            bancoDados.execSQL("INSERT INTO CadastroAulas(codCadastroAulas, CPFProfessor, diaDaSemana, descAula, horarioAula, qtdMaximaAlunos)\n" +
            " VALUES (1, 12312153726, 'Segunda', 'GENERAL ENGLISH', '18:00', 4)");

            Cursor cursor = bancoDados.rawQuery("SELECT * FROM CadastroAulas ", null);

            int indiceCodAula = cursor.getColumnIndex("codCadastroAulas");
            int indiceCPFProfessor = cursor.getColumnIndex("CPFProfessor");
            int indicediaDaSemanaa = cursor.getColumnIndex("diaDaSemana");
            int indicedescAula = cursor.getColumnIndex("descAula");
            int indicehorarioAula = cursor.getColumnIndex("horarioAula");
            int indiceqtdMaximaAlunos = cursor.getColumnIndex("qtdMaximaAlunos");

            cursor.moveToFirst();

            while (cursor!=null){
                Log.i("Resultado - Aula: ", cursor.getString(indicedescAula));

                cursor.moveToNext();
            }

            bancoDados.close();
        } catch (Exception e) {
            e.printStackTrace();
        } */

        ListViewAulas = (ListView) findViewById(R.id.ListViewAulas);

        criarBancoDados();
        inserirAulas();
        ListarAulas();
        selecionaAula();

    }

    private void selecionaAula() {
        ListViewAulas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Toast.makeText(getApplicationContext(),
                        "Clicou no item " + position, Toast.LENGTH_LONG).show();


                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                intent.putExtra("Chave", "OI");

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
            bancoDados = openOrCreateDatabase("classRoomApp", MODE_PRIVATE, null);

            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS CadastroAulas(" +
                            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "descAula VARCHAR)");
            bancoDados.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void inserirAulas() {
        try {
            bancoDados = openOrCreateDatabase("classRoomApp", MODE_PRIVATE, null);

            Cursor resultado = bancoDados.rawQuery("SELECT * FROM CadastroAulas", null);

            if (!resultado.moveToFirst()) {
                String sql = "INSERT INTO CadastroAulas(descAula) VALUES (?)";
                SQLiteStatement stmt = bancoDados.compileStatement(sql);

                stmt.bindString(1, "GENERAL ENGLISH");
                stmt.executeInsert();

                stmt.bindString(1, "CONVERSATION");
                stmt.executeInsert();

                stmt.bindString(1, "BUSINESS ENGLISH");
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
            bancoDados = openOrCreateDatabase("classRoomApp", MODE_PRIVATE, null);

            Cursor cListaAulas =  bancoDados.rawQuery("SELECT id, descAula FROM CadastroAulas", null);

            ArrayList<String> linhas = new ArrayList<String>();

            ArrayAdapter meuAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                linhas
            );

            ListViewAulas.setAdapter(meuAdapter);

            cListaAulas.moveToFirst();
            while (cListaAulas!=null){
                linhas.add(cListaAulas.getString(1));
                cListaAulas.moveToNext();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}