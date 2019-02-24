package com.yoyo.makeiteven;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;

public class AudioManager {

    @SuppressLint("StaticFieldLeak")
    private static AudioManager mInstance;
    private MediaPlayer mGameMediaPlayer = new MediaPlayer();
    private MediaPlayer mEffectsMediaPlayer = new MediaPlayer();
    private Context mContext;

    private float mSound_Effects_Volume;
    private float mCurrentMainVolume;


    private AudioManager(Context context) {
        mContext = context.getApplicationContext();
    }

    public static synchronized AudioManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AudioManager( context );
        }
        return mInstance;
    }


    public void startGameMusic() {
        mCurrentMainVolume = (DataStore.getInstance( mContext ).getMainSoundSetting());

        mGameMediaPlayer = MediaPlayer.create( mContext, R.raw.super_duper_by_ian_post );
        mGameMediaPlayer.setVolume( mCurrentMainVolume / 100, (mCurrentMainVolume / 100) );
        mGameMediaPlayer.setLooping( true );
        mGameMediaPlayer.start();
    }

    public void stopGameMusic() {
        mGameMediaPlayer.stop();
    }

    public void pauseGameMusic() {
        mGameMediaPlayer.pause();
    }

    public void setGameVolume(int mainVolume ) {
        mGameMediaPlayer.setVolume( (float) mainVolume / 100, (float) mainVolume / 100 );
    }

    public void setEffectVolume(int effectVolume) {
        mEffectsMediaPlayer.setVolume( (float) effectVolume / 100, (float) effectVolume / 100 );
    }


    public void startTaDaSound() {
        int temp = DataStore.getInstance( mContext ).getSoundEffectSetting();
        mSound_Effects_Volume = (float)(temp / 100);
        mEffectsMediaPlayer = MediaPlayer.create( mContext, R.raw.ta_da );
        mEffectsMediaPlayer.setVolume( mSound_Effects_Volume, mCurrentMainVolume );
        mEffectsMediaPlayer.start();
    }

    public void playBtnOn() {
        int temp = DataStore.getInstance( mContext ).getSoundEffectSetting();
        mSound_Effects_Volume = (float)(temp / 100);
        mEffectsMediaPlayer = MediaPlayer.create( mContext, R.raw.btn_on_sound );
        mEffectsMediaPlayer.setVolume( mSound_Effects_Volume, mSound_Effects_Volume );
        mEffectsMediaPlayer.start();
    }

    public void playBtnOff() {
        int temp = DataStore.getInstance( mContext ).getSoundEffectSetting();
        mSound_Effects_Volume = (float)(temp / 100);
        mGameMediaPlayer = MediaPlayer.create( mContext, R.raw.btn_off_sound );
        mGameMediaPlayer.setVolume( mSound_Effects_Volume, mSound_Effects_Volume );
        mGameMediaPlayer.start();
    }

    public void startWaWaSound() {
        int temp = DataStore.getInstance( mContext ).getSoundEffectSetting();
        mSound_Effects_Volume = (float)(temp / 100);
        mEffectsMediaPlayer = MediaPlayer.create( mContext, R.raw.waa_waa_waaaa );
        mEffectsMediaPlayer.setVolume( mSound_Effects_Volume, mCurrentMainVolume );
        mEffectsMediaPlayer.start();
    }
}
