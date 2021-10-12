package com.example.assignmet.day;


public class DailyForecasts {
    private String Date;
    private Temperature Temperature;
    private Day Day;

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public com.example.assignmet.day.Temperature getTemperature() {
        return Temperature;
    }

    public void setTemperature(com.example.assignmet.day.Temperature temperature) {
        Temperature = temperature;
    }

    public com.example.assignmet.day.Day getDay() {
        return Day;
    }

    public void setDay(com.example.assignmet.day.Day day) {
        Day = day;
    }
}
