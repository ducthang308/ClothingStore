package Adapter;

import android.content.Context;
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

import DTO.ReviewWithUserFullNameDTO;
import Model.Review;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private Context context;
    private List<ReviewWithUserFullNameDTO> rvDTOList;  // Use List<Object> to handle both types

    // Constructor to handle either List<ReviewWithUserFullNameDTO> or List<Review>
    public ReviewAdapter(Context context, List<ReviewWithUserFullNameDTO> rvDTOList) {
        this.context = context;
        this.rvDTOList = rvDTOList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Object currentItem = rvDTOList.get(position);

        if (currentItem instanceof ReviewWithUserFullNameDTO) {
            // Handle ReviewWithUserFullNameDTO
            ReviewWithUserFullNameDTO rv = (ReviewWithUserFullNameDTO) currentItem;
            holder.reviewerImage.setImageResource(R.drawable.user); // Placeholder image for user
            holder.reviewerName.setText("Họ và tên: " + rv.getFullName());
            holder.reviewRating.setRating(rv.getReview().getNumberOfStars());
            holder.reviewContent.setText(rv.getReview().getNote());
        } else if (currentItem instanceof Review) {
            // Handle Review
            Review review = (Review) currentItem;
            holder.reviewerName.setText("Họ và tên: Unknown"); // Placeholder name
            holder.reviewRating.setRating(review.getNumberOfStars());
            holder.reviewContent.setText(review.getNote());
        }
    }

    @Override
    public int getItemCount() {
        return rvDTOList.size();
    }

    // ViewHolder class
    public static class ReviewViewHolder extends RecyclerView.ViewHolder {
        ImageView reviewerImage;
        TextView reviewerName;
        RatingBar reviewRating;
        TextView reviewContent;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            reviewerImage = itemView.findViewById(R.id.reviewer_image);
            reviewerName = itemView.findViewById(R.id.reviewer_name);
            reviewRating = itemView.findViewById(R.id.review_rating);
            reviewContent = itemView.findViewById(R.id.review_content);
        }
    }
}
