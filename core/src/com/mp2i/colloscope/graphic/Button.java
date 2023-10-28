package com.mp2i.colloscope.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button extends Rect {
    private boolean clicked;
    private final Color color;
    private final Vector2 pos;
    private final Anchor horizontalAnchor;
    private final Anchor verticalAnchor;
    private final myTexture texture;




    public Button(String texturePath, Vector2 position, Vector2 size, Color color, Anchor horizontalAnchor, Anchor verticalAnchor) {
        super(new Vector2(position.x, position.y), size);
        // have to explicitly make a copy because in this language every fucking thing is a reference
        // debugged for an hour for this

        this.color = color;
        this.pos = position;
        this.horizontalAnchor = horizontalAnchor;
        this.verticalAnchor = verticalAnchor;
        this.texture = new myTexture(texturePath);


    }


    public void update(SpriteBatch batch) {
        this.handleInput();
        super.setToAnchor(this.pos, this.horizontalAnchor, this.verticalAnchor);
        this.texture.draw(batch, this);

    }

    public void handleInput() {
        this.clicked = false;
        for (int i = 0; i < 20; i++) { // 20 is max number of touch points
            if (Gdx.input.justTouched() && super.detectPointCollision(Gdx.input.getX(i), Gdx.graphics.getHeight() - Gdx.input.getY(i))) {
                this.clicked = true;
                break;
            }
        }
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
