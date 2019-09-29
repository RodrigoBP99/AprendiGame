package br.com.rodrigo.aprendigame.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Model.Presenca;

public class PresencaDAO extends DBHelper{

    public PresencaDAO(Context applicationContext) {
        super(applicationContext);
    }

    public void inserirPresenca(Presenca presenca) {
        getWritableDatabase().insert(TABELA_PRESENCA, null, presenca.getValues());
    }

    public void limparPresencas(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + TABELA_PRESENCA);
        db.close();
    }

    public List<Presenca> buscarPresencaAula(String idAula){
        List<Presenca> list = new ArrayList<>();
        String[] colunas = new String[]{COLUNA_ID_PRESENCA, COLUNA_DATA_PRESENCA, COLUNA_AULA_PRESENCA, COLUNA_PROFESSOR_PRESENCA, COLUNA_ID_ALUNO, COLUNA_HORA_PRESENCA};
        String selection = COLUNA_AULA_PRESENCA + " = ?";
        String[] selectionArgs = {idAula};

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.query(TABELA_PRESENCA, colunas, selection, selectionArgs, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Presenca p = new Presenca();
                p.setId(cursor.getString(cursor.getColumnIndex(COLUNA_ID_PRESENCA)));
                p.setAula(cursor.getString(cursor.getColumnIndex(COLUNA_AULA_PRESENCA)));
                p.setProfessor(cursor.getString(cursor.getColumnIndex(COLUNA_PROFESSOR_PRESENCA)));
                p.setIdAluno(cursor.getString(cursor.getColumnIndex(COLUNA_ID_ALUNO)));

                //p.setData(cursor.getString(cursor.getColumnIndex(COLUNA_DATA_PRESENCA)));

                list.add(p);
            }while (cursor.moveToNext());
        }
        return(list);
    }

    public boolean checkScannedPresenca(String arrayPresenca) {
        String[] collumns = {COLUNA_ID_PRESENCA};

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUNA_ID_PRESENCA + " = ? ";
        String[] selectionArgs = {arrayPresenca};

        Cursor cursor = db.query(TABELA_PRESENCA, collumns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();

        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public int checkTableSize(){
        String count = "SELECT count(*) FROM " + TABELA_PRESENCA;

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery(count, null);
        cursor.moveToFirst();

        int iCount = cursor.getInt(0);

        return iCount;
    }
}
