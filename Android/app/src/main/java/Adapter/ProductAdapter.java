package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;
import com.example.duanandroid.View.ChitietsanphamActivity;

import java.util.List;

import Model.Product;
import Model.ProductImage;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private List<ProductImage> productImageList;
    private Context context;

    // Constructor
    public ProductAdapter(List<Product> productList, List<ProductImage> productImageList) {
        this.productList = productList;
        this.productImageList = productImageList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Get current product and product image
        Product product = productList.get(position);
        ProductImage productImage = productImageList.get(position);

        // Bind data to the views
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(String.format("₫%,.0f", product.getPrice()));

        // Load the product image from drawable using its resource ID
        int imageResId = Integer.parseInt(productImage.getImageUrl());
        holder.productImage.setImageResource(Integer.parseInt(productImage.getImageUrl()));
//        int imageResId = Integer.parseInt(productImage.getImageUrl());
//        holder.productImage.setImageResource(imageResId);
        // Set click listener for item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to go to chitietsanpham activity
                Intent intent = new Intent(context, ChitietsanphamActivity.class);
                // Pass product details to the new activity
                intent.putExtra("productName", product.getProductName());
                intent.putExtra("productPrice", product.getPrice());
                intent.putExtra("productImage",productImage.getImageUrl() ); // Pass image resource ID

                // Start chitietsanpham activity
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder class for holding item views
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
