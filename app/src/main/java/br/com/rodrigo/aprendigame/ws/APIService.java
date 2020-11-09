package br.com.rodrigo.aprendigame.ws;

import java.util.List;

import br.com.rodrigo.aprendigame.Model.CourseClass;
import br.com.rodrigo.aprendigame.Model.CoursesUnit;
import br.com.rodrigo.aprendigame.Model.Presenc;
import br.com.rodrigo.aprendigame.Model.Quizz;
import br.com.rodrigo.aprendigame.Model.Student;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface APIService {
    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })

//    @POST("novaPresenca")
//    Call<Presenc> createPresenca(@Body Presenc presenc);

    @POST("/api/student/register")
    Call<Student> createStudent(@Body Student student);

    @POST("/api/student/login")
    Call<Student> studentLogin(@Body Student student);

    @GET("/api/student/getStudent/{id}")
    Call<Student> getStudent(@Path("id") Long id);

    @GET("/api/courseClass/getCourseClass/{id}")
    Call<CourseClass> getCourseClass(@Path("id") Long id);

    @PUT("/api/student/update/{id}")
    Call<Student> updateStudent(@Path("id") Long id, @Body Student student);

    @GET("/api/quizz/getQuizz/{id}")
    Call<Quizz> getQuizz(@Path("id") Long id);

    @PUT("/api/student/{id}/updatePointsAndLevel")
    Call<Student> updateLevelAndPoints(@Path("id") Long id, @Body Quizz quizz);

//    @GET("quizzes")
//    Call<List<Quizz>> getListQuizz();
//
//    @GET("courses/{id}")
//    Call<List<CoursesUnit>> getListCourseUnit(@Path("id") Long idStudent);
//
//    @GET("students/exist/{telefone}")
//    Call<Boolean> getUserExistence(@Path("telefone") String telefone);
}
