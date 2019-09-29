package br.com.rodrigo.aprendigame.WsHelper;

import br.com.rodrigo.aprendigame.Model.Student;
import br.com.rodrigo.aprendigame.ws.SetupRest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HelperWs {

    public void getStudent(Long idStudent){
        SetupRest.apiService.getStudent(idStudent).enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()){
                    Student student = response.body();
                }
            }

            @Override
            public void onFailure(Call<Student> call, Throwable t) {

            }
        });

    }
}
