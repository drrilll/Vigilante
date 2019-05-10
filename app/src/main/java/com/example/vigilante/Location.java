package com.example.vigilante;

public class Location {
    int x, y;

    public Location (int x, int y){
        this.x = x; this.y = y;
    }

    public Location (Location loc){
        this.x = loc.x; this.y = loc.y;
    }

    public void setXY(int x, int y){
        this.x = x; this.y = y;
    }
}
