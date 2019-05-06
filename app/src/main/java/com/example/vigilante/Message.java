package com.example.vigilante;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Message implements Drawable {


    String message;
    Paint paint;


    public Message(){
        paint = new Paint();
        paint.setColor(Color.rgb(250,250,250));
        paint.setTextSize(60);
        message = "";
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(message, 0,50,paint);
    }
}
