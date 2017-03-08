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
import com.football.yuedong.happysports.utils.FileLog;
import com.football.yuedong.happysports.views.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**

 */
public class PitchesAdapter extends RecyclerView.Adapter<PitchesAdapter.ViewHolder> implements View.OnClickListener{

  private Context mContext;
  private List<FieldGround.ValueEntity> mDataSet = new ArrayList<FieldGround.ValueEntity>();
  private RecyclerItemClickListener itemClickListener;

  public PitchesAdapter(Context context) {
    mContext = context;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(mContext).inflate(R.layout.list_pitches_item, parent, false);

    ViewHolder holder = new ViewHolder(v);
    v.setOnClickListener(this);
    return holder;
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {

    FieldGround.ValueEntity pitche_value = mDataSet.get(position);
    holder.itemView.setTag(pitche_value);
    if(!TextUtils.isEmpty(pitche_value.getUrl())){
      Uri uri = Uri.parse(pitche_value.getUrl());
      holder.image.setImageURI(uri);
    }
    holder.name.setText(pitche_value.getName());

    holder.address.setText(pitche_value.getAddress());
    holder.price.setText("$300");
    holder.distance.setText("500km");

  }

  @Override public int getItemCount() {
    return mDataSet.size();
  }

  public void remove(int position) {
    mDataSet.remove(position);
    notifyItemRemoved(position);
  }

  public void add(FieldGround.ValueEntity text, int position) {
    mDataSet.add(position, text);
    notifyItemInserted(position);
  }


  public void addSubPitchesList(List<FieldGround.ValueEntity> pitches){
    mDataSet.addAll(pitches);
    notifyDataSetChanged();
  }

  @Override
  public void onClick(View v) {
    FileLog.d("PitchesAdapter", "onClick");
    if(itemClickListener != null){
       itemClickListener.onItemClick(v, (FieldGround.ValueEntity)v.getTag());
    }
  }


  public void setOnRecyclerItemClickListener(RecyclerItemClickListener listener){
     this.itemClickListener = listener;
  }


  static class ViewHolder extends RecyclerView.ViewHolder {

    public SimpleDraweeView image;
    public TextView name;
    public TextView address;
    public TextView price;
    public TextView distance;

    public ViewHolder(View itemView) {
      super(itemView);
      image = (SimpleDraweeView) itemView.findViewById(R.id.pitch_image);
      name = (TextView) itemView.findViewById(R.id.pitch_name);
      address = (TextView) itemView.findViewById(R.id.pitch_address);
      price = (TextView) itemView.findViewById(R.id.tv_price);
      distance = (TextView) itemView.findViewById(R.id.tv_distance);
    }
  }
}
