package Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.WaitingDeliveryAdapter;
import Model.OrderDetail;
import Model.Product;
import Model.ProductImage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaitingForDeliveryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitingForDeliveryFragment extends Fragment {
    private RecyclerView recyclerView;
    private WaitingDeliveryAdapter waitingDeliveryAdapter; // Sửa lại nếu cần
    private List<Product> productList;
    private List<OrderDetail> orderDetailList;
    private List<ProductImage> productImageList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WaitingForDeliveryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaitingForDeliveryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WaitingForDeliveryFragment newInstance(String param1, String param2) {
        WaitingForDeliveryFragment fragment = new WaitingForDeliveryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting_for_delivery, container, false); // Đảm bảo rằng bạn có layout tương ứng

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.itemDeliveryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Lists
        productList = new ArrayList<>();
        orderDetailList = new ArrayList<>();
        productImageList = new ArrayList<>();

//        // Add Dummy Data (for testing)
//        productList.add(new Product("Áo thun ngắn tay nữ trắng", "M", 200000));
//        orderDetailList.add(new OrderDetail(1, 1, 1, 200000, 100, 200000, null));
//        productImageList.add(new ProductImage(1, 1, "url_image_here"));



        // Initialize Adapter
        waitingDeliveryAdapter = new WaitingDeliveryAdapter(getContext(), productList, orderDetailList, productImageList);
        recyclerView.setAdapter(waitingDeliveryAdapter);

        return view; // Trả về View
    }

}