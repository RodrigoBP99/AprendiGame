package br.com.rodrigo.aprendigame.Model;

import android.content.ContentValues;

import java.io.Serializable;

import br.com.rodrigo.aprendigame.DB.DBHelper;
import lombok.Data;

@Data
public class Presenc implements Serializable {

    private String id;
    private String date;

    public ContentValues getValues() {
        ContentValues valores = new ContentValues();
        valores.put(DBHelper.COLUNA_ID_PRESENCA, id);
        valores.put(DBHelper.COLUNA_DATA_PRESENCA, date);

        return valores;
    }

}
