package Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duanandroid.R;
import java.util.List;
import Model.Category;

public class danhMucAdapter extends RecyclerView.Adapter<danhMucAdapter.DanhMucViewHolder> {
    private List<Category> categoryList;

    public danhMucAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public DanhMucViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danhmuc_activity, parent, false);
        return new DanhMucViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DanhMucViewHolder holder, int position) {
        Category category = categoryList.get(position);
        if (category == null) {
            return;
        }

        holder.tvCategoryName.setText(category.getCategoryName());


        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Xử lý logic khi CheckBox được chọn hoặc bỏ chọn
            // Ví dụ: cập nhật trạng thái của danh mục
        });
    }

    @Override
    public int getItemCount() {
        if (categoryList != null) {
            return categoryList.size();
        }
        return 0;
    }

    public static class DanhMucViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCategoryName;
        private CheckBox checkBox;

        public DanhMucViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.txtCategoryName);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
