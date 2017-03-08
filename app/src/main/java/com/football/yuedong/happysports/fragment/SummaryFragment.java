package com.football.yuedong.happysports.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.adapter.PlayersAdapter;
import com.football.yuedong.happysports.domain.Team;
import com.football.yuedong.happysports.domain.TeamMemberRelp;
import com.football.yuedong.happysports.service.SportsInterface;
import com.football.yuedong.happysports.service.SportsService;
import com.football.yuedong.happysports.user.UserConfig;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.github.ksoichiro.android.observablescrollview.Scrollable;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/17 0017.
 */
public class SummaryFragment extends FlexibleSpaceWithImageBaseFragment<ObservableScrollView> {

    private View baseView;
    private List<TeamMemberRelp.ValueEntity> mDatas = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ObservableScrollView scrollView;
    private PlayersAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        baseView = inflater.inflate(R.layout.fragment_flexiblespace_summary, container, false);

        scrollView = (ObservableScrollView) baseView.findViewById(R.id.scroll);
        // TouchInterceptionViewGroup should be a parent view other than ViewPager.
        // This is a workaround for the issue #117:
        // https://github.com/ksoichiro/Android-ObservableScrollView/issues/117
        scrollView.setTouchInterceptionViewGroup((ViewGroup) baseView.findViewById(R.id.fragment_root));

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
            updateFlexibleSpace(scrollY, baseView);
        } else {
            updateFlexibleSpace(0, baseView);
        }
        scrollView.setScrollViewCallbacks(this);

        mRecyclerView = (RecyclerView) baseView.findViewById(R.id.id_recyclerview);

        initData();
        mAdapter = new PlayersAdapter(getActivity(), mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        return baseView;
    }

    protected void initData() {
        SportsInterface sportsInterface = SportsService.createSportsService(UserConfig.ACCESS_TOKEN);
        sportsInterface.getTeamDetails("9", "1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Team>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Team team) {
                        if (team.isSuccess()) {
                            ((TextView) scrollView.findViewById(R.id.location)).setText(team.getValue().getLocation());
                            ((TextView) scrollView.findViewById(R.id.home)).setText(team.getValue().getHomeCourt());
                        }
                    }
                });
        // 成员列表数据
        sportsInterface.getTeamRoles("10", "0", 0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TeamMemberRelp>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(TeamMemberRelp teamMemberRelp) {
                        if (teamMemberRelp.isSuccess()) {
                            mDatas = teamMemberRelp.getValue();
                            mAdapter.updateDatas(mDatas);
                        }
                    }
                });
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
}
