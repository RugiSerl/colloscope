package com.mp2i.colloscope.graphic.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.Colles;
import com.mp2i.colloscope.dateUtils;
import com.mp2i.colloscope.graphic.utils.Anchor;
import com.mp2i.colloscope.graphic.utils.Rect;
import com.mp2i.colloscope.graphic.utils.Vector2;
import com.mp2i.colloscope.graphic.utils.text;

import java.util.Calendar;

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

    private final myTexture localisationIcon;
    private final myTexture timeIcon;
    private final myTexture personIcon;
    private myTexture subjectIcon;

    private final float SWIPE_STRIGGER = 7;
    private final float SWIPE_SPEED = 15;



    public ColleDisplay(BitmapFont font) {
        this.font = font;
        this.position = new Vector2();
        this.mouseSpeed = new Vector2();
        this.oldPosition = new Vector2();
        touching = false;
        this.action = Action.NONE;

        this.localisationIcon = new myTexture("location.png");
        this.personIcon = new myTexture("person.png");
        this.timeIcon = new myTexture("time.png");
        this.subjectIcon = new myTexture("subject.png");
    }

    public void update(SpriteBatch b, Colles CollesToDisplay, int week, int groupNumber, String groupMembers, Vector2 offset, float scale, Color boxColor, float boxPadding, float boxRadius) {
        handleInput();

        render(b, CollesToDisplay, week, groupNumber, groupMembers, offset, scale, boxColor, boxPadding, boxRadius);
    }

    /**
     * Gérer les actions de l'utilisateur lorsqu'il swipe pour changer de semaine
     */
    public void handleInput() {
        Vector2 currentPosition = new Vector2(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());

        action = Action.NONE;

        //déplacement du contexte dans le cas ou l'utilisateur est en train d'appuyer
        if (Gdx.input.isTouched()) {

            //case if the user just touched
            if (!touching) {
                oldPosition = currentPosition;
            }

            this.mouseSpeed = oldPosition.addCpy(currentPosition.SclCpy(-1));


        } else {

            //action à déclencher dans le cas ou l'utilisateur vient de relâcher son curseur
            if (touching) {
                if (mouseSpeed.x - SWIPE_STRIGGER*this.position.x/(Gdx.graphics.getWidth()/2.0f) > SWIPE_STRIGGER) {
                    this.action = Action.LEFT;
                    this.mouseSpeed.x = SWIPE_STRIGGER;

                } else if (mouseSpeed.x - SWIPE_STRIGGER*this.position.x/(Gdx.graphics.getWidth()/2.0f) < -SWIPE_STRIGGER) {
                    this.action = Action.RIGHT;
                    this.mouseSpeed.x = -SWIPE_STRIGGER;
                }

            }


        }


        //mise à jour de la position
        if (touching) {
            this.position.x = this.position.x - mouseSpeed.x;
        } else {
            //rapprochement du centre
            this.position.x -= this.position.x*SWIPE_SPEED*Gdx.graphics.getDeltaTime();
        }

        //mise à jour des variables
        oldPosition = currentPosition;
        touching = Gdx.input.isTouched();

    }

    public Action getAction() {
        return this.action;
    }

    public void render(SpriteBatch b, Colles CollesToDisplay, int week, int groupNumber, String groupMembers, Vector2 offset, float scale, Color boxColor, float boxPadding, float boxRadius) {
        position.y = -0.001f*scale*Math.abs(offset.x+ position.x);
        if (CollesToDisplay != null) {
            displayColles(b, CollesToDisplay, groupNumber, groupMembers, offset, scale, boxColor, boxPadding, boxRadius);
        } else {
            text.drawText(b, font, "pas de colles trouvées", offset.addCpy(position), Anchor.CENTER, Anchor.CENTER, boxColor, boxPadding, boxRadius, Colors.smallBoxBorderWidth*scale, Colors.boxBorderColor);

        }
        text.drawText(b, font, "semaine du " + formatWeek(week), new Vector2(offset.x + position.x, scale+ position.y), Anchor.CENTER, Anchor.TOP, boxColor, boxPadding, boxRadius, Colors.smallBoxBorderWidth*scale, Colors.boxBorderColor);
        text.drawTextButStretched(b, font, "groupe n°" + groupNumber + ": " + groupMembers, new Vector2(0, scale*1.5f), Anchor.CENTER, Anchor.BOTTOM, boxColor, boxPadding, boxRadius, new Rect(new Vector2(0, 0), new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight())),Colors.smallBoxBorderWidth*scale, Colors.boxBorderColor,  Gdx.graphics.getWidth()-2*boxPadding);
    }

    private String formatWeek(int weekNumber) {
        Calendar date = Calendar.getInstance();
        String finalString = "";

        date.add(Calendar.DATE, 7*(weekNumber));

        dateUtils.setToFirstDayOfTheWeek(date);
        finalString += formatNumber(date.get(Calendar.DAY_OF_MONTH)) + "/" +  formatNumber(date.get(Calendar.MONTH)+1);

        finalString += " au ";

        date.add(Calendar.DATE, 5);
        finalString += formatNumber(date.get(Calendar.DAY_OF_MONTH)) + "/" +  formatNumber(date.get(Calendar.MONTH)+1);


        return finalString;
    }

    private String formatNumber(int number) {
        String s = "" + number;
        if (s.length() < 2) {
            s = "0" + s;
        }

        return s;

    }


    /**
     *  afficher le contexte des colles
     */
    private void displayColles(SpriteBatch b, Colles CollesToDisplay, int groupNumber, String groupMembers, Vector2 offset, float scale, Color boxColor, float boxPadding, float boxRadius) {
        Vector2 renderPosition = offset.addCpy(position);

        for (int i = 0; i < CollesToDisplay.amount; i++) {

            String txt = String.format(
                    "%s\n" +
                            "%s\n" +
                            "%s\n" +
                            "%s",
                    CollesToDisplay.colles.get(i).matiere, CollesToDisplay.colles.get(i).creneau, CollesToDisplay.colles.get(i).salle, CollesToDisplay.colles.get(i).nom);

            renderPosition.y = position.y + (i + 0.5f - CollesToDisplay.amount / 2f) * scale * 5.7f;

            Rect r = text.drawText(b, font, txt, renderPosition, Anchor.CENTER, Anchor.CENTER, boxColor, 0, boxRadius, 0, Color.CLEAR);
            r.addPaddingX(scale);
            r.draw(b, boxColor, boxPadding, boxRadius, Colors.smallBoxBorderWidth*scale, Colors.shadowColor);

            this.subjectIcon.draw(b, new Rect(new Vector2(r.position.x, r.position.y -scale*0.6f*1.3f), new Vector2(scale*0.75f, scale*0.75f)));
            this.timeIcon.draw(b, new Rect(new Vector2(r.position.x, r.position.y -scale*1.4f*1.3f), new Vector2(scale*0.75f, scale*0.75f)));
            this.localisationIcon.draw(b, new Rect(new Vector2(r.position.x, r.position.y -scale*2.2f*1.3f), new Vector2(scale*0.75f, scale*0.75f)));
            this.personIcon.draw(b, new Rect(new Vector2(r.position.x, r.position.y -scale*3f*1.3f), new Vector2(scale*0.75f, scale*0.75f)));



            //kind of poor optimisation to call the draw function two times..
            text.drawText(b, font, txt, renderPosition, Anchor.CENTER, Anchor.CENTER, Color.CLEAR, boxPadding, boxRadius, 0, Color.CLEAR);


        }


    }


}
