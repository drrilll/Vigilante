package com.example.vigilante;

public class Ray extends Vector {

    Location location;

    public Ray(Location location, Vector vector){
        super(vector);
        this.location = new Location(location);
    }
}
