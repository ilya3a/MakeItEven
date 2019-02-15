package com.yoyo.makeiteven;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;

import java.util.ArrayList;
import java.util.List;

public class LevelsActivity extends AppCompatActivity {

    private List<Level> levelItems;
    private int mCurrentStage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (Build.VERSION.SDK_INT >= 21) {
            TransitionInflater inflater = TransitionInflater.from( this );
            Transition transition = inflater.inflateTransition( R.transition.transition_a );
            getWindow().setExitTransition( transition );
            Explode explode = new Explode();
            explode.setDuration( 600 );
            getWindow().setEnterTransition( explode );
        }
        mCurrentStage = DataStore.getInstance( this ).getCurrentStage();
        setContentView( R.layout.activity_levels );

        levelItems = new ArrayList<>();
        for (int i = 1; i < 101; ++i)
            levelItems.add( new Level( i ) );


        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById( R.id.recycler_levels_list );
        LevelAdapter adapter = new LevelAdapter( levelItems, this, mCurrentStage );
        recyclerView.setAdapter( adapter );
        recyclerView.setLayoutManager( new GridLayoutManager( LevelsActivity.this, 4 ) );
    }

    public static void startLevelsActivity(Context context) {
        Intent intent = new Intent( context, LevelsActivity.class );
        context.startActivity( intent );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        StartScreenActivity.startStartScreenActivity( this );
    }
}
