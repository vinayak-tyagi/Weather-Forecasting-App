package com.hackerkernel.forecast.Utils;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.hackerkernel.forecast.Activity.MainActivity;
import com.hackerkernel.forecast.Pojo.CurrentPojo;
import com.hackerkernel.forecast.Pojo.DayPojo;
import com.hackerkernel.forecast.Pojo.HourlyPojo;
import com.hackerkernel.forecast.Pojo.MonthlyPojo;
import com.hackerkernel.forecast.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
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

    public static void JsonParse(String response, Context context) throws JSONException {
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

            Log.e("VIN:","jason parse successfully");

        } catch (JSONException e) {
//            onErrorParse(response,context);
            Log.e("VIN: ","Invalid Input by User");
            Toast.makeText(context, "Unable to find Location !!!", Toast.LENGTH_SHORT).show();
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

//    public static String JsonParselocation(String response) throws JSONException {
//
//        JSONObject jsonObject = new JSONObject(response);
//        JSONArray resultsArrary = jsonObject.getJSONArray("results");
//
//            JSONObject innerObj = resultsArrary.getJSONObject(resultsArrary.length()-3);
//            String  location = innerObj.getJSONArray("address_components").getJSONObject(0).getString("long_name");
//            Log.e("VIN: parse name:", location);
//
//            Log.e("VIN:","Location data parse successfully");
//
//        return location;
//    }


    public static String handleVolleyError(VolleyError error) {
        String message = null;
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            message = "Bad network Connection";
        } else if (error instanceof AuthFailureError) {
            message = "Failed to perform a request";
        } else if (error instanceof ServerError) {
            message = "Server error";
        } else if (error instanceof NetworkError) {
            message = "Network error while performing a request";
        } else if (error instanceof ParseError) {
            message = "Server response could not be parsed";
        }
        return message;
    }


    public static void handleSimpleVolleyRequestError(VolleyError error, Context context) {
        if (error.networkResponse != null) {
            int statusCode = error.networkResponse.statusCode;
            try {
                String body = new String(error.networkResponse.data, "UTF-8");
                if (statusCode == 400) {
                    //server error
                    String errorMsg = Util.simpleJsonParser(body);
                    Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show();
                }else if (statusCode == 401){
                    Toast.makeText(context, "You are unauthorized to view this request. Please try again", Toast.LENGTH_SHORT).show();
                }else if (statusCode == 422){
                    JSONObject  json = new JSONObject(body);
                    JSONArray namearray = json.names();

                    if (namearray.length() > 0){
                        String key = namearray.get(0).toString();
                        JSONArray ja = json.getJSONArray(key);
                        Toast.makeText(context, ja.get(0).toString(), Toast.LENGTH_SHORT).show();
                    }

                }else {
                    String errorString = handleVolleyError(error);
                    Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show();
                }
            } catch (UnsupportedEncodingException | JSONException e) {
                e.printStackTrace();
                showParsingErrorAlert(context);
            }
        } else {
            String errorString = handleVolleyError(error);
            Toast.makeText(context, errorString, Toast.LENGTH_SHORT).show();
        }
    }

    //Parse Simple JSON Parse
    private static String simpleJsonParser(String response) throws JSONException {
        JSONObject obj = new JSONObject(response);
        return obj.getString("msg");
    }

    private static void showParsingErrorAlert(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.oops))
                .setMessage(context.getString(R.string.dont_worry_engineers_r_working))
                .setNegativeButton(context.getString(R.string.report_issue), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //TODO:: take user to report issue area
                    }
                })
                .setPositiveButton(context.getString(R.string.try_again), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
