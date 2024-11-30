package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import DTO.CartItemsDTO;
import DTO.ProductDTO;
import Interface.APIClient;
import Interface.ApiCartItems;
import Interface.PreferenceManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends ArrayAdapter<CartItemsDTO> {
    private Context context;
    private List<CartItemsDTO> cartItems;
    private ApiCartItems apiCartItems;
    private String token;
    private int cartId;
    private List<Integer> selectedItems = new ArrayList<>();  // Lưu trữ các item đã chọn

    public CartAdapter(@NonNull Context context, @NonNull List<CartItemsDTO> cartItems,
                       ApiCartItems apiCartItems, String token, int cartId) {
        super(context, R.layout.item_cart, cartItems);
        this.context = context;
        this.cartItems = cartItems;
        this.apiCartItems = apiCartItems;
        this.token = token;
        this.cartId = cartId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
            holder = new ViewHolder();
            holder.ivImage = convertView.findViewById(R.id.iv_image);
            holder.tvName = convertView.findViewById(R.id.tv_name);
            holder.tvSize = convertView.findViewById(R.id.tv_color);
            holder.tvPrice = convertView.findViewById(R.id.tv_price);
            holder.tvQuantity = convertView.findViewById(R.id.tv_quantity);
            holder.btnIncrease = convertView.findViewById(R.id.btn_increase);
            holder.btnDecrease = convertView.findViewById(R.id.btn_decrease);
            holder.checkBox = convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CartItemsDTO item = cartItems.get(position);

        // Set thông tin sản phẩm
        holder.tvName.setText(item.getProductName());
        holder.tvSize.setText(item.getColor());
        holder.tvPrice.setText(String.format("₫%,.0f", item.getPrice()));
        holder.tvQuantity.setText(String.valueOf(item.getQuantity()));

        Glide.with(context)
                .load(item.getImageUrl())
                .placeholder(R.drawable.co4la)
                .error(R.drawable.error)
                .into(holder.ivImage);

        // Xử lý checkbox
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedItems.add(item.getProductId());  // Thêm ID sản phẩm vào danh sách đã chọn
            } else {
                selectedItems.remove(Integer.valueOf(item.getProductId()));  // Xóa ID sản phẩm khỏi danh sách đã chọn
            }
        });

        // Xử lý nút tăng giảm số lượng
        holder.btnIncrease.setOnClickListener(v -> {
            int newQuantity = item.getQuantity() + 1;
            item.setQuantity(newQuantity);
            holder.tvQuantity.setText(String.valueOf(newQuantity));
            updateCartItem(item);
        });

        holder.btnDecrease.setOnClickListener(v -> {
            int newQuantity = item.getQuantity() - 1;
            if (newQuantity > 0) {
                item.setQuantity(newQuantity);
                holder.tvQuantity.setText(String.valueOf(newQuantity));
                updateCartItem(item);
            } else {
                deleteCartItem(cartId, item.getProductId());
                cartItems.remove(position);
                notifyDataSetChanged();
            }
        });

        return convertView;
    }

    // Thêm các phương thức update và delete (giống như trước)
    private void updateCartItem(CartItemsDTO item) {
        Call<ResponseBody> call = apiCartItems.updateCartItem("Bearer " + token, item.getId(), item);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "Lỗi cập nhật số lượng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Lỗi kết nối mạng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteCartItem(int cartId, int productId) {
        Call<Void> call = apiCartItems.deleteCartItem("Bearer " + token, cartId, productId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Đã xóa sản phẩm khỏi giỏ hàng!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Lỗi xóa sản phẩm!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Lỗi kết nối mạng!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<Integer> getSelectedItems() {
        return selectedItems;
    }

    private static class ViewHolder {
        ImageView ivImage;
        TextView tvName, tvSize, tvPrice, tvQuantity;
        ImageButton btnIncrease, btnDecrease;
        CheckBox checkBox;  // CheckBox cho mỗi item
    }
}

