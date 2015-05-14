package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.apps.mysimpletweets.utils.DateUtils;
import com.codepath.apps.mysimpletweets.views.LinkifiedTextView;
import com.squareup.picasso.Picasso;

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
    @InjectView(R.id.tvTime)
    TextView time;
    @InjectView(R.id.tvNumRetweet)
    TextView retweets;
    @InjectView(R.id.tvNumFavorites)
    TextView refavor;
    @InjectView(R.id.ivReply)
    ImageView iVReply;
    @InjectView(R.id.ivRetweet)
    ImageView ivRetweet;
    @InjectView(R.id.ivFavorite)
    ImageView ivFavorite;
    @InjectView(R.id.ivShare)
    ImageView ivShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        ButterKnife.inject(this);

        final Tweet tweet = getIntent().getParcelableExtra("tweet");
        if (tweet != null) {
            User user = tweet.getUser();
            userName.setText(user.getName());
            screenName.setText("@" + user.getScreenName());
            Picasso.with(TweetDetailsActivity.this).load(user.getProfileImageUrl()).into(profileImage);
            body.setText(tweet.getBody());
            Date date = DateUtils.getDateFromString(tweet.getCreatedAt());
            time.setText(DateUtils.getShortDateTimeString(date));
            retweets.setText("" + tweet.getNumRetweet());
            refavor.setText("" + tweet.getNumFavorites());
            iVReply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TweetDetailsActivity.this, ComposeActivity.class);
                    intent.putExtra("uid", tweet.getUid());
                    intent.putExtra("user", tweet.getUser().getScreenName());
                    startActivity(intent);
                    finish();
                }
            });
        }
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
