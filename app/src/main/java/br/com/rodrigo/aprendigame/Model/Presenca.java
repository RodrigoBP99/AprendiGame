package br.com.rodrigo.aprendigame.Model;

import android.content.ContentValues;

import br.com.rodrigo.aprendigame.DB.DBHelper;
import lombok.Data;

@Data
public class Presenca {

    private String id;
    private String aula;
    private String professor;
    private String idAluno;
    private String data;

    public ContentValues getValues() {
        ContentValues valores = new ContentValues();
        valores.put(DBHelper.COLUNA_ID_PRESENCA, id);
        valores.put(DBHelper.COLUNA_AULA_PRESENCA, aula);
        valores.put(DBHelper.COLUNA_PROFESSOR_PRESENCA, professor);
        valores.put(DBHelper.COLUNA_ID_ALUNO, idAluno);
        valores.put(DBHelper.COLUNA_DATA_PRESENCA, data);

        return valores;
    }

}
