package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.List;

import Model.Product;
import Model.Review;
import Model.User;

public class ListReviewAdapter extends RecyclerView.Adapter<ListReviewAdapter.ListReviewViewHolder> {
    private List<Review> reviewList;
    private List<User> userList;
    private List<Product> productList;

    public ListReviewAdapter(List<Review> reviewList, List<User> userList, List<Product> productList) {
        this.reviewList = reviewList;
        this.userList = userList;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ListReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the review item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_review, parent, false);
        return new ListReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListReviewViewHolder holder, int position) {
        // Get the review data
        Review review = reviewList.get(position);

        // Find the corresponding user by userId
        User user = null;
        for (User u : userList) {
            if (u.getId() == review.getUserId()) {
                user = u;
                break;
            }
        }

        // Find the corresponding product by productId
        Product product = null;
        for (Product p : productList) {
            if (p.getId() == review.getProductId()) {
                product = p;
                break;
            }
        }

        // Bind user information
        if (user != null) {
            holder.reviewerName.setText(user.getFullname());
        }
        holder.reviewRating.setRating(review.getNumberOfStars());
        holder.reviewContent.setText(review.getNote());
        holder.reviewerImage.setImageResource(R.drawable.user); // Placeholder image

        // Bind product information
        if (product != null) {
            holder.productName.setText(product.getProductName() + ", " + product.getSize());
            holder.productPrice.setText(product.getPrice() + "đ");
            holder.productImage.setImageResource(R.drawable.ao); // Placeholder image
        }
    }

    @Override
    public int getItemCount() {
        return (reviewList != null) ? reviewList.size() : 0;
    }

    public static class ListReviewViewHolder extends RecyclerView.ViewHolder {
        ImageView reviewerImage;
        TextView reviewerName;
        TextView reviewContent;
        RatingBar reviewRating;

        ImageView productImage;
        TextView productName;
        TextView productPrice;

        public ListReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewerImage = itemView.findViewById(R.id.reviewer_image);
            reviewerName = itemView.findViewById(R.id.reviewer_name);
            reviewContent = itemView.findViewById(R.id.review_content);
            reviewRating = itemView.findViewById(R.id.review_rating);

            productImage = itemView.findViewById(R.id.product_image);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
        }
    }
}
