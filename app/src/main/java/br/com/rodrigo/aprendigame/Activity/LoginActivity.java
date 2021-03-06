package br.com.rodrigo.aprendigame.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.io.IOException;

import br.com.rodrigo.aprendigame.DB.StudentDAO;
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


public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.buttonLogin)
    Button buttonLogin;
    @BindView(R.id.progressBarLogin)
    ProgressBar progressBar;
    @BindView(R.id.textViewCadastroRedirect)
    TextView textViewCadastro;
    @BindView(R.id.editTextLoginStudentRegistration)
    EditText editTextLoginStudentRegistration;
    @BindView(R.id.textInputStudentRegistration)
    TextInputLayout textInputStudentRegistration;

    @BindView(R.id.editTextLoginStudentPassword)
    EditText editTextStudentPassword;

    @BindView(R.id.textInputStudentPassword)
    TextInputLayout textInputStudentPassword;

    public static Student student;

    public Student studentLogin;
    private StudentDAO studentDAO;

    private String registration;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
        crashlytics.setCrashlyticsCollectionEnabled(true);
        crashlytics.log("E/TAG: crash");

        ButterKnife.bind(this);

        studentDAO = new StudentDAO(LoginActivity.this);
        studentLogin = studentDAO.checkIfDataExists();

        if (studentLogin.getRegistration() != null){
            progressBarVisibility(View.GONE, View.GONE, View.VISIBLE, false);
            getStudent(studentLogin);
        }

    }

    private void createSnackBar(String message) {
        View parentLayout = findViewById(R.id.layoutLogin);
        Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.textViewCadastroRedirect)
    void cadastroRedirect(){
        Intent cadastroActivity = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(cadastroActivity);
    }

    @OnClick(R.id.buttonLogin) void login(){
        metodoFecharTeclado();
        registration = editTextLoginStudentRegistration.getText().toString().trim();
        password = editTextStudentPassword.getText().toString().trim();

        progressBarVisibility(View.GONE, View.GONE, View.VISIBLE, false);

        validatRegistrationAndPassword();
    }

    private void validatRegistrationAndPassword() {
        if(registration.isEmpty()){
            editTextLoginStudentRegistration.setError("Campo nao pode ser vazio");
            editTextLoginStudentRegistration.requestFocus();
            progressBarVisibility(View.VISIBLE, View.VISIBLE, View.GONE, true);
        } else if (password.isEmpty()){
                editTextStudentPassword.setError("Campo nao pode ser vazio");
                editTextStudentPassword.requestFocus();
                progressBarVisibility(View.VISIBLE, View.VISIBLE, View.GONE, true);
        } else {
            studentLogin = new Student();
            studentLogin.setRegistration(registration);
            studentLogin.setPassword(password);

            getStudent(studentLogin);
        }
    }

    private void progressBarVisibility(int visibilityInput, int gone, int visible, boolean b) {
        textInputStudentRegistration.setVisibility(visibilityInput);
        textInputStudentPassword.setVisibility(visibilityInput);
        buttonLogin.setVisibility(gone);
        progressBar.setVisibility(visible);
        textViewCadastro.setClickable(b);
    }

    private void metodoFecharTeclado() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void getStudent(Student studentL) {
        SetupRest.apiService.studentLogin(studentL).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()){
                    student = response.body();

                    studentDAO.clearStudent();
                    studentDAO.salvarUsuario(student);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    progressBarVisibility(View.VISIBLE, View.VISIBLE, View.GONE, true);
                    try {
                        String errormesage = getErroMessage(response);
                        createSnackBar(errormesage);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                recoverLocalStudent();
                progressBarVisibility(View.VISIBLE, View.VISIBLE, View.GONE, true);
                createSnackBar(t.getMessage());
            }
        });
    }


    private String getErroMessage(Response<Student> response) throws IOException {
        ResponseBody responseBody = response.errorBody();
        return responseBody.string();
    }

    private void recoverLocalStudent() {
        student = studentDAO.checkIfDataExists();
        if (student.getRegistration() != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}