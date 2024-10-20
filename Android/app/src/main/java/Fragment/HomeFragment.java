package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.View.CartActivity;
import com.example.duanandroid.View.ItemDecoration;
import com.example.duanandroid.View.chatUserActivity;
import com.example.duanandroid.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import Adapter.ProductAdapter;
import Model.Product;
import Model.ProductImage;

public class HomeFragment extends Fragment {
    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private List<ProductImage> productImageList;
    FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        productRecyclerView = binding.items;
        productRecyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        productRecyclerView.addItemDecoration(new ItemDecoration(2, 24, true));

        productList = loadDataFromLayout();
        productImageList = loadProductImages();

        productAdapter = new ProductAdapter(productList, productImageList);
        productRecyclerView.setAdapter(productAdapter);

        binding.chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), chatUserActivity.class);
                startActivity(intent);
            }
        });

        binding.shoppingCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), CartActivity.class);
                intent.putExtra("origin", "CartToHome");
                startActivity(intent);
            }
        });

        return view;
    }

    private List<Product> loadDataFromLayout() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            products.add(new Product("Tên sản phẩm " + (i + 1), "M", (i + 1) * 100000));
        }
        return products;
    }

    private List<ProductImage> loadProductImages()
    { List<ProductImage> productImages = new ArrayList<>();
        for (int i = 0; i < 8; i++)
        { String imageName = "ao" + (i + 1);
            int imageResId = getResources().getIdentifier(imageName, "drawable", requireContext().getPackageName());
            productImages.add(new ProductImage(i, i, String.valueOf(imageResId)));
        }
        return productImages;
    }
}
