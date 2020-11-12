package br.com.rodrigo.aprendigame.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import br.com.rodrigo.aprendigame.DB.StudentDAO;
import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.R;
import br.com.rodrigo.aprendigame.glide.GlideApp;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPerfilActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.imageViewEditarPerfilFoto)
    ImageView imageViewPerfil;
    @BindView(R.id.editTextNameEditPerfil)
    EditText editTextPerfilNome;
    @BindView(R.id.editTextSchoolNameEditPerfil)
    EditText editTextSchoolName;
    @BindView(R.id.editTextBirthDayEditPerfil)
    EditText editTextBirthday;
    @BindView(R.id.toolbarEditarPerfil)
    Toolbar toolbar;
    @BindView(R.id.textViewTitleToolbarMain)
    TextView textViewTitleToolbar;

    private String fotoSelecionada;
    private Student student = new Student();
    private StudentDAO studentDAO;
    private int daySelect, monthSelect, yearSelect;
    private int dayFinal, monthFinal, yearFinal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        ButterKnife.bind(this);

        setToolbar();

        studentDAO = new StudentDAO(EditarPerfilActivity.this);
        Long id = studentDAO.checkIfDataExists().getId();

        SetupRest.apiService.getStudent(id).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()){
                    student = response.body();
                    setUserInformation(student);
                } else {
                    try {
                        String erroMessage = getErroMessage(response);
                        createSnackBar(erroMessage);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                Snackbar.make(getCurrentFocus(), t.getMessage(), Snackbar.LENGTH_LONG).show();
                student = studentDAO.checkIfDataExists();

                setUserInformation(student);
            }
        });
    }

    private void createSnackBar(String message) {
        View parentView = findViewById(R.id.layoutEditPerfil);
        Snackbar.make(parentView, message, Snackbar.LENGTH_LONG).show();
    }

    private String getErroMessage(Response<Student> response) throws IOException {
        ResponseBody responseBody = response.errorBody();

        return responseBody.string();
    }

    @Override
    protected void onResume() {
        super.onResume();
        textViewTitleToolbar.setText(getString(R.string.editar_perfil));
    }

    private void setUserInformation(Student student) {
        try {
            editTextPerfilNome.setText(student.getName());
            editTextSchoolName.setText(student.getSchoolName());
            fotoSelecionada = student.getPhoto();
            editTextBirthday.setText(student.getBirthday());
            GlideApp.with(EditarPerfilActivity.this).load(student.getPhoto()).placeholder(R.mipmap.perfil_foto_launcher_round).circleCrop().into(imageViewPerfil);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClick(R.id.imageViewEditarPerfilFoto) void trocarFoto() {
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
                    Glide.with(EditarPerfilActivity.this).load(new BitmapDrawable(bitmap)).circleCrop().into(imageViewPerfil);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @OnClick(R.id.editTextBirthDayEditPerfil) void pegarData(){
        Calendar data = Calendar.getInstance();
        daySelect = data.get(Calendar.DAY_OF_MONTH);
        monthSelect = data.get(Calendar.MONTH);
        yearSelect = data.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(EditarPerfilActivity.this, EditarPerfilActivity.this, yearSelect, monthSelect, daySelect);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        yearFinal = year;
        monthFinal = month;
        dayFinal = day;
        if (yearFinal < (yearSelect - 4)) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH, dayFinal);
            calendar.set(Calendar.MONTH, monthFinal);
            calendar.set(Calendar.YEAR, yearFinal);
            Date date = calendar.getTime();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String horaDataAtual = simpleDateFormat.format(date);
            editTextBirthday.setText(horaDataAtual);
        } else {
            Toast.makeText(EditarPerfilActivity.this, "Data invalida", Toast.LENGTH_LONG).show();
        }
    }

    private void setToolbar() {
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.return_icon);
        toolbar.setNavigationOnClickListener(v -> {
            alertCancelarEdicao();
        });
    }

    private void alertCancelarEdicao() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogCustom);
        builder.setMessage(getString(R.string.deseja_cancelar_edicao)).setPositiveButton(getString(R.string.sim),
                (dialogInterface, i) -> finish())
                .setNegativeButton(getString(R.string.nao), null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_editar_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.salvarPerfil) {
            String nome = editTextPerfilNome.getText().toString().trim();
            String schoolName = editTextSchoolName.getText().toString().trim();
            String birthday = editTextBirthday.getText().toString().trim();

            View view = getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            if (nome.isEmpty()) {
                editTextPerfilNome.setError("Preencha o campo");
            } else if( schoolName.isEmpty()) {
                editTextSchoolName.setError("Preencha o campo");
            } else if(birthday.isEmpty()){
                editTextBirthday.setError("Selecione uma data");
            } else {
                student.setName(nome);
                student.setSchoolName(schoolName);
                student.setBirthday(birthday);
                student.setPhoto(fotoSelecionada);

                updateStudent(student);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        alertCancelarEdicao();
    }

    private void updateStudent(Student student){
        SetupRest.apiService.updateStudent(student.getId(), student).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()){
                    studentDAO.clearStudent();
                    studentDAO.salvarUsuario(response.body());
                    finish();
                } else {
                    Log.d("Student: ", student.toString());
                    try {
                        String erroMessage = getErroMessage(response);
                        createSnackBar(erroMessage);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                createSnackBar(t.getMessage());
                finish();
            }
        });
    }
}
