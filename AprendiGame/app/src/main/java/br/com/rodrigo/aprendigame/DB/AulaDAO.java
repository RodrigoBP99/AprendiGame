package br.com.rodrigo.aprendigame.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Model.Aula;

public class AulaDAO extends DBHelper {
    public AulaDAO(Context context) {
        super(context);
    }

    public void inserirAula(Aula aula){
        getWritableDatabase().insert(TABELA_AULA, null, aula.getValues());
    }

    public List<Aula> getListAula(){
        List<Aula> listAula = new ArrayList<>();
        String[] coluns = new String[]{COLUNA_ID_AULA, COLUNA_TITULO_AULA, COLUNA_PROFESSOR_PRESENCA, COLUNA_CURSO_AULA};

        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.query(TABELA_AULA, coluns, null, null, null, null, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Aula a = new Aula();
                a.setIdAula(cursor.getString(cursor.getColumnIndex(COLUNA_ID_AULA)));
                a.setNomeAula(cursor.getString(cursor.getColumnIndex(COLUNA_TITULO_AULA)));
                a.setProfessorAula(cursor.getString(cursor.getColumnIndex(COLUNA_PROFESSOR_PRESENCA)));
                a.setNomeCurso(cursor.getString(cursor.getColumnIndex(COLUNA_CURSO_AULA)));

                listAula.add(a);
            }while (cursor.moveToNext());
        }
        return(listAula);
    }
}
