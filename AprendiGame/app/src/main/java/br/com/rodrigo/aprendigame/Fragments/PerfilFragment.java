package br.com.rodrigo.aprendigame.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.rodrigo.aprendigame.DB.UsuarioDAO;
import br.com.rodrigo.aprendigame.EditarPerfil;
import br.com.rodrigo.aprendigame.LoginActivity;
import br.com.rodrigo.aprendigame.Model.Usuario;
import br.com.rodrigo.aprendigame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private Usuario usuario;
    private TextView textViewNome;
    private TextView textViewIdade;
    private TextView textViewTurma;
    private TextView textViewInstituicao;
    private TextView textViewEmail;
    private TextView textViewEndereco;
    public final static String USUARIO = "username";

    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar_perfil_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.buttonEditarPerfil:
                Intent editarPerfil = new Intent(getContext(), EditarPerfil.class);
                editarPerfil.putExtra(USUARIO, pegarUsuario());
                startActivity(editarPerfil);
        }
        return super.onOptionsItemSelected(item);
    }

    public String pegarUsuario(){
        final String userName = getActivity().getIntent().getStringExtra(LoginActivity.USERNAME);
        return userName;
    }

    @Override
    public void onResume() {
        super.onResume();

        textViewNome = getActivity().findViewById(R.id.textViewNomePerfil);
        textViewIdade = getActivity().findViewById(R.id.textViewIdadePerfil);
        textViewTurma = getActivity().findViewById(R.id.textViewTurmaPerfil);
        textViewInstituicao = getActivity().findViewById(R.id.textViewInstituicaoPerfil);
        textViewEmail = getActivity().findViewById(R.id.textViewEmailPerfil);
        textViewEndereco = getActivity().findViewById(R.id.textViewEnderecoPerfil);

        UsuarioDAO usuarioDAO = new UsuarioDAO(getContext());

        final String userName = getActivity().getIntent().getStringExtra(LoginActivity.USERNAME);

        try {
            usuario = usuarioDAO.selectUsuario(userName);
            textViewNome.setText(usuario.getNome());
            textViewIdade.setText("Idade: " + usuario.getIdade());
            textViewTurma.setText("Turma: " + usuario.getTurma());
            textViewInstituicao.setText("Instituição: " + usuario.getInstituicao());
            textViewEmail.setText("E-mail: " + usuario.getEmail());
            textViewEndereco.setText("Endereço: " + usuario.getEndereco());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
