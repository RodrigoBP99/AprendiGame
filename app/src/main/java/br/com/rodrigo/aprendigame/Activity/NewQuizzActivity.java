package br.com.rodrigo.aprendigame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Fragments.NewQuizFragment;
import br.com.rodrigo.aprendigame.Fragments.NewQuizQuestionFragment;
import br.com.rodrigo.aprendigame.Model.Question;
import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewQuizzActivity extends AppCompatActivity {

    @BindView(R.id.textViewTitleToolbarMain)
    TextView textView;
    @BindView(R.id.tabLayoutNewQuiz)
    TabLayout tabLayout;

    public static String titleQuizz;
    public static int quizId = 0;
    public static ArrayList<Quizz> quizzes = new ArrayList<>();

    public static List<Question> selectedQuestions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quizz);
        ButterKnife.bind(this);
        textView.setText("Novo Quiz");

        ArrayList<Quizz> quizzActivity = (ArrayList<Quizz>) getIntent().getSerializableExtra(QuizzActivity.QUIZ);
        for (Quizz quizz : quizzActivity){
            quizzes.add(quizz);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutNewQuizz, new NewQuizFragment()).commit();

        createTabItems();

        tabNavigation();
    }

    private void tabNavigation() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0){
                    getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutNewQuizz, new NewQuizFragment()).commit();
                } else {
                    quizId = (int) tab.getTag();
                    getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutNewQuizz, new NewQuizQuestionFragment()).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void createTabItems() {
        int i = 1;
        for (Quizz quizz : quizzes){
            int tag = quizzes.indexOf(quizz);
            tabLayout.addTab(tabLayout.newTab().setText("Aula " + i).setTag(tag));
            i++;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        titleQuizz = "";
        selectedQuestions.clear();
        quizzes.clear();
    }
}
