package com.football.yuedong.happysports.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liuyj on 2016/4/14.
 */
public class BlockCell implements Parcelable {

    private String timeId;

    private int start = 0;

    private int end = 0;

    private String blockId;

    private STATUS  status;

    private String pitchId;


    private String block_name;


    public static enum STATUS{
        IDLE, USED, ABORT;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public String getPitchId() {
        return pitchId;
    }

    public void setPitchId(String pitchId) {
        this.pitchId = pitchId;
    }

    public String getBlock_name() {
        return block_name;
    }

    public void setBlock_name(String block_name) {
        this.block_name = block_name;
    }

    public BlockCell(int cellId) {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.timeId);
        dest.writeInt(this.start);
        dest.writeInt(this.end);
        dest.writeString(this.blockId);
        dest.writeInt(this.status == null ? -1 : this.status.ordinal());
        dest.writeString(this.pitchId);
        dest.writeString(this.block_name);
    }

    protected BlockCell(Parcel in) {
        this.timeId = in.readString();
        this.start = in.readInt();
        this.end = in.readInt();
        this.blockId = in.readString();
        int tmpStatus = in.readInt();
        this.status = tmpStatus == -1 ? null : STATUS.values()[tmpStatus];
        this.pitchId = in.readString();
        this.block_name = in.readString();
    }

    public static final Creator<BlockCell> CREATOR = new Creator<BlockCell>() {
        @Override
        public BlockCell createFromParcel(Parcel source) {
            return new BlockCell(source);
        }

        @Override
        public BlockCell[] newArray(int size) {
            return new BlockCell[size];
        }
    };
}
