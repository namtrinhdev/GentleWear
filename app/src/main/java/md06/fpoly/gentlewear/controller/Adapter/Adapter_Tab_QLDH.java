package md06.fpoly.gentlewear.controller.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import md06.fpoly.gentlewear.views.fragments.tab_QLDH.ChoXacNhanFragment;
import md06.fpoly.gentlewear.views.fragments.tab_QLDH.DaGiaoFragment;
import md06.fpoly.gentlewear.views.fragments.tab_QLDH.DaHuyFragment;
import md06.fpoly.gentlewear.views.fragments.tab_QLDH.DaXacNhanFragment;
import md06.fpoly.gentlewear.views.fragments.tab_QLDH.DangGiaoFragment;

public class Adapter_Tab_QLDH extends FragmentStateAdapter {
    public Adapter_Tab_QLDH(@NonNull FragmentActivity fragmentActivity) {
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
                fragment = DaXacNhanFragment.newInstance();
                break;
            case 2:
                fragment = DangGiaoFragment.newInstance();
                break;
            case 3:
                fragment = DaGiaoFragment.newInstance();
                break;
            case 4:
                fragment = DaHuyFragment.newInstance();
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
