package br.com.rodrigo.aprendigame.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Model.Usuario;


public class UsuarioDAO extends DBHelper {

    public UsuarioDAO(Context applicationContext) {
        super(applicationContext);
    }

    public void salvar(Usuario usuario){
        if (usuario.getId() == null){
            getWritableDatabase().insert(TABELA_NOME, null, usuario.getValues());
        } else {
            getWritableDatabase().update(TABELA_NOME, usuario.getValues(), "_ID = ?", new String[]{usuario.getId().toString()});
        }
    }

    private Usuario novoUsuario(Cursor cursor){
        Usuario usuario = new Usuario();
        usuario.setUserName(cursor.getString(cursor.getColumnIndex(COLUNA_USERNAME)));
        usuario.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
        usuario.setIdade(cursor.getString(cursor.getColumnIndex(COLUNA_IDADE)));
        usuario.setTurma(cursor.getString(cursor.getColumnIndex(COLUNA_TURMA)));
        usuario.setInstituicao(cursor.getString(cursor.getColumnIndex(COLUNA_INSTITUICAO)));
        usuario.setEmail(cursor.getString(cursor.getColumnIndex(COLUNA_EMAIL)));
        usuario.setEndereco(cursor.getString(cursor.getColumnIndex(COLUNA_ENDERECO)));
        usuario.setSenha(cursor.getString(cursor.getColumnIndex(COLUNA_SENHA)));

        return usuario;
    }

    public boolean autentica(String userName, String senha){
        String[] collumns = {COLUNA_ID};

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUNA_USERNAME + " = ? " + " AND " + COLUNA_SENHA + " = ? ";
        String[] selectionArgs = { userName, senha};

        Cursor cursor = db.query(TABELA_NOME, collumns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();

        db.close();

        if (cursorCount > 0 ){
            return true;
        }
        return false;
    }


    public boolean checkUser(String userName){
        String[] collumns = {COLUNA_ID};

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUNA_USERNAME + " = ? ";
        String[] selectionArgs = { userName};

        Cursor cursor = db.query(TABELA_NOME, collumns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();

        db.close();

        if (cursorCount > 0 ){
            return true;
        }
        return false;
    }

    public void atualizar(Usuario usuario){
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_NOME, usuario.getNome());
        valores.put(COLUNA_IDADE, usuario.getIdade());
        valores.put(COLUNA_TURMA, usuario.getTurma());
        valores.put(COLUNA_INSTITUICAO, usuario.getInstituicao());
        valores.put(COLUNA_EMAIL, usuario.getEmail());
        valores.put(COLUNA_ENDERECO, usuario.getEndereco());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABELA_NOME, valores, COLUNA_ID + " = ? ", new String[]{"" + usuario.getId()});
    }

}
