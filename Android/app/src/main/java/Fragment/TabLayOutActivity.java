package Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.duanandroid.R;
import com.google.android.material.tabs.TabLayout;

public class TabLayOutActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_lay_out);

        // Ánh xạ ViewPager và TabLayout
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        // Tạo và thiết lập adapter cho ViewPager
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new WaitingForPaymentFragment();
                    case 1:
                        return new WaitingForShipingFragment();
                    case 2:
                        return new WaitingForDeliveryFragment();
                    case 3:
                        return new ReviewFragment();
                    case 4:
                        return new ReturnAndCancelFragment();
                    default:
                        return new WaitingForPaymentFragment();
                }
            }

            @Override
            public int getCount() {
                // Số lượng tab
                return 5;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                // Thiết lập tiêu đề cho từng tab
                switch (position) {
                    case 0:
                        return "Chờ thanh toán";
                    case 1:
                        return "Chờ vận chuyển";
                    case 2:
                        return "Chờ giao hàng";
                    case 3:
                        return "Đánh giá";
                    case 4:
                        return "Hủy/ Trả hàng";
                    default:
                        return "Chờ thanh toán";
                }
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        int tabPosition = getIntent().getIntExtra("tabPosition", 0); // Mặc định là tab 0 nếu không có dữ liệu
        viewPager.setCurrentItem(tabPosition);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        ImageView btn_back = findViewById(R.id.back_arrow_account);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TabLayOutActivity.this,AccountUserFragment.class);
//                startActivity(intent);
                finish();
            }
        });



    }
}
