package com.football.yuedong.happysports.domain;

import android.os.Parcel;

import java.util.List;

/**
 * Created by liuyanjun on 2016/3/16.
 */
public class TeamMemberRelp extends BaseResponse{

    /**
     * roleId : 1
     * teamId : 0
     * uid : 9
     */

    private List<ValueEntity> value;

    public void setValue(List<ValueEntity> value) {
        this.value = value;
    }

    public List<ValueEntity> getValue() {
        return value;
    }

    public static class ValueEntity implements android.os.Parcelable {
        private int roleId;
        private int teamId;
        private int uid;



        public void setRoleId(int roleId) {
            this.roleId = roleId;
        }

        public void setTeamId(int teamId) {
            this.teamId = teamId;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getRoleId() {
            return roleId;
        }

        public int getTeamId() {
            return teamId;
        }

        public int getUid() {
            return uid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.roleId);
            dest.writeInt(this.teamId);
            dest.writeInt(this.uid);
        }

        public ValueEntity() {
        }

        protected ValueEntity(Parcel in) {
            this.roleId = in.readInt();
            this.teamId = in.readInt();
            this.uid = in.readInt();
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

    public TeamMemberRelp() {
    }

    protected TeamMemberRelp(Parcel in) {
        super(in);
        this.value = in.createTypedArrayList(ValueEntity.CREATOR);
    }

    public static final Creator<TeamMemberRelp> CREATOR = new Creator<TeamMemberRelp>() {
        public TeamMemberRelp createFromParcel(Parcel source) {
            return new TeamMemberRelp(source);
        }

        public TeamMemberRelp[] newArray(int size) {
            return new TeamMemberRelp[size];
        }
    };
}
