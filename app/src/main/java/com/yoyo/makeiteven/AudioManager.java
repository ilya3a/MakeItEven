package com.yoyo.makeiteven;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;

public class AudioManager {

    @SuppressLint("StaticFieldLeak")
    private static AudioManager mInstance;
    private MediaPlayer mMediaPlayer = new MediaPlayer();
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
        mMediaPlayer = MediaPlayer.create( mContext, R.raw.super_duper_by_ian_post );
        mMediaPlayer.setVolume( mCurrentMainVolume / 100, (mCurrentMainVolume / 100) );
        mMediaPlayer.setLooping( true );
        mMediaPlayer.start();
    }

    public void stopGameMusic() {
        mMediaPlayer.stop();
    }

    public void pauseGameMusic() {
        mMediaPlayer.pause();
    }

    public void setGameVolume(int mainVolume) {
        mMediaPlayer.setVolume( (float) mainVolume / 100, (float) mainVolume / 100 );
    }

    public void startTaDaSound() {
        mSound_Effects_Volume = (float) (DataStore.getInstance( mContext ).getSoundEffectSetting() / 100);
        mMediaPlayer = MediaPlayer.create( mContext, R.raw.ta_da );
        mMediaPlayer.setVolume( mSound_Effects_Volume, mCurrentMainVolume );
        mMediaPlayer.start();
    }

    public void playBtnOn() {
        mSound_Effects_Volume = (float) (DataStore.getInstance( mContext ).getSoundEffectSetting() / 100);
        mMediaPlayer = MediaPlayer.create( mContext, R.raw.btn_on_sound );
        mMediaPlayer.setVolume( mSound_Effects_Volume, mSound_Effects_Volume );
        mMediaPlayer.start();
    }

    public void playBtnOff() {
        mSound_Effects_Volume = (float) (DataStore.getInstance( mContext ).getSoundEffectSetting() / 100);
        mMediaPlayer = MediaPlayer.create( mContext, R.raw.btn_off_sound );
        mMediaPlayer.setVolume( mSound_Effects_Volume, mSound_Effects_Volume );
        mMediaPlayer.start();
    }

    public void startWaWaSound() {
        mSound_Effects_Volume = (float) (DataStore.getInstance( mContext ).getSoundEffectSetting() / 100);
        mMediaPlayer = MediaPlayer.create( mContext, R.raw.waa_waa_waaaa );
        mMediaPlayer.setVolume( mSound_Effects_Volume, mCurrentMainVolume );
        mMediaPlayer.start();
    }
}
