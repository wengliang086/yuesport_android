package com.football.yuedong.happysports.views;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;

import com.football.yuedong.happysports.R;

public class SlideFromBottomPopup extends BasePopupWindow implements View.OnClickListener{

    private View popupView;
    public SlideFromBottomPopup(Activity context) {
        super(context);
        bindEvent();
    }

    @Override
    protected Animation getShowAnimation() {
        return getTranslateAnimation(250*2,0,300);
    }

    @Override
    protected View getClickToDismissView() {
        return popupView.findViewById(R.id.click_to_dismiss);
    }

    @Override
    public View getPopupView() {
        popupView= LayoutInflater.from(mContext).inflate(R.layout.popup_slide_from_bottom,null);
        return popupView;
    }

    @Override
    public View getAnimaView() {
        return popupView.findViewById(R.id.popup_anima);
    }

    private void bindEvent() {
        if (popupView!=null){
            popupView.findViewById(R.id.tx_1).setOnClickListener(this);
            popupView.findViewById(R.id.tx_2).setOnClickListener(this);
            popupView.findViewById(R.id.tx_3).setOnClickListener(this);
        }

    }

    @Override
    public void onClick(View v) {

    }
}
