package com.codepath.apps.mysimpletweets.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
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
    @InjectView(R.id.tvCount)
    TextView tCount;

    TwitterClient client;
    String atString = null;
    long uid = 0;
    int TWEET_LENGTH = 140;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        getSupportActionBar().hide();

        ButterKnife.inject(this);

        SharedPreferences preferences = getApplicationContext()
                .getSharedPreferences("cpsimpletweets", Context.MODE_PRIVATE);
        String userName = preferences.getString("user", "");
        String screenName = preferences.getString("screen", "");
        String profileUrl = preferences.getString("profile", "");

        Intent intent = getIntent();
        uid = intent.getLongExtra("uid", 0);
        String uScreenName = intent.getStringExtra("user");
        if (uScreenName != null) {
            atString = "@" + uScreenName + " ";
            tweetText.setText(atString);
            TWEET_LENGTH -= atString.length();
            tCount.setText("" + TWEET_LENGTH);
            tweetText.requestFocus();
        }

        client = TwitterApplication.getRestClient();

        user.setText(userName);
        screen.setText("@" + screenName);
        Picasso.with(this).load(profileUrl).into(profile);

        btTweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tweet = tweetText.getText().toString();
                client.postStatusUpdate(tweet, uid, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                        Log.d("DEBUG", json.toString());
                        Tweet tweet = Tweet.fromJSON(json);
                        Intent intent = new Intent();
                        intent.putExtra("tweet", tweet);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Log.d("DEBUG", errorResponse.toString());
                    }
                });
                // ComposeActivity.this.finish();
            }
        });

        tweetText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tCount.setText("" + TWEET_LENGTH);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tCount.setText("" + (TWEET_LENGTH - tweetText.getText().length()));
            }

            @Override
            public void afterTextChanged(Editable s) {

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
