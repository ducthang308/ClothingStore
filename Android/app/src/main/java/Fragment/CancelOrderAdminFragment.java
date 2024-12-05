package Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.CancelOrderAdminAdapter;
import Adapter.StatusCancelReturnGoodsAdapter;
import DTO.OrderDetailReturnDTO;
import DTO.OrdersDTO;
import Interface.APIClient;
import Interface.ApiOrderDetail;
import Interface.ApiOrders;
import Interface.PreferenceManager;
import Model.OrderDetail;
import Model.Product;
import Model.ProductImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CancelOrderAdminFragment extends Fragment {
    private RecyclerView recyclerView;
    private CancelOrderAdminAdapter cancelOrderAdminAdapter; // Đổi tên adapter
    private ApiOrders apiOrders;
    private ApiOrderDetail apiOrderDetail;
    private List<OrderDetailReturnDTO> orderDetailList = new ArrayList<>();
    private String token;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cancel_order_admin, container, false);
        recyclerView = view.findViewById(R.id.item_cancelAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        PreferenceManager preferenceManager = new PreferenceManager(getContext());
        token = preferenceManager.getToken();

        apiOrders = APIClient.getClient().create(ApiOrders.class);
        apiOrderDetail = APIClient.getClient().create(ApiOrderDetail.class);

        fetchOrdersAndDetails();

        return view;
    }

    private void fetchOrdersAndDetails() {
        apiOrders.getAllOrders("Bearer " + token).enqueue(new Callback<List<OrdersDTO>>() {
            @Override
            public void onResponse(Call<List<OrdersDTO>> call, Response<List<OrdersDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OrdersDTO> ordersList = response.body();
                    Log.d("API Response", "Total Orders: " + ordersList.size());

                    // Filter orders based on "cancel" status
                    List<OrdersDTO> filteredOrders = new ArrayList<>();
                    for (OrdersDTO order : ordersList) {
                        if (order.getStatus() != null && order.getStatus().toLowerCase().contains("cancel")) {
                            filteredOrders.add(order);
                        }
                    }

                    // Log and fetch details for each filtered order
                    Log.d("Filtered Orders", "Orders count with 'cancel': " + filteredOrders.size());

                    if (!filteredOrders.isEmpty()) {
                        for (OrdersDTO order : filteredOrders) {
                            int orderId = order.getId();
                            fetchOrderDetails(orderId);
                        }
                    } else {
                        Toast.makeText(getContext(), "Không có đơn hàng nào đã hủy!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Không thể lấy danh sách đơn hàng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrdersDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchOrderDetails(int orderId) {
        apiOrderDetail.getOrderDetails("Bearer " + token, orderId).enqueue(new Callback<List<OrderDetailReturnDTO>>() {
            @Override
            public void onResponse(Call<List<OrderDetailReturnDTO>> call, Response<List<OrderDetailReturnDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OrderDetailReturnDTO> orderDetails = response.body();
                    Log.d("Order Details", "Received order details: " + orderDetails.size());

                    // Thêm tất cả các sản phẩm vào orderDetailList
                    orderDetailList.addAll(orderDetails);  // Thêm toàn bộ danh sách vào orderDetailList

                    Log.d("OrderDetailList", "Total items in order detail list: " + orderDetailList.size());

                    // Check if the adapter is initialized, and notify changes
                    if (cancelOrderAdminAdapter == null) {
                        cancelOrderAdminAdapter = new CancelOrderAdminAdapter(getContext(), orderDetailList);
                        recyclerView.setAdapter(cancelOrderAdminAdapter);
                    } else {
                        cancelOrderAdminAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("API Error", "Error code: " + response.code());
                    Toast.makeText(getContext(), "Không thể lấy chi tiết đơn hàng! Lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrderDetailReturnDTO>> call, Throwable t) {
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
