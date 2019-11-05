package br.com.rodrigo.aprendigame.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import br.com.rodrigo.aprendigame.Adapter.PresencaAdapter;
import br.com.rodrigo.aprendigame.DB.PresencaDAO;
import br.com.rodrigo.aprendigame.Model.Presenca;
import br.com.rodrigo.aprendigame.R;

public class CourseClassActivity extends AppCompatActivity {

    private PresencaAdapter adapter;
    private String idAula;
    private PresencaDAO presencaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_class);

        //pega o id do aluno e da aula escolhida para recuperar as respectivas presenças
        Intent intent = getIntent();
        idAula = intent.getStringExtra("courseUnit");

        RecyclerView recyclerViewPresenca = findViewById(R.id.recycleViewPresencas);
        recyclerViewPresenca.setLayoutManager(new LinearLayoutManager(CourseClassActivity.this));

        presencaDAO = new PresencaDAO(CourseClassActivity.this);

        ArrayList<Presenca> list = (ArrayList<Presenca>) presencaDAO.buscarPresencaAula(idAula);

        adapter = new PresencaAdapter(list, this);
        recyclerViewPresenca.setAdapter(adapter);
    }

    private void setListPresencaRecycle() {
        if (presencaDAO.checkTableSize() > 0) {
            try{
                // enviar lista de presenças que estão locais
            } catch (Exception e){

            }
        }
    }

    public void getListPresencas(){
        // recuperar lista de presenças
    }
}