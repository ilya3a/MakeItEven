package com.yoyo.makeiteven;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

public class LevelsActivity extends AppCompatActivity {

    private List<Level> levelItems;
    private int mCurrentStage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            TransitionInflater inflater = TransitionInflater.from(this);
            Transition transition = inflater.inflateTransition(R.transition.transition_a);
            getWindow().setExitTransition(transition);
            Explode explode = new Explode();
            explode.setDuration(600);
            getWindow().setEnterTransition(explode);
        }

        mCurrentStage = DataStore.getInstance(this).getCurrentStage();
        setContentView(R.layout.activity_levels);

        levelItems = new ArrayList<>();
        for (int i = 1; i < 101; ++i)
            levelItems.add(new Level(i));


        initRecyclerView();
        ImageButton gameBackLevelsBtn = (ImageButton) findViewById(R.id.game_back_btn_levels_screen);
        gameBackLevelsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentStage = DataStore.getInstance(this).getCurrentStage();
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_levels_list);
        LevelAdapter adapter = new LevelAdapter(levelItems, this, mCurrentStage);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(LevelsActivity.this, 4));
    }

    public static void startLevelsActivity(Context context) {
        Intent intent = new Intent(context, LevelsActivity.class);
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context);
        context.startActivity(intent, compat.toBundle());

    }
}
