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
