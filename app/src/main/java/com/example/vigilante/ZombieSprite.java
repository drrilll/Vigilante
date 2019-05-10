package com.example.vigilante;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

public class ZombieSprite extends Sprite  {
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

    public ZombieSprite(Location location, Vector direction, GameView gameView){
        super(location, gameView, direction);
        Resources resources = gameView.getResources();
        bitmap = BitmapFactory.decodeResource(resources, R.drawable.zombiebasic);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        textPaint = new Paint();
        textPaint.setTextSize(60);
        textPaint.setColor(Color.rgb(250,250,250));
        rotator = new Matrix();
        dest = new Rect(location.x, location.y, location.x+(int)(zfWidth*scale), location.y+(int)(zfHeight*scale));
        test = new Rect(0,0,zfWidth,zfHeight);
        currentLeft = startLeft; currentTop = startHeight;
        rect = new Rect(currentLeft, currentTop, currentLeft+zfWidth,currentTop+zfHeight);
    }

    public int getCurrentFrame(){
        return currentFrame;
    }

    public void setZfWidth(int zfWidth) {
        this.zfWidth = zfWidth;
    }

    public int getZfWidth(){
        return zfWidth;
    }

    @Override
    public void draw(Canvas canvas) {
        //the angle adjustments were necessary, I did not look too deep into why
        canvas.rotate(-angle+180, dest.centerX(), dest.centerY());
        canvas.drawBitmap(bitmap, rect, dest, null);
        canvas.rotate(angle-180, dest.centerX(), dest.centerY());
    }

    @Override
    public void update() {

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

        Location loc =model.getHero().getLocation();
        //put the zombie at 0,0, thus translate hero
        //sign((Bx - Ax) * (Y - Ay) - (By - Ay) * (X - Ax))
        Vector vec = new Vector(0,loc.x-location.x, loc.y-location.y);
        vec.normalize();
        //double sign =((direction.x) * (loc.y-location.y) - (direction.y) * (loc.x-location.x));
        double sign =((direction.x) * (vec.y) - (direction.y) * (vec.x));
        double unsign = sign;
        int speed;
        double turnSpeed;
        if (sign<0){unsign = -sign;}
        if (unsign >0.5){
            speed = slowSpeed;
            turnSpeed = FASTTURNSPEED;
        }else{
            speed = fastSpeed;
            turnSpeed = SLOWTURNSPEED;
        }
        //TODO throw in some randomness, as the zombies wind up exactly on top of
        //one another. Another option is to have them bump into each other. That might be better.
        if (sign<0){angle +=turnSpeed;}else{angle -=turnSpeed;}
        direction.setXY(Math.sin(Math.toRadians(angle)), Math.cos(Math.toRadians(angle)));

        //direction.setXY(location.x-loc.x, location.y-loc.y);
        //direction.normalize();

        //if the zombie has a long way to turn he slows down. That way he doesn't move in giant arcs

        location.x+=(int)(direction.x*speed);
        location.y+=(int)(direction.y*speed);
        message.setMessage(" "+(int)(direction.x*speed)+" "+(int)(direction.y*speed));
        dest.set(location.x, location.y, location.x+zfWidth, location.y+zfHeight);

        //set the angle for direction
        //angle = (float)Math.atan2(direction.y,direction.x);
        //angle = (float)Math.toDegrees(angle);
    }

}
