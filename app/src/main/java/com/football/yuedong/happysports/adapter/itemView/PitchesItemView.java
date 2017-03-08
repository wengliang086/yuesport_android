package com.football.yuedong.happysports.adapter.itemView;

import android.content.Context;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.football.yuedong.happysports.R;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by liuyanjun on 2015/12/28.
 */
@EViewGroup(R.layout.list_pitches_item)
public class PitchesItemView extends RelativeLayout {

    @ViewById(R.id.pitch_image)
    ImageView icon;

    @ViewById(R.id.pitch_name)
    TextView name;

    @ViewById(R.id.pitch_address)
    TextView address;

    @ViewById(R.id.tv_price)
    TextView price;

    @ViewById(R.id.tv_price)
    TextView tv_distance;



    public PitchesItemView(Context context) {
        super(context);

    }

}
