package com.example.vigilante;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.File;

/**
 * This will hold a two dimensional array of ints that encode the type of tile there.
 * This class will hold the model and also be able to draw itself. It will start by
 * taking an argument of a filename that it reads into memory and loads the corresponding
 * bitmaps. The model (array) is used to compute what is legal.
 *
 * The format for the file is first two lines are columns and rows respectively. I'll
 * write and read the files with newlines in the appropriate places.
 *
 * Since we live in the grid, we can check for collisions when things are "close", that is,
 * within one grid cell of one another. Maybe a 2D array of game objects.
 *
 * We can keep a left to right order and an up to down order. Maybe we can model these things
 * as lines. Well, simply being "orderble". The problem is some of these hit boxes can overlap
 * or contain one another.
 *
 * But even just putting them into grid cells makes this immediately easier. Also we only care
 * about collisions between certain classes of objects.
 *
 * We'll start with something really simple though..
 *
 * Spritesheet is 20x27 items
 */
public class Background implements Drawable{

    File file;

    int[][] background;

    Bitmap bgmap;
    double width, height, colw, rowh;

    final int rows = 20; final int cols = 27;

    GameView view;

    public Background(GameView view){
        //this.file = file;
        background = new int[10][10];
        this.view = view;

    }

    public void initialize() {
        for (int i = 0; i < 10; i++){
            for (int j=0; j<10; j++){
                background[i][j]= i+j;
            }
        }

        bgmap =  BitmapFactory.decodeResource(view.getResources(), R.drawable.tilesheet_complete);
        width = bgmap.getWidth();
        height = bgmap.getHeight();
        colw = width/cols;
        rowh = height/rows;

    }

    @Override
    public void draw(Canvas canvas) {
        Rect source = new Rect();
        Rect dest = new Rect();
        int left, top, bottom, right;
        for (int i = 0; i < 5; i++){
            for (int j=0; j<5; j++){
                left = i*(int)colw;
                top = j*(int)rowh;
                bottom = top + (int)rowh;
                right = left +(int)colw;
                source.set(left, top, right ,bottom );
                dest.set(left, top, right ,bottom );
                canvas.drawBitmap(bgmap, source, dest, null );
            }
        }
    }
}
