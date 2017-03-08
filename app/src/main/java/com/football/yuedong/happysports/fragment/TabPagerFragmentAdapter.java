package com.football.yuedong.happysports.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.ui.FragmentFactory;
import com.shizhefei.view.indicator.IndicatorViewPager;


/**
 * Created by liuyanjun on 2016/2/22.
 */
public class TabPagerFragmentAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
    private String[] tabNames = { "球场", "球队", "联赛", "设置" };
    private int[] tabIcons = { R.drawable.tab_pitches_selector, R.drawable.tab_teams_selector, R.drawable.tab_matches_selector,
            R.drawable.tab_profile_selector };

    private LayoutInflater inflater;


    public TabPagerFragmentAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tabNames.length;
    }

    @Override
    public View getViewForTab(int position, View convertView, ViewGroup container) {
        if (convertView == null) {
            convertView = (TextView) inflater.inflate(R.layout.tab_main, container, false);
        }
        TextView textView = (TextView) convertView;
        textView.setText(tabNames[position]);
        textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[position], 0, 0);
        return textView;
    }

    @Override
    public Fragment getFragmentForPage(int i) {
        return FragmentFactory.getInstanceByIndex(i);
    }
}
