package Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.List;

import Model.CartItem;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;

    public CartAdapter(List<CartItem> cartItems) {
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
        CartItem cartItem = cartItems.get(position);

        holder.tvName.setText(cartItem.getName());
        holder.tvSize.setText(cartItem.getSize());
        holder.tvPrice.setText(cartItem.getPrice());

        // Handle checkbox and image loading (use Glide or similar library)
        // Glide.with(holder.productImage.getContext()).load(cartItem.getImageUrl()).into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
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
            tvSize = itemView.findViewById(R.id.tv_size);
            tvPrice = itemView.findViewById(R.id.tv_price);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
            tvQuantity = itemView.findViewById(R.id.tv_quantity);
        }
    }
}

