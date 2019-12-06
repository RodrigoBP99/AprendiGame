package br.com.rodrigo.aprendigame.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Adapter.NewQuizzAdadpter;
import br.com.rodrigo.aprendigame.Model.Question;
import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class NewQuizzActivity extends AppCompatActivity {

    @BindView(R.id.textViewTitleToolbarMain)
    TextView textView;
    @BindView(R.id.recycleViewNewQuizz)
    RecyclerView recyclerView;
    @BindView(R.id.tabLayoutNewQuiz)
    TabLayout tabLayout;

    private NewQuizzAdadpter newQuizzAdadpter;
    private List<Quizz> quizzes = new ArrayList<>();
    private List<Question> questions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_quizz);
        ButterKnife.bind(this);
        textView.setText("Criar Questionário");

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
        question6.setQuestionTitle("Quem venceu a segunda Guerra?");
        Question question7 = new Question();
        question7.setQuestionTitle("Quem venceu a segunda Guerra?");
        Question question8 = new Question();
        question8.setQuestionTitle("Quem venceu a segunda Guerra?");
        Question question9 = new Question();
        question9.setQuestionTitle("Quem venceu a segunda Guerra?");
        Question question10 = new Question();
        question10.setQuestionTitle("Quem venceu a segunda Guerra?");

/*
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
*/


        Quizz quizz1 = new Quizz();
        quizz1.setTitle("Primeira Guerra");
        quizz1.setQuestions(questions);

        Quizz quizz2 = new Quizz();
        quizz2.setTitle("Segunda Guerra");
        quizz2.setQuestions(questions);

        Quizz quizz3 = new Quizz();
        quizz3.setTitle("Guerra do Confederados");
        quizz3.setQuestions(questions);

        Quizz quizz4 = new Quizz();
        quizz4.setTitle("Revolução Industrial");
        quizz4.setQuestions(questions);

        Quizz quizz5 = new Quizz();
        quizz5.setTitle("Revolta das Vacinas");
        quizz5.setQuestions(questions);

        Quizz quizz6 = new Quizz();
        quizz6.setTitle("Descoberta do Brasil");
        quizz6.setQuestions(questions);

        Quizz quizz7 = new Quizz();
        quizz7.setTitle("Independencia do País");
        quizz7.setQuestions(questions);

        Quizz quizz8 = new Quizz();
        quizz8.setTitle("Revolta de 86");
        quizz8.setQuestions(questions);

        quizzes.add(quizz1);
        quizzes.add(quizz2);
        quizzes.add(quizz3);
        quizzes.add(quizz4);
        quizzes.add(quizz5);
        quizzes.add(quizz6);
        quizzes.add(quizz7);
        quizzes.add(quizz8);

        for (Quizz quizz : quizzes){
            tabLayout.addTab(tabLayout.newTab().setText(quizz.getTitle()));
        }

        newQuizzAdadpter = new NewQuizzAdadpter(questions,this);

        recyclerView.setAdapter(newQuizzAdadpter);
    }
}
