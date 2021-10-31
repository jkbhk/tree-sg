package com.example.treesg;

public class Points {
    private int points;
    private String userName;

    public Points(String uName, int p){
        this.userName = uName;
        this.points = p;
    }

    public void setPoints(int p){
        this.points = p;
    }

    public void setUsername(String uname){
        this.userName = uname;
    }

    public int getPoints(){
        return this.points;
    }

    public String getUsername(){
        return this.userName;
    }
}
