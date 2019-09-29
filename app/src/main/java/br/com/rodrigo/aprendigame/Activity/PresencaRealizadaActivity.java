package br.com.rodrigo.aprendigame.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.rodrigo.aprendigame.Adapter.PresencaAdapter;
import br.com.rodrigo.aprendigame.DB.PresencaDAO;
import br.com.rodrigo.aprendigame.Model.Presenca;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PresencaRealizadaActivity extends AppCompatActivity {

    private PresencaAdapter adapter;
    private String idAula;
    private String idAluno;
    private PresencaDAO presencaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presenca_realizada);

        Intent intent = getIntent();
        //pega o id do aluno e da aula escolhida para recuperar as respectivas presenças
        idAula = getIntent().getStringExtra("aula");
        idAluno = getIntent().getStringExtra(LoginActivity.USERMATRICULA);

        setListPresencaRecycle();

        getListPresencas();
    }

    private void setListPresencaRecycle() {
        RecyclerView recyclerViewPresenca = findViewById(R.id.recycleViewPresencas);
        recyclerViewPresenca.setLayoutManager(new LinearLayoutManager(PresencaRealizadaActivity.this));

        presencaDAO = new PresencaDAO(PresencaRealizadaActivity.this);

        ArrayList<Presenca> list = (ArrayList<Presenca>) presencaDAO.buscarPresencaAula(idAula);

        adapter = new PresencaAdapter(list, this);
        recyclerViewPresenca.setAdapter(adapter);


        if (presencaDAO.checkTableSize() > 0) {
            sendList(list, presencaDAO);
        }
    }

    public void getListPresencas(){

    }

    public void sendList(ArrayList<Presenca> lista, final PresencaDAO presencaDAO){
        try{
            SetupRest.apiService.sendList(idAula,lista).enqueue(new Callback<ArrayList<Presenca>>() {
                @Override
                public void onResponse(Call<ArrayList<Presenca>> call, Response<ArrayList<Presenca>> response) {
                    if (response.isSuccessful()){
                        presencaDAO.limparPresencas();
                    }
                }
                @Override
                public void onFailure(Call<ArrayList<Presenca>> call, Throwable t) {
                    Toast.makeText(PresencaRealizadaActivity.this, "Erro ao subir lista", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){

        }
    }
}