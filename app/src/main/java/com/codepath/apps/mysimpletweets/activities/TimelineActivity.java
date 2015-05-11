package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.adapters.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.listeners.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class TimelineActivity extends ActionBarActivity {
    private final int TWEET_REQUEST = 100;
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;

    @InjectView(R.id.lvTweets)
    ListView lvTweets;
    @InjectView(R.id.etCompose)
    EditText etCompose;
    @InjectView(R.id.swipeContainter)
    SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.inject(this);
        client = TwitterApplication.getRestClient();

        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
            }
        });

        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("INFO", "Toched!");
                Tweet tweet = tweets.get(position);
                Log.i("INFO", tweet.toString());
                Intent intent = new Intent(TimelineActivity.this, TweetDetailsActivity.class);
                intent.putExtra("tweet", tweet);
                startActivity(intent);
            }
        });

        etCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCompose(v);
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        populateTimeline(0, 1);
    }

    private void fetchTimelineAsync(int i) {
        populateTimeline(0, 1);
    }

    private void populateTimeline(final long maxId, long sinceId) {
        client.getHomeTimeline(maxId, sinceId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
//                Log.d("DEBUG", json.toString());
                if (maxId == 0)
                    aTweets.clear();

                aTweets.addAll(Tweet.fromJSONArray(json));
//                Log.d("DEBUG", aTweets.toString());
                Log.d("DEBUG", "SIZE OF THE JSON IS: " + json.length());

                if (maxId == 0)
                    swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        });
    }

    private void customLoadMoreDataFromApi(int offset) {
        long maxId = 0;
        long sinceId = 0;
        int count = tweets.size();
        if (count > 0) {
            maxId = tweets.get(count - 1).getUid() - 1;
            sinceId = 1;
        }

        populateTimeline(maxId, sinceId);
    }


    public void onCompose(View view) {
        Intent i = new Intent(this, ComposeActivity.class);
        // startActivity(i);
        startActivityForResult(i, TWEET_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TWEET_REQUEST) {
            if (resultCode == RESULT_OK) {
                Tweet tweet = data.getParcelableExtra("tweet");
                aTweets.insert(tweet, 0);
                // Toast.makeText(this, newTweet, Toast.LENGTH_SHORT).show();
                // populateTimeline(0, 1);
            }
            // else RESULT_CANCELED, nothing changed
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
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
