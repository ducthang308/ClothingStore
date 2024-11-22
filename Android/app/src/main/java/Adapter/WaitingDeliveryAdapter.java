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

import com.example.duanandroid.R;

import java.util.List;

import Fragment.TabLayOutActivity;
import Model.OrderDetail;
import Model.Product;
import Model.ProductImage;
public class WaitingDeliveryAdapter extends RecyclerView.Adapter<WaitingDeliveryAdapter.WaitingDeliveryViewHolder> {



        private Context context;
        private List<Product> productList;
        private List<OrderDetail> orderDetailList;
        private List<ProductImage> productImageList;
        public WaitingDeliveryAdapter(Context context, List<Product> productList, List<OrderDetail> orderDetailList, List<ProductImage> productImageList) {

                this.context = context;
                this.productList = productList;
                this.orderDetailList = orderDetailList;
                this.productImageList = productImageList;
            }

            @NonNull
            public WaitingDeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(R.layout.item_in_status_delivery, parent, false);
                return new WaitingDeliveryViewHolder(view);
            }

            public void onBindViewHolder(@NonNull WaitingDeliveryViewHolder holder, int position) {
                // Lấy product, orderDetail từ danh sách tương ứng
                Product product = productList.get(position);
                OrderDetail orderDetail = orderDetailList.get(position);

                // Set thông tin sản phẩm
                holder.productName.setText(product.getProductName());
                holder.productSize.setText("Size: " + product.getSize());
                holder.productPrice.setText(String.valueOf(product.getPrice()) + "đ");
                holder.productQuantity.setText("x" + orderDetail.getNumberOfProduct());
                holder.totalPayment.setText(String.valueOf(orderDetail.getTotalMoney()) + "đ");


                holder.btnReceive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(context, TabLayOutActivity.class);
                        intent.putExtra("tabPosition", 3);
                        context.startActivity(intent);
                    }
                });
                if (productImageList != null && !productImageList.isEmpty()) {

                    ProductImage productImage = productImageList.get(position);
                    holder.productImage.setImageResource(R.drawable.ao); // Hình mặc định
                }
            }

            public int getItemCount() {
                return productList.size();
            }

            public static class WaitingDeliveryViewHolder extends RecyclerView.ViewHolder {

                TextView productName, productSize, productPrice, productQuantity, totalPayment;
                ImageView productImage;
                Button btnReceive;
                public WaitingDeliveryViewHolder(@NonNull View itemView) {
                    super(itemView);
                    productName = itemView.findViewById(R.id.product_name);
                    productSize = itemView.findViewById(R.id.product_size);
                    productPrice = itemView.findViewById(R.id.product_price);
                    productQuantity = itemView.findViewById(R.id.product_quantity);
                    totalPayment = itemView.findViewById(R.id.total_money);
                    productImage = itemView.findViewById(R.id.product_image);
                    btnReceive = itemView.findViewById(R.id.btn_receive);
                }
            }
}


