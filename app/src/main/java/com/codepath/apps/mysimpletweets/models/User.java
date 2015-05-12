package com.codepath.apps.mysimpletweets.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static User fromJSON(JSONObject json) {
        User user = new User();
        try {
            user.name = json.getString("name");
            user.uid = json.getLong("id");
            user.screenName = json.getString("screen_name");
            user.profileImageUrl = json.getString("profile_image_url");
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

    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public long getUid() {
        return uid;
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
    }

    public User() {
    }

    private User(Parcel in) {
        this.uid = in.readLong();
        this.name = in.readString();
        this.screenName = in.readString();
        this.profileImageUrl = in.readString();
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
