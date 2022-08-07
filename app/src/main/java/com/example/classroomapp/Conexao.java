package com.example.classroomapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Conexao extends SQLiteOpenHelper {

    private static final String name = "classRoomApp.db";
    private static final int version = 1;

    public Conexao(Context context){
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE IF NOT EXISTS aluno(id integer PRIMARY KEY AUTOINCREMENT, " +
                "nome varchar(50), login varchar(50), senha varchar(50))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){

    }
}
