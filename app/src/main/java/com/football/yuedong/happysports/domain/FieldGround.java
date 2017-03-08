package com.football.yuedong.happysports.domain;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyj on 2016/4/26.
 */
public class FieldGround extends BaseResponse{


    /**
     * address : 3
     * commissionRate : 3
     * countyId : 1
     * id : 3
     * name : 3
     * phone : 3
     */

    private List<ValueEntity> value;

    public List<ValueEntity> getValue() {
        return value;
    }

    public void setValue(List<ValueEntity> value) {
        this.value = value;
    }

    public static class ValueEntity implements android.os.Parcelable {
        private String address;
        private int commissionRate;
        private int countyId;
        private int id;
        private String name;
        private String phone;
        private String url;
        private float base_price;
        private int distance;



        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getCommissionRate() {
            return commissionRate;
        }

        public void setCommissionRate(int commissionRate) {
            this.commissionRate = commissionRate;
        }

        public int getCountyId() {
            return countyId;
        }

        public void setCountyId(int countyId) {
            this.countyId = countyId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public float getBase_price() {
            return base_price;
        }

        public void setBase_price(float base_price) {
            this.base_price = base_price;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public ValueEntity() {

        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.address);
            dest.writeInt(this.commissionRate);
            dest.writeInt(this.countyId);
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.phone);
            dest.writeString(this.url);
            dest.writeFloat(this.base_price);
            dest.writeInt(this.distance);
        }

        protected ValueEntity(Parcel in) {
            this.address = in.readString();
            this.commissionRate = in.readInt();
            this.countyId = in.readInt();
            this.id = in.readInt();
            this.name = in.readString();
            this.phone = in.readString();
            this.url = in.readString();
            this.base_price = in.readFloat();
            this.distance = in.readInt();
        }

        public static final Creator<ValueEntity> CREATOR = new Creator<ValueEntity>() {
            @Override
            public ValueEntity createFromParcel(Parcel source) {
                return new ValueEntity(source);
            }

            @Override
            public ValueEntity[] newArray(int size) {
                return new ValueEntity[size];
            }
        };
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeList(this.value);
    }

    public FieldGround() {
    }

    protected FieldGround(Parcel in) {
        super(in);
        this.value = new ArrayList<ValueEntity>();
        in.readList(this.value, ValueEntity.class.getClassLoader());
    }

    public static final Creator<FieldGround> CREATOR = new Creator<FieldGround>() {
        @Override
        public FieldGround createFromParcel(Parcel source) {
            return new FieldGround(source);
        }

        @Override
        public FieldGround[] newArray(int size) {
            return new FieldGround[size];
        }
    };
}
