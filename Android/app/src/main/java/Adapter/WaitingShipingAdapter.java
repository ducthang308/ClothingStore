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
import com.example.duanandroid.View.ReasoncancelActivity;

import java.util.List;

import Model.OrderDetail;
import Model.Product;
import Model.ProductImage;

public class WaitingShipingAdapter extends RecyclerView.Adapter<WaitingShipingAdapter.WaitingShipingViewHolder> {
    private Context context;
    private List<Product> productList;
    private List<OrderDetail> orderDetailList;
    private List<ProductImage> productImageList;

    // Constructor
    public WaitingShipingAdapter(Context context, List<Product> productList, List<OrderDetail> orderDetailList, List<ProductImage> productImageList) {
        this.context = context;
        this.productList = productList;
        this.orderDetailList = orderDetailList;
        this.productImageList = productImageList;
    }

    @NonNull
    @Override
    public WaitingShipingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_in_status_shiping, parent, false);
        return new WaitingShipingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaitingShipingAdapter.WaitingShipingViewHolder holder, int position) {
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
            ProductImage productImage = productImageList.get(position);
            holder.productImage.setImageResource(R.drawable.ao); // Hình mặc định
        }

        // Thêm sự kiện OnClick cho nút btnCancelOrder
        holder.btnCancelOrderinShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang ReasoncancelActivity khi người dùng click vào nút
                Intent intent = new Intent(context, ReasoncancelActivity.class);
//                intent.putExtra("productName", product.getProductName());
//                intent.putExtra("orderDetailId", orderDetail.getId());  // Gửi thêm thông tin nếu cần
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class WaitingShipingViewHolder extends RecyclerView.ViewHolder {

        TextView productName, productSize, productPrice, productQuantity, totalPayment;
        ImageView productImage;
        Button btnCancelOrderinShip;

        public WaitingShipingViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.product_name);
            productSize = itemView.findViewById(R.id.product_size);
            productPrice = itemView.findViewById(R.id.product_price);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            totalPayment = itemView.findViewById(R.id.total_money);
            productImage = itemView.findViewById(R.id.product_image);
            btnCancelOrderinShip = itemView.findViewById(R.id.btn_cancel_inShiping); // Ánh xạ nút Cancel
        }
    }
}
