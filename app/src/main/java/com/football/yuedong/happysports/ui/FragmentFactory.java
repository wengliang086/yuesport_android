package com.football.yuedong.happysports.ui;


import android.support.v4.app.Fragment;

import com.football.yuedong.happysports.fragment.MatchesFragment_;
import com.football.yuedong.happysports.fragment.PitchesFragment_;
import com.football.yuedong.happysports.fragment.ProfileFragment_;
import com.football.yuedong.happysports.fragment.RankingsFatherFragment;
import com.football.yuedong.happysports.fragment.SummaryFragment;
import com.football.yuedong.happysports.fragment.TeamsFragment_;

/**
 * Created by liuyanjun on 2015/12/25.
 */
public class FragmentFactory {

    public static Fragment getInstanceByIndex(int index) {
        Fragment fragment = null;
        switch (index) {
            case 0:
                fragment = new PitchesFragment_();
                break;
            case 1:
                fragment = new TeamsFragment_();
                break;
            case 2:
                fragment = new MatchesFragment_();
//                fragment = new RankingsFatherFragment();
//                fragment = new SummaryFragment();
                break;
            case 3:
                fragment = new ProfileFragment_();
                break;
        }
        return fragment;
    }
}
