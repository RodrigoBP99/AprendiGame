package br.com.rodrigo.aprendigame.Model;

import android.content.ContentValues;

import br.com.rodrigo.aprendigame.DB.DBHelper;

public class Usuario {

    private Integer id;
    private String matricula;
    private String nome;
    private String idade;
    private String turma;
    private String instituicao;
    private String email;
    private String endereco;
    private String senha;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public ContentValues getValues() {
        ContentValues valores = new ContentValues();
        valores.put(DBHelper.COLUNA_USERMATRICULA, matricula);
        valores.put(DBHelper.COLUNA_NOME, nome);
        valores.put(DBHelper.COLUNA_IDADE, idade);
        valores.put(DBHelper.COLUNA_TURMA, turma);
        valores.put(DBHelper.COLUNA_INSTITUICAO, instituicao);
        valores.put(DBHelper.COLUNA_EMAIL, email);
        valores.put(DBHelper.COLUNA_ENDERECO, endereco);
        valores.put(DBHelper.COLUNA_SENHA, senha);

        return valores;
    }
}
