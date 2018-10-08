
package me.jordancarlson.dabeetsdeets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reading {

    @SerializedName("DT")
    @Expose
    private String dT;
    @SerializedName("ST")
    @Expose
    private String sT;
    @SerializedName("Trend")
    @Expose
    private Integer trend;
    @SerializedName("Value")
    @Expose
    private Integer value;
    @SerializedName("WT")
    @Expose
    private String wT;

    public String getDT() {
        return dT;
    }

    public void setDT(String dT) {
        this.dT = dT;
    }

    public String getST() {
        return sT;
    }

    public void setST(String sT) {
        this.sT = sT;
    }

    public Integer getTrend() {
        return trend;
    }

    public void setTrend(Integer trend) {
        this.trend = trend;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getWT() {
        return wT;
    }

    public void setWT(String wT) {
        this.wT = wT;
    }

}
