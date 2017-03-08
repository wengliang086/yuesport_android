package com.football.yuedong.happysports.domain;

import android.os.Parcel;

import java.util.List;

/**
 * Created by liuyj on 2016/4/26.
 */
public class PitchInfo extends BaseResponse {


    /**
     * filedId : 1
     * groupId : 11
     * id : 6
     * name : 场地2
     * note : note22
     * scaleId : 0
     * statusId : 0
     */

    private List<ValueEntity> value;

    public List<ValueEntity> getValue() {
        return value;
    }

    public void setValue(List<ValueEntity> value) {
        this.value = value;
    }

    public static class ValueEntity implements android.os.Parcelable {
        private int filedId;
        private int groupId;
        private int id;
        private String name;
        private String note;
        private int scaleId;
        private int statusId;

        public int getFiledId() {
            return filedId;
        }

        public void setFiledId(int filedId) {
            this.filedId = filedId;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
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

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public int getScaleId() {
            return scaleId;
        }

        public void setScaleId(int scaleId) {
            this.scaleId = scaleId;
        }

        public int getStatusId() {
            return statusId;
        }

        public void setStatusId(int statusId) {
            this.statusId = statusId;
        }


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.filedId);
            dest.writeInt(this.groupId);
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.note);
            dest.writeInt(this.scaleId);
            dest.writeInt(this.statusId);
        }

        public ValueEntity() {
        }

        protected ValueEntity(Parcel in) {
            this.filedId = in.readInt();
            this.groupId = in.readInt();
            this.id = in.readInt();
            this.name = in.readString();
            this.note = in.readString();
            this.scaleId = in.readInt();
            this.statusId = in.readInt();
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
        dest.writeTypedList(value);
    }

    public PitchInfo() {
    }

    protected PitchInfo(Parcel in) {
        super(in);
        this.value = in.createTypedArrayList(ValueEntity.CREATOR);
    }

    public static final Creator<PitchInfo> CREATOR = new Creator<PitchInfo>() {
        @Override
        public PitchInfo createFromParcel(Parcel source) {
            return new PitchInfo(source);
        }

        @Override
        public PitchInfo[] newArray(int size) {
            return new PitchInfo[size];
        }
    };
}
