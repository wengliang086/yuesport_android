package com.football.yuedong.happysports.fragment;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.football.yuedong.happysports.R;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by liuyanjun on 2015/12/15.
 */
@EFragment(R.layout.activity_flexiblespacewithimagescrollview)
public class ProfileFragment extends BaseFragment  implements ObservableScrollViewCallbacks {

    private static final float MAX_TEXT_SCALE_DELTA = 0.3f;

    private View mImageView;
    private View mOverlayView;
    private ObservableScrollView mScrollView;
    private TextView mTitleView;
    private View mFab;
    private int mActionBarSize;
    private int mFlexibleSpaceShowFabOffset;
    private int mFlexibleSpaceImageHeight;
    private int mFabMargin;
    private boolean mFabIsShown;


    @AfterViews
    public void initViews(){

            mFlexibleSpaceImageHeight = getResources().getDimensionPixelSize(R.dimen.flexible_space_image_height);
            mFlexibleSpaceShowFabOffset = getResources().getDimensionPixelSize(R.dimen.flexible_space_show_fab_offset);
            mActionBarSize = getActionBarSize();

            mImageView = getView().findViewById(R.id.image);
            mOverlayView = getView().findViewById(R.id.overlay);
            mScrollView = (ObservableScrollView) getView().findViewById(R.id.scroll);
            mScrollView.setScrollViewCallbacks(this);
            mTitleView = (TextView)getView(). findViewById(R.id.title);
            mTitleView.setText("");

            mFab = getView().findViewById(R.id.fab);
            mFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "FAB is clicked", Toast.LENGTH_SHORT).show();
                }
            });
            mFabMargin = getResources().getDimensionPixelSize(R.dimen.margin_standard);
            ViewHelper.setScaleX(mFab, 0);
            ViewHelper.setScaleY(mFab, 0);

            ScrollUtils.addOnGlobalLayoutListener(mScrollView, new Runnable() {
                @Override
                public void run() {
                    mScrollView.scrollTo(0, mFlexibleSpaceImageHeight - mActionBarSize);

                    // If you'd like to start from scrollY == 0, don't write like this:
                    //mScrollView.scrollTo(0, 0);
                    // The initial scrollY is 0, so it won't invoke onScrollChanged().
                    // To do this, use the following:
                    //onScrollChanged(0, false, false);

                    // You can also achieve it with the following codes.
                    // This causes scroll change from 1 to 0.
                    //mScrollView.scrollTo(0, 1);
                    //mScrollView.scrollTo(0, 0);
                }
            });
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {

    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }
}
