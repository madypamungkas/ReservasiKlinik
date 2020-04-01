package id.technow.reservasiklinik.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import id.technow.reservasiklinik.R;

public class HistoryFragment extends Fragment {
    public HistoryFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    ViewPager viewPager;
   // ViewPagerAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_history, container, false);
        /*TabLayout tabLayout = fragmentView.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) fragmentView.findViewById(R.id.pager);
        tabLayout.setupWithViewPager(viewPager);
        adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.AddFragment(new HistorySelfFragment(), "Diri Sendiri");
        adapter.AddFragment(new HistoryOtherFragment(), "Orang Lain");
        viewPager.setAdapter(adapter);*/
        return fragmentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        /*adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.AddFragment(new HistorySelfFragment(), "Diri Sendiri");
        adapter.AddFragment(new HistoryOtherFragment(), "Orang Lain");
        viewPager.setAdapter(adapter);*/
    }
}

