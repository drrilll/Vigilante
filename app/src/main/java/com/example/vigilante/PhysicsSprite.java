package com.example.vigilante;

public abstract class PhysicsSprite extends Sprite implements PhysicsObject {
    PhysicsSprite(Location location, GameModel gameView, Vector direction) {
        super(location, gameView, direction);
    }
}
