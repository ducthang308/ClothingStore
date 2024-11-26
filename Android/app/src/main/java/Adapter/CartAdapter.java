package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import DTO.ProductDTO;
import Model.CartItem;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<ProductDTO> cartItems;
    private Context context;

    public CartAdapter(List<ProductDTO> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        ProductDTO productDTO = cartItems.get(position);

        holder.tvName.setText(productDTO.getProductName());
        holder.tvSize.setText(productDTO.getColor());
        holder.tvPrice.setText(String.format("₫%,.0f", productDTO.getPrice()));
        loadProductImage(holder.ivImage, productDTO.getImageUrls());
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
        return cartItems != null ? cartItems.size() : 0;
    }


    public static class CartViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageView ivImage;
        TextView tvName, tvSize, tvPrice;
        Button btnIncrease, btnDecrease;
        TextView tvQuantity;

        @SuppressLint("WrongViewCast")
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSize = itemView.findViewById(R.id.tv_color);
            tvPrice = itemView.findViewById(R.id.tv_price);
//            btnIncrease = itemView.findViewById(R.id.btn_increase);
//            btnDecrease = itemView.findViewById(R.id.btn_decrease);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
            ivImage =itemView.findViewById(R.id.iv_image);
        }
    }
}

