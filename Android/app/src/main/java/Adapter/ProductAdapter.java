package Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duanandroid.R;
import com.example.duanandroid.View.AdminChitietSpActivity;
import com.example.duanandroid.View.ChitietsanphamActivity;

import java.util.ArrayList;
import java.util.List;

import DTO.ProductDTO;
import Fragment.HomeFragment;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<ProductDTO> productList;
    private Context context;
    private boolean isAdmin;

    public ProductAdapter(List<ProductDTO> productList, Context context, boolean isAdmin) {
        this.productList = productList;
        this.context = context;
        this.isAdmin = isAdmin;
    }

    public ProductAdapter(List<ProductDTO> productList, HomeFragment fragment, boolean isAdmin) {
        this.productList = productList;
        this.context = fragment.requireContext(); // Use fragment's context
        this.isAdmin = isAdmin;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductDTO product = productList.get(position);

        // Gán dữ liệu cho ViewHolder
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(String.format("₫%,.0f", product.getPrice())); // Format tiền tệ
        loadProductImage(holder.productImage, product.getImageUrls());

        // Sự kiện click vào item
        holder.itemView.setOnClickListener(v -> {
            Log.d("ProductAdapter", "Clicked product: " + product.toString());
            navigateToDetailPage(product);
        });
    }

    private void navigateToDetailPage(ProductDTO product) {
        if (product == null || product.getId() <= 0 || product.getProductName() == null || product.getPrice() == 0 || product.getPrice() <= 0.0) {
            Log.e("ProductAdapter", "Invalid product data. Cannot navigate to detail page.");
            Toast.makeText(context, "Dữ liệu sản phẩm không hợp lệ!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent;
        if (isAdmin) {
            intent = new Intent(context, AdminChitietSpActivity.class);
            intent.putExtra("caller", "admin");
        } else {
            intent = new Intent(context, ChitietsanphamActivity.class);
            intent.putExtra("caller", "user");
        }

        intent.putExtra("productId", product.getId());
        intent.putExtra("productName", product.getProductName());
        intent.putExtra("productPrice", product.getPrice());
        if (product.getImageUrls() != null && !product.getImageUrls().isEmpty()) {
            intent.putStringArrayListExtra("productImage", new ArrayList<>(product.getImageUrls()));
        } else {
            intent.putStringArrayListExtra("productImage", new ArrayList<>());
        }

        Log.d("ProductAdapter", "Navigating to detail page with product: " + product.toString() + " | Caller: " + (isAdmin ? "admin" : "user"));
        context.startActivity(intent);
    }

    private void loadProductImage(ImageView imageView, List<String> imageUrls) {
        if (imageUrls != null && !imageUrls.isEmpty()) {
            String imageUrl = imageUrls.get(0);
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.co4la) // Hình placeholder
                    .error(R.drawable.error) // Hình lỗi
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.error); // Ảnh lỗi mặc định
        }
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    public void updateProductList(List<ProductDTO> newProductList) {
        this.productList = newProductList;
        notifyDataSetChanged();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productImage = itemView.findViewById(R.id.product_image);
        }
    }
}