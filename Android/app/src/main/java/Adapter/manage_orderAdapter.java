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


        // Lấy API instance và token
        apiOrders = APIClient.updateStatus();
        PreferenceManager preferenceManager = new PreferenceManager(context);
        token = preferenceManager.getToken();

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


        if (token == null || token.isEmpty()) {
            Toast.makeText(context, "Token không hợp lệ. Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Hiển thị thông tin đơn hàng

        holder.productName.setText(orderDetail.getProductName());
        holder.productPrice.setText(String.format("%,.0fđ", orderDetail.getTotalMoney() / orderDetail.getNumberOfProduct()));
        holder.productQuantity.setText("x" + orderDetail.getNumberOfProduct());
        holder.totalPayment.setText(String.format("%,.0fđ", orderDetail.getTotalMoney()));


        // Hiển thị hình ảnh sản phẩm
        Glide.with(context)
                .load(orderDetail.getImageUrl())
                .apply(new RequestOptions().placeholder(R.drawable.co4la).error(R.drawable.error).centerCrop())

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

        String formattedDate = dateFormat.format(orderDetail.getOrderDate());

        // Khi người dùng click vào item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, OrderDetailActivity.class);

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

            intent.putExtra("order_id", ordersDTO.getId());

            intent.putExtra("order_id", orderId);// Truyền ngày đã được định dạng

            Log.d("Adapter", "Sending data: " + intent.getExtras());

            context.startActivity(intent);
        });


        // Xử lý cập nhật trạng thái khi bấm nút
        View.OnClickListener updateStatusListener = view -> {
            String newStatus = null;

            // Xác định trạng thái mới dựa trên nút được bấm
            if (view.getId() == R.id.btn_delivering) {
                newStatus = "Waiting for shipping";
            } else if (view.getId() == R.id.btn_shipping) {
                newStatus = "Waiting for delivery";
            } else if (view.getId() == R.id.btn_delivered) {
                newStatus = "Delivered";
            } else if (view.getId() == R.id.btn_cancel) {
                newStatus = "Cancelled";
            }

            if (newStatus != null) {
                updateOrderStatus(ordersDTO, newStatus, position);
            }
        };

        // Gán sự kiện cho các nút
        holder.btnDelivering.setOnClickListener(updateStatusListener);
        holder.btnShipping.setOnClickListener(updateStatusListener);
        holder.btnDelivered.setOnClickListener(updateStatusListener);
        holder.btnCancel.setOnClickListener(updateStatusListener);
    }


    @Override
    public int getItemCount() {
        return orderDetailList.size();
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

    // Cập nhật trạng thái đơn hàng qua API
    private void updateOrderStatus(OrdersDTO ordersDTO, String newStatus, int position) {
        if (token == null || token.isEmpty()) {
            Toast.makeText(context, "Token không hợp lệ. Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ordersDTO == null || ordersDTO.getId() == 0) {
            Toast.makeText(context, "Đơn hàng không hợp lệ.", Toast.LENGTH_SHORT).show();
            return;
        }

        ordersDTO.setStatus(newStatus);

        Call<String> call = apiOrders.updateOrderStatus("Bearer " + token, ordersDTO.getId(), ordersDTO);
        call.enqueue(new Callback<String>() {
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
}
