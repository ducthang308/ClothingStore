package Interface;

import java.util.List;
import java.util.Map;

import DTO.CartItemsDTO;
import Model.CartItems;
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
import retrofit2.http.Query;

public interface ApiCartItems {
    @POST("api/v1/cartitems")
    @Headers("Content-Type: application/json")
    Call<String> createCartItem(@Header("Authorization") String token, @Body CartItems cartItemsDTO);

    @GET("api/v1/cartitems")
    Call<List<CartItems>> getAllCartItems(@Header("Authorization") String token);

    @GET("api/v1/cartitems/cart/{cartId}")
    Call<List<CartItemsDTO>> getAllCartItemsByCartId(@Header("Authorization") String token, @Path("cartId") int cartId);


    @PUT("api/v1/cartitems/{id}")
    Call<ResponseBody> updateCartItem(@Header("Authorization") String token, @Path("id") int id, @Body CartItemsDTO cartItems);

    @DELETE("api/v1/cartitems")
    Call<Void> deleteCartItem(@Header("Authorization") String token, @Query("cartId") int cartId, @Query("productId") int productId);
}
