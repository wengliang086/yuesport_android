package com.football.yuedong.happysports.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.adapter.TeamIconsAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class TeamIconsActivity extends Activity {

    private RecyclerView mRecyclerView;
    private List<Integer> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_icons);

        initData();

        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        TeamIconsAdapter mAdapter = new TeamIconsAdapter(this, mDatas);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    public void btnCamera(View view) {
        Toast.makeText(TeamIconsActivity.this, "该功能暂未实现！", Toast.LENGTH_SHORT).show();
    }

    protected void initData() {
        mDatas = new ArrayList<Integer>();
        for (int i = 0; i < 40; i++) {
            if (i % 2 == 0) {
                mDatas.add(R.drawable.profile_pressed);
            } else {
                mDatas.add(R.drawable.pitch_image_1);
            }
        }
    }
}
