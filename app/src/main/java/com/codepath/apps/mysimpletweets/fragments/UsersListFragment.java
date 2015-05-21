package com.codepath.apps.mysimpletweets.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.activities.ProfileActivity;
import com.codepath.apps.mysimpletweets.adapters.UsersArrayAdapter;
import com.codepath.apps.mysimpletweets.listeners.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.models.User;
import com.codepath.apps.mysimpletweets.models.UserList;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public abstract class UsersListFragment extends Fragment {
    private ArrayList<User> users;
    private UsersArrayAdapter aUsers;
    protected UserList list = null;

    @InjectView(R.id.lvUsers)
    ListView lvUsers;
    @InjectView(R.id.swipeContainter)
    SwipeRefreshLayout swipeContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users_list, parent, false);
        ButterKnife.inject(this, view);

        lvUsers.setAdapter(aUsers);
        lvUsers.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
            }
        });

        lvUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = users.get(position);
                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (TwitterApplication.isNetworkAvailable()) {
                    // fetchTimelineAsync(0);
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
        users = new ArrayList<>();
        aUsers = new UsersArrayAdapter(getActivity(), users);
    }


    private void customLoadMoreDataFromApi(int offset) {
        if (list != null) {
            long next_cursor = list.getNextCursor();
            if (next_cursor != 0)
                populateUserList(next_cursor);
        }
    }

    abstract void populateUserList(long cursor);

    public void addAll(List<User> users) {
        aUsers.addAll(users);
    }
}
