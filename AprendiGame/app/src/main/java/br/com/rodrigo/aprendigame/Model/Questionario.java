package br.com.rodrigo.aprendigame.Model;

public class Questionario {

    private String tituloQuestionario;
    private String autorQuestionario;
    private String materiaQuestionario;
    private String notaQuestionario;


    public Questionario(String tituloQuestionario, String autorQuestionario, String materiaQuestionario, String notaQuestionario){
        setTituloQuestionario(tituloQuestionario);
        setAutorQuestionario("Autor: " + autorQuestionario);
        setMateriaQuestionario("Materia: " + materiaQuestionario);
        setNotaQuestionario(notaQuestionario);
    }


    public String getMateriaQuestionario() {
        return materiaQuestionario;
    }

    public void setMateriaQuestionario(String materiaQuestionario) {
        this.materiaQuestionario = materiaQuestionario;
    }

    public String getAutorQuestionario() {
        return autorQuestionario;
    }

    public void setAutorQuestionario(String autorQuestionario) {
        this.autorQuestionario = autorQuestionario;
    }

    public String getTituloQuestionario() {
        return tituloQuestionario;
    }

    public void setTituloQuestionario(String tituloQuestionario) {
        this.tituloQuestionario = tituloQuestionario;
    }

    public String getNotaQuestionario() {
        return notaQuestionario;
    }

    public void setNotaQuestionario(String notaQuestionario) {
        this.notaQuestionario = notaQuestionario;
    }
}
