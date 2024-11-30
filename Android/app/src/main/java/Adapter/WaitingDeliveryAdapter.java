package Adapter;

import android.content.Context;
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

import java.util.List;

import DTO.OrderDetailReturnDTO;
import Model.OrderDetail;
import Model.Product;
import Model.ProductImage;
public class WaitingDeliveryAdapter extends RecyclerView.Adapter<WaitingDeliveryAdapter.WaitingDeliveryViewHolder> {
    private final Context context;
    private final List<OrderDetailReturnDTO> orderDetailList;

    public WaitingDeliveryAdapter(Context context, List<OrderDetailReturnDTO> orderDetailList) {
        this.context = context;
        this.orderDetailList = orderDetailList;
    }

    @NonNull
    @Override
            public WaitingDeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(R.layout.item_in_status_delivery, parent, false);
                return new WaitingDeliveryViewHolder(view);
            }

            public void onBindViewHolder(@NonNull WaitingDeliveryViewHolder holder, int position) {
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
//                // Lấy product, orderDetail từ danh sách tương ứng
//                Product product = productList.get(position);
//                OrderDetail orderDetail = orderDetailList.get(position);
//
//                // Set thông tin sản phẩm
//                holder.productName.setText(product.getProductName());
//                holder.productSize.setText("Size: " + product.getSize());
//                holder.productPrice.setText(String.valueOf(product.getPrice()) + "đ");
//                holder.productQuantity.setText("x" + orderDetail.getNumberOfProduct());
//                holder.totalPayment.setText(String.valueOf(orderDetail.getTotalMoney()) + "đ");
//
//                // Gán ảnh sản phẩm nếu có
//                if (productImageList != null && !productImageList.isEmpty()) {
//                    // Lấy ảnh sản phẩm theo productId
//                    ProductImage productImage = productImageList.get(position);
//                    holder.productImage.setImageResource(R.drawable.ao); // Hình mặc định
//                }
            }

            public int getItemCount() {
                // Log số lượng phần tử trong Adapter
                Log.d("Adapter", "Item count: " + orderDetailList.size());
                return orderDetailList.size();
            }

            public static class WaitingDeliveryViewHolder extends RecyclerView.ViewHolder {

                TextView productName, productSize, productPrice, productQuantity, totalPayment;
                ImageView productImage;

                public WaitingDeliveryViewHolder(@NonNull View itemView) {
                    super(itemView);
                    productName = itemView.findViewById(R.id.product_name);
                    productPrice = itemView.findViewById(R.id.product_price);
                    productQuantity = itemView.findViewById(R.id.product_quantity);
                    totalPayment = itemView.findViewById(R.id.total_money);
                    productImage = itemView.findViewById(R.id.product_image);
                }
            }
}


