package com.yoyo.makeiteven;

import android.app.Fragment;
import android.content.Context;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import static com.yoyo.makeiteven.GameActivity.SCORE_COUNTER;

public class EndOfArcadeGameFragment extends Fragment {

    EditText nickNameEt;
    TextView finalScoreTv;
    Button playAgainBtn, playStageBtn;
    ImageButton scoreBoardIb;
    private EndOfArcadeGameFragmentListener listener;



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        listener = (EndOfArcadeGameFragmentListener) getActivity();

    }

    interface EndOfArcadeGameFragmentListener {
        void onArcadeGameEndAndPlayAgain(String nickName);
        void onArcadeGameEndAndPlayStage(String nickName);
        void onArcadeGameEndAndGoToScoreboard(String nickName);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach( context );
        if (context instanceof EndOfArcadeGameFragmentListener) {
            listener = (EndOfArcadeGameFragmentListener) context;
        } else {
            throw new RuntimeException( context.toString() + "The activity must implement onSignUpListener interface" );
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        int finalScoreFromGameActivity = bundle.getInt( SCORE_COUNTER, 123 );
        View rootView = inflater.inflate( R.layout.end_of_arcade_game_fragment, container, false );

        finalScoreTv = rootView.findViewById( R.id.final_score_tv );
        finalScoreTv.setText( finalScoreFromGameActivity + "" );
        nickNameEt = rootView.findViewById( R.id.nickname_et );
        playAgainBtn = rootView.findViewById( R.id.play_again_btn );
        playStageBtn = rootView.findViewById( R.id.play_stage_mode_btn );
        scoreBoardIb = rootView.findViewById( R.id.scoreBoard_ib );

        playAgainBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty( nickNameEt.getText().toString() )) {
                    listener.onArcadeGameEndAndPlayAgain( nickNameEt.getText().toString() );

                } else {
                    Toast.makeText( getActivity(), "please enter nickname", Toast.LENGTH_SHORT ).show();
                }
            }
        } );


        playStageBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty( nickNameEt.getText().toString() )) {
                    listener.onArcadeGameEndAndPlayStage( nickNameEt.getText().toString() );

                } else {
                    Toast.makeText( getActivity(), "please enter nickname", Toast.LENGTH_SHORT ).show();
                }
            }
        } );


        scoreBoardIb.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty( nickNameEt.getText().toString() )) {
                    listener.onArcadeGameEndAndGoToScoreboard( nickNameEt.getText().toString() );
                } else {
                    Toast.makeText( getActivity(), "please enter nickname", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

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

        super.onCreate( savedInstanceState );
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
