package br.com.rodrigo.aprendigame.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String BANCO_NOME = "aprendigame";

    public static final String TABELA_NOME = "usuario";

    public static final String COLUNA_ID = "_ID";
    public static final String COLUNA_USERNAME = "USERNAME";
    public static final String COLUNA_NOME = "NOME";
    public static final String COLUNA_IDADE = "IDADE";
    public static final String COLUNA_TURMA= "TURMA";
    public static final String COLUNA_INSTITUICAO = "INSTITUICAO";
    public static final String COLUNA_EMAIL = "EMAIL";
    public static final String COLUNA_ENDERECO = "ENDERECO";
    public static final String COLUNA_SENHA = "SENHA";


    public static final String TABELA_PRESENCA = "presenca";

    public static final String COLUNA_ID_PRESENCA = "ID";
    public static final String COLUNA_DATA = "DATA";
    public static final String COLUNA_AULA = "AULA";
    public static final String COLUNA_PROFESSOR = "PROFESSOR";
    public static final String COLUNA_HORA = "HORA";



    private static final String CRIAR_TABELA = "CREATE TABLE " +
            TABELA_NOME + "( " +
            COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUNA_USERNAME + " TEXT UNIQUE, " + COLUNA_NOME + " TEXT, " +
            COLUNA_IDADE + " TEXT, " + COLUNA_TURMA + " TEXT, " +
            COLUNA_INSTITUICAO + " TEXT, " + COLUNA_EMAIL + " TEXT, " +
            COLUNA_ENDERECO + " TEXT, " + COLUNA_SENHA + " TEXT)";


    private static final String CRIAR_TABELA_PRESENCA = "CREATE TABLE " +
            TABELA_PRESENCA + "( " +
            COLUNA_ID_PRESENCA + " TEXT PRIMARY KEY, " +
            COLUNA_DATA + " TEXT, " +
            COLUNA_AULA + " TEXT, " +
            COLUNA_PROFESSOR + " TEXT, " +
            COLUNA_HORA + " TEXT)";

    public DBHelper(Context context) {
        super(context, BANCO_NOME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRIAR_TABELA);
        db.execSQL(CRIAR_TABELA_PRESENCA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
