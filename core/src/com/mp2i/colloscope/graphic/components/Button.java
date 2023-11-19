package com.mp2i.colloscope.graphic.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.graphic.utils.Rect;
import com.mp2i.colloscope.graphic.utils.Vector2;

public class Button extends Rect {
    private boolean clicked;
    private final Vector2 pos;
    private final Anchor horizontalAnchor;
    private final Anchor verticalAnchor;
    private final myTexture texture;




    public Button(String texturePath, Vector2 position, Vector2 size, Anchor horizontalAnchor, Anchor verticalAnchor) {
        super(new Vector2(position.x, position.y), size);
        // have to explicitly make a copy because in this language every fucking thing is a reference
        // debugged for an hour for this

        this.pos = position;
        this.horizontalAnchor = horizontalAnchor;
        this.verticalAnchor = verticalAnchor;
        this.texture = new myTexture(texturePath);


    }

    public void update(SpriteBatch batch, Rect containingRect) {
        this.handleInput(batch);
        super.setToAnchor(this.pos, this.horizontalAnchor, this.verticalAnchor, containingRect);
        if (this.clicked) {
            super.draw(batch, new Color(1.0f, 1, 1, 0.2f));
        }
        this.texture.draw(batch, this);

    }

    /**
     * Subset of function update above
     * @param batch
     */
    public void update(SpriteBatch batch) {
        this.update(batch, new Rect(new Vector2(0, 0), new Vector2((float) Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight())));

    }



    public void handleInput(SpriteBatch batch) {
        this.clicked = Gdx.input.justTouched() && super.detectPointCollision(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());


        /*if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && super.detectPointCollision(Gdx.input.getX(), Gdx.graphics.getHeight() -  Gdx.input.getY())) {
            this.clicked = true;
        }*/


    }

    public boolean isClicked() {
        return this.clicked;
    }

    public void dispose() {
        texture.dispose();
    }

}