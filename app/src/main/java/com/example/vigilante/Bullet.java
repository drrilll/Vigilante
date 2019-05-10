package com.example.vigilante;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Bullet extends Sprite implements Drawable{

    static final double SPEED = 20;
    static final int RADIUS = 10;
    Paint paint;
    double viewWidth, viewHeight;
    boolean outOfBounds = true;
    Message message;

    public Bullet(GameModel view){
        super(new Location(1,1), view, new Vector(0,1,0));
        paint = new Paint();
        paint.setColor(Color.rgb(250,250,250));
        //message = new Message();
    }

    @Override
    public void setMessage(Message message){
        this.message = message;
    }

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

    public void giveMessage(String message){
        this.message.setMessage(message);
    }

    public boolean isOutOfBounds() {
        return outOfBounds;
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
}
