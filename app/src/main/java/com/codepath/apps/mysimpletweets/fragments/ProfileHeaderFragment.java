package com.codepath.apps.mysimpletweets.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.activities.UsersListActivity;
import com.codepath.apps.mysimpletweets.models.User;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileHeaderFragment extends Fragment {
    private User user;

    @InjectView(R.id.ivProfileImage)
    ImageView ivProfile;
    @InjectView(R.id.ivBackgroundProfileImage)
    ImageView ivBackground;
    @InjectView(R.id.tvUserName)
    TextView tvName;
    @InjectView(R.id.tvScreenName)
    TextView tvScreenName;
    @InjectView(R.id.tvTagLine)
    TextView tvTagLine;
    @InjectView(R.id.tvNumFollower)
    TextView tvNumFollower;
    @InjectView(R.id.tvNumFollowing)
    TextView tvNumFollowing;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_header, parent, false);
        ButterKnife.inject(this, view);

        return view;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void populateHeader() {

        tvName.setText(user.getName());
        tvScreenName.setText("@" + user.getScreenName());
        tvTagLine.setText(user.getTagLine());
        Picasso.with(getActivity()).load(user.getProfileImageUrl()).into(ivProfile);
        Picasso.with(getActivity()).load(user.getBackgroundImageUrl()).into(ivBackground);
        tvNumFollower.setText(Html.fromHtml("<b>" + user.getFollowersCount() + "</b> FOLLOWERS"));
        tvNumFollower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFollower();
            }
        });
        tvNumFollowing.setText(Html.fromHtml("<b>" + user.getFriendsCount() + "</b> FOLLOWING"));
        tvNumFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFollowing();
            }
        });
    }

    public void onFollowing() {
        Intent intent = new Intent(getActivity(), UsersListActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("flag", "following");
        startActivity(intent);
    }

    public void onFollower() {
        Intent intent = new Intent(getActivity(), UsersListActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("flag", "follower");
        startActivity(intent);
    }

}

