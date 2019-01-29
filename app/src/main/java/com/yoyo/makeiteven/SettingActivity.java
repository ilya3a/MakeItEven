package com.yoyo.makeiteven;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import moe.codeest.enviews.ENVolumeView;

public class SettingActivity extends Activity {
    ENVolumeView volumeView;
    SeekBar sbVolume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
//        volumeView =  findViewById(R.id.view_volume);
//        sbVolume =  findViewById(R.id.seek_bar_1);
//
//        sbVolume.setMax(1000);
//
//        sbVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                volumeView.updateVolumeValue(i+100);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
    }

}