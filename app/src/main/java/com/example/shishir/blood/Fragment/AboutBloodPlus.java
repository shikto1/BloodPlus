package com.example.shishir.blood.Fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shishir.blood.Adapter.ImageAdapter;
import com.example.shishir.blood.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;


public class AboutBloodPlus extends Fragment {

    private static ViewPager mPager;
    private static int currentPage = 0;
    private final int DELAY = 3000;
    private final int INTERVAL = 3000;
    private static final Integer[] XMEN = {R.drawable.cover_photo, R.drawable.donor};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_about_blood_plus, container, false);
        init(v);
        return v;
    }

    private void init(View v) {
        for (int i = 0; i < XMEN.length; i++)
            XMENArray.add(XMEN[i]);

        mPager = (ViewPager) v.findViewById(R.id.pager);
        mPager.setAdapter(new ImageAdapter(getActivity(), XMENArray));
        CircleIndicator indicator = (CircleIndicator) v.findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY, INTERVAL);
    }

}
