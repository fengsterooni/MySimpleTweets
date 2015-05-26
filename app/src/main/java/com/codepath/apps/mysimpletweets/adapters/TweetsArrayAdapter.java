package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.activities.ComposeActivity;
import com.codepath.apps.mysimpletweets.activities.ProfileActivity;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.apps.mysimpletweets.views.LinkifiedTextView;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TweetsArrayAdapter extends ArrayAdapter<Tweet>{
    private static User user;

    static class ViewHolder {
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
        @InjectView(R.id.ivTweetImage)
        ImageView tweetImage;
        @InjectView(R.id.ivReplyItem)
        ImageView replyItem;
        @InjectView(R.id.ivRetweetItem)
        ImageView retweetItem;
        @InjectView(R.id.tvNumRetweetItem)
        TextView numRetweet;
        @InjectView(R.id.ivFavoriteItem)
        ImageView favoriteItem;
        @InjectView(R.id.tvNumFavoritesItem)
        TextView numFavorites;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Tweet tweet = getItem(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        user = tweet.getUser();
        viewHolder.userName.setText("@" + user.getScreenName());
        viewHolder.screenName.setText(user.getName());
        viewHolder.body.setText(tweet.getBody());
        viewHolder.profileImage.setImageResource(android.R.color.transparent);
        viewHolder.profileImage.setTag(user);
        Picasso.with(getContext()).load(user.getProfileImageUrl()).into(viewHolder.profileImage);
        viewHolder.time.setText(getRelativeTimeAgo(tweet.getCreatedAt()));

        final long numFavorite = tweet.getNumFavorites();
        final long numRetweet = tweet.getNumRetweet();
        final long uid = tweet.getUid();
        viewHolder.numRetweet.setText("" + numRetweet);
        viewHolder.numFavorites.setText("" + numFavorite);

        if (tweet.isFavorited())
            viewHolder.favoriteItem.setImageResource(R.drawable.favorite_on);
        viewHolder.favoriteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tweet.isFavorited()) {
                    viewHolder.numFavorites.setText("" + (numFavorite - 1));
                    viewHolder.favoriteItem.setImageResource(R.drawable.favorite);
                    // unfavoriteTweet(uid);
                }
                else {
                    viewHolder.numFavorites.setText("" + (numFavorite + 1));
                    viewHolder.favoriteItem.setImageResource(R.drawable.favorite_on);
                    // favoriteTweet(uid);
                }
            }
        });

        viewHolder.replyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ComposeActivity.class);
                intent.putExtra("uid", tweet.getUid());
                intent.putExtra("user", tweet.getUser().getScreenName());
                getContext().startActivity(intent);
            }
        });
        viewHolder.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = (User) viewHolder.profileImage.getTag();
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                intent.putExtra("user", user);
                getContext().startActivity(intent);
            }
        });

        if (tweet.getMediaUrl() != null) {
            viewHolder.tweetImage.setVisibility(View.VISIBLE);
            viewHolder.tweetImage
                    .setImageResource(android.R.color.transparent);
            Picasso.with(getContext()).load(tweet.getMediaUrl()).into(viewHolder.tweetImage);
        } else {
            viewHolder.tweetImage.setVisibility(View.GONE);
        }

        return convertView;
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String shortTimeString = "";

        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();

            PrettyTime p = new PrettyTime();
            String relativeDate = p.format(new Date(dateMillis));

            Log.i("INFO", relativeDate);

            int index = relativeDate.indexOf(' ', 0);
            if (relativeDate.indexOf("year") > 0)
                shortTimeString = relativeDate.substring(0, index) + "yr";
            else if (relativeDate.indexOf("month") > 0)
                shortTimeString = relativeDate.substring(0, index) + "mo";
            else if (relativeDate.contains("moments"))
                shortTimeString = "<1m";
            else
                shortTimeString = relativeDate.substring(0, index) + relativeDate.charAt(index + 1);

            Log.i("INFO", "Relative time: " + relativeDate);
            Log.i("INFO", "Short time: " + shortTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return shortTimeString;
    }
}
