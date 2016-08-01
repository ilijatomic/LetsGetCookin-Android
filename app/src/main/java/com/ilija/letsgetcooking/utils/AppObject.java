package com.ilija.letsgetcooking.utils;

import android.app.Application;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Executing code when lunching application
 *
 * Created by ilija.tomic on 7/27/2016.
 */
public class AppObject extends Application {

    private static final long MAX_CACHE_SIZE = 50000000;
    private static final String CACHE_FOLDER = "images";


    @Override
    public void onCreate() {
        super.onCreate();

        // Creating cache folder for images, setting max cache size and setting single instance for picasso use
        File imageCache = new File(getApplicationContext().getCacheDir(), CACHE_FOLDER);
        Picasso picasso = new Picasso.Builder(getApplicationContext()).downloader(new OkHttpDownloader(imageCache, MAX_CACHE_SIZE)).build();
        Picasso.setSingletonInstance(picasso);

        // Configuring realm database
        RealmConfiguration realmConfig = new RealmConfiguration.Builder(getApplicationContext()).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);

    }
}
