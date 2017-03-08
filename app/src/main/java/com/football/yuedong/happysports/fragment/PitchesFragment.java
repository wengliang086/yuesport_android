package com.football.yuedong.happysports.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.football.yuedong.happysports.R;
import com.football.yuedong.happysports.adapter.PitchesAdapter;
import com.football.yuedong.happysports.domain.FieldGround;
import com.football.yuedong.happysports.ui.FieldDetailActivity;
import com.football.yuedong.happysports.views.LineDividerItemDecoration;
import com.football.yuedong.happysports.views.RecyclerItemClickListener;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import jp.wasabeef.recyclerview.animators.FadeInAnimator;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liuyanjun on 2015/12/15.
 */
@EFragment(R.layout.pitches_layout)
public class PitchesFragment extends BaseFragment{

    private static final String TAG = "PitchesFragment";

    @ViewById(R.id.pitch_list)
    RecyclerView recyclerView;

    PitchesAdapter pitchesAdapter;

    private MaterialSearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pitches_layout, null);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_pitches);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
        searchView = (MaterialSearchView) view.findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.color_cursor_white);
        return view;
    }


    @AfterViews
    public void initView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new FadeInAnimator());
        recyclerView.addItemDecoration(new LineDividerItemDecoration(getActivity()));

        pitchesAdapter = new PitchesAdapter(getActivity());

        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(pitchesAdapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        recyclerView.setAdapter(scaleAdapter);

        pitchesAdapter.setOnRecyclerItemClickListener(new RecyclerItemClickListener() {

            @Override
            public void onItemClick(View view, FieldGround.ValueEntity entity) {
                getActivity().startActivity(new Intent(getActivity(), FieldDetailActivity.class));
            }
        });

        loadPitches();
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick  = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(item.getItemId() == R.id.action_search){
               searchView.showSearch();
            }else {

            }
            return false;
        }
    };


    private void loadPitches(){
        _subscriptions.add(sportsInterface.queryFields(0, 0, 10)
                       .subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new Observer<FieldGround>() {
                           @Override
                           public void onCompleted() {
                           }

                           @Override
                           public void onError(Throwable e) {

                           }

                           @Override
                           public void onNext(FieldGround fieldGround) {
                               pitchesAdapter.addSubPitchesList(fieldGround.getValue());
                           }
                       }));
    }

}
