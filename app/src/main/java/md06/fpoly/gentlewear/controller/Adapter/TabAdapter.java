package md06.fpoly.gentlewear.controller.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import md06.fpoly.gentlewear.views.fragments.tabFragment.NewsFragment;
import md06.fpoly.gentlewear.views.fragments.tabFragment.PopularFragment;
import md06.fpoly.gentlewear.views.fragments.tabFragment.SaleFragment;
import md06.fpoly.gentlewear.views.fragments.tabFragment.TrendingFragment;

public class TabAdapter extends FragmentStateAdapter {
    int tabCount = 4;

    NewsFragment newsFragment;
    PopularFragment popularFragment;
    SaleFragment saleFragment;
    TrendingFragment trendingFragment;

    public TabAdapter(@NonNull Fragment fragment) {
        super(fragment);
        newsFragment = new NewsFragment();
        popularFragment = new PopularFragment();
        saleFragment = new SaleFragment();
        trendingFragment = new TrendingFragment();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return popularFragment;
            case 1:
                return trendingFragment;
            case 2:
                return newsFragment;
            case 3:
                return saleFragment;
            default:
                return popularFragment;
        }
    }

    @Override
    public int getItemCount() {
        return tabCount;
    }
}
