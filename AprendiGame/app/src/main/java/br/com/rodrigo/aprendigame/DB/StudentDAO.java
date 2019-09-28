package br.com.rodrigo.aprendigame.DB;

import android.content.Context;

import br.com.rodrigo.aprendigame.Model.Student;


public class StudentDAO extends DBHelper {

    public StudentDAO(Context applicationContext) {
        super(applicationContext);
    }

    public void salvarUsuario(Student student) {
        getWritableDatabase().insert(TABELA_STUDENT, null, student.getValues());
    }

}
