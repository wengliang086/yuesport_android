package com.football.yuedong.happysports.ui;

import android.content.Context;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.fragment.BaseFragment;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by liuyanjun on 2016/3/31.
 */
@EFragment(R.layout.layout_order_block)
public class PitchFiledFragment extends BaseFragment{

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @AfterInject
    protected void init(){

    }

    @AfterViews
    protected void initViews(){

    }


}
