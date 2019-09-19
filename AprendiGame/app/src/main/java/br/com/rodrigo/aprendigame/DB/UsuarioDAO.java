package br.com.rodrigo.aprendigame.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.rodrigo.aprendigame.Model.Usuario;


public class UsuarioDAO extends DBHelper {

    public UsuarioDAO(Context applicationContext) {
        super(applicationContext);
    }

    public void salvarUsuario(Usuario usuario) {
        getWritableDatabase().insert(TABELA_USUARIO, null, usuario.getValues());
    }

    public boolean autenticaUsuario(String userMatricula, String senha) {
        String[] collumns = {COLUNA_ID};

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUNA_ID + " = ? " + " AND " + COLUNA_SENHA + " = ? ";
        String[] selectionArgs = {userMatricula, senha};

        Cursor cursor = db.query(TABELA_USUARIO, collumns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();

        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }


    public boolean checkUserMatricula(String userMatricula) {
        String[] collumns = {COLUNA_ID};

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUNA_ID + " = ? ";
        String[] selectionArgs = {userMatricula};

        Cursor cursor = db.query(TABELA_USUARIO, collumns, selection, selectionArgs, null, null, null);

        int cursorCount = cursor.getCount();
        cursor.close();

        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public void atualizaUsuario(Usuario usuario) {
        ContentValues valores = new ContentValues();
        valores.put(COLUNA_NOME, usuario.getNome());
        valores.put(COLUNA_IDADE, usuario.getIdade());
        valores.put(COLUNA_TURMA, usuario.getTurma());
        valores.put(COLUNA_INSTITUICAO, usuario.getInstituicao());
        valores.put(COLUNA_EMAIL, usuario.getEmail());
        valores.put(COLUNA_ENDERECO, usuario.getEndereco());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABELA_USUARIO, valores, COLUNA_ID + " = ? ", new String[]{"" + usuario.getId()});
    }

    public Usuario selectUsuario(String userMatricula) {
        Usuario usuario = new Usuario();
        SQLiteDatabase db = this.getWritableDatabase();

        String[] collumns = {COLUNA_ID, COLUNA_NOME, COLUNA_IDADE, COLUNA_TURMA, COLUNA_INSTITUICAO, COLUNA_EMAIL, COLUNA_ENDERECO};
        String selection = COLUNA_ID + " = ?";
        String[] selectionArgs = {userMatricula};

        Cursor cursor = db.query(TABELA_USUARIO, collumns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            usuario.setId(cursor.getString(cursor.getColumnIndex(COLUNA_ID)));
            usuario.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME)));
            usuario.setIdade(cursor.getString(cursor.getColumnIndex(COLUNA_IDADE)));
            usuario.setTurma(cursor.getString(cursor.getColumnIndex(COLUNA_TURMA)));
            usuario.setInstituicao(cursor.getString(cursor.getColumnIndex(COLUNA_INSTITUICAO)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndex(COLUNA_EMAIL)));
            usuario.setEndereco(cursor.getString(cursor.getColumnIndex(COLUNA_ENDERECO)));
        }
        cursor.close();

        return usuario;
    }

}
