package br.com.rodrigo.aprendigame.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.com.rodrigo.aprendigame.Fragments.QuizzQuestionFragment;
import br.com.rodrigo.aprendigame.R;

public class QuizzQuestionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_question);

        getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutQuizzQuestionaActivity, new QuizzQuestionFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Atenção!!!").setMessage("Você tem certeza que deseja sair do questionario?")
                .setPositiveButton("Sim", (dialogInterface, i) -> finish())
                .setNegativeButton("Continuar Avaliação", null).show();
    }
}
