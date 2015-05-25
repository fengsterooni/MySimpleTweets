package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.apps.mysimpletweets.models.UserList;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class FriendsListFragment extends UsersListFragment {
    private TwitterClient client;
    @InjectView(R.id.pbSpinner)
    ProgressBar progressBar;

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
        ButterKnife.inject(getActivity());
        client = TwitterApplication.getRestClient();

        if (TwitterApplication.isNetworkAvailable()) {
            populateUserList(-1);

        } else {
            new MaterialDialog.Builder(getActivity())
                    .title(R.string.no_network_title)
                    .content(R.string.no_network_message)
                    .positiveText(R.string.OK)
                    .show();
        }
    }

    @Override
    void populateUserList(final long cursor) {
        User user = getActivity().getIntent().getParcelableExtra("user");
        String screenName = user.getScreenName();

        client.getFriendsList(screenName, cursor, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                progressBar.setVisibility(View.GONE);
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
