package com.hackerkernel.forecast.Pojo;

/**
 * Created by vinayak tyagi on 08-04-2018.
 */

public class DayPojo {
    String date;
    int tempMax;
    int tempMin;
    String sunset;
    String sunrise;
    int index;
    int uv;

    public DayPojo(String date, int tempMax, int tempMin, String sunset, String sunrise,int index, int uv) {
        this.date = date;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.sunset = sunset;
        this.sunrise = sunrise;
        this.index = index;
        this.uv = uv;
    }

    public int getIndex() {
        return index;
    }

    public String getDate() {
        return date;
    }

    public int getTempMax() {
        return tempMax;
    }

    public int getTempMin() {
        return tempMin;
    }

    public String getSunset() {
        return sunset;
    }

    public String getSunrise() {
        return sunrise;
    }

    public int getUv() {
        return uv;
    }
}
