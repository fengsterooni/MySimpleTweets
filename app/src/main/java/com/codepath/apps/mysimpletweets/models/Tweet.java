package com.codepath.apps.mysimpletweets.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@Table(name = "Tweet")
public class Tweet extends Model implements Parcelable {

    @Column(name = "Uid", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private long uid;
    @Column(name = "User", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private User user;
    @Column(name = "CreatedAt")
    private String createdAt;
    @Column(name = "Body")
    private String body;
    @Column(name = "RetweetCount")
    private long numRetweet;
    @Column(name = "FavoriteCount")
    private long numFavorites;

    // Deserialize the JSON
    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.findOrCreateFromJson(jsonObject.getJSONObject("user"));
            tweet.numRetweet = jsonObject.getLong("retweet_count");
            tweet.numFavorites = jsonObject.getLong("favorite_count");

            tweet.save();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();

        JSONObject tweetJson = null;
        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if (tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        return tweets;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    public long getNumFavorites() {
        return numFavorites;
    }

    public long getNumRetweet() {
        return numRetweet;
    }

    public Tweet() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.uid);
        dest.writeParcelable(this.user, 0);
        dest.writeString(this.createdAt);
        dest.writeString(this.body);
        dest.writeLong(this.numRetweet);
        dest.writeLong(this.numFavorites);
    }

    private Tweet(Parcel in) {
        this.uid = in.readLong();
        this.user = in.readParcelable(User.class.getClassLoader());
        this.createdAt = in.readString();
        this.body = in.readString();
        this.numRetweet = in.readLong();
        this.numFavorites = in.readLong();
    }

    public static final Creator<Tweet> CREATOR = new Creator<Tweet>() {
        public Tweet createFromParcel(Parcel source) {
            return new Tweet(source);
        }

        public Tweet[] newArray(int size) {
            return new Tweet[size];
        }
    };
}
