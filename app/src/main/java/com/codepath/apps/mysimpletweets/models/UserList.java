package com.codepath.apps.mysimpletweets.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class UserList {
    private long nextCursor;
    private ArrayList<User> users;

    public static UserList fromJSON(JSONObject json) {
        UserList list = new UserList();
        try {
            list.nextCursor = json.getLong("next_cursor");
            list.users = User.fromJSONArray(json.getJSONArray("users"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public long getNextCursor() {
        return nextCursor;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
