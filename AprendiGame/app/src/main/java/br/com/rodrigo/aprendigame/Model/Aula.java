package br.com.rodrigo.aprendigame.Model;

public class Aula {

    private int idAula;
    private String nomeAula;
    private String professorAula;
    private String nomeCurso;

    public int getIdAula() {
        return idAula;
    }

    public void setIdAula(int idAula) {
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
}