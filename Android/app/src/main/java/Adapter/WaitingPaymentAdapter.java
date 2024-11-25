package Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.duanandroid.R;
import com.example.duanandroid.View.OrderDetailActivity;
import com.example.duanandroid.View.ReasoncancelActivity;

import java.util.List;

import DTO.OrderDetailDTO;
import DTO.ProductDTO;

public class WaitingPaymentAdapter extends RecyclerView.Adapter<WaitingPaymentAdapter.WaitingPaymentViewHolder> {

    private final Context context;
    private final List<ProductDTO> productList;
    private final List<OrderDetailDTO> orderDetailList;

    public WaitingPaymentAdapter(Context context, List<ProductDTO> productList, List<OrderDetailDTO> orderDetailList) {
        this.context = context;
        this.productList = productList;
        this.orderDetailList = orderDetailList;
    }

    @NonNull
    @Override
    public WaitingPaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_in_status_payment, parent, false);
        return new WaitingPaymentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaitingPaymentViewHolder holder, int position) {
        // Ensure the positions are valid
        if (position >= productList.size() || position >= orderDetailList.size()) {
            return;
        }

        ProductDTO product = productList.get(position);
        OrderDetailDTO orderDetail = orderDetailList.get(position);

        // Set product name, price, quantity, and total money
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(String.format("%,.0fđ", product.getPrice()));
        holder.productQuantity.setText("x" + orderDetail.getNumberOfProduct());
        holder.totalPayment.setText(String.format("%,.0fđ", orderDetail.getTotalMoney()));

        // Set product image using Glide
        if (product.getImageUrls() != null && !product.getImageUrls().isEmpty()) {
            Glide.with(context)
                    .load(product.getImageUrls().get(0)) // Load the first image URL
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.co4la) // Placeholder image
                            .error(R.drawable.error) // Error image if loading fails
                            .centerCrop())
                    .into(holder.productImage);
        } else {
            holder.productImage.setImageResource(R.drawable.co4la); // Fallback image if no image available
        }

        // Cancel order button logic
        holder.btnCancelOrder.setOnClickListener(v -> {
            Intent intent = new Intent(context, ReasoncancelActivity.class);
            intent.putExtra("orderDetailId", orderDetail.getOrderId());
            context.startActivity(intent);
        });

        // On item click listener for details
        holder.itemView.setOnClickListener(view -> {
            // Logic for navigating to order details (could open a new activity or fragment)
            Intent intent = new Intent(context, OrderDetailActivity.class); // Example: OrderDetailActivity to view more details
            intent.putExtra("orderDetailId", orderDetail.getOrderId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class WaitingPaymentViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productSize, productPrice, productQuantity, totalPayment;
        ImageView productImage;
        Button btnCancelOrder;

        public WaitingPaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            totalPayment = itemView.findViewById(R.id.total_money);
            productImage = itemView.findViewById(R.id.product_image);
            btnCancelOrder = itemView.findViewById(R.id.btn_cancel_order);
        }
    }
}
