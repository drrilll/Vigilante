package com.example.vigilante.levelEditor;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class LevelEditorThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private LevelEditView levelEditView;
    private boolean running;
    public static Canvas canvas;

    public LevelEditorThread(SurfaceHolder holder, LevelEditView view){
        super();
        this.surfaceHolder = holder;
        levelEditView = view;
    }

    @Override
    public void run() {
        while (running) {
            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized(surfaceHolder) {
                    this.levelEditView.update();
                    this.levelEditView.draw(canvas);
                }
            } catch (Exception e) {} finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void setRunning(boolean running){
        this.running=running;
    }
}


