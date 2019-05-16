package com.example.vigilante;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public  class GameView extends SurfaceView implements SurfaceHolder.Callback, GameModel {
        private GameThread gameThread;
        private HeroSprite hero;
        private AnalogButton dbutton, mbutton; //direction and move respectively
        ArrayList<Drawable> drawables;
        ArrayList<Sprite> sprites;
        ArrayList<Button> buttons;
        //class1 we check collisions against everyone. class2 we check agaist class1
        ArrayList<PhysicsObject> class1, class2;
        private float radius = 150; //analog button radius
        Background background;
        Message message;
        Horde horde;


        @Override
        public Resources getModelResources(){
                return getResources();
        }


        public GameView(Context context) {
                super(context);
                getHolder().addCallback(this);
                gameThread = new GameThread(getHolder(), this);
                setFocusable(true);
                message = Message.getInstance();
                class1 = new ArrayList<>();
                class2 = new ArrayList<>();
                drawables = new ArrayList<>();
                sprites = new ArrayList<>();
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
                drawables.add(message);
                horde = new Horde(this, new Location(500,800), 2);
                drawables.add(horde);

                buttons = new ArrayList<>();
                buttons.add(dbutton);
                buttons.add(mbutton);
                sprites.add(hero);
                sprites.add(horde);

                class1.addAll(horde.getContainedPhysicsObjects());
                class2.addAll(hero.getContainedPhysicsObjects());



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

        /**
         * Check for collisions, then update. I think make different collision classes. I think I should
         * handle this type of logic here as well. That way it is all handled in one place. I check if
         * two things collide, then inform the sprites what they have collided with. But I need to
         * resolve direction and whatnot. Or well, hitBy(Class.human, Vector direction). Vector carries
         * magnitude as well. Maybe I need a collisionable interface, and getCollidable, since sprites
         * may have associated objects, like something they have shot or thrown.
         */
        public void update() {

                detectCollisions();


                for(Sprite sprite: sprites){
                        if(sprite.isActive()){
                                sprite.update();
                        }
                }

        }

        /*
         * This is inefficient, as we are checking all bullets against one another. Maybe I can do some
         * kind of collection related to the bullet class, static maybe?
         */
        private void detectCollisions(){
                int size = class1.size();
                PhysicsObject obj1, obj2;
                for (int i = 0; i < size; i++){
                        obj1 = class1.get(i);
                        if(!obj1.isActive()){continue;}
                        for (int j = i; j < size; j++){
                                if (j==i){continue;}
                                obj2 = class1.get(j);
                                if(!obj2.isActive()){continue;}
                                if (obj1.getBoundingBox().intersect(obj2.getBoundingBox())){
                                        obj1.hitBy(obj2.getCollisionClass(), obj2.getDirection());
                                        obj2.hitBy(obj1.getCollisionClass(), obj1.getDirection());
                                }
                        }
                        for (PhysicsObject po: class2){
                                if(!po.isActive()){continue;}
                                if (obj1.getBoundingBox().intersect(po.getBoundingBox())){
                                        obj1.hitBy(po.getCollisionClass(), po.getDirection());
                                        po.hitBy(obj1.getCollisionClass(), obj1.getDirection());
                                }
                        }
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
