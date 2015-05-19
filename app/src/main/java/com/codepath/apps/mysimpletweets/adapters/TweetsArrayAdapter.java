package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
        viewHolder.numRetweet.setText("" + tweet.getNumRetweet());
        viewHolder.numFavorites.setText("" + tweet.getNumFavorites());

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
        return convertView;
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        String shortTimeString;

        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (relativeDate.equals("Yesterday"))
            shortTimeString = "1d";
        else {
            int index = relativeDate.indexOf(' ', 0);
            shortTimeString = relativeDate.substring(0, index) + relativeDate.charAt(index+1);
        }

        return shortTimeString;
    }
}
