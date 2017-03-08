package com.football.yuedong.happysports.views;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.domain.FieldGround;

/**
 * Created by liuyj on 2016/5/26.
 */
public class HeaderDetailView extends HeaderViewInterface<FieldGround>{

    public HeaderDetailView(Activity context){
        super(context);
    }

    @Override
    protected void getView(FieldGround fieldGround, ListView listView) {
        View view = mInflate.inflate(R.layout.header_detail_layout, listView, false);
        listView.addHeaderView(view);

    }
}
