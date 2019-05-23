package com.example.vigilante;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * I will get around to this, but this class should hold the bullets and know how to
 * draw them all and such, so I am not running through an array in GameView, which
 * is a little hacky
 */

public class Gun extends PhysicsSprite {

    ArrayList<Bullet> bullets;
    ArrayList<PhysicsObject> po;
    Sprite hero;
    boolean firing = false;
    int shotTimer = 0;
    final int SPREAD = 10;
    int count = 0;

    public Gun(GameModel model, Sprite hero){
        this(10, model, hero);
    }

    public Gun(int numBullets, GameModel model, Sprite hero){
        super(hero.location, model, hero.direction);
        this.hero =hero;
        po = new ArrayList<>(numBullets);
        bullets = new ArrayList<>(numBullets);
        for( int i = 0; i < numBullets; i++){
            bullets.add(new Bullet(model));
        }
        po.addAll(bullets);

    }

    public void setFiring(boolean firing){
        this.firing = firing;
    }

    public boolean isFiring(){
        return firing;
    }

    @Override
    public void draw(Canvas canvas) {
        for(Bullet bul: bullets){
            bul.draw(canvas);
        }
    }

    @Override
    public Location getLocation(){
        return hero.getLocation();
    }

    @Override
    public Vector getDirection(){
        return hero.getDirection();
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public ArrayList<PhysicsObject> getContainedPhysicsObjects() {
        return po;
    }

    @Override
    public void update() {

        //decide if another bullet will be fired
        int size = bullets.size();
        if(firing) {
            if (++shotTimer > SPREAD) {
                shotTimer = 0;
                for (int i = 0; i < size; i++) {
                    if (!bullets.get(i).isActive()) {
                        //message.setMessage("new bullet");
                        //bullet[i].giveMessage("the fuck");
                        bullets.get(i).initialize(hero.getLocation(), hero.getDirection());
                        message.setMessage("new bullet2");
                        break;
                    }

                }
            }
        }
        //message.setMessage(" "+bullet[0].isOutOfBounds());
        //update the bullets
        for(Bullet bul: bullets){
            //message.setMessage("updating bullets");
            bul.update();

        }
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
        // do the bounce thing
    }

    @Override
    public void initialize(Location location, Vector direction) {

    }
}
