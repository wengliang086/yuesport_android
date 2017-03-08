package com.football.yuedong.happysports.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.domain.PitchsStatus;
import com.football.yuedong.happysports.utils.AndroidUtilities;

import java.util.ArrayList;

public class ListHeader extends LinearLayout {

    private Context context;

	public ListHeader(Context context){
		super(context);
		this.setOrientation(LinearLayout.HORIZONTAL);
		this.context = context;
		this.setBackgroundColor(getResources().getColor(R.color.bg_gray));
	}


	public ListHeader(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public void addItemHeader(ArrayList<String> itemCells){
		for(int i=0;i<itemCells.size();i++){
			String header_name = itemCells.get(i);
			TextView view = (TextView)getReadView(header_name);
			this.addView(view, LayoutHelper.createLinear(AndroidUtilities.dp(20f), AndroidUtilities.dp(12f), 0, 0, 1, 1));
		}
		requestLayout();
	}

	private View getReadView(String status){
		TextView code = new TextView(context);
		code.setGravity(Gravity.CENTER);
		code.setText(status);
		return code;
	}
}
