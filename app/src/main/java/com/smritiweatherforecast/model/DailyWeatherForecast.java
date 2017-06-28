package com.smritiweatherforecast.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piyush on 6/27/2017.
 */
public class DailyWeatherForecast {

    private List<DayWiseWeatherForecast> daysForecast = new ArrayList<DayWiseWeatherForecast>();

    public void addForecast(DayWiseWeatherForecast forecast) {
        daysForecast.add(forecast);
        System.out.println("Add forecast [" + forecast + "]");
    }

    public DayWiseWeatherForecast getForecast(int dayNum) {
        return daysForecast.get(dayNum);
    }
}