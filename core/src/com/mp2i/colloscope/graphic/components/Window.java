package com.mp2i.colloscope.graphic.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.graphic.utils.Rect;
import com.mp2i.colloscope.graphic.utils.Vector2;

public class Window extends Rect {
    private final Color color;
    private final Button exitButton;
    public boolean hidden;
    // padding inside that box
    float boxPadding;
    //radius of the edges of the box (rounded box)
    float boxRadius;


    public Window(Vector2 position, Vector2 size, Color color, float boxPadding, float boxRadius) {
        super(position, size);
        this.color = color;
        exitButton = new Button("exit.png", new Vector2(0, 0), new Vector2(100, 100), Anchor.RIGHT, Anchor.TOP);
        this.boxPadding = boxPadding;
        this.boxRadius = boxRadius;
        this.hidden = false;


    }

    public void update(SpriteBatch batch) {
        // explicitly making copy
        Rect r = new Rect(super.position, super.size);
        r.setToAnchor(new Vector2(0, 0), Anchor.CENTER, Anchor.CENTER);


        //there's is a bug with rounded rectangles but I'm just to lazy to correct it
        r.position.y += r.size.y;
        super.draw(batch, this.color, boxPadding, boxRadius);
        r.position.y -= r.size.y;


        exitButton.update(batch, r);

        if (exitButton.isClicked()) {
            this.hidden = true;
        }
    }




    public void dispose() {
        this.exitButton.dispose();
    }
}
