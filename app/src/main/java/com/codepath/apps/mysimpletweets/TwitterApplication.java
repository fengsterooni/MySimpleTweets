package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.codepath.apps.mysimpletweets.models.User;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 *
 *     RestClient client = TwitterApplication.getRestClient();
 *     // use client to send requests to API
 *
 */
public class TwitterApplication extends com.activeandroid.app.Application {
    private static Context context;
    private static User owner;

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterApplication.context = this;
    }

    public static TwitterClient getRestClient() {
        return (TwitterClient) TwitterClient.getInstance(TwitterClient.class, TwitterApplication.context);
    }

    public static Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static User getOwner() {
        return owner;
    }

    public static void setOwner(User owner) {
        TwitterApplication.owner = owner;
    }
}