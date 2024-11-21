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

import Model.Product;
import Model.Product1;
import Model.ProductImage;

public class ProductManageAdapter extends RecyclerView.Adapter<ProductManageAdapter.ProductManageHolder> {
    private List<Product1> productList;
    private List<ProductImage> productImageList;
    private Context context;

    // Constructor
    public ProductManageAdapter(Context context, List<Product1> productList, List<ProductImage> productImageList) {
        this.productList = productList;
        this.productImageList = productImageList;
        this.context = context;
    }

    // Inflate layout cho từng item trong RecyclerView
    @NonNull
    @Override
    public ProductManageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_product_manage, parent, false);
        return new ProductManageHolder(view);
    }

    // Bind dữ liệu sản phẩm và hình ảnh vào ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ProductManageHolder holder, int position) {
//        Product product = productList.get(position);
//        ProductImage productImage = productImageList.get(position);
//        holder.productName.setText(product.getProductName());
//        holder.productPrice.setText(String.format("₫%,.0f", product.getPrice()));
//        holder.soluong.setText(String.valueOf(product.getSoluong()));
//        int imageResId = Integer.parseInt(productImage.getImageUrl());
//        holder.productImage.setImageResource(Integer.parseInt(productImage.getImageUrl()));
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    // ViewHolder chứa các view trong item_product.xml
    public class ProductManageHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productSize, soluong; // Thêm productSize
        ImageView productImage;

        public ProductManageHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.image_product);
            productName = itemView.findViewById(R.id.tv_tensp);
            productSize = itemView.findViewById(R.id.size);
            soluong = itemView.findViewById(R.id.soluong_sizeS);
            productPrice = itemView.findViewById(R.id.price);

        }
    }
}
