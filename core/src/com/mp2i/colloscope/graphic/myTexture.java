package com.mp2i.colloscope.graphic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class myTexture extends Texture {
    public myTexture(String internalPath) {
        super(internalPath);
    }

    public void draw(SpriteBatch batch, Rect target) {
        batch.draw(this, target.position.x, target.position.y, target.size.x, target.size.y);
    }
}
