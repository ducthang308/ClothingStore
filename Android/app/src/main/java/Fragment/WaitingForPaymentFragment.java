package Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.WaitingPaymentAdapter;
import DTO.OrderDetailReturnDTO;
import DTO.OrdersDTO;
import DTO.ProductDTO;
import Interface.APIClient;
import Interface.ApiOrderDetail;
import Interface.ApiOrders;
import Interface.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaitingForPaymentFragment extends Fragment {
    private RecyclerView recyclerView;
    private WaitingPaymentAdapter adapter;
    private ApiOrders apiOrders;
    private ApiOrderDetail apiOrderDetail;
    private List<ProductDTO> productList = new ArrayList<>();
    private List<OrderDetailReturnDTO> orderDetailList = new ArrayList<OrderDetailReturnDTO>();
    private String token;
    private int userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting_for_payment, container, false);
        recyclerView = view.findViewById(R.id.itempaymentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        PreferenceManager preferenceManager = new PreferenceManager(getContext());
        token = preferenceManager.getToken();
        userId = preferenceManager.getUserId();

        apiOrders = APIClient.getClient().create(ApiOrders.class);
        apiOrderDetail = APIClient.getClient().create(ApiOrderDetail.class);


        fetchOrdersAndDetails();

        return view;
    }

    private void fetchOrdersAndDetails() {
        apiOrders.getAllOrdersByUser("Bearer " + token, userId).enqueue(new Callback<List<OrdersDTO>>() {
            @Override
            public void onResponse(Call<List<OrdersDTO>> call, Response<List<OrdersDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OrdersDTO> ordersList = response.body();
                    Log.d("API Response", "Total Orders: " + ordersList.size());

                    // Lọc danh sách đơn hàng, bỏ qua những đơn hàng có trạng thái "Cancel"
                    List<OrdersDTO> filteredOrders = new ArrayList<>();
                    for (OrdersDTO order : ordersList) {
                        if (!"Cancel".equalsIgnoreCase(order.getStatus())) { // Lọc trạng thái Cancel
                            filteredOrders.add(order);
                        }
                    }

                    Log.d("Filtered Orders", "Orders excluding 'Cancel': " + filteredOrders.size());

                    // Tiếp tục xử lý các đơn hàng sau khi lọc
                    for (OrdersDTO order : filteredOrders) {
                        int orderId = order.getId();
                        fetchOrderDetails(orderId);
                    }

                    if (filteredOrders.isEmpty()) {
                        Toast.makeText(getContext(), "Không có đơn hàng nào phù hợp!", Toast.LENGTH_SHORT).show();
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
                    Log.d("Order Details", "Received order details: " + response.body().size()); // Log số lượng chi tiết nhận được
                    orderDetailList.addAll(response.body());
                    Log.d("OrderDetailList", "Total items in order detail list: " + orderDetailList.size()); // Kiểm tra size sau khi thêm
                    if (adapter == null) {
                        adapter = new WaitingPaymentAdapter(getContext(), orderDetailList);
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
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

