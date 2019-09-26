package br.com.rodrigo.aprendigame.Model;

import android.content.ContentValues;

import br.com.rodrigo.aprendigame.DB.DBHelper;

public class Aula {

    private String idAula;
    private String nomeAula;
    private String professorAula;
    private String nomeCurso;

    public String getIdAula() {
        return idAula;
    }

    public void setIdAula(String idAula) {
        this.idAula = idAula;
    }

    public String getNomeAula() {
        return nomeAula;
    }

    public void setNomeAula(String nomeAula) {
        this.nomeAula = nomeAula;
    }

    public String getProfessorAula() {
        return professorAula;
    }

    public void setProfessorAula(String professorAula) {
        this.professorAula = professorAula;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }

    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public ContentValues getValues(){
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUNA_ID_ALUNO, idAula);
        values.put(DBHelper.COLUNA_TITULO_AULA, nomeAula);
        values.put(DBHelper.COLUNA_PROFESSOR_PRESENCA, professorAula);
        values.put(DBHelper.COLUNA_CURSO_AULA, nomeCurso);

        return values;
    }
}