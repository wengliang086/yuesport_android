package com.football.yuedong.happysports.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liuyanjun on 2015/12/14.
 */
public class User extends BaseResponse {

    public ValueEntity value;

    public User() {
    }

    protected User(Parcel in) {
        value = in.readParcelable(ValueEntity.class.getClassLoader());
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(value, i);
    }

    public static class ValueEntity implements Parcelable {
        public String username;

        public String passwd;

        public String uid;

        public String birthDate;

        public String address;

        public String location;

        public String startKickDate;

        public int customFoot = 1;

        public int tags = 0;

        public int height;

        public int weight;

        public int sex;

        public boolean joined;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.username);
            dest.writeString(this.passwd);
            dest.writeString(this.uid);
            dest.writeString(this.birthDate);
            dest.writeString(this.address);
            dest.writeString(this.location);
            dest.writeString(this.startKickDate);
            dest.writeInt(this.customFoot);
            dest.writeInt(this.tags);
            dest.writeInt(this.height);
            dest.writeInt(this.weight);
            dest.writeInt(this.sex);
            dest.writeByte(joined ? (byte) 1 : (byte) 0);
        }

        public ValueEntity() {
        }

        protected ValueEntity(Parcel in) {
            this.username = in.readString();
            this.passwd = in.readString();
            this.uid = in.readString();
            this.birthDate = in.readString();
            this.address = in.readString();
            this.location = in.readString();
            this.startKickDate = in.readString();
            this.customFoot = in.readInt();
            this.tags = in.readInt();
            this.height = in.readInt();
            this.weight = in.readInt();
            this.sex = in.readInt();
            this.joined = in.readByte() != 0;
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
}
