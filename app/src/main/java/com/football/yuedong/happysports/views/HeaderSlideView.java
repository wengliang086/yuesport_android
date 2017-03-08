package com.football.yuedong.happysports.views;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.widget.viewimage.Animations.DescriptionAnimation;
import com.football.yuedong.happysports.widget.viewimage.Animations.SliderLayout;
import com.football.yuedong.happysports.widget.viewimage.SliderTypes.BaseSliderView;
import com.football.yuedong.happysports.widget.viewimage.SliderTypes.TextSliderView;

import java.util.List;

/**
 * Created by liuyj on 2016/5/25.
 */
public class HeaderSlideView extends HeaderViewInterface<List<String>> implements BaseSliderView.OnSliderClickListener{

    protected SliderLayout mSlider;

    public HeaderSlideView(Activity context){
        super(context);
    }


    @Override
    protected void getView(List<String> urls, ListView listView) {
        View view = mInflate.inflate(R.layout.head_item, listView,false);
        mSlider = (SliderLayout) view.findViewById(R.id.slider);
        for(String url : urls){
            TextSliderView textSliderView = new TextSliderView(mContext);
            textSliderView.setOnSliderClickListener(HeaderSlideView.this);
            textSliderView.image(url);
            mSlider.addSlider(textSliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        listView.addHeaderView(view);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
}
