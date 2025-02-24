package Fragment;

import android.os.Bundle;
import android.os.IBinder;
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

import Adapter.WaitingReviewAdapter;
import Adapter.WaitingShipingAdapter;
import DTO.OrderDetailReturnDTO;
import DTO.OrdersDTO;
import Interface.APIClient;
import Interface.ApiOrderDetail;
import Interface.ApiOrders;
import Interface.ApiReview;
import Interface.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewFragment extends Fragment {
    private RecyclerView recyclerView;
    private WaitingReviewAdapter adapter;
    private ApiOrderDetail apiOrderDetail;
    private List<OrderDetailReturnDTO> orderDetailList = new ArrayList<>();
    private String token;
    private ApiReview apiReview;
    private int userId;
    private ApiOrders apiOrders;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        recyclerView = view.findViewById(R.id.itemReviewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        PreferenceManager preferenceManager = new PreferenceManager(getContext());
        token = preferenceManager.getToken();
        userId = preferenceManager.getUserId();


        apiReview = APIClient.getClient().create(ApiReview.class);
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
                    List<OrdersDTO> filteredOrders = new ArrayList<>();
                    List<Integer> reviewOrderIds = new ArrayList<>();

                    for (OrdersDTO order : ordersList) {
                        Log.d("OrderStatus", "Order ID: " + order.getId() + ", Status: " + order.getStatus());
                        if (order.getStatus() != null && order.getStatus().equalsIgnoreCase("Shipped")) {
                            reviewOrderIds.add(order.getId());
                        }
                    }

                    Log.d("Filtered Orders", "Orders count with status 'Shipped': " + reviewOrderIds.size());

                    if (!reviewOrderIds.isEmpty()) {
                        fetchOrderDetails(reviewOrderIds, "Shipped");
                    } else {
                        Toast.makeText(getContext(), "Không có đơn hàng nào đang chờ giao!", Toast.LENGTH_SHORT).show();
                    }

                    Log.d("Filtered Orders", "Orders count with status 'Shipped': " + filteredOrders.size());

                    if (filteredOrders.isEmpty()) {
                        Toast.makeText(getContext(), "Không có đơn hàng nào đang chờ chờ đánh giá!", Toast.LENGTH_SHORT).show();
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

    private void fetchOrderDetails(List<Integer> orderId, String status) {
        apiOrderDetail.getOrderDetailsByStatus("Bearer " + token, status, orderId)
                .enqueue(new Callback<List<OrderDetailReturnDTO>>() {
                    @Override
                    public void onResponse(Call<List<OrderDetailReturnDTO>> call, Response<List<OrderDetailReturnDTO>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<OrderDetailReturnDTO> details = response.body();
                            Log.d("Order Details", "Order ID: " + orderId + " - Received order details: " + details.size());

                            orderDetailList.clear();
                            orderDetailList.addAll(details);

                            Log.d("OrderDetailList", "Total items in order detail list: " + orderDetailList.size());

                            if (adapter == null) {
                                adapter = new WaitingReviewAdapter(getContext(), orderDetailList, apiReview);
                                recyclerView.setAdapter(adapter);
                            } else {
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.e("API Error", "Error fetching details for Order ID: " + orderId + ". Code: " + response.code());
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
