package com.hackerkernel.forecast.Pojo;

/**
 * Created by vinayak tyagi on 08-04-2018.
 */

public class HourlyPojo {
     String time;
     int temp;
     double pricip;
     int humid;
     int dew;
     String summary;
     int icon;

    public HourlyPojo(String time, int temp, double pricip, int humid, int dew, String summary, int icon) {
        this.time = time;
        this.temp = temp;
        this.pricip = pricip;
        this.humid = humid;
        this.dew = dew;
        this.summary = summary;
        this.icon = icon;
    }

    public String getTime() {
        return time;
    }

    public int getTemp() {
        return temp;
    }

    public double getPricip() {
        return pricip;
    }

    public int getHumid() {
        return humid;
    }

    public int getDew() {
        return dew;
    }

    public String getSummary() {
        return summary;
    }

    public int getIcon() {
        return icon;
    }
}
