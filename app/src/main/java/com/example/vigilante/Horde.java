package com.example.vigilante;

import android.graphics.Canvas;

public class Horde extends Sprite {

    private ZombieSprite[] zombies;
    Message message;

    public Horde(GameModel model, Location location, int size){
        super(location, model, new Vector(0,1,0));
        zombies = new ZombieSprite[2];
        zombies[0] = new ZombieSprite(new Location(500,500), new Vector(0,0,1), model);
        zombies[1] = new ZombieSprite(new Location(800,200), new Vector(0,0,1), model);
            
    }

    @Override
    public void draw(Canvas canvas) {
        for(ZombieSprite zombie: zombies){
            zombie.draw(canvas);
        }
    }

    @Override
    public void update() {
        for(ZombieSprite zombie: zombies){
            zombie.update();
        }

    }

    public void setMessage(Message message) {
        this.message = message;
        for(ZombieSprite zombie: zombies){
            zombie.setMessage(message);
        }
    }
    @Override
    public void detectCollision(Sprite sprite) {
        for (ZombieSprite zs: zombies){
            sprite.detectCollision(zs);
        }
    }

    @Override
    public Sprite.CollisionClass getCollisionClass(){
        return CollisionClass.human;
    }

}
