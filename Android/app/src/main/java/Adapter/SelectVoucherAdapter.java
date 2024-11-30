package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.ArrayList;
import java.util.List;

import Model.Discount;

public class SelectVoucherAdapter extends RecyclerView.Adapter<SelectVoucherAdapter.VoucherViewHolder> {

    private List<Discount> discountList;
    private OnVoucherSelectListener selectListener;

    // Constructor
    public SelectVoucherAdapter(List<Discount> discountList, OnVoucherSelectListener selectListener) {
        this.discountList = discountList != null ? discountList : new ArrayList<>();
        this.selectListener = selectListener;
    }

    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_voucher, parent, false);
        return new VoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        Discount discount = discountList.get(position);
        if (discount == null) return;

        // Bind data to item views
        holder.txtDiscount.setText(String.format("%.0f%%", discount.getPercent() * 100));
        holder.tvMota.setText(discount.getNote());

        // Handle button click
        holder.btnUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectListener != null) {
                    selectListener.onVoucherSelected(discount);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return discountList.size();
    }

    // ViewHolder class
    public static class VoucherViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDiscount;
        private TextView tvMota;
        private Button btnUse;

        public VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDiscount = itemView.findViewById(R.id.txtDiscount);
            tvMota = itemView.findViewById(R.id.tv_mota);
            btnUse = itemView.findViewById(R.id.btn_use);
        }
    }

    // Callback interface
    public interface OnVoucherSelectListener {
        void onVoucherSelected(Discount discount);
    }
}
