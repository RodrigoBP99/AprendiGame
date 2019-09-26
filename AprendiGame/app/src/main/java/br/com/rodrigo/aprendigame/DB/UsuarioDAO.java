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
        String[] collumns = {COLUNA_ID_USER};

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUNA_ID_USER + " = ? " + " AND " + COLUNA_SENHA_USER + " = ? ";
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
        String[] collumns = {COLUNA_ID_USER};

        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUNA_ID_USER + " = ? ";
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
        valores.put(COLUNA_NOME_USER, usuario.getNome());
        valores.put(COLUNA_IDADE_USER, usuario.getIdade());
        valores.put(COLUNA_TURMA_USER, usuario.getTurma());
        valores.put(COLUNA_INSTITUICAO_USER, usuario.getInstituicao());
        valores.put(COLUNA_EMAIL_USER, usuario.getEmail());
        valores.put(COLUNA_ENDERECO_USER, usuario.getEndereco());

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABELA_USUARIO, valores, COLUNA_ID_USER + " = ? ", new String[]{"" + usuario.getId()});
    }

    public Usuario selectUsuario(String userMatricula) {
        Usuario usuario = new Usuario();
        SQLiteDatabase db = this.getWritableDatabase();

        String[] collumns = {COLUNA_ID_USER, COLUNA_NOME_USER, COLUNA_IDADE_USER, COLUNA_TURMA_USER, COLUNA_INSTITUICAO_USER, COLUNA_EMAIL_USER, COLUNA_ENDERECO_USER};
        String selection = COLUNA_ID_USER + " = ?";
        String[] selectionArgs = {userMatricula};

        Cursor cursor = db.query(TABELA_USUARIO, collumns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            usuario.setId(cursor.getString(cursor.getColumnIndex(COLUNA_ID_USER)));
            usuario.setNome(cursor.getString(cursor.getColumnIndex(COLUNA_NOME_USER)));
            usuario.setIdade(cursor.getString(cursor.getColumnIndex(COLUNA_IDADE_USER)));
            usuario.setTurma(cursor.getString(cursor.getColumnIndex(COLUNA_TURMA_USER)));
            usuario.setInstituicao(cursor.getString(cursor.getColumnIndex(COLUNA_INSTITUICAO_USER)));
            usuario.setEmail(cursor.getString(cursor.getColumnIndex(COLUNA_EMAIL_USER)));
            usuario.setEndereco(cursor.getString(cursor.getColumnIndex(COLUNA_ENDERECO_USER)));
        }
        cursor.close();

        return usuario;
    }

}
