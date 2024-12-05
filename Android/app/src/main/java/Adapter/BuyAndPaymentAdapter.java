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

import java.util.ArrayList;
import java.util.List;

import DTO.CartItemsDTO;
import DTO.ProductDTO;
import DTO.ProductImageDTO;
import Model.OrderDetail;
import Model.Product;
import Model.Product1;
import Model.ProductImage;

public class BuyAndPaymentAdapter extends RecyclerView.Adapter<BuyAndPaymentAdapter.BuyAndPaymentHolder> {
    private final List<ProductDTO> productList;
    private final List<CartItemsDTO> cartList;
    private final Context context;
    private OnQuantityChangeListener quantityChangeListener;

    public BuyAndPaymentAdapter(List<ProductDTO> productList, List<CartItemsDTO> cartList, Context context) {
        this.productList = productList;
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public BuyAndPaymentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_buyandpayment, parent, false);
        return new BuyAndPaymentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BuyAndPaymentHolder holder, int position) {
        if (isCartMode()) {
            bindCartItem(holder, position);
        } else {
            bindProductItem(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return isCartMode() ? cartList.size() : (productList != null ? productList.size() : 0);
    }

    private boolean isCartMode() {
        return cartList != null && !cartList.isEmpty();
    }

    private void bindCartItem(BuyAndPaymentHolder holder, int position) {
        CartItemsDTO cartItem = cartList.get(position);

        holder.productName.setText(cartItem.getProductName());
        holder.productPrice.setText(String.format("₫%,.0f", cartItem.getPrice()));
        loadProductImage(holder.productImage, cartItem.getImageUrl());

        updateQuantity(holder, position, cartItem.getQuantity());
    }

    private void bindProductItem(BuyAndPaymentHolder holder, int position) {
        ProductDTO product = productList.get(position);

        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(String.format("₫%,.0f", product.getPrice()));
        loadProductImage(holder.productImage, product.getImageUrls());

        updateQuantity(holder, position, 1);
    }


    private void updateQuantity(BuyAndPaymentHolder holder, int position, int initialQuantity) {
        holder.productQuantity.setText(String.valueOf(initialQuantity));

        holder.btnIncrease.setOnClickListener(v -> {
            int updatedQuantity = Integer.parseInt(holder.productQuantity.getText().toString()) + 1;
            holder.productQuantity.setText(String.valueOf(updatedQuantity));
            updateCartItemQuantity(position, updatedQuantity);

            if (quantityChangeListener != null) {
                quantityChangeListener.onQuantityChanged(position, updatedQuantity);
            }
        });

        holder.btnDecrease.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(holder.productQuantity.getText().toString());
            if (currentQuantity > 1) {
                int updatedQuantity = currentQuantity - 1;
                holder.productQuantity.setText(String.valueOf(updatedQuantity));
                updateCartItemQuantity(position, updatedQuantity);

                if (quantityChangeListener != null) {
                    quantityChangeListener.onQuantityChanged(position, updatedQuantity);
                }
            }
        });
    }

    private void updateCartItemQuantity(int position, int updatedQuantity) {
        if (isCartMode()) {
            cartList.get(position).setQuantity(updatedQuantity);
        }

        if (context instanceof BuyandpaymentActivity) {
            ((BuyandpaymentActivity) context).calculateTotalCostForCart();
        }
    }

    public List<Integer> getUpdatedQuantities() {
        List<Integer> updatedQuantities = new ArrayList<>();
        if (isCartMode()) {
            for (CartItemsDTO item : cartList) {
                updatedQuantities.add(item.getQuantity());
            }
        } else if (productList != null) {
            for (int i = 0; i < productList.size(); i++) {
                updatedQuantities.add(1);
            }
        }
        return updatedQuantities;
    }

    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
        this.quantityChangeListener = listener;
    }

    public class BuyAndPaymentHolder extends RecyclerView.ViewHolder {
        final TextView productName, productPrice, productQuantity;
        final ImageView productImage;
        final ImageButton btnIncrease, btnDecrease;

        public BuyAndPaymentHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product_image1);
            productName = itemView.findViewById(R.id.product_name);
            productQuantity = itemView.findViewById(R.id.product_quantity);
            productPrice = itemView.findViewById(R.id.product_price);
            btnIncrease = itemView.findViewById(R.id.btn_increase);
            btnDecrease = itemView.findViewById(R.id.btn_decrease);
        }
    }

    private void loadProductImage(ImageView imageView, Object imageSource) {
        if (imageSource instanceof String) {
            Glide.with(context)
                    .load((String) imageSource)
                    .placeholder(R.drawable.co4la)
                    .error(R.drawable.error)
                    .into(imageView);
        } else if (imageSource instanceof List) {
            List<String> imageUrls = (List<String>) imageSource;
            Glide.with(context)
                    .load(imageUrls.isEmpty() ? null : imageUrls.get(0))
                    .placeholder(R.drawable.co4la)
                    .error(R.drawable.error)
                    .into(imageView);
        } else {
            imageView.setImageResource(R.drawable.error);
        }
    }

    public interface OnQuantityChangeListener {
        void onQuantityChanged(int position, int updatedQuantity);
    }

    public void updateProductList(List<ProductDTO> products) {
        this.productList.clear();
        this.productList.addAll(products);
        notifyDataSetChanged();
    }
}