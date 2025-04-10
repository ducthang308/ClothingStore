package Fragment;

import android.annotation.SuppressLint;
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

import Adapter.WaitingShipingAdapter;
import Adapter.WaitingShipingAdminAdapter;
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

public class WaitingForShippingAdminFragment extends Fragment {
    private RecyclerView recyclerView;
    private WaitingShipingAdminAdapter waitingShipingAdminAdapter;
    private ApiOrders apiOrders;
    private ApiOrderDetail apiOrderDetail;
    private List<OrderDetailReturnDTO> orderDetailList = new ArrayList<OrderDetailReturnDTO>();
    private String token;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting_for_shipping_admin, container, false);
        recyclerView = view.findViewById(R.id.itemShipingList);
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
                    List<Integer> waitingForDeliveryOrderIds = new ArrayList<>();

                    for (OrdersDTO order : ordersList) {
                        Log.d("OrderStatus", "Order ID: " + order.getId() + ", Status: " + order.getStatus());
                        if (order.getStatus() != null && order.getStatus().equalsIgnoreCase("Waiting for Delivery")) {
                            waitingForDeliveryOrderIds.add(order.getId());
                        }
                    }

                    Log.d("Filtered Orders", "Orders count with status 'Waiting for Delivery': " + waitingForDeliveryOrderIds.size());

                    if (!waitingForDeliveryOrderIds.isEmpty()) {
                        fetchOrderDetails(waitingForDeliveryOrderIds, "Waiting for Delivery");
                    } else {
                        Toast.makeText(getContext(), "Không có đơn hàng nào đang chờ giao!", Toast.LENGTH_SHORT).show();
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

    private void fetchOrderDetails(List<Integer> orderIds, String status) {
        apiOrderDetail.getOrderDetailsByStatus("Bearer " + token, status, orderIds).enqueue(new Callback<List<OrderDetailReturnDTO>>() {
            @Override
            public void onResponse(Call<List<OrderDetailReturnDTO>> call, Response<List<OrderDetailReturnDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OrderDetailReturnDTO> details = response.body();
                    Log.d("OrderDetails", "Received " + details.size() + " order details for status: " + status);

                    orderDetailList.clear();
                    orderDetailList.addAll(details);

                    Log.d("OrderDetailList", "Total items in order detail list: " + orderDetailList.size());

                    // Update or initialize adapter
                    if (waitingShipingAdminAdapter == null) {
                        waitingShipingAdminAdapter = new WaitingShipingAdminAdapter(getContext(), orderDetailList);
                        recyclerView.setAdapter(waitingShipingAdminAdapter);
                    } else {
                        waitingShipingAdminAdapter.notifyDataSetChanged();
                    }
                } else {
                    Log.e("API Error", "Failed to fetch order details for status: " + status + ". Error code: " + response.code());
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
