package Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import DTO.ProductDTO;

public class ProductManageAdapter extends RecyclerView.Adapter<ProductManageAdapter.ProductViewHolder> {
    private final Context context;
    private List<ProductDTO> productList;
    private ProductDTO selectedProduct = null; // Sản phẩm được chọn
    private final OnProductClickListener listener;

    // Constructor
    public ProductManageAdapter(Context context, List<ProductDTO> productList, OnProductClickListener listener) {
        this.context = context;
        this.productList = productList != null ? productList : new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_manage, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductDTO product = productList.get(position);

        // Bind product data
        holder.productName.setText(product.getProductName());
        holder.price.setText(String.format("₫%.0f", product.getPrice()));
        holder.color.setText("Màu sắc: " + product.getColor());

        // Load product image with Glide
        if (product.getImageUrls() != null && !product.getImageUrls().isEmpty()) {
            String imageUrl = product.getImageUrls().get(0);
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.co4la)
                    .error(R.drawable.error)
                    .into(holder.imageProduct);
        } else {
            holder.imageProduct.setImageResource(R.drawable.error);
        }

        // Checkbox logic
        holder.checkBox.setOnCheckedChangeListener(null); // Avoid triggering listener during binding
        holder.checkBox.setChecked(product.equals(selectedProduct)); // Check if current product is selected

        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedProduct = product; // Update selected product
                notifyDataSetChanged(); // Refresh all checkboxes
            } else if (selectedProduct != null && selectedProduct.equals(product)) {
                selectedProduct = null; // Deselect if unchecked
            }
        });

        // Handle item click (optional)
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProductClick(product); // Call listener when clicked
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    // Update data
    public void updateData(List<ProductDTO> newProductList) {
        this.productList = newProductList != null ? newProductList : new ArrayList<>();
        selectedProduct = null; // Reset selected product
        notifyDataSetChanged();
    }

    // Get the selected product
    public ProductDTO getSelectedProduct() {
        return selectedProduct;
    }

    // Interface for product click
    public interface OnProductClickListener {
        void onProductClick(ProductDTO product);
    }

    // ViewHolder class
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageView imageProduct;
        TextView productName, price, color;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            imageProduct = itemView.findViewById(R.id.image_product);
            productName = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.price);
            color = itemView.findViewById(R.id.color);
        }
    }
}
