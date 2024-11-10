package Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
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
import Model.Category;
import Model.Discount;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class danhMucAdapter extends RecyclerView.Adapter<danhMucAdapter.CategoryViewHolder> {
    private List<Category> caterogList;
    private Context context;
    private OnCategorySelectedListener listener;
    private int selectedPosition = -1;

    public danhMucAdapter(List<Category> caterogList, Context context,OnCategorySelectedListener listener) {
        this.caterogList = caterogList;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_danhmuc_activity, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category = caterogList.get(position);
        holder.txtCategoryName.setText(category.getCategoryName());

        holder.checkBox.setOnClickListener(v -> {
            if (selectedPosition == position) {
                selectedPosition = -1;
                listener.OnCategorySelected(null);
            } else {
                selectedPosition = position;
                listener.OnCategorySelected(category);
            }
            notifyDataSetChanged();
        });

        holder.checkBox.setChecked(selectedPosition == position); // Cập nhật trạng thái checkbox
    }


    @Override
    public int getItemCount() {
        return caterogList.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategoryName;
        CheckBox checkBox;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.tv_name1);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
    public interface OnCategorySelectedListener {
        void OnCategorySelected(Category categoriesDTO);
    }
}
