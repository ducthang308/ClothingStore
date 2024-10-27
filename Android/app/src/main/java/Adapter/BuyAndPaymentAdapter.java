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
import Model.Product1;
import Model.ProductImage;

public class BuyAndPaymentAdapter extends RecyclerView.Adapter<BuyAndPaymentAdapter.BuyAndPaymentHolder> {
    private List<Product1> productList;
    private List<OrderDetail> orderDetailList;
    private List<ProductImage> productImageList;
    private Context context;

    // Constructor
    public BuyAndPaymentAdapter(Context context, List<Product1> productList, List<OrderDetail> orderDetailList, List<ProductImage> productImageList) {
        this.productList = productList;
        this.orderDetailList = orderDetailList;
        this.productImageList = productImageList;
        this.context = context;
    }

    // Inflate layout cho từng item trong RecyclerView
    @NonNull
    @Override
    public BuyAndPaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_buyandpayment, parent, false);
        return new BuyAndPaymentHolder(view);
    }

    // Bind dữ liệu sản phẩm và hình ảnh vào ViewHolder
    @Override
    public void onBindViewHolder(@NonNull BuyAndPaymentHolder holder, int position) {
        Product1 product = productList.get(position);
        OrderDetail orderDetail = orderDetailList.get(position);
        ProductImage productImage = productImageList.get(position);

        // Set dữ liệu vào các view
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(String.format("₫%,.0f", orderDetail.getPrice()));
        holder.productSize.setText(product.getSize());
        holder.productQuantity.setText(String.valueOf(orderDetail.getNumberOfProduct()));
        int imageResId = Integer.parseInt(productImage.getImageUrl());
        holder.productImage.setImageResource(Integer.parseInt(productImage.getImageUrl()));
    }

    // Trả về số lượng sản phẩm
    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder chứa các view trong item_buy_and_payment.xml
    public class BuyAndPaymentHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productSize, productQuantity;
        ImageView productImage;

        public BuyAndPaymentHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image1);
            productName = itemView.findViewById(R.id.product_name);
            productSize = itemView.findViewById(R.id.product_size);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            productPrice = itemView.findViewById(R.id.product_price);
        }
    }
}
