package md06.fpoly.gentlewear.views.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import md06.fpoly.gentlewear.R;
import md06.fpoly.gentlewear.activitys.SearchActivity;
import md06.fpoly.gentlewear.controller.Adapter.TabAdapter;


public class HomeFragment extends Fragment {

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    ImageView img_search;
    TabAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager2 = view.findViewById(R.id.tab_page);
        tabLayout = view.findViewById(R.id.tab_layout);
        img_search = view.findViewById(R.id.btnSearch);
        adapter = new TabAdapter(this);
        viewPager2.setAdapter(adapter);

        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabLayout.setTabTextColors(getResources().getColor(R.color.tab_unselected_text), getResources().getColor(R.color.tab_selected_text));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tabLayout.setTabTextColors(getResources().getColor(R.color.tab_selected_text), getResources().getColor(R.color.tab_unselected_text));

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        TabLayoutMediator mediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Popular");
                        break;
                    case 1:
                        tab.setText("Trend");
                        break;
                    case 2:
                        tab.setText("News");
                        break;
                    case 3:
                        tab.setText("Sale");
                        break;
                }
            }
        });
        mediator.attach();
    }
}