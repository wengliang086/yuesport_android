package com.football.yuedong.happysports.fragment;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.domain.BaseResponse;
import com.football.yuedong.happysports.service.SportsInterface;
import com.football.yuedong.happysports.service.SportsService;
import com.football.yuedong.happysports.user.UserConfig;
import com.football.yuedong.happysports.utils.AndroidUtilities;
import com.football.yuedong.happysports.views.SlidingTabLayout;
import com.github.ksoichiro.android.observablescrollview.CacheFragmentStatePagerAdapter;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.github.ksoichiro.android.observablescrollview.Scrollable;
import com.google.gson.Gson;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liuyanjun on 2015/12/15.
 */
@EFragment(R.layout.activity_flexiblespacewithimagewithviewpagertab)
public class TeamsFragment extends BaseFragment {

    protected static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    private ViewPager mPager;
    private NavigationAdapter mPagerAdapter;
    private SlidingTabLayout mSlidingTabLayout;
    private int mFlexibleSpaceHeight;
    private int mTabHeight;


    @AfterViews
    public void initView(){
            mPagerAdapter = new NavigationAdapter(getChildFragmentManager());
            mPager = (ViewPager) getView().findViewById(R.id.pager);
            mPager.setAdapter(mPagerAdapter);
            mFlexibleSpaceHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
            mTabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);

            TextView titleView = (TextView) getView().findViewById(R.id.title);
            titleView.setMinHeight(AndroidUtilities.statusBarHeight);

            mSlidingTabLayout = (SlidingTabLayout) getView().findViewById(R.id.sliding_tabs);
            mSlidingTabLayout.setCustomTabView(R.layout.tab_indicator, android.R.id.text1);
            mSlidingTabLayout.setSelectedIndicatorColors(getResources().getColor(R.color.accent));
            mSlidingTabLayout.setDistributeEvenly(true);
            mSlidingTabLayout.setViewPager(mPager);

