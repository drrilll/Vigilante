package com.example.vigilante;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;

/**
 * We want this class to return a vector, basically.
 */
public class AnalogButton implements Drawable, Button {

    Location location;
    float radius;
    Paint buttonPaint;
    Vector touch;
    boolean touched = false;

    public AnalogButton(int x, int y, float radius, int color){
        location = new Location(x,y);
        this.radius = radius;
        buttonPaint = new Paint();
        buttonPaint.setColor(color);
        touch = new Vector(-1);
    }

    @Override
    public void resetTouched(){
        touched = false;
    }

    @Override
    public boolean wasTouched(){
        return touched;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(location.x,location.y,radius,buttonPaint);
    }

    @Override
    public void touched(double x, double y, int pointerId) {
        double mag = computeMag(x-location.x, y-location.y);
        if (mag<=radius){
            touched = true;
            touch.setXY(x-location.x, y-location.y);
            touch.setId(pointerId);
        };

    }

    @Override
    public void setPointer(double x, double y, int pointerId) {
        touch.id = pointerId;
        touch.setXY(x-location.x, y-location.y);
    }

    private double computeMag(double x, double y){
        return Math.sqrt(x*x+y*y);
    }

    @Override
    public int getRightMostPoint() {
        return 0;
    }

    @Override
    public Vector getVector() {
        return touch;
    }

    @Override
    public int getPointerId() {
        return touch.id;
    }

    @Override
    public void setPointerId(int id) {
        touch.id =id;
    }
}
