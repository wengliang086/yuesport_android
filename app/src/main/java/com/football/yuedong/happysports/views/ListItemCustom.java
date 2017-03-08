package com.football.yuedong.happysports.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.domain.PitchsStatus;
import com.football.yuedong.happysports.utils.AndroidUtilities;

import java.util.ArrayList;
import java.util.HashMap;

public class ListItemCustom extends LinearLayout {



	public ListItemCustom(Context context){
		super(context);
	}


	public ListItemCustom(Context context, AttributeSet attrs) {
		super(context, attrs);
	}


	private Context context = null;
	private ArrayList<View> viewList = new ArrayList();
	private TextView timer_tv;
	private String time_name;
	private int headWidthArr;


	public void buildItem(Context context,int headWidthArr,ArrayList<PitchsStatus> itemCells, String time_name){
		this.context = context;
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.time_name = time_name;
		this.headWidthArr = headWidthArr;
		this.setBackgroundColor(getResources().getColor(R.color.bg_gray));
		this.addCell(itemCells);
	}


	public void refreshData(ArrayList<PitchsStatus> itemCells, String name){
		this.refreshCells(itemCells, name);
	}


	private void refreshCells(ArrayList<PitchsStatus> itemCells, String time_name){
		timer_tv.setText(time_name);
		for(int i=0;i<itemCells.size();i++){
			PitchsStatus itemCell = itemCells.get(i);
			TextView view = (TextView)viewList.get(i);
			if(TextUtils.isEmpty(itemCell.status)){
				view.setBackgroundColor(getResources().getColor(R.color.white));
			}else if(itemCell.status.equals("0")){
				view.setBackgroundColor(getResources().getColor(R.color.lite_blue));
			}else if(itemCell.status.equals("1")){
				view.setBackgroundColor(getResources().getColor(R.color.bg_gray));
			}else if(itemCell.status.equals("2")){
				view.setBackgroundColor(getResources().getColor(R.color.item_cell_pressed));
			}
		}
	}


	private void addCell(ArrayList<PitchsStatus> itemCells){
		getTimeTextView(time_name);
		for(int i=0;i<itemCells.size();i++){
			PitchsStatus itemCell = itemCells.get(i);
			TextView view = (TextView)getReadView(itemCell);
			this.addView(view, LayoutHelper.createLinear(AndroidUtilities.dp(20f), AndroidUtilities.dp(12f), 0, 0, 1, 1));
			viewList.add(view);
		}
	}

	private View getReadView(PitchsStatus itemCell){
		final TextView code = new TextView(context);
		code.setTag(itemCell);
		code.setGravity(Gravity.CENTER);
        code.setText(" ");
		if(TextUtils.isEmpty(itemCell.status)){
			code.setClickable(false);
			code.setEnabled(false);
			code.setBackgroundColor(getResources().getColor(R.color.white));
		}else if(itemCell.status.equals("0")){
			code.setBackgroundColor(getResources().getColor(R.color.lite_blue));
		}else if(itemCell.status.equals("1")){
			code.setClickable(false);
			code.setEnabled(false);
			code.setBackgroundColor(getResources().getColor(R.color.bg_gray));
		}else if(itemCell.status.equals("2")){
			code.setBackgroundColor(getResources().getColor(R.color.item_cell_pressed));
		}
        code.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(itemCellClickListener != null){
					itemCellClickListener.onItemCellClick((PitchsStatus) v.getTag());
				}
			}
		});


		return code;
	}

	private void getTimeTextView(String time_name){
		timer_tv = new TextView(context);
		timer_tv.setText(time_name);
		timer_tv.setTextColor(getResources().getColor(R.color.black_de));
		timer_tv.setGravity(Gravity.CENTER);
		timer_tv.setBackgroundResource(R.color.white);
		this.addView(timer_tv, LayoutHelper.createLinear(AndroidUtilities.dp(20f), AndroidUtilities.dp(12f), 0, 0, 1, 1));
	}

	private ItemCellClickListener itemCellClickListener;

	public void setItemCellClickListener(ItemCellClickListener itemCellClickListener){
       this.itemCellClickListener = itemCellClickListener;
	}

	public interface ItemCellClickListener {

		public void onItemCellClick(PitchsStatus status);
	}

}
