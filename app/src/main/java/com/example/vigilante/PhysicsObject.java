package com.example.vigilante;

import android.graphics.Rect;

import java.util.ArrayList;

public interface PhysicsObject {

    Sprite.CollisionClass getCollisionClass();
    Rect getBoundingBox();
    boolean intersects(PhysicsObject obj);
    void hitBy(Sprite.CollisionClass type, Vector direction);
    Location getLocation();
    Vector getDirection();
    boolean isActive();
    ArrayList<PhysicsObject> getContainedPhysicsObjects();

}
