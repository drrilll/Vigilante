package com.example.vigilante;


public interface Sprite {
     void update();
     Location getLocation();
     Vector getDirection();
     void setMessage(Message message);

}
