package com.hackerkernel.forecast.Pojo;

/**
 * Created by vinayak tyagi on 08-04-2018.
 */

public class CurrentPojo {

    private static String time;
    private static String summary;
    private static String location;
    private static int temp;
    private static double humidity;
    private static double pricip;
    private static int icon_id;

    public static String getTime() {
        return time;
    }

    public static void setTime(String time) {
        CurrentPojo.time = time;
    }

    public static String getSummary() {
        return summary;
    }

    public static void setSummary(String summary) {
        CurrentPojo.summary = summary;
    }

    public static String getLocation() {
        return location;
    }

    public static void setLocation(String location) {
        CurrentPojo.location = location;
    }

    public static int getTemp() {
        return temp;
    }

    public static void setTemp(int temp) {
        CurrentPojo.temp = temp;
    }

    public static double getHumidity() {
        return humidity;
    }

    public static void setHumidity(double humidity) {
        CurrentPojo.humidity = humidity;
    }

    public static double getPricip() {
        return pricip;
    }

    public static void setPricip(double pricip) {
        CurrentPojo.pricip = pricip;
    }

    public static int getIcon_id() {
        return icon_id;
    }

    public static void setIcon_id(int icon_id) {
        CurrentPojo.icon_id = icon_id;
    }
}
