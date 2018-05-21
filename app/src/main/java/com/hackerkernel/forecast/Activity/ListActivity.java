package com.hackerkernel.forecast.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hackerkernel.forecast.Adapter.DayAdapter;
import com.hackerkernel.forecast.Adapter.HourlyAdapter;
import com.hackerkernel.forecast.Adapter.MonthlyAdapter;

import com.hackerkernel.forecast.Pojo.DayPojo;
import com.hackerkernel.forecast.Pojo.HourlyPojo;
import com.hackerkernel.forecast.R;
import com.hackerkernel.forecast.Utils.Util;

import java.util.List;

/**
 * Created by vinayak tyagi on 08-04-2018.
 */

public class ListActivity extends AppCompatActivity {
    public static  int CONSTANT;
    DayAdapter dayAdapter;
    MonthlyAdapter monthlyAdapter;
    HourlyAdapter hourlyAdapter;
    ListView listView;
    Toolbar toolbar;
    String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lauout_forecat_list);

       CONSTANT =  getIntent().getIntExtra("value",-1);

        listView = findViewById(R.id.list);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (CONSTANT == 1) {  // DAILY AND HOURLY
            title = "Daily List";
            dayAdapter = new DayAdapter(getApplicationContext(), R.layout.include_daily_items, Util.dayPojoList);
            listView.setAdapter(dayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    getSupportActionBar().setTitle("Hourly List");
                    hourlyAdapter = new HourlyAdapter(getApplicationContext(),R.layout.include_hourly_items,Util.hourlyPojoList);
                    listView.setAdapter(hourlyAdapter);
                }
            });
        }else if (CONSTANT == 0){ // MONTHLY
            title = "Monthly List";

            monthlyAdapter = new MonthlyAdapter(getApplicationContext(),R.layout.include_monthly_items,Util.monthlyPojoList);
            listView.setAdapter(monthlyAdapter);
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

    }
}
