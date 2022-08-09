package com.example.classroomapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Funcoes {
    private Conexao conexao;
    private SQLiteDatabase bancoDados;
    public String dtIncioSemana, dtFinalSemana;

    public Funcoes(Context context){
        conexao = new Conexao(context);
        bancoDados = conexao.getReadableDatabase();
    }

    public Integer CalculaQtdChechins(Integer alunoIdLogado, Integer idAula) {
        try {
            dtIncioSemana = retornaDiaInicialeDiaFinalSemana(true);
            dtFinalSemana = retornaDiaInicialeDiaFinalSemana(false);

            Cursor resultado = bancoDados.rawQuery("SELECT * FROM checkins a where" +
                    " a.idAluno = " + alunoIdLogado + " and a.idCadastroAulas = " + idAula +
                    " and a.dataCheckin BETWEEN " + "'" + dtIncioSemana + " 00:00:00" + "'" + " AND " + "'" + dtFinalSemana + " 23:59:00" + "'", null);

            if (resultado.moveToFirst()) {
                return resultado.getCount();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    private String retornaDiaInicialeDiaFinalSemana(boolean isPrimeiro) {

        //Seta a data atual.
        Date dataAtual = new Date();

        GregorianCalendar calendar = new GregorianCalendar();
        //Define que a semana começa no domingo.
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        //Define a data atual.
        calendar.setTime(dataAtual);

        if (isPrimeiro) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        } else {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        }

        SimpleDateFormat formatador = new SimpleDateFormat("yyyy-MM-dd");
        return formatador.format(calendar.getTime());
    }

    public Integer retornaQtdMaximaAula(Integer aulaIdSelecionada){
        try
        {
            Cursor resultado = bancoDados.rawQuery("SELECT qtdMaximaAlunos FROM cadastroAulas WHERE id = " + aulaIdSelecionada, null);

            if (resultado.moveToFirst()) {
                return resultado.getInt(0);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
