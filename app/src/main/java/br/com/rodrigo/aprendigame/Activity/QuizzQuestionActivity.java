package br.com.rodrigo.aprendigame.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import br.com.rodrigo.aprendigame.Adapter.QuizzAdapter;
import br.com.rodrigo.aprendigame.Fragments.QuizzQuestionFragment;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizzQuestionActivity extends AppCompatActivity {

    @BindView(R.id.textViewTitleToolbarMain)
    TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_question);
        ButterKnife.bind(this);

        textViewTitle.setText(getIntent().getStringExtra(QuizzAdapter.QUESTIONARIO));
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
