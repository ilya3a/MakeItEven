package com.yoyo.makeiteven;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private static final String SHARED_KEY_NEW_USER = "shared_key_new_user";
    private static DataStore sInstance;
    static final String SHARED_KEY_SCORE_BOARD = "scoreBoard";
    static final String PREFS = "sharedPref";

    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEditor;
    private Gson mGson = new Gson();

    public static DataStore getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DataStore( context );
        }
        return sInstance;
    }

    @SuppressLint("CommitPrefEdits")
    private DataStore(Context context) {
        mSharedPref = context.getSharedPreferences( PREFS, Context.MODE_PRIVATE );
        mEditor = mSharedPref.edit();
    }

    public SharedPreferences getSharedPref() {
        return mSharedPref;
    }

    public List<ScoreBoard> getScoreBoardList() {
        String scoreBoardJsonList = mSharedPref.getString( SHARED_KEY_SCORE_BOARD, "[]" );
        Type type = new TypeToken<ArrayList<ScoreBoard>>() {
        }.getType();

        // convert the json String to Java ArrayList object
        return mGson.fromJson( scoreBoardJsonList, type );
    }

    public void saveUser(User user) {
        String userString = mGson.toJson( user );
        mEditor.putString( SHARED_KEY_NEW_USER, userString );
        mEditor.apply();
    }
}
