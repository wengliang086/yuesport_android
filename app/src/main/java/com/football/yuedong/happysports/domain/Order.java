package com.football.yuedong.happysports.domain;

import android.os.Parcel;

/**
 * Created by Administrator on 2016/6/21.
 */
public class Order extends BaseResponse {

    private String value;

    protected Order(Parcel in) {
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
