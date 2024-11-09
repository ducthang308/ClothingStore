package Interface;

import DTO.UsersDTO;
import Reponse.LoginResponseDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiUsers {
    @POST("api/v1/users/login")
    @Headers("Content-Type: application/json")
    Call<LoginResponseDTO> login(@Body UsersDTO usersDTO);

    @POST("api/v1/users/register")
    @Headers("Content-Type: application/json")
    Call<String> register(@Body UsersDTO usersDTO);
}

