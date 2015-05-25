package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.apps.mysimpletweets.utils.DateUtils;
import com.codepath.apps.mysimpletweets.views.LinkifiedTextView;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;

import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TweetDetailsActivity extends ActionBarActivity {
    @InjectView(R.id.ivProfileImage)
    ImageView profileImage;
    @InjectView(R.id.tvUserName)
    TextView userName;
    @InjectView(R.id.tvScreenName)
    TextView screenName;
    @InjectView(R.id.tvBody)
    LinkifiedTextView body;
    @InjectView(R.id.ivTweetImage)
    ImageView tweetImage;
    @InjectView(R.id.tvTime)
    TextView time;
    @InjectView(R.id.tvNumRetweet)
    TextView retweets;
    @InjectView(R.id.tvNumFavorites)
    TextView favorites;
    @InjectView(R.id.ivReply)
    ImageView iVReply;
    @InjectView(R.id.ivRetweet)
    ImageView ivRetweet;
    @InjectView(R.id.ivFavorite)
    ImageView ivFavorite;
    @InjectView(R.id.ivShare)
    ImageView ivShare;
    @InjectView(R.id.etDetailReply)
    EditText etReply;

    boolean favorite;
    static Tweet tweet;
    long numFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_icon);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        ButterKnife.inject(this);

        tweet = getIntent().getParcelableExtra("tweet");
        if (tweet != null) {
            final User user = tweet.getUser();
            userName.setText(user.getName());
            screenName.setText("@" + user.getScreenName());
            Picasso.with(TweetDetailsActivity.this).load(user.getProfileImageUrl()).into(profileImage);
            profileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TweetDetailsActivity.this, ProfileActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
            });
            body.setText(tweet.getBody());
            Date date = DateUtils.getDateFromString(tweet.getCreatedAt());
            time.setText(DateUtils.getShortDateTimeString(date));
            numFavorite = tweet.getNumFavorites();
            retweets.setText("" + tweet.getNumRetweet());
            favorites.setText("" + numFavorite);
            iVReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reply(tweet);
                }
            });
            etReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reply(tweet);
                }
            });
            etReply.setHint("Reply to " + user.getName());

            if (tweet.getMediaUrl() != null) {
                tweetImage.setVisibility(View.VISIBLE);
                tweetImage
                        .setImageResource(android.R.color.transparent);
                Picasso.with(TweetDetailsActivity.this).load(tweet.getMediaUrl()).into(tweetImage);
            } else {
                tweetImage.setVisibility(View.GONE);
            }

            favorite = tweet.isFavorited();
            if (favorite) {
                ivFavorite.setImageResource(R.drawable.favorite_on);
            } else {
                ivFavorite.setImageResource(R.drawable.favorite);
            }

            ivFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (favorite) {
                        Log.i("INFO", "Unfavor");
                        ivFavorite.setImageResource(R.drawable.favorite);
                        numFavorite -= 1;
                        favorites.setText("" + numFavorite);
                    } else {
                        Log.i("INFO", "Favor");
                        ivFavorite.setImageResource(R.drawable.favorite_on);
                        numFavorite += 1;
                        favorites.setText("" + numFavorite);
                    }
                    favorite = !favorite;
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (favorite != tweet.isFavorited()) {
            if (favorite) {
                TwitterApplication.getRestClient().postFavoriteCreate(tweet.getUid(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
                Log.i("INFO", "Favorited");
            }
            else {
                TwitterApplication.getRestClient().postFavoriteDestory(tweet.getUid(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
                Log.i("INFO", "Unfavorited");
            }
        }
    }

    private void reply(Tweet tweet) {
        Intent intent = new Intent(TweetDetailsActivity.this, ComposeActivity.class);
        intent.putExtra("uid", tweet.getUid());
        intent.putExtra("user", tweet.getUser().getScreenName());
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tweet_details, menu);
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
