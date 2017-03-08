package com.football.yuedong.happysports.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.widget.viewimage.Indicators.RankingPagerIndicator;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.github.ksoichiro.android.observablescrollview.Scrollable;
import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2016/4/12.
 */
public class RankingsFatherFragment extends FlexibleSpaceWithImageBaseFragment<ObservableRecyclerView> {

    private ViewPager mViewPager;
    private RankingPagerIndicator mIndicator;
    private List<String> mTitles = Arrays.asList("影响力", "活跃度", "战绩榜");
    private List<RankingsFragment> mContents = new ArrayList<RankingsFragment>();
    private PagerAdapter mAdapter;
    private View mBaseView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBaseView = inflater.inflate(R.layout.layout_scroll_rank, container, false);

        final ObservableScrollView scrollView = (ObservableScrollView) mBaseView.findViewById(R.id.scroll);
        scrollView.setTouchInterceptionViewGroup((ViewGroup) mBaseView.findViewById(R.id.fragment_root));

        // Scroll to the specified offset after layout
        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_SCROLL_Y)) {
            final int scrollY = args.getInt(ARG_SCROLL_Y, 0);
            ScrollUtils.addOnGlobalLayoutListener(scrollView, new Runnable() {
                @Override
                public void run() {
                    scrollView.scrollTo(0, scrollY);
                }
            });
            updateFlexibleSpace(scrollY, mBaseView);
        } else {
            updateFlexibleSpace(0, mBaseView);
        }

        scrollView.setScrollViewCallbacks(this);

        initViews();
        initDatas();

        mViewPager.setAdapter(mAdapter);
        mIndicator.setViewPager(mViewPager, 0);
        return mBaseView;
    }

    private void initDatas() {
        mIndicator.setTitles(mTitles);
        for (String title : mTitles) {
            RankingsFragment fragment = RankingsFragment.newInstance(title);
            mContents.add(fragment);
        }
        mAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mContents.size();
            }

            @Override
            public Fragment getItem(int location) {
                return mContents.get(location);
            }
        };
    }

    private void initViews() {
        mIndicator = (RankingPagerIndicator) mBaseView.findViewById(R.id.id_indicator);
        mViewPager = (ViewPager) mBaseView.findViewById(R.id.id_viewpager);
    }

    @Override
    protected void updateFlexibleSpace(int scrollY, View view) {
        TeamsFragment parentActivity =
                (TeamsFragment) getParentFragment();
        ObservableScrollView scrollView = (ObservableScrollView) view.findViewById(R.id.scroll);

        // Also pass this event to parent Activity
        if (parentActivity != null) {
            parentActivity.onScrollChanged(scrollY, scrollView);
        }
    }


    @Override
    protected void updateFlexibleSpace(int scrollY) {
        // Sometimes scrollable.getCurrentScrollY() and the real scrollY has different values.
        // As a workaround, we should call scrollVerticallyTo() to make sure that they match.
        Scrollable s = getScrollable();
        s.scrollVerticallyTo(scrollY);

        // If scrollable.getCurrentScrollY() and the real scrollY has the same values,
        // calling scrollVerticallyTo() won't invoke scroll (or onScrollChanged()), so we call it here.
        // Calling this twice is not a problem as long as updateFlexibleSpace(int, View) has idempotence.
        updateFlexibleSpace(scrollY, getView());
    }


}
