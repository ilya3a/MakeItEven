package com.yoyo.makeiteven;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class player implements Comparable {
    private String name;
    private Integer score;


    @Override
    public String toString() {
        return name +"     "+ score;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        player player = (player) o;
        return Objects.equals(getName(), player.getName()) &&
                Objects.equals(getScore(), player.getScore());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getScore());
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public player(String name, Integer score) {
        this.name = name;
        this.score = score;
    }
    @Override
    public int compareTo(Object comparesto) {
        Integer comparscore=((player)comparesto).getScore();
        return comparscore-this.score;
    }
    public player (String name,Integer score,SharedPreferences mySharedPref){
        this.name=name;
        this.score=score;

        ArrayList<player> players = new ArrayList<>();
        SharedPreferences.Editor myEditor;
        myEditor=mySharedPref.edit();
        final Gson gson=new Gson();
        //get the data form Sharedpref
        String response=mySharedPref.getString("MyObject","");
        if (response!="")
        {
            //set list if the list is not in the first run
            players=gson.fromJson(response,new TypeToken<List<player>>(){}.getType());
        }
        //adding new player to list
        players.add(new player(name,score));
        //sorting the list
        Collections.sort(players);
        //saving back to Sharedpref
        if(players.size()>20)
           players.remove(20);
        String json = gson.toJson(players);
        myEditor.putString("MyObject", json);
        myEditor.commit();//may use apply

    }

}
