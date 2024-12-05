package Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.duanandroid.R;
import com.example.duanandroid.View.adminAcountActivity;
import com.google.android.material.tabs.TabLayout;

public class TabLayoutAdminActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_admin);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new WaitingForShippingAdminFragment();
                    case 1:
                        return new WaitingForDeliveryAdminFragment();
                    case 2:
                        return new CancelOrderAdminFragment();
                    default:
                        return new WaitingForShippingAdminFragment();
                }
            }

            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "Chờ vận chuyển";
                    case 1:
                        return "Đã nhận hàng";
                    case 2:
                        return "Hủy/ Trả hàng";
                    default:
                        return "Chờ vận chuyển";
                }
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        int tabPosition = getIntent().getIntExtra("tabPosition", 0);
        if (tabPosition < 0 || tabPosition >= viewPager.getAdapter().getCount()) {
            tabPosition = 0;
        }
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
                Intent intent = new Intent(TabLayoutAdminActivity.this, adminAcountActivity.class);
                startActivity(intent);
            }
        });
    }
}