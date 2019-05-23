package com.example.vigilante.levelEditor;

import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.vigilante.Location;
import com.example.vigilante.Vector;

public class Tile  {

    LevelEditView view;
    Rect window;

    public Tile(){
        window = new Rect();
    }

    public void setTileWindow(Rect window){
        this.window.set(window);
    }

    public void update(){

    }

    public void draw(Canvas canvas){

    }


}
