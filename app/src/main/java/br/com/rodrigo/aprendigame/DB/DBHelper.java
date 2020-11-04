package br.com.rodrigo.aprendigame.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String BANCO_NOME = "aprendigame";

    public static final String TABLE_STUDENT = "student";
    public static final String COLUN_ID_STUDENT = "ID";
    public static final String COLUN_NOME_STUDENT = "NAME";
    public static final String COLUN_REGISTRATION_STUDENT = "REGISTRATION";
    public static final String COLUN_PASSWORD_STUDENT = "PASSWORD";
    public static final String COLUN_COURSE_STUDENT = "COURSE";
    public static final String COLUN_PHOTO_STUDENT = "PHOTO";
    public static final String COLUN_BIRTHDAY_STUDENT = "BIRTHDAY";
    public static final String COLUN_POINTS_STUDENT = "POINTS";
    public static final String COLUN_REQUIRED_POINTS_STUDENT = "REQUIREDPOINTS";
    public static final String COLUN_ACTUAL_LEVEL_STUDENT = "ACTUALLEVEL";
    public static final String COLUN_NEXT_LEVEL_STUDENT = "NEXTLEVEL";

    public static final String TABELA_PRESENCA = "presenca";
    public static final String COLUNA_ID_PRESENCA = "ID";
    public static final String COLUNA_DATA_PRESENCA = "DATA";
    public static final String COLUNA_ID_AULA_PRESENCA = "AULA";
    public static final String COLUNA_MATRICULA_ALUNO = "IDALUNO";
    public static final String COLUNA_HORA_PRESENCA = "HORA";


    private static final String CREATE_TABLE_STUDENT = "CREATE TABLE " +
            TABLE_STUDENT + "( " +
            COLUN_ID_STUDENT + " INTEGER PRIMARY KEY, " +
            COLUN_NOME_STUDENT + " TEXT, " +
            COLUN_REGISTRATION_STUDENT + " TEXT, " +
            COLUN_PASSWORD_STUDENT + " TEXT, " +
            COLUN_PHOTO_STUDENT + " TEXT, " +
            COLUN_BIRTHDAY_STUDENT + " TEXT," +
            COLUN_POINTS_STUDENT + " REAL, " +
            COLUN_REQUIRED_POINTS_STUDENT + " REAL, " +
            COLUN_ACTUAL_LEVEL_STUDENT + " INTEGER, " +
            COLUN_NEXT_LEVEL_STUDENT + " INTEGER)";

    private static final String CREATE_TABLE_PRESENCA = "CREATE TABLE " +
            TABELA_PRESENCA + "( " +
            COLUNA_ID_PRESENCA + " TEXT PRIMARY KEY, " +
            COLUNA_DATA_PRESENCA + " TEXT, " +
            COLUNA_ID_AULA_PRESENCA + " TEXT, " +
            COLUNA_MATRICULA_ALUNO + " TEXT, " +
            COLUNA_HORA_PRESENCA + " TEXT)";

    public DBHelper(Context context) {
        super(context, BANCO_NOME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_STUDENT);
        db.execSQL(CREATE_TABLE_PRESENCA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_PRESENCA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        onCreate(db);
    }
}
