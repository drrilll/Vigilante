package com.example.vigilante;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

public class Horde extends PhysicsSprite {

    private ArrayList<ZombieSprite> zombies;

    public Horde(GameModel model, Location location, int size){
        super(location, model, new Vector(1,0));
        zombies = new ArrayList<>();
        zombies.add(new ZombieSprite(new Location(500,500), new Vector(0,1), model));
        zombies.add(new ZombieSprite(new Location(800,200), new Vector(0,1), model));
            
    }

    @Override
    public void draw(Canvas canvas) {
        for(ZombieSprite zombie: zombies){
            zombie.draw(canvas);
        }
    }

    @Override
    public void update() {
        for(ZombieSprite zombie: zombies){
            if (zombie.isActive()) {
                zombie.update();
            }
        }

    }

    @Override
    public ArrayList<PhysicsObject> getContainedPhysicsObjects() {
        return new ArrayList<PhysicsObject>(zombies);
    }


    @Override
    public Sprite.CollisionClass getCollisionClass(){
        return CollisionClass.human;
    }

    @Override
    public Rect getBoundingBox() {
        return null;
    }

    @Override
    public boolean intersects(PhysicsObject obj) {
        return false;
    }

    @Override
    public void hitBy(CollisionClass type, Vector direction) {

    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void initialize(Location location, Vector direction) {
        return;
    }

}
