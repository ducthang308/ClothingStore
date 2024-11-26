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

public class ItemProductInItemOrderAdapter extends RecyclerView.Adapter<ItemProductInItemOrderAdapter.ProductViewHolder> {
    private Context context;
    private List<Product> productList;
    private List<ProductImage> productImageList;
    private List<OrderDetail> orderDetailList;

    public ItemProductInItemOrderAdapter(Context context, List<Product> productList, List<ProductImage> productImageList, List<OrderDetail> orderDetailList) {
        this.context = context;
        this.productList = productList;
        this.productImageList = productImageList;
        this.orderDetailList = orderDetailList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_in_order_manage, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        ProductImage productImage = productImageList.get(position);
        OrderDetail orderDetail = orderDetailList.get(position);

        holder.tvProductName.setText(product.getProductName());
        holder.tvQuantity.setText("x" + orderDetail.getNumberOfProduct());
        holder.tvPrice.setText(String.valueOf( product.getPrice()));
        holder.productImage.setImageResource(productImage.getId());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView tvProductName, tvQuantity, tvPrice,ivImage;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.image_order);
            tvProductName = itemView.findViewById(R.id.tv_tensp);
            tvQuantity = itemView.findViewById(R.id.tv_soluong);
            tvPrice = itemView.findViewById(R.id.tv_gia);
        }
    }
}
