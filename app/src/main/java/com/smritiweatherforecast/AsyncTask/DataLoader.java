package com.smritiweatherforecast.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.smritiweatherforecast.Data.Constants;
import com.smritiweatherforecast.listener.DataLoaderListener;
import com.smritiweatherforecast.model.Weather;

/**
 * Created by Piyush on 6/27/2017.
 */
public class DataLoader extends AsyncTask<String, Void, Weather> {
    String serviceType;
    WeatherHttpClient networkHandler;
    private DataLoaderListener listener;
    private ProgressDialog progressDialog;
    private Context context;

    public DataLoader(Context context, String service) {
        this.context = context;
        this.listener = (DataLoaderListener) context;
        this.serviceType = service;
        networkHandler = new WeatherHttpClient();
    }

    @Override
    protected void onPreExecute() {

        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        if (serviceType.equals(Constants.SERVICE_FETCH)) {
            progressDialog.setMessage("Fetching data...");
        }
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected Weather doInBackground(String... params) {
        String result = null;
        this.serviceType = params[0];
        if (serviceType.equalsIgnoreCase(Constants.SERVICE_FETCH)) {
            try {
                // result =  getWeatherForecastData(params[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Weather result) {
        progressDialog.dismiss();
        progressDialog = null;
        if (serviceType.equals(Constants.SERVICE_FETCH)) {
            if (result.equals("success")) {
                listener.onTaskComplete(result, Constants.SERVICE_FETCH);
            }
        }

        super.onPostExecute(result);
    }
}
