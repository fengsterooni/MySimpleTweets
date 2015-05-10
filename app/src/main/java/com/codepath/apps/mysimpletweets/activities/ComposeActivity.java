package com.codepath.apps.mysimpletweets.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ComposeActivity extends ActionBarActivity {

    @InjectView(R.id.ivImageComp)
    ImageView profile;
    @InjectView(R.id.tvUserComp)
    TextView user;
    @InjectView(R.id.tvscreenComp)
    TextView screen;
    @InjectView(R.id.etTweetComp)
    EditText tweetText;
    @InjectView(R.id.btTweet)
    Button btTweet;

    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        ButterKnife.inject(this);

        SharedPreferences preferences = getApplicationContext()
                .getSharedPreferences("cpsimpletweets", Context.MODE_PRIVATE);
        String userName = preferences.getString("user", "");
        String screenName = preferences.getString("screen", "");
        String profileUrl = preferences.getString("profile", "");

        client = TwitterApplication.getRestClient();

        user.setText(userName);
        screen.setText("@" + screenName);
        Picasso.with(this).load(profileUrl).into(profile);

        btTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tweet = tweetText.getText().toString();
                client.postStatusUpdate(tweet, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                        Log.d("DEBUG", json.toString());
                        /*
                        Intent data = new Intent();
                        data.putExtra("newTweet", tweet);
                        setResult(RESULT_OK, data);
                        ComposeActivity.this.finish();
                        */
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("DEBUG", errorResponse.toString());
                    }
                });
                ComposeActivity.this.finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_compose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
