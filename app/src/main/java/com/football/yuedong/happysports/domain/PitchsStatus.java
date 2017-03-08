package com.football.yuedong.happysports.domain;

import android.os.Parcel;

/**
 * Created by liuyj on 2016/5/11.
 * 每一个cell的数据
 */
public class PitchsStatus  extends BaseResponse{

    public String date;

    public String status = "";

    public int cellId;

    public String pitchId;

    public String modelId;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.date);
        dest.writeString(this.status);
        dest.writeInt(this.cellId);
        dest.writeString(this.pitchId);
        dest.writeString(this.modelId);
    }

    public PitchsStatus() {
    }

    protected PitchsStatus(Parcel in) {
        super(in);
        this.date = in.readString();
        this.status = in.readString();
        this.cellId = in.readInt();
        this.pitchId = in.readString();
        this.modelId = in.readString();
    }

    public static final Creator<PitchsStatus> CREATOR = new Creator<PitchsStatus>() {
        @Override
        public PitchsStatus createFromParcel(Parcel source) {
            return new PitchsStatus(source);
        }

        @Override
        public PitchsStatus[] newArray(int size) {
            return new PitchsStatus[size];
        }
    };
}
