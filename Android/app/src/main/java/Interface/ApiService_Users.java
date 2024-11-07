package Interface;

import DTO.UsersDTO;
import Reponse.LoginResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService_Users {
    @POST("api/v1/users/login")
    @Headers("Content-Type: application/json")
    Call<LoginResponseDTO> login(@Body UsersDTO usersDTO);

    @POST("api/v1/users/register")
    @Headers("Content-Type: application/json")
    Call<String> register(@Body UsersDTO usersDTO);
}



//    Gson gson = new GsonBuilder()
//            .setDateFormat("yy-mm-dd HH:mm:ss ")
//            .create();
//
//    APIservice apiservice = new Retrofit.Builder()
//            .baseUrl("http://localhost/APILogin/")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//            .create(APIservice.class);
//
//
//    @GET("User")
//    Call<List<User>> getListuser(String key);



//    Gson gson = new GsonBuilder()
//            .setDateFormat("yy-mm-dd HH:mm:ss ")
//            .create();
//
//    APIservice apiservice = new Retrofit.Builder()
//            .baseUrl("")
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//            .create(APIservice.class);
//
//
//    @GET("User")
//    Call<List<User>> getListuser(@Query("") String key);

