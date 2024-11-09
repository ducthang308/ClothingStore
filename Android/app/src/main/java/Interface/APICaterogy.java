package Interface;

import java.util.List;

import DTO.CategoriesDTO;
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
    Call<List<CategoriesDTO>> getAllCategories();

    @PUT("categories/{id}")
    Call<String> updateCategory(@Header("Authorization") String token, @Path("id") Long id, @Body CategoriesDTO categoriesDTO);

    @DELETE("categories/{id}")
    Call<Void> deleteCategory(@Header("Authorization") String token, @Path("id") Long id);
}
