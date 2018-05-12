package com.hackerkernel.forecast.Pojo;

/**
 * Created by vinayak tyagi on 08-04-2018.
 */

public class MonthlyPojo {
    String month;
    double mintemp;
    double maxtemp;
    double avgRainfall;

    public MonthlyPojo(String month, double mintemp, double maxtemp, double avgRainfall) {
        this.month = month;
        this.mintemp = mintemp;
        this.maxtemp = maxtemp;
        this.avgRainfall = avgRainfall;
    }

    public String getMonth() {
        return month;
    }

    public double getMintemp() {
        return mintemp;
    }

    public double getMaxtemp() {
        return maxtemp;
    }

    public double getAvgRainfall() {
        return avgRainfall;
    }
}
