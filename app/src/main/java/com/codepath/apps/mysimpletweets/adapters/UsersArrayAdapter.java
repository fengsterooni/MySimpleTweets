package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.activities.ProfileActivity;
import com.codepath.apps.mysimpletweets.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;


import butterknife.ButterKnife;
import butterknife.InjectView;

public class UsersArrayAdapter extends ArrayAdapter<User>{

    static class ViewHolder {
        @InjectView(R.id.ivProfileImage)
        ImageView profileImage;
        @InjectView(R.id.tvUserName)
        TextView userName;
        @InjectView(R.id.tvScreenName)
        TextView screenName;
        @InjectView(R.id.tvTagLine)
        TextView tagLine;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public UsersArrayAdapter(Context context, List<User> users) {
        super(context, android.R.layout.simple_list_item_1, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final User user = getItem(position);
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.userName.setText("@" + user.getScreenName());
        viewHolder.screenName.setText(user.getName());
        viewHolder.tagLine.setText(user.getTagLine());
        viewHolder.profileImage.setImageResource(android.R.color.transparent);
        viewHolder.profileImage.setTag(user);
        Picasso.with(getContext()).load(user.getProfileImageUrl()).into(viewHolder.profileImage);

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
}
