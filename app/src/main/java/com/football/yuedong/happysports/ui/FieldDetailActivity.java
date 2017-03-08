package com.football.yuedong.happysports.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.football.yuedong.happysports.BuildVars;
import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.adapter.SiteDetailViewAdapter;
import com.football.yuedong.happysports.domain.BaseResponse;
import com.football.yuedong.happysports.domain.FieldGround;
import com.football.yuedong.happysports.domain.Order;
import com.football.yuedong.happysports.domain.PitchsStatus;
import com.football.yuedong.happysports.service.SportsService;
import com.football.yuedong.happysports.utils.AndroidUtilities;
import com.football.yuedong.happysports.utils.ColorUtil;
import com.football.yuedong.happysports.utils.T;
import com.football.yuedong.happysports.views.DayHorScrollView;
import com.football.yuedong.happysports.views.HeaderDayView;
import com.football.yuedong.happysports.views.HeaderDetailView;
import com.football.yuedong.happysports.views.HeaderPitchView;
import com.football.yuedong.happysports.views.HeaderSlideView;
import com.football.yuedong.happysports.views.LayoutHelper;
import com.football.yuedong.happysports.views.SmoothListView;
import com.google.gson.Gson;
import com.hyphenate.util.DensityUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liuyj on 2016/5/25.
 */
public class FieldDetailActivity extends AppCompatActivity {

    protected SmoothListView smoothListView;

    protected RelativeLayout rlBar;

    private Context mContext;

    private int mScreenHeight;

    private int mScreenWidth;

    private int mItemWidth;

    private HeaderSlideView listSlideView;

    private HeaderDetailView detailView;

    private HeaderPitchView pitchView;

    private HeaderDayView dayView;

    private DayHorScrollView dayHorScrollView;

    private ArrayList<String> header_url = new ArrayList<>();

    private boolean isScrollIdle = false;

    private SiteDetailViewAdapter mAdapter;

    private final ArrayList<String> header_tile = new ArrayList<>();

    private List<AndroidUtilities.Day_Week> day_weeks = AndroidUtilities.getWeekList();

    private View itemHeaderAdView; // 从ListView获取的广告子View
    private View itemHeaderFilterView; // 从ListView获取的筛选子View
    private boolean isStickyTop = false; // 是否吸附在顶部
    private boolean isSmooth = false; // 没有吸附的前提下，是否在滑动
    private int titleViewHeight = 50; // 标题栏的高度
    private int filterPosition = -1; // 点击FilterView的位置：分类(0)、排序(1)、筛选(2)

    private int adViewHeight = 180; // 广告视图的高度
    private int adViewTopSpace; // 广告视图距离顶部的距离

    private int filterViewPosition = 3; // 筛选视图的位置
    private int filterViewTopSpace; // 筛选视图距离顶部的距离

    private ImageView btn_back;

