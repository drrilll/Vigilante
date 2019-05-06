package com.example.vigilante;

public class Vector {

    double x, y;
    double mag;
    int id;

    public Vector(int id){
        this(id,0,0);
    }

    public Vector(int id, double x, double y){
        this.x = x;
        this.y = y;
        this.id = id;
        computeMag();
    }

    public void setXY(double x, double y){
        this.x = x;
        this.y = y;
        computeMag();
    }

    public void setId(int id)
    {
        this.id = id;
    }
    private void computeMag(){
        mag = Math.sqrt(x*x+y*y);
    }
}
