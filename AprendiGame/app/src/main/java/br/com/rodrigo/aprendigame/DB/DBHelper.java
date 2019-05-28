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


    private static final String CRIAR_TABELA = "CREATE TABLE " +
            TABELA_NOME + "( " +
            COLUNA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUNA_USERNAME + " TEXT UNIQUE, " + COLUNA_NOME + " TEXT, " +
            COLUNA_IDADE + " TEXT, " + COLUNA_TURMA + " TEXT, " +
            COLUNA_INSTITUICAO + " TEXT, " + COLUNA_EMAIL + " TEXT, " +
            COLUNA_ENDERECO + " TEXT, " + COLUNA_SENHA + " TEXT)";


    public DBHelper(Context context) {
        super(context, BANCO_NOME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CRIAR_TABELA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
