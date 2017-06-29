package com.smritiweatherforecast.listener;

/**
 * Created by Piyush on 6/27/2017.
 */
public interface DataLoaderListener {

    /**
     * Invoked when the AsyncTask has completed its execution.
     *
     * @param //results The resulting object from the AsyncTask.
     */
    void onDataLoaded(int requestId, Object data);
}
