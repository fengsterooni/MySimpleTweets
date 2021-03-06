package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.activeandroid.query.Select;
import com.afollestad.materialdialogs.MaterialDialog;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class HomeTimelineFragment extends TweetsListFragment{
    private TwitterClient client;
    @InjectView(R.id.pbSpinner)
    ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(getActivity());
        client = TwitterApplication.getRestClient();

        if (TwitterApplication.isNetworkAvailable()) {
            populateTimeline(0);

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
        }
    }

    void populateTimeline(final long maxId) {
        client.getHomeTimeline(maxId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                progressBar.setVisibility(View.GONE);
                if (maxId == 0)
                    clear();

                addAll(Tweet.fromJSONArray(json));
                Log.d("DEBUG", "SIZE OF THE JSON IS: " + json.length());

                if (maxId == 0)
                    swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                if (errorResponse != null)
                    Log.d("DEBUG", errorResponse.toString());
            }
        });
    }
}
