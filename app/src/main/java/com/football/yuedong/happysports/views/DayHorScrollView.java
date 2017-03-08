package com.football.yuedong.happysports.views;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.utils.AndroidUtilities;

import java.util.List;

/**
 * Created by liuyj on 2016/5/27.
 */
public class DayHorScrollView extends HorizontalScrollView{


    protected LinearLayout weeksContainers;

    private int mScreenWidth;

    private int mItemWidth;

    private int columnSelectIndex;

    private LinearLayout.LayoutParams params;

    private Context context;

    public DayHorScrollView(Context context) {
        super(context);
        init(context);

    }

    public DayHorScrollView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        this.context = context;
        mScreenWidth = AndroidUtilities.getWindowsWidth((Activity)context);
        mItemWidth = mScreenWidth / 4;
        params = new LinearLayout.LayoutParams(mItemWidth, LinearLayout.LayoutParams.MATCH_PARENT);

        setVisibility(View.INVISIBLE);
        setFillViewport(false);
        setHorizontalScrollBarEnabled(false);
        setBackgroundColor(context.getResources().getColor(R.color.bg_gray));


        weeksContainers  = new LinearLayout(context);
        weeksContainers.setOrientation(LinearLayout.HORIZONTAL);
        addView(weeksContainers, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, 50, Gravity.CENTER));
    }






    public interface OnFilterClickListener {
        void onFilterClick(int position);
    }


    public void setFilterData(List<AndroidUtilities.Day_Week> weeks){
        weeksContainers.removeAllViews();
        int count = weeks.size();
        for (int i = 0; i < count; i++) {
            TextView columnTextView = new TextView(context);
            columnTextView.setBackgroundResource(R.drawable.header_week_selector);
            columnTextView.setGravity(Gravity.CENTER);
            columnTextView.setPadding(0, 5, 0, 0);
            columnTextView.setText(weeks.get(i).week);
            columnTextView.setClickable(true);
            columnTextView.setTextColor(context.getResources().getColorStateList(R.color.header_week_textcolor_selector));
//          Drawable drawable= mContext.getResources().getDrawable(R.drawable.under_line);
//          drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
//          columnTextView.setCompoundDrawables(null, null, null, drawable);
            if (columnSelectIndex == i) {
                columnTextView.setSelected(true);
            }
            columnTextView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    for (int i = 0; i < weeksContainers.getChildCount(); i++) {
                        View localView = weeksContainers.getChildAt(i);
                        if (localView != v)
                            localView.setSelected(false);
                        else {
                            localView.setSelected(true);
                            /**
                             * TODO update tableview;
                             */
                        }
                    }
                }
            });
            weeksContainers.addView(columnTextView, i, params);
        }
    }

}
