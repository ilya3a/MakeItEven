package com.yoyo.makeiteven;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
public class score_board extends ListActivity {
ArrayList<player> players = new ArrayList<>();
List<Map<String,Object>> data =new ArrayList<>();
SharedPreferences mySharedPref;
SharedPreferences.Editor myEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);
        mySharedPref=getApplicationContext().getSharedPreferences("Mypref",MODE_PRIVATE);
        myEditor=mySharedPref.edit();
        final EditText name=findViewById(R.id.name_fild);
        final EditText score=findViewById(R.id.score_fild);
        final Button btn=findViewById(R.id.btn_test);

         final Gson gson=new Gson();
         String response=mySharedPref.getString("MyObject","");
         players=gson.fromJson(response,new TypeToken<List<player>>(){}.getType());

//        players.add(new player("roni",205));
//        players.add(new player("dani",700));
//        players.add(new player("dogi",450));
//        players.add(new player("eli",1));
//        players.add(new player("garik",200));
//        players.add(new player("alex",3030));
//        players.add(new player("loni",105000));
//        players.add(new player("asi",260));
//        players.add(new player("mohamadni",7009));
//        players.add(new player("lovi",4750));
//        players.add(new player("eli",6603));
//        players.add(new player("kori",2400));
//        players.add(new player("lala",300));
//        players.add(new player("dori",104000));
//        Collections.sort(players);

        HashMap<String,Object> p1 =new HashMap<>();
        HashMap<String,Object> p2 =new HashMap<>();
        HashMap<String,Object> p3 =new HashMap<>();
        HashMap<String,Object> p4 =new HashMap<>();
        HashMap<String,Object> p5 =new HashMap<>();
        HashMap<String,Object> p6 =new HashMap<>();
        HashMap<String,Object> p7 =new HashMap<>();
        HashMap<String,Object> p8 =new HashMap<>();
        HashMap<String,Object> p9 =new HashMap<>();
        HashMap<String,Object> p10 =new HashMap<>();
        HashMap<String,Object> p11 =new HashMap<>();
        HashMap<String,Object> p12 =new HashMap<>();
        HashMap<String,Object> p13 =new HashMap<>();
        HashMap<String,Object> p14 =new HashMap<>();
        HashMap<String,Object> p15 =new HashMap<>();
        HashMap<String,Object> p16 =new HashMap<>();
        HashMap<String,Object> p17 =new HashMap<>();
        HashMap<String,Object> p18 =new HashMap<>();
        HashMap<String,Object> p19 =new HashMap<>();
        HashMap<String,Object> p20 =new HashMap<>();
        data.add(p1);
        data.add(p2);
        data.add(p3);
        data.add(p4);
        data.add(p5);
        data.add(p6);
        data.add(p7);
        data.add(p8);
        data.add(p9);
        data.add(p10);
        data.add(p11);
        data.add(p12);
        data.add(p13);
        data.add(p14);
        data.add(p15);
        data.add(p16);
        data.add(p17);
        data.add(p18);
        data.add(p19);
        data.add(p20);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                players.add(new player(name.getText().toString(),Integer.parseInt(score.getText().toString())));
                for (int i=0;i<players.size();i++)
                {
                    data.get(i).put("number",i+1+"");
                    data.get(i).put("name",players.get(i).getName());
                    data.get(i).put("score",players.get(i).getScore().toString());
                }

                String [] from = {"number","name","score"};
                int[] ids= {R.id.flag_txt,R.id.name_txt,R.id.score_txt};
                final SimpleAdapter simpleAdapter = new SimpleAdapter(score_board.this,data,R.layout.score_cell,from,ids);
                setListAdapter(simpleAdapter);
                String json = gson.toJson(players);
                myEditor.putString("MyObject", json);
                myEditor.commit();
            }
        });

    }
}
