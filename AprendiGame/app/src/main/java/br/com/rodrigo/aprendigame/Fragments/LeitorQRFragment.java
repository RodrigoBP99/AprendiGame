package br.com.rodrigo.aprendigame.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;


import java.util.Calendar;

import br.com.rodrigo.aprendigame.DB.PresencaDAO;
import br.com.rodrigo.aprendigame.Model.Presenca;
import br.com.rodrigo.aprendigame.PresencaRealizadaActivity;
import br.com.rodrigo.aprendigame.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeitorQRFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView ScannerView;

    public LeitorQRFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ScannerView = new ZXingScannerView(getActivity());
        setHasOptionsMenu(true);
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
                Intent presencaIntent = new Intent(getContext(), PresencaRealizadaActivity.class);
                startActivity(presencaIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void handleResult(Result rawResult) {
        rawResult.toString();
        String texto = String.valueOf(rawResult);
        String array[] = texto.split("&");


        Calendar agora = Calendar.getInstance();
        int horaAtual = agora.get(Calendar.HOUR_OF_DAY);
        int minutos = agora.get(Calendar.MINUTE);
        String hora = horaAtual + ":" + minutos;


        try {
            Presenca presenca = new Presenca();
            presenca.setData(array[0]);
            presenca.setAula(array[1]);
            presenca.setProfessor(array[2]);
            presenca.setHora(hora);
            PresencaDAO presencaDAO = new PresencaDAO(getContext());
            presencaDAO.inserir(presenca);
        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(), "QR Code Invalido!", Toast.LENGTH_SHORT).show();
        }

        Intent intent = new Intent(getContext(), PresencaRealizadaActivity.class);
        startActivity(intent);

    }


    @Override
    public void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }


    @Override
    public void onResume() {
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}
