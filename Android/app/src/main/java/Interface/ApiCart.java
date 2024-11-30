package Interface;

import java.util.List;

import Model.Carts;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiCart {
    @POST("api/v1/carts")
    @Headers("Content-Type: application/json")
    Call<String> createCart(@Header("Authorization") String token, @Body Carts carts);

    @GET("api/v1/carts")
    Call<List<Carts>> getAllCarts(@Header("Authorization") String token);

    @GET("api/v1/carts/{userId}")
    Call<String> getCartByUserId(@Header("Authorization") String token, @Path("userId") int userId);

    @PUT("api/v1/carts/{id}")
    Call<ResponseBody> updateCart(@Header("Authorization") String token, @Path("id") int id);

    @DELETE("api/v1/carts/{id}")
    Call<Void> deleteCart(@Header("Authorization") String token, @Path("id") int id);
}
