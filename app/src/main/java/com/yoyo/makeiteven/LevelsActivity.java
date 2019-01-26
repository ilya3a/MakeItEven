package com.yoyo.makeiteven;

import android.graphics.Rect;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class LevelsActivity extends AppCompatActivity {

    private List<Level_item> levelItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            TransitionInflater inflater = TransitionInflater.from(this);
            Transition transition = inflater.inflateTransition(R.transition.transition_a);
            getWindow().setExitTransition(transition);
            Explode explode = new Explode();
            explode.setDuration(1000);
            getWindow().setEnterTransition(explode);


        }

        setContentView(R.layout.activity_levels);
        levelItems = new ArrayList<>();
        for (int i = 1; i < 40; ++i)
            levelItems.add(new Level_item(i));

        initRecyclerView();


//        final Button button = findViewById(R.id.button2);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View clickedView) {
//                // save rect of view in screen coordinates
//                final Rect viewRect = new Rect();
//                clickedView.getGlobalVisibleRect(viewRect);
//
//                // create Explode transition with epicenter
//                Explode explode = null;
//
//                explode = new Explode();
//
//                explode.setEpicenterCallback(new Transition.EpicenterCallback() {
//                    @Override
//                    public Rect onGetEpicenter(Transition transition) {
//                        return viewRect;
//                    }
//                });
//
//                explode.setDuration(1000);
//
//                TransitionManager.beginDelayedTransition(recyclerView, explode);
//
//                // remove all views from Recycler View
//                recyclerView.setAdapter(null);
//            }
//        });
    }


    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_levels_list);
        LevelAdapter adapter = new LevelAdapter(levelItems, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(LevelsActivity.this, 6));
    }
}