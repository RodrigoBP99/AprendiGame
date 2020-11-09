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
    public Student checkIfDataExists(){
        Student student = new Student();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        Cursor mCursor = sqLiteDatabase.query(TABLE_STUDENT, null, null, null, null, null, null);
        if (mCursor.getCount()  == 1 ) {
            if (mCursor.moveToFirst()) {
                student.setId(mCursor.getLong(mCursor.getColumnIndex(COLUN_ID_STUDENT)));
                student.setName(mCursor.getString(mCursor.getColumnIndex(COLUN_NOME_STUDENT)));
                student.setRegistration(mCursor.getString(mCursor.getColumnIndex(COLUN_REGISTRATION_STUDENT)));
                student.setSchoolName(mCursor.getString(mCursor.getColumnIndex(COLUN_SCHOOLNAME_STUDENT)));
                student.setPassword(mCursor.getString(mCursor.getColumnIndex(COLUN_PASSWORD_STUDENT)));
                student.setPhoto(mCursor.getString(mCursor.getColumnIndex(COLUN_PHOTO_STUDENT)));
                student.setBirthday(mCursor.getString(mCursor.getColumnIndex(COLUN_BIRTHDAY_STUDENT)));
                student.setPoints(mCursor.getDouble(mCursor.getColumnIndex(COLUN_POINTS_STUDENT)));
                student.setRequiredPoints(mCursor.getDouble(mCursor.getColumnIndex(COLUN_REQUIRED_POINTS_STUDENT)));
                student.setActualLevel(mCursor.getInt(mCursor.getColumnIndex(COLUN_ACTUAL_LEVEL_STUDENT)));
                student.setNextLevel(mCursor.getInt(mCursor.getColumnIndex(COLUN_NEXT_LEVEL_STUDENT)));
            }
        } else {
            clearStudent();
        }

        mCursor.close();
        return student;
    }

}
