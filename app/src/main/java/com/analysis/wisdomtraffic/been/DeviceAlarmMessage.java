package com.analysis.wisdomtraffic.been;

import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

/**
 * @Author hejunfeng
 * @Date 16:06 2021/5/22 0022
 * @Description com.analysis.wisdomtraffic.been
 **/
@SmartTable(name = "设备告警信息")
public class DeviceAlarmMessage  implements Parcelable {
    private String recordId;
    @SmartColumn(id = 1,name = "设备名称",align = Paint.Align.CENTER)
    private String devName;
    @SmartColumn(id = 2,name = "设备编号",align = Paint.Align.CENTER)
    private String devId;
    @SmartColumn(id = 3,name = "报警类型",align = Paint.Align.CENTER)
    private String warringType;
    @SmartColumn(id = 4,name = "时间",align = Paint.Align.CENTER)
    private String dateTime;
    private String cmd;


    protected DeviceAlarmMessage(Parcel in) {
        recordId = in.readString();
        devName = in.readString();
        devId = in.readString();
        warringType = in.readString();
        dateTime = in.readString();
        cmd = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recordId);
        dest.writeString(devName);
        dest.writeString(devId);
        dest.writeString(warringType);
        dest.writeString(dateTime);
        dest.writeString(cmd);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DeviceAlarmMessage> CREATOR = new Creator<DeviceAlarmMessage>() {
        @Override
        public DeviceAlarmMessage createFromParcel(Parcel in) {
            return new DeviceAlarmMessage(in);
        }

        @Override
        public DeviceAlarmMessage[] newArray(int size) {
            return new DeviceAlarmMessage[size];
        }
    };

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevName() {
        return devName;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }



    public void setDevId(String devId) {
        this.devId = devId;
    }

    public void setWarringType(String warringType) {
        this.warringType = warringType;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getRecordId() {
        return recordId;
    }


    public String getDevId() {
        return devId;
    }

    public String getWarringType() {
        return warringType;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getCmd() {
        return cmd;
    }


}
