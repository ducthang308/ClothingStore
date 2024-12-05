package Adapter;

import android.content.Context;
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

import java.util.List;

import DTO.OrderDetailReturnDTO;
import DTO.OrdersDTO;
import Interface.APIClient;
import Interface.ApiOrders;
import Interface.PreferenceManager;
import Model.OrderDetail;
import Model.Product;
import Model.ProductImage;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaitingDeliveryAdapter extends RecyclerView.Adapter<WaitingDeliveryAdapter.WaitingDeliveryViewHolder> {
    private final Context context;
    private final List<OrderDetailReturnDTO> orderDetailList;
    private ApiOrders apiOrders;
    private String token;


    public WaitingDeliveryAdapter(Context context, List<OrderDetailReturnDTO> orderDetailList) {
        this.context = context;
        this.orderDetailList = orderDetailList;

        PreferenceManager preferenceManager = new PreferenceManager(context);
        token = preferenceManager.getToken();
        apiOrders = APIClient.getClient().create(ApiOrders.class);
    }

    @NonNull
    @Override
    public WaitingDeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_in_status_delivery, parent, false);
        return new WaitingDeliveryViewHolder(view);
    }

    public void onBindViewHolder(@NonNull WaitingDeliveryViewHolder holder, int position) {
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

        holder.btn_payment.setOnClickListener(v -> {
            OrdersDTO orderDTO = new OrdersDTO();
            orderDTO.setStatus("Shipped");

            updateOrderStatus(orderDetail.getOrderId(), orderDTO, holder.getAdapterPosition());
        });
    }

    public int getItemCount() {
        Log.d("Adapter", "Item count: " + orderDetailList.size());
        return orderDetailList.size();
    }

    public static class WaitingDeliveryViewHolder extends RecyclerView.ViewHolder {

    TextView productName, productSize, productPrice, productQuantity, totalPayment;
    ImageView productImage;
    Button btn_payment;

        public WaitingDeliveryViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            totalPayment = itemView.findViewById(R.id.total_money);
            productImage = itemView.findViewById(R.id.product_image);
            btn_payment = itemView.findViewById(R.id.btn_payment);
        }
    }
    private void updateOrderStatus(int orderId, OrdersDTO orderDTO, int position) {
        apiOrders.updateOrderStatus("Bearer " + token, orderId, orderDTO).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // Successfully updated status
                    Toast.makeText(context, "Đơn hàng đã được nhận!", Toast.LENGTH_SHORT).show();

                    // Remove item from the list or update UI
                    orderDetailList.remove(position);
                    notifyItemRemoved(position);
                } else {
                    // Handle error
                    Toast.makeText(context, "Lỗi cập nhật trạng thái đơn hàng!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Handle failure
                Toast.makeText(context, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


