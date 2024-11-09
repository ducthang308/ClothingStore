package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.List;

import Model.Discount;

public class DiscountManageAdapter extends RecyclerView.Adapter<DiscountManageAdapter.DiscountViewHolder> {

    private List<Discount> discountList;
    private Context context;
    private OnDiscountSelectedListener listener;
    private int selectedPosition = -1; // Track the selected position

    public DiscountManageAdapter(Context context, List<Discount> discountList, OnDiscountSelectedListener listener) {
        this.context = context;
        this.discountList = discountList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DiscountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_discount_manage, parent, false);
        return new DiscountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Discount discount = discountList.get(position);
        holder.tvPercent.setText(discount.getPercent() + "%");
        holder.tvNote.setText(discount.getNote());

        // Set the checkbox checked state based on the selected position
        holder.checkbox.setChecked(position == selectedPosition);

        // Checkbox click listener
        holder.checkbox.setOnClickListener(v -> {
            if (selectedPosition == position) {
                // If the current item is already selected, deselect it
                selectedPosition = -1;
                listener.onDiscountSelected(null); // No discount selected
            } else {
                // Select the new item
                selectedPosition = position;
                listener.onDiscountSelected(discount);
            }
            notifyDataSetChanged(); // Update the UI to reflect the selection change
        });
    }

    @Override
    public int getItemCount() {
        return discountList.size();
    }

    public static class DiscountViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkbox;
        TextView tvPercent, tvNote;

        public DiscountViewHolder(View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox);
            tvPercent = itemView.findViewById(R.id.tv_percent);
            tvNote = itemView.findViewById(R.id.tv_note);
        }
    }

    public interface OnDiscountSelectedListener {
        void onDiscountSelected(Discount discount);
    }
}
