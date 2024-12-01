package Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import Interface.APIClient;
import Interface.ApiOrders;
import Interface.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class manage_orderAdapter extends RecyclerView.Adapter<manage_orderAdapter.ManageOrderViewHolder> {
    private final Context context;
    private final List<OrderDetailReturnDTO> orderDetailList;
    private final List<OrdersDTO> ordersDTOS;
    private final ApiOrders apiOrders;
    private final String token;

    public manage_orderAdapter(Context context, List<OrderDetailReturnDTO> orderDetailList, List<OrdersDTO> ordersDTOS) {
        this.context = context;
        this.orderDetailList = orderDetailList;
        this.ordersDTOS = ordersDTOS;
        this.apiOrders = APIClient.updateStatus();
        this.token = new PreferenceManager(context).getToken();
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

        // Kiểm tra token hợp lệ
        if (token == null || token.isEmpty()) {
            Toast.makeText(context, "Token không hợp lệ. Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hiển thị thông tin sản phẩm
        holder.productName.setText(orderDetail.getProductName());
        holder.productPrice.setText(String.format("%,.0fđ", orderDetail.getTotalMoney() / orderDetail.getNumberOfProduct()));
        holder.productQuantity.setText("x" + orderDetail.getNumberOfProduct());
        holder.totalPayment.setText(String.format("%,.0fđ", orderDetail.getTotalMoney()));

        // Hiển thị hình ảnh sản phẩm
        Glide.with(context)
                .load(orderDetail.getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.co4la)
                        .error(R.drawable.error)
                        .centerCrop())
                .into(holder.productImage);

        // Định dạng ngày
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = orderDetail.getOrderDate() != null ? dateFormat.format(orderDetail.getOrderDate()) : "N/A";

        // Xử lý sự kiện khi click vào item
        holder.itemView.setOnClickListener(v -> openOrderDetailActivity(orderDetail, ordersDTO, formattedDate));

        // Gán sự kiện cho các nút trạng thái
        holder.btnDelivering.setOnClickListener(v -> updateOrderStatus(ordersDTO, "Waiting for shipping", position));
        holder.btnShipping.setOnClickListener(v -> updateOrderStatus(ordersDTO, "Waiting for delivery", position));
        holder.btnDelivered.setOnClickListener(v -> updateOrderStatus(ordersDTO, "Delivered", position));
        holder.btnCancel.setOnClickListener(v -> updateOrderStatus(ordersDTO, "Cancelled", position));
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    private void openOrderDetailActivity(OrderDetailReturnDTO orderDetail, OrdersDTO ordersDTO, String formattedDate) {
        Intent intent = new Intent(context, OrderDetailActivity.class);
        intent.putExtra("product_name", orderDetail.getProductName());
        intent.putExtra("product_price", String.format("%,.0fđ", orderDetail.getTotalMoney() / orderDetail.getNumberOfProduct()));
        intent.putExtra("product_quantity", orderDetail.getNumberOfProduct());
        intent.putExtra("total_payment", String.format("%,.0fđ", orderDetail.getTotalMoney()));
        intent.putExtra("product_image_url", orderDetail.getImageUrl());
        intent.putExtra("delivery_address", orderDetail.getAddress());
        intent.putExtra("order_date", formattedDate);
        intent.putExtra("order_id", ordersDTO.getId());
        Log.d("Adapter", "Sending data: " + intent.getExtras());
        context.startActivity(intent);
    }

    private void updateOrderStatus(OrdersDTO ordersDTO, String newStatus, int position) {
        if (ordersDTO == null || ordersDTO.getId() == 0) {
            Toast.makeText(context, "Đơn hàng không hợp lệ.", Toast.LENGTH_SHORT).show();
            return;
        }

        ordersDTO.setStatus(newStatus);

        apiOrders.updateOrderStatus("Bearer " + token, ordersDTO.getId(), ordersDTO).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    ordersDTOS.get(position).setStatus(newStatus);
                    notifyItemChanged(position);
                    Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Cập nhật thất bại: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(context, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class ManageOrderViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productQuantity, totalPayment;
        ImageView productImage;
        Button btnDelivering, btnShipping, btnDelivered, btnCancel;

        public ManageOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            totalPayment = itemView.findViewById(R.id.total_money);
            productImage = itemView.findViewById(R.id.product_image);
            btnDelivering = itemView.findViewById(R.id.btn_delivering);
            btnShipping = itemView.findViewById(R.id.btn_shipping);
            btnDelivered = itemView.findViewById(R.id.btn_delivered);
            btnCancel = itemView.findViewById(R.id.btn_cancel);
        }
    }
}
