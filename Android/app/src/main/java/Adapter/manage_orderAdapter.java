package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Model.Order;

public class manage_orderAdapter extends RecyclerView.Adapter<manage_orderAdapter.ManageOrderViewHolder> {
    private Context context;
    private List<Order> ordersList;
    private OnItemClickListener listener;

    public manage_orderAdapter(Context context, List<Order> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }

    public interface OnItemClickListener {
        void onItemClick(Order order);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ManageOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_manage, parent, false);
        return new ManageOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageOrderViewHolder holder, int position) {
        Order order = ordersList.get(position);

        holder.orderId.setText(String.valueOf(order.getId()));
        holder.customerName.setText(String.valueOf(order.getUserId()));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        holder.Orderdate.setText(dateFormat.format(order.getOrderDate()));
        float total = order.getTotal_money();
        holder.tvtotal.setText(String.format("%.2f", total));
        holder.tvdiscount.setText(String.valueOf(order.getOrderDate()));
        // Đặt trạng thái RadioButton
        holder.rbDelivering.setChecked(order.getStatus().equals("Đang giao"));
        holder.rbDelivered.setChecked(order.getStatus().equals("Đã giao"));
        holder.rbCancel.setChecked(order.getStatus().equals("Hủy"));

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(order);
            }
        });
    }


    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public static class ManageOrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderId, customerName, tvtotal,Orderdate,tvdiscount;
        RadioButton rbDelivering,rbDelivered,rbCancel;

        public ManageOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.id_sp);
            customerName = itemView.findViewById(R.id.name_cus);
            tvtotal = itemView.findViewById(R.id.total);
            Orderdate = itemView.findViewById(R.id.date_create);
            tvdiscount = itemView.findViewById(R.id.tvdiscount);
            rbDelivering = itemView.findViewById(R.id.radioBtn_delivering);
            rbDelivered = itemView.findViewById(R.id.radioBtn_delivered);
            rbCancel = itemView.findViewById(R.id.radioBtn_cancel);

        }
    }
}