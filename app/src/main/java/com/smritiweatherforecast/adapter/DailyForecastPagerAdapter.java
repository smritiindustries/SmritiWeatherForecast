package com.smritiweatherforecast.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.smritiweatherforecast.fragment.DayWiseForecastFragment;
import com.smritiweatherforecast.model.DailyWeatherForecast;
import com.smritiweatherforecast.model.DayWiseWeatherForecast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Piyush on 6/27/2017.
 */
public class DailyForecastPagerAdapter extends FragmentPagerAdapter {

    private final static SimpleDateFormat sdf = new SimpleDateFormat("E, dd-MM");
    private int numDays;
    private FragmentManager fm;
    private DailyWeatherForecast forecast;

    public DailyForecastPagerAdapter(int numDays, FragmentManager fm, DailyWeatherForecast forecast) {
        super(fm);
        this.numDays = numDays;
        this.fm = fm;
        this.forecast = forecast;

    }


    // Page title
    @Override
    public CharSequence getPageTitle(int position) {
        // We calculate the next days adding position to the current date
        Date d = new Date();
        Calendar gc = new GregorianCalendar();
        gc.setTime(d);
        gc.add(GregorianCalendar.DAY_OF_MONTH, position);

        return sdf.format(gc.getTime());


    }


    @Override
    public Fragment getItem(int num) {
        DayWiseWeatherForecast dayForecast = forecast.getForecast(num);
        DayWiseForecastFragment fragment = DayWiseForecastFragment.newInstance(num);
        fragment.setForecast(dayForecast);
        return fragment;
    }

    /*
     * Number of the days we have the forecast
     */
    @Override
    public int getCount() {

        return numDays;
    }

}