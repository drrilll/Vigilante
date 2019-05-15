package com.example.vigilante;

import android.graphics.Canvas;

/**
 * I will get around to this, but this class should hold the bullets and know how to
 * draw them all and such, so I am not running through an array in GameView, which
 * is a little hacky
 */

public class Gun extends Sprite implements Drawable {

    Bullet[] bullet;
    Sprite hero;
    boolean firing = false;
    int shotTimer = 0;
    final int SPREAD = 10;
    Message message;
    int count = 0;

    public Gun(GameModel model, Sprite hero){
        this(10, model, hero);
    }

    public Gun(int numBullets, GameModel model, Sprite hero){
        super(hero.location, model, hero.direction);
        this.hero =hero;
        bullet = new Bullet[numBullets];
        for( int i = 0; i < numBullets; i++){
            bullet[i] = new Bullet(model);
        }

    }

    public void setFiring(boolean firing){
        this.firing = firing;
    }

    public boolean isFiring(){
        return firing;
    }

    @Override
    public void draw(Canvas canvas) {
        for(Bullet bul: bullet){
            bul.draw(canvas);
        }
    }

    @Override
    public Location getLocation(){
        return hero.getLocation();
    }

    @Override
    public Vector getDirection(){
        return hero.getDirection();
    }

    @Override
    public void setMessage(Message message) {
        this.message = message;
        for( int i = 0; i < bullet.length; i++){
            bullet[i] = new Bullet(model);
            bullet[i].setMessage(message);
        }
    }

    @Override
    public void update() {

        //decide if another bullet will be fired
        if(firing) {
            if (++shotTimer > SPREAD) {
                shotTimer = 0;
                for (int i = 0; i < bullet.length; i++) {
                    if (bullet[i].isOutOfBounds()) {
                        //message.setMessage("new bullet");
                        //bullet[i].giveMessage("the fuck");
                        bullet[i].initialize(hero.getLocation(), hero.getDirection());
                        message.setMessage("new bullet2");
                        break;
                    }

                }
            }
        }
        //message.setMessage(" "+bullet[0].isOutOfBounds());
        //update the bullets
        for(Bullet bul: bullet){
            //message.setMessage("updating bullets");
            bul.update();

        }
    }

    public Bullet[] getBullets() {
        return bullet;
    }

    @Override
    public void detectCollision(Sprite sprite) {
        for(Bullet bul: bullet){
            //message.setMessage("updating bullets");
            if (!bul.isOutOfBounds()) {
                sprite.detectCollision(bul);
            }

        }
    }

    @Override
    public Sprite.CollisionClass getCollisionClass(){
        return CollisionClass.human;
    }
}
