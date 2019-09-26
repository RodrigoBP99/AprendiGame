package br.com.rodrigo.aprendigame.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.Calendar;

import br.com.rodrigo.aprendigame.Activity.AulasActivity;
import br.com.rodrigo.aprendigame.Activity.LoginActivity;
import br.com.rodrigo.aprendigame.DB.PresencaDAO;
import br.com.rodrigo.aprendigame.Model.Presenca;
import br.com.rodrigo.aprendigame.Activity.PresencaRealizadaActivity;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeitorQRFragment extends Fragment implements ZXingScannerView.ResultHandler {

    private ZXingScannerView ScannerView;
    private String idAluno;

    private String arrayPresenca[];

    public LeitorQRFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ScannerView = new ZXingScannerView(getActivity());
        idAluno = getActivity().getIntent().getStringExtra(LoginActivity.USERMATRICULA);
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
                Intent presencaIntent = new Intent(getContext(), AulasActivity.class);
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
        arrayPresenca = texto.split("&");
        
        String hora = getHora();

        createNewPresenca(texto, hora);
    }

    private void createNewPresenca(String texto, String hora) {
        PresencaDAO presencaDAO = new PresencaDAO(getContext());
        if (presencaDAO.checkScannedPresenca(texto)){
            Toast.makeText(getContext(), "Essa presença já foi realizada!!!", Toast.LENGTH_SHORT).show();
            onResume();
        } else {
            try {
                Presenca presenca = new Presenca();
                presenca.setId(texto);
                presenca.setData(arrayPresenca[0]);
                presenca.setAula(arrayPresenca[1]);
                presenca.setProfessor(arrayPresenca[2]);
                presenca.setIdAluno(idAluno);
                presenca.setHora(hora);

                createPresenca(presenca);

            } catch (Exception e){
                e.printStackTrace();
                Toast.makeText(getContext(), "QR Code Invalido!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private String getHora() {
        Calendar agora = Calendar.getInstance();
        int horaAtual = agora.get(Calendar.HOUR_OF_DAY);
        int minutos = agora.get(Calendar.MINUTE);
        String hora = String.format("%02d", horaAtual) + ":" + String.format("%02d", minutos);
        return hora;
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

    public void createPresenca(final Presenca presenca){
        try {
            SetupRest.apiService.createPresenca(arrayPresenca[1],presenca).enqueue(new Callback<Presenca>() {
                PresencaDAO presencaDAO = new PresencaDAO(getContext());
                @Override
                public void onResponse(Call<Presenca> call, Response<Presenca> response) {
                    if (response.isSuccessful()){

                        Toast.makeText(getContext(), "Presença Atualizada", Toast.LENGTH_SHORT).show();
                    } else{
                        presencaDAO.inserirPresenca(presenca);
                    }
                    Intent intent = new Intent(getContext(), PresencaRealizadaActivity.class);
                    intent.putExtra("aula", arrayPresenca[1]);
                    startActivity(intent);
                }

                @Override
                public void onFailure(Call<Presenca> call, Throwable t) {
                    Log.e("SendPresencaErro: ", t.getMessage());
                }
            });
        } catch (Exception e){

        }
    }
}