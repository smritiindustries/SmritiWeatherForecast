package com.smritiweatherforecast.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Piyush on 6/27/2017.
 */
public class DayWiseWeatherForecast {

    private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    public Weather weather = new Weather();
    public TemperatureForecast temperatureForecast = new TemperatureForecast();
    public long timestamp;

    public String getStringDate() {
        return sdf.format(new Date(timestamp));
    }

    public class TemperatureForecast {
        public float day;
        public float min;
        public float max;
        public float night;
        public float eve;
        public float morning;
    }
}