package br.com.rodrigo.aprendigame.ws;

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
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })

    @POST("novaPresenca")
    Call<Presenca> createPresenca(@Body Presenca presenca);

    @POST("save")
    Call<String> createStudent(@Body Student student);

    @GET("login/{idStudent}")
    Call<Student> getStudent(@Path("idStudent") Long idStudent);

    @GET("quizzes")
    Call<List<Quizz>> getListQuizz();

    @GET("courses/{id}")
    Call<List<CoursesUnit>> getListCourseUnit(@Path("id") Long idStudent);

    @GET("students/exist/{telefone}")
    Call<Boolean> getUserExistence(@Path("telefone") String telefone);
}
