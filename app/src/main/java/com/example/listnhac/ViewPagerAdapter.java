package com.example.listnhac;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new PlaylistFragment();
        } else if (position == 1) {
            return new SavedListFragment();
        } else {
            return new PlaylistFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
