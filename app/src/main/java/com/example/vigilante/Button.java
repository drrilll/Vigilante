package com.example.vigilante;

import android.view.MotionEvent;

public interface Button {

    boolean wasTouched();

    void resetTouched();

    void touched(double x, double y, int pointerId);

    void setPointer(double x, double y, int pointerId);

    int getRightMostPoint();

    Vector getVector();

    /**
     * return -1 if there is no current pointer
     *
     * @return
     */
    public int getPointerId();

    /**
     * for whatever reason we have lost a pointer
     *
     * @param
     */
    void setPointerId(int pointerId);
}