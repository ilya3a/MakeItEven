package com.yoyo.makeiteven;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EndOfArcadeGame extends AppCompatActivity {

    static final String EXTRA_SCORE = "extra_score";
    EditText nickNameEt;
    TextView finalScoreTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_end_of_arcade_game );

        finalScoreTv = findViewById( R.id.final_score_tv );
        nickNameEt = findViewById( R.id.nickname_et );

        final int score = getIntent().getIntExtra( EXTRA_SCORE, 0 );
        final String nickname = nickNameEt.getText().toString();
        finalScoreTv.setText( score );

        Button nextBtn = findViewById( R.id.next_btn );
        nextBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( EndOfArcadeGame.this, ScoreBoardActivity.class );
                ScoreBoard scoreBoard = new ScoreBoard( nickname, score );

                startActivity( intent );
                finish();
            }
        } );


    }
}

