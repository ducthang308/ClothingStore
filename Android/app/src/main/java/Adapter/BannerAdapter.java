package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.duanandroid.R;

import java.util.List;

public class BannerAdapter extends PagerAdapter {
    private final Context context;
    private final List<Integer> bannerImages;

    public BannerAdapter(Context context, List<Integer> bannerImages) {
        this.context = context;
        this.bannerImages = bannerImages;
    }

    @Override
    public int getCount() {
        return bannerImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // Inflate item_banner layout and bind data
        View view = LayoutInflater.from(context).inflate(R.layout.item_banner, container, false);
        ImageView bannerImage = view.findViewById(R.id.bannerImage); // Updated to match item_banner.xml ID
        bannerImage.setImageResource(bannerImages.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
