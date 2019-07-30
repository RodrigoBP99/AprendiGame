package br.com.rodrigo.aprendigame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Adapter.PresencaAdapter;
import br.com.rodrigo.aprendigame.DB.PresencaDAO;
import br.com.rodrigo.aprendigame.Model.Presenca;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresencaRealizadaActivity extends AppCompatActivity {

    private PresencaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presenca_realizada);

        RecyclerView recyclerViewPresenca = findViewById(R.id.recycleViewPresencas);
        recyclerViewPresenca.setLayoutManager(new LinearLayoutManager(PresencaRealizadaActivity.this));

        final PresencaDAO presencaDAO = new PresencaDAO(PresencaRealizadaActivity.this);

        ArrayList<Presenca> list = (ArrayList<Presenca>) presencaDAO.buscar();

        adapter = new PresencaAdapter(list);
        recyclerViewPresenca.setAdapter(adapter);

        try {
            SetupRest.apiService.listPresenca().enqueue(new Callback<List<Presenca>>() {
                @Override
                public void onResponse(Call<List<Presenca>> call, Response<List<Presenca>> response) {
                    adapter.update(response.body());
                    Log.e("Sucesso", call.toString());
                }

                @Override
                public void onFailure(Call<List<Presenca>> call, Throwable t) {
                    Log.e("Erro", call.toString());
                    t.printStackTrace();
                }
            });
        } catch (Exception e){

        }

        if (isOnline() == true){
            presencaDAO.limparLista();
        } else {
            isOnline();
        }
    }


    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }
}