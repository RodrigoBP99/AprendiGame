package br.com.rodrigo.aprendigame.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.rodrigo.aprendigame.DB.UsuarioDAO;
import br.com.rodrigo.aprendigame.LoginActivity;
import br.com.rodrigo.aprendigame.Model.Usuario;
import br.com.rodrigo.aprendigame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends Fragment {

    private TextView textViewNome;
    private Usuario usuario;
    public RankingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ranking, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutFeedRanking, new RankingFeedFragment()).commit();
        textViewNome = getView().findViewById(R.id.textViewNomeRanking);


        UsuarioDAO usuarioDAO = new UsuarioDAO(getContext());

        String userName = getActivity().getIntent().getStringExtra(LoginActivity.USERNAME);

        try {
            usuario = usuarioDAO.selectedUsuario(userName);
            textViewNome.setText(usuario.getNome());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
