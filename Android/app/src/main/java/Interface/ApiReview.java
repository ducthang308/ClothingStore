package Interface;

import java.util.List;

import DTO.ReviewDTO;
import DTO.ReviewWithUserFullNameDTO;
import Model.Review;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiReview {
    @POST("api/v1/review")
    Call<String> createReview(@Header("Authorization") String token, @Body ReviewDTO reviewDTO);

    @GET("api/v1/review/{productId}")
    Call<List<ReviewWithUserFullNameDTO>> getAllReview(@Path("productId") int productId);
}
