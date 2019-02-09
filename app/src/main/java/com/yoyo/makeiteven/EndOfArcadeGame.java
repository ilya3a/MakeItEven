package com.yoyo.makeiteven;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;

import static com.yoyo.makeiteven.GameActivity.EXTRA_SCORE;

public class EndOfArcadeGame extends AppCompatActivity {


    EditText nickNameEt;
    TextView finalScoreTv;
    private int score;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_end_of_arcade_game );

        finalScoreTv = findViewById( R.id.final_score_tv );
        nickNameEt = findViewById( R.id.nickname_et );

        score = getIntent().getIntExtra( EXTRA_SCORE, 0 );
        finalScoreTv.setText( score + "" );



        Button playAgainBtn = findViewById( R.id.play_again_btn );
        playAgainBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveName();
                GameActivity.startGameActivity( EndOfArcadeGame.this, ArcadeGame.TYPE );
                finish();
            }
        } );


        ImageButton scoreBoardBtn = findViewById( R.id.scoreBoard_ib );
        scoreBoardBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveName();
                Intent intent = new Intent( EndOfArcadeGame.this, ScoreBoardActivity.class );
                startActivity( intent );
                finish();
            }
        } );


    }

    private void saveName() {
        nickname = nickNameEt.getText().toString();
        ScoreBoard scoreBoard = new ScoreBoard( nickname, score );
        // get instance of shared prefs with relevant key
        SharedPreferences sharedPref = getSharedPreferences( DataStore.PREFS, MODE_PRIVATE );
        String scoreBoardJsonList = sharedPref.getString( DataStore.SHARED_KEY_SCORE_BOARD, "[]" );

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ScoreBoard>>() {
        }.getType();

        // convert the json String to Java ArrayList object
        ArrayList<ScoreBoard> scoreBoards = gson.fromJson( scoreBoardJsonList, type );

        // manipulate the java arrayList
        scoreBoards.add( scoreBoard );

        Collections.sort( scoreBoards, scoreBoard );

        // convert the array list back to json string
        String jsonUpdatedScoreBoard = gson.toJson( scoreBoards );

        SharedPreferences.Editor editor = sharedPref.edit();

        // save the json string back to the shared prefs
        editor.putString( DataStore.SHARED_KEY_SCORE_BOARD, jsonUpdatedScoreBoard );
        editor.apply();
    }
}

