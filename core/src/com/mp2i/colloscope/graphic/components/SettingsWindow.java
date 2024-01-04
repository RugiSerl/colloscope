package com.mp2i.colloscope.graphic.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.graphic.utils.Anchor;
import com.mp2i.colloscope.graphic.utils.Vector2;
import com.mp2i.colloscope.graphic.utils.text;

public class SettingsWindow extends Window {

    // here, the array contains 1 ELEMENT, in order to have the value "by adress"
    int[] groupNumber;
    //same
    boolean[] refreshNeeded;
    //button (left arrow) to substract one from group number
    Button nextGroup;
    //button (right arrow) to add one from group number
    Button previousGroup;
    public Button refreshNewVersion;

    float scale;

    public SettingsWindow(int[] currentGroupNumber, float scale, float boxRadius, float boxPadding, boolean[] refreshNeeded) {
        super("Paramètres", new Vector2(0, 0), new Vector2(scale * 17, scale * 15), boxPadding, boxRadius, scale);
        this.groupNumber = currentGroupNumber;
        previousGroup = new Button("left.png", new Vector2(scale * 3, scale * 5.5f), scale, Anchor.RIGHT, Anchor.TOP);
        nextGroup = new Button("right.png", new Vector2(0, scale * 5.5f), scale, Anchor.RIGHT, Anchor.TOP);
        refreshNewVersion = new Button("internet.png", new Vector2(), scale, Anchor.RIGHT, Anchor.BOTTOM);
        this.refreshNeeded = refreshNeeded;
        this.scale = scale;


    }


    public void update(SpriteBatch batch, BitmapFont titleFont) {
        super.update(batch, titleFont);

        text.drawText(batch, titleFont, "numéro du groupe", new Vector2(0, scale * 6), Anchor.LEFT, Anchor.TOP, new Color(0, 0, 0, 0.2f), scale / 2, scale / 2, super.getRect(), 0, Color.CLEAR);
        text.drawText(batch, titleFont, "Colloscope v5", new Vector2(0, scale), Anchor.LEFT, Anchor.BOTTOM, new Color(0, 0, 0, 0.2f), scale / 2, scale / 2, super.getRect(), 0, Color.CLEAR);

        nextGroup.update(batch, super.getRect());
        previousGroup.update(batch, super.getRect());
        refreshNewVersion.update(batch, super.getRect());

        if (nextGroup.isClicked()) {
            groupNumber[0] += 1;
            refreshNeeded[0] = true;
        } else if (previousGroup.isClicked()) {
            groupNumber[0] -= 1;
            refreshNeeded[0] = true;
        }

        if (groupNumber[0] > 16) {
            groupNumber[0] = 1;
        } else if (groupNumber[0] < 1) {
            groupNumber[0] = 16;
        }


    }


    @Override
    public void dispose() {
        super.dispose();
        previousGroup.dispose();
        nextGroup.dispose();
    }

}

