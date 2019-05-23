package com.example.vigilante.levelEditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.vigilante.Button;
import com.example.vigilante.Vector;


/**
 * Let's list the objects we will need. We need a picker for the different parts of the landscape,
 * and we will need a builder window. The picker should scroll, the builder should scroll too
 * actually. So both have a common super class, or maybe same. Could be if they are configurable.
 * So we need mini-windows. Kind of like giant sprites?
 */
public class LevelEditView extends SurfaceView implements SurfaceHolder.Callback {
    LevelEditorThread thread;
    double height, width;
    ScrollWindow scrollWindow;
    float oldX, oldY;
    Bitmap tilemap;
    enum EditState  {none, carryTile};

    EditState current = EditState.none;

    Tile tile;



    //Background background;

    public LevelEditView(Context context){
        super(context);
        getHolder().addCallback(this);
        thread = new LevelEditorThread(getHolder(), this);
        setFocusable(true);
        height = getHeight();
        width = getWidth();
        scrollWindow = new ScrollWindow(this);
        tilemap = scrollWindow.drawTiledBitmap();
        //scrollWindow.initialize(new Rect(0,0,(int)width/2, (int)height));
        //scrollWindow.initialize(new Rect(0,0,600, 600));
        //background = new Background(this);
        //background.initialize();
    }

    final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {
        public void onLongPress(MotionEvent e) {
            //assuming that this is a registered long press, find the appropriate tile.
            //We will blit the appropriate tile, which we know how to do. We need to know which tile
            //though. Return an x-y coordinate. So we need a rectangle that maps onto the tilemap.
            //Like the scrollWindow is, so this is the scrollwindow but smaller. Then we need a
            //destination rectangle that goes onto the screen whereever the users fat fucking
            //finger is.

            //the event point plus the offset of the scrollwindow we can map onto the tilemap
            current = EditState.carryTile;
            Vector offset = scrollWindow.getOffset();

            //these two should map directly onto the tilemap
            double xTouch = e.getX() + offset.getX();
            double yTouch = e.getY() + offset.getY();
            tile.setTileWindow(scrollWindow.getTile(xTouch,yTouch));

        }
    });

    @Override
    public boolean onTouchEvent(MotionEvent event){
        /*
        for now we just scroll, no selection
         */

        // detect a long press event.
        gestureDetector.onTouchEvent(event);

        float newX, newY;
        int maskedAction = event.getActionMasked();
        switch (maskedAction) {
            case MotionEvent.ACTION_MOVE:
                newX = event.getX();
                newY = event.getY();
                scrollWindow.changeXY(oldX - newX,oldY - newY);
                oldX = newX;
                oldY = newY;
                break;

            case MotionEvent.ACTION_DOWN:
                oldX = event.getX();
                oldY = event.getY();

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_CANCEL: {

            }
        }


        return true;

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        thread.setRunning(true);
        thread.start();
        height = getHeight();
        width = getWidth();
        scrollWindow.initialize(new Rect(0,0,(int)width/2, (int)height));
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    public void update(){
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        if (canvas != null) {

            canvas.drawBitmap(tilemap, scrollWindow.bounds, scrollWindow.onscreen, null );

            //background.draw(canvas);
        }
    }
}
