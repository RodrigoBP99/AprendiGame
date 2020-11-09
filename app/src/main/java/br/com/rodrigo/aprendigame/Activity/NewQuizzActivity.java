package br.com.rodrigo.aprendigame.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import br.com.rodrigo.aprendigame.Fragments.NewQuizFragment;
import br.com.rodrigo.aprendigame.Fragments.NewQuizQuestionFragment;
import br.com.rodrigo.aprendigame.Model.Question;
import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewQuizzActivity extends AppCompatActivity  implements NewQuizQuestionFragment.CheckedQuestionsPass, NewQuizFragment.TitleNewQuizPass {

    @BindView(R.id.textViewTitleToolbarMain)
    TextView textView;
    @BindView(R.id.tabLayoutNewQuiz)
    TabLayout tabLayout;

    public static String LISTQUIZ = "listQuiz";
    public static String QUIZID = "quizID";
    public static String CHECKEDQUESTIONS = "checkQuestions";
    public static String TITLENEWQUIZ = "titleNewQuiz";
    private int quizId = 0;
    private ArrayList<Quizz> quizzes = new ArrayList<>();
    private ArrayList<Question> checkedQuestions = new ArrayList<>();
    private NewQuizQuestionFragment quizQuestionFragment;
    private Bundle listQuiz = new Bundle();
    private String titleNewQuiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quizz);
        ButterKnife.bind(this);
        textView.setText("Novo Quiz");

        ArrayList<Quizz> quizzActivity = (ArrayList<Quizz>) getIntent().getSerializableExtra(QuizzListActivity.LISTQUIZ);
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
                    if (checkedQuestions.size() > 0) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(CHECKEDQUESTIONS, checkedQuestions);
                        bundle.putString(TITLENEWQUIZ, titleNewQuiz);
                        NewQuizFragment newQuizFragment = new NewQuizFragment();
                        newQuizFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutNewQuizz, newQuizFragment).commit();
                    } else{
                        getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutNewQuizz, new NewQuizFragment()).commit();
                    }
                } else {
                    quizId = (int) tab.getTag();
                    sendArgToQuestionFrag();
                    getSupportFragmentManager().beginTransaction().replace(R.id.linearLayoutNewQuizz, quizQuestionFragment).commit();
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

    @Override
    public void listQuestion(ArrayList<Question> questions) {
        checkedQuestions.addAll(questions);
    }

    @Override
    public void titleNewQuiz(String titleNewQuiz) {
        this.titleNewQuiz = titleNewQuiz;
    }

    private void sendArgToQuestionFrag() {
        listQuiz.putSerializable(LISTQUIZ, quizzes);
        listQuiz.putInt(QUIZID, quizId);
        quizQuestionFragment = new NewQuizQuestionFragment();
        quizQuestionFragment.setArguments(listQuiz);
    }

    private void createTabItems() {
        int i = 1;
        for (Quizz quizz : quizzes){
            int tag = quizzes.indexOf(quizz);
            tabLayout.addTab(tabLayout.newTab().setText("Aula " + i).setTag(tag));
            i++;
        }
    }
}
