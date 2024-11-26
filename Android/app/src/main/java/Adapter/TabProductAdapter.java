package Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import Fragment.ViewProductListFragment;

public class TabProductAdapter extends FragmentPagerAdapter {

    public TabProductAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return ViewProductListFragment.newInstance(0);
            case 1:
                return ViewProductListFragment.newInstance(1);
            case 2:
                return ViewProductListFragment.newInstance(2);
            default:
                return ViewProductListFragment.newInstance(0);
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
                return "Sản phẩm bán chạy";
            case 1:
                return "Sản phẩm mới";
            case 2:
                return "Thời trang xu hướng";
            default:
                return "Sản phẩm bán chạy";
        }
    }
}