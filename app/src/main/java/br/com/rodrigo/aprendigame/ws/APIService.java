package br.com.rodrigo.aprendigame.ws;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Model.CoursesUnit;
import br.com.rodrigo.aprendigame.Model.Presenca;
import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.Model.Student;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface APIService {

    @POST("novaPresenca/{idAula}")
    Call<Presenca> createPresenca(@Path("idAula") String idAula, @Body Presenca presenca);

    @POST("listaPresencas/{idAula}")
    Call<ArrayList<Presenca>> sendList(@Path("idAula") String idAula, @Body ArrayList<Presenca> presencas);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })

    @GET("students/{idStudent}")
    Call<Student> getStudent(@Path("idStudent") Long idStudent);

    @GET("quizzes")
    Call<List<Quizz>> getListQuizz();

    @GET("/courses/{id}")
    Call<List<CoursesUnit>> getListCourseUnit(@Path("id") Long idStudent);
}
