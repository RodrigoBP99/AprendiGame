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

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import br.com.rodrigo.aprendigame.DB.StudentDAO;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private StudentDAO usuarioDAO;

    @BindView(R.id.buttonLogin)
    Button buttonLogin;

    @BindView(R.id.progressBarLogin)
    ProgressBar progressBar;

    @BindView(R.id.textViewCadastroRedirect)
    TextView textViewCadastro;

    @BindView(R.id.editTextLoginUserMatricula)
    EditText editTextUserMatriculaLogin;

    public static String NUMERO = "numero";

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private StudentDAO studentDAO;

    private String phoneNumber;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        studentDAO = new StudentDAO(LoginActivity.this);

        if (firebaseUser != null){
            getStudent(Long.valueOf(firebaseUser.getPhoneNumber()));
            progressBarVisibility(View.GONE, View.GONE, View.VISIBLE, false);
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

        if(phoneNumber.isEmpty() || phoneNumber.length() < 10){
            editTextUserMatriculaLogin.setError("Entre com um número válido");
            editTextUserMatriculaLogin.requestFocus();
            progressBarVisibility(View.VISIBLE, View.VISIBLE, View.GONE, true);
        } else {
            Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
            intent.putExtra(NUMERO, phoneNumber);
            startActivity(intent);
            finish();
        }
    }

    private void progressBarVisibility(int visibility, int gone, int visible, boolean b) {
        editTextUserMatriculaLogin.setVisibility(visibility);
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

    private void getStudent(Long numero) {
        SetupRest.apiService.getStudent(numero).enqueue(new Callback<Student>() {
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
                    if (studentDAO.selectUsuario(String.valueOf(numero)) != null) {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    Snackbar.make(getCurrentFocus(), "Ops! Parece que ocorreu um erro.", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Você está desconectado!", Toast.LENGTH_LONG).show();
                if (studentDAO.selectUsuario(String.valueOf(numero)) != null) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                progressBarVisibility(View.VISIBLE, View.VISIBLE, View.GONE, true);
            }
        });
    }
}