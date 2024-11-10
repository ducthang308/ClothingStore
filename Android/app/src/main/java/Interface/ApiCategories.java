package Interface;

import java.util.List;

import Model.Category;
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
import retrofit2.http.Path;

public interface ApiCategories {
    @POST("api/v1/categories")
    @Headers("Content-Type: application/json")
    Call<String> createCategory(@Header("Authorization") String token, @Body Category category);

    @GET("api/v1/categories")
    @Headers("Content-Type: application/json")
    Call<List<Category>> getCategories();

    @PUT("api/v1/categories/{id}")
    @Headers("Content-Type: application/json")
    Call<ResponseBody> updateCategory(@Header("Authorization") String token, @Path("id") int id, @Body Category category);

    @DELETE("api/v1/categories/{id}")
    @Headers("Content-Type: application/json")
    Call<Void> deleteCategory(@Header("Authorization") String token, @Path("id") int id);
}
