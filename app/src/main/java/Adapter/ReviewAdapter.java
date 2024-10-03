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

import Model.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<Review> reviewList;

    public ReviewAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the review item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhgia, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        // Get the review data
        Review review = reviewList.get(position);

        // Set reviewer name
        holder.reviewerName.setText(review.getReviewerName());

        // Set review rating
        holder.reviewRating.setRating(review.getRating());

        // Set review content
        holder.reviewContent.setText(review.getReviewContent());

        // Set reviewer image (using a placeholder or loading image from URL)
        holder.reviewerImage.setImageResource(R.drawable.user); // You can use Glide/Picasso to load images
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        ImageView reviewerImage;
        TextView reviewerName;
        TextView reviewContent;
        RatingBar reviewRating;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewerImage = itemView.findViewById(R.id.reviewer_image);
            reviewerName = itemView.findViewById(R.id.reviewer_name);
            reviewContent = itemView.findViewById(R.id.review_content);
            reviewRating = itemView.findViewById(R.id.review_rating);
        }
    }
}

