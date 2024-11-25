package Interface;

import DTO.UpdatePassDTO;
import DTO.UsersDTO;
import Reponse.LoginResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiUsers {
    @POST("api/v1/users/login")
    @Headers("Content-Type: application/json")
    Call<LoginResponseDTO> login(@Body UsersDTO usersDTO);

    @POST("api/v1/users/register")
    @Headers("Content-Type: application/json")
    Call<String> register(@Body UsersDTO usersDTO);

    @PUT("api/v1/users/{id}")
    @Headers("Content-Type: application/json")
    Call<String> updatePass(@Header("Authorization") String token, @Path("id") int id, @Body UpdatePassDTO updatePassDTO);
}

