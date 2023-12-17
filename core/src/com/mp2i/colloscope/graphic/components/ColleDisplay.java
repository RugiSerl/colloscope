package com.mp2i.colloscope.graphic.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.Colles;
import com.mp2i.colloscope.graphic.utils.Anchor;
import com.mp2i.colloscope.graphic.utils.Vector2;
import com.mp2i.colloscope.graphic.utils.text;

public class ColleDisplay {
    //differents actions of the user
    public enum Action {
        NONE,
        LEFT,
        RIGHT,
    }

    Action action;

    BitmapFont font;
    public Vector2 position;

    private Vector2 oldPosition;
    //dérivée temporelle de la position
    private Vector2 mouseSpeed;
    private boolean touching;

    private final float SlideTrigger = 10;



    public ColleDisplay(BitmapFont font) {
        this.font = font;
        this.position = new Vector2();
        this.mouseSpeed = new Vector2();
        this.oldPosition = new Vector2();
        touching = false;
        this.action = Action.NONE;
    }

    public void update(SpriteBatch b, Colles CollesToDisplay, int groupNumber, String groupMembers, Vector2 offset, float scale, Color boxColor, float boxPadding, float boxRadius) {
        handleInput();
        render(b, CollesToDisplay, groupNumber, groupMembers, offset, scale, boxColor, boxPadding, boxRadius);
    }


    public void handleInput() {
        Vector2 currentPosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());

        action = Action.NONE;

        if (Gdx.input.isTouched()) {

            //case if the user just touched
            if (!touching) {
                oldPosition = currentPosition;
            }

            this.mouseSpeed.x = (oldPosition.x - currentPosition.x);


        } else {

            if (touching) {
                if (mouseSpeed.x > SlideTrigger) {
                    this.action = Action.LEFT;
                    this.mouseSpeed.x = SlideTrigger;
                } else if (mouseSpeed.x < -SlideTrigger) {
                    this.action = Action.RIGHT;
                    this.mouseSpeed.x = -SlideTrigger;




                }

            }


            this.position.x *= 0.9;
            this.mouseSpeed.x *= 0.9f;



        }

        touching = Gdx.input.isTouched();
        this.position.x -= mouseSpeed.x;


        oldPosition = currentPosition;
    }

    public Action getAction() {
        return this.action;
    }

    public void render(SpriteBatch b, Colles CollesToDisplay, int groupNumber, String groupMembers, Vector2 offset, float scale, Color boxColor, float boxPadding, float boxRadius) {
        if (CollesToDisplay != null) {
            displayColles(b, CollesToDisplay, groupNumber, groupMembers, offset, scale, boxColor, boxPadding, boxRadius);
        } else {
            text.drawText(b, font, "pas de colles trouvées", offset.addCpy(position), Anchor.CENTER, Anchor.CENTER, boxColor, boxPadding, boxRadius, 0, Colors.boxBorderColor);

        }

        text.drawText(b, font, "groupe n°" + groupNumber + ": " + groupMembers, new Vector2(), Anchor.LEFT, Anchor.TOP, boxColor, boxPadding, boxRadius, 0, Colors.boxBorderColor);


    }

    private void displayColles(SpriteBatch b, Colles CollesToDisplay, int groupNumber, String groupMembers, Vector2 offset, float scale, Color boxColor, float boxPadding, float boxRadius) {
        Vector2 renderPosition = offset.addCpy(position);

        for (int i = 0; i < CollesToDisplay.amount; i++) {

            String txt = String.format(
                    "Colle %s\n" +
                            "Matière:  %s\n" +
                            "Professeur: %s\n" +
                            "Salle: %s",
                    CollesToDisplay.colles.get(i).creneau, CollesToDisplay.colles.get(i).matiere, CollesToDisplay.colles.get(i).nom, CollesToDisplay.colles.get(i).salle);

            renderPosition.y = (i + 1 - CollesToDisplay.amount / 2f) * scale * 6;
            text.drawText(b, font, txt, renderPosition, Anchor.CENTER, Anchor.CENTER, boxColor, boxPadding, boxRadius, 0, Colors.boxBorderColor);

        }


    }


}