    private TextView title_tv;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_field_detail);
        mContext = this;
        mScreenHeight = AndroidUtilities.getWindowHeight(this);
        smoothListView = (SmoothListView) findViewById(R.id.listView);
        smoothListView.addFooterView(generateFooterView());
        rlBar = (RelativeLayout) findViewById(R.id.rl_bar);

        btn_back = (ImageView) findViewById(R.id.btn_back);
        title_tv = (TextView) findViewById(R.id.tv_title);

        dayHorScrollView = (DayHorScrollView) findViewById(R.id.filter_day_view);

        mScreenWidth = AndroidUtilities.getWindowsWidth(this);
        mItemWidth = mScreenWidth / 4;

        initData();

        initView();

        initListener();

    }

    /**
     *   <TextView
     android:id="@+id/pitch_order"
     android:layout_width="match_parent"
     android:layout_height="wrap_content"
     android:layout_marginLeft="30dp"
     android:layout_marginRight="30dp"
     android:text="预定场地"
     android:textColor="@color/white"
     android:gravity="center"
     android:textSize="16sp"
     android:background="@drawable/btn_no_solid_yellow"/>
     *
     *
     * @return
     */

    private View generateFooterView(){

        LinearLayout footer_container = new LinearLayout(this);

        TextView footer = new TextView(this);
        footer.setText("预定场地");
        footer.setTextSize(AndroidUtilities.sp2px(this, 5));
        footer.setTextColor(getResources().getColor(R.color.white));
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(2, 2, 2, 2);
        footer.setBackgroundResource(R.drawable.btn_no_solid_yellow);
        footer_container.addView(footer, LayoutHelper.createLinear(LayoutHelper.MATCH_PARENT, LayoutHelper.WRAP_CONTENT, Gravity.CENTER, 20, 3, 20, 3));
        footer_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();
            }
        });
        return footer_container;
    }

    private void createOrder(){
        PitchsStatus status = mAdapter.getPressedStatus();
        if(status == null){
            //TODO;
            return;
        }
        String[] aa = status.modelId.split("_");
        SportsService.createSportsService("").createOrder(aa[0], "9", "20160528", aa[1])
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Order>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        T.show(FieldDetailActivity.this, e.getMessage());
                    }

                    @Override
                    public void onNext(Order order) {
                        if(order.isSuccess()){
                            Intent intent = new Intent(FieldDetailActivity.this, OrderConfirmActivity.class);
                            intent.putExtra("order", order);
                            startActivity(intent);
                        } else {
                            T.show(FieldDetailActivity.this, order.getDesc());
                        }
                    }
                });
    }


    private void initData(){

        day_weeks = AndroidUtilities.getWeekList();

        // slide view image;
        header_url.add("http://115.159.150.158/group1/M00/00/00/CmlEFlbABfeAEbYyAAT9K76bVpI333_big.jpg?w=200&h=200");

        queryPictchStatus();

        queryFieldInfo();

    }


    private void initView(){


        dayHorScrollView.setVisibility(View.INVISIBLE);
        dayHorScrollView.setFilterData(day_weeks);

        //hide week views;
        listSlideView = new HeaderSlideView(this);
        listSlideView.fillView(header_url, smoothListView);

        detailView = new HeaderDetailView(this);
        FieldGround group = new FieldGround();
        detailView.fillView(group, smoothListView);

        dayView = new HeaderDayView(this);
        dayView.fillView(day_weeks, smoothListView);

        pitchView = new HeaderPitchView(this);
        header_tile.add("");
        pitchView.fillView(header_tile, smoothListView);

        mAdapter = new SiteDetailViewAdapter(this, smoothListView, mItemWidth);
    }

    private void initListener(){

        dayView.setOnFilterClickListener(new HeaderDayView.OnFilterClickListener() {
            @Override
            public void onFilterClick(int position) {
                //TODO query pitch status;
            }
        });

        smoothListView.setRefreshEnable(false);
        smoothListView.setLoadMoreEnable(false);
        smoothListView.setOnScrollListener(new SmoothListView.OnSmoothScrollListener() {


            @Override
            public void onSmoothScrolling(View view) {

            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                isScrollIdle = (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (isScrollIdle && adViewTopSpace < 0) return;

                // 获取广告头部View、自身的高度、距离顶部的高度
                if (itemHeaderAdView == null) {
                    itemHeaderAdView = smoothListView.getChildAt(1-firstVisibleItem);
                }
                if (itemHeaderAdView != null) {
                    adViewTopSpace = DensityUtil.px2dip(mContext, itemHeaderAdView.getTop());
                    adViewHeight = DensityUtil.px2dip(mContext, itemHeaderAdView.getHeight());
                }

                // 获取筛选View、距离顶部的高度
                if (itemHeaderFilterView == null) {
                    itemHeaderFilterView = smoothListView.getChildAt(filterViewPosition - firstVisibleItem);
                }
                if (itemHeaderFilterView != null) {
                    filterViewTopSpace = DensityUtil.px2dip(mContext, itemHeaderFilterView.getTop());
                }

                // 处理筛选是否吸附在顶部
                if (filterViewTopSpace > titleViewHeight) {
                    isStickyTop = false; // 没有吸附在顶部
                    dayHorScrollView.setVisibility(View.INVISIBLE);
                } else {
                    isStickyTop = true; // 吸附在顶部
                    dayHorScrollView.setVisibility(View.VISIBLE);
                }

                if (firstVisibleItem > filterViewPosition) {
                    isStickyTop = true;
                    dayHorScrollView.setVisibility(View.VISIBLE);
                }

//                if (isSmooth && isStickyTop) {
//                    isSmooth = false;
//                    dayHorScrollView.showFilterLayout(filterPosition);
//                }
//
//                dayHorScrollView.setStickyTop(isStickyTop);

                // 处理标题栏颜色渐变
                handleTitleBarColorEvaluate();
            }
        });
    }


    private static ArrayList<String> columsTitles = new ArrayList<>();
    static {
        for (int i = 8; i < 22; i++) {
            if (i < 10) {
                columsTitles.add("0" + i + ":" + "00");
                columsTitles.add("0" + i + ":" + "30");
            } else {
                columsTitles.add(i + ":" + "00");
                columsTitles.add(i + ":" + "30");
            }
        }
    }


    private void queryFieldInfo(){

    }

    private void queryPictchStatus(){
        OkHttpUtils.get().url(BuildVars.SERVER_PITCH_STATUS)
                .addParams("filedId","1")
                .addParams("day", "20160528")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(String s) {
                        Map map_value = new Gson().fromJson(s, Map.class);
                        ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) map_value.get("value");
                        // 画表头
                        for (Map<String, Object> rt : list) {
                            header_tile.add((String) rt.get("name"));
                        }
                        // 画每一行数据
                        final ArrayList<ArrayList<PitchsStatus>> time_pitch = new ArrayList<ArrayList<PitchsStatus>>();
                        int row = 0;
                        for (String ct : columsTitles) {
                            row ++ ;
                            ArrayList<PitchsStatus> list1 = new ArrayList<PitchsStatus>();
                            time_pitch.add(list1);
                            int column = 0;
                            for (Map<String, Object> rt : list) {
                                PitchsStatus m = new PitchsStatus();
                                m.cellId = column ++;
                                Map<String, String> map = (Map<String, String>) rt.get(ct);
                                if (map != null) {
                                    m.modelId = map.get("id");
                                    m.status = map.get("status");
                                }
                                list1.add(m);
                            }
                        }

                        AndroidUtilities.runOnUIThread(new Runnable() {
                            @Override
                            public void run() {
                                pitchView.dealWithView(header_tile);
                                mAdapter.addSubList(columsTitles, time_pitch);
                            }
                        });
                    }
                });
    }


    private void handleTitleBarColorEvaluate() {
        float fraction;
        if (adViewTopSpace > 0) {
            fraction = 1f - adViewTopSpace * 1f / 60;
            if (fraction < 0f) fraction = 0f;
            rlBar.setAlpha(fraction);
            return ;
        }

        float space = Math.abs(adViewTopSpace) * 1f;
        fraction = space / (adViewHeight - titleViewHeight);
        if (fraction < 0f) fraction = 0f;
        if (fraction > 1f) fraction = 1f;
        rlBar.setAlpha(1f);

        if (fraction >= 1f || isStickyTop) {
            isStickyTop = true;
            btn_back.setAlpha(0f);
            title_tv.setAlpha(0f);
            rlBar.setBackgroundColor(mContext.getResources().getColor(R.color.primary));
        } else {
            btn_back.setAlpha(1f - fraction);
            title_tv.setAlpha(1f - fraction);
            rlBar.setBackgroundColor(ColorUtil.getNewColorByStartEndColor(mContext, fraction, R.color.transparent, R.color.primary));
        }
    }
}
