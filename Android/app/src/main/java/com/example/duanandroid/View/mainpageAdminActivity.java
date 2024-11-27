package com.example.duanandroid.View;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.duanandroid.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Adapter.BannerAdapter;
import Adapter.ProductAdapter;
import Model.Product;
import Model.ProductImage;

public class mainpageAdminActivity extends AppCompatActivity {
    private RecyclerView productRecyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    private List<ProductImage> productImageList;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int currentPage = 0;
    private ViewPager bannerViewPager;
    private TabLayout bannerIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mainpageadmin);

        productRecyclerView = findViewById(R.id.items);
        productRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        productRecyclerView.addItemDecoration(new ItemDecoration(2, 24, true)); // 2 cột, khoảng cách 24dp
        productList = loadDataFromLayout();
        productImageList = loadProductImages();

        productAdapter = new ProductAdapter(productList, productImageList,true);
        productRecyclerView.setAdapter(productAdapter);

        bannerViewPager = findViewById(R.id.banner_viewpager);
        bannerIndicator = findViewById(R.id.bannerIndicator);


        List<Integer> bannerImages = Arrays.asList(
                R.drawable.banner_hoi_trang_nu_dep,
                R.drawable.anh_banner1,
                R.drawable.anh_banner2,
                R.drawable.banner_hoi_trang_nu_dep
        );

        BannerAdapter bannerAdapter = new BannerAdapter(this, bannerImages);
        bannerViewPager.setAdapter(bannerAdapter);

        bannerIndicator.setupWithViewPager(bannerViewPager, true);

        startBannerAutoScroll(bannerImages.size());

        LinearLayout nav_home = findViewById(R.id.nav_home);
        nav_home.setBackgroundColor(getResources().getColor(R.color.colorgray));
        LinearLayout btn = findViewById(R.id.nav_account);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainpageAdminActivity.this, adminAcountActivity.class);
                startActivity(intent);
            }
        });

        ImageView imv=  findViewById(R.id.chat);
        imv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainpageAdminActivity.this, ChatAdminActivity.class);
                intent.putExtra("previousActivity", "mainpageAdminActivity");
                startActivity(intent);
            }
        });
    }

    private List<Product> loadDataFromLayout() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            products.add(new Product("Tên sản phẩm " + (i + 1), "M", (i + 1) * 100000));
        }
        return products;
    }

    private List<ProductImage> loadProductImages() {
        List<ProductImage> productImages = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            String imageName = "ao";
            int imageResId = getResources().getIdentifier(imageName, "drawable", getPackageName());
            productImages.add(new ProductImage(i, i, String.valueOf(imageResId)));
        }
        return productImages;
    }

    private void startBannerAutoScroll(final int totalBanners) {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentPage == totalBanners) {
                    currentPage = 0;
                }
                bannerViewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(runnable, 3000);
            }
        };
        handler.post(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
