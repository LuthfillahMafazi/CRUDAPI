package co.example.lutfillahmafazi.crudapi.api;

import co.example.lutfillahmafazi.crudapi.model.LoginBody;
import co.example.lutfillahmafazi.crudapi.model.LoginResponse;
import co.example.lutfillahmafazi.crudapi.model.ResponseDetailUser;
import co.example.lutfillahmafazi.crudapi.model.UserData;
import co.example.lutfillahmafazi.crudapi.model.UserResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    // TODO 2
    // Membuat Login
    @POST("/api/login")
    Call<LoginResponse> postLogin(@Body LoginBody loginBody);
    // Membuat request data user
    @GET("/api/users")
    Call<UserResponse> getUser(@Query("per_page") int perPage);
    @GET("/api/users/{id}")
    Call<ResponseDetailUser> getUserData(@Path("id")int id);
}
