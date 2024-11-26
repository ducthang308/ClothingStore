package Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.ProductAdapter;
import Model.Product;
import Model.ProductImage;

public class ViewProductListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private List<ProductImage> productImageList;


    public static ViewProductListFragment newInstance(int position) {
        ViewProductListFragment fragment = new ViewProductListFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_product_list, container, false);
        recyclerView = view.findViewById(R.id.items);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        int position = getArguments() != null ? getArguments().getInt("position") : 0;
        loadProductData(position);
        productAdapter = new ProductAdapter(productList, productImageList, false);
        recyclerView.setAdapter(productAdapter);

        return view;
    }

    private void loadProductData(int position) {

        productList = new ArrayList<>();
        productImageList = new ArrayList<>();

        if (position == 0) {
            for (int i = 0; i < 8; i++) {
                productList.add(new Product("Sản phẩm bán chạy " + (i + 1), "M", (i + 1) * 100000));
                String imageName = "ao" ;
                int imageResId = getResources().getIdentifier(imageName, "drawable", requireContext().getPackageName());
                productImageList.add(new ProductImage(i, i, String.valueOf(imageResId)));
            }
        } else if (position == 1) {
            for (int i = 0; i < 5; i++) {
                productList.add(new Product("Sản phẩm mới " + (i + 1), "L", (i + 1) * 120000));
                String imageName = "ao" ;
                int imageResId = getResources().getIdentifier(imageName, "drawable", requireContext().getPackageName());
                productImageList.add(new ProductImage(i, i, String.valueOf(imageResId)));
            }
        } else if (position == 2) {
            for (int i = 0; i < 7; i++) {
                productList.add(new Product("Sản phẩm " + (i + 1), "M", (i + 1) * 150000));
                String imageName = "ao" ;
                int imageResId = getResources().getIdentifier(imageName, "drawable", requireContext().getPackageName());
                productImageList.add(new ProductImage(i, i, String.valueOf(imageResId)));
            }
        }
    }
}