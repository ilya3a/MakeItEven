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

    //ArrayList<player> players = new ArrayList<>();
//List<Map<String,Object>> data =new ArrayList<>();
//SharedPreferences mySharedPref;
//SharedPreferences.Editor myEditor;
//    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_score_board );
        lv = findViewById(android.R.id.list);

        scoreboardAdapter = new ScoreBoardAdapter( this, R.layout.score_cell, DataStore.getInstance( this ).getScoreBoardList() );
        lv.setAdapter( scoreboardAdapter );


//        mySharedPref=getApplicationContext().getSharedPreferences("Mypref",MODE_PRIVATE);
//        myEditor=mySharedPref.edit();
//
//        final Gson gson=new Gson();
//        String response=mySharedPref.getString("MyObject","");
//        players=gson.fromJson(response,new TypeToken<List<player>>(){}.getType());
//        if (!response.equals("")) {
//            for (int i = 0; i < players.size(); i++) {
//                data.add(new HashMap<String, Object>());
//                int temp = i + 1;
//                data.get(i).put("number", " " + temp);
//                data.get(i).put("name", players.get(i).getName() + "                     ");
//                data.get(i).put("score", players.get(i).getScore().toString());
//            }
//        }
//        String [] from = {"number","name","score"};
//        int[] ids= {R.id.flag_txt,R.id.name_txt,R.id.score_txt};
//        final SimpleAdapter simpleAdapter = new SimpleAdapter( ScoreBoardActivity.this,data,R.layout.score_cell,from,ids);
//        setListAdapter(simpleAdapter);

    }
}
