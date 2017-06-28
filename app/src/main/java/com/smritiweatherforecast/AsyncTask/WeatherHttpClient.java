package com.smritiweatherforecast.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Piyush on 6/27/2017.
 */
public class WeatherHttpClient {

    private static String WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static String API_KEY = "&appid=ec24ac3fc0372d4d40682a50d1c8f537";
    private static String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&q=";

    public String getWeatherData(String location) {
        HttpURLConnection con = null;
        InputStream is = null;

        try {
            con = (HttpURLConnection) (new URL(WEATHER_URL + location + API_KEY)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null)
                buffer.append(line + "rn");

            is.close();
            con.disconnect();
            return buffer.toString();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }

        return null;

    }

    public String getForecastWeatherData(String location, String sForecastDayNum) {
        HttpURLConnection con = null;
        InputStream is = null;
        int forecastDayNum = Integer.parseInt(sForecastDayNum);

        try {

            // Forecast
            String url = FORECAST_URL + location + API_KEY;

            url = url + "&cnt=" + forecastDayNum;
            con = (HttpURLConnection) (new URL(url)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            StringBuffer buffer1 = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br1 = new BufferedReader(new InputStreamReader(is));
            String line1 = null;
            while ((line1 = br1.readLine()) != null)
                buffer1.append(line1 + "\r\n");

            is.close();
            con.disconnect();

            return buffer1.toString();
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }

        return null;

    }
}
