package com.example.vigilante;

public class ButtonVector extends Vector {
    int id;

    public ButtonVector(int id, double x, double y){
        super(x,y);
        this.id = id;
    }

    public ButtonVector(int id){
        super();
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
