package com.example.assignmet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.assignmet.day.DailyForecasts;
import com.example.assignmet.day.Wheather1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvHour;
    private TextView tvTem;
    private TextView tvStatus;
    private TextView tvNow;
    private RecyclerView rvDay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTem = (TextView) findViewById(R.id.tvTem);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        tvNow = (TextView) findViewById(R.id.tvNow);

        getHours();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvHour = (RecyclerView) findViewById(R.id.rvHour);
        rvHour.setLayoutManager(layoutManager);

        getDays();

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        rvDay = (RecyclerView) findViewById(R.id.rvDay);
        rvDay.setLayoutManager(layoutManager1);

    }
    private void getHours(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiManager.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiManager service = retrofit.create(ApiManager.class);

        service.gethour().enqueue(new Callback<List<Wheather>>() {
            @Override
            public void onResponse(Call<List<Wheather>> call, Response<List<Wheather>> response) {
                if(response.body() == null) return;

                List<Wheather> listWheather = response.body();
                HourAdapter adapter = new HourAdapter(MainActivity.this,listWheather);
                rvHour.setAdapter(adapter);

                Wheather wheather = listWheather.get(0);
                tvTem.setText(wheather.getTemperature().getValue().intValue()+"ºC");
                tvStatus.setText(wheather.getIconPhrase());
            }

            @Override
            public void onFailure(Call<List<Wheather>> call, Throwable t) {

            }
        });
    }

    private void getDays(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiManager.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        ApiManager service = retrofit.create(ApiManager.class);

        service.getDay().enqueue(new Callback<Wheather1>() {
            @Override
            public void onResponse(Call<Wheather1> call, Response<Wheather1> response) {
                if (response.body() != null) {
                    Wheather1 wheather1 = response.body();
                    DayAdapter adapter = new DayAdapter(MainActivity.this, wheather1.getDailyForecasts());
                    rvDay.setAdapter(adapter);

                    DailyForecasts dailyForecasts = wheather1.getDailyForecasts().get(1);
                    tvNow.setText(convertTime(wheather1.getDailyForecasts().get(1).getDate()));
                }
            }

            @Override
            public void onFailure(Call<Wheather1> call, Throwable t) {

            }

        });
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