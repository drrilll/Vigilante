package com.example.vigilante;

import android.graphics.Rect;

public interface PhysicsObject {

    Sprite.CollisionClass getCollisionClass();
    Rect getBoundingBox();
    void hitBy(Sprite.CollisionClass type, Vector direction);
    Location getLocation();
    Vector getDirection();
    boolean isActive();

}
