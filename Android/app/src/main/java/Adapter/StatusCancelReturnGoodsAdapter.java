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

import java.util.List;

import DTO.OrderDetailReturnDTO;

public class StatusCancelReturnGoodsAdapter extends RecyclerView.Adapter<StatusCancelReturnGoodsAdapter.StatusCancelReturnGoodsViewHolder> {
    private final Context context;
    private final List<OrderDetailReturnDTO> orderDetailList;

    public StatusCancelReturnGoodsAdapter(Context context, List<OrderDetailReturnDTO> orderDetailList) {
        this.context = context;
        this.orderDetailList = orderDetailList;
    }

    @NonNull
    @Override
    public StatusCancelReturnGoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_in_status_return_cancel, parent, false);
        return new StatusCancelReturnGoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusCancelReturnGoodsViewHolder holder, int position) {
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
        // Log số lượng phần tử trong Adapter
        return orderDetailList.size();
    }

    public static class StatusCancelReturnGoodsViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productQuantity, totalPayment;
        ImageView productImage;

        public StatusCancelReturnGoodsViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            totalPayment = itemView.findViewById(R.id.total_money);
            productImage = itemView.findViewById(R.id.product_image);
        }
    }
}
