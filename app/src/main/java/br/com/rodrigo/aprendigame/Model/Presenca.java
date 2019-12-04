package br.com.rodrigo.aprendigame.Model;

import android.content.ContentValues;

import br.com.rodrigo.aprendigame.DB.DBHelper;
import lombok.Data;

@Data
public class Presenca {

    private String id;
    private String data;
    private String Hora;
    private String Aula;

    public ContentValues getValues() {
        ContentValues valores = new ContentValues();
        valores.put(DBHelper.COLUNA_ID_PRESENCA, id);

        return valores;
    }

}
