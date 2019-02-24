package com.yoyo.makeiteven;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreBoardActivity extends ListActivity {

    ListView lv;
    ScoreBoardAdapter scoreboardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        lv = findViewById(android.R.id.list);

        scoreboardAdapter = new ScoreBoardAdapter(this, R.layout.score_cell, DataStore.getInstance(this).getScoreBoardList());
        lv.setAdapter(scoreboardAdapter);
        StartScreenActivity.gameMusic.start();

    }
}
