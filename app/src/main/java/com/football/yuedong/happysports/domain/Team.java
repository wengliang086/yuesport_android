package com.football.yuedong.happysports.domain;

import android.os.Parcel;

/**
 * Created by Administrator on 2016/4/22.
 */
public class Team extends BaseResponse {

    private Teams.ValueEntity value;

    public Teams.ValueEntity getValue() {
        return value;
    }

    public void setValue(Teams.ValueEntity value) {
        this.value = value;
    }

    protected Team(Parcel in) {
        value = in.readParcelable(Teams.ValueEntity.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(value, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };
}
