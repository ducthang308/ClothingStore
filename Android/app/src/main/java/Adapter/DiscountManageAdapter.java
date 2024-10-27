package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Model.Discount;

public class DiscountManageAdapter extends RecyclerView.Adapter<DiscountManageAdapter.DiscountViewHolder> {
    private List<Discount> discountList;
    private int activityType;

    public DiscountManageAdapter(List<Discount> discountList, int activityType) {
        this.discountList = discountList != null ? discountList : new ArrayList<>();
        this.activityType = activityType;
    }

    @NonNull
    @Override
    public DiscountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discount_manage, parent, false);
        return new DiscountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscountViewHolder holder, int position) {
        Discount discount = discountList.get(position);
        if (discount == null) return;

        holder.txtDiscount.setText(String.format("%s%%", discount.getDiscount()));
        holder.tv_mota.setText(discount.getMota());

        // Phân biệt logic hiển thị dựa trên activityType
        if (activityType == 1) { // DiscountManageActivity
            holder.checkBox.setVisibility(View.VISIBLE);
            holder.checkBox.setChecked(false);
        } else if (activityType == 2) { // KhoVoucherActivity
            holder.checkBox.setVisibility(View.GONE); // Ẩn CheckBox
        }
    }

    @Override
    public int getItemCount() {
        return discountList.size();
    }

    public static class DiscountViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox;
        private TextView txtDiscount;
        private TextView tv_mota;

        public DiscountViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.checkbox);
            txtDiscount = itemView.findViewById(R.id.txtdiscount);
            tv_mota = itemView.findViewById(R.id.tv_mota);
        }
    }
}
