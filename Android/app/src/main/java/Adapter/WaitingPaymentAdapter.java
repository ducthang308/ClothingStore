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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.duanandroid.R;
import com.example.duanandroid.View.OrderDetailActivity;
import com.example.duanandroid.View.ReasoncancelActivity;

import java.util.List;

import DTO.OrderDetailReturnDTO;

public class WaitingPaymentAdapter extends RecyclerView.Adapter<WaitingPaymentAdapter.WaitingPaymentViewHolder> {

    private final Context context;
    private final List<OrderDetailReturnDTO> orderDetailList;

    public WaitingPaymentAdapter(Context context, List<OrderDetailReturnDTO> orderDetailList) {
        this.context = context;
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
        OrderDetailReturnDTO orderDetail = orderDetailList.get(position);

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

//        // Sự kiện nút "Hủy đơn hàng"
//        holder.btnCancelOrder.setOnClickListener(v -> {
//            Intent intent = new Intent(context, ReasoncancelActivity.class);
//            intent.putExtra("orderDetailId", orderDetail.getOrderDetailId()); // Truyền đúng ID đơn hàng
//            context.startActivity(intent);
//        });
//
//        // Sự kiện khi nhấn vào toàn bộ item
//        holder.itemView.setOnClickListener(view -> {
//            Intent intent = new Intent(context, OrderDetailActivity.class);
//            intent.putExtra("orderDetailId", orderDetail.getOrderDetailId()); // Truyền đúng ID đơn hàng
//            context.startActivity(intent);
//        });
    }

    @Override
    public int getItemCount() {
        // Log số lượng phần tử trong Adapter
        Log.d("Adapter", "Item count: " + orderDetailList.size());
        return orderDetailList.size();
    }

    // ViewHolder class
    public static class WaitingPaymentViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productQuantity, totalPayment;
        ImageView productImage;
        Button btnCancelOrder;

        public WaitingPaymentViewHolder(@NonNull View itemView) {
            super(itemView);
            // Ánh xạ View
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            totalPayment = itemView.findViewById(R.id.total_money);
            productImage = itemView.findViewById(R.id.product_image);
            btnCancelOrder = itemView.findViewById(R.id.btn_cancel_order);
        }
    }
}
