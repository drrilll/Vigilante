package com.example.vigilante;

public class Vector {

    double x, y;
    double mag;

    public Vector(){
        this(0,0);
    }

    public Vector(double x, double y){
        this.x = x;
        this.y = y;
        computeMag();
    }

    public Vector(Vector vec){
        setXY(vec);
    }

    public void rotate(double angle){

    }

    public void setXY(Vector vec){
        this.x = vec.x;
        this.y = vec.y;
        this.mag = vec.mag;
    }

    public void normalize(){
        x = x/mag;
        y = y/mag;
        mag = 1;
    }

    public void setXY(double x, double y){
        this.x = x;
        this.y = y;
        computeMag();
    }


    private void computeMag(){
        mag = Math.sqrt(x*x+y*y);
    }
}
