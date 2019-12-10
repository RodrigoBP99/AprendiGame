package br.com.rodrigo.aprendigame.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

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

    public static final String STUDENT = "student";
    private String mVerificacaoId;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        ButterKnife.bind(this);
        FirebaseApp.initializeApp(this);

        firebaseAuth = FirebaseAuth.getInstance();

        String numero = getIntent().getStringExtra(LoginActivity.NUMERO);
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
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificacaoId, codigo);
        signInWithPhoneAuthCredential(credential);
    }


    @OnClick(R.id.buttonConfirmCode) void confirmCode(){
        String code = editText.getText().toString();
        Snackbar.make(getCurrentFocus(), "Logando", Snackbar.LENGTH_SHORT).show();
        if (code.isEmpty() || code.length() < 6){
            editText.setError("Codigo invalido");
            editText.requestFocus();
        }
        try {
            verifyCodeSent(code);
        } catch (Exception e){
            Snackbar.make(getCurrentFocus(), "Código expirado", Snackbar.LENGTH_SHORT).show();
        }
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String codigo = phoneAuthCredential.getSmsCode();
            if (codigo != null) {
                editText.setText(codigo);
                verifyCodeSent(codigo);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Log.e("erro login: ", e.getMessage());
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificacaoId = s;
        }
    };

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(AuthenticationActivity.this, task -> {
            try {
                if (task.isSuccessful()) {
                    SetupRest.apiService.getStudent(1L).enqueue(new Callback<Student>() {
                        @Override
                        public void onResponse(Call<Student> call, Response<Student> response) {
                            if (response.isSuccessful()){
                                Student student = response.body();

                                Intent intent = new Intent(AuthenticationActivity.this, MainActivity.class);
                                intent.putExtra(STUDENT, student);
                                startActivity(intent);
                                finish();
                            } else {
                                Snackbar.make(getCurrentFocus(), "Erro ao logar. Servidor dormindo!", Snackbar.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Student> call, Throwable t) {
                            Snackbar.make(getCurrentFocus(), "Usuario não existente!", Snackbar.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                Toast toast = Toast.makeText(this, "Verificação não realizada", Toast.LENGTH_SHORT);
                toast.show();
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
