package Interface;

import java.util.List;

import DTO.ProductDTO;
import DTO.ProductImageDTO;
import Model.Discount;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiProduct {
    @POST("api/v1/product")
    @Headers("Content-Type: application/json")
    Call<String> createProduct(@Header("Authorization") String token, @Body ProductDTO productDTO);

    @Multipart
    @POST("api/v1/uploads/{id}")
    Call<List<ProductImageDTO>> uploadImages(
            @Header("Authorization") String token,
            @Path("id") Long productId,
            @Part List<MultipartBody.Part> files
    );

    @GET("api/v1/product")
    @Headers("Content-Type: application/json")
    Call<List<ProductDTO>> getProducts();

    @GET("api/v1/product/{id}")
    @Headers("Content-Type: application/json")
    Call<List<ProductDTO>> getProductById(@Path("id") int id);

    @GET("api/v1/product/by-ids")
    @Headers("Content-Type: application/json")
    Call<List<ProductDTO>> getProductByIds(@Query("ids") String ids);

    @PUT("api/v1/product/{id}")
    @Headers("Content-Type: application/json")
    Call<ResponseBody> updateProduct(@Header("Authorization") String token, @Path("id") int id, @Body Discount discount);

    @DELETE("api/v1/product/{id}")
    @Headers("Content-Type: application/json")
    Call<Void> deleteProduct(@Header("Authorization") String token, @Path("id") int id);
}
