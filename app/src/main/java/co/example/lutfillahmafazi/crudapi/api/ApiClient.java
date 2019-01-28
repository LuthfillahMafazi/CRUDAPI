package co.example.lutfillahmafazi.crudapi.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    // TODO 1
    private static Retrofit retrofit = null;
    // Membuat method return getClient
    public static Retrofit getClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
