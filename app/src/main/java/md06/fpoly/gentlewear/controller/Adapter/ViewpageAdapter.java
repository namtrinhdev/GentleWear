package md06.fpoly.gentlewear.controller.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import md06.fpoly.gentlewear.views.fragments.CartFragment;
import md06.fpoly.gentlewear.views.fragments.HomeFragment;
import md06.fpoly.gentlewear.views.fragments.ProfileFragment;
import md06.fpoly.gentlewear.views.fragments.WishlistFragment;

public class ViewpageAdapter extends FragmentStateAdapter {


    public ViewpageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();

            case 1:
                return new CartFragment();

            case 2:
                return new WishlistFragment();

            case 3:
                return new ProfileFragment();

            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        // Return the total number of fragments
        return 4; // Assuming you have 4 fragments
    }
}
