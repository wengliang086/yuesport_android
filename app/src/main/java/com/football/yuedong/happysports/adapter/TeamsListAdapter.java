package com.football.yuedong.happysports.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.domain.FieldGround;
import com.football.yuedong.happysports.domain.Team;
import com.football.yuedong.happysports.domain.Teams;
import com.football.yuedong.happysports.utils.FileLog;
import com.football.yuedong.happysports.views.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyj on 2016/4/11.
 */
public class TeamsListAdapter extends RecyclerView.Adapter<TeamsListAdapter.ViewHolder> implements View.OnClickListener{


    private Context context;

    //界面缓存数据
    private List<Teams.ValueEntity> mDataSet = new ArrayList<Teams.ValueEntity>();
    private RecyclerItemClickListener itemClickListener;

    public TeamsListAdapter(Context mContext){
        this.context = mContext;
    }


    @Override
    public TeamsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_teams_item, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(TeamsListAdapter.ViewHolder holder, int position) {
        Teams.ValueEntity team_info = mDataSet.get(position);
        holder.itemView.setTag(team_info);
        if(!TextUtils.isEmpty(team_info.getIcon())){
            Uri uri = Uri.parse(team_info.getIcon());
            holder.team_image.setImageURI(uri);
        }
        holder.team_name.setText(team_info.getName());

        holder.team_count.setText(team_info.getHomeCourt());
        holder.team_distance.setText("400km");
    }

    public void setOnRecyclerItemClickListener(RecyclerItemClickListener listener){
        this.itemClickListener = listener;
    }

    public void remove(int position) {
        mDataSet.remove(position);
        notifyItemRemoved(position);
    }

    public void add(Teams.ValueEntity value, int position) {
        mDataSet.add(position, value);
        notifyItemInserted(position);
    }


    public void addSubTeamsList(List<Teams.ValueEntity> teams){
        mDataSet.addAll(teams);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public void onClick(View v) {
        if(itemClickListener != null){
            itemClickListener.onItemClick(v, (FieldGround.ValueEntity)v.getTag());
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public SimpleDraweeView team_image;
        public TextView team_name;
        public TextView team_distance;
        public TextView team_count; //球队成员数目
        public TextView joined;

        public ViewHolder(View itemView) {
            super(itemView);
            team_image = (SimpleDraweeView) itemView.findViewById(R.id.team_header);
            team_name = (TextView) itemView.findViewById(R.id.team_name);
            team_distance = (TextView) itemView.findViewById(R.id.team_location);
            team_count = (TextView) itemView.findViewById(R.id.team_count);
        }

    }
}
