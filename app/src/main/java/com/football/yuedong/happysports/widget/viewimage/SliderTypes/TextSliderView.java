
package com.football.yuedong.happysports.widget.viewimage.SliderTypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.football.yuedong.happysports.R;

/**
 * This is a slider with a description TextView.
 */
public class TextSliderView extends BaseSliderView {
    public TextSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.render_type_text, null);
        SimpleDraweeView target = (SimpleDraweeView) v.findViewById(R.id.daimajia_slider_image);
        TextView description = (TextView) v.findViewById(R.id.description);
        description.setText(getDescription());
        loadImage(target);
        bindClickEvent(v);
        return v;
    }
}
