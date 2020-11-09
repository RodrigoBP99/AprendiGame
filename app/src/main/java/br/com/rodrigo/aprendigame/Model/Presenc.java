package br.com.rodrigo.aprendigame.Model;

import android.content.ContentValues;

import java.io.Serializable;

import br.com.rodrigo.aprendigame.DB.DBHelper;
import lombok.Data;

@Data
public class Presenc implements Serializable {

    private String id;
    private String code;
    private CourseClass courseClass;
    private String date;
    private String hour;
    private Student student;
    private Long courseClassId;
    private String studentRegistration;

    public ContentValues getValues() {
        ContentValues valores = new ContentValues();
        valores.put(DBHelper.COLUNA_ID_PRESENCA, id);
        valores.put(DBHelper.COLUNA_DATA_PRESENCA, date);
        valores.put(DBHelper.COLUNA_HORA_PRESENCA, hour);
        valores.put(DBHelper.COLUNA_ID_AULA_PRESENCA, courseClassId);
        valores.put(DBHelper.COLUNA_MATRICULA_ALUNO, studentRegistration);

        return valores;
    }

}
