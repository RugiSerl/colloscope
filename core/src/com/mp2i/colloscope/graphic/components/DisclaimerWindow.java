package com.mp2i.colloscope.graphic.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.graphic.utils.Anchor;
import com.mp2i.colloscope.graphic.utils.Vector2;
import com.mp2i.colloscope.graphic.utils.text;

public class DisclaimerWindow extends Window {

    private final String warning = "" +
            "Je ne me tiens pas responsable\n" +
            "des absences/retards engendrés\n" +
            "par mon application.\n" +
            "Veillez à régulièrement mettre\n" +
            "votre application à jour, notamment\n" +
            "en cas de modifications d'emploi\n" +
            "du temps.\n" +
            "Swipez pour changer de semaine.\n" +
            "Vous pouvez changer de groupe\n" +
            "dans les paramètres.";


    private float scale;


    public DisclaimerWindow(float boxPadding, float boxRadius, float scale) {
        super("Mise en garde", new Vector2(), new Vector2(scale * 15), boxPadding, boxRadius, scale);
        this.scale = scale;
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        super.render(batch, font);
        super.updateInput();


        text.drawText(batch, font, warning, new Vector2(0, -scale*0.5f), Anchor.CENTER, Anchor.CENTER, Color.CLEAR, scale / 2, scale / 2, super.getRect(), 0, Color.CLEAR);

    }


}
