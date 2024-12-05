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

public class ReasonCancelAdapter extends RecyclerView.Adapter<ReasonCancelAdapter.ReasonCancelHolder> {
    private Context context;
    private List<Product> productList;
    private List<OrderDetail> orderDetailList;
    private List<ProductImage> productImageList;

    public ReasonCancelAdapter(Context context, List<Product> productList, List<OrderDetail> orderDetailList, List<ProductImage> productImageList) {
        this.context = context;
        this.productList = productList;
        this.orderDetailList = orderDetailList;
        this.productImageList = productImageList;
    }

    @NonNull
    @Override
    public ReasonCancelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reason_cancel, parent, false);
        return new ReasonCancelAdapter.ReasonCancelHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReasonCancelHolder holder, int position) {
//        Product product = productList.get(position);
//        OrderDetail orderDetail = orderDetailList.get(position);
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
//            holder.productImage.setImageResource(R.drawable.ao); // Hình mặc định
//        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ReasonCancelHolder extends RecyclerView.ViewHolder{
        TextView productName, productSize, productPrice, productQuantity, totalPayment;
        ImageView productImage;
        public ReasonCancelHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            totalPayment = itemView.findViewById(R.id.total_money);
            productImage = itemView.findViewById(R.id.product_image);
        }
    }
}
