package com.analysis.wisdomtraffic.been;

import android.os.Parcel;
import android.os.Parcelable;

public class AlarmMessage implements Parcelable {
    private Integer id;
    private String eventtype;
    private String monitorypoint;
    private String channel;
    private String longitude;
    private String latitude;
    private String starttime;
    private String endtime;
    private String serialnum;
    private Integer callState;
    private Integer orderState;
    private Integer handleState;
    private Integer finishState;

    public AlarmMessage() {
    }


    protected AlarmMessage(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        eventtype = in.readString();
        monitorypoint = in.readString();
        channel = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        starttime = in.readString();
        endtime = in.readString();
        serialnum = in.readString();
        if (in.readByte() == 0) {
            callState = null;
        } else {
            callState = in.readInt();
        }
        if (in.readByte() == 0) {
            orderState = null;
        } else {
            orderState = in.readInt();
        }
        if (in.readByte() == 0) {
            handleState = null;
        } else {
            handleState = in.readInt();
        }
        if (in.readByte() == 0) {
            finishState = null;
        } else {
            finishState = in.readInt();
        }
    }

    public static final Creator<AlarmMessage> CREATOR = new Creator<AlarmMessage>() {
        @Override
        public AlarmMessage createFromParcel(Parcel in) {
            return new AlarmMessage(in);
        }

        @Override
        public AlarmMessage[] newArray(int size) {
            return new AlarmMessage[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(eventtype);
        dest.writeString(monitorypoint);
        dest.writeString(channel);
        dest.writeString(longitude);
        dest.writeString(latitude);
        dest.writeString(starttime);
        dest.writeString(endtime);
        dest.writeString(serialnum);
        if (callState == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(callState);
        }
        if (orderState == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(orderState);
        }
        if (handleState == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(handleState);
        }
        if (finishState == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(finishState);
        }
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setEventtype(String eventtype) {
        this.eventtype = eventtype;
    }

    public void setMonitorypoint(String monitorypoint) {
        this.monitorypoint = monitorypoint;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public void setSerialnum(String serialnum) {
        this.serialnum = serialnum;
    }

    public void setCallState(Integer callState) {
        this.callState = callState;
    }

    public void setOrderState(Integer orderState) {
        this.orderState = orderState;
    }

    public void setHandleState(Integer handleState) {
        this.handleState = handleState;
    }

    public void setFinishState(Integer finishState) {
        this.finishState = finishState;
    }

    public Integer getId() {
        return id;
    }

    public String getEventtype() {
        return eventtype;
    }

    public String getMonitorypoint() {
        return monitorypoint;
    }

    public String getChannel() {
        return channel;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getStarttime() {
        return starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getSerialnum() {
        return serialnum;
    }

    public Integer getCallState() {
        return callState;
    }

    public Integer getOrderState() {
        return orderState;
    }

    public Integer getHandleState() {
        return handleState;
    }

    public Integer getFinishState() {
        return finishState;
    }
}
