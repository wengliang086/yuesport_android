package com.football.yuedong.happysports.views;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.utils.AndroidUtilities;

import java.util.List;

/**
 * Created by liuyj on 2016/5/26.
 */
public class HeaderPitchView extends HeaderViewInterface<List<String>> {


    private LinearLayout pitch_header;

    public HeaderPitchView(Activity context){
        super(context);
    }

    @Override
    protected void getView(List<String> pitchs, ListView listView) {
        View view = mInflate.inflate(R.layout.header_pitchinfo_layout, listView, false);
        pitch_header = (LinearLayout) view.findViewById(R.id.header_container);
        dealWithView(pitchs);
        listView.addHeaderView(view);
    }


    public void dealWithView(List<String> pitchs){
        pitch_header.removeAllViews();
        for(int i=0;i<pitchs.size();i++){
            String header_name = pitchs.get(i);
            TextView view = (TextView)getReadView(header_name);
            pitch_header.addView(view, LayoutHelper.createLinear(AndroidUtilities.dp(20f), AndroidUtilities.dp(12f), 0, 0, 1, 1));
        }
    }

    private View getReadView(String status){
        TextView code = new TextView(mContext);
        code.setGravity(Gravity.CENTER);
        code.setText(status);
        return code;
    }
}
