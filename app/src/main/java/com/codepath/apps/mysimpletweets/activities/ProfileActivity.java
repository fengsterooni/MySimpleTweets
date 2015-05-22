package com.codepath.apps.mysimpletweets.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.fragments.ProfileHeaderFragment;
import com.codepath.apps.mysimpletweets.fragments.UserTimelineFragment;
import com.codepath.apps.mysimpletweets.models.User;

import butterknife.ButterKnife;

public class ProfileActivity extends ActionBarActivity {


    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().hide();

        ButterKnife.inject(this);

        ProfileHeaderFragment headerFragment = (ProfileHeaderFragment)
                getSupportFragmentManager().findFragmentById(R.id.flProfileHeader);

        user = getIntent().getParcelableExtra("user");
        if (user == null)
            user = TwitterApplication.getOwner();

        if (user != null) {
            String screenName = user.getScreenName();
            getSupportActionBar().setTitle("@" + screenName);

            headerFragment.setUser(user);

            headerFragment.populateHeader();

            if (savedInstanceState == null) {
                UserTimelineFragment fragment = UserTimelineFragment.newInstance(screenName);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flContainer, fragment);
                ft.commit();
            }
        }
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
