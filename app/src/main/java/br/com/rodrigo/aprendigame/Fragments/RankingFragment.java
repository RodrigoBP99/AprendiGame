package br.com.rodrigo.aprendigame.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import br.com.rodrigo.aprendigame.Activity.AuthenticationActivity;
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
        ButterKnife.bind(this, getActivity());

        try {
            Student student = new Student();
            if (getActivity().getIntent().getSerializableExtra(AuthenticationActivity.STUDENT) != null) {
                student = (Student) getActivity().getIntent().getSerializableExtra(AuthenticationActivity.STUDENT);
            } else if (getActivity().getIntent().getSerializableExtra(LoginActivity.STUDENT) != null) {
                student = (Student) getActivity().getIntent().getSerializableExtra(LoginActivity.STUDENT);
            }

            textViewName.setText(student.getName());
            textViewPoints.setText(student.getPoints() + "/" + student.getRequiredPoints());
            textViewActualLevel.setText(getString(R.string.lvl) + ". " + student.getActualLevel());
            textViewNextLevel.setText(getString(R.string.lvl) + ". " + student.getNextLevel());
            progressBarRanking.setMax((int) student.getRequiredPoints());
            progressBarRanking.setProgress((int) student.getPoints());
            Glide.with(getActivity()).load(student.getPhoto()).circleCrop().into(imageViewPerfilStudent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
