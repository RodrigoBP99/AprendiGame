package br.com.rodrigo.aprendigame.Fragments;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.rodrigo.aprendigame.Activity.CourseUnitActivity;
import br.com.rodrigo.aprendigame.Activity.LoginActivity;
import br.com.rodrigo.aprendigame.DB.PresencaDAO;
import br.com.rodrigo.aprendigame.Model.Presenca;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
/**
 * A simple {@link Fragment} subclass.
 */
public class LeitorQRFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView ScannerView;
    private String idAluno;

    private Student student;

    public LeitorQRFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ScannerView = new ZXingScannerView(getActivity());
        idAluno = getActivity().getIntent().getStringExtra(LoginActivity.STUDENT);
        setHasOptionsMenu(true);
        student = (Student) getActivity().getIntent().getSerializableExtra(LoginActivity.STUDENT);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return ScannerView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.toolbar_main_presenca, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case(R.id.buttonPresenca):
                Intent presencaIntent = new Intent(getContext(), CourseUnitActivity.class);
                startActivity(presencaIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    @Override
    public void handleResult(Result rawResult) {
        rawResult.toString();

        String scannedCode = student.getId() + "&" + getData() + "&" + rawResult;

    }

    private void createNewPresenca(String texto) {
        PresencaDAO presencaDAO = new PresencaDAO(getContext());
        if (presencaDAO.checkScannedPresenca(texto)){
            Toast.makeText(getContext(), getString(R.string.qrCode_ja_lido), Toast.LENGTH_SHORT).show();
            onResume();
        } else {
            try {
//                Presenca presenca = new Presenca();
//                presenca.setId(texto);
//                presenca.setAula(arrayPresenca[1]);
//                presenca.setProfessor(arrayPresenca[2]);
//                presenca.setIdAluno(idAluno);
//                presenca.setData(getData());
//
//                createPresenca(presenca);

            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getContext(), getString(R.string.qrCode_invalido), Toast.LENGTH_SHORT).show();
            }
        }
        onResume();
    }

    private String getData() {
        Calendar agora = Calendar.getInstance();
        Date date = agora.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String horaDataAtual = simpleDateFormat.format(date);

        return horaDataAtual;
    }

    @Override
    public void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }

    public void createPresenca(final Presenca presenca){
        try {
//            SetupRest.apiService.createPresenca(arrayPresenca[1],presenca).enqueue(new Callback<Presenca>() {
//                PresencaDAO presencaDAO = new PresencaDAO(getContext());
//                @Override
//                public void onResponse(Call<Presenca> call, Response<Presenca> response) {
//                    if (response.isSuccessful()){
//
//                        Toast.makeText(getContext(), getString(R.string.presenca_atualizada), Toast.LENGTH_SHORT).show();
//                    } else{
//                        presencaDAO.inserirPresenca(presenca);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<Presenca> call, Throwable t) {
//                    Log.e("SendPresencaErro: ", t.getMessage());
//                }
//            });
        } catch (Exception e){

        }
    }
}