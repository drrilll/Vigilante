package com.example.vigilante;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public  class GameView extends SurfaceView implements SurfaceHolder.Callback, GameModel {
        private GameThread gameThread;
        private HeroSprite hero;
        private ZombieSprite[] zombie;
        private AnalogButton dbutton, mbutton; //direction and move respectively
        ArrayList<Drawable> drawables;
        ArrayList<Sprite> updateables;
        ArrayList<Button> buttons;
        private float radius = 150; //analog button radius
        Background background;
        Message message;


        @Override
        public Resources getModelResources(){
                return getResources();
        }


        public GameView(Context context) {
                super(context);
                getHolder().addCallback(this);
                gameThread = new GameThread(getHolder(), this);
                setFocusable(true);

                drawables = new ArrayList<>();
                updateables = new ArrayList<>();
                background = new Background(this);
                background.initialize();
                drawables.add(background);
                hero = new HeroSprite(100,100, 100, this);
                drawables.add(hero);
                // we want to do a relative layout here, but for now we hard code
                Point size = new Point();
                ((Activity)context).getWindowManager().getDefaultDisplay().getSize(size);
                dbutton = new AnalogButton(size.x - (int)(2*radius),size.y - (int)(3*radius),radius,  Color.rgb(250, 0,0));
                mbutton = new AnalogButton(0 + (int)(2*radius),size.y - (int)(3*radius),radius,  Color.rgb(0, 250,0));
                drawables.add(dbutton);
                drawables.add(mbutton);
                zombie = new ZombieSprite[2];
                zombie[0] = new ZombieSprite(new Location(500,500), new Vector(0,0,1), this);
                zombie[1] = new ZombieSprite(new Location(800,200), new Vector(0,0,1), this);
                drawables.add(zombie[0]);
                drawables.add(zombie[1]);
                message = new Message();
                drawables.add(message);
                hero.setMessage(message);
                zombie[0].setMessage(message);
                zombie[1].setMessage(message);

                buttons = new ArrayList<>();
                buttons.add(dbutton);
                buttons.add(mbutton);
                updateables.add(hero);
                updateables.add(zombie[0]);
                //TODO make this a zombie horde instead
                updateables.add(zombie[1]);


        }

        @Override
        public boolean onTouchEvent(MotionEvent event){

                int numPointers = event.getPointerCount();
                double x, y;
                for (Button button: buttons){
                        button.resetTouched();
                }
                for (int i = 0; i < numPointers; i++){
                        x = event.getX(i);
                        y = event.getY(i);
                        for (Button button: buttons){
                                button.touched(x,y, event.getPointerId(i));
                        }
                }

                int pointerIndex = event.getActionIndex();

                // get pointer ID
                int pointerId = event.getPointerId(pointerIndex);
                int maskedAction = event.getActionMasked();

                switch (maskedAction) {
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_POINTER_UP:
                        case MotionEvent.ACTION_CANCEL: {
                                for (Button button: buttons) {
                                        if (button.getPointerId() == pointerId){
                                                //cancel this bitch
                                                button.resetTouched();
                                        }
                                }
                        }
                }


                return true;

        }

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder){
                gameThread.setRunning(true);
                gameThread.start();
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                boolean retry = true;
                while (retry) {
                        try {
                                gameThread.setRunning(false);
                                gameThread.join();
                        } catch (InterruptedException e) {
                                e.printStackTrace();
                        }
                        retry = false;
                }
        }

        public void update() {

                for(Sprite sprite: updateables){
                        sprite.update();
                }

        }


        @Override
        public void draw(Canvas canvas){
                super.draw(canvas);
                if (canvas != null){
                        //canvas.drawColor(Color.WHITE);
                        for(Drawable drawable: drawables){
                                drawable.draw(canvas);
                        }
                }
        }


        @Override
        public boolean moveActive() {
                return mbutton.touched;
        }

        @Override
        public boolean shootActive() {
                return dbutton.touched;
        }

        @Override
        public Vector getMoveDirection() {
                return mbutton.getVector();
        }

        @Override
        public Vector getShootDirection() {
                return dbutton.getVector();
        }

        @Override
        public double getModelHeight() {
                return getHeight();
        }

        @Override
        public double getModelWidth() {
                return getWidth();
        }

        @Override
        public Sprite getHero() {
                return hero;
        }
}
