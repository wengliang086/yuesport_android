package com.football.yuedong.happysports.views;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.domain.FieldGround;
import com.football.yuedong.happysports.utils.AndroidUtilities;
import com.football.yuedong.happysports.widget.viewimage.ColumnHorizontalScrollView;

import java.util.List;

/**
 * Created by liuyj on 2016/5/26.
 */
public class HeaderDayView extends HeaderViewInterface<List<AndroidUtilities.Day_Week>> implements DayHorScrollView.OnFilterClickListener{

    private DayHorScrollView dayHorScrollView;

    public HeaderDayView(Activity context){
        super(context);
    }

    @Override
    protected void getView(List<AndroidUtilities.Day_Week> weeks, ListView listView) {
        View view = mInflate.inflate(R.layout.filter_day_layout, listView, false);
        dayHorScrollView = (DayHorScrollView) view.findViewById(R.id.filter_day);
        dayHorScrollView.setVisibility(View.VISIBLE);
        dealWithView(weeks);
        listView.addHeaderView(view);
    }

    private void dealWithView(List<AndroidUtilities.Day_Week> weeks){
        dayHorScrollView.setFilterData(weeks);
    }

    @Override
    public void onFilterClick(int position) {
        if (onFilterClickListener != null) {
            onFilterClickListener.onFilterClick(position);
        }
    }

    private OnFilterClickListener onFilterClickListener;


    public void setOnFilterClickListener(OnFilterClickListener onFilterClickListener) {
        this.onFilterClickListener = onFilterClickListener;
    }
    public interface OnFilterClickListener {
        void onFilterClick(int position);
    }
}
