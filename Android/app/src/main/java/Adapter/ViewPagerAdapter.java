package Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import Fragment.HomeFragment;
import Fragment.AccountUserFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new AccountUserFragment(); // Tab 0 - Location
            case 1:
                return new AccountUserFragment(); // Tab 1 - Shop
            case 2:
                return new HomeFragment(); // Tab 2 - Home
            case 3:
                return new AccountUserFragment(); // Tab 3 - Notice
            case 4:
                return new AccountUserFragment(); // Tab 4 - Account/User
            default:
                throw new IllegalArgumentException("Invalid position: " + position);
        }
    }

    @Override
    public int getCount() {
        return 5; // Số lượng tabs
    }
}
