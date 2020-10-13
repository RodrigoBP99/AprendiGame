package br.com.rodrigo.aprendigame.DB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.rodrigo.aprendigame.Model.Student;


public class StudentDAO extends DBHelper {

    public StudentDAO(Context applicationContext) {
        super(applicationContext);
    }

    public void salvarUsuario(Student student) {
        getWritableDatabase().insert(TABLE_STUDENT, null, student.getValues());
    }

    public void clearStudent(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from " + TABLE_STUDENT);
        db.close();
    }

    public Student selectUsuario(String phoneNumber){
        Student student = new Student();
        SQLiteDatabase db = this.getWritableDatabase();

        String[] collumns = {COLUN_ID_STUDENT, COLUN_NOME_STUDENT, COLUN_REGISTRATION_STUDENT, COLUN_PASSWORD_STUDENT, COLUN_COURSE_STUDENT, COLUN_PHOTO_STUDENT, COLUN_BIRTHDAY_STUDENT,
                COLUN_POINTS_STUDENT, COLUN_REQUIRED_POINTS_STUDENT, COLUN_ACTUAL_LEVEL_STUDENT, COLUN_NEXT_LEVEL_STUDENT};
        String selection = COLUN_ID_STUDENT + " = ?";
        String[] selectionArgs = {phoneNumber};

        Cursor cursor = db.query(TABLE_STUDENT, collumns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()){
            student.setId(cursor.getLong(cursor.getColumnIndex(COLUN_ID_STUDENT)));
            student.setName(cursor.getString(cursor.getColumnIndex(COLUN_NOME_STUDENT)));
            student.setRegistration(cursor.getString(cursor.getColumnIndex(COLUN_REGISTRATION_STUDENT)));
            student.setPassword(cursor.getString(cursor.getColumnIndex(COLUN_PASSWORD_STUDENT)));
            student.setCourse(cursor.getString(cursor.getColumnIndex(COLUN_COURSE_STUDENT)));
            student.setPhoto(cursor.getString(cursor.getColumnIndex(COLUN_PHOTO_STUDENT)));
            student.setBirthday(cursor.getString(cursor.getColumnIndex(COLUN_BIRTHDAY_STUDENT)));
            student.setPoints(cursor.getDouble(cursor.getColumnIndex(COLUN_POINTS_STUDENT)));
            student.setRequiredPoints(cursor.getDouble(cursor.getColumnIndex(COLUN_REQUIRED_POINTS_STUDENT)));
            student.setActualLevel(cursor.getInt(cursor.getColumnIndex(COLUN_ACTUAL_LEVEL_STUDENT)));
            student.setNextLevel(cursor.getInt(cursor.getColumnIndex(COLUN_NEXT_LEVEL_STUDENT)));
        }
        cursor.close();

        return student;
    }

}
