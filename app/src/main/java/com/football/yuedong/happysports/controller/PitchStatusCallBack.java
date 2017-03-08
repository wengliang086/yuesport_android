package com.football.yuedong.happysports.controller;

import com.football.yuedong.happysports.domain.PitchsStatus;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.zhy.http.okhttp.callback.Callback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by liuyj on 2016/5/11.
 */
public class PitchStatusCallBack extends Callback<Map<String, ArrayList<PitchsStatus>>> {
    @Override
    public Map<String, ArrayList<PitchsStatus>> parseNetworkResponse(Response response) throws Exception {
        Map<String, ArrayList<PitchsStatus>> map_pitch_status = new HashMap<>();
        String string = response.body().string();
        Map map_value = new Gson().fromJson(string, Map.class);
        ArrayList<Object> list = (ArrayList<Object>) map_value.get("value");
        for(Object obj: list){
            Map map = (Map)obj;
            String name = (String) map.get("name");
            ArrayList<PitchsStatus> pitchsStatuses;
            if(map_pitch_status.containsKey(name)){
                pitchsStatuses = map_pitch_status.get(name);
            }else {
                pitchsStatuses = new ArrayList<>();
                map_pitch_status.put(name,pitchsStatuses);
            }
            for(Object obj_key : map.keySet()){ //linktreemap; 不同时间点
                LinkedTreeMap  v_map = (LinkedTreeMap) map.get(obj_key);
                PitchsStatus status = new PitchsStatus();
                status.date = (String) obj_key;
                status.modelId = (String) v_map.get("id");
                status.pitchId = name;
                status.status = (String) v_map.get("status");
                pitchsStatuses.add(status);
            }
        }
        return map_pitch_status;
    }

    @Override
    public void onError(Call call, Exception e) {

    }

    @Override
    public void onResponse(Map<String, ArrayList<PitchsStatus>> pitchsStatus_map) {

    }
}
