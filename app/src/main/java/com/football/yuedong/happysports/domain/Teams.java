package com.football.yuedong.happysports.domain;

import android.os.Parcel;

import java.util.List;

/**
 * Created by liuyj on 2016/2/20.
 */
public class Teams extends BaseResponse{

    /**
     * affectScore : 1
     * createrId : 9
     * homeCourt : home
     * icon : icon
     * id : 0
     * location : location
     * name : teamName
     * selfTags : tags
     */

    private List<ValueEntity> value;

    public void setValue(List<ValueEntity> value) {
        this.value = value;
    }

    public List<ValueEntity> getValue() {
        return value;
    }

    public static class ValueEntity implements android.os.Parcelable {

        private int affectScore;
        private int createrId;
        private String homeCourt;
        private String icon;
        private int id;
        private String location;
        private String name;
        private String selfTags;

        public void setAffectScore(int affectScore) {
            this.affectScore = affectScore;
        }

        public void setCreaterId(int createrId) {
            this.createrId = createrId;
        }

        public void setHomeCourt(String homeCourt) {
            this.homeCourt = homeCourt;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSelfTags(String selfTags) {
            this.selfTags = selfTags;
        }

        public int getAffectScore() {
            return affectScore;
        }

        public int getCreaterId() {
            return createrId;
        }

        public String getHomeCourt() {
            return homeCourt;
        }

        public String getIcon() {
            return icon;
        }

        public int getId() {
            return id;
        }

        public String getLocation() {
            return location;
        }

        public String getName() {
            return name;
        }

        public String getSelfTags() {
            return selfTags;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.affectScore);
            dest.writeInt(this.createrId);
            dest.writeString(this.homeCourt);
            dest.writeString(this.icon);
            dest.writeInt(this.id);
            dest.writeString(this.location);
            dest.writeString(this.name);
            dest.writeString(this.selfTags);
        }

        public ValueEntity() {
        }

        protected ValueEntity(Parcel in) {
            this.affectScore = in.readInt();
            this.createrId = in.readInt();
            this.homeCourt = in.readString();
            this.icon = in.readString();
            this.id = in.readInt();
            this.location = in.readString();
            this.name = in.readString();
            this.selfTags = in.readString();
        }

        public static final Creator<ValueEntity> CREATOR = new Creator<ValueEntity>() {
            public ValueEntity createFromParcel(Parcel source) {
                return new ValueEntity(source);
            }

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
        dest.writeTypedList(value);
    }

    public Teams() {
    }

    protected Teams(Parcel in) {
        super(in);
        this.value = in.createTypedArrayList(ValueEntity.CREATOR);
    }

    public static final Creator<Teams> CREATOR = new Creator<Teams>() {
        public Teams createFromParcel(Parcel source) {
            return new Teams(source);
        }

        public Teams[] newArray(int size) {
            return new Teams[size];
        }
    };
}
