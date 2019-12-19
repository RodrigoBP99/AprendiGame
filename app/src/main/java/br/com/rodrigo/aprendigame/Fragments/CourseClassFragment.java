package br.com.rodrigo.aprendigame.Fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.rodrigo.aprendigame.Adapter.PresencaAdapter;
import br.com.rodrigo.aprendigame.Model.Presenc;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseClassFragment extends Fragment {

    private int CameraPermission = 1;
    @BindView(R.id.recycleViewCourseClass)
    RecyclerView recyclerViewCourseClass;
    private ArrayList<Presenc> presencs = new ArrayList<>();

    public CourseClassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_course_class, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getActivity());

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerViewCourseClass.addItemDecoration(itemDecoration);
        recyclerViewCourseClass.setLayoutManager(new LinearLayoutManager(getContext()));

        Presenc presenc = new Presenc();

        presencs.add(presenc);

        Presenc presenc1 = new Presenc();


        presencs.add(presenc1);

        PresencaAdapter presencaAdapter = new PresencaAdapter(presencs, getContext());

        recyclerViewCourseClass.setAdapter(presencaAdapter);
    }

    @OnClick(R.id.buttonLerPresenca) void lerPresenca(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutMainActivity, new LeitorQRFragment(), "leitorQR").commit();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CameraPermission);
        }
    }
}
