package com.football.yuedong.happysports.controller;

import com.football.yuedong.happysports.domain.RankingListInfo;
import com.football.yuedong.happysports.utils.AndroidUtilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyanjun on 2015/12/28.
 */
public class DataProvider {
    private static volatile DataProvider Instance = null;

    public static DataProvider getInstance() {
        DataProvider localInstance = Instance;
        if (localInstance == null) {
            synchronized (DataProvider.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new DataProvider();
                }
            }
        }
        return localInstance;
    }

    private DataProvider() {

    }

    private void loadPitches() {
        AndroidUtilities.runOnUIThread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {

                }
            }

        });
    }

    public List<RankingListInfo.ValueEntity> getRankings() {
        RankingListInfo infos = new RankingListInfo();
        List<RankingListInfo.ValueEntity> valueEntities = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            RankingListInfo.ValueEntity entity = new RankingListInfo.ValueEntity();
            entity.setIcon("http://115.159.150.158/group1/M00/00/00/CmlEFlbABfeAEbYyAAT9K76bVpI333_big.jpg?w=200&h=200");
            entity.setName("西二旗狼球队");
            entity.setAffectScore(999);
            valueEntities.add(entity);
        }
        infos.setValue(valueEntities);
        return infos.getValue();
    }
}
