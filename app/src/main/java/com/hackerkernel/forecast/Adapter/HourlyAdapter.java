package com.hackerkernel.forecast.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hackerkernel.forecast.Pojo.HourlyPojo;
import com.hackerkernel.forecast.R;

import java.util.List;

/**
 * Created by vinayak tyagi on 08-04-2018.
 */

public class HourlyAdapter extends ArrayAdapter<HourlyPojo> {
    Context context;
    int res;
    List<HourlyPojo> hourlyPojoList;

    public HourlyAdapter(@NonNull Context context, int resource, @NonNull List<HourlyPojo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.res = resource;
        this.hourlyPojoList = objects;
    }


    @Override
    public int getCount() {
        return hourlyPojoList.size();
    }

    @Nullable
    @Override
    public HourlyPojo getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable HourlyPojo item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent)
        View rowview = convertView;
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null){

          rowview =  LayoutInflater.from(context).inflate(R.layout.include_hourly_items,null);
          viewHolder.time = rowview.findViewById(R.id.time_hourly);
          viewHolder.dew = rowview.findViewById(R.id.dew_hourly);
          viewHolder.humidity = rowview.findViewById(R.id.humidity_hourly);
          viewHolder.icon = rowview.findViewById(R.id.hourly_icon);
          viewHolder.summary = rowview.findViewById(R.id.summary_hourly);
          viewHolder.temp = rowview.findViewById(R.id.temp_hourly);

          rowview.setTag(viewHolder);
        }else{
          viewHolder = (ViewHolder)rowview.getTag();
        }

        HourlyPojo hourlyPojo = hourlyPojoList.get(position);

        viewHolder.temp.setText("Temp: "+hourlyPojo.getTemp());
        viewHolder.time.setText(hourlyPojo.getTime());
        viewHolder.dew.setText("Dew: "+hourlyPojo.getDew());
        viewHolder.humidity.setText("Humidity: "+hourlyPojo.getHumid());
        viewHolder.summary.setText(hourlyPojo.getSummary());
        viewHolder.icon.setImageResource(hourlyPojo.getIcon());

        return rowview;
    }

    public class ViewHolder{
         TextView time,temp,dew,humidity,summary;
         ImageView icon;
    }
}
