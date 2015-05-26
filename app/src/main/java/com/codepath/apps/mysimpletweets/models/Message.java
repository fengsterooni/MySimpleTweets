package com.codepath.apps.mysimpletweets.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Message implements Parcelable {

    private long uid;
    private User recipient;
    private User sender;
    private String createdAt;
    private String body;

    // Deserialize the JSON
    public static Message fromJSON(JSONObject jsonObject) {
        Message message = new Message();

        try {
            message.body = jsonObject.getString("text");
            message.uid = jsonObject.getLong("id");
            message.createdAt = jsonObject.getString("created_at");
            message.recipient = User.findOrCreateFromJson(jsonObject.getJSONObject("recipient"));
            message.sender = User.findOrCreateFromJson(jsonObject.getJSONObject("sender"));

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return message;
    }

    public static ArrayList<Message> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Message> messages = new ArrayList<>();

        JSONObject messageJson = null;
        for (int i = 0; i < jsonArray.length(); i++) {

            try {
                messageJson = jsonArray.getJSONObject(i);
                Message message = Message.fromJSON(messageJson);
                if (message != null) {
                    messages.add(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }

        return messages;
    }

    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getRecipient() {
        return recipient;
    }

    public User getSender() {
        return sender;
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
        dest.writeParcelable(this.recipient, 0);
        dest.writeParcelable(this.sender, 0);
        dest.writeString(this.createdAt);
        dest.writeString(this.body);
    }

    public Message() {
    }

    private Message(Parcel in) {
        this.uid = in.readLong();
        this.recipient = in.readParcelable(User.class.getClassLoader());
        this.sender = in.readParcelable(User.class.getClassLoader());
        this.createdAt = in.readString();
        this.body = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
