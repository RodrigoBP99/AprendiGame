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
    public static List<Quizz> quizzes = new ArrayList<>();

    public static List<Question> selectedQuestions = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();
    private List<Quizz> quizzes1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quizz);
        ButterKnife.bind(this);
        textView.setText("Novo Quiz");

        getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutNewQuizz, new NewQuizFragment()).commit();

        getQuestions();

        novoQuestionario();

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
        for (Quizz quizz : quizzes){
            int posicao = quizzes.indexOf(quizz) + 1;
            int tag = quizzes.indexOf(quizz);
            tabLayout.addTab(tabLayout.newTab().setText("Aula " + posicao).setTag(tag));
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        questions.addAll(selectedQuestions);
        quizzes1.addAll(quizzes);
        selectedQuestions.clear();
        quizzes.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();

        quizzes.addAll(quizzes1);
        selectedQuestions.addAll(questions);
    }

    private void novoQuestionario() {
        Question question1 = new Question();
        question1.setQuestionTitle("asdsadasd");
        Question question2 = new Question();
        question2.setQuestionTitle("asdasdasdas");
        Question question3 = new Question();
        question3.setQuestionTitle("asdasdasd");
        Question question4 = new Question();
        question4.setQuestionTitle("qwe12e12e?");
        Question question5 = new Question();
        question5.setQuestionTitle("hjkjlhjk,mnb,bmn,");
        Question question6 = new Question();
        question6.setQuestionTitle("çjklbnmc ");
        Question question7 = new Question();
        question7.setQuestionTitle("123trez");
        Question question8 = new Question();
        question8.setQuestionTitle("123124cxvb?");
        Question question9 = new Question();
        question9.setQuestionTitle("12321321");
        Question question10 = new Question();
        question10.setQuestionTitle("asdaxcas");

        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        questions.add(question4);
        questions.add(question5);
        questions.add(question6);
        questions.add(question7);
        questions.add(question8);
        questions.add(question9);
        questions.add(question10);

        Quizz quizz2 = new Quizz();
        quizz2.setId(2L);
        quizz2.setTitle("Segunda Guerra");
        quizz2.setQuestions(questions);

        quizzes.add(quizz2);
    }

    private void getQuestions() {
        Question question1 = new Question();
        question1.setQuestionTitle("Quem venceu a segunda Guerra?");
        Question question2 = new Question();
        question2.setQuestionTitle("Qual ano ocorreu a segunda Guerra?");
        Question question3 = new Question();
        question3.setQuestionTitle("Quanto tempo durou a segunda guerra?");
        Question question4 = new Question();
        question4.setQuestionTitle("Quais paises participaram da segunda guerra?");
        Question question5 = new Question();
        question5.setQuestionTitle("Quais as maiores invenções durante a guerra?");
        Question question6 = new Question();
        question6.setQuestionTitle("Quem venceu a Primeira Guerra?");
        Question question7 = new Question();
        question7.setQuestionTitle("Quem venceu a Revolução?");
        Question question8 = new Question();
        question8.setQuestionTitle("Quando ocorreu sei lá oque?");
        Question question9 = new Question();
        question9.setQuestionTitle("Não sei oque falar");
        Question question10 = new Question();
        question10.setQuestionTitle("To sem ideia pra pergunta?");

        List<Question> questions = new ArrayList<>();
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        questions.add(question4);
        questions.add(question5);
        questions.add(question6);
        questions.add(question7);
        questions.add(question8);
        questions.add(question9);
        questions.add(question10);

        Quizz quizz1 = new Quizz();
        quizz1.setId(1L);
        quizz1.setTitle("Funcionou!!!!!!!!!!!!!!!!!");
        quizz1.setQuestions(questions);

        quizzes.add(quizz1);
    }
}
