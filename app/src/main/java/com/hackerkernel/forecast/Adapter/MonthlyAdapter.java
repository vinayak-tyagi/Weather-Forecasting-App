package com.hackerkernel.forecast.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hackerkernel.forecast.Pojo.MonthlyPojo;
import com.hackerkernel.forecast.R;

import java.util.List;

/**
 * Created by vinayak tyagi on 08-04-2018.
 */

public class MonthlyAdapter extends ArrayAdapter<MonthlyPojo> {
    Context context;
    int res;
    List<MonthlyPojo> monthlyPojoList;

    public MonthlyAdapter(@NonNull Context context, int resource, @NonNull List<MonthlyPojo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.res = resource;
        this.monthlyPojoList = objects;
    }

    @Override
    public int getCount() {
        return monthlyPojoList.size();
    }

    @Nullable
    @Override
    public MonthlyPojo getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable MonthlyPojo item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);

        View rowview = convertView;
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null){

            rowview = LayoutInflater.from(context).inflate(res,null);
            viewHolder.maxtemp = rowview.findViewById(R.id.temp_monthly);
            viewHolder.mintemp = rowview.findViewById(R.id.tempmin_monthly);
            viewHolder.rain = rowview.findViewById(R.id.rain_monthly);
            viewHolder.month = rowview.findViewById(R.id.month_name);

             rowview.setTag(viewHolder);
        }else{
                viewHolder = (ViewHolder) rowview.getTag();
        }

        MonthlyPojo monthlyPojo = monthlyPojoList.get(position);

        viewHolder.month.setText(monthlyPojo.getMonth());
        viewHolder.rain.setText("Rain: "+monthlyPojo.getAvgRainfall());
        viewHolder.mintemp.setText("Min: "+monthlyPojo.getMintemp());
        viewHolder.maxtemp.setText("Max: "+monthlyPojo.getMaxtemp());

        return rowview;
    }

    public class ViewHolder{
        TextView month,maxtemp,mintemp,rain;
    }
}
