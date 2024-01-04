package com.mp2i.colloscope.graphic.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.graphic.utils.Anchor;
import com.mp2i.colloscope.graphic.utils.Vector2;
import com.mp2i.colloscope.graphic.utils.text;

public class NetworkInfoWindow extends Window {

    public String messageText;
    private float scale;
    private Button github;

    public NetworkInfoWindow(String text, float boxPadding, float boxRadius, float scale) {
        super("information", new Vector2(), new Vector2(scale * 10), boxPadding, boxRadius, scale);
        this.messageText = text;
        this.scale = scale;
        github = new Button("github.png", new Vector2(), scale, Anchor.RIGHT, Anchor.BOTTOM);


    }
    public void update(SpriteBatch batch, BitmapFont titleFont) {
        super.update(batch, titleFont);
        text.drawText(batch, titleFont, this.messageText, new Vector2(), Anchor.CENTER, Anchor.CENTER, Color.CLEAR, scale/2, scale/2, super.getRect(), 0, Color.CLEAR);

        if (this.messageText.equals("La version n'est pas à jour")) {
            text.drawText(batch, titleFont, "télécharger :", new Vector2(scale*2, scale*2), Anchor.LEFT, Anchor.BOTTOM,  Color.CLEAR, scale/2, scale/2, super.getRect(), 0, Color.CLEAR);
            github.update(batch, super.getRect());

            if (github.isClicked()) {
                Gdx.net.openURI("https://github.com/RugiSerl/colloscope/releases/latest");
            }
        }



    }



}
