package md06.fpoly.gentlewear.controller.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import md06.fpoly.gentlewear.views.fragments.ChoLayHangFragment;
import md06.fpoly.gentlewear.views.fragments.ChoXacNhanFragment;
import md06.fpoly.gentlewear.views.fragments.DaGiaoHangFragment;
import md06.fpoly.gentlewear.views.fragments.DaHuyDonHangFragment;
import md06.fpoly.gentlewear.views.fragments.DangGiaoHangFragment;


public class AdapterQLDH extends FragmentStateAdapter {
    public AdapterQLDH(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = ChoXacNhanFragment.newInstance();
                break;
            case 1:
                fragment = ChoLayHangFragment.newInstance();
                break;
            case 2:
                fragment = DangGiaoHangFragment.newInstance();
                break;
            case 3:
                fragment = DaGiaoHangFragment.newInstance();
                break;
            case 4:
                fragment = DaHuyDonHangFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
