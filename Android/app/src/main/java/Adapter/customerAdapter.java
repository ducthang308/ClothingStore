package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duanandroid.R;
import java.util.List;
import Model.customer;

public class customerAdapter extends RecyclerView.Adapter<customerAdapter.customerViewHolder> {
    private List<customer> customerList;

    public customerAdapter(List<customer> customerList) {
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public customerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_manager, parent, false);
        return new customerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull customerViewHolder holder, int position) {
        customer customer = customerList.get(position);
        if (customer == null) {
            return;
        }
        holder.tvNameCus.setText(customer.getFullname());
        holder.tv_sdt.setText(customer.getPhoneNumber());
        holder.btnLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnLock.setVisibility(View.GONE);
                holder.btnOpen.setVisibility(View.VISIBLE);
            }
        });

        holder.btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btnOpen.setVisibility(View.GONE);
                holder.btnLock.setVisibility(View.VISIBLE);
            }
        });
    }

        @Override
        public int getItemCount () {
            if (customerList != null) {
                return customerList.size();
            }
            return 0;
        }

        public class customerViewHolder extends RecyclerView.ViewHolder {

            public View btnLock, btnOpen;
            private TextView tvNameCus, tv_sdt;

            public customerViewHolder(@NonNull View itemView) {
                super(itemView);
                tvNameCus = itemView.findViewById(R.id.tv_nameCustomer);
                tv_sdt = itemView.findViewById(R.id.tv_phone);
                btnLock = itemView.findViewById(R.id.btnLock);
                btnOpen= itemView.findViewById(R.id.btnOpen);
            }
        }
    }

