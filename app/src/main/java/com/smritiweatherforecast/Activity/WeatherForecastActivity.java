package com.smritiweatherforecast.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smritiweatherforecast.AsyncTask.DataLoader;
import com.smritiweatherforecast.Data.Constants;
import com.smritiweatherforecast.R;
import com.smritiweatherforecast.adapter.DailyForecastPagerAdapter;
import com.smritiweatherforecast.listener.DataLoaderListener;
import com.smritiweatherforecast.model.DailyWeatherForecast;
import com.smritiweatherforecast.model.Weather;

public class WeatherForecastActivity extends AppCompatActivity implements View.OnClickListener, DataLoaderListener {
    Weather weatherObject;
    DailyWeatherForecast dailyWeatherForecastObject;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            DataLoader mDataLoader = new DataLoader(this, DataLoader.ACTION_GET_WEATHER);
            mDataLoader.execute(DataLoader.ACTION_GET_WEATHER, cityName);
        } else {
            edCityName.setError(getResources().getString(R.string.city_name_blank));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.forecastButton:
                getWeatherForecastData();
                break;
            case R.id.backButton:
                createFreshView();
                break;
        }
    }

    private void createFreshView() {
        cityText.setVisibility(View.GONE);
        temperature.setVisibility(View.GONE);
        temperatureUnit.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
        backButton.setVisibility(View.GONE);
        cityInputLayout.setVisibility(View.VISIBLE);
        forecastButton.setVisibility(View.VISIBLE);
        descriptionSky.setVisibility(View.GONE);
        edCityName.setText("");
    }

    @Override
    public void onDataLoaded(int requestId, Object data) {
        switch (requestId) {
            case DataLoader.ACTION_GET_WEATHER:
                if (data instanceof Weather) {
                    weatherObject = (Weather) data;
                }
                generateTodaysWeatherDataUI(weatherObject);
                break;
            case DataLoader.ACTION_GET_WEATHER_FORECAST_DATA:
                if (data instanceof DailyWeatherForecast) {
                    dailyWeatherForecastObject = (DailyWeatherForecast) data;
                }
                DailyForecastPagerAdapter adapter = new DailyForecastPagerAdapter(Integer.parseInt(Constants.forecastDaysNum), getSupportFragmentManager(), dailyWeatherForecastObject);

                prepareViewForDailyForecast(adapter);

                break;
        }
    }

    private void prepareViewForDailyForecast(DailyForecastPagerAdapter adapter) {
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

    private void generateTodaysWeatherDataUI(Weather weatherObject) {
        temperatureUnit.setText("°C");
        cityText.setText(weatherObject.location.getCity() + "," + weatherObject.location.getCountry());
        temperature.setText("" + Math.round((weatherObject.temperature.getTemp() - 275.15)));
        descriptionSky.setText(weatherObject.currentCondition.getCondition() + "(" + weatherObject.currentCondition.getDescr() + ")");

        DataLoader mDataLoader = new DataLoader(this, DataLoader.ACTION_GET_WEATHER_FORECAST_DATA);
        mDataLoader.execute(DataLoader.ACTION_GET_WEATHER_FORECAST_DATA, cityName, Constants.forecastDaysNum);
    }
}
