package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duanandroid.R;
import com.example.duanandroid.View.BuyandpaymentActivity;

import java.util.List;

import DTO.ProductDTO;
import DTO.ProductImageDTO;
import Model.OrderDetail;
import Model.Product;
import Model.Product1;
import Model.ProductImage;

public class BuyAndPaymentAdapter extends RecyclerView.Adapter<BuyAndPaymentAdapter.BuyAndPaymentHolder> {
    private List<ProductDTO> productList;
    private int[] quantities;
    private Context context;

    public BuyAndPaymentAdapter(List<ProductDTO> productList, Context context) {
        this.productList = productList;
        this.context = context;
        this.quantities = new int[productList.size()];

        // Khởi tạo số lượng mặc định là 1 cho mỗi sản phẩm
        for (int i = 0; i < productList.size(); i++) {
            quantities[i] = 1;
        }
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
        ProductDTO product = productList.get(position);


        // Set dữ liệu vào các view
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(String.format("₫%,.0f", product.getPrice()));

        // Tải hình ảnh sản phẩm
        loadProductImage(holder.productImage, product.getImageUrls());



        // Xử lý sự kiện tăng số lượng
        holder.btnIncrease.setOnClickListener(v -> {
            quantities[position]++;
            holder.productQuantity.setText(String.valueOf(quantities[position]));
            if (context instanceof BuyandpaymentActivity) {
                ((BuyandpaymentActivity) context).calculateTotalCost();
            }
        });

        holder.btnDecrease.setOnClickListener(v -> {
            if (quantities[position] > 1) {
                quantities[position]--;
                holder.productQuantity.setText(String.valueOf(quantities[position]));
                if (context instanceof BuyandpaymentActivity) {
                    ((BuyandpaymentActivity) context).calculateTotalCost();
                }
            }
        });
    }

    // Trả về số lượng sản phẩm
    @Override
    public int getItemCount() {
        return productList != null ? productList.size() : 0;
    }

    // ViewHolder chứa các view trong item_buy_and_payment.xml
    public class BuyAndPaymentHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productSize, productQuantity;
        ImageView productImage;
        ImageButton btnIncrease,btnDecrease;

        public BuyAndPaymentHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image1);
            productName = itemView.findViewById(R.id.product_name);
            productSize = itemView.findViewById(R.id.product_size);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            productPrice = itemView.findViewById(R.id.product_price);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
        }
    }

    // Phương thức để lấy số lượng từng sản phẩm
    public int[] getQuantities() {
        return quantities;
    }

    private void loadProductImage(ImageView imageView, List<String> imageUrls) {
        if (imageUrls != null && !imageUrls.isEmpty()) {
            String imageUrl = imageUrls.get(0); // Lấy ảnh đầu tiên
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable.co4la) // Hình placeholder
                    .error(R.drawable.error) // Hình lỗi
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.error); // Ảnh lỗi mặc định
        }
    }
}

