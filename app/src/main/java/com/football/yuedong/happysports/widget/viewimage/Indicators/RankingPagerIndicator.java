package com.football.yuedong.happysports.widget.viewimage.Indicators;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class RankingPagerIndicator extends LinearLayout {

    private static final int COLOR_TEXT_NORMAL = Color.parseColor("#ff475787");
    private static final int COLOR_TEXT_HIGHLIGHT = Color.WHITE;
    private ViewPager mViewPager;

    public RankingPagerIndicator(Context context) {
        this(context, null);
    }

    public RankingPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setViewPager(ViewPager viewpager, int position) {
        mViewPager = viewpager;
        setItemClickEvent();
        mViewPager.addOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                highLightTextView(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        mViewPager.setCurrentItem(position);
        highLightTextView(position);
    }

    /**
     * 高亮被点击的tab
     *
     * @param position
     */
    private void highLightTextView(int position) {
        resetTextViewColor();
        View view = getChildAt(position);
        if (view instanceof TextView) {
            ((TextView) view).setTextColor(COLOR_TEXT_HIGHLIGHT);
            view.setEnabled(false);
        }
    }

    /**
     * 重置tab文本颜色
     */
    private void resetTextViewColor() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(COLOR_TEXT_NORMAL);
                view.setEnabled(true);
            }
        }
    }

    /**
     * 设置Tab的点击事件
     */
    private void setItemClickEvent() {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final int j = i;
            View view = getChildAt(i);
            if (view instanceof TextView) {
                view.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(j);
                    }
                });
            }
        }
    }

    public void setTitles(List<String> mTitles) {
        if (mTitles == null || mTitles.size() != getChildCount()) {
            return;
        }
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof TextView) {
                ((TextView) view).setText(mTitles.get(i));
            }
        }
    }
}
