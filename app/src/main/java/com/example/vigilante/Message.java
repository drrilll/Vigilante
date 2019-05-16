package com.example.vigilante;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Message implements Drawable{

    private static Message single_instance;
    String message;
    Paint paint;


    private Message(){
        paint = new Paint();
        paint.setColor(Color.rgb(250,250,250));
        paint.setTextSize(60);
        message = "";
    }

    public static Message getInstance(){
        if (single_instance == null){
            single_instance = new Message();
        }

        return single_instance;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void draw(Canvas canvas) {
        canvas.drawText(message, 0,50,paint);
    }
}

