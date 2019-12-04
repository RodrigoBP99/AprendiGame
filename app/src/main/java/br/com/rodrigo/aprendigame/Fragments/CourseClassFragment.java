package br.com.rodrigo.aprendigame.Fragments;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.rodrigo.aprendigame.Activity.MainActivity;
import br.com.rodrigo.aprendigame.Adapter.PresencaAdapter;
import br.com.rodrigo.aprendigame.Model.Presenca;
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
    private ArrayList<Presenca> presencas = new ArrayList<>();

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

        Presenca presenca = new Presenca();
        presenca.setAula("Pesquisa Operacional");
        presenca.setData("05/12/2019");
        presenca.setHora("20:30");
        presencas.add(presenca);

        Presenca presenca1 = new Presenca();
        presenca1.setAula("Tecnicas de Projeto");
        presenca1.setData("06/12/2019");
        presenca1.setHora("18:35");

        presencas.add(presenca1);

        PresencaAdapter presencaAdapter = new PresencaAdapter(presencas, getContext());

        recyclerViewCourseClass.setAdapter(presencaAdapter);
    }

    @OnClick(R.id.buttonLerPresenca) void lerPresenca(){
        if (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutMainActivity, new LeitorQRFragment(), "leitorQR").commit();
        } else {
            requestCameraPermission();
        }

    }

    private void requestCameraPermission(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)){
            new AlertDialog.Builder(getContext(), R.style.AlertDialogCustom)
                    .setTitle(getString(R.string.title_permissao_camera))
                    .setMessage(getString(R.string.message_permissao_camera))
                    .setPositiveButton(getString(R.string.ok), (dialog, which) ->
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CameraPermission))
                    .setNegativeButton(getString(R.string.cancelar), (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CameraPermission);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CameraPermission){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutMainActivity, new LeitorQRFragment(), "leitorQR").commit();
            } else {
                Toast.makeText(getContext(), getString(R.string.permissao_negada), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
