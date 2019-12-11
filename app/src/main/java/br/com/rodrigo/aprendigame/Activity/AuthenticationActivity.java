package br.com.rodrigo.aprendigame.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

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

public class AuthenticationActivity extends AppCompatActivity {

    @BindView(R.id.editTextAuthenticationCode)
    EditText editText;
    @BindView(R.id.progressBarAuthentication)
    ProgressBar progressBar;
    @BindView(R.id.buttonConfirmCode)
    Button button;

    private String verificacaoId;
    private FirebaseAuth firebaseAuth;
    private String numero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);
        FirebaseApp.initializeApp(this);
        firebaseAuth = FirebaseAuth.getInstance();

        numero = getIntent().getStringExtra(LoginActivity.NUMERO);
        sendAuthenticationCode(numero);
    }

    private void sendAuthenticationCode(String mobile) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+55" + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBacks);
    }

    private void verifyCodeSent(String codigo) {
        signInWithPhoneAuthCredential(PhoneAuthProvider.getCredential(verificacaoId, codigo));
    }

    @OnClick(R.id.buttonConfirmCode) void confirmCode(){
        String code = editText.getText().toString();
        hideKeybord();
        if (code.isEmpty() || code.length() < 6){
            editText.setError("Codigo invalido");
            editText.requestFocus();
        } else {
            try {
                verifyCodeSent(code);
                Snackbar.make(getCurrentFocus(), "Logando", Snackbar.LENGTH_INDEFINITE).show();
            } catch (Exception e) {
                Log.e("ErroCodigo: ", e.getMessage());
                Snackbar.make(getCurrentFocus(), "Código expirado", Snackbar.LENGTH_SHORT).show();
                progressBarVisibility(View.VISIBLE, View.GONE);
            }
        }
    }

    private void hideKeybord() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void progressBarVisibility(int visible, int gone) {
        button.setVisibility(visible);
        progressBar.setVisibility(gone);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                editText.setText(code);
                verifyCodeSent(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.e("ErroFireBaseCode: ", e.getMessage());
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificacaoId = s;
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(AuthenticationActivity.this, task -> {
            try {
                if (task.isSuccessful()) {
                    getStudent(Long.valueOf(numero));
                }
            } catch (Exception e) {
                Log.e("ErroStudentLogin: ", e.getMessage());
                Toast toast = Toast.makeText(this, "Verificação não realizada", Toast.LENGTH_SHORT);
                toast.show();
            }

        });
    }

    private void getStudent(Long numero) {
        SetupRest.apiService.getStudent(numero).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()){
                    Student student = response.body();

                    StudentDAO studentDAO = new StudentDAO(AuthenticationActivity.this);
                    studentDAO.clearStudent();
                    studentDAO.salvarUsuario(student);

                    Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
                    intent.putExtra(LoginActivity.NUMERO, numero);
                    startActivity(intent);
                    finish();
                } else {
                    Snackbar.make(getCurrentFocus(), "Ops! Parece que ocorreu um erro.", Snackbar.LENGTH_LONG).show();
                    progressBarVisibility(View.VISIBLE, View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Log.e("GetStudentErro: ", t.getMessage());
                Snackbar.make(getCurrentFocus(), "Você não tem conexão com a Internet!", Snackbar.LENGTH_LONG).show();
                progressBarVisibility(View.VISIBLE, View.GONE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
