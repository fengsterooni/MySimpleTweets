package com.codepath.apps.mysimpletweets.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.activities.ComposeActivity;
import com.codepath.apps.mysimpletweets.activities.TweetDetailsActivity;
import com.codepath.apps.mysimpletweets.adapters.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.listeners.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public abstract class TweetsListFragment extends Fragment {
    private final int TWEET_REQUEST = 100;

    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;

    @InjectView(R.id.lvTweets)
    ListView lvTweets;
    @InjectView(R.id.etCompose)
    EditText etCompose;
    @InjectView(R.id.swipeContainter)
    SwipeRefreshLayout swipeContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tweets_list, parent, false);
        ButterKnife.inject(this, view);

        lvTweets.setAdapter(aTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
            }
        });

        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tweet tweet = tweets.get(position);
                Intent intent = new Intent(getActivity(), TweetDetailsActivity.class);
                intent.putExtra("tweet", tweet);
                startActivity(intent);
            }
        });

        etCompose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCompose(v);
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (TwitterApplication.isNetworkAvailable()) {
                    fetchTimelineAsync(0);
                }
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
    }

    private void fetchTimelineAsync(int i) {
        populateTimeline(0);
    }

    private void customLoadMoreDataFromApi(int offset) {
        long maxId = 0;
        int count = tweets.size();
        if (count > 0) {
            maxId = tweets.get(count - 1).getUid() - 1;
        }

        populateTimeline(maxId);
    }


    public void onCompose(View view) {
        Intent i = new Intent(getActivity(), ComposeActivity.class);
        // startActivity(i);
        startActivityForResult(i, TWEET_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TWEET_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                Tweet tweet = data.getParcelableExtra("tweet");
                aTweets.insert(tweet, 0);
                // Toast.makeText(this, newTweet, Toast.LENGTH_SHORT).show();
                // populateTimeline(0, 1);
            }
            // else RESULT_CANCELED, nothing changed
        }
    }

    public void addAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    public void clear() {
        aTweets.clear();
    }

    abstract void populateTimeline(long maxId);
}
