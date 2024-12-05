package Adapter;

import android.content.Context;
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

import java.util.Collections;
import java.util.List;

import DTO.OrderDetailReturnDTO;
import Model.OrderDetail;
import Model.Product;
import Model.ProductImage;

public class CancelOrderAdminAdapter extends RecyclerView.Adapter<CancelOrderAdminAdapter.CancelOrderAdminViewHolder> {
    private final Context context;
    private final List<OrderDetailReturnDTO> orderDetailList;


    public CancelOrderAdminAdapter(Context context, List<OrderDetailReturnDTO> orderDetailList ){
        this.context = context;
        this.orderDetailList = orderDetailList;
    }

    @NonNull
    @Override
    public CancelOrderAdminViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_in_status_return_cancel_admin, parent, false); // Đổi layout thành trạng thái "cancel/return goods"
        return new CancelOrderAdminViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CancelOrderAdminViewHolder holder, int position) {
        OrderDetailReturnDTO orderDetail = orderDetailList.get(position);

        holder.productName.setText(orderDetail.getProductName());
        holder.productPrice.setText(String.format("%,.0fđ", orderDetail.getTotalMoney() / orderDetail.getNumberOfProduct()));
        holder.productQuantity.setText("x" + orderDetail.getNumberOfProduct());
        holder.totalPayment.setText(String.format("%,.0fđ", orderDetail.getTotalMoney()));

        Glide.with(context)
                .load(orderDetail.getImageUrl())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.co4la)
                        .error(R.drawable.error)
                        .centerCrop())
                .into(holder.productImage);
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    public static class CancelOrderAdminViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productSize, productPrice, productQuantity, totalPayment;
        ImageView productImage;

        public CancelOrderAdminViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            totalPayment = itemView.findViewById(R.id.total_money);
            productImage = itemView.findViewById(R.id.product_image);
        }
    }
}
