package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.text.DecimalFormat;
import java.util.List;

import Model.CartItem;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartItem> cartItems;
    private OnItemCheckedChangeListener onItemCheckedChangeListener;

    // Interface để cập nhật trạng thái checkbox "Chọn tất cả"
    public interface OnItemCheckedChangeListener {
        void onItemCheckedChanged();
    }

    public CartAdapter(List<CartItem> cartItems, OnItemCheckedChangeListener listener) {
        this.cartItems = cartItems;
        this.onItemCheckedChangeListener = listener;
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

        // Định dạng giá sản phẩm
        DecimalFormat decimalFormat = new DecimalFormat("#,### đ");

        holder.tvName.setText(cartItem.getName());
        holder.tvSize.setText(cartItem.getSize());
        holder.tvPrice.setText(decimalFormat.format(cartItem.getPrice())); // Hiển thị giá dạng có phân cách
        holder.ivImage.setImageResource(cartItem.getImageUrl());

        holder.checkBox.setChecked(cartItem.isSelected());

        // Lắng nghe sự kiện click vào checkbox của từng item
        holder.checkBox.setOnClickListener(v -> {
            cartItem.setSelected(holder.checkBox.isChecked());
            // Gọi callback để cập nhật trạng thái "Chọn tất cả"
            if (onItemCheckedChangeListener != null) {
                onItemCheckedChangeListener.onItemCheckedChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public void selectAllItems(boolean isSelected) {
        for (CartItem item : cartItems) {
            item.setSelected(isSelected);
        }
        notifyDataSetChanged(); // Refresh toàn bộ RecyclerView
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        ImageView ivImage;
        TextView tvName, tvSize, tvPrice;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSize = itemView.findViewById(R.id.tv_size);
            tvPrice = itemView.findViewById(R.id.tv_price);
            ivImage = itemView.findViewById(R.id.iv_image);
        }
    }
}
