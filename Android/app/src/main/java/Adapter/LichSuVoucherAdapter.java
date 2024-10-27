package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.List;

import Model.Discount;

public class LichSuVoucherAdapter extends RecyclerView.Adapter<LichSuVoucherAdapter.LichSuVoucherViewHolder> {

    private List<Discount> discountList;

    public LichSuVoucherAdapter(List<Discount> discountList) {
        this.discountList = discountList;
    }

    @NonNull
    @Override
    public LichSuVoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lichsuvoucher, parent, false);
        return new LichSuVoucherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LichSuVoucherViewHolder holder, int position) {
        Discount discount = discountList.get(position);
        if (discount != null) {
            holder.txtDiscount.setText(String.format("%s%%", discount.getDiscount()));
            holder.tvMota.setText(discount.getMota());
        }
    }

    @Override
    public int getItemCount() {
        return discountList != null ? discountList.size() : 0;
    }

    public static class LichSuVoucherViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDiscount;
        private TextView tvMota;

        public LichSuVoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDiscount = itemView.findViewById(R.id.txtDiscount);
            tvMota = itemView.findViewById(R.id.tv_mota);
            // TextView "Đã sử dụng" có sẵn trong layout, không cần tham chiếu trong code
        }
    }
}
