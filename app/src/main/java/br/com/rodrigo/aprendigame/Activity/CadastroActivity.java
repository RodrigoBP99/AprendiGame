package br.com.rodrigo.aprendigame.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CadastroActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Student student = new Student();
    private String fotoSelecionada;
    private String schoolName;
    private String cursoNome;
    private String nome;
    private String userRegistration;
    private String dataNascimento;
    private String password;
    private String confirmPassword;

    @BindView(R.id.editTextUserMatriculaCadastro)
            EditText editTextRegistrationCadastro;
    @BindView(R.id.editTextNomeCadastro)
            EditText editTextNome;
    @BindView(R.id.editTextSchoolNameCadastro)
            EditText editTextSchoolNameCadastro;
    @BindView(R.id.editTextCursoCadastro)
            EditText editTextCourseName;
    @BindView(R.id.toolbarCadastro)
            Toolbar toolbar;
    @BindView(R.id.textViewTitleToolbarMain)
            TextView textViewToolbar;
    @BindView(R.id.imageViewStudentRegister)
            ImageView imageViewRegister;
    @BindView(R.id.editTextBirthDayRegister)
            EditText editTextBirthDay;
    @BindView(R.id.editTextPasswordCadastro)
            EditText editTextPassword;
    @BindView(R.id.editTextConfirmPasswordCadastro)
            EditText editTextConfirmPassword;

    int dia, mes, ano;
    int diaFinal, mesFinal, anoFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        ButterKnife.bind(this);

        textViewToolbar.setText(getString(R.string.cadastro));
    }

    @OnClick(R.id.editTextBirthDayRegister) void pegarData(){
        Calendar data = Calendar.getInstance();
        dia = data.get(Calendar.DAY_OF_MONTH);
        mes = data.get(Calendar.MONTH);
        ano = data.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(CadastroActivity.this, CadastroActivity.this, ano, mes, dia);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        anoFinal = year;
        mesFinal = month;
        diaFinal = day;
        if (anoFinal < (ano - 4)) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, diaFinal);
            calendar.set(Calendar.MONTH, mesFinal);
            calendar.set(Calendar.YEAR, anoFinal);
            Date date = calendar.getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String horaDataAtual = simpleDateFormat.format(date);
            editTextBirthDay.setText(horaDataAtual);
        } else {
            Toast.makeText(CadastroActivity.this, "Data invalida", Toast.LENGTH_LONG).show();
        }
    }

    @OnClick(R.id.imageViewStudentRegister)
    void carregarImagem() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            if (data != null) {
                fotoSelecionada = String.valueOf(data.getData());
                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(fotoSelecionada));
                    Glide.with(CadastroActivity.this).load(new BitmapDrawable(bitmap)).circleCrop().into(imageViewRegister);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @OnClick(R.id.buttonConfirmarCadastro)
    void confirmRegister() {
        userRegistration = editTextRegistrationCadastro.getText().toString().trim();
        nome = editTextNome.getText().toString().trim();
        cursoNome = editTextCourseName.getText().toString().trim();
        schoolName = editTextSchoolNameCadastro.getText().toString().trim();
        dataNascimento = editTextBirthDay.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();
        confirmPassword = editTextConfirmPassword.getText().toString().trim();
        cadastroUsuario();
    }

    @OnClick(R.id.buttonCancelarCadastro)
    void cancelRegister() {
        alertaRegisterCancel();
    }

    @Override
    public void onBackPressed() {
        alertaRegisterCancel();
    }

    private void alertaRegisterCancel() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(CadastroActivity.this, R.style.AlertDialogCustom);
        alerta.setTitle(R.string.titulo_alerta_cancelar_cadastro)
                .setPositiveButton(R.string.sim, (dialog, which) ->
                        finish()).setNegativeButton(R.string.nao, null)
                .setMessage(R.string.messagem_cancelar_cadastro).show();
    }

    public void cadastroUsuario() {
        //Metodo para fechar teclado
        fecharTeclado();
        if (nome.isEmpty()) {
            editTextNome.setError("Campo vazio");
        } else if (userRegistration.isEmpty()) {
            editTextRegistrationCadastro.setError("Campo vazio");
        } else if (schoolName.isEmpty()) {
            editTextSchoolNameCadastro.setError("Campo vazio");
        } else if (cursoNome.isEmpty()) {
            editTextCourseName.setError("Campo vazio");
        } else if (dataNascimento.isEmpty()) {
            Toast.makeText(this, "Preencha sua Data de Nascimento", Toast.LENGTH_LONG).show();
        }  else if (password.isEmpty()) {
            editTextPassword.setError("Campo Vazio");
        } else if (confirmPassword.isEmpty()) {
            editTextConfirmPassword.setError("Campo vazio");
        } else if (!confirmPassword.equals(password)){
            editTextConfirmPassword.setError("As senhas devem ser iguais");
        } else if (fotoSelecionada == null || fotoSelecionada.isEmpty()) {
            Toast.makeText(this, "Selecione uma foto!", Toast.LENGTH_LONG).show();
        } else {
            student.setName(nome);
            student.setRegistration(userRegistration);
            student.setSchoolName(schoolName);
            student.setCourseName(cursoNome);
            student.setPhoto(fotoSelecionada);
            student.setBirthday(dataNascimento);
            student.setPassword(password);


            createStudent(student);
        }
    }

    private void fecharTeclado() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void createStudent(Student student) {
        SetupRest.apiService.createStudent(student).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CadastroActivity.this, "Usuario '" + response.body().getName() + "' cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    try {
                        String errormesage = getErrorMesage(response);
                        Toast.makeText(CadastroActivity.this, errormesage.toString(), Toast.LENGTH_LONG).show();
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Toast.makeText(CadastroActivity.this, t.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private String getErrorMesage(Response<Student> response) throws IOException {
        ResponseBody responseBody = new ResponseBody() {
            @Nullable
            @Override
            public MediaType contentType() {
                return null;
            }

            @Override
            public long contentLength() {
                return 0;
            }

            @Override
            public BufferedSource source() {
                return null;
            }
        };
        responseBody = response.errorBody();
        return responseBody.string();
    }

}
