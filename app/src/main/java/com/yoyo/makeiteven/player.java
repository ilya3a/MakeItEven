package com.yoyo.makeiteven;

import java.util.Objects;

public class player implements Comparable {
    private String name;
    private Integer score;

    @Override
    public String toString() {
        return name +"       "+ score;
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
}
