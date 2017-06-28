package com.smritiweatherforecast.listener;

import com.smritiweatherforecast.model.Weather;

/**
 * Created by Piyush on 6/27/2017.
 */
public interface DataLoaderListener {

    /**
     * Invoked when the AsyncTask has completed its execution.
     *
     * @param result The resulting object from the AsyncTask.
     */
    void onTaskComplete(Weather result, String servicetype);
}
