package Interface;
import java.util.List;

import DTO.OrdersDTO;
import Model.Order;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
public interface ApiOrders {
    @POST("orders")
    Call<Void> createOrder(@Body OrdersDTO odersDTO);

    // API lấy thông tin chi tiết của đơn hàng
    @GET("orders/{id}")
    Call<OrdersDTO> getOrderById(@Path("id") Long id);

    @GET("orders/user/{user_id}")
    Call<List<OrdersDTO>> getAllOrdersByUser(@Path("user_id") Long userId);

    // Cập nhật đơn hàng
    @PUT("orders/{id}")
    Call<Void> updateOrder(@Path("id") Long id, @Body Order order);

    // Xóa đơn hàng
    @DELETE("orders/{id}")
    Call<Void> deleteOrder(@Path("id") Long id);
}
