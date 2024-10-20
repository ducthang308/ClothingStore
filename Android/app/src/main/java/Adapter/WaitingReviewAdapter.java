package Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.List;

import Model.OrderDetail;
import Model.Product;
import Model.ProductImage;

public class WaitingReviewAdapter extends RecyclerView.Adapter<WaitingReviewAdapter.WaitingReviewViewHolder> {




        private Context context;
        private List<Product> productList;
        private List<OrderDetail> orderDetailList;
        private List<ProductImage> productImageList;

        // Constructor
        public WaitingReviewAdapter(Context context, List<Product> productList, List<OrderDetail> orderDetailList, List<ProductImage> productImageList) {
            this.context = context;
            this.productList = productList;
            this.orderDetailList = orderDetailList;
            this.productImageList = productImageList;
        }

        @NonNull
        @Override
        public WaitingReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_in_status_review, parent, false); // Đổi layout thành trạng thái "review"
            return new WaitingReviewViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull WaitingReviewAdapter.WaitingReviewViewHolder holder, int position) {
            Product product = productList.get(position);
            OrderDetail orderDetail = orderDetailList.get(position);

            // Set thông tin sản phẩm
            holder.productName.setText(product.getProductName());
            holder.productSize.setText("Size: " + product.getSize());
            holder.productPrice.setText(String.valueOf(product.getPrice()) + "đ");
            holder.productQuantity.setText("x" + orderDetail.getNumberOfProduct());
            holder.totalPayment.setText(String.valueOf(orderDetail.getTotalMoney()) + "đ");

            // Gán ảnh sản phẩm nếu có
            if (productImageList != null && !productImageList.isEmpty()) {
                // Lấy ảnh sản phẩm theo productId
                ProductImage productImage = productImageList.get(position);
                holder.productImage.setImageResource(R.drawable.ao); // Hình mặc định
            }

        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        public static class WaitingReviewViewHolder extends RecyclerView.ViewHolder {

            TextView productName, productSize, productPrice, productQuantity, totalPayment;
            ImageView productImage;

            public WaitingReviewViewHolder(@NonNull View itemView) {
                super(itemView);
                productName = itemView.findViewById(R.id.product_name);
                productSize = itemView.findViewById(R.id.product_size);
                productPrice = itemView.findViewById(R.id.product_price);
                productQuantity = itemView.findViewById(R.id.product_quantity);
                totalPayment = itemView.findViewById(R.id.total_money);
                productImage = itemView.findViewById(R.id.product_image);
            }
        }

}
