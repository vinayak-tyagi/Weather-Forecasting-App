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

import com.hackerkernel.forecast.Pojo.DayPojo;
import com.hackerkernel.forecast.R;

import java.util.List;

/**
 * Created by vinayak tyagi on 08-04-2018.
 */

public class DayAdapter extends ArrayAdapter<DayPojo> {
    private Context context;
    private int res;
    private List<DayPojo> dayPojoList;

    public DayAdapter(@NonNull Context context, int resource, @NonNull List<DayPojo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.res = resource;
        this.dayPojoList = objects;
    }

    @Override
    public int getCount() {
        return dayPojoList.size();
    }

    @Nullable
    @Override
    public DayPojo getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(@Nullable DayPojo item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//         return super.getView(position, convertView, parent);

        View rowview = convertView;
        ViewHolder viewHolder = new ViewHolder();

        if(convertView == null){

            rowview = LayoutInflater.from(context).inflate(res,null);
            viewHolder.date = rowview.findViewById(R.id.date_daily);
            viewHolder.sunset = rowview.findViewById(R.id.set_daily);
            viewHolder.sunrise = rowview.findViewById(R.id.rise_daily);
            viewHolder.tempMax = rowview.findViewById(R.id.temp_daily);
            viewHolder.tempMin = rowview.findViewById(R.id.tempmin_daily);
            viewHolder.uv = rowview.findViewById(R.id.uv_daily);

            rowview.setTag(viewHolder);

        }else{
            viewHolder = (ViewHolder)  rowview.getTag();
        }

        DayPojo dayPojo = dayPojoList.get(position);

        viewHolder.tempMin.setText("Min: "+dayPojo.getTempMin());
        viewHolder.tempMax.setText("Max: "+dayPojo.getTempMax());
        viewHolder.sunrise.setText("Sunrise: "+dayPojo.getSunrise());
        viewHolder.sunset.setText("Sunset: "+dayPojo.getSunset());
        viewHolder.date.setText(dayPojo.getDate());
        viewHolder.uv.setText("UV: "+dayPojo.getUv());

       return rowview;
    }

    public class ViewHolder {
        TextView tempMax,tempMin,date,sunset,sunrise,uv;
    }

}
