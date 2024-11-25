package Fragment;

import android.os.Bundle;
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
import java.util.stream.Collectors;

import Adapter.WaitingPaymentAdapter;
import DTO.OrderDetailDTO;
import DTO.OrdersDTO;
import DTO.ProductDTO;
import Interface.APIClient;
import Interface.ApiOrders;
import Interface.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaitingForPaymentFragment extends Fragment {
    private RecyclerView recyclerView;
    private WaitingPaymentAdapter adapter;
    private ApiOrders apiOrders;
    private List<ProductDTO> productList = new ArrayList<>();
    private List<OrderDetailDTO> orderDetailList = new ArrayList<>();
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

        loadOrdersWaitingForPayment(userId);


        return view;
    }

    private void loadOrdersWaitingForPayment(int userId) {
        apiOrders.getAllOrdersByUser(token, userId).enqueue(new Callback<List<OrdersDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<OrdersDTO>> call, @NonNull Response<List<OrdersDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OrdersDTO> orders = response.body();

                    List<OrdersDTO> waitingForPaymentOrders = orders.stream()
                            .filter(order -> "Waiting for Payment".equalsIgnoreCase(order.getStatus()))
                            .collect(Collectors.toList());

                    productList = fetchProducts(waitingForPaymentOrders);
                    orderDetailList = fetchOrderDetails(waitingForPaymentOrders);

                    adapter = new WaitingPaymentAdapter(getContext(), productList, orderDetailList);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Failed to fetch orders", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<OrdersDTO>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private List<ProductDTO> fetchProducts(List<OrdersDTO> orders) {
        // Mock or fetch product data based on the orders
        return new ArrayList<>(); // Replace with actual implementation
    }

    private List<OrderDetailDTO> fetchOrderDetails(List<OrdersDTO> orders) {
        // Mock or fetch order details based on the orders
        return new ArrayList<>(); // Replace with actual implementation
    }
}
