package com.example.vigilante;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class HeroSprite extends PhysicsSprite{
    private Bitmap hero;
    int width;
    int height;
    double speed = 10; //the higher this number, the slower our hero
   // Location location;
    //Direction direction;
    Paint textPaint;
   // Vector direction;
    private Matrix rotator;
    float angle;
    int spread;
    int shotTimer = 0;
    ArrayList<PhysicsObject> physicsObjects;
    Gun gun;
    public HeroSprite(Location location, Vector direction, GameModel gameView){
        super(location,gameView, direction);
        gun = new Gun(gameView, this);
        Resources resources = gameView.getModelResources();
        hero = BitmapFactory.decodeResource(resources, R.drawable.player_chaingun);
        width = hero.getWidth();
        height = hero.getHeight();
        textPaint = new Paint();
        textPaint.setTextSize(60);
        textPaint.setColor(Color.rgb(250,250,250));
        rotator = new Matrix();
        physicsObjects = new ArrayList<>();
        physicsObjects.addAll(gun.getContainedPhysicsObjects());
    }

    public HeroSprite(GameModel gameView){
        this (new Location(0,0), new Vector(1,0), gameView);
    }

    public HeroSprite(int x, int y, double direction, GameView gameView){
        this(new Location(x,y), new Vector(1,0), gameView);
    }


    @Override
    public void update(){

        boolean moveActive = model.moveActive();
        boolean shootActive = model.shootActive();
        Vector move;
        if (moveActive&&shootActive){
            move(model.getMoveDirection());
            rotateTo(model.getShootDirection());
            gun.setFiring(true);
        }else if (shootActive){
            rotateTo(model.getShootDirection());
            gun.setFiring(true);
        }else if (moveActive){
            move = model.getMoveDirection();
            move(move);
            rotateTo(move);
            gun.setFiring(false);
            shotTimer = 0;
        }else{
            gun.setFiring(false);
            shotTimer = 0;
        }
        rotator.reset();
        rotator.postRotate(angle,50,50);
        rotator.postTranslate(location.x,location.y);
        gun.update();
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public Vector getDirection() {
        return direction;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public ArrayList<PhysicsObject> getContainedPhysicsObjects() {
        return physicsObjects;
    }


    @Override
    public void draw(Canvas canvas){

        canvas.drawBitmap(hero, rotator, null);
        gun.draw(canvas);

    }



    /**
     *
     */
    public void rotateTo(Vector touch) {
        direction.setXY(touch.x, touch.y);
        angle = (float)Math.atan2(touch.y,touch.x);
        angle = (float)Math.toDegrees(angle);
    }

    public void move(Vector touch) {
        location.x += touch.x/speed;
        location.y += touch.y/speed;
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
    public void initialize(Location location, Vector direction) {

    }

}
