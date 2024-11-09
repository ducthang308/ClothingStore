package Interface;

import java.util.List;

import Model.Discount;
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

public interface ApiDiscounts {
    @POST("api/v1/discounts")
    @Headers("Content-Type: application/json")
    Call<String> createDiscount(@Header("Authorization") String token, @Body Discount discount);

    @GET("api/v1/discounts")
    @Headers("Content-Type: application/json")
    Call<List<Discount>> getDiscounts();

    @PUT("api/v1/discounts/{id}")
    @Headers("Content-Type: application/json")
    Call<ResponseBody> updateDiscount(@Header("Authorization") String token, @Path("id") int id, @Body Discount discount);

    @DELETE("api/v1/discounts/{id}")
    @Headers("Content-Type: application/json")
    Call<Void> deleteDiscount(@Header("Authorization") String token, @Path("id") int id);
}
