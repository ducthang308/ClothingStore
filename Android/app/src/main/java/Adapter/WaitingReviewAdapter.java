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
import com.example.duanandroid.View.PageDanhgiaActivity;

import java.util.List;
import java.util.Map;

import DTO.OrderDetailReturnDTO;
import Interface.ApiReview; // Import ApiReview
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaitingReviewAdapter extends RecyclerView.Adapter<WaitingReviewAdapter.WaitingReviewViewHolder> {
    private final Context context;
    private final List<OrderDetailReturnDTO> orderDetailList;
    private final ApiReview apiReview; // Thêm ApiReview

    // Constructor nhận 3 tham số: context, orderDetailList, apiReview
    public WaitingReviewAdapter(Context context, List<OrderDetailReturnDTO> orderDetailList, ApiReview apiReview) {
        this.context = context;
        this.orderDetailList = orderDetailList;
        this.apiReview = apiReview;  // Khởi tạo ApiReview
    }

    @NonNull
    @Override
    public WaitingReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_in_status_review, parent, false);
        return new WaitingReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaitingReviewAdapter.WaitingReviewViewHolder holder, int position) {
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

        holder.danhgia.setOnClickListener(v -> {
            Intent intent = new Intent(context, PageDanhgiaActivity.class);
            intent.putExtra("productId", orderDetail.getProductId());
            intent.putExtra("orderId", orderDetail.getOrderId());
            context.startActivity(intent);
        });

        checkIfReviewed(holder.danhgia, orderDetail.getOrderId(), orderDetail.getProductId());
    }

    @Override
    public int getItemCount() {
        return orderDetailList.size();
    }

    private void checkIfReviewed(Button danhgia, int orderId, int productId) {
        apiReview.checkIfReviewed(orderId, productId).enqueue(new Callback<Map<String, Boolean>>() {
            @Override
            public void onResponse(Call<Map<String, Boolean>> call, Response<Map<String, Boolean>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Boolean hasReviewed = response.body().get("hasReviewed");
                    if (hasReviewed != null && hasReviewed) {
                        danhgia.setVisibility(View.GONE);
                    } else {
                        danhgia.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Map<String, Boolean>> call, Throwable t) {
            }
        });
    }

    public static class WaitingReviewViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productSize, productPrice, productQuantity, totalPayment;
        ImageView productImage;
        Button danhgia;

        public WaitingReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            totalPayment = itemView.findViewById(R.id.total_money);
            productImage = itemView.findViewById(R.id.product_image);
            danhgia = itemView.findViewById(R.id.btn_review);
        }
    }
}
