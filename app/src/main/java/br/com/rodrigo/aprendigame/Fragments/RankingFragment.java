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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.rodrigo.aprendigame.DB.StudentDAO;
import br.com.rodrigo.aprendigame.Activity.LoginActivity;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends Fragment {

    private TextView textViewName;
    private TextView textViewPoints;
    private ImageView imageViewPerfilStudent;
    private TextView textViewActualLevel;
    private TextView textViewNextLevel;
    private ProgressBar progressBarRanking;


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
        switch (item.getItemId()) {
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

        findViewsById();

        StudentDAO usuarioDAO = new StudentDAO(getContext());
        String userName = getActivity().getIntent().getStringExtra(LoginActivity.USERMATRICULA);

        try {
            SetupRest.apiService.getStudent(1L).enqueue(new Callback<Student>() {
                @Override
                public void onResponse(Call<Student> call, Response<Student> response) {
                    if (response.isSuccessful()) {
                        Student student = response.body();
                        textViewName.setText(student.getName());
                        textViewPoints.setText(student.getPoints() + "/" + student.getRequiredPoints());
                        textViewActualLevel.setText("Lvl. " + student.getActualLevel());
                        textViewNextLevel.setText("Lvl. " + student.getNextLevel());
                        progressBarRanking.setMax((int) student.getRequiredPoints());
                        progressBarRanking.setProgress((int) student.getPoints());
                        Picasso.get().load(student.getPhoto()).into(imageViewPerfilStudent);

                    }
                }

                @Override
                public void onFailure(Call<Student> call, Throwable t) {

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void findViewsById() {
        textViewName = getView().findViewById(R.id.textViewNameStudentRanking);
        textViewPoints = getView().findViewById(R.id.textViewPointsStudent);
        textViewActualLevel = getView().findViewById(R.id.textViewActualLevelStudentRanking);
        textViewNextLevel = getView().findViewById(R.id.textViewNextLevelStudentRanking);
        imageViewPerfilStudent = getView().findViewById(R.id.imageViewPerfilStudentRanking);
        progressBarRanking = getView().findViewById(R.id.progressBarRanking);
    }

    @Override
    public void onResume() {
        super.onResume();
        bottomNavigationView = getActivity().findViewById(R.id.navViewMain);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }
}
