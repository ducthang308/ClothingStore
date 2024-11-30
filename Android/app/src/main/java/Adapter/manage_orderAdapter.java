package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.duanandroid.R;
import com.example.duanandroid.View.OrderDetailActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import DTO.OrderDetailReturnDTO;
import DTO.OrdersDTO;

public class manage_orderAdapter extends RecyclerView.Adapter<manage_orderAdapter.ManageOrderViewHolder> {
    private final Context context;
    private final List<OrderDetailReturnDTO> orderDetailList;
    private final List<OrdersDTO> ordersDTOS;

    public manage_orderAdapter(Context context, List<OrderDetailReturnDTO> orderDetailList, List<OrdersDTO> ordersDTOS) {
        this.context = context;
        this.orderDetailList = orderDetailList;
        this.ordersDTOS = ordersDTOS;
    }

    @NonNull
    @Override
    public ManageOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_manage, parent, false);
        return new ManageOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageOrderViewHolder holder, int position) {
        OrderDetailReturnDTO orderDetail = orderDetailList.get(position);
        OrdersDTO ordersDTO = ordersDTOS.get(position);

        // Hiển thị thông tin sản phẩm
        holder.productName.setText(orderDetail.getProductName());
        holder.productPrice.setText(String.format("%,.0fđ", orderDetail.getTotalMoney() / orderDetail.getNumberOfProduct()));
        holder.productQuantity.setText("x" + orderDetail.getNumberOfProduct());
        holder.totalPayment.setText(String.format("%,.0fđ", orderDetail.getTotalMoney()));

        // Load hình ảnh sản phẩm
        Glide.with(context)
                .load(orderDetail.getImageUrl()) // URL hình ảnh
                .apply(new RequestOptions()
                        .placeholder(R.drawable.co4la) // Hình chờ
                        .error(R.drawable.error)       // Hình lỗi
                        .centerCrop())
                .into(holder.productImage);

        // Định dạng ngày
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(orderDetail.getOrderDate()); // Nếu orderDate là kiểu Date
        int orderId = ordersDTO.getId();
        // Sự kiện click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);

            // Truyền dữ liệu qua Intent
            intent.putExtra("product_name", orderDetail.getProductName());
            intent.putExtra("product_price", String.format("%,.0fđ", orderDetail.getTotalMoney() / orderDetail.getNumberOfProduct()));
            intent.putExtra("product_quantity", orderDetail.getNumberOfProduct());
            intent.putExtra("total_payment", String.format("%,.0fđ", orderDetail.getTotalMoney()));
            intent.putExtra("product_image_url", orderDetail.getImageUrl());
            intent.putExtra("delivery_address", orderDetail.getAddress());
            intent.putExtra("order_date", formattedDate);
            intent.putExtra("order_id", orderId);// Truyền ngày đã được định dạng

            Log.d("Adapter", "Sending data: " + intent.getExtras());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    public static class ManageOrderViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productQuantity, totalPayment;
        ImageView productImage;

        public ManageOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            totalPayment = itemView.findViewById(R.id.total_money);
            productImage = itemView.findViewById(R.id.product_image);
        }
    }
}