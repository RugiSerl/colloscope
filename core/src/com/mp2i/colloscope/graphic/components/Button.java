package com.mp2i.colloscope.graphic.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.graphic.utils.Anchor;
import com.mp2i.colloscope.graphic.utils.Rect;
import com.mp2i.colloscope.graphic.utils.Vector2;

public class Button extends Rect {
    private boolean clicked;
    //used for button animation
    private float lastClicked;
    private final float ANIMATION_DURATION = 0.1f;
    private final Vector2 pos;
    private final Anchor horizontalAnchor;
    private final Anchor verticalAnchor;
    private final myTexture texture;
    private final float scale;
    private static long frameID;




    public Button(String texturePath, Vector2 position, float scale, Anchor horizontalAnchor, Anchor verticalAnchor) {
        super(new Vector2(position.x, position.y), new Vector2(scale*2.6f));
        // have to explicitly make a copy of the vector because in this language every fucking thing is a reference
        // debugged for an hour for this

        this.pos = position;
        this.horizontalAnchor = horizontalAnchor;
        this.verticalAnchor = verticalAnchor;
        this.texture = new myTexture(texturePath);
        this.lastClicked = -1;
        this.scale = scale;
        frameID = 0;


    }

    public void update(SpriteBatch batch, Rect containingRect) {

        this.handleInput();


        this.render(batch, containingRect);


    }

    public void render(SpriteBatch batch, Rect containingRect) {
        super.setToAnchor(this.pos, this.horizontalAnchor, this.verticalAnchor, containingRect);
        Rect temp = super.Copy();

        //draw square surrounding the button during the animation
        if (this.lastClicked < this.ANIMATION_DURATION && this.lastClicked >=0) {
            float alpha = (this.ANIMATION_DURATION - this.lastClicked) / this.ANIMATION_DURATION * 0.005f*scale;
            temp.addPadding(-alpha*10);
            temp.draw(batch, new Color(1.0f, 1, 1, alpha));
        }


        this.texture.draw(batch, temp);
    }


    /**
     * Subset of function update above
     * @param batch
     */
    public void update(SpriteBatch batch) {
        this.update(batch, new Rect(new Vector2(0, 0), new Vector2((float) Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight())));

    }



    public void handleInput() {
        this.clicked = Gdx.input.justTouched() && super.detectPointCollision(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

        //prevent multiple button input on the same frame
        if (this.clicked) {
            if (frameID != Gdx.graphics.getFrameId()) {
                //no other updates in the frame
                frameID = Gdx.graphics.getFrameId();
            } else {
                clicked = false;
            }
        }


        if (this.clicked) {
            this.lastClicked = 0;
        } else if (this.lastClicked >= 0) {

            this.lastClicked += Gdx.graphics.getDeltaTime();
        }



    }

    public boolean isClicked() {
        return this.clicked;
    }

    public void dispose() {
        texture.dispose();
    }

}
