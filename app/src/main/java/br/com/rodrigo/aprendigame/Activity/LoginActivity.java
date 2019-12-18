package br.com.rodrigo.aprendigame.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import br.com.rodrigo.aprendigame.DB.StudentDAO;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;
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
    @BindView(R.id.editTextLoginUserMatricula)
    EditText editTextUserMatriculaLogin;
    @BindView(R.id.textInputPhoneNumber)
    TextInputLayout textInputEditText;

    public static String NUMERO = "numero";
    public static Student student;

    private StudentDAO studentDAO;

    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Fabric.with(this, new Crashlytics());
        ButterKnife.bind(this);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        studentDAO = new StudentDAO(LoginActivity.this);

        if (firebaseUser != null){
            progressBarVisibility(View.GONE, View.GONE, View.VISIBLE, false);
            getStudent(Long.parseLong(firebaseUser.getProviderId()));
        }
    }

    @OnClick(R.id.textViewCadastroRedirect)
    void cadastroRedirect(){
        Intent cadastroActivity = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(cadastroActivity);
    }

    @OnClick(R.id.buttonLogin) void login(){
        metodoFecharTeclado();
        phoneNumber = editTextUserMatriculaLogin.getText().toString().trim();

        progressBarVisibility(View.GONE, View.GONE, View.VISIBLE, false);

        testPhoneNumber();
    }

    private void testPhoneNumber() {
        if(phoneNumber.isEmpty() || phoneNumber.length() < 10){
            editTextUserMatriculaLogin.setError("Entre com um número válido");
            editTextUserMatriculaLogin.requestFocus();
            progressBarVisibility(View.VISIBLE, View.VISIBLE, View.GONE, true);
        } else {
            try {
                checkUserPhone();
            } catch (Exception e){
                Log.e("PhoneError: ", e.getMessage());
                progressBarVisibility(View.VISIBLE, View.VISIBLE, View.GONE, true);
            }
        }
    }

    private void checkUserPhone() {
        SetupRest.apiService.getUserExistence(phoneNumber).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    if (response.body()) {
                        Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
                        intent.putExtra(NUMERO, phoneNumber);
                        startActivity(intent);
                        finish();
                    } else {
                        editTextUserMatriculaLogin.setError("Número não registrado");
                    }
                }
                progressBarVisibility(View.VISIBLE, View.VISIBLE, View.GONE, true);
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Parece que ocorreu um erro", Toast.LENGTH_LONG).show();
                Log.e("PhoneAPI: ", t.getMessage());
                progressBarVisibility(View.VISIBLE, View.VISIBLE, View.GONE, true);
            }
        });
    }

    private void progressBarVisibility(int visibilityInput, int gone, int visible, boolean b) {
        textInputEditText.setVisibility(visibilityInput);
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

    private void getStudent(Long idStudent) {
        SetupRest.apiService.getStudent(idStudent).enqueue(new Callback<Student>() {
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
                    recoverLocalStudent(idStudent);
                    Snackbar.make(getCurrentFocus(), "Ops! Parece que ocorreu um erro.", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Você está desconectado!", Toast.LENGTH_LONG).show();
                recoverLocalStudent(idStudent);
                progressBarVisibility(View.VISIBLE, View.VISIBLE, View.GONE, true);
            }
        });
    }

    private void recoverLocalStudent(Long idStudent) {
        student = studentDAO.selectUsuario(String.valueOf(idStudent));
        if (student != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}