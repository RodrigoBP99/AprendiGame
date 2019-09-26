package br.com.rodrigo.aprendigame.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Adapter.AulaAdapter;
import br.com.rodrigo.aprendigame.DB.AulaDAO;
import br.com.rodrigo.aprendigame.Model.Aula;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AulasActivity extends AppCompatActivity {

    private String idAluno;
    private AulaAdapter aulaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aulas);

        idAluno = getIntent().getStringExtra(LoginActivity.USERMATRICULA);

        setListAulaRecycle();

    }

    private void setListAulaRecycle() {
        RecyclerView recyclerViewAulas = findViewById(R.id.recycleViewAulas);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewAulas.addItemDecoration(itemDecoration);
        recyclerViewAulas.setLayoutManager(new LinearLayoutManager(this));

        AulaDAO aulaDAO = new AulaDAO(this);
        ArrayList<Aula> listAula = (ArrayList<Aula>) aulaDAO.getListAula();

        aulaAdapter = new AulaAdapter(listAula, this);
        getAula();
        recyclerViewAulas.setAdapter(aulaAdapter);
    }

    private void getAula() {
        try {
            SetupRest.apiService.listAula(idAluno).enqueue(new Callback<List<Aula>>() {
                @Override
                public void onResponse(Call<List<Aula>> call, Response<List<Aula>> response) {
                    if (response.isSuccessful()){
                        aulaAdapter.atualiza(response.body());
                    }else {
                        Log.e("getAula: ", call.toString());
                        Toast.makeText(AulasActivity.this, "Ocorreu um pequeno imprevisto/nTente Novamente mais tarde", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Aula>> call, Throwable t) {
                    Log.e("getAulaErro: ", t.getMessage());
                    Toast.makeText(AulasActivity.this, "Erro na conexão com servidor", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e){

        }
    }
}