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
    //Direction direction;
    GameModel model;
    Paint textPaint;
    Vector direction;
    private Matrix rotator;
    float angle;
    int spread;
    int shotTimer = 0;
    Gun gun;
    Message message;
    public HeroSprite(Location location, Vector direction, GameView gameView){
        this.location = location;
        this.direction = direction;
        this.model = gameView;
        gun = new Gun(model, this);
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
        this (new Location(0,0), new Vector(0,1,0), gameView);
    }

    public HeroSprite(int x, int y, double direction, GameView gameView){
        this(new Location(x,y), new Vector(0,1,0), gameView);
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
    public void setMessage(Message message) {
        this.message = message;
        gun.setMessage(message);
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
        direction = touch;
        angle = (float)Math.atan2(touch.y,touch.x);
        angle = (float)Math.toDegrees(angle);
    }

    public void move(Vector touch) {
        location.x += touch.x/speed;
        location.y += touch.y/speed;
    }
}
