package com.hackerkernel.forecast.Utils;


import android.util.Log;

import com.hackerkernel.forecast.Pojo.CurrentPojo;
import com.hackerkernel.forecast.Pojo.DayPojo;
import com.hackerkernel.forecast.Pojo.HourlyPojo;
import com.hackerkernel.forecast.Pojo.MonthlyPojo;
import com.hackerkernel.forecast.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vinayak tyagi on 08-04-2018.
 */

public class Util  {
    public static List<DayPojo> dayPojoList;
    public static List<MonthlyPojo> monthlyPojoList;
    public static List<HourlyPojo> hourlyPojoList;

    public static void JsonParse(String response) {
     Log.e("Response",response);
        CurrentPojo currentPojo = new CurrentPojo();
        dayPojoList = new ArrayList<>();
        monthlyPojoList = new ArrayList<>();
        hourlyPojoList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject data = jsonObject.getJSONObject("data");

            // LOCATION
            JSONArray requestArray = data.getJSONArray("request");
            JSONObject requestArrayObj = requestArray.getJSONObject(0);
            String location =  requestArrayObj.getString("query");

            // CURRENT WEATHER
           JSONArray currentArray = data.getJSONArray("current_condition");
           JSONObject innerObj =  currentArray.getJSONObject(0);

           String time  =   innerObj.getString("observation_time");
           int temp =       innerObj.getInt("temp_C");
           double pricp =   innerObj.getDouble("precipMM");
           int humidity =   innerObj.getInt("humidity");
           String summary = innerObj.getJSONArray("weatherDesc").getJSONObject(0).getString("value");

            currentPojo.setLocation(location);
            currentPojo.setTime(time);
            currentPojo.setSummary(summary);
            currentPojo.setPricip(pricp);
            currentPojo.setHumidity(humidity);
            currentPojo.setTemp(temp);
            currentPojo.setIcon_id(getIconId(summary));

//           Log.e("VIN: CURRENT WEATHER ",time+"\n"
//                   +temp+"\n"
//                   +pricp+"\n"
//                   +humidity+"\n"
//                   +summary+"\n"
//                   +location
//           );

            // DAILY WEATHER
           JSONArray weatherArray = data.getJSONArray("weather");
           for(int i=0; i<weatherArray.length(); i++){
                JSONObject weatherInnerObj =  weatherArray.getJSONObject(i);

           String date =    weatherInnerObj.getString("date");
           int maxtemp =    weatherInnerObj.getInt("maxtempC");
           int mintemp =    weatherInnerObj.getInt("mintempC");
           int uv =         weatherInnerObj.getInt("uvIndex");
           String sunrise = weatherInnerObj.getJSONArray("astronomy").getJSONObject(0).getString("sunrise");
           String sunset =  weatherInnerObj.getJSONArray("astronomy").getJSONObject(0).getString("sunset");

           DayPojo dayPojo = new DayPojo(
                   date,
                   maxtemp,
                   mintemp,
                   sunset,
                   sunrise,
                   i,
                   uv
           );

           dayPojoList.add(dayPojo);

//           Log.e("VIN: DAILY WEATHER",date+"\n"+
//                   maxtemp+"\n"+
//                   mintemp+"\n"+
//                   uv+"\n"+
//                   sunrise+"\n"+
//                   sunset+"\n"
//           );

               // HOURLY WEATHER
            JSONArray hourlyArray =   weatherInnerObj.getJSONArray("hourly");
            for (int j=0; j<hourlyArray.length(); j++){
             JSONObject hourlyInnerObj = hourlyArray.getJSONObject(j);

             int timeh =    hourlyInnerObj.getInt("time");
             int temph =    hourlyInnerObj.getInt("tempC");
             double precip = hourlyInnerObj.getDouble("precipMM");
             int humid =    hourlyInnerObj.getInt("humidity");
             int dew  =     hourlyInnerObj.getInt("DewPointC");
             String summaryh = hourlyInnerObj.getJSONArray("weatherDesc").getJSONObject(0).getString("value");


                HourlyPojo hourlyPojo = new HourlyPojo(
                        getTimeFormat(timeh),
                        temph,
                        precip,
                        humid,
                        dew,
                        summaryh,
                        getIconId(summaryh)
                );

               hourlyPojoList.add(hourlyPojo);

//                Log.e("VIN: HOURLY WEATHER",timeh+"\n"+
//                        temph+"\n"+
//                        precip+"\n"+
//                        humid+"\n"+
//                        dew+"\n"+
//                        summaryh+"\n"
//                );

            } // END OF HOURLY LOOP
           } // END OF  DAILY FOR LOOP or OUTER LOOP


            // MONTHLY WEATHER
            JSONArray ClimateAvarages =  data.getJSONArray("ClimateAverages");
           JSONArray monthArray =  ClimateAvarages.getJSONObject(0).getJSONArray("month");
            for (int i=0; i<monthArray.length(); i++){
              JSONObject monthInnerObj =  monthArray.getJSONObject(i);

            String month = monthInnerObj.getString("name");
            double mintemp = monthInnerObj.getDouble("avgMinTemp");
            double maxtemp = monthInnerObj.getDouble("absMaxTemp");
            double rainfal = monthInnerObj.getDouble("avgDailyRainfall");

                MonthlyPojo monthlyPojo = new MonthlyPojo(
                        month,
                        mintemp,
                        maxtemp,
                        rainfal
                );

                monthlyPojoList.add(monthlyPojo);

//                Log.e("VIN: MONTHLY WEATHER",month+"\n"+
//                        maxtemp+"\n"+
//                        mintemp+"\n"+
//                        rainfal
//                );

            }// END OF LOOP

            Log.e("VIN:","jason parse sucessffuly");

        } catch (JSONException e) {
            Log.e("VIN: ","Error in fetching Json Parsing");
            e.printStackTrace();
        }
    }

    public static String getTimeFormat(int timeStamp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
        Date date = new Date(timeStamp * 1000);
        return simpleDateFormat.format(date);
    }

    public static int  getIconId(String iconString){

        int Icon_id = R.drawable.clear_day;

        if(iconString.equals("Clear")){

            Icon_id = R.drawable.clear_day;

        }else if(iconString.equals("Clear-night")){

            Icon_id = R.drawable.clear_night;

        }else if(iconString.equals("Moderate rain") || iconString.equals("Light rain") || iconString.equals("Patchy light rain") || iconString.equals("Patchy rain possible")){

            Icon_id = R.drawable.rain;

        }else if(iconString.equals("Light sleet") || iconString.equals("Moderate sleet")){

            Icon_id = R.drawable.sleet;

        }else if(iconString.equals("wind")){

            Icon_id = R.drawable.wind;

        }else if(iconString.equals("fog") || iconString.equals("Light drizzle") || iconString.equals("Overcast")){

            Icon_id = R.drawable.fog;

        }else if(iconString.equals("Partly cloudy")){

            Icon_id = R.drawable.partly_cloudy;

        }else if(iconString.equals("Cloudy")){

            Icon_id = R.drawable.cloudy;

        }else if(iconString.equals("Sunny")){

            Icon_id = R.drawable.sunny;

        }
        return Icon_id;
    }

    public static String JsonParselocation(String response) throws JSONException {

        JSONObject jsonObject = new JSONObject(response);
        JSONArray resultsArrary = jsonObject.getJSONArray("results");

            JSONObject innerObj = resultsArrary.getJSONObject(resultsArrary.length()-3);
            String  location = innerObj.getJSONArray("address_components").getJSONObject(0).getString("long_name");
            Log.e("VIN: parse name:", location);

            Log.e("VIN:","Location data parse successfully");

        return location;
    }

}
