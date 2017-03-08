package com.football.yuedong.happysports.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.domain.RankingListInfo;
import com.football.yuedong.happysports.service.SportsInterface;
import com.football.yuedong.happysports.service.SportsService;
import com.football.yuedong.happysports.ui.TeamCreateActivity;
import com.football.yuedong.happysports.user.UserConfig;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/12.
 */
public class RankingsFragment extends Fragment {

    private String mTitle;
    private static final String BUNDLE_TITLE = "title";
    private View baseView;
    private List<RankingListInfo.ValueEntity> mDataSet;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        baseView = inflater.inflate(R.layout.fragment_ranking_list, container, false);
        initData(inflater);
        return baseView;
    }

    private void initData(final LayoutInflater inflater) {
//        mDataSet = DataProvider.getInstance().getRankings();
//        afterCreateView(inflater);
        SportsInterface sportsInterface = SportsService.createSportsService(UserConfig.ACCESS_TOKEN);
        sportsInterface.getRankingList(0, 0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RankingListInfo>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getActivity(), "onCompleted", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getActivity(), "onError", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNext(RankingListInfo rankingListInfo) {
                        mDataSet = rankingListInfo.getValue();
                        afterCreateView(inflater);
                    }
                });
    }

    private void afterCreateView(final LayoutInflater inflater) {
        RecyclerView recyclerview = (RecyclerView) baseView.findViewById(R.id.id_recyclerview);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mTitle = bundle.getString(BUNDLE_TITLE);
        }
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerview.setAdapter(new RecyclerView.Adapter<MyViewHolder>() {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View v = inflater.inflate(R.layout.list_ranking_item, parent, false);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getActivity().startActivity(new Intent(getActivity(), TeamCreateActivity.class));
                    }
                });
                MyViewHolder myViewHolder = new MyViewHolder(v);
                return myViewHolder;
            }

            @Override
            public void onBindViewHolder(MyViewHolder holder, int position) {
                RankingListInfo.ValueEntity value = mDataSet.get(position);
                holder.itemView.setTag(value);
                holder.tv1.setText(mTitle + "：" + value.getAffectScore());
                holder.name.setText(value.getName());
                holder.imageView.setImageURI(Uri.parse(value.getIcon()));
            }

            @Override
            public int getItemCount() {
                return mDataSet.size();
            }
        });
    }

    public static RankingsFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        RankingsFragment fragment = new RankingsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        SimpleDraweeView imageView;
        TextView tv1;
        TextView name;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.ranking_value);
            name = (TextView) itemView.findViewById(R.id.name);
            imageView = (SimpleDraweeView) itemView.findViewById(R.id.pic);
        }
    }
}
