package com.mp2i.colloscope.graphic.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.Colles;
import com.mp2i.colloscope.graphic.utils.Anchor;
import com.mp2i.colloscope.graphic.utils.Vector2;
import com.mp2i.colloscope.graphic.utils.text;

import java.beans.VetoableChangeListener;

public class ColleDisplay {

    BitmapFont font;
    private Vector2 position;

    private Vector2 oldPosition;
    private Vector2 momentum;
    private boolean touching;

    private Vector2 startPosition;



    public ColleDisplay(BitmapFont font) {
        this.font = font;
        this.position = new Vector2();
        this.startPosition = new Vector2();
        this.momentum = new Vector2();
        this.oldPosition = new Vector2();
        touching = false;
    }

    public void update(SpriteBatch b, Colles CollesToDisplay, int groupNumber, String groupMembers, Vector2 groupPosition, float scale, Color boxColor, float boxPadding, float boxRadius) {
        handleInput();
        render(b, CollesToDisplay, groupNumber, groupMembers, groupPosition, scale, boxColor, boxPadding, boxRadius);
    }


    public void handleInput() {
        Vector2 currentPosition =  new Vector2(Gdx.input.getX(), Gdx.input.getY());

        if (Gdx.input.isTouched()) {

            //case if the user just touched
            if (!touching) {
                oldPosition = currentPosition;
            }


            this.momentum.x = oldPosition.x - currentPosition.x;

        } else {

            this.position.x *= 0.5;

        }


        this.position.x -= momentum.x;

        this.momentum.x *= 0.9f;

        oldPosition = currentPosition;
        touching = Gdx.input.isTouched();
    }

    public void render(SpriteBatch b, Colles CollesToDisplay, int groupNumber, String groupMembers, Vector2 groupPosition, float scale, Color boxColor, float boxPadding, float boxRadius) {


        for (int i = 0; i < CollesToDisplay.amount; i++) {

            String txt = String.format(
                    "Colle %s\n" +
                            "Matière:  %s\n" +
                            "Professeur: %s\n" +
                            "Salle: %s",
                    CollesToDisplay.colles.get(i).creneau, CollesToDisplay.colles.get(i).matiere, CollesToDisplay.colles.get(i).nom, CollesToDisplay.colles.get(i).salle);

            this.position.y = (i + 1 - CollesToDisplay.amount / 2f) * scale * 6;
            text.drawText(b, font, txt, this.position, Anchor.CENTER, Anchor.CENTER, boxColor, boxPadding, boxRadius, 0, Colors.boxBorderColor);

        }


        text.drawText(b, font, "groupe n°" + groupNumber + ": " + groupMembers, groupPosition, Anchor.LEFT, Anchor.TOP, boxColor, boxPadding, boxRadius, 0, Colors.boxBorderColor);
    }


}
