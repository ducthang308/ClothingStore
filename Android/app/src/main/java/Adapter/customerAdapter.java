package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duanandroid.R;
import java.util.List;

import Interface.APIClient;
import Interface.ApiUsers;
import Interface.PreferenceManager;
import Model.User;
import Model.customer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class customerAdapter extends RecyclerView.Adapter<customerAdapter.customerViewHolder> {
    private List<User> customerList;
    private Context context;
    private String token;  // Token người dùng để gửi API request

    public customerAdapter(List<User> customerList, Context context, String token) {
        this.customerList = customerList;
        this.context = context;
        this.token = token;
    }

    @NonNull
    @Override
    public customerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_customer_manager, parent, false);
        return new customerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull customerViewHolder holder, int position) {
        User customer = customerList.get(position);
        if (customer == null) {
            return;
        }

        holder.tvNameCus.setText(customer.getFullname());
        holder.tv_sdt.setText(customer.getPhoneNumber());

        if ("Open".equals(customer.getActive())) {
            holder.btnLock.setVisibility(View.VISIBLE);
            holder.btnOpen.setVisibility(View.GONE);
        } else if ("Lock".equals(customer.getActive())) {
            holder.btnLock.setVisibility(View.GONE);
            holder.btnOpen.setVisibility(View.VISIBLE);
        }

        // Khi nhấn btnLock, cập nhật trạng thái và gọi API
        holder.btnLock.setOnClickListener(v -> {
            customer.setActive("Lock");
            updateCustomerStatus(customer.getId(), "Lock", holder, position);
        });

        // Khi nhấn btnOpen, cập nhật trạng thái và gọi API
        holder.btnOpen.setOnClickListener(v -> {
            customer.setActive("Open");
            updateCustomerStatus(customer.getId(), "Open", holder, position);
        });
    }

    @Override
    public int getItemCount() {
        return customerList != null ? customerList.size() : 0;
    }

    public void updateData(List<User> newCustomers) {
        this.customerList = newCustomers;
        notifyDataSetChanged();
    }

    private void updateCustomerStatus(int userId, String status, customerViewHolder holder, int position) {

        ApiUsers apiUsers = APIClient.getClient().create(ApiUsers.class);
        PreferenceManager preferenceManager = new PreferenceManager(context);
        token = preferenceManager.getToken();

        // Gửi request với token và userId
        Call<String> call = apiUsers.updateActive("Bearer " + token, userId, new User(status));

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    // Cập nhật thành công
                    Toast.makeText(context, "Status updated to: " + status, Toast.LENGTH_SHORT).show();
                    // Cập nhật UI nếu thành công
                    if ("Lock".equals(status)) {
                        holder.btnLock.setVisibility(View.GONE);
                        holder.btnOpen.setVisibility(View.VISIBLE);
                    } else {
                        holder.btnLock.setVisibility(View.VISIBLE);
                        holder.btnOpen.setVisibility(View.GONE);
                    }
                } else {
                    // Xử lý khi API trả về lỗi
                    Toast.makeText(context, "Failed to update status", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Xử lý khi không kết nối được với API
                Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static class customerViewHolder extends RecyclerView.ViewHolder {
        public View btnLock, btnOpen;
        private TextView tvNameCus, tv_sdt;

        public customerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCus = itemView.findViewById(R.id.tv_nameCustomer);
            tv_sdt = itemView.findViewById(R.id.tv_phone);
            btnLock = itemView.findViewById(R.id.btnLock);
            btnOpen = itemView.findViewById(R.id.btnOpen);
        }
    }
}
