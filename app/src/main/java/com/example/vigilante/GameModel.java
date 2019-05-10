package com.example.vigilante;

import android.content.res.Resources;

public interface GameModel {

    boolean moveActive();
    boolean shootActive();
    Vector getMoveDirection();
    Vector getShootDirection();
    double getModelHeight();
    double getModelWidth();
    Resources getModelResources();
    Sprite getHero();
}
