package com.example.vigilante;

/**
 * simply a 360 degree direction
 */
public class Direction {
    double dir;

    public Direction(double direction){
        setDirection(direction);
    }

    public Direction(){
        this(0);
    }

    public void setDirection(double direction){
        dir = direction;
        while (dir > 360){
            dir -=360;
        }
        while (dir <0){
            dir += 360;
        }
    }

    public void changeDirection(long change){
        double temp = dir + change;
        setDirection(temp);
    }

    public double getDirection(){
        return dir;
    }
}
