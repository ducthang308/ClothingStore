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
        this.productList = productList != null ? productList : new ArrayList<>();
        this.cartList = cartList != null ? cartList : new ArrayList<>();
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
        return isCartMode() ? cartList.size() : productList.size();
    }

    private boolean isCartMode() {
        return !cartList.isEmpty();
    }

    private void bindCartItem(BuyAndPaymentHolder holder, int position) {
        CartItemsDTO cartItem = cartList.get(position);

        holder.productName.setText(cartItem.getProductName());
        holder.productPrice.setText(String.format("₫%,.0f", cartItem.getPrice()));
        loadProductImage(holder.productImage, cartItem.getImageUrl());

        updateQuantity(holder, position, cartItem.getQuantity(), true);
    }

    private void bindProductItem(BuyAndPaymentHolder holder, int position) {
        ProductDTO product = productList.get(position);

        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(String.format("₫%,.0f", product.getPrice()));
        loadProductImage(holder.productImage, product.getImageUrls());

        updateQuantity(holder, position, 1, false);
    }

    private void updateQuantity(BuyAndPaymentHolder holder, int position, int initialQuantity, boolean isCart) {
        holder.productQuantity.setText(String.valueOf(initialQuantity));

        holder.btnIncrease.setOnClickListener(v -> {
            int updatedQuantity = Integer.parseInt(holder.productQuantity.getText().toString()) + 1;
            holder.productQuantity.setText(String.valueOf(updatedQuantity));
            updateItemQuantity(position, updatedQuantity, isCart);

            if (quantityChangeListener != null) {
                quantityChangeListener.onQuantityChanged(position, updatedQuantity);
            }
        });

        holder.btnDecrease.setOnClickListener(v -> {
            int currentQuantity = Integer.parseInt(holder.productQuantity.getText().toString());
            if (currentQuantity > 1) {
                int updatedQuantity = currentQuantity - 1;
                holder.productQuantity.setText(String.valueOf(updatedQuantity));
                updateItemQuantity(position, updatedQuantity, isCart);

                if (quantityChangeListener != null) {
                    quantityChangeListener.onQuantityChanged(position, updatedQuantity);
                }
            }
        });
    }

    private void updateItemQuantity(int position, int updatedQuantity, boolean isCart) {
        if (isCart) {
            cartList.get(position).setQuantity(updatedQuantity);
        } else {
            productList.get(position).setQuantity(updatedQuantity);
        }
        if (quantityChangeListener != null) {
            quantityChangeListener.onQuantityChanged(position, updatedQuantity);
        }

        if (context instanceof BuyandpaymentActivity) {
            float totalCost = 0f;
            if (isCart) {
                for (CartItemsDTO cartItem : cartList) {
                    totalCost += cartItem.getPrice() * cartItem.getQuantity();
                }
            } else {
                for (ProductDTO product : productList) {
                    totalCost += product.getPrice() * product.getQuantity();
                }
            }
            ((BuyandpaymentActivity) context).updateTotalCostView(totalCost);
        }
    }


    public List<Integer> getUpdatedQuantities() {
        List<Integer> updatedQuantities = new ArrayList<>();
        if (isCartMode()) {
            for (CartItemsDTO item : cartList) {
                updatedQuantities.add(item.getQuantity());
            }
        } else {
            for (ProductDTO product : productList) {
                updatedQuantities.add(product.getQuantity());
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
        String imageUrl = null;
        if (imageSource instanceof String) {
            imageUrl = (String) imageSource;
        } else if (imageSource instanceof List) {
            List<String> imageUrls = (List<String>) imageSource;
            if (!imageUrls.isEmpty()) {
                imageUrl = imageUrls.get(0);
            }
        }

        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.co4la)
                .error(R.drawable.error)
                .into(imageView);
    }

    public interface OnQuantityChangeListener {
        void onQuantityChanged(int position, int updatedQuantity);
    }
}
