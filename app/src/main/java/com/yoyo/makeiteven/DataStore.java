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

    private static DataStore sInstance;
    static final String SHARED_KEY_SCORE_BOARD = "scoreBoard";
    static final String CURRENT_STAGE = "currentStage";
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

    public List<ScoreBoard> getScoreBoardList() {
        String scoreBoardJsonList = mSharedPref.getString( SHARED_KEY_SCORE_BOARD, "[]" );
        Type type = new TypeToken<ArrayList<ScoreBoard>>() {
        }.getType();

        // convert the json String to Java ArrayList object
        return mGson.fromJson( scoreBoardJsonList, type );
    }

    public void saveCurrentStage(int currentStage) {
        mEditor.putInt( CURRENT_STAGE, currentStage );
        mEditor.apply();
    }

    public int getCurrentStage() {

        return mSharedPref.getInt( CURRENT_STAGE, 1 );
    }
}
