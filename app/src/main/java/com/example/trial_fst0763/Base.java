package com.example.trial_fst0763;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.adapters.ViewPagerAdapter;
import com.example.fst_t0763.R;
import com.google.android.material.tabs.TabLayout;

public class Base extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_base, container, false);
        tabLayout=v.findViewById(R.id.base_tablayout);
        viewPager =v.findViewById(R.id.base_viewpager);

        tabLayout.setupWithViewPager(viewPager);

        ViewPagerAdapter adapter=new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFrag(new PostAPI(),"POST API");
        adapter.addFrag(new StringEncryptionsTest(),"String Encryption");

        viewPager.setAdapter(adapter);

        return v;
    }
}