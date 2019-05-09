package com.example.vigilante;

public interface GameModel {

    boolean moveActive();
    boolean shootActive();
    Vector getMoveDirection();
    Vector getShootDirection();
    double getModelHeight();
    double getModelWidth();
    Sprite getHero();
}
