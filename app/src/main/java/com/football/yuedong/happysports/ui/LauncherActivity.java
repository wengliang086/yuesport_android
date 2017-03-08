package com.football.yuedong.happysports.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.football.yuedong.happysports.ApplicationLoader;
import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.user.UserConfig;
import com.football.yuedong.happysports.utils.AndroidUtilities;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;

@EActivity(R.layout.activity_launcher)
@Fullscreen
public class LauncherActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ApplicationLoader.postInitApplication();

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.drawable.transparent);
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            AndroidUtilities.statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        if (!UserConfig.isClientActivated()) {
            startActivity(new Intent(this, FieldDetailActivity.class));
            finish();
            return;
        }
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
