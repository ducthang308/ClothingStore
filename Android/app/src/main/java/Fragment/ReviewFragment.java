package Fragment;

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

import Adapter.WaitingReviewAdapter;
import DTO.OrderDetailReturnDTO;
import Interface.APIClient;
import Interface.ApiOrderDetail;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        recyclerView = view.findViewById(R.id.itemReviewList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Lấy token từ SharedPreferences
        PreferenceManager preferenceManager = new PreferenceManager(getContext());
        token = preferenceManager.getToken();


        apiReview = APIClient.getClient().create(ApiReview.class);

        apiOrderDetail = APIClient.getClient().create(ApiOrderDetail.class);

        // Lấy danh sách đơn hàng đã giao (Shipped)
        fetchOrdersAndDetails();

        return view;
    }

    private void fetchOrdersAndDetails() {
        // Truyền trạng thái "Shipped" để lấy đơn hàng đã giao
        apiOrderDetail.getOrderDetailsByStatus("Bearer " + token, "Shipped").enqueue(new Callback<List<OrderDetailReturnDTO>>() {
            @Override
            public void onResponse(Call<List<OrderDetailReturnDTO>> call, Response<List<OrderDetailReturnDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Log để kiểm tra số lượng đơn hàng nhận được
                    Log.d("Order Details", "Received order details: " + response.body().size());
                    orderDetailList.addAll(response.body());

                    // Cập nhật Adapter sau khi nhận dữ liệu
                    if (adapter == null) {
                        adapter = new WaitingReviewAdapter(getContext(), orderDetailList, apiReview);
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    // Log lỗi khi không thể lấy dữ liệu
                    Log.e("API Error", "Error code: " + response.code());
                    Toast.makeText(getContext(), "Không thể lấy chi tiết đơn hàng! Lỗi: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<OrderDetailReturnDTO>> call, Throwable t) {
                // Xử lý lỗi kết nối
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
