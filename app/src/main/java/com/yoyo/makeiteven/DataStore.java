package com.yoyo.makeiteven;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Comment;

import java.io.File;
import java.lang.reflect.Type;
import java.sql.DatabaseMetaData;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static android.support.constraint.Constraints.TAG;

public class DataStore {


    private static DataStore sInstance;
    static final String SHARED_KEY_SCORE_BOARD = "scoreBoard";
    static final String SHARED_KEY_STAGE_INFO = "stageInfo";
    static final String CURRENT_STAGE = "currentStage";
    static final String PREFS = "sharedPref";
    static final String HINTS_LEFT = "hints_left";

    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEditor;
    private Gson mGson = new Gson();
    //firebase
    private FirebaseStorage storage = FirebaseStorage.getInstance();

    public static DataStore getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DataStore(context);
        }
        return sInstance;
    }

    @SuppressLint("CommitPrefEdits")
    private DataStore(Context context) {
        mSharedPref = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        mEditor = mSharedPref.edit();
    }

    public List<ScoreBoard> getScoreBoardList() {
        String scoreBoardJsonList = mSharedPref.getString(SHARED_KEY_SCORE_BOARD, "[]");
        Type type = new TypeToken<ArrayList<ScoreBoard>>() {
        }.getType();

        // convert the json String to Java ArrayList object
        return mGson.fromJson(scoreBoardJsonList, type);
    }

    public void saveNameAndScore(String nickName, int scoreCounter) {
        //firebaseinstans
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("Scores");
        final ScoreBoard scoreBoard = new ScoreBoard(nickName, scoreCounter);
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                // A new comment has been added, add it to the displayed list
                ScoreBoard comment = dataSnapshot.getValue(ScoreBoard.class);

                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so displayed the changed comment.
                ScoreBoard newComment = dataSnapshot.getValue(ScoreBoard.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                // A comment has changed, use the key to determine if we are displaying this
                // comment and if so remove it.
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                // A comment has changed position, use the key to determine if we are
                // displaying this comment and if so move it.
                ScoreBoard movedComment = dataSnapshot.getValue(ScoreBoard.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        myRef.addChildEventListener(childEventListener);
//        // get instance of shared prefs with relevant key
//        String scoreBoardJsonList = mSharedPref.getString(DataStore.SHARED_KEY_SCORE_BOARD, "[]");
//        Type type = new TypeToken<ArrayList<ScoreBoard>>() {
//        }.getType();
//
//        // convert the json String to Java ArrayList object
//        ArrayList<ScoreBoard> scoreBoards = mGson.fromJson(scoreBoardJsonList, type);
//        // manipulate the java arrayList
//        scoreBoards.add(scoreBoard);
//
//        Collections.sort(scoreBoards, scoreBoard);
//
//        // convert the array list back to json string
//        String jsonUpdatedScoreBoard = mGson.toJson(scoreBoards);
//
//        // save the json string back to the shared prefs
//        mEditor.putString(DataStore.SHARED_KEY_SCORE_BOARD, jsonUpdatedScoreBoard);
//        //save to database
//        myRef.setValue(scoreBoards);
//        //
//        mEditor.apply();
    }

    public void saveVolumeSetting(int mainSound, int soundEffects) {

        mEditor.putInt("mainSound", mainSound);
        mEditor.putInt("soundEffects", soundEffects);
        mEditor.apply();
    }

    public int getMainSoundSetting() {
        return mSharedPref.getInt("mainSound", 60);
    }

    public int getSoundEffectSetting() {

        return mSharedPref.getInt("soundEffects", 100);
    }

    public void saveCurrentStage(int currentStage) {
        mEditor.putInt(CURRENT_STAGE, currentStage);
        mEditor.apply();
    }

    public int getCurrentStage() {

        return mSharedPref.getInt(CURRENT_STAGE, 1);
    }

    public void saveStageInfo(StageInfo currentStageToSave) {
        ArrayList<StageInfo> stageInfos;
        String stageInfoJsonList = mSharedPref.getString(DataStore.SHARED_KEY_STAGE_INFO, "[]");
        if (!stageInfoJsonList.equals("[]")) {
            stageInfos = mGson.fromJson(stageInfoJsonList, new TypeToken<ArrayList<StageInfo>>() {
            }.getType());
        } else {
            stageInfos = new ArrayList<>();
        }
        stageInfos.add(currentStageToSave);
        String jsonUpdatedStageInfo = mGson.toJson(stageInfos);
        mEditor.putString(SHARED_KEY_STAGE_INFO, jsonUpdatedStageInfo);
        mEditor.commit();
    }

    public ArrayList<StageInfo> getStageInfo() {
        String stageInfoJsonList = mSharedPref.getString(DataStore.SHARED_KEY_STAGE_INFO, "[]");
        ArrayList<StageInfo> stageInfos;

        if (stageInfoJsonList.equals("[]")) {
            stageInfos = new ArrayList<>();
            return stageInfos;
        }

        stageInfos = mGson.fromJson(stageInfoJsonList, new TypeToken<ArrayList<StageInfo>>() {
        }.getType());
        return stageInfos;
    }

    public void resetLevels() {
        ArrayList<StageInfo> stageInfos = new ArrayList<>();
        String jsonCleanStageInfo = mGson.toJson(stageInfos);
        mEditor.putString(SHARED_KEY_STAGE_INFO, jsonCleanStageInfo);
        mEditor.commit();

    }

    public int getNumHintsLeft() {
        return mSharedPref.getInt(HINTS_LEFT, 3);
    }
    public void saveNumHintsLeft(int numOfHints){
        mEditor.putInt(HINTS_LEFT,numOfHints).commit();
    }
}
