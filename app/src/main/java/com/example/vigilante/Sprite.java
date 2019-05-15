package com.example.vigilante;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

abstract class Sprite implements Drawable, PhysicsObject {
    Location location;
    GameModel model;
    Vector direction;
    Message message;
    Bitmap bitmap;
    enum CollisionClass {human, small, wall};
    CollisionClass collisionClass;


    Sprite(Location location, GameModel gameView, Vector direction) {
        this.location = location;
        this.model = gameView;
        this.direction = direction;
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

    public void setMessage(Message message) {
        this.message = message;
    }
}
