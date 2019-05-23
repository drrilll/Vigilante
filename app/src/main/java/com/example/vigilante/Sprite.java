package com.example.vigilante;

import android.graphics.Bitmap;


public abstract class Sprite implements Drawable{
    Location location;
    GameModel model;
    Vector direction;
    Bitmap bitmap;
    enum CollisionClass {human, bullet, wall};
    CollisionClass collisionClass;
    Message message;


    public Sprite(Location location, GameModel gameView, Vector direction) {
        this.location = location;
        this.model = gameView;
        this.direction = direction;
        message = Message.getInstance();
    }


    public abstract void update();

    public Location getLocation() {
        return location;
    }

    public Vector getDirection() {
        return direction;
    }

    public CollisionClass getCollisionClass(){
        return collisionClass;
    }

    public abstract void initialize(Location location, Vector direction);

}
