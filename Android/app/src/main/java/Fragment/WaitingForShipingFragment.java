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

import Adapter.WaitingShipingAdapter;
import Model.OrderDetail;
import Model.Product;
import Model.ProductImage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WaitingForShipingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WaitingForShipingFragment extends Fragment {
    private RecyclerView recyclerView;
    private WaitingShipingAdapter waitingShipingAdapter; // Sửa lại nếu cần
    private List<Product> productList;
    private List<OrderDetail> orderDetailList;
    private List<ProductImage> productImageList;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;


    public WaitingForShipingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WaitingForShipingFragment.
     */

    public static WaitingForShipingFragment newInstance(String param1, String param2) {
        WaitingForShipingFragment fragment = new WaitingForShipingFragment();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_waiting_for_shiping, container, false);
        // Inflate the layout for this fragment
        recyclerView = view.findViewById(R.id.itemShipingList);
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
        waitingShipingAdapter = new WaitingShipingAdapter(getContext(), productList, orderDetailList, productImageList);
        recyclerView.setAdapter(waitingShipingAdapter);

        // Initialize button layouts
//        layoutProductButtons = view.findViewById(R.id.layout_product_buttons);
//        layoutShippingButtons = view.findViewById(R.id.layout_shipping_buttons);
//        layoutDeliveryButtons = view.findViewById(R.id.layout_delivery_buttons);
//        layoutReviewButtons = view.findViewById(R.id.layout_review_buttons);
//        layoutReviewnote = view.findViewById(R.id.layout_review_note);

        // Handle "Waiting for shipping" tab click
//        TextView tabWaitingShipping = view.findViewById(R.id.tab_waiting_shipping);
//        tabWaitingShipping.setOnClickListener(v -> {
//            // Chuyển sang Fragment khác
//            // FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
//            // transaction.replace(R.id.fragment_container, new WaitingForShippingFragment());
//            // transaction.addToBackStack(null);
//            // transaction.commit();
//        });
//
//        TextView tabwaitingdelivery = view.findViewById(R.id.waiting_delivery);
//        tabwaitingdelivery.setOnClickListener(v -> {
//            // Tương tự như trên, bạn có thể sử dụng FragmentTransaction
//        });
//
//        TextView tabreviewstatus = view.findViewById(R.id.tab_review);
//        tabreviewstatus.setOnClickListener(v -> {
//            // Tương tự như trên, bạn có thể sử dụng FragmentTransaction
//        });
//
//        TextView tab_return_cancel_goods = view.findViewById(R.id.return_cancel_goods);
//        tab_return_cancel_goods.setOnClickListener(v -> {
//            // Tương tự như trên, bạn có thể sử dụng FragmentTransaction
//        });
//
//        ImageView btnback = view.findViewById(R.id.back_arrow);
//        btnback.setOnClickListener(v -> {
//            // Chuyển về Fragment trước đó nếu cần
//            // getActivity().onBackPressed();
//        });
//
//        Button btcancel = view.findViewById(R.id.btn_cancel_order);
//        btcancel.setOnClickListener(v -> {
//            // Tương tự như trên, bạn có thể sử dụng FragmentTransaction
//        });
//
        return view; // Trả về View

    }

}
