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
@SmartTable(name = "泄洪报警")
public class HistoryData implements Parcelable {
    private String recordId;

    @SmartColumn(id = 1,name = "设备id",align = Paint.Align.CENTER)
    private String devId;

    @SmartColumn(id = 2,name = "安装高度",align = Paint.Align.CENTER)
    private String bmark;

    @SmartColumn(id = 3,name = "上限水位",align = Paint.Align.CENTER)
    private String hlimit;

    @SmartColumn(id = 4,name = "下限水位",align = Paint.Align.CENTER)
    private String llimit;

    @SmartColumn(id = 5,name = "采集时间间隔",align = Paint.Align.CENTER)
    private String collectInterval;

    @SmartColumn(id = 6,name = "空高水位",align = Paint.Align.CENTER)
    private String alevel;

    @SmartColumn(id = 7,name = "上报水位",align = Paint.Align.CENTER)
    private String uplevel;

    @SmartColumn(id = 8,name = "2G信号质量",align = Paint.Align.CENTER)
    private String gprs;

    @SmartColumn(id = 9,name = "电池电压",align = Paint.Align.CENTER)
    private String vtage;

    @SmartColumn(id = 10,name = "超声波水位状态",align = Paint.Align.CENTER)
    private String ultstate;

    @SmartColumn(id = 11,name = "水位报警",align = Paint.Align.CENTER)
    private String waterlevelAlarm;

    @SmartColumn(id = 12,name = "电压报警",align = Paint.Align.CENTER)
    private String voltageAlarm;

    @SmartColumn(id = 13,name = "雨量",align = Paint.Align.CENTER)
    private String rainFall;

    @SmartColumn(id = 14,name = "累计雨量",align = Paint.Align.CENTER)
    private String rainFallTotal;

    @SmartColumn(id = 15,name = "风速",align = Paint.Align.CENTER)
    private String windSpeed;

    @SmartColumn(id = 16,name = "风向",align = Paint.Align.CENTER)
    private String windDirection;

    @SmartColumn(id = 17,name = "时间",align = Paint.Align.CENTER)
    private Date time;

    protected HistoryData(Parcel in) {
        recordId = in.readString();
        devId = in.readString();
        bmark = in.readString();
        hlimit = in.readString();
        llimit = in.readString();
        collectInterval = in.readString();
        alevel = in.readString();
        uplevel = in.readString();
        gprs = in.readString();
        vtage = in.readString();
        ultstate = in.readString();
        waterlevelAlarm = in.readString();
        voltageAlarm = in.readString();
        rainFall = in.readString();
        rainFallTotal = in.readString();
        windSpeed = in.readString();
        windDirection = in.readString();
    }

    public static final Creator<HistoryData> CREATOR = new Creator<HistoryData>() {
        @Override
        public HistoryData createFromParcel(Parcel in) {
            return new HistoryData(in);
        }

        @Override
        public HistoryData[] newArray(int size) {
            return new HistoryData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recordId);
        dest.writeString(devId);
        dest.writeString(bmark);
        dest.writeString(hlimit);
        dest.writeString(llimit);
        dest.writeString(collectInterval);
        dest.writeString(alevel);
        dest.writeString(uplevel);
        dest.writeString(gprs);
        dest.writeString(vtage);
        dest.writeString(ultstate);
        dest.writeString(waterlevelAlarm);
        dest.writeString(voltageAlarm);
        dest.writeString(rainFall);
        dest.writeString(rainFallTotal);
        dest.writeString(windSpeed);
        dest.writeString(windDirection);
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public void setBmark(String bmark) {
        this.bmark = bmark;
    }

    public void setHlimit(String hlimit) {
        this.hlimit = hlimit;
    }

    public void setLlimit(String llimit) {
        this.llimit = llimit;
    }

    public void setCollectInterval(String collectInterval) {
        this.collectInterval = collectInterval;
    }

    public void setAlevel(String alevel) {
        this.alevel = alevel;
    }

    public void setUplevel(String uplevel) {
        this.uplevel = uplevel;
    }

    public void setGprs(String gprs) {
        this.gprs = gprs;
    }

    public void setVtage(String vtage) {
        this.vtage = vtage;
    }

    public void setUltstate(String ultstate) {
        this.ultstate = ultstate;
    }

    public void setWaterlevelAlarm(String waterlevelAlarm) {
        this.waterlevelAlarm = waterlevelAlarm;
    }

    public void setVoltageAlarm(String voltageAlarm) {
        this.voltageAlarm = voltageAlarm;
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

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getDevId() {
        return devId;
    }

    public String getBmark() {
        return bmark;
    }

    public String getHlimit() {
        return hlimit;
    }

    public String getLlimit() {
        return llimit;
    }

    public String getCollectInterval() {
        return collectInterval;
    }

    public String getAlevel() {
        return alevel;
    }

    public String getUplevel() {
        return uplevel;
    }

    public String getGprs() {
        return gprs;
    }

    public String getVtage() {
        return vtage;
    }

    public String getUltstate() {
        return ultstate;
    }

    public String getWaterlevelAlarm() {
        return waterlevelAlarm;
    }

    public String getVoltageAlarm() {
        return voltageAlarm;
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

    public Date getTime() {
        return time;
    }
}
