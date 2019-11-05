package br.com.rodrigo.aprendigame.Model;

import android.content.ContentValues;

import java.io.Serializable;

import br.com.rodrigo.aprendigame.DB.DBHelper;
import lombok.Data;

@Data
public class Student implements Serializable {

    private Long id;
    private String name;
    private String course;
    private String photo;
    private double points;
    private double requiredPoints;
    private int actualLevel;
    private int nextLevel;

    public ContentValues getValues() {
        ContentValues valores = new ContentValues();
        valores.put(DBHelper.COLUNA_ID_STUDENT, id);

        return valores;
    }
}
