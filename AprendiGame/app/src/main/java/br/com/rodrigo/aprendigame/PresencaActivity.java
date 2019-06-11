package br.com.rodrigo.aprendigame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import br.com.rodrigo.aprendigame.Adapter.PresencaAdapter;
import br.com.rodrigo.aprendigame.Model.Presenca;

public class PresencaActivity extends AppCompatActivity {

    private ArrayList<Presenca> presencas = new ArrayList<>();
    private PresencaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presenca);

        RecyclerView recyclerViewPresenca = findViewById(R.id.recycleViewPresencas);
        recyclerViewPresenca.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Presenca presenca1 = new Presenca("06/06/2019", "Raphael Ramos", "Dispositivos Moveis");
        presencas.add(presenca1);

        Presenca presenca2 = new Presenca("06/06/2019", "Raphael Ramos", "Pesquisa Operacional");
        presencas.add(presenca2);

        adapter = new PresencaAdapter(presencas, getApplicationContext());
        recyclerViewPresenca.setAdapter(adapter);
    }
}
