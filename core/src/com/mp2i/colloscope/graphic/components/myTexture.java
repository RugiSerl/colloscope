package com.mp2i.colloscope.graphic.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.graphic.utils.Rect;

public class myTexture extends Texture {
    public myTexture(String internalPath) {
        super(internalPath);
        super.setFilter(TextureFilter.Linear, TextureFilter.Linear);
    }

    public void draw(SpriteBatch batch, Rect target) {
        batch.draw(this, target.position.x, target.position.y, target.size.x, target.size.y);
    }
}
