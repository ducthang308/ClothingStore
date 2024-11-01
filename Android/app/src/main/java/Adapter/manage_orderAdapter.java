package Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Model.OrderManage;

public class manage_orderAdapter extends RecyclerView.Adapter<manage_orderAdapter.manage_orderViewHolder> {
    private List<OrderManage> ListOrder;

    public manage_orderAdapter(List<OrderManage> ListOrder) {
        this.ListOrder = ListOrder;
    }

    @NonNull
    @Override
    public manage_orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_manage, parent, false);
        return new manage_orderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull manage_orderViewHolder holder, int position) {
        OrderManage orderManage = ListOrder.get(position);
        if (orderManage == null) {
            return;
        }

        holder.imageView.setImageResource(orderManage.getImageUrl());
        holder.soluong.setText(String.valueOf(orderManage.getNumberOfProduct()));
        holder.tensp.setText(orderManage.getProductName());
        holder.gia.setText(String.format("₫%,.0f", orderManage.getPrice()));
        holder.id_sp.setText(String.valueOf(orderManage.getId()));
        holder.nameKH.setText(orderManage.getFullname());
        holder.total.setText(String.format("₫%,.0f", orderManage.getTotalMoney()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date dateCreate = orderManage.getDateCreate();
        if (dateCreate != null) {
            String formattedDate = dateFormat.format(dateCreate);
            holder.dateCreate.setText(formattedDate); // Sử dụng TextView để hiển thị ngày
        }

        // Xử lý trạng thái đơn hàng và gán trạng thái cho các RadioButton
        String status = orderManage.getStatus();
        if (status != null) {
            switch (status) {
                case "delivering":
                    holder.rbtn_delivering.setChecked(true);
                    holder.rbtn_delivered.setChecked(false);
                    holder.rbtn_cancel.setChecked(false);
                    break;
                case "delivered":
                    holder.rbtn_delivering.setChecked(false);
                    holder.rbtn_delivered.setChecked(true);
                    holder.rbtn_cancel.setChecked(false);
                    break;
                case "cancel":
                    holder.rbtn_delivering.setChecked(false);
                    holder.rbtn_delivered.setChecked(false);
                    holder.rbtn_cancel.setChecked(true);
                    break;
                default:
                    holder.rbtn_delivering.setChecked(false);
                    holder.rbtn_delivered.setChecked(false);
                    holder.rbtn_cancel.setChecked(false);
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (ListOrder != null) {
            return ListOrder.size();
        }
        return 0;
    }

    public class manage_orderViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tensp, soluong, gia, id_sp, nameKH, total, dateCreate; // Chuyển dateCreate thành TextView
        RadioButton rbtn_delivering, rbtn_delivered, rbtn_cancel;

        @SuppressLint("WrongViewCast")
        public manage_orderViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_order);
            tensp = itemView.findViewById(R.id.tv_tensp);
            soluong = itemView.findViewById(R.id.tv_soluong);
            gia = itemView.findViewById(R.id.tv_gia);
            id_sp = itemView.findViewById(R.id.id_sp);
            nameKH = itemView.findViewById(R.id.name_cus);
            total = itemView.findViewById(R.id.total);
            dateCreate = itemView.findViewById(R.id.date_create); // Sử dụng TextView cho dateCreate
            rbtn_cancel = itemView.findViewById(R.id.radioBtn_cancel);
            rbtn_delivering = itemView.findViewById(R.id.radioBtn_delivering);
            rbtn_delivered = itemView.findViewById(R.id.radioBtn_delivered);
        }
    }
}
