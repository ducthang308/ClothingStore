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

import com.bumptech.glide.Glide;
import com.example.duanandroid.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import DTO.OrderDetailDTO;
import DTO.OrdersDTO;
import DTO.ProductDTO;
import DTO.ProductImageDTO;
import DTO.UsersDTO;
import Model.OrderManage;

public class manage_orderAdapter extends RecyclerView.Adapter<manage_orderAdapter.manage_orderViewHolder> {
    private List<OrdersDTO> ListOrder;
    private List<ProductImageDTO> ListProductImageDTOS;
    private List<ProductDTO> LisProductDTOS;
    private List<UsersDTO> usersDTOS;
    private List<OrderDetailDTO> orderDetailDTOS;
    public manage_orderAdapter(List<OrdersDTO> ListOrder) {
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
        OrdersDTO ordersDTO = ListOrder.get(position);
        ProductImageDTO productImageDTO = ListProductImageDTOS.get(position);
        ProductDTO productDTO = LisProductDTOS.get(position);
        OrderDetailDTO orderDetailDTO = orderDetailDTOS.get(position);
        UsersDTO usersDTO = usersDTOS.get(position);
        if (ordersDTO == null) {
            return;
        }

        // Sử dụng Glide để tải ảnh từ URL
        String imageUrl = productImageDTO.getImageUrl();
        Glide.with(holder.itemView.getContext())
                .load(imageUrl)
                .into(holder.imageView);
        holder.soluong.setText(orderDetailDTO.getNumberOfProduct());
        holder.tensp.setText(productDTO.getProductName());
        holder.gia.setText(String.format("₫%,.0f", productDTO.getPrice()));
        holder.id_sp.setText(String.valueOf(productDTO.getId()));
        holder.nameKH.setText(usersDTO.getFullName());
        holder.total.setText(String.format("₫%,.0f", orderDetailDTO.getTotalMoney()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date dateCreate = ordersDTO.getOrderDate();
        if (dateCreate != null) {
            String formattedDate = dateFormat.format(dateCreate);
            holder.dateCreate.setText(formattedDate); // Sử dụng TextView để hiển thị ngày
        }

        // Xử lý trạng thái đơn hàng và gán trạng thái cho các RadioButton
        String status = ordersDTO.getStatus();
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