            // Initialize the first Fragment's state when layout is completed.
            ScrollUtils.addOnGlobalLayoutListener(mSlidingTabLayout, new Runnable() {
                @Override
                public void run() {
                    translateTab(0, true);
                }
            });
        // 签到、点赞
        getView().findViewById(R.id.id_sign).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SportsInterface sportsInterface = SportsService.createSportsService(UserConfig.ACCESS_TOKEN);
                Observable<BaseResponse> observable = sportsInterface.signTeam(0, "9", "0");
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<BaseResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "onError", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(BaseResponse baseResponse) {
                        Toast.makeText(getActivity(), "onNext：" + baseResponse.getCode(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    public void onScrollChanged(int scrollY, Scrollable s) {
        FlexibleSpaceWithImageBaseFragment fragment =
                (FlexibleSpaceWithImageBaseFragment) mPagerAdapter.getItemAt(mPager.getCurrentItem());
        if (fragment == null) {
            return;
        }
        View view = fragment.getView();
        if (view == null) {
            return;
        }
        Scrollable scrollable = (Scrollable) view.findViewById(R.id.scroll);
        if (scrollable == null) {
            return;
        }
        if (scrollable == s) {
            // This method is called by not only the current fragment but also other fragments
            // when their scrollY is changed.
            // So we need to check the caller(S) is the current fragment.
            int adjustedScrollY = Math.min(scrollY, mFlexibleSpaceHeight - mTabHeight);
            translateTab(adjustedScrollY, false);
            propagateScroll(adjustedScrollY);
        }
    }

    private void translateTab(int scrollY, boolean animated) {
        int flexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
        int tabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
        View imageView = getView().findViewById(R.id.image);
        View overlayView = getView().findViewById(R.id.overlay);
        TextView titleView = (TextView) getView().findViewById(R.id.title);

        // Translate overlay and image
        float flexibleRange = flexibleSpaceImageHeight - getActionBarSize();
        int minOverlayTransitionY = tabHeight - overlayView.getHeight();
        ViewHelper.setTranslationY(overlayView, ScrollUtils.getFloat(-scrollY, minOverlayTransitionY, 0));
        ViewHelper.setTranslationY(imageView, ScrollUtils.getFloat(-scrollY / 2, minOverlayTransitionY, 0));

        // Change alpha of overlay
        ViewHelper.setAlpha(overlayView, ScrollUtils.getFloat((float) scrollY / flexibleRange, 0, 1));

        // Scale title text
        float scale = 1 + ScrollUtils.getFloat((flexibleRange - scrollY - tabHeight) / flexibleRange, 0, MAX_TEXT_SCALE_DELTA);
        setPivotXToTitle(titleView);
        ViewHelper.setPivotY(titleView, 0);
        ViewHelper.setScaleX(titleView, scale);
        ViewHelper.setScaleY(titleView, scale);

        // Translate title text
        int maxTitleTranslationY = flexibleSpaceImageHeight - tabHeight - getActionBarSize();
        int titleTranslationY = maxTitleTranslationY - scrollY;
        ViewHelper.setTranslationY(titleView, titleTranslationY);

        // If tabs are moving, cancel it to start a new animation.
        ViewPropertyAnimator.animate(mSlidingTabLayout).cancel();
        // Tabs will move between the top of the screen to the bottom of the image.
        float translationY = ScrollUtils.getFloat(-scrollY + mFlexibleSpaceHeight - mTabHeight, 0, mFlexibleSpaceHeight - mTabHeight);
        if (animated) {
            // Animation will be invoked only when the current tab is changed.
            ViewPropertyAnimator.animate(mSlidingTabLayout)
                    .translationY(translationY)
                    .setDuration(200)
                    .start();
        } else {
            // When Fragments' scroll, translate tabs immediately (without animation).
            ViewHelper.setTranslationY(mSlidingTabLayout, translationY);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void setPivotXToTitle(View view) {
        final TextView mTitleView = (TextView) view.findViewById(R.id.title);
        Configuration config = getResources().getConfiguration();
        if (Build.VERSION_CODES.JELLY_BEAN_MR1 <= Build.VERSION.SDK_INT
                && config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            ViewHelper.setPivotX(mTitleView, view.findViewById(android.R.id.content).getWidth());
        } else {
            ViewHelper.setPivotX(mTitleView, 0);
        }
    }

    private void propagateScroll(int scrollY) {
        // Set scrollY for the fragments that are not created yet
        mPagerAdapter.setScrollY(scrollY);

        // Set scrollY for the active fragments
        for (int i = 0; i < mPagerAdapter.getCount(); i++) {
            // Skip current item
            if (i == mPager.getCurrentItem()) {
                continue;
            }

            // Skip destroyed or not created item
            FlexibleSpaceWithImageBaseFragment f =
                    (FlexibleSpaceWithImageBaseFragment) mPagerAdapter.getItemAt(i);
            if (f == null) {
                continue;
            }

            View view = f.getView();
            if (view == null) {
                continue;
            }
            f.setScrollY(scrollY, mFlexibleSpaceHeight);
            f.updateFlexibleSpace(scrollY);
        }
    }


    private static class NavigationAdapter extends CacheFragmentStatePagerAdapter {

        private static final String[] TITLES = new String[]{"简介", "动态", "排行榜", "更衣室"};

        private int mScrollY;

        public NavigationAdapter(FragmentManager fm) {
            super(fm);
        }

        public void setScrollY(int scrollY) {
            mScrollY = scrollY;
        }

        @Override
        protected Fragment createItem(int position) {
            FlexibleSpaceWithImageBaseFragment f;
            switch (position) {
                case 0: {
//                    f = new FlexibleSpaceWithImageScrollViewFragment();
                    f = new SummaryFragment();
                    break;
                }
                case 1: {
                    f = new FlexibleSpaceWithImageListViewFragment();
                    break;
                }
                case 2: {
//                    f = new FlexibleSpaceWithImageRecyclerViewFragment();
                      f = new RankingsFatherFragment();
                    break;
                }
                case 3:
                default: {
                    f = new FlexibleSpaceWithImageRecyclerViewFragment();
                    break;
                }
            }
            f.setArguments(mScrollY);
            return f;
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }
    }
}




