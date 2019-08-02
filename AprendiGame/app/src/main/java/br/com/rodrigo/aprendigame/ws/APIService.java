package br.com.rodrigo.aprendigame.ws;

import java.util.ArrayList;
import java.util.List;

import br.com.rodrigo.aprendigame.Model.Presenca;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface APIService {

    @POST("novaPresenca")
    Call<Presenca> createPresenca(@Body Presenca presenca);

    /*
    @POST("listaPresencas")
    Call<ArrayList<Presenca>> enviaLista(@Body ArrayList<Presenca> presencas); */


    @POST("listaPresencas")
    Call<String> enviaLista(@Body ArrayList<Presenca> presencas);

    @Headers({
            "Content-Type: application/json",
            "Accept: application/json"
    })
    @GET("presenca")
    Call<List<Presenca>> listPresenca();
}