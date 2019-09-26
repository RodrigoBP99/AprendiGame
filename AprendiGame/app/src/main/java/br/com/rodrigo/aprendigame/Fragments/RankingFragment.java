package br.com.rodrigo.aprendigame.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.rodrigo.aprendigame.DB.UsuarioDAO;
import br.com.rodrigo.aprendigame.Activity.LoginActivity;
import br.com.rodrigo.aprendigame.Model.Usuario;
import br.com.rodrigo.aprendigame.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends Fragment {

    private TextView textViewNome;
    private Usuario usuario;

    private BottomNavigationView bottomNavigationView;

    public RankingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_ranking, container, false);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar_main_perfil, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.perfil:
                getFragmentManager().beginTransaction().replace(R.id.linearLayoutMainActivity, new PerfilFragment()).commit();
                bottomNavigationView = getActivity().findViewById(R.id.navViewMain);
                bottomNavigationView.setVisibility(View.GONE);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutFeedRanking, new RankingFeedFragment()).commit();
        textViewNome = getView().findViewById(R.id.textViewNomeRanking);

        UsuarioDAO usuarioDAO = new UsuarioDAO(getContext());
        String userName = getActivity().getIntent().getStringExtra(LoginActivity.USERMATRICULA);

        try {
            usuario = usuarioDAO.selectUsuario(userName);
            textViewNome.setText(usuario.getNome());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        bottomNavigationView = getActivity().findViewById(R.id.navViewMain);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }
}
