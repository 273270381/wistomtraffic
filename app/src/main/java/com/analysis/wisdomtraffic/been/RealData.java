package com.analysis.wisdomtraffic.been;

import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

import java.util.Date;

/**
 * @Author hejunfeng
 * @Date 15:54 2021/5/24 0024
 * @Description com.analysis.wisdomtraffic.been
 **/
@SmartTable(name = "实时数据")
public class RealData implements Parcelable {

    @SmartColumn(id = 1,name = "设备id",align = Paint.Align.CENTER)
    private String devId;

    @SmartColumn(id = 2,name = "站点",align = Paint.Align.CENTER)
    private String damName;

    private String devType;

    @SmartColumn(id = 3,name = "设备名称",align = Paint.Align.CENTER)
    private String devDesc;

    @SmartColumn(id = 4,name = "超声波水位状态",align = Paint.Align.CENTER)
    private String ultstate;

    @SmartColumn(id = 5,name = "电池电压",align = Paint.Align.CENTER)
    private String voltageAlarm;

    @SmartColumn(id = 6,name = "上报水位(m)",align = Paint.Align.CENTER)
    private String uplevel;

    @SmartColumn(id = 7,name = "水位报警",align = Paint.Align.CENTER)
    private String waterlevelAlarm;

    @SmartColumn(id = 8,name = "瞬时雨量(mm/min)",align = Paint.Align.CENTER)
    private String rainFall;

    @SmartColumn(id = 9,name = "累计雨量(mm)",align = Paint.Align.CENTER)
    private String rainFallTotal;

    @SmartColumn(id = 10,name = "风速(m/s)",align = Paint.Align.CENTER)
    private String windSpeed;

    @SmartColumn(id = 11,name = "风向",align = Paint.Align.CENTER)
    private String windDirection;


    protected RealData(Parcel in) {
        devId = in.readString();
        damName = in.readString();
        devType = in.readString();
        devDesc = in.readString();
        ultstate = in.readString();
        voltageAlarm = in.readString();
        uplevel = in.readString();
        waterlevelAlarm = in.readString();
        rainFall = in.readString();
        rainFallTotal = in.readString();
        windSpeed = in.readString();
        windDirection = in.readString();
    }

    public static final Creator<RealData> CREATOR = new Creator<RealData>() {
        @Override
        public RealData createFromParcel(Parcel in) {
            return new RealData(in);
        }

        @Override
        public RealData[] newArray(int size) {
            return new RealData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(devId);
        dest.writeString(damName);
        dest.writeString(devType);
        dest.writeString(devDesc);
        dest.writeString(ultstate);
        dest.writeString(voltageAlarm);
        dest.writeString(uplevel);
        dest.writeString(waterlevelAlarm);
        dest.writeString(rainFall);
        dest.writeString(rainFallTotal);
        dest.writeString(windSpeed);
        dest.writeString(windDirection);
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public void setDamName(String damName) {
        this.damName = damName;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }

    public void setDevDesc(String devDesc) {
        this.devDesc = devDesc;
    }

    public void setUltstate(String ultstate) {
        this.ultstate = ultstate;
    }

    public void setVoltageAlarm(String voltageAlarm) {
        this.voltageAlarm = voltageAlarm;
    }

    public void setUplevel(String uplevel) {
        this.uplevel = uplevel;
    }

    public void setWaterlevelAlarm(String waterlevelAlarm) {
        this.waterlevelAlarm = waterlevelAlarm;
    }

    public void setRainFall(String rainFall) {
        this.rainFall = rainFall;
    }

    public void setRainFallTotal(String rainFallTotal) {
        this.rainFallTotal = rainFallTotal;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getDevId() {
        return devId;
    }

    public String getDamName() {
        return damName;
    }

    public String getDevType() {
        return devType;
    }

    public String getDevDesc() {
        return devDesc;
    }

    public String getUltstate() {
        return ultstate;
    }

    public String getVoltageAlarm() {
        return voltageAlarm;
    }

    public String getUplevel() {
        return uplevel;
    }

    public String getWaterlevelAlarm() {
        return waterlevelAlarm;
    }

    public String getRainFall() {
        return rainFall;
    }

    public String getRainFallTotal() {
        return rainFallTotal;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }
}
