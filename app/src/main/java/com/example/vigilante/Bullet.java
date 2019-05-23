package com.example.vigilante;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

public class Bullet extends PhysicsSprite{

    static final double SPEED = 20;
    static final int RADIUS = 10;
    Paint paint;
    double viewWidth, viewHeight;
    boolean outOfBounds = true;
    Rect box;

    public Bullet(GameModel view){
        super(new Location(1,1), view, new Vector(1,0));
        paint = new Paint();
        paint.setColor(Color.rgb(250,250,250));
        box = new Rect(location.x, location.y, location.x+RADIUS, location.y + RADIUS);
    }


    @Override
    public ArrayList<PhysicsObject> getContainedPhysicsObjects() {
        return null;
    }

    @Override
    public void initialize(Location location, Vector direction){
        message.setMessage("Initialized");
        this.direction.setXY(direction.x, direction.y);
        this.direction.normalize();
        this.location.setXY(location.x+60+(int)(direction.x*1.3), location.y+60+(int)(direction.y*1.3));
        //bullet speed is constant, so we don't need magnitude information

        outOfBounds = false;

        viewWidth = model.getModelWidth();
        viewHeight = model.getModelHeight();

    }

    @Override
    public Rect getBoundingBox(){
        box.set(location.x, location.y, location.x+RADIUS, location.y + RADIUS);
        return box;
    }

    @Override
    public boolean intersects(PhysicsObject obj) {
        return false;
    }


    @Override
    public void hitBy(CollisionClass type, Vector direction) {
        if (type.equals(CollisionClass.wall)){
            outOfBounds = true;
        }
    }

    public void giveMessage(String message){
        this.message.setMessage(message);
    }

    @Override
    public boolean isActive(){
        return !outOfBounds;
    }

    @Override
    public void draw(Canvas canvas) {
        //just a circle
        if(!outOfBounds) {
            canvas.drawCircle(location.x, location.y, RADIUS, paint);
        }
    }

    @Override
    public void update() {
        //message.setMessage("Active bullet ");
        if(!outOfBounds) {

            location.x += direction.x * SPEED;
            location.y += direction.y * SPEED;
            message.setMessage(" bullet is "+location.x+" "+location.y+" "+viewWidth+" "+viewHeight);
            if ((location.x < 0) || (location.x > viewWidth) || (location.y < 0) || (location.y > viewHeight)) {
                outOfBounds = true;
                message.setMessage("out of bounds for some reason"+location.x+" "+location.y+" "+viewWidth+" "+viewHeight);
            }
        }
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
    public Sprite.CollisionClass getCollisionClass(){
        return CollisionClass.bullet;
    }

}
