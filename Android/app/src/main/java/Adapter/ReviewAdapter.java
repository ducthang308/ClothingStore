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
import Model.User;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<Review> reviewList;
    private List<User> userList;

    public ReviewAdapter(List<Review> reviewList, List<User> userList) {
        this.reviewList = reviewList;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the review item layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
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

        if (user != null) {
            // Set reviewer name
            holder.reviewerName.setText(user.getFullname());
        }
        holder.reviewRating.setRating(review.getNumberOfStars());
        holder.reviewContent.setText(review.getNote());
        holder.reviewerImage.setImageResource(R.drawable.user); // Placeholder image
    }

    @Override
    public int getItemCount() {
        return (reviewList != null) ? reviewList.size() : 0;
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

