package com.codepath.apps.mysimpletweets.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.fragments.FollowersListFragment;
import com.codepath.apps.mysimpletweets.fragments.FriendsListFragment;
import com.codepath.apps.mysimpletweets.fragments.UsersListFragment;
import com.codepath.apps.mysimpletweets.models.User;

public class UsersListActivity extends ActionBarActivity {
    UsersListFragment fragment;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        user = getIntent().getParcelableExtra("user");
        String flag = getIntent().getStringExtra("flag");
        Log.i("INFO", flag);
        if (user == null)
            user = TwitterApplication.getOwner();

        if (user != null) {
            String screenName = user.getScreenName();
            getSupportActionBar().setTitle("@" + screenName);
            // populateProfileHeader(user);

            if (savedInstanceState == null) {

                if (flag.equals("following")) {
                    fragment = FriendsListFragment.newInstance(screenName);
                    Log.i("INFO", flag);
                }
                else if (flag.equals("follower")) {
                    fragment = FollowersListFragment.newInstance(screenName);
                    Log.i("INFO", flag);
                }
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flUserContainer, fragment);
                ft.commit();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_users_list, menu);
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
