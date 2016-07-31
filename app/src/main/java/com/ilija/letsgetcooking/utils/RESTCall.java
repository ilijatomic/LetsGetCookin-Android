package com.ilija.letsgetcooking.utils;

import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ilija on 7/27/2016.
 */
public class RESTCall extends Thread {

    private static final String TAG = RESTCall.class.getSimpleName();

    public enum RestType {RECIPE, INGREDIENT, TAG, ERROR}

    private String httpUrl;
    private RestType restType;
    private DownloadListener listener;

    public RESTCall(String url, RestType type, DownloadListener downloadListener) {
        this.httpUrl = url;
        this.restType = type;
        this.listener = downloadListener;
    }

    @Override
    public void run() {

        HttpURLConnection connection = null;
        try {
            URL url = new URL(httpUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                listener.downloadComplete(null, RestType.ERROR);
                Log.d(TAG, String.valueOf(connection.getResponseCode()));
                return;
            }
            listener.downloadComplete(connection.getInputStream(), restType);
        } catch (Exception e) {
            Log.e(TAG, "Network communication error: " + e.getMessage());
            listener.downloadComplete(null, RestType.ERROR);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public interface DownloadListener {
        void downloadComplete(InputStream inputStream, RestType restType);
    }
}
