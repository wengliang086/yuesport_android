package com.football.yuedong.happysports.ui;


import android.app.ActionBar;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.fragment.TabPagerFragmentAdapter;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.viewpager.SViewPager;


import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

/**
 * Created by liuyanjun on 2015/12/14.
 */
//@EActivity(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private SViewPager viewPager;

    private IndicatorViewPager indicatorViewPager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (SViewPager)findViewById(R.id.viewPager);
        Indicator indicator =  (Indicator) findViewById(R.id.indicator);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        TabPagerFragmentAdapter tabPagerFragmentAdapter = new TabPagerFragmentAdapter(getSupportFragmentManager(), this);
        indicatorViewPager.setAdapter(tabPagerFragmentAdapter);
        // 禁止viewpager的滑动事件
        viewPager.setCanScroll(false);
        // 设置viewpager保留界面不重新加载的页面数量
        viewPager.setOffscreenPageLimit(4);
    }

}
