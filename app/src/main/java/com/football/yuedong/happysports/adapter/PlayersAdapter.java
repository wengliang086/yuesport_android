package com.football.yuedong.happysports.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.domain.TeamMemberRelp;
import com.football.yuedong.happysports.ui.TeamCreateActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/4/11.
 */
public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.MyViewHolder> {

    private List<TeamMemberRelp.ValueEntity> mDatas;
    private LayoutInflater mInflater;
    private Activity mContext;

    public void updateDatas(List<TeamMemberRelp.ValueEntity> datas) {
        mDatas = datas;
    }

    public PlayersAdapter(Activity context, List<TeamMemberRelp.ValueEntity> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public PlayersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(mInflater.inflate(R.layout.player_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final PlayersAdapter.MyViewHolder holder, int position) {
        holder.tv.setImageResource(mDatas.get(position).getUid());
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int pos = holder.getLayoutPosition();
//                Integer res = mDatas.get(pos);
//                TeamCreateActivity.avatar.setImageResource(res);
//                mContext.finish();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView tv;

        public MyViewHolder(View view) {
            super(view);
            tv = (ImageView) view.findViewById(R.id.icon);
        }
    }
}
