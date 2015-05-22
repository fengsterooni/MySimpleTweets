package com.codepath.apps.mysimpletweets.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

@Table(name = "User")
public class User extends Model implements Parcelable{

    @Column(name = "Uid", unique = true)
    private long uid;
    @Column(name = "Name")
    private String name;
    @Column(name = "ScreeName")
    private String screenName;
    @Column(name = "ProfileImageUrl")
    private String profileImageUrl;
    @Column(name = "BackgroundImageUrl")
    private String backgroundImageUrl;
    @Column(name = "TagLine")
    private String tagLine;
    @Column(name = "Follower")
    private int followersCount;
    @Column(name = "Following")
    private int followingsCount;

    public static User fromJSON(JSONObject json) {
        User user = new User();
        try {
            user.name = json.getString("name");
            user.uid = json.getLong("id");
            user.screenName = json.getString("screen_name");
            user.profileImageUrl = json.getString("profile_image_url");
            user.backgroundImageUrl = json.getString("profile_background_image_url");
            user.tagLine = json.getString("description");
            user.followersCount = json.getInt("followers_count");
            user.followingsCount = json.getInt("friends_count");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static User findOrCreateFromJson(JSONObject json) {
        User user = User.fromJSON(json);

        User existingUser =
                new Select().from(User.class).where("Uid = ?", user.uid).executeSingle();
        if (existingUser != null)
            return existingUser;
        else {
            user.save();
            return user;
        }
    }

    public static ArrayList<User> fromJSONArray(JSONArray jsonArray) {
        ArrayList<User> users = new ArrayList<>();

        JSONObject userJson = null;
        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                userJson = jsonArray.getJSONObject(i);
                User user = User.fromJSON(userJson);
                if (user != null) {
                    users.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        return users;
    }

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public long getUid() {
        return uid;
    }


    public int getFollowersCount() {
        return followersCount;
    }

    public int getFriendsCount() {
        return followingsCount;
    }

    public String getTagLine() {
        return tagLine;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.uid);
        dest.writeString(this.name);
        dest.writeString(this.screenName);
        dest.writeString(this.profileImageUrl);
        dest.writeString(this.backgroundImageUrl);
        dest.writeString(this.tagLine);
        dest.writeInt(this.followersCount);
        dest.writeInt(this.followingsCount);
    }

    public User() {
    }

    private User(Parcel in) {
        this.uid = in.readLong();
        this.name = in.readString();
        this.screenName = in.readString();
        this.profileImageUrl = in.readString();
        this.backgroundImageUrl = in.readString();
        this.tagLine = in.readString();
        this.followersCount = in.readInt();
        this.followingsCount = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
