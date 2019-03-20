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

## Screenshort
![IMG-20190314-WA0005](https://user-images.githubusercontent.com/39195953/54363903-e81e1a80-4691-11e9-8136-764f67544ab9.jpg)
![IMG-20190314-WA0002](https://user-images.githubusercontent.com/39195953/54363858-cc1a7900-4691-11e9-870b-96d4449d25c1.jpg)
![IMG-20190314-WA0000](https://user-images.githubusercontent.com/39195953/54363864-d177c380-4691-11e9-8094-f71ffef3365f.jpg)
![IMG-20190314-WA0003](https://user-images.githubusercontent.com/39195953/54363877-d76da480-4691-11e9-9888-b1abe19e30a0.jpg)
![IMG-20190314-WA0004](https://user-images.githubusercontent.com/39195953/54363883-dc325880-4691-11e9-9cb5-944d6b8828f1.jpg)
![IMG-20190314-WA0005](https://user-images.githubusercontent.com/39195953/54363903-e81e1a80-4691-11e9-8136-764f67544ab9.jpg)
![IMG-20190314-WA0007](https://user-images.githubusercontent.com/39195953/54363914-ed7b6500-4691-11e9-9af2-87cc0818c7ac.jpg)
![IMG-20190314-WA0008](https://user-images.githubusercontent.com/39195953/54363916-eeac9200-4691-11e9-8ee7-ca035969d0e5.jpg)
![IMG-20190314-WA0006](https://user-images.githubusercontent.com/39195953/54363918-ef452880-4691-11e9-80b3-151e185ef73e.jpg)
