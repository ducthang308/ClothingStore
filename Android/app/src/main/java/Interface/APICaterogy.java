package Interface;

import java.util.List;

import DTO.CategoriesDTO;
import Model.Category;
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

public interface APICaterogy {
    @POST("api/v1/categories")
    Call<String> createCategory(@Header("Authorization") String token, @Body CategoriesDTO categoriesDTO);

    @GET("api/v1/categories")
    Call<List<Category>> getAllCategories();

    @PUT("api/v1/categories/{id}")
    Call<ResponseBody> updateCategory(@Header("Authorization") String token, @Path("id") int id, @Body CategoriesDTO categoriesDTO);

    @DELETE("api/v1/categories/{id}")
    @Headers("Content-Type: application/json")
    Call<Void> deleteCategory(@Header("Authorization") String token, @Path("id") int id);
}
