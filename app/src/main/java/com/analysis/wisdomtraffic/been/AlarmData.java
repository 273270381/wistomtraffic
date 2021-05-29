package com.analysis.wisdomtraffic.been;

import android.graphics.Paint;
import android.os.Parcel;
import android.os.Parcelable;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

/**
 * @Author hejunfeng
 * @Date 13:51 2021/5/27 0027
 * @Description com.analysis.wisdomtraffic.been
 **/
@SmartTable(name = "泄洪报警")
public class AlarmData implements Parcelable {

    @SmartColumn(id = 1,name = "站点",align = Paint.Align.CENTER)
    private String damName;

    @SmartColumn(id = 2,name = "采集时间",align = Paint.Align.CENTER)
    private String time;

    @SmartColumn(id = 3,name = "风速(m/s)",align = Paint.Align.CENTER)
    private String windSpeed;

    @SmartColumn(id = 4,name = "风速预警",align = Paint.Align.CENTER)
    private String windAlarmLever;

    @SmartColumn(id = 5,name = "雨量(mm)",align = Paint.Align.CENTER)
    private String rainFall;

    @SmartColumn(id = 6,name = "雨量预警",align = Paint.Align.CENTER)
    private String rainAlarmLever;

    @SmartColumn(id = 7,name = "水位(m)",align = Paint.Align.CENTER)
    private String waterLever;

    @SmartColumn(id = 8,name = "水位预警",align = Paint.Align.CENTER)
    private String waterAlarmLever;

    @SmartColumn(id = 9,name = "水量(m3)",align = Paint.Align.CENTER)
    private String volumelever;

    @SmartColumn(id = 10,name = "水量预警",align = Paint.Align.CENTER)
    private String volumeAlarmlever;

    protected AlarmData(Parcel in) {
        damName = in.readString();
        time = in.readString();
        windSpeed = in.readString();
        windAlarmLever = in.readString();
        rainFall = in.readString();
        rainAlarmLever = in.readString();
        waterLever = in.readString();
        waterAlarmLever = in.readString();
        volumelever = in.readString();
        volumeAlarmlever = in.readString();
    }

    public static final Creator<AlarmData> CREATOR = new Creator<AlarmData>() {
        @Override
        public AlarmData createFromParcel(Parcel in) {
            return new AlarmData(in);
        }

        @Override
        public AlarmData[] newArray(int size) {
            return new AlarmData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public void setDamName(String damName) {
        this.damName = damName;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public void setWindAlarmLever(String windAlarmLever) {
        this.windAlarmLever = windAlarmLever;
    }

    public void setRainFall(String rainFall) {
        this.rainFall = rainFall;
    }

    public void setRainAlarmLever(String rainAlarmLever) {
        this.rainAlarmLever = rainAlarmLever;
    }

    public void setWaterLever(String waterLever) {
        this.waterLever = waterLever;
    }

    public void setWaterAlarmLever(String waterAlarmLever) {
        this.waterAlarmLever = waterAlarmLever;
    }

    public void setVolumelever(String volumelever) {
        this.volumelever = volumelever;
    }

    public void setVolumeAlarmlever(String volumeAlarmlever) {
        this.volumeAlarmlever = volumeAlarmlever;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(damName);
        dest.writeString(time);
        dest.writeString(windSpeed);
        dest.writeString(windAlarmLever);
        dest.writeString(rainFall);
        dest.writeString(rainAlarmLever);
        dest.writeString(waterLever);
        dest.writeString(waterAlarmLever);
        dest.writeString(volumelever);
        dest.writeString(volumeAlarmlever);
    }

    public String getDamName() {
        return damName;
    }

    public String getTime() {
        return time;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWindAlarmLever() {
        return windAlarmLever;
    }

    public String getRainFall() {
        return rainFall;
    }

    public String getRainAlarmLever() {
        return rainAlarmLever;
    }

    public String getWaterLever() {
        return waterLever;
    }

    public String getWaterAlarmLever() {
        return waterAlarmLever;
    }

    public String getVolumelever() {
        return volumelever;
    }

    public String getVolumeAlarmlever() {
        return volumeAlarmlever;
    }
}
