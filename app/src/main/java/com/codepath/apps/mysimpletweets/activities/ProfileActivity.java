package com.codepath.apps.mysimpletweets.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileActivity extends ActionBarActivity {
    @InjectView(R.id.ivProfileImage)
    ImageView ivProfile;
    @InjectView(R.id.tvUserName)
    TextView tvName;
    @InjectView(R.id.tvTagline)
    TextView tvTagLine;
    @InjectView(R.id.tvFollowers)
    TextView tvFollowers;
    @InjectView(R.id.tvFollowing)
    TextView tvFollowing;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ButterKnife.inject(this);

        user = getIntent().getParcelableExtra("user");
        if (user == null)
            user = TwitterApplication.getOwner();

        if (user != null) {
            String screenName = user.getScreenName();
            getSupportActionBar().setTitle("@" + screenName);
            populateProfileHeader(user);

            if (savedInstanceState == null) {
                UserTimelineFragment fragment = UserTimelineFragment.newInstance(screenName);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flContainer, fragment);
                ft.commit();
            }
        }
    }

    private void populateProfileHeader(User user) {
        tvName.setText(user.getName());
        tvTagLine.setText(user.getTagLine());
        Picasso.with(this).load(user.getProfileImageUrl()).into(ivProfile);
        tvFollowers.setText(user.getFollowersCount() + " Followers");
        tvFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFollower();
            }
        });
        tvFollowing.setText(user.getFriendsCount() + " Followings");
        tvFollowing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFollowing();
            }
        });
    }

    public void onFollowing() {
        Intent intent = new Intent(this, UsersListActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("flag", "following");
        startActivity(intent);
    }

    public void onFollower() {
        Intent intent = new Intent(this, UsersListActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("flag", "follower");
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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