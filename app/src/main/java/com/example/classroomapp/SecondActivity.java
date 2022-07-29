package com.example.classroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class SecondActivity extends AppCompatActivity {

    private TextView textSemana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textSemana = (TextView) findViewById(R.id.textSemana);

        Calendar cal = Calendar.getInstance();
        cal.set(2022, 07, 27);
        int semanaAtual = cal.get(Calendar.WEEK_OF_MONTH);
        textSemana.setText(" Semana: " + Integer.toString(semanaAtual));

        try {
            SQLiteDatabase bancoDados = openOrCreateDatabase("classRoomApp", MODE_PRIVATE, null);

            bancoDados.execSQL("CREATE TABLE IF NOT EXISTS Professor(CPF Varchar(11),\n" +
                    "  nome Varchar(100),\n" +
                    "  dataNascimento Date,\n" +
                    "  PRIMARY KEY (CPF))");

            bancoDados.execSQL("INSERT INTO Professor(CPF, nome, dataNascimento) VALUES ('55448384102', 'Ana','02/02/1988')");

            Cursor cProf = bancoDados.rawQuery("SELECT CPF FROM Professor", null);

            int incideCPF = cProf.getColumnIndex("CPF");
         //   int indiceNome = cProf.getColumnIndex("nome");

            cProf.moveToFirst();

            while (cProf!=null){
               // Log.i("Resultado - CPF: ", cProf.getString(incideCPF));
                textSemana.append("Aqui: " + cProf.getString(incideCPF));

                cProf.moveToNext();
            }

          //  bancoDados.execSQL("INSERT INTO Professor(CPF, nome, dataNascimento) VALUES ('12312153726', 'Sabrina','01/01/1988')");
          //  bancoDados.execSQL("INSERT INTO Professor(CPF, nome, dataNascimento) VALUES ('55448384102', 'Ana','02/02/1988')");
          //  bancoDados.execSQL("INSERT INTO Professor(CPF, nome, dataNascimento) VALUES ('78591344383', 'Francine','03/03/1988')");

            //bancoDados.execSQL("INSERT INTO CadastroAulas()"

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
            } */
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}