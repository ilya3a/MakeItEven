package com.yoyo.makeiteven;

import com.google.gson.annotations.SerializedName;

class User {

    @SerializedName("username")
    String mUserName;

    public User(String userName) {
        mUserName = userName;
    }

    public String getmUserName() {
        return mUserName;
    }
}
