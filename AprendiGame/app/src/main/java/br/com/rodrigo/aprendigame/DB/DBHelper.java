package br.com.rodrigo.aprendigame.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String BANCO_NOME = "aprendigame";


    public static final String TABELA_USUARIO = "usuario";

    public static final String COLUNA_ID_USER = "ID";
    public static final String COLUNA_NOME_USER = "NOME";
    public static final String COLUNA_IDADE_USER = "IDADE";
    public static final String COLUNA_TURMA_USER = "TURMA";
    public static final String COLUNA_INSTITUICAO_USER = "INSTITUICAO";
    public static final String COLUNA_EMAIL_USER = "EMAIL";
    public static final String COLUNA_ENDERECO_USER = "ENDERECO";
    public static final String COLUNA_SENHA_USER = "SENHA";


    public static final String TABELA_PRESENCA = "presenca";

    public static final String COLUNA_ID_PRESENCA = "ID";
    public static final String COLUNA_DATA_PRESENCA = "DATA";
    public static final String COLUNA_AULA_PRESENCA = "AULA";
    public static final String COLUNA_PROFESSOR_PRESENCA = "PROFESSOR";
    public static final String COLUNA_ID_ALUNO = "IDALUNO";
    public static final String COLUNA_HORA_PRESENCA = "HORA";


    public static final String TABELA_AULA = "aula";

    public static final String COLUNA_ID_AULA = "ID";
    public static final String COLUNA_TITULO_AULA = "TITULO";
    public static final String COLUNA_CURSO_AULA = "CURSO";

    private static final String CREATE_TABLE_USUARIO = "CREATE TABLE " +
            TABELA_USUARIO + "( " +
            COLUNA_ID_USER + " TEXT PRIMARY KEY, " +
            COLUNA_NOME_USER + " TEXT, " +
            COLUNA_IDADE_USER + " TEXT, " +
            COLUNA_TURMA_USER + " TEXT, " +
            COLUNA_INSTITUICAO_USER + " TEXT, " +
            COLUNA_EMAIL_USER + " TEXT, " +
            COLUNA_ENDERECO_USER + " TEXT, " +
            COLUNA_SENHA_USER + " TEXT)";

    private static final String CREATE_TABLE_PRESENCA = "CREATE TABLE " +
            TABELA_PRESENCA + "( " +
            COLUNA_ID_PRESENCA + " TEXT PRIMARY KEY, " +
            COLUNA_DATA_PRESENCA + " TEXT, " +
            COLUNA_AULA_PRESENCA + " TEXT, " +
            COLUNA_ID_ALUNO + " TEXT, " +
            COLUNA_PROFESSOR_PRESENCA + " TEXT, " +
            COLUNA_HORA_PRESENCA + " TEXT)";

    private static final String CREATE_TABLE_AULA = "CREATE TABLE " +
            TABELA_AULA + "( " +
            COLUNA_ID_AULA + " TEXT PRIMARY KEY, " +
            COLUNA_TITULO_AULA + " TEXT, " +
            COLUNA_PROFESSOR_PRESENCA + " TEXT, " +
            COLUNA_CURSO_AULA + " TEXT)";

    public DBHelper(Context context) {
        super(context, BANCO_NOME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USUARIO);
        db.execSQL(CREATE_TABLE_PRESENCA);
        db.execSQL(CREATE_TABLE_AULA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PRESENCA);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_USUARIO);
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_AULA);
        onCreate(db);
    }
}
