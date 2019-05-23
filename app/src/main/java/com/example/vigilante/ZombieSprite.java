package com.example.vigilante;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

import static com.example.vigilante.Sprite.CollisionClass.human;

public class ZombieSprite extends PhysicsSprite  {
    int width;
    int height;
    int currentLeft, currentTop, startLeft = 130, startHeight=65;
    final int animSpeed = 20; //higher is slower
    final int fastSpeed = 3;
    final int slowSpeed = 1;
    int animCount =0;
    int zfWidth = 265, zfHeight = 265;
    final double scale = 0.80;
    Paint textPaint;
    private Matrix rotator;
    float angle = 0;
    double SLOWTURNSPEED = 0.5;
    double FASTTURNSPEED = 1;
    Rect rect;
    Rect dest, test;
    static final int FRAMES = 6;
    int currentFrame = 0;
    boolean lurch = false;
    int LURCH = 7;
    int lurchCount =0;
    double lurchModifier = 2;
    Random random;
    double LURCHPROB;
    boolean dead = false;
    int deadTimer = 0;
    int DEADLIMIT = 500;

    public ZombieSprite(Location location, Vector direction, GameModel gameView){
        super(location, gameView, direction);
        Resources resources = gameView.getModelResources();
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.zombiebasic);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        textPaint = new Paint();
        textPaint.setTextSize(60);
        textPaint.setColor(Color.rgb(250,250,250));
        rotator = new Matrix();
        dest = new Rect(location.x, location.y, location.x+(int)(zfWidth*scale), location.y+(int)(zfHeight*scale));
        currentLeft = startLeft; currentTop = startHeight;
        rect = new Rect(currentLeft, currentTop, currentLeft+zfWidth,currentTop+zfHeight);
        random = new Random();
        LURCHPROB = 0.8+random.nextDouble()/20;
        collisionClass = human;
    }


    @Override
    public void draw(Canvas canvas) {
        if (dead){return;}
        //the angle adjustments were necessary, I did not look too deep into why
        canvas.rotate(-angle+180, dest.centerX(), dest.centerY());
        canvas.drawBitmap(bitmap, rect, dest, null);
        canvas.rotate(angle-180, dest.centerX(), dest.centerY());
    }

    @Override
    public boolean isActive(){
        message.setMessage("is active " + !dead);
        return !dead;
    }

    @Override
    public void update() {

        /*if(dead){
            return;
        }*/
        message.setMessage("in zombie update");

        //first we update the animation
        if(animCount++>animSpeed) {
            animCount = 0;
            currentFrame++;
            if (currentFrame >= FRAMES) {
                currentFrame = 0;
            }
        }
        if (currentFrame>2){
            currentTop = startHeight+zfHeight;
            currentLeft = startLeft + (currentFrame-3)*zfWidth;

        }else{
            currentTop = startHeight;
            currentLeft = startLeft + (currentFrame)*zfWidth;

        }
        rect.set(currentLeft, currentTop, currentLeft+zfWidth,currentTop+zfHeight);

        //now we update the AI and direction and so forth
        //start by checking if we are lurching

        if(lurch){
            if(lurchCount++>LURCH){
                lurch = false;
                lurchCount = 0;
            }
        }else{
            if (random.nextFloat()>LURCHPROB){
                lurch = true;
                LURCH = random.nextInt(5)+5;
            }
        }

        //we want to chase the hero, this assumes the hero is in sight.
        //we'll fix this later.
        Location loc =model.getHero().getLocation();
        //put the zombie at 0,0, thus translate hero
        //sign((Bx - Ax) * (Y - Ay) - (By - Ay) * (X - Ax))
        Vector vec = new Vector(loc.x-location.x, loc.y-location.y);
        vec.normalize();
        //double sign =((direction.x) * (loc.y-location.y) - (direction.y) * (loc.x-location.x));
        double sign =((direction.x) * (vec.y) - (direction.y) * (vec.x));
        double unsign = sign;
        double speed;
        double turnSpeed;
        if (sign<0){unsign = -sign;}
        if (unsign >0.5){
            if(lurch){
                speed = slowSpeed*lurchModifier;
                turnSpeed = FASTTURNSPEED*lurchModifier;
            }else{
                speed = slowSpeed;
                turnSpeed = FASTTURNSPEED;
            }

        }else{
            if(lurch){
                speed = fastSpeed*lurchModifier;
                turnSpeed = SLOWTURNSPEED*lurchModifier;
            }else{
                speed = fastSpeed;
                turnSpeed = SLOWTURNSPEED;
            }

        }
        //TODO throw in some randomness, as the zombies wind up exactly on top of
        //one another. Another option is to have them bump into each other. That might be better.
        if (sign<0){angle +=turnSpeed;}else{angle -=turnSpeed;}
        direction.setXY(Math.sin(Math.toRadians(angle)), Math.cos(Math.toRadians(angle)));

        //if the zombie has a long way to turn he slows down. That way he doesn't move in giant arcs

        location.x+=(int)(direction.x*speed);
        location.y+=(int)(direction.y*speed);
        message.setMessage(" "+(int)(direction.x*speed)+" "+(int)(direction.y*speed));
        dest.set(location.x, location.y, location.x+zfWidth, location.y+zfHeight);

    }

    @Override
    public void hitBy(CollisionClass type, Vector direction) {
            switch(type){
                case human:
                    //implement bumping into or attack
                    break;

                case bullet:
                    //we dead or take damage.
                    dead = true;
                    break;


                case wall:
            }
    }



    @Override
    public Rect getBoundingBox(){
        return dest;
    }

    @Override
    public boolean intersects(PhysicsObject obj) {
        return false;
    }

    @Override
    public void initialize(Location location, Vector direction){
        this.location.setXY(location);
        this.direction.setXY(direction);
        dead = false;
    }

    @Override
    public ArrayList<PhysicsObject> getContainedPhysicsObjects() {
        return null;
    }


}
