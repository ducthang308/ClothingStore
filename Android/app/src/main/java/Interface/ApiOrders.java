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
    Call<String> createOrder(@Header("Authorization") String token, @Body OrdersDTO odersDTO);

    @GET("api/v1/orders/{id}")
    Call<List<OrdersDTO>> getOrderById(@Header("Authorization") String token, @Path("id") Long id);

    @GET("api/v1/orders/user/{user_id}")
    Call<List<OrdersDTO>> getAllOrdersByUser(@Header("Authorization") String token, @Path("user_id") int userId);

    @GET("api/v1/orders")
    Call<List<Order>> getAllOrders(@Header("Authorization") String token);

    @PUT("api/v1/orders/{id}")
    Call<String> updateOrder(@Header("Authorization") String token, @Path("id") Long id, @Body Order order);

    @DELETE("api/v1/orders/{id}")
    Call<String> deleteOrder(@Header("Authorization") String token, @Path("id") Long id);

}
