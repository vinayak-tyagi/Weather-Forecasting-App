# Weather-Forecasting-App
This is the android Application which forecast the weather for 12-months and  next 7-days according to your geolocation, it basically needed 
the `GPS Enable` and `Internet Enable` if it's not enable it will genrate an alert in your device.

## Installation
* Its mandatory to have an on going  `Android Studio` in your system before going to this application.
* Than Clone the project and Open it.
* Now, Comes the Most important Part [World Weather](https://www.worldweatheronline.com/developer/)
* Now, get an api from the above link and Replace the link in this part of Code in MainActivity.java file

```java
 private void onFetchingData(String locationName) {
        isNetworkAvailable();
        pd.show();

        // NOTE THAT THE API KEY OVERHERE IS HIGHLY IMPORTANT AND CONFIDENTIAL
        StringRequest stringRequest = new StringRequest(Request.Method.GET,"https://api.worldweatheronline.com/premium/v1/weather.ashx?key=c3_Your_Own_Key_950956180&q="+locationName+"&format=json&num_of_days=7",
        new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.dismiss();
                try {
                    Util.JsonParse(response,MainActivity.this);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                UIuppdate();

```
* Please Check your Link before putting into the Code `Eg: Postman tool`
* Now, Save Your Code and You are ready to Run the Code.
