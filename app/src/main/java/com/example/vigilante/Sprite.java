package com.example.vigilante;

import android.graphics.Bitmap;
import android.graphics.Canvas;

abstract class Sprite implements Drawable {
    Location location;
    GameModel model;
    Vector direction;
    Message message;
    Bitmap bitmap;


    Sprite(Location location, GameModel gameView, Vector direction) {
        this.location = location;
        this.model = gameView;
        this.direction = direction;
    }

    @Override
    public abstract void draw(Canvas canvas);

    public abstract void update();

    public Location getLocation() {
        return location;
    }

    public Vector getDirection() {
        return direction;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
