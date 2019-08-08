package br.com.rodrigo.aprendigame.ws;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SetupRest {

    public static final APIService apiService;

    static {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://retrofit.com")
                .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();

        apiService = retrofit.create(APIService.class);
    }
}
