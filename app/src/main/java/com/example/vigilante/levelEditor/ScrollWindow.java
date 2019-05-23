package com.example.vigilante.levelEditor;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.vigilante.Drawable;
import com.example.vigilante.R;
import com.example.vigilante.Vector;

/**
 * This might get weird trying to draw to an exact boundary.
 * We keep track of the upper left part of the window. Everything drawn in dynamically.
 * We have a faux giant bitmap. We figure out where to start drawing and - provide a window.
 */

public class ScrollWindow {

    Rect onscreen, bounds;
    LevelEditView levelEditView;
    double width, height, colw, rowh;
    float bmWidth, bmHeight; // height and width of bitmap
    double bufferX=20, bufferY=20;

    final int rows = 20; final int cols = 27;
    Vector offset;
    Rect tile;

    public ScrollWindow(LevelEditView leview){
        levelEditView = leview;
        offset = new Vector();
        tile = new Rect();
    }

    public void initialize(Rect onscreen){
        this.onscreen = new Rect(onscreen);
        bounds = new Rect(0,0, onscreen.width(), onscreen.height());
        drawTiledBitmap();

    }

    public Rect getOnscreen(){
        return onscreen;
    }


    public Bitmap drawTiledBitmap(){
        Bitmap bgmap =  BitmapFactory.decodeResource(levelEditView.getResources(), R.drawable.tilesheet_complete);
        Bitmap tilemap;
        double oldwidth, oldheight;
        oldwidth = bgmap.getWidth();
        width = oldwidth + (cols-1)*bufferX;
        oldheight = bgmap.getHeight();
        height = oldheight +(rows -1)*bufferY;
        colw = oldwidth/cols;
        rowh = oldheight/rows;
        tilemap = Bitmap.createBitmap( (int)width, (int)height, Bitmap.Config.RGB_565 );
        Canvas canvas = new Canvas(tilemap);
        Rect source = new Rect();
        Rect dest = new Rect();
        int left, top, bottom, right, dtop, dbottom, dright, dleft;
        for (int i = 0; i < cols; i++){
            for (int j=0; j<rows; j++){
                left = i*(int)colw;
                dleft = i*(int)(colw)+(int)bufferX;
                top = j*(int)rowh;
                dtop = j*(int)(rowh)+(int)bufferY;
                bottom = top + (int)rowh;
                dbottom = top + (int)(rowh);
                right = left +(int)colw;
                dright = left +(int)(colw);
                source.set(left, top, right ,bottom );
                dest.set(dleft, dtop, dright ,dbottom );
                canvas.drawBitmap(bgmap, source, dest, null );
            }
        }
        bmWidth = tilemap.getWidth();
        bmHeight = tilemap.getHeight();
        return tilemap;
    }


    /**
     *
     * @return a rectangle that will return the tile, given a point on the tilemap.
     */
    public Rect getTile(double x, double y){
        double width = ((int)(colw)+(int)bufferX);
        double height = ((int)(rowh)+(int)bufferY);
        int col = (int)x / (int) width;
        int row = (int)y / (int) height ;
        tile.set((int)(col*width), (int)(row*height), (int)((col+1)*(width-bufferX)), (int)((row+1)*(height-bufferY)));
        return tile;
    }

    public void changeXY(float x, float y){

        //bounds.offset((int)x,(int)y);
        if ((bounds.left + x)<0){
            x = - bounds.left;
        }
        if ((bounds.right+x)>bmWidth){
            x =bmWidth - bounds.right;
        }
        if ((bounds.bottom+y)>bmHeight){
            y =bmHeight - bounds.bottom;
        }
        if ((bounds.top +y)<0){
            y = -bounds.top;
        }
        bounds.offset((int)x,(int)y);
    }


    /**
     Return the offset of this window onto the tilemap.
     */
    public Vector getOffset(){
        offset.setXY(bounds.left, bounds.top);
        return offset;
    }


}
