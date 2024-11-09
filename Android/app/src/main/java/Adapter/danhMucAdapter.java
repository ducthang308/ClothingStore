package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duanandroid.R;
import com.example.duanandroid.View.addDanhmucActivity;

import java.util.List;

import DTO.CategoriesDTO;
import Interface.APIClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class danhMucAdapter extends RecyclerView.Adapter<danhMucAdapter.CategoryViewHolder> {
    private List<CategoriesDTO> caterogyDTOList;
    private Context context;

    public danhMucAdapter(List<CategoriesDTO> caterogyDTOList, Context context) {
        this.caterogyDTOList = caterogyDTOList;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danhmuc_activity, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoriesDTO caterogyDTO = caterogyDTOList.get(position);
        // Kiểm tra null cho tên danh mục trước khi set
        if (caterogyDTO.getName() != null) {
            holder.txtCategoryName.setText(caterogyDTO.getName());
        } else {
            holder.txtCategoryName.setText("Tên danh mục không có sẵn");
        }

        // Lấy token từ SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);

        // Sự kiện nút chỉnh sửa
        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, addDanhmucActivity.class);
            intent.putExtra("category_id", caterogyDTO.getId());
            intent.putExtra("category_name", caterogyDTO.getName());
            context.startActivity(intent);
        });

        // Sự kiện nút xóa
        holder.btnDelete.setOnClickListener(v -> {
            if (token != null) { // Kiểm tra token trước khi gọi API
                APIClient.getCaterogyService(context.getApplicationContext())
                        .deleteCategory("Bearer " + token, caterogyDTO.getId())
                        .enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    caterogyDTOList.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, caterogyDTOList.size());
                                    Toast.makeText(context, "Danh mục đã được xóa", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Lỗi khi xóa danh mục", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                Toast.makeText(context, "Lỗi mạng khi xóa danh mục", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                Toast.makeText(context, "Token không hợp lệ. Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return caterogyDTOList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategoryName;
        Button btnEdit, btnDelete;
        CheckBox checkBox;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
