package br.com.rodrigo.aprendigame.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

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
        question4.setQuestionTitle("Quais paises participaram da segunda guerra");
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

        Quizz quizz = new Quizz();
        quizz.setQuestions(questions);
        quizz.setTitle("Segunda Guerra mundial");

        Quizz quizz1 = new Quizz();
        quizz1.setQuestions(questions);
        quizz1.setTitle("Guerra Civil Americana");

        Quizz quizz2 = new Quizz();
        quizz2.setQuestions(questions);
        quizz2.setTitle("Geografia");

        Quizz quizz3 = new Quizz();
        quizz3.setQuestions(questions);
        quizz3.setTitle("Matématica");

        Quizz quizz4 = new Quizz();
        quizz4.setQuestions(questions);
        quizz4.setTitle("Portugues");

        quizzes.add(quizz);
        quizzes.add(quizz1);
        quizzes.add(quizz2);
        quizzes.add(quizz3);
        quizzes.add(quizz4);

        newQuizzAdadpter = new NewQuizzAdadpter(quizzes,this);

        recyclerView.setAdapter(newQuizzAdadpter);
    }
}
