package com.football.yuedong.happysports.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/5/14 0014.
 */
public class T {

    public static void show(Context context, CharSequence text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
}
