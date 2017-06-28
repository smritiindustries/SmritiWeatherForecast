package com.smritiweatherforecast.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smritiweatherforecast.R;
import com.smritiweatherforecast.model.DayWiseWeatherForecast;

/**
 * Created by Piyush on 6/27/2017.
 */
public class DayWiseForecastFragment extends Fragment {
    private DayWiseWeatherForecast daywiseWeatherForecast;


    public DayWiseForecastFragment() {
    }

    // newInstance constructor for creating fragment with arguments
    public static DayWiseForecastFragment newInstance(int page) {
        DayWiseForecastFragment dayWiseForecastFragment = new DayWiseForecastFragment();
        Bundle args = new Bundle();
        args.putInt("SCREEN", page);
        dayWiseForecastFragment.setArguments(args);
        return dayWiseForecastFragment;
    }

    public void setForecast(DayWiseWeatherForecast dayForecast) {

        this.daywiseWeatherForecast = dayForecast;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.daywise_forecast_fragment, container, false);

        TextView tempView = (TextView) view.findViewById(R.id.tempForecast);
        TextView descView = (TextView) view.findViewById(R.id.description);
        tempView.setText((int) (daywiseWeatherForecast.temperatureForecast.min - 275.15) + "-" + (int) (daywiseWeatherForecast.temperatureForecast.max - 275.15) + "(°C)");
        descView.setText(daywiseWeatherForecast.weather.currentCondition.getDescr());
        return view;
    }

}