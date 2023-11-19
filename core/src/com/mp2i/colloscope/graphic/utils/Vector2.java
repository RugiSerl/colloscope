package com.mp2i.colloscope.graphic.utils;

public class Vector2 {
    public float x,y;

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
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

}