package br.com.rodrigo.aprendigame.Model;

public class Presenca {

    private String aula;
    private String professor;
    private String data;

    public Presenca(String data, String professor, String aula) {
        this.aula = aula;
        this.professor = professor;
        this.data = data;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
