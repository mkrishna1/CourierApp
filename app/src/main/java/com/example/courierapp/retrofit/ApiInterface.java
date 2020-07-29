package com.example.courierapp.retrofit;

import com.example.courierapp.model.DirectionsApiResponseDTO;
import com.example.courierapp.model.LoginAuthResponse;
import com.example.courierapp.model.LoginRequest;
import com.example.courierapp.model.LoginResponse;
import com.example.courierapp.model.RegisterRequest;
import com.example.courierapp.model.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    //Login
   /* @Headers({"Content-Type:application/json"})
    @POST("get_single_user.php")
    Call<SignedInJSONResponse> getLoginUser(@Body SignInDTO signInDTO);
*/

   /*@GET(BuildConfig.NAJDI_END_POINTS + "products")
    @Headers({"Content-Type:application/json", "Authorization" + ": " + Constants.BASIC_64_AUTH})
    Call<List<ProductListResponse>> getProducts();
*/

    @GET("/maps/api/directions/json")
    Call<DirectionsApiResponseDTO> getRouteDistance(@Query("origin") String origin, @Query("destination") String destination, @Query("key") String key);

    /*Call<DirectionsApiResponseDTO> getRouteDistance(String s, double v, double v1, String apiKey);*/

    @POST("register")
    Call<RegisterResponse> doRegister(@Body RegisterRequest registerRequest);

    @POST("login")
    @Headers({"Content-Type:application/json"})
    Call<LoginAuthResponse> doCheckLogin(@Body LoginRequest loginRequest);

    //@Headers({"Content-Type:application/json", "Authorization" + ": " + AppConstant.AUTHTOKEN})
    @POST("User")
    @Headers({"Content-Type:application/json"})
    Call<LoginResponse> doGetUserDetails(@Header("Authorization") String token);

}
