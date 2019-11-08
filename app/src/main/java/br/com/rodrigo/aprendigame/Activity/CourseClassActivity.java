package br.com.rodrigo.aprendigame.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.rodrigo.aprendigame.Adapter.PresencaAdapter;
import br.com.rodrigo.aprendigame.DB.PresencaDAO;
import br.com.rodrigo.aprendigame.Model.Presenca;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseClassActivity extends AppCompatActivity {

    private PresencaAdapter adapter;
    private String idAula;
    private PresencaDAO presencaDAO;
    @BindView(R.id.recycleViewCourseClass) RecyclerView recyclerViewCourseClass;
    @BindView(R.id.toolbarCourseClass)
    Toolbar toolbar;
    @BindView(R.id.textViewTitleToolbarMain)
    TextView textViewTitleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_class);
        ButterKnife.bind(this);

        //pega o id do aluno e da aula escolhida para recuperar as respectivas presenças
        Intent intent = getIntent();
        idAula = intent.getStringExtra("courseUnit");

        recyclerViewCourseClass.setLayoutManager(new LinearLayoutManager(CourseClassActivity.this));

        presencaDAO = new PresencaDAO(CourseClassActivity.this);

        ArrayList<Presenca> list = (ArrayList<Presenca>) presencaDAO.buscarPresencaAula(idAula);

        adapter = new PresencaAdapter(list, this);
        recyclerViewCourseClass.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        textViewTitleToolbar.setText(getString(R.string.aulas));
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