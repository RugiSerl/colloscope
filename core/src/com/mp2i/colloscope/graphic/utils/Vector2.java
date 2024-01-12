package com.mp2i.colloscope.graphic.utils;

import java.beans.VetoableChangeListener;

public class Vector2 {
    public float x,y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(float xy) {
        this.x = xy;
        this.y = xy;
    }

    public Vector2() {
        this.x = 0;
        this.y = 0;
    }

    public void rotate(float angle) {
        float tempX = x * (float)Math.cos(angle) - y*(float)Math.sin(angle);
        float tempY = x * (float)Math.sin(angle) + y*(float)Math.cos(angle);
        this.x = tempX;
        this.y = tempY;
    }

    public void add(Vector2 otherVector) {
        this.x += otherVector.x;
        this.y += otherVector.y;
    }

    public void add(float value) {
        this.x += value;
        this.y += value;
    }
    public void add(float x, float y) {
        this.x += x;
        this.y += y;
    }

    public Vector2 addCpy(float x, float y) {
        return new Vector2(x + this.x, y + this.y);
    }

    public Vector2 addCpy(Vector2 otherVector) {
        return new Vector2(otherVector.x + this.x, otherVector.y + this.y);
    }
    public Vector2 SclCpy(float value) {
        return new Vector2(this.x*value, this.y*value);
    }

    @Override
    public String toString() {
        return "("+this.x + ", " + this.y + ")";
    }
}
