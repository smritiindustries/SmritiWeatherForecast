package com.smritiweatherforecast.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smritiweatherforecast.AsyncTask.WeatherHttpClient;
import com.smritiweatherforecast.Data.Constants;
import com.smritiweatherforecast.R;
import com.smritiweatherforecast.adapter.DailyForecastPagerAdapter;
import com.smritiweatherforecast.model.DailyWeatherForecast;
import com.smritiweatherforecast.model.Weather;
import com.smritiweatherforecast.parser.JSONParser;

import org.json.JSONException;

public class WeatherForecastActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edCityName;
    private Button forecastButton;
    private TextView cityText;
    private TextView descriptionSky;
    private TextView temperature;
    private TextView temperatureUnit;
    private ViewPager viewPager;
    private String cityName;
    private LinearLayout cityInputLayout;
    private Button backButton;
    private ProgressDialog progressDialog;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        initializeData();
    }

    private void initializeData() {

        backButton = (Button) findViewById(R.id.backButton);
        backButton.setOnClickListener(this);

        cityInputLayout = (LinearLayout) findViewById(R.id.cityNameLayout);

        edCityName = (EditText) findViewById(R.id.cityName);

        viewPager = (ViewPager) findViewById(R.id.pager);

        cityText = (TextView) findViewById(R.id.cityNameText);
        temperature = (TextView) findViewById(R.id.cityTemperature);
        temperatureUnit = (TextView) findViewById(R.id.unittemp);
        descriptionSky = (TextView) findViewById(R.id.skyDescription);

        forecastButton = (Button) findViewById(R.id.forecastButton);
        forecastButton.setOnClickListener(this);
    }

    private void getWeatherForecastData() {
        cityName = edCityName.getText().toString().trim();
        if (cityName != null && !cityName.equalsIgnoreCase("")) {
            JSONWeatherTask task = new JSONWeatherTask();
            task.execute(cityName);
        } else {
            edCityName.setError("City Name required");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forecastButton:

                getWeatherForecastData();
                break;
            case R.id.backButton:
                cityText.setVisibility(View.GONE);
                temperature.setVisibility(View.GONE);
                temperatureUnit.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
                backButton.setVisibility(View.GONE);
                cityInputLayout.setVisibility(View.VISIBLE);
                forecastButton.setVisibility(View.VISIBLE);
                descriptionSky.setVisibility(View.GONE);
                edCityName.setText("");
                break;

        }
    }

    private class JSONWeatherTask extends AsyncTask<String, Void, Weather> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mActivity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Fetching current Temperature...");
            progressDialog.show();

        }

        @Override
        protected Weather doInBackground(String... params) {
            Weather weather = new Weather();
            String data = ((new WeatherHttpClient()).getWeatherData(params[0]));

            try {
                weather = JSONParser.getWeather(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;
        }


        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);
            progressDialog.dismiss();
            progressDialog = null;


            temperatureUnit.setText("°C");
            cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
            temperature.setText("" + Math.round((weather.temperature.getTemp() - 275.15)));
            descriptionSky.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");

            JSONForecastWeatherTask weatherForecast = new JSONForecastWeatherTask();
            weatherForecast.execute(cityName, Constants.forecastDaysNum);

        }
    }


    private class JSONForecastWeatherTask extends AsyncTask<String, Void, DailyWeatherForecast> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mActivity);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Fetching 5 days Temperature...");
            progressDialog.show();

        }

        @Override
        protected DailyWeatherForecast doInBackground(String... params) {

            String data = ((new WeatherHttpClient()).getForecastWeatherData(params[0], params[1]));
            DailyWeatherForecast forecast = new DailyWeatherForecast();
            try {
                forecast = JSONParser.getForecastWeather(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return forecast;
        }


        @Override
        protected void onPostExecute(DailyWeatherForecast forecastWeather) {
            super.onPostExecute(forecastWeather);
            progressDialog.dismiss();
            progressDialog = null;
            DailyForecastPagerAdapter adapter = new DailyForecastPagerAdapter(Integer.parseInt(Constants.forecastDaysNum), getSupportFragmentManager(), forecastWeather);

            viewPager.setAdapter(adapter);
            viewPager.setVisibility(View.VISIBLE);
            backButton.setVisibility(View.VISIBLE);
            cityInputLayout.setVisibility(View.GONE);
            forecastButton.setVisibility(View.GONE);
            cityText.setVisibility(View.VISIBLE);
            temperature.setVisibility(View.VISIBLE);
            temperatureUnit.setVisibility(View.VISIBLE);
            descriptionSky.setVisibility(View.VISIBLE);

        }
    }
}
