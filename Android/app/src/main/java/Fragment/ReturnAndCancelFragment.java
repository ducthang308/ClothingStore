package Fragment;

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

import Adapter.StatusCancelReturnGoodsAdapter;
import Model.OrderDetail;
import Model.Product;
import Model.ProductImage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReturnAndCancelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReturnAndCancelFragment extends Fragment {
    private RecyclerView recyclerView;
    private StatusCancelReturnGoodsAdapter statusCancelReturnGoodsAdapter; // Sửa lại nếu cần
    private List<Product> productList;
    private List<OrderDetail> orderDetailList;
    private List<ProductImage> productImageList;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ReturnAndCancelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReturnAndCancelFragment.
     */

    public static ReturnAndCancelFragment newInstance(String param1, String param2) {
        ReturnAndCancelFragment fragment = new ReturnAndCancelFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_return_and_cancel, container, false); // Đảm bảo rằng bạn có layout tương ứng

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.item_cancelReturn);
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
        statusCancelReturnGoodsAdapter = new StatusCancelReturnGoodsAdapter(getContext(), productList, orderDetailList, productImageList);
        recyclerView.setAdapter(statusCancelReturnGoodsAdapter);


        return view; // Trả về View
    }
}