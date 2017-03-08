package com.football.yuedong.happysports.domain;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuyj on 2016/3/12.
 */
public class MemberRoleRep extends BaseResponse {



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

    public static class ValueEntity {
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


    public MemberRoleRep() {
    }

    protected MemberRoleRep(Parcel in) {
        super(in);
        this.value = new ArrayList<ValueEntity>();
        in.readList(this.value, List.class.getClassLoader());
    }

    public static final Creator<MemberRoleRep> CREATOR = new Creator<MemberRoleRep>() {
        public MemberRoleRep createFromParcel(Parcel source) {
            return new MemberRoleRep(source);
        }

        public MemberRoleRep[] newArray(int size) {
            return new MemberRoleRep[size];
        }
    };
}
