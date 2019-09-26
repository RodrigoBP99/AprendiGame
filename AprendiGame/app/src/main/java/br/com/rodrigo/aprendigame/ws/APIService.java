package br.com.rodrigo.aprendigame.ws;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Model.Aula;
import br.com.rodrigo.aprendigame.Model.Presenca;
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

    @GET("presenca/{idAula}/{idAluno}")
    Call<List<Presenca>> listPresenca(@Path("idAula") String idAula, @Path("idAluno") String idAluno);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })

    @GET("aula/{idAluno}")
    Call<List<Aula>> listAula(@Path("idAluno") String idAluno);
}
