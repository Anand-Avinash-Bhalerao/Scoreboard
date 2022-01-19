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

    public void inc1(){
        score +=1;
        plus1 +=1;
    }

    public void inc2(){
        score +=2;
        plus2 +=1;
    }

    public void inc3(){
        score +=3;
        plus3 +=1;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "name='" + name + '\'' +
                ", score=" + score +
                ", plus1=" + plus1 +
                ", plus2=" + plus2 +
                ", plus3=" + plus3 +
                ", fouls=" + fouls +
                '}';
    }
}
