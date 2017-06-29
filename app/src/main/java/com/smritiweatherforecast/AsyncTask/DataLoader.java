package com.smritiweatherforecast.AsyncTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.smritiweatherforecast.R;
import com.smritiweatherforecast.listener.DataLoaderListener;
import com.smritiweatherforecast.parser.JSONParser;

import org.json.JSONException;

/**
 * Created by Piyush on 6/27/2017.
 */
public class DataLoader extends AsyncTask<Object, String, Object> {

    public static final byte ACTION_GET_WEATHER = 0;
    public static final byte ACTION_GET_WEATHER_FORECAST_DATA = 1;
    WeatherHttpClient weatherHttpClient;
    private DataLoaderListener listener;
    private ProgressDialog progressDialog;
    private Activity mActivity;
    private byte action;

    public DataLoader(Activity activity, byte action) {
        mActivity = activity;
        this.listener = (DataLoaderListener) mActivity;
        weatherHttpClient = new WeatherHttpClient();
        this.action = action;
    }

    @Override
    protected void onPreExecute() {

        progressDialog = new ProgressDialog(mActivity);
        String loadingMsg = "";
        switch (action) {
            case ACTION_GET_WEATHER:
                loadingMsg = mActivity.getString(R.string.weather_fetch_msg);
                break;
            case ACTION_GET_WEATHER_FORECAST_DATA:
                loadingMsg = mActivity.getString(R.string.weather_forecast_msg);
                break;
        }
        progressDialog.setMessage(loadingMsg);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object... arg) {
        Object resultDataObject = null;
        String response = "";
        action = (Byte) arg[0];
        switch (action) {
            case ACTION_GET_WEATHER:
                response = weatherHttpClient.getWeatherData((String) arg[1]);
                try {
                    resultDataObject = JSONParser.getWeather(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case ACTION_GET_WEATHER_FORECAST_DATA:
                response = weatherHttpClient.getForecastWeatherData((String) arg[1], (String) arg[2]);
                try {
                    resultDataObject = JSONParser.getForecastWeather(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
        return resultDataObject;
    }

    @Override
    protected void onPostExecute(Object result) {
        switch (action) {
            case ACTION_GET_WEATHER:
                listener.onDataLoaded(action, result);
                break;
            case ACTION_GET_WEATHER_FORECAST_DATA:
                listener.onDataLoaded(action, result);
                break;
        }
        try {
            progressDialog.dismiss();
            progressDialog = null;
            super.onPostExecute(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
