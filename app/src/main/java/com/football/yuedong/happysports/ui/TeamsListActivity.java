package com.football.yuedong.happysports.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.adapter.PitchesAdapter;
import com.football.yuedong.happysports.adapter.TeamsListAdapter;
import com.football.yuedong.happysports.domain.FieldGround;
import com.football.yuedong.happysports.domain.Teams;
import com.football.yuedong.happysports.service.SportsInterface;
import com.football.yuedong.happysports.service.SportsService;
import com.football.yuedong.happysports.user.UserConfig;
import com.football.yuedong.happysports.utils.RxUtils;
import com.football.yuedong.happysports.views.LineDividerItemDecoration;
import com.football.yuedong.happysports.views.RecyclerItemClickListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by liuyj on 2016/4/11.
 */
public class TeamsListActivity extends AppCompatActivity{

    RecyclerView recyclerView;

    Toolbar toolbar;

    MaterialSearchView searchView;

    TeamsListAdapter  teamsListAdapter;

    private CompositeSubscription _subscriptions = new CompositeSubscription();

    private SportsInterface sportsInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sportsInterface = SportsService.createSportsService(UserConfig.ACCESS_TOKEN);
        setContentView(R.layout.activity_teamlist_layout);
        recyclerView = (RecyclerView) findViewById(R.id.teams_list);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("");
            setSupportActionBar(toolbar);
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                                               @Override
                                               public boolean onMenuItemClick(MenuItem item) {
                                                   switch (item.getItemId()){
                                                       case R.id.action_search:
                                                           break;
                                                       case R.id.action_create:
                                                           startActivity(new Intent(TeamsListActivity.this, TeamCreateActivity.class));
                                                   }
                                                   return true;
                                               }
                                           }
                                         );

        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setEllipsize(true);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
            }

            @Override
            public void onSearchViewClosed() {
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new FadeInAnimator());
        recyclerView.addItemDecoration(new LineDividerItemDecoration(this));
        teamsListAdapter = new TeamsListAdapter(this);

        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(teamsListAdapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        recyclerView.setAdapter(scaleAdapter);

        teamsListAdapter.setOnRecyclerItemClickListener(new RecyclerItemClickListener() {

            @Override
            public void onItemClick(View view, FieldGround.ValueEntity entity) {

            }
        });
        queryTeamsData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        RxUtils.unsubscribeIfNotNull(_subscriptions);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_teams, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return  true;
    }


    @Override
    public void onBackPressed() {
        if(searchView.isSearchOpen()){
            searchView.closeSearch();
        }else{
            super.onBackPressed();
        }
    }

    private void queryTeamsData(){
        _subscriptions.add(sportsInterface.getTeams(0,10)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Teams>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Teams teams) {
                        if(teamsListAdapter == null){
                            teamsListAdapter = new TeamsListAdapter(TeamsListActivity.this);
                        }
                        teamsListAdapter.addSubTeamsList(teams.getValue());
                    }
                }));
    }
}
