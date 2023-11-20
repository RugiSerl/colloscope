package com.mp2i.colloscope.graphic.components;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.graphic.utils.Rect;
import com.mp2i.colloscope.graphic.utils.Vector2;

public abstract class InputComponent {

    public enum inputType {
        Button
    }

    public abstract void update(SpriteBatch batch, Rect rect);


    //TODO : change the type to interface to allow differents types
    public abstract boolean getValue();

    public abstract Rect getDimensions();

    public abstract void giveValue(Boolean value);


}
