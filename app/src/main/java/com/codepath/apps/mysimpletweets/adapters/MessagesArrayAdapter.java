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
import com.codepath.apps.mysimpletweets.activities.ProfileActivity;
import com.codepath.apps.mysimpletweets.models.Message;
import com.codepath.apps.mysimpletweets.models.User;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MessagesArrayAdapter extends ArrayAdapter<Message>{
    private static User user;

    static class ViewHolder {
        @InjectView(R.id.ivProfileImage)
        ImageView profileImage;
        @InjectView(R.id.tvUserName)
        TextView userName;
        @InjectView(R.id.tvScreenName)
        TextView screenName;
        @InjectView(R.id.tvBody)
        TextView body;
        @InjectView(R.id.tvTime)
        TextView time;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public MessagesArrayAdapter(Context context, List<Message> messages) {
        super(context, android.R.layout.simple_list_item_1, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Message message = getItem(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_message, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        user = message.getSender();
        viewHolder.userName.setText("@" + user.getScreenName());
        viewHolder.screenName.setText(user.getName());
        viewHolder.body.setText(message.getBody());
        viewHolder.profileImage.setImageResource(android.R.color.transparent);
        viewHolder.profileImage.setTag(user);
        Picasso.with(getContext()).load(user.getProfileImageUrl()).into(viewHolder.profileImage);
        viewHolder.time.setText(getRelativeTimeAgo(message.getCreatedAt()));
        
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
