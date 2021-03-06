
package me.jordancarlson.dabeetsdeets.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DateTimeCreated {

    @SerializedName("DateTime")
    @Expose
    private String dateTime;
    @SerializedName("OffsetMinutes")
    @Expose
    private Integer offsetMinutes;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getOffsetMinutes() {
        return offsetMinutes;
    }

    public void setOffsetMinutes(Integer offsetMinutes) {
        this.offsetMinutes = offsetMinutes;
    }

}
