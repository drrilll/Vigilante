package com.example.vigilante;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;

public class ZombieSprite implements Drawable, Sprite{
    private Bitmap zombie;
    int width;
    int height;
    int currentLeft, currentTop, startLeft = 130, startHeight=65;
    final int animSpeed = 20; //higher is slower
    int animCount =0;
    int zfWidth = 265, zfHeight = 265;
    final double scale = 0.80;
    double speed = 10; //the higher this number, the slower our hero
    Location location;
    GameView gameView;
    Paint textPaint;
    Vector direction;
    private Matrix rotator;
    float angle;
    Rect[] rect;
    Rect dest, test;
    static final int FRAMES = 6;
    int currentFrame = 0;
    Message message;
    public ZombieSprite(Location location, Vector direction, GameView gameView){
        this.location = location;
        this.direction = direction;
        this.gameView = gameView;
        Resources resources = gameView.getResources();
        zombie = BitmapFactory.decodeResource(resources, R.drawable.zombiebasic);
        width = zombie.getWidth();
        height = zombie.getHeight();
        textPaint = new Paint();
        textPaint.setTextSize(60);
        textPaint.setColor(Color.rgb(250,250,250));
        rotator = new Matrix();
        dest = new Rect(location.x, location.y, location.x+(int)(zfWidth*scale), location.y+(int)(zfHeight*scale));
        test = new Rect(0,0,zfWidth,zfHeight);
        rect = new Rect[FRAMES];
        int startx = 20, starty = 20,  currentLeft = startx, currentTop = starty;
        for (int i = 0; i < 2; i++){
            for (int j = 0; j < 3; j++){
                currentLeft = startx +(j*zfWidth);
                currentTop = starty+(i*zfHeight);
                rect[j+3*i] = new Rect(currentLeft, currentTop, currentLeft+zfWidth,currentTop+zfHeight);
            }
        }
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
        canvas.drawBitmap(zombie, rect[0], dest, null);
        //canvas.drawBitmap(zombie, test, dest, null);
    }

    @Override
    public void update() {
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
        rect[0].set(currentLeft, currentTop, currentLeft+zfWidth,currentTop+zfHeight);
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
    public void setMessage(Message message) {
        this.message = message;
    }
}
