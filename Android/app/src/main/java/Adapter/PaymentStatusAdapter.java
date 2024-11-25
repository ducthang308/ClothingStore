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

public class PaymentStatusAdapter extends RecyclerView.Adapter<PaymentStatusAdapter.PaymentStatusViewHolder> {

    private Context context;
    private List<Product> productList;
    private List<OrderDetail> orderDetailList;
    private List<ProductImage> productImageList;
//    private List<Order> orderList;

    public PaymentStatusAdapter(Context context, List<Product> productList, List<OrderDetail> orderDetailList, List<ProductImage> productImageList) {
        this.context = context;
        this.productList = productList;
        this.orderDetailList = orderDetailList;
        this.productImageList = productImageList;
//        this.orderList = orderList;
    }

    @NonNull
    @Override
    public PaymentStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_in_status_payment, parent, false); // Tham chiếu đến layout đầu tiên
        return new PaymentStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentStatusViewHolder holder, int position) {
//        // Lấy product, orderDetail từ danh sách tương ứng
//        Product product = productList.get(position);
//        OrderDetail orderDetail = orderDetailList.get(position);
////        Order oder = orderList.get(position);
//
//        // Set thông tin sản phẩm
//        holder.productName.setText(product.getProductName());
//        holder.productSize.setText("Size: " + product.getSize());
//        holder.productPrice.setText(String.valueOf(product.getPrice()) + "đ");
//        holder.productQuantity.setText("x" + orderDetail.getNumberOfProduct());
//        holder.totalPayment.setText(String.valueOf(orderDetail.getTotalMoney()) + "đ");
//
//        // Gán ảnh sản phẩm nếu có
//        if (productImageList != null && !productImageList.isEmpty()) {
//            // Lấy ảnh sản phẩm theo productId
//            ProductImage productImage = productImageList.get(position);
//            // Nếu sử dụng image URL hoặc drawable, bạn có thể set ảnh vào ImageView như sau
//            // Glide.with(context).load(productImage.getImageUrl()).into(holder.productImage);
//            // Ví dụ set ảnh tạm thời
//            holder.productImage.setImageResource(R.drawable.ao); // Hình mặc định
//        }


//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {



                // Chuyển trang khi click vào item
//               Intent intent = new Intent(context, OrderDetailActivity.class);

//                // Truyền dữ liệu sản phẩm qua Intent
//                intent.putExtra("productName", product.getProductName());
//                intent.putExtra("productPrice", product.getPrice());
//                intent.putExtra("productSize", product.getSize());
//                intent.putExtra("productQuantity", orderDetail.getNumberOfProduct());
//                intent.putExtra("totalPayment", orderDetail.getTotalMoney());

                // Bắt đầu Activity mới
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class PaymentStatusViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productSize, productPrice, productQuantity, totalPayment;
        ImageView productImage;

        public PaymentStatusViewHolder(@NonNull View itemView) {
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
