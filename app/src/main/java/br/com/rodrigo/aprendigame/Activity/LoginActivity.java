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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import br.com.rodrigo.aprendigame.DB.StudentDAO;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
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
        Fabric.with(this, new Crashlytics());
        ButterKnife.bind(this);

        studentDAO = new StudentDAO(LoginActivity.this);

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

            getStudent();
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

    private void getStudent() {
        SetupRest.apiService.studentLogin(studentLogin).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()){
                    student = response.body();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    progressBarVisibility(View.VISIBLE, View.VISIBLE, View.GONE, true);
                    try {
                        String errormesage = getErroMessage(response);
                        Toast.makeText(LoginActivity.this, errormesage.toString(), Toast.LENGTH_LONG).show();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }


            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
                progressBarVisibility(View.VISIBLE, View.VISIBLE, View.GONE, true);
            }
        });
    }


    private String getErroMessage(Response<Student> response) throws IOException {
        ResponseBody responseBody = response.errorBody();

        return responseBody.string();
    }

//    private void recoverLocalStudent(Long idStudent) {
//        student = studentDAO.selectUsuario(String.valueOf(idStudent));
//        if (student != null) {
//            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }
}