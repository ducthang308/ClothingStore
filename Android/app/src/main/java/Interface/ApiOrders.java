package Interface;
import java.util.List;

import DTO.OrdersDTO;
import Model.Order;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
public interface ApiOrders {
    @POST("api/v1/orders")
    Call<String> createOrder(@Body OrdersDTO odersDTO);

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
    Call<List<Order>> getAllOrders(
            @Header("Authorization") String token
    );


    // Cập nhật đơn hàng
    @PUT("api/v1/orders/{id}")
    Call<String> updateOrder(@Path("id") Long id, @Body Order order);

    // Xóa đơn hàng
    @DELETE("api/v1/orders/{id}")
    Call<String> deleteOrder(@Path("id") Long id);
}
