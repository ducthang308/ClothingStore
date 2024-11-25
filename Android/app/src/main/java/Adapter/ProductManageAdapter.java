package Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import DTO.ProductDTO;

public class ProductManageAdapter extends BaseAdapter {

    private final Context context;
    private List<ProductDTO> productList;
    private ProductDTO selectedProduct = null;
    private final OnProductClickListener listener;

    public ProductManageAdapter(Context context, List<ProductDTO> productList, OnProductClickListener listener) {
        this.context = context;
        this.productList = productList != null ? productList : new ArrayList<>();
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productList.get(position).getId(); // Assuming `getId` returns a unique ID for each product
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            // Inflate the layout if it's not reused
            convertView = LayoutInflater.from(context).inflate(R.layout.item_product_manage, parent, false);
            holder = new ViewHolder();
            holder.productName = convertView.findViewById(R.id.product_name);
            holder.price = convertView.findViewById(R.id.price);
            holder.color = convertView.findViewById(R.id.color);
            holder.checkBox = convertView.findViewById(R.id.checkbox);
            holder.imageProduct = convertView.findViewById(R.id.image_product);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ProductDTO product = productList.get(position);

        holder.productName.setText(product.getProductName());
        holder.price.setText(String.format("₫%.0f", product.getPrice()));
        holder.color.setText("Màu sắc: " + product.getColor());

        loadProductImage(holder.imageProduct, product.getImageUrls());

        holder.checkBox.setOnCheckedChangeListener(null);  // Reset the listener to prevent triggering the event when binding
        holder.checkBox.setChecked(product.equals(selectedProduct));
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                setSelectedProduct(product);
            } else {
                deselectProduct();
            }
        });

        // Item click listener
        convertView.setOnClickListener(v -> {
            if (listener != null && product != null) {
                listener.onProductClick(product);
            } else {
                Toast.makeText(context, "Sản phẩm không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    public void updateData(List<ProductDTO> newProductList) {
        if (newProductList == null) {
            newProductList = new ArrayList<>();
        }
        this.productList = newProductList;
        notifyDataSetChanged();
    }

    public ProductDTO getSelectedProduct() {
        return selectedProduct;
    }

    private void loadProductImage(ImageView imageView, List<String> imageUrls) {
        if (imageUrls != null && !imageUrls.isEmpty()) {
            String imageUrl = imageUrls.get(0);
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.co4la)
                    .error(R.drawable.error)
                    .into(imageView);
        } else {
            Log.d("ProductManageAdapter", "No image URL available for product.");
            imageView.setImageResource(R.drawable.error);
        }
    }

    private void setSelectedProduct(ProductDTO product) {
        selectedProduct = product;
        notifyDataSetChanged();
    }

    private void deselectProduct() {
        selectedProduct = null;
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        TextView productName, price, color;
        CheckBox checkBox;
        ImageView imageProduct;
    }

    public interface OnProductClickListener {
        void onProductClick(ProductDTO product);
    }
}

