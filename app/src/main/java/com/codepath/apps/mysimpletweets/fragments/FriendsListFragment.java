package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.apps.mysimpletweets.models.UserList;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

public class FriendsListFragment extends UsersListFragment {

    private TwitterClient client;

    public static FriendsListFragment newInstance(String screen_name) {
        FriendsListFragment fragment = new FriendsListFragment();
        Bundle args = new Bundle();
        args.putString("screen_name", screen_name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();

        //User user = getActivity().getIntent().getParcelableExtra("user");
        //String screenName = user.getScreenName();

        // String screenName = getArguments().getString("screen_name");


        populateUserList(-1);

        /*
        if (TwitterApplication.isNetworkAvailable()) {
            populateUserList(0);

        } else {
            new MaterialDialog.Builder(getActivity())
                    .title(R.string.no_network_title)
                    .content(R.string.no_network_activate)
                    .positiveText(R.string.OK)
                    .show();
            List<Tweet> queryResults = new Select().from(Tweet.class)
                    .execute();
            Log.i("INFO", "queryResults SIZE " + queryResults.size());
            if (queryResults.size() > 0) {
                addAll(queryResults);
            }
        } */
    }

    @Override
    void populateUserList(final long cursor) {
        User user = getActivity().getIntent().getParcelableExtra("user");
        String screenName = user.getScreenName();

        client.getFriendsList(screenName, cursor, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                list = UserList.fromJSON(json);
                addAll(list.getUsers());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", "Failure on reading Followers List");
            }
        });

    }
}
