package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;

import java.util.List;

public class PaymentMethodAdapter extends RecyclerView.Adapter<PaymentMethodAdapter.ViewHolder> {

    private List<String> paymentMethods;
    private OnItemClickListener onItemClickListener;

    public PaymentMethodAdapter(List<String> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_method, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String method = paymentMethods.get(position);
        holder.paymentMethodName.setText(method);
    }

    @Override
    public int getItemCount() {
        return paymentMethods.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView paymentMethodName;

        public ViewHolder(View itemView) {
            super(itemView);
            paymentMethodName = itemView.findViewById(R.id.payment_method_name);

            // Xử lý sự kiện chọn item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(paymentMethods.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    // Interface để xử lý sự kiện chọn phương thức thanh toán
    public interface OnItemClickListener {
        void onItemClick(String method);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
