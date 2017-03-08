package com.football.yuedong.happysports.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liuyanjun on 2016/3/11.
 */
public class BaseResponse implements Parcelable {

    private String code;
    private String desc;
    private String group;

    public boolean isSuccess() {
        return "SUCCESS".endsWith(code);
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public String getGroup() {
        return group;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public BaseResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.desc);
        dest.writeString(this.group);
    }

    protected BaseResponse(Parcel in) {
        this.code = in.readString();
        this.desc = in.readString();
        this.group = in.readString();
    }

}
