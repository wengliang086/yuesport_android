package com.football.yuedong.happysports.views;

import android.view.View;

import com.football.yuedong.happysports.domain.FieldGround;

/**
 * Created by liuyanjun on 2016/3/17.
 */
public interface RecyclerItemClickListener {
    public void onItemClick(View view,FieldGround.ValueEntity entity);
}
