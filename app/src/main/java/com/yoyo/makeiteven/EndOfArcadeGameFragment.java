package com.yoyo.makeiteven;

import android.app.Fragment;
import android.content.Context;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static com.yoyo.makeiteven.GameActivity.SCORE_COUNTER;

public class EndOfArcadeGameFragment extends Fragment {

    TextInputLayout nickNameEt;
    TextView finalScoreTv;
    ImageButton playAgainIb, homeIb,scoreBoardIb;
    private EndOfArcadeGameFragmentListener listener;


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listener = (EndOfArcadeGameFragmentListener) getActivity();

    }

    interface EndOfArcadeGameFragmentListener {
        void onArcadeGameEndAndPlayAgain(String nickName);

        void onArcadeGameEndAndGoToHome(String nickName);

        void onArcadeGameEndAndGoToScoreboard(String nickName);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EndOfArcadeGameFragmentListener) {
            listener = (EndOfArcadeGameFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString() + "The activity must implement onSignUpListener interface");
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        int finalScoreFromGameActivity = bundle.getInt(SCORE_COUNTER, 123);
        View rootView = inflater.inflate(R.layout.fragment_end_of_arcade_game, container, false);

        finalScoreTv = rootView.findViewById(R.id.final_score_tv);
        finalScoreTv.setText(finalScoreFromGameActivity + "");
        nickNameEt = rootView.findViewById(R.id.nickname_et);
        playAgainIb = rootView.findViewById(R.id.play_again_btn);
        homeIb = rootView.findViewById(R.id.play_stage_mode_btn);
        scoreBoardIb = rootView.findViewById(R.id.scoreBoard_ib);
        final Animation btn_press = AnimationUtils.loadAnimation(inflater.getContext(), R.anim.btn_pressed);
        final Animation btn_release = AnimationUtils.loadAnimation(inflater.getContext(), R.anim.btn_realeas);
        final View.OnTouchListener btn_animation = new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.startAnimation(btn_press);
                    btn_press.setFillAfter(true);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    v.startAnimation(btn_release);
                }
                return false;
            }
        };
        playAgainIb.setOnTouchListener(btn_animation);
        playAgainIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(nickNameEt.getEditText().getText().toString())) {
                    listener.onArcadeGameEndAndPlayAgain(nickNameEt.getEditText().getText().toString());

                } else {
                    //Toast.makeText(getActivity(), "please enter nickname", Toast.LENGTH_SHORT).show();
                    nickNameEt.setErrorEnabled(true);
                    nickNameEt.setError("this field required");
                }
            }
        });

        homeIb.setOnTouchListener(btn_animation);
        homeIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(nickNameEt.getEditText().getText().toString())) {
                    listener.onArcadeGameEndAndGoToHome(nickNameEt.getEditText().getText().toString());

                } else {
                    //Toast.makeText(getActivity(), "please enter nickname", Toast.LENGTH_SHORT).show();
                    nickNameEt.setErrorEnabled(true);
                    nickNameEt.setError("this field required");
                }
            }
        });

        scoreBoardIb.setOnTouchListener(btn_animation);
        scoreBoardIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(nickNameEt.getEditText().getText().toString())) {
                    listener.onArcadeGameEndAndGoToScoreboard(nickNameEt.getEditText().getText().toString());
                } else {
                    //Toast.makeText(getActivity(), "please enter nickname", Toast.LENGTH_SHORT).show();
                    nickNameEt.setErrorEnabled(true);
                    nickNameEt.setError("this field required");
                }
            }
        });

        return rootView;
    }

//    private void saveName() {
//        nickname = nickNameEt.getText().toString();
//        ScoreBoard scoreBoard = new ScoreBoard( nickname, score );
//        // get instance of shared prefs with relevant key
//        SharedPreferences sharedPref = getSharedPreferences( DataStore.PREFS, MODE_PRIVATE );
//        String scoreBoardJsonList = sharedPref.getString( DataStore.SHARED_KEY_SCORE_BOARD, "[]" );
//
//        Gson gson = new Gson();
//        Type type = new TypeToken<ArrayList<ScoreBoard>>() {
//        }.getType();
//
//        // convert the json String to Java ArrayList object
//        ArrayList<ScoreBoard> scoreBoards = gson.fromJson( scoreBoardJsonList, type );
//
//        // manipulate the java arrayList
//        scoreBoards.add( scoreBoard );
//
//        Collections.sort( scoreBoards, scoreBoard );
//
//        // convert the array list back to json string
//        String jsonUpdatedScoreBoard = gson.toJson( scoreBoards );
//
//        SharedPreferences.Editor editor = sharedPref.edit();
//
//        // save the json string back to the shared prefs
//        editor.putString( DataStore.SHARED_KEY_SCORE_BOARD, jsonUpdatedScoreBoard );
//        editor.apply();
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
