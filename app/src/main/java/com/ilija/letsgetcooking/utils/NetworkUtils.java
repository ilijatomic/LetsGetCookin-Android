package com.ilija.letsgetcooking.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by ilija.tomic on 7/27/2016.
 */
public class NetworkUtils {

    /**
     * Checking if there is a active network connection,
     * used if there is empty database of recipes
     *
     * @param context application context
     * @return check success
     */
    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
