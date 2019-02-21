package com.yoyo.makeiteven;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import moe.codeest.enviews.ENVolumeView;

public class SettingFragment extends Fragment {
    private ENVolumeView mainVolumeView,soundEffectView;
    private SettingsFragmentListener listener;
    private SeekBar mainVolumeBar,soundEffectsBar;
    private Button resetGameBtn;
    private ImageButton exitBtn;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listener=(SettingsFragmentListener) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting,container,false);
        final Animation btn_press = AnimationUtils.loadAnimation( inflater.getContext(), R.anim.btn_pressed );
        final Animation btn_releas = AnimationUtils.loadAnimation( inflater.getContext(), R.anim.btn_realeas );

        resetGameBtn=rootView.findViewById(R.id.game_reset_btn);
        mainVolumeBar=rootView.findViewById(R.id.main_sound_seekbar);
        soundEffectsBar=rootView.findViewById(R.id.soundEffect_bar);
        mainVolumeView=rootView.findViewById(R.id.view_volume);
        soundEffectView=rootView.findViewById(R.id.view_sound_efects);
        exitBtn=rootView.findViewById(R.id.close_setting_btn);

        mainVolumeView.updateVolumeValue(DataStore.getInstance(inflater.getContext()).getMainSoundSetting());
        soundEffectView.updateVolumeValue(DataStore.getInstance(inflater.getContext()).getSoundEffectSetting());
        mainVolumeBar.setProgress(DataStore.getInstance(inflater.getContext()).getMainSoundSetting());
        soundEffectsBar.setProgress(DataStore.getInstance(inflater.getContext()).getSoundEffectSetting());

            SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(seekBar.getId()==R.id.main_sound_seekbar) {
                    DataStore.getInstance(inflater.getContext()).saveVolumeSetting(progress, soundEffectsBar.getProgress());
                    mainVolumeView.updateVolumeValue(progress);
                }
                else {
                    DataStore.getInstance(inflater.getContext()).saveVolumeSetting(mainVolumeBar.getProgress(), progress);
                    soundEffectView.updateVolumeValue(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        };
            soundEffectsBar.setOnSeekBarChangeListener(seekBarListener);
            mainVolumeBar.setOnSeekBarChangeListener(seekBarListener);

        final View.OnTouchListener btn_animation = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.startAnimation( btn_press );
                    btn_press.setFillAfter( true );
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.startAnimation( btn_releas );
                }
                return false;
            }
        };
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction().remove(SettingFragment.this).commit();
                listener.OnExit();
            }
        });

        resetGameBtn.setOnTouchListener(btn_animation);
        resetGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnResetGame();

            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        if (context instanceof SettingFragment.SettingsFragmentListener) {
            listener = (SettingsFragmentListener) context;
        } else {
            throw new RuntimeException( context.toString() + "The activity must implement onSignUpListener interface" );
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    interface SettingsFragmentListener{
        void OnSeekBarMainVolume(int mainVolume);
        void OnSeekBarSoundEffects(int soundEffectsVolume);
        void OnResetGame ();
        void OnExit();
    }
}