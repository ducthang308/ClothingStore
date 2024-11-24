package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;
import com.example.duanandroid.View.CartActivity;
import com.example.duanandroid.View.ItemDecoration;
import com.example.duanandroid.View.chatUserActivity;
import com.example.duanandroid.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

import Adapter.ProductAdapter;
import DTO.ProductDTO;
import Interface.APIClient;
import Interface.ApiProduct;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private ProductAdapter productAdapter;
    private List<ProductDTO> productList = new ArrayList<>();
    private ApiProduct apiProduct;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiProduct = APIClient.getProduct(); // Initialize API client
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        setupUI();
        fetchProducts();
        return binding.getRoot();
    }

    private void setupUI() {
        // Set up RecyclerView
        binding.items.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        binding.items.addItemDecoration(new ItemDecoration(2, 24, true));
        productAdapter = new ProductAdapter(productList, requireActivity(), false);
        binding.items.setAdapter(productAdapter);

        // Set up listeners
        binding.chat.setOnClickListener(v -> {
            startActivity(new Intent(requireActivity(), chatUserActivity.class));
        });

        binding.shoppingCart.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), CartActivity.class);
            intent.putExtra("origin", "CartToHome");
            startActivity(intent);
        });
    }

    private void fetchProducts() {
        showLoading(true); // Show loading indicator
        apiProduct.getProducts().enqueue(new Callback<List<ProductDTO>>() {
            @Override
            public void onResponse(@NonNull Call<List<ProductDTO>> call, @NonNull Response<List<ProductDTO>> response) {
                showLoading(false); // Hide loading indicator

                if (response.isSuccessful() && response.body() != null) {
                    productList.clear();
                    productList.addAll(response.body());
                    productAdapter.notifyDataSetChanged();
                    Log.d("fetchProducts", "Loaded " + productList.size() + " products.");
                } else {
                    handleApiError(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<ProductDTO>> call, @NonNull Throwable t) {
                showLoading(false); // Hide loading indicator
                handleNetworkError(t);
            }
        });
    }

    private void showLoading(boolean isLoading) {
        if (isLoading) {
            binding.progressBar.setVisibility(View.VISIBLE); // Show ProgressBar
            binding.items.setVisibility(View.GONE); // Hide RecyclerView
        } else {
            binding.progressBar.setVisibility(View.GONE); // Hide ProgressBar
            binding.items.setVisibility(View.VISIBLE); // Show RecyclerView
        }
    }

    private void handleApiError(int code, String message) {
        String errorMessage = "Lỗi API: " + code + " - " + message;
        Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_SHORT).show();
        Log.e("HomeFragment", errorMessage);
    }

    private void handleNetworkError(Throwable t) {
        String errorMessage = "Lỗi mạng: " + t.getMessage();
        Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_SHORT).show();
        Log.e("HomeFragment", errorMessage, t);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Release binding
    }
}
