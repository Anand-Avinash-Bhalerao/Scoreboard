package com.example.scoreboard;

public class PersonInfo {
    private String name ="";
    private int score = 0;
    private int plus1 = 0;
    private int plus2 = 0;
    private int plus3 = 0;
    private int fouls = 0;

    PersonInfo(){
    }
    PersonInfo(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPlus1() {
        return plus1;
    }

    public void setPlus1(int plus1) {
        this.plus1 = plus1;
    }

    public int getPlus2() {
        return plus2;
    }

    public void setPlus2(int plus2) {
        this.plus2 = plus2;
    }

    public int getPlus3() {
        return plus3;
    }

    public void setPlus3(int plus3) {
        this.plus3 = plus3;
    }

    public int getFouls() {
        return fouls;
    }

    public void setFouls(int fouls) {
        this.fouls = fouls;
    }
}
