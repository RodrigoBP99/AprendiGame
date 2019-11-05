package br.com.rodrigo.aprendigame.Model;

import android.content.ContentValues;

import br.com.rodrigo.aprendigame.DB.DBHelper;

public class Presenca {

    private String id;
    private String aula;
    private String professor;
    private String idAluno;
    private String data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(String idAluno) {
        this.idAluno = idAluno;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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
