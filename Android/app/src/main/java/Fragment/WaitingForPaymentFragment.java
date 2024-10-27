package Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.WaitingPaymentAdapter;
import Model.OrderDetail;
import Model.Product;
import Model.ProductImage;

public class WaitingForPaymentFragment extends Fragment {

    private RecyclerView recyclerView;
    private WaitingPaymentAdapter waitingPaymentAdapter; // Sửa lại nếu cần
    private List<Product> productList;
    private List<OrderDetail> orderDetailList;
    private List<ProductImage> productImageList;


    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting_for_payment, container, false); // Đảm bảo rằng bạn có layout tương ứng

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.itempaymentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Lists
        productList = new ArrayList<>();
        orderDetailList = new ArrayList<>();
        productImageList = new ArrayList<>();

        // Add Dummy Data (for testing)
        productList.add(new Product("Áo thun ngắn tay nữ trắng", "M", 200000));
        orderDetailList.add(new OrderDetail(1, 1, 1, 200000, 100, 200000, null));
        productImageList.add(new ProductImage(1, 1, "url_image_here"));

        // Initialize Adapter
        waitingPaymentAdapter = new WaitingPaymentAdapter(getContext(), productList, orderDetailList, productImageList);
        recyclerView.setAdapter(waitingPaymentAdapter);

//        Button btn_cancel_order = view.findViewById(R.id.btn_cancel_order);
//        btn_cancel_order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ReasoncancelActivity.class);
//                startActivity(intent);
//            }
//        });
//        // Tìm Button và gắn sự kiện OnClickListener


        return view;
    }
}
