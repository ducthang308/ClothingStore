package Interface;
import java.util.List;

import DTO.OrdersDTO;
import Model.Order;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
public interface ApiOrders {
    @POST("api/v1/orders")
    @Headers("Content-Type: application/json")
    Call<OrdersDTO> createOrder(@Header("Authorization") String token, @Body OrdersDTO odersDTO);
    // API lấy thông tin chi tiết của đơn hàng
    @GET("api/v1/orders/{id}")
    Call<List<OrdersDTO>> getOrderById(
            @Header("Authorization") String token,
            @Path("id") Long id);

    @GET("api/v1/orders/user/{user_id}")
    Call<List<Order>> getAllOrdersByUser(
            @Header("Authorization") String token,
            @Path("user_id") Long userId);

    @GET("api/v1/orders")
    Call<List<OrdersDTO>> getAllOrders(
            @Header("Authorization") String token
    );

    // Xóa đơn hàng
    @DELETE("api/v1/orders/{id}")
    Call<String> deleteOrder(@Path("id") Long id);

    @GET("api/v1/orders/{id}")
    Call<List<OrdersDTO>> getOrderById(@Header("Authorization") String token, @Path("id") int id);


    @GET("api/v1/orders/user/{user_id}")
    Call<List<OrdersDTO>> getAllOrdersByUser(@Header("Authorization") String token, @Path("user_id") int userId);

    @PUT("api/v1/orders/{id}")
    Call<String> updateOrder(@Header("Authorization") String token, @Path("id") int id, @Body OrdersDTO ordersDTO);

    @DELETE("api/v1/orders/{id}")
    Call<String> deleteOrder(@Header("Authorization") String token, @Path("id") Long id);


    @PATCH("api/v1/orders/status/{id}")
    Call<String> updateOrderStatus(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Body OrdersDTO orderDTO
    );
}

