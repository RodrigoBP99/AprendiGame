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

import br.com.rodrigo.aprendigame.DB.StudentDAO;
import br.com.rodrigo.aprendigame.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


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

    private String userName;
    private String senha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        usuarioDAO = new StudentDAO(getApplicationContext());
    }

    @OnClick(R.id.textViewCadastroRedirect)
    void cadastroRedirect(){
        Intent cadastroActivity = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(cadastroActivity);
    }

    @OnClick(R.id.buttonLogin) void login(){
        //metodo fechar teclado
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

        userName = editTextUserMatriculaLogin.getText().toString().trim();
        if(userName.isEmpty() || userName.length() < 10){
            editTextUserMatriculaLogin.setError("Entre com um número válido");
            editTextUserMatriculaLogin.requestFocus();
        } else {
            Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
            intent.putExtra(NUMERO, userName);
            startActivity(intent);
            finish();
        }
        buttonLogin.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        textViewCadastro.setClickable(false);
    }

}