package com.football.yuedong.happysports.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.domain.Team;
import com.football.yuedong.happysports.service.SportsInterface;
import com.football.yuedong.happysports.service.SportsService;
import com.football.yuedong.happysports.user.UserConfig;
import com.football.yuedong.happysports.utils.StringUtils;
import com.football.yuedong.happysports.utils.T;
import com.google.gson.Gson;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2016/4/11.
 */
public class TeamCreateActivity extends Activity {

    public static ImageView avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_create);
        avatar = (ImageView) findViewById(R.id.team_avatar);
        avatar.setImageResource(R.drawable.profile_pressed);
    }

    public void openIconsSet(View view) {
        Intent intent = new Intent(this, TeamIconsActivity.class);
        startActivity(intent);
    }

    public void btnBack(View view) {
        finish();
    }

    public void teamCreate(View view) {
        String name = ((EditText) findViewById(R.id.id_team_name)).getText().toString();
        String home = ((EditText) findViewById(R.id.id_team_home)).getText().toString();
        String desc = ((EditText) findViewById(R.id.id_team_desc)).getText().toString();
        if (StringUtils.isNullOrEmpty(name) || StringUtils.isNullOrEmpty(home) || StringUtils.isNullOrEmpty(desc)) {
            T.show(this, "场地名称、主场、描述不可以空！");
            return;
        }
        SportsInterface sportsInterface = SportsService.createSportsService(UserConfig.ACCESS_TOKEN);
        Observable<Team> a = sportsInterface.createTeam("9", name, "iconUrl", home, 0, desc);
        a.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Team>() {
                    @Override
                    public void onCompleted() {
//                        T.show(TeamCreateActivity.this, "onCompleted");
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        T.show(TeamCreateActivity.this, "onError");
                    }

                    @Override
                    public void onNext(Team team) {
                        T.show(TeamCreateActivity.this, team.getDesc());
                        Log.i("aaa", new Gson().toJson(team));
                    }
                });
    }
}
