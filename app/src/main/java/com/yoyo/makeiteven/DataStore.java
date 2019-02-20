package com.yoyo.makeiteven;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.provider.ContactsContract;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataStore {

    private static DataStore sInstance;
    static final String SHARED_KEY_SCORE_BOARD = "scoreBoard";
    static final String SHARED_KEY_STAGE_INFO="stageInfo";
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

    public void saveNameAndScore(String nickName, int scoreCounter) {

        ScoreBoard scoreBoard = new ScoreBoard( nickName, scoreCounter );
        // get instance of shared prefs with relevant key
        String scoreBoardJsonList = mSharedPref.getString( DataStore.SHARED_KEY_SCORE_BOARD, "[]" );
        Type type = new TypeToken<ArrayList<ScoreBoard>>() {
        }.getType();
        // convert the json String to Java ArrayList object
        ArrayList<ScoreBoard> scoreBoards = mGson.fromJson( scoreBoardJsonList, type );

        // manipulate the java arrayList
        scoreBoards.add( scoreBoard );

        Collections.sort( scoreBoards, scoreBoard );

        // convert the array list back to json string
        String jsonUpdatedScoreBoard = mGson.toJson( scoreBoards );

        SharedPreferences.Editor editor = mSharedPref.edit();

        // save the json string back to the shared prefs
        editor.putString( DataStore.SHARED_KEY_SCORE_BOARD, jsonUpdatedScoreBoard );
        editor.apply();
    }
    public void saveVolumeSetting (int mainSound,int soundEffects){

        mEditor.putInt("mainSound",mainSound);
        mEditor.putInt("soundEffects",soundEffects);
        mEditor.apply();
    }
    public int getMainSoundSetting (){
        return mSharedPref.getInt("mainSound",100);
    }
    public int getSoundEffectSetting (){

        return mSharedPref.getInt("soundEffects",100);
    }

    public void saveCurrentStage(int currentStage) {
        mEditor.putInt( CURRENT_STAGE, currentStage );
        mEditor.apply();
    }

    public int getCurrentStage() {

        return mSharedPref.getInt( CURRENT_STAGE, 1 );
    }

    public  void saveStageInfo (StageInfo currentStageToSave){
        ArrayList<StageInfo> stageInfos;
        String stageInfoJsonList=mSharedPref.getString(DataStore.SHARED_KEY_STAGE_INFO,"[]");
        if (!stageInfoJsonList.equals("[]")) {
            stageInfos = mGson.fromJson(stageInfoJsonList, new TypeToken<ArrayList<StageInfo>>() {
            }.getType());
        }else {
            stageInfos=new ArrayList<>();
        }
        stageInfos.add(currentStageToSave);
        String jsonUpdatedStageInfo = mGson.toJson( stageInfos );
        mEditor.putString(SHARED_KEY_STAGE_INFO,jsonUpdatedStageInfo);
        mEditor.commit();
    }
    public ArrayList<StageInfo> getStageInfo (){
        String stageInfoJsonList=mSharedPref.getString(DataStore.SHARED_KEY_STAGE_INFO,"[]");
        ArrayList<StageInfo> stageInfos=mGson.fromJson(stageInfoJsonList,new TypeToken<ArrayList<StageInfo>>() {
        }.getType());
        return stageInfos;
    }
}
