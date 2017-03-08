package com.football.yuedong.happysports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.domain.PitchsStatus;
import com.football.yuedong.happysports.utils.FileLog;
import com.football.yuedong.happysports.views.ListItemCustom;

import java.util.ArrayList;
import java.util.HashMap;

public class SiteDetailViewAdapter extends BaseAdapter implements ListItemCustom.ItemCellClickListener {
	private Context context;
	private LayoutInflater inflater;

	private ArrayList<ArrayList<PitchsStatus>> lists = new ArrayList<>();
	private ArrayList<String> time_header = new ArrayList<>();
	private ArrayList<PitchsStatus> pressedStatus = new ArrayList<>();


	private ListView listView = null;
	private int arrHeadWidth = 0;//每列宽度

	public SiteDetailViewAdapter(Context context, ListView listView, int arrHeadWidth) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.listView = listView;
		this.arrHeadWidth = arrHeadWidth;
		this.listView.setAdapter(this);
	}

	public void addSubList(ArrayList<String> time_header, ArrayList<ArrayList<PitchsStatus>> statuses){
		this.time_header = time_header;
		this.lists = statuses;
		notifyDataSetChanged();
	}


	@Override
	public int getCount() {
		return lists.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {
		String name = time_header.get(index);
		ArrayList itemCells = lists.get(index);
		if (view == null) {
			view = inflater.inflate(R.layout.layout_row_cell, null);
			ListItemCustom itemCustom = (ListItemCustom) view.findViewById(R.id.custome_item);
			itemCustom.setItemCellClickListener(this);
			itemCustom.buildItem(context, arrHeadWidth, itemCells, name);
			view.setTag(itemCustom);
		} else {
			ListItemCustom itemCustom = (ListItemCustom) view.getTag();
			itemCustom.refreshData(itemCells, name);
		}
		return view;
	}

	@Override
	public void onItemCellClick(PitchsStatus status) {
		for(PitchsStatus stat: pressedStatus){
			stat.status = "0";
		}
		int row_column = status.cellId;
		for(ArrayList<PitchsStatus> statuses : lists){
			PitchsStatus aa = statuses.get(row_column);
			if (status.modelId.equals(aa.modelId)) {
				aa.status = "2";
				pressedStatus.add(aa);
			}
		}
		FileLog.d("SiteDetailViewAdapter", "cell id is " +  status.cellId);
		notifyDataSetChanged();
	}

	public PitchsStatus getPressedStatus(){
		if(pressedStatus.size() ==0){
			return null;
		}
		return pressedStatus.get(0);
	}

}