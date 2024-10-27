package com.example.duanandroid.View;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.duanandroid.R;
import com.example.duanandroid.databinding.ActivityPagemainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import Adapter.ViewPagerAdapter;


public class mainpageActivity extends AppCompatActivity {
    private ActivityPagemainBinding binding;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPagemainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        {
            // Ánh xạ ViewPager và BottomNavigationView từ layout
            viewPager = binding.viewPager;
            bottomNavigationView = binding.bottomNavigationView;

            // Thiết lập adapter cho ViewPager
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            viewPager.setAdapter(adapter);


            int tabPosition = 2;  // Mặc định là tab Home (2)
            if (getIntent() != null) {
                tabPosition = getIntent().getIntExtra("tabPosition", 2);
            }
            viewPager.setCurrentItem(tabPosition);

            viewPager.setCurrentItem(2);

            bottomNavigationView.setSelectedItemId(R.id.menu_home);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    switch (position) {
                        case 0:
                            bottomNavigationView.setSelectedItemId(R.id.menu_location);
                            break;
                        case 1:
                            bottomNavigationView.setSelectedItemId(R.id.menu_shop);
                            break;
                        case 2:
                            bottomNavigationView.setSelectedItemId(R.id.menu_home);
                            break;
                        case 3:
                            bottomNavigationView.setSelectedItemId(R.id.menu_notice);
                            break;
                        case 4:
                            bottomNavigationView.setSelectedItemId(R.id.menu_acount);  // Đã sửa thành "menu_account"
                            break;
                    }
                }


                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();

                    if (itemId == R.id.menu_location) {
                        viewPager.setCurrentItem(0);
                    } else if (itemId == R.id.menu_shop) {
                        viewPager.setCurrentItem(1);
                    } else if (itemId == R.id.menu_home) {
                        viewPager.setCurrentItem(2);
                    } else if (itemId == R.id.menu_notice) {
                        viewPager.setCurrentItem(3);
                    } else if (itemId == R.id.menu_acount) {  // Sửa thành "menu_account"
                        viewPager.setCurrentItem(4);
                    }

                    return true;  // Trả về true để cho phép lựa chọn
                }
            });


        }
    }
}
