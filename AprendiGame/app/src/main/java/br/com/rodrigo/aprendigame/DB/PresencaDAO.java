package br.com.rodrigo.aprendigame.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Model.Presenca;

public class PresencaDAO {

    private SQLiteDatabase db;

    public PresencaDAO(Context context){
        DBHelper auxBD = new DBHelper(context);
        db = auxBD.getWritableDatabase();
    }

    public void inserirPresenca(Presenca presenca){
        ContentValues values = new ContentValues();

        values.put("ID", presenca.getId());
        values.put("DATA", presenca.getData());
        values.put("AULA", presenca.getAula());
        values.put("PROFESSOR", presenca.getProfessor());
        values.put("HORA", presenca.getHora());

        db.insert("presenca", null, values);
    }

    public void limparPresencas(){
        db.execSQL("delete from " + "presenca");
        db.close();
    }

    public List<Presenca> buscarListaPresenca(){
        List<Presenca> list = new ArrayList<>();
        String[] colunas = new String[]{"ID", "DATA", "AULA", "PROFESSOR", "HORA"};

        Cursor cursor = db.query("presenca", colunas, null, null, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
        do {
            Presenca p = new Presenca();
            p.setId(cursor.getString(0));
            p.setData(cursor.getString(1));
            p.setAula(cursor.getString(2));
            p.setProfessor(cursor.getString(3));
            p.setHora(cursor.getString(4));

            list.add(p);
          }while (cursor.moveToNext());
        }
        return(list);
    }
}
