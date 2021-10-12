package com.example.assignmet;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.assignmet.R;
import com.example.assignmet.day.DailyForecasts;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter {
    private Activity activity;
    private List<DailyForecasts> listDailyForecast;

    public DayAdapter(Activity activity, List<DailyForecasts> listDailyForecast) {
        this.activity = activity;
        this.listDailyForecast = listDailyForecast;
    }

    public void reloadData(List<DailyForecasts> listDailyForecast){
        this.listDailyForecast = listDailyForecast;
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = activity.getLayoutInflater();
        View itemView = inflater.inflate(R.layout.item_day,parent,false);
        DayHolder holder = new DayHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DayHolder vh1 = (DayHolder) holder;
        DailyForecasts dailyforescasts = listDailyForecast.get(position);
        vh1.tvDay.setText(convertTime(dailyforescasts.getDate()));
        vh1.tvmax.setText(dailyforescasts.getTemperature().getMaximum().getValue().intValue()+"");
        vh1.tvmin.setText(dailyforescasts.getTemperature().getMinimum().getValue().intValue()+"");
        String url ="";
        if (dailyforescasts.getDay().getIcon()<10){
            url = "https://developer.accuweather.com/sites/default/files/0" + dailyforescasts.getDay().getIcon() + "-s.png";
        }else {
            url = "https://developer.accuweather.com/sites/default/files/" + dailyforescasts.getDay().getIcon() + "-s.png";
        }
        Glide.with(activity).load(url).into(vh1.icon1);
    }

    @Override
    public int getItemCount() {
        return listDailyForecast.size();
    }

    public static class DayHolder extends RecyclerView.ViewHolder{
        private TextView tvDay;
        private ImageView icon1;
        private TextView tvmax;
        private TextView tvmin;

        public DayHolder(@NonNull View itemView){
            super(itemView);
            tvDay = (TextView) itemView.findViewById(R.id.tvDay);
            icon1 = (ImageView) itemView.findViewById(R.id.icon1);
            tvmax = (TextView) itemView.findViewById(R.id.tvmax);
            tvmin = (TextView) itemView.findViewById(R.id.tvmin);
        }

    }
    public String convertTime(String inputTime){
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = null;
        try {
            date = inFormat.parse(inputTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
        String goal = outFormat.format(date);
        return goal;
    }
}
