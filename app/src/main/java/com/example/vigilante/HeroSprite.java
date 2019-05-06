package com.example.vigilante;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class HeroSprite implements Sprite, Drawable {
    private Bitmap hero;
    int width;
    int height;
    double speed = 10; //the higher this number, the slower our hero
    Location location;
    Direction direction;
    GameView gameView;
    Paint textPaint;
    private Matrix rotator;
    float angle;
    public HeroSprite(Location location, Direction direction, GameView gameView){
        this.location = location;
        this.direction = direction;
        this.gameView = gameView;
        Resources resources = gameView.getResources();
        hero = BitmapFactory.decodeResource(resources, R.drawable.player_chaingun);
        width = hero.getWidth();
        height = hero.getHeight();
        textPaint = new Paint();
        textPaint.setTextSize(60);
        textPaint.setColor(Color.rgb(250,250,250));
        rotator = new Matrix();
    }

    public HeroSprite(GameView gameView){
        this (new Location(0,0), new Direction(0), gameView);
    }

    public HeroSprite(int x, int y, double direction, GameView gameView){
        this(new Location(x,y), new Direction(direction), gameView);
    }

    @Override
    public void update(){
        rotator.reset();
        rotator.postRotate(angle,50,50);
        rotator.postTranslate(location.x,location.y);
    }


    @Override
    public void draw(Canvas canvas){
        //canvas.drawBitmap(hero[current],location.x+(int)xoffset,location.y+(int)yoffset, null);
        //canvas.drawText(Integer.toString(current), 200,200,textPaint);


        canvas.drawBitmap(hero, rotator, null);

    }

    /**
     * So the clever part here is we need drawing offsets that translate the center to
     * where it is supposed to be.
     * @param touch
     */
    public void rotateTo(Vector touch) {
        angle = (float)Math.atan2(touch.y,touch.x);
        angle = (float)Math.toDegrees(angle);
    }

    public void move(Vector touch) {
        location.x += touch.x/speed;
        location.y += touch.y/speed;
    }
}
