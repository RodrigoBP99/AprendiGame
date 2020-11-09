package br.com.rodrigo.aprendigame.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.DB.StudentDAO;
import br.com.rodrigo.aprendigame.Model.Answer;
import br.com.rodrigo.aprendigame.Model.AnswerType;
import br.com.rodrigo.aprendigame.Model.Question;
import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizzQuestionActivity extends AppCompatActivity {
    private List<Question> questions = new ArrayList<>();
    private List<Question> correctQuestions= new ArrayList<>();
    private List<Answer> answers = new ArrayList<>();
    private Question question = new Question();

    private Quizz quizz;
    private int position = 0;

    @BindView(R.id.textViewQuestionTitle)
    TextView textViewQuestionTitle;
    @BindView(R.id.radioButtonAnswerOne)
    RadioButton radioButtonOne;
    @BindView(R.id.radioButtonAnswerTwo)
    RadioButton radioButtonTwo;
    @BindView(R.id.radioButtonAnswerThree)
    RadioButton radioButtonThree;
    @BindView(R.id.radioButtonAnswerFour)
    RadioButton radioButtonFour;
    @BindView(R.id.radioGroupAnswers)
    RadioGroup radioGroup;
    @BindView(R.id.textViewTitleToolbarMain)
    TextView textViewTitle;

    private ViewGroup rootView;

    private RadioButton radioButton;
    private Handler handler;
    private Double pointsGet;
    private Double quizzValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz_question);
        ButterKnife.bind(this);
        String id = getIntent().getStringExtra("questionario");
        getQuizz(Long.valueOf(id));
        handler = new Handler();
    }

    private void getQuizz(Long id) {
        SetupRest.apiService.getQuizz(id).enqueue(new Callback<Quizz>() {
            @Override
            public void onResponse(Call<Quizz> call, Response<Quizz> response) {
                if (response.isSuccessful()){
                    quizz = response.body();
                    questions = quizz.getQuestions();

                    setValues();
                } else {
                    try {
                        String messageErro = getErroMessage(response);
                        Toast.makeText(QuizzQuestionActivity.this, messageErro, Toast.LENGTH_LONG).show();
                        finish();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Quizz> call, Throwable t) {
                Toast.makeText(QuizzQuestionActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }

    private void setValues() {
        question = questions.get(position);
        answers = question.getAnswers();
        textViewTitle.setText(quizz.getTitle());
        textViewQuestionTitle.setText(question.getQuestionTittle());
        radioButtonOne.setText(question.getAnswers().get(0).getText());
        radioButtonTwo.setText(question.getAnswers().get(1).getText());
        radioButtonThree.setText(question.getAnswers().get(2).getText());
        radioButtonFour.setText(question.getAnswers().get(3).getText());
    }

    @OnClick(R.id.buttonGetAnswer) void proximaQuestao(){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = this.findViewById(radioId);
        if (radioButton == null) {
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this, R.style.AlertDialogCustom);
            builder.setMessage("Você deve selecionar uma resposta para poder avançar!!").setPositiveButton("OK", (dialogInterface, i) ->
            {
            }).show();
        } else {
            verifyIfAnswerIsCorrect();
            checkPositionAndSetValues();
        }
    }

    private void checkPositionAndSetValues() {
        if (position == (questions.size() -1)) {
            quizzValue = (quizz.getValue()/quizz.getQuestions().size());
            int questionsCorrect = correctQuestions.size();
            pointsGet = (quizzValue * questionsCorrect);

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
            builder.setTitle("Parabéns, você concluiu o Quiz!!").setMessage("Sua nota foi: " + pointsGet + " pontos.").setPositiveButton("Ok", (dialogInterface, i) -> {
                updateStudentPoints();
            }).setCancelable(false).show();
        } else {
            position++;
            radioGroup.clearCheck();
            rootView = findViewById(R.id.linearLayoutQuizzQuestionaActivity);
            setValues();
        }
    }

    private void updateStudentPoints() {
        quizzValue = (quizz.getValue()/quizz.getQuestions().size());

        int questionsCorrect = correctQuestions.size();

        pointsGet = (quizzValue * questionsCorrect);

        quizz.setPoints(pointsGet);
        StudentDAO studentDAO = new StudentDAO(this);
        Student student = studentDAO.checkIfDataExists();
        SetupRest.apiService.updateLevelAndPoints(student.getId(), quizz).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()){
                    studentDAO.clearStudent();
                    studentDAO.salvarUsuario(response.body());
                    finish();
                } else {
                    try {
                        String erroMessage = getErroMessageStudent(response);
                        Toast.makeText(QuizzQuestionActivity.this, erroMessage, Toast.LENGTH_SHORT).show();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(QuizzQuestionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getErroMessageStudent(Response<Student> response) throws IOException {
        ResponseBody responseBody = response.errorBody();
        return responseBody.string();
    }


    private void verifyIfAnswerIsCorrect() {
        int index = radioGroup.indexOfChild(radioButton);

        Answer answer = answers.get(index);

        if (answer.getAnswerType().equals(AnswerType.CORRECT)){
            correctQuestions.add(question);
        }
    }

    private String getErroMessage(Response<Quizz> response) throws IOException {
        ResponseBody responseBody = response.errorBody();
        return responseBody.string();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setTitle("Você tem certeza que deseja sair do questionario?").setMessage("Sua nota será salva de acordo com o progresso atual!")
                .setPositiveButton("Sim", (dialogInterface, i) -> {
                    updateStudentPoints();
                    finish();
                })
                .setNegativeButton("Continuar Avaliação", null).show();
    }
}
