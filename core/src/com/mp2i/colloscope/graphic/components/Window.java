package com.mp2i.colloscope.graphic.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.graphic.utils.Anchor;
import com.mp2i.colloscope.graphic.utils.Rect;
import com.mp2i.colloscope.graphic.utils.Vector2;
import com.mp2i.colloscope.graphic.utils.text;

public class Window extends Rect {
    private final Color color;
    private final Button exitButton;
    public boolean hidden;
    // padding inside that box
    float boxPadding;
    //radius of the edges of the box (rounded box)
    float boxRadius;
    String title;


    public Window(String title, Vector2 position, Vector2 size, float boxPadding, float boxRadius, float scale) {
        super(position, size);
        this.color = Colors.windowColor;
        exitButton = new Button("exit.png", new Vector2(0, 0), new Vector2(4*scale, 4*scale), Anchor.RIGHT, Anchor.TOP);
        this.boxPadding = boxPadding;
        this.boxRadius = boxRadius;
        this.hidden = false;
        this.title = title;



    }

    public void update(SpriteBatch batch, BitmapFont titleFont) {

        Rect r = this.getRect();
        //there's a bug with rounded rectangles but I'm just to lazy to correct it
        r.position.y += r.size.y;
        super.draw(batch, this.color, boxPadding, boxRadius,5 ,Colors.boxBorderColor);
        r.position.y -= r.size.y;


        exitButton.update(batch, r);

        if (exitButton.isClicked()) {
            this.hidden = true;
        }

        text.drawText(batch, titleFont, this.title, new Vector2(), Anchor.LEFT, Anchor.TOP, Color.CLEAR, 0, 0, r, 0, Color.CLEAR);

    }

    public Rect getRect() {
        // explicitly making copy
        Rect r = new Rect(super.position, super.size);
        r.setToAnchor(new Vector2(0, 0), Anchor.CENTER, Anchor.CENTER);
        return r;
        }




    public void dispose() {
        this.exitButton.dispose();
    }
}
