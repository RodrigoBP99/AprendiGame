package br.com.rodrigo.aprendigame.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
        return inflater.inflate(R.layout.fragment_perfil, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        textViewNome = getActivity().findViewById(R.id.textViewNomePerfil);
        textViewIdade = getActivity().findViewById(R.id.textViewIdadePerfil);
        textViewTurma = getActivity().findViewById(R.id.textViewTurmaPerfil);
        textViewInstituicao = getActivity().findViewById(R.id.textViewInstituicaoPerfil);
        textViewEmail = getActivity().findViewById(R.id.textViewEmailPerfil);
        textViewEndereco = getActivity().findViewById(R.id.textViewEnderecoPerfil);

        UsuarioDAO usuarioDAO = new UsuarioDAO(getContext());

        final String userName = getActivity().getIntent().getStringExtra(LoginActivity.USERNAME);

        try {
            usuario = usuarioDAO.selectedUsuario(userName);
            textViewNome.setText(usuario.getNome());
            textViewIdade.setText("Idade: " + usuario.getIdade());
            textViewTurma.setText("Turma: " + usuario.getTurma());
            textViewInstituicao.setText("Instituição: " + usuario.getInstituicao());
            textViewEmail.setText("E-mail: " + usuario.getEmail());
            textViewEndereco.setText("Endereço: " + usuario.getEndereco());
        } catch (Exception e){
            e.printStackTrace();
        }


        FloatingActionButton floatingActionButtonEditarPerfil = getView().findViewById(R.id.floatingActionButton_editarPerfil);

        floatingActionButtonEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editarPerfil = new Intent(getContext(), EditarPerfil.class);
                editarPerfil.putExtra(USUARIO, userName);
                startActivity(editarPerfil);
            }
        });

    }
}
