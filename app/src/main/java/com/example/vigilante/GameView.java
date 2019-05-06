package com.example.vigilante;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

public  class GameView extends SurfaceView implements SurfaceHolder.Callback {
        private GameThread gameThread;
        private HeroSprite hero;
        private AnalogButton dbutton, mbutton; //direction and move respectively
        ArrayList<Drawable> drawables;
        ArrayList<Button> buttons;
        private float radius = 150; //analog button radius
        Background background;



        public GameView(Context context) {
                super(context);
                getHolder().addCallback(this);
                gameThread = new GameThread(getHolder(), this);
                setFocusable(true);

                drawables = new ArrayList<>();
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
                buttons = new ArrayList<>();
                buttons.add(dbutton);
                buttons.add(mbutton);

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

                //ok I think that is everything

                /*
                // get pointer index from the event object
                int pointerIndex = event.getActionIndex();

                // get pointer ID
                int pointerId = event.getPointerId(pointerIndex);

                // get masked (not specific to a pointer) action
                int maskedAction = event.getActionMasked();

                switch (maskedAction) {

                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_POINTER_DOWN:
                                // We are assuming smart users to start. Thus if this even is
                                // on a button, we reset our button to use this pointer.
                                for (Button button: buttons) {
                                        if (button.touched(event)) {
                                                button.setPointer(event);
                                        }
                                }

                        break;
                        case MotionEvent.ACTION_MOVE: { // a pointer was moved
                                /*
                                if we have a matching id and the pointer moves off of the button,
                                then we set the pointer to null (pointerId = -1)

                                for (Button button: buttons) {
                                        if (button.touched(event)) {
                                                button.setPointer(event);
                                        }else if (button.getPointerId() == pointerId){
                                                //cancel this bitch
                                                button.setPointerId(-1);
                                        }
                                }
                                break;
                        }
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_POINTER_UP:
                        case MotionEvent.ACTION_CANCEL: {
                                for (Button button: buttons) {
                                        if (button.getPointerId() == pointerId){
                                                //cancel this bitch
                                                button.setPointerId(-1);
                                        }
                                }
                                break;
                        }
                }*/
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
                if (mbutton.wasTouched()&&(dbutton.wasTouched())){
                        hero.move(mbutton.getVector());
                        hero.rotateTo(dbutton.getVector());
                }else if (dbutton.wasTouched()){
                        hero.rotateTo(dbutton.getVector());
                }else if (mbutton.wasTouched()){
                        hero.move(mbutton.getVector());
                        hero.rotateTo(mbutton.getVector());
                }
                hero.update();
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


}
