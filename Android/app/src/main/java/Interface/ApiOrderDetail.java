package Interface;

import java.util.List;

import DTO.OrderDetailDTO;
import DTO.OrderDetailReturnDTO;
import DTO.OrdersDTO;
import Model.Order;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiOrderDetail {
    @POST("api/v1/orderdetail")
    @Headers("Content-Type: application/json")
    Call<String> createOrderdetail(@Header("Authorization") String token, @Body OrderDetailDTO orderDetailDTO);

    @GET("api/v1/orderdetail/{id}")
    Call<List<OrderDetailDTO>> getOrderById(@Header("Authorization") String token, @Path("id") int id);

    @GET("api/v1/orderdetail/orders/{orderId}")
    Call<List<OrderDetailDTO>> getAllOrderDetailByOrder(@Header("Authorization") String token, @Path("orderId") int orderId);

    @PUT("api/v1/orderdetail/{id}")
    Call<String> updateOrder(@Header("Authorization") String token, @Path("id") int id, @Body OrderDetailDTO orderDetailDTO);

    @DELETE("api/v1/orderdetail/{id}")
    Call<String> deleteOrder(@Header("Authorization") String token, @Path("id") int id);

    @GET("api/v1/orderdetail/orders/{orderId}/details")
    Call<List<OrderDetailReturnDTO>> getOrderDetails(@Header("Authorization") String token, @Path("orderId") int orderId);

    @GET("api/v1/orderdetail/orders/details/{status}")
    Call<List<OrderDetailReturnDTO>> getOrderDetailsByStatus(@Header("Authorization") String token, @Path("status") String status);

}
