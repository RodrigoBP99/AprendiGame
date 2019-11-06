package br.com.rodrigo.aprendigame.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import br.com.rodrigo.aprendigame.Activity.LoginActivity;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RankingFragment extends Fragment {

    @BindView(R.id.textViewNameStudentRanking)
    TextView textViewName;
    @BindView(R.id.textViewPointsStudent)
    TextView textViewPoints;
    @BindView(R.id.imageViewPerfilStudentRanking)
    ImageView imageViewPerfilStudent;
    @BindView(R.id.textViewActualLevelStudentRanking)
    TextView textViewActualLevel;
    @BindView(R.id.textViewNextLevelStudentRanking)
    TextView textViewNextLevel;
    @BindView(R.id.progressBarRanking)
    ProgressBar progressBarRanking;


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
        ButterKnife.bind(this, getActivity());

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutFeedRanking, new RankingFeedFragment()).commit();

        try {
            Student student = (Student) getActivity().getIntent().getSerializableExtra(LoginActivity.STUDENT);
            textViewName.setText(student.getName());
            textViewPoints.setText(student.getPoints() + "/" + student.getRequiredPoints());
            textViewActualLevel.setText("Lvl. " + student.getActualLevel());
            textViewNextLevel.setText("Lvl. " + student.getNextLevel());
            progressBarRanking.setMax((int) student.getRequiredPoints());
            progressBarRanking.setProgress((int) student.getPoints());
            Glide.with(getActivity()).load(student.getPhoto()).circleCrop().into(imageViewPerfilStudent);
        } catch (Exception e) {
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
