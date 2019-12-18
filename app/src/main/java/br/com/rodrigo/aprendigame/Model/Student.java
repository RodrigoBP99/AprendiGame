package br.com.rodrigo.aprendigame.Model;

import android.content.ContentValues;

import java.io.Serializable;
import java.util.Date;

import br.com.rodrigo.aprendigame.DB.DBHelper;
import lombok.Data;

@Data
public class Student implements Serializable {

    private Long id;
    private String name;
    private String course;
    private String photo;
    private Date birthday;
    private double points;
    private double requiredPoints;
    private int actualLevel;
    private int nextLevel;

    public ContentValues getValues() {
        ContentValues valores = new ContentValues();
        valores.put(DBHelper.COLUN_ID_STUDENT, id);
        valores.put(DBHelper.COLUN_NOME_STUDENT, name);
        valores.put(DBHelper.COLUN_COURSE_STUDENT, course);
        valores.put(DBHelper.COLUN_PHOTO_STUDENT, photo);
        valores.put(DBHelper.COLUN_POINTS_STUDENT, points);
        valores.put(DBHelper.COLUN_REQUIRED_POINTS_STUDENT, requiredPoints);
        valores.put(DBHelper.COLUN_ACTUAL_LEVEL_STUDENT, actualLevel);
        valores.put(DBHelper.COLUN_NEXT_LEVEL_STUDENT, nextLevel);

        return valores;
    }
}
