package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.duanandroid.R;
import com.example.duanandroid.View.CartActivity;
import com.example.duanandroid.View.chatUserActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.Arrays;
import java.util.List;

import Adapter.BannerAdapter;
import Adapter.TabProductAdapter;

public class HomeFragment extends Fragment {

    private ViewPager viewPager, bannerViewPager;
    private TabLayout tabLayout, bannerIndicator;
    private ImageView chat, shoppingCart;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int currentPage = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        bannerViewPager = view.findViewById(R.id.banner_viewpager);
        bannerIndicator = view.findViewById(R.id.bannerIndicator);

        List<Integer> bannerImages = Arrays.asList(
                R.drawable.banner_hoi_trang_nu_dep,
                R.drawable.anh_banner1,
                R.drawable.anh_banner2,
                R.drawable.banner_hoi_trang_nu_dep
        );


        BannerAdapter bannerAdapter = new BannerAdapter(requireContext(), bannerImages);
        bannerViewPager.setAdapter(bannerAdapter);
        bannerIndicator.setupWithViewPager(bannerViewPager, true);

        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        TabProductAdapter adapter = new TabProductAdapter(getChildFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        chat = view.findViewById(R.id.chat);
        shoppingCart = view.findViewById(R.id.shopping_cart);

        chat.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), chatUserActivity.class);
            startActivity(intent);
        });

        shoppingCart.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), CartActivity.class);
            intent.putExtra("origin", "CartToHome");
            startActivity(intent);
        });


        runnable = new Runnable() {
            @Override
            public void run() {

                if (currentPage >= bannerImages.size()) {
                    currentPage = 0;
                }
                bannerViewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(runnable, 3000);

        bannerViewPager.setOffscreenPageLimit(3);

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.removeCallbacks(runnable);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 5000);
    }

}
