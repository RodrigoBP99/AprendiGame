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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.rodrigo.aprendigame.DB.StudentDAO;
import br.com.rodrigo.aprendigame.Activity.EditarPerfilActivity;
import br.com.rodrigo.aprendigame.Activity.LoginActivity;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.WsHelper.HelperWs;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {

    private Student usuario;
    private TextView textViewNome;
    private TextView textViewIdade;
    private TextView textViewTurma;
    private TextView textViewInstituicao;
    private TextView textViewEmail;
    private TextView textViewEndereco;
    private ImageView imageViewPerfil;

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
        switch (item.getItemId()) {
            case R.id.buttonEditarPerfil:
                Intent editarPerfil = new Intent(getContext(), EditarPerfilActivity.class);
                editarPerfil.putExtra(USUARIO, pegarUsuario());
                startActivity(editarPerfil);
        }
        return super.onOptionsItemSelected(item);
    }

    public String pegarUsuario() {
        final String userName = getActivity().getIntent().getStringExtra(LoginActivity.USERMATRICULA);
        return userName;
    }

    @Override
    public void onResume() {
        super.onResume();

        findViewsById();

        StudentDAO usuarioDAO = new StudentDAO(getContext());
        final String userName = getActivity().getIntent().getStringExtra(LoginActivity.USERMATRICULA);

        SetupRest.apiService.getStudent(1L).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()) {
                    Student student = response.body();
                    textViewNome.setText(student.getName());

                    Picasso.get().load(student.getPhoto()).into(imageViewPerfil);
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {

            }
        });


        //recuperar usuario
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findViewsById() {
        textViewNome = getActivity().findViewById(R.id.textViewNomePerfil);
        textViewIdade = getActivity().findViewById(R.id.textViewNascimentoPerfil);
        textViewTurma = getActivity().findViewById(R.id.textViewTurmaPerfil);
        textViewInstituicao = getActivity().findViewById(R.id.textViewInstituicaoPerfil);
        textViewEmail = getActivity().findViewById(R.id.textViewEmailPerfil);
        textViewEndereco = getActivity().findViewById(R.id.textViewEnderecoPerfil);
        imageViewPerfil = getActivity().findViewById(R.id.imageViewPerfil);
    }
}
