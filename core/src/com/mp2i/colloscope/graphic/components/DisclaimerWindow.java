package com.mp2i.colloscope.graphic.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.graphic.utils.Anchor;
import com.mp2i.colloscope.graphic.utils.Vector2;
import com.mp2i.colloscope.graphic.utils.text;

public class DisclaimerWindow extends Window {

    private final String warning = "" +
            "Je ne suis pas responsable\n" +
            "si vous êtes absent/en retard\n" +
            "à cause de mon application.\n" +
            "Faites attention à mettre à\n" +
            "jour régulièrement l'application\n" +
            "surtout lorsqu'il y a des\n" +
            "changements d'emplois du temps.\n" +
            "\n" +
            "Vous pouvez swipe à gauche et à\n" +
            "droite pour changer de semaine.\n" +
            "Vous pouvez changer votre groupe\n" +
            "dans les paramètres.";


    private float scale;


    public DisclaimerWindow(float boxPadding, float boxRadius, float scale) {
        super("Mise en garde", new Vector2(), new Vector2(scale * 15), boxPadding, boxRadius, scale);
        this.scale = scale;
    }

    public void update(SpriteBatch batch, BitmapFont font) {
        super.update(batch, font);

        text.drawText(batch, font, warning, new Vector2(0, -scale*0.5f), Anchor.CENTER, Anchor.CENTER, Color.CLEAR, scale / 2, scale / 2, super.getRect(), 0, Color.CLEAR);

    }


}
