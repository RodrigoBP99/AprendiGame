package br.com.rodrigo.aprendigame.Fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.zxing.Result;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.rodrigo.aprendigame.Activity.LoginActivity;
import br.com.rodrigo.aprendigame.DB.PresencaDAO;
import br.com.rodrigo.aprendigame.Model.Presenc;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class LeitorQRFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView ScannerView;
    private Student student;

    @BindView(R.id.toolbarMainActivity)
    Toolbar toolbar;
    @BindView(R.id.navViewMain)
    BottomNavigationView bottomNavigationView;

    private Presenc presenc;

    public LeitorQRFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ScannerView = new ZXingScannerView(getActivity());
        student = LoginActivity.student;
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return ScannerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ButterKnife.bind(this, getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        toolbar.setVisibility(View.VISIBLE);
        bottomNavigationView.setVisibility(View.VISIBLE);
    }

    @Override
    public void handleResult(Result rawResult) {
        String resultado = rawResult.toString();
        String[] result = resultado.split("&");

        presenc.setCode(result[1]);
        presenc.setDate(result[2]);
        presenc.setCourseClassId(Long.getLong(result[3]));

        Toast.makeText(getContext(), presenc.getCourseClassId().toString(), Toast.LENGTH_LONG).show();
//        String scannedCode = student.getId() + "&" + getData() + "&" + rawResult;
//        createNewPresenca(presenc);

    }

    private void createNewPresenca(Presenc presenc) {
        PresencaDAO presencaDAO = new PresencaDAO(getContext());
        if (presencaDAO.checkScannedPresenca(presenc.getCode())){
            Toast.makeText(getContext(), getString(R.string.qrCode_ja_lido), Toast.LENGTH_SHORT).show();
            onResume();
        } else if (!presenc.getDate().equals(getData())){
            Toast.makeText(getContext(), "Data da presença ultrapassada", Toast.LENGTH_LONG).show();
        } else {
            try {


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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
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

    public void createPresenca(final Presenc presenc){

    }

    @Override
    public void onStart() {
        super.onStart();
        toolbar.setVisibility(View.GONE);
        bottomNavigationView.setVisibility(View.GONE);
    }
}