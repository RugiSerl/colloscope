package com.mp2i.colloscope.graphic.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.graphic.utils.Rect;
import com.mp2i.colloscope.graphic.utils.Vector2;
import com.mp2i.colloscope.graphic.utils.text;

public class TextLabel {
    String textContent;
    Vector2 position;
    Vector2 size;
    Anchor verticalAnchor;
    Anchor horizontalAnchor;
    BitmapFont font;

    public TextLabel(String textContent, Vector2 position, BitmapFont font, Anchor horizontalAnchor, Anchor verticalAnchor) {
        this.textContent = textContent;
        this.position = position;
        this.size = text.getTextSize(font, textContent);
        this.font = font;
        this.verticalAnchor = verticalAnchor;
        this.horizontalAnchor = horizontalAnchor;
    }

    public void draw(SpriteBatch batch, Rect containingRect, Color boxColor, float boxRadius, float boxPadding) {

        text.drawText(batch, this.font, this.textContent, this.position, horizontalAnchor, verticalAnchor, boxColor, boxPadding, boxRadius, containingRect);


    }


}
