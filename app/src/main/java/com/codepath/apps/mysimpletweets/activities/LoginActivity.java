package com.codepath.apps.mysimpletweets.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.oauth.OAuthLoginActionBarActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class LoginActivity extends OAuthLoginActionBarActivity<TwitterClient> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }


    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    // OAuth authenticated successfully, launch primary authenticated activity
    // i.e Display application "homepage"
    @Override
    public void onLoginSuccess() {
        TwitterApplication.getRestClient().getVerifyCredentials(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                Log.d("DEBUG", json.toString());
                User user = User.fromJSON(json);
                if (user != null) {
                    SharedPreferences pref = getApplicationContext()
                            .getSharedPreferences("cpsimpletweets", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("user", user.getName());
                    editor.putString("screen", user.getScreenName());
                    editor.putString("profile", user.getProfileImageUrl());
                    editor.commit();
                    Log.i("INFO", "User: " + user.getName());
                    Log.i("INFO", "Profile: " + user.getProfileImageUrl().toString());
                    Log.i("INFO", "Screen: " + user.getScreenName());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
        Intent i = new Intent(this, TimelineActivity.class);
        startActivity(i);
        // Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
    }

    // OAuth authentication flow failed, handle the error
    // i.e Display an error dialog or toast
    @Override
    public void onLoginFailure(Exception e) {
        e.printStackTrace();
    }

    // Click handler method for the button used to start OAuth flow
    // Uses the client to initiate OAuth authorization
    // This should be tied to a button used to login
    public void loginToRest(View view) {
        getClient().connect();
    }

}
