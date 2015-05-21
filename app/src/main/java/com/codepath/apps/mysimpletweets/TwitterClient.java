package com.codepath.apps.mysimpletweets;

import android.content.Context;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */

public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "yTZE4EFScK2xFIFbbEvmBWvT1";       // Change this
    public static final String REST_CONSUMER_SECRET = "AfigrYThw9XlHzgZOg4tn1CH04YF3nhynZPhKxcYbqVUTa7fJy"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://cpsimpletweets"; // Change this (here and in manifest)

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }

    public void getHomeTimeline(long maxId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", 25);
        if (maxId > 0)
            params.put("max_id", maxId);
        else {
            params.put("since_id", 1);
        }
        client.get(apiUrl, params, handler);
        Log.i("INFO", apiUrl);
    }

    public void getMentionsTimeline(long maxId, long sinceId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", 25);
        if (maxId > 0)
            params.put("max_id", maxId);
        else {
            params.put("since_id", 1);
        }
        client.get(apiUrl, params, handler);
        Log.i("INFO", apiUrl);
    }

    public void getUserTimeline(String userName, long maxId, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("screen_name", userName);
        params.put("count", 25);
        if (maxId > 0)
            params.put("max_id", maxId);
        else {
            params.put("since_id", 1);
        }
        client.get(apiUrl, params, handler);
        Log.i("INFO", apiUrl);
    }

    public void getUserInfo(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        RequestParams params = new RequestParams();
        client.get(apiUrl, null, handler);
        Log.i("INFO", apiUrl);
    }

    public void postStatusUpdate(String status, long uid, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", status);
        if (uid != 0)
            params.put("in_reply_to_status_id", uid);
        client.post(apiUrl, params, handler);
        Log.i("INFO", apiUrl);
    }

    public void getFollowersList(String screenName, long cursor, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("followers/list.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);
        params.put("cursor", cursor);
        client.get(apiUrl, params, handler);
        Log.i("INFO", apiUrl);
    }

    public void getFriendsList(String screenName, long cursor, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("friends/list.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);
        params.put("cursor", cursor);
        client.get(apiUrl, params, handler);
        Log.i("INFO", apiUrl);
    }
}