package com.mp2i.colloscope.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.Colles;

import java.util.Objects;

public class UserInterface {

    private Colles CollesToDisplay;
    //Error message to display, leave "" to no error
    String message = "";
    //main font used to display text
    BitmapFont font;
    //scale of the interface on which relie most of the ui components
    float scale;
    //position of the text (created here to avoid having to create new instances in the functions
    Vector2 textPosition;
    //Size of the text to be displayed
    int textSize = 24;
    //color of the box in which text is displayed
    Color boxColor;
    // padding inside that box
    float boxPadding = 50;
    //radius of the edges of the box (rounded box)
    float boxRadius = 10;
    //button (left arrow) to substract one from group number
    Button nextGroup;
    //button (right arrow) to add one from group number
    Button previousGroup;

    // group number
    int groupNumber = 1;
    //position of the text displaying the group number
    Vector2 groupPosition = new Vector2();
    // whether or not the Colles should be updated to the group number
    private boolean refreshRequested = false;




    public UserInterface() {
        //important to init before rendering shapes
        Rect.initShapeRenderer();
        this.loadInterface();
    }

    /**
     * update the interface with the new windows dimensions
     */
    public void resize() {
        this.disposeMembers();
        this.loadInterface();

        //tbh I don't understand why but this prevents the boxes of the texts to break
        Rect.disposeShapeRenderer();
        Rect.initShapeRenderer();
    }

    /**
     * Avoid unnecessary memory leaks
     */
    public void disposeMembers() {
        previousGroup.dispose();
        nextGroup.dispose();
        font.dispose();
    }

    /**
     * Load members
     */
    private void loadInterface() {
        scale = Math.min((float) Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight())  / 15;
        textSize = (int) scale;

        font = text.loadFont("VarelaRound-Regular.ttf", textSize);
        textPosition = new Vector2(0, 0);

        boxColor = new Color(0, 0, 0, 0.2f);

        previousGroup = new Button("left.png", new Vector2(), new Vector2(scale*4, scale*4), boxColor, Anchor.LEFT, Anchor.BOTTOM);
        nextGroup = new Button("right.png", new Vector2(scale*4, 0), new Vector2(scale*4, scale*4), boxColor, Anchor.LEFT, Anchor.BOTTOM);
    }

    /**
     * Set the colles to be displayed on the interface
     * @param c Colles object
     */
    public void setColles(Colles c) {
        this.CollesToDisplay = c;
        this.refreshRequested = false;
    }

    /**
     * Set the message to be displayed on the interface, which replaces the Colles display, and serves to show errors
     * @param m String message to display
     */
    public void setMessage(String m) {
        this.message = m;
    }


    /**
     * @return the current group number selected
     */
    public int getGroupNumber() {
        return this.groupNumber;
    }

    /**
     * @return whether the interface needs to be refreshed
     */
    public boolean needsToBeRefreshed() {
        return this.refreshRequested;
    }


    /**
     * Main function called every frame to display the interface
     * @param batch surface to draw things
     */
    public void update(SpriteBatch batch) {
        if (Objects.equals(message, "")) {
            this.displayColles(batch);
            this.handleInput(batch);

        } else {
            this.displayMessage(batch);
        }
        //textPosition.rotate(0.1f);

    }

    /**
     * Handle the input of the user
     * @param batch surface to draw things
     */
    public void handleInput(SpriteBatch batch) {
        nextGroup.update(batch);
        previousGroup.update(batch);

        if (nextGroup.isClicked()) {
            groupNumber += 1;
            this.refreshRequested = true;
        } else if (previousGroup.isClicked()) {
            groupNumber -= 1;
            this.refreshRequested = true;
        }


        if (groupNumber > 16) {
            groupNumber = 1;
        } else if (groupNumber < 1) {
            groupNumber = 16;
        }

    }

    /**
     * Draw the Colles context
     * @param b surface to draw things
     */
    public void displayColles(SpriteBatch b) {
        Vector2 displayposition = new Vector2(textPosition.x, textPosition.y);

        for (int i = 0; i < CollesToDisplay.amount; i++) {
            String txt = String.format("colle le %s\ncolleur: %s\nsalle: %s", CollesToDisplay.colles.get(i).creneau, CollesToDisplay.colles.get(i).nom, CollesToDisplay.colles.get(i).salle);

            text.drawText(b, font, txt, displayposition, Anchor.CENTER, Anchor.CENTER, boxColor, boxPadding, boxRadius);
            displayposition.y += textSize*5;

        }


        text.drawText(b, font, "groupe n"+groupNumber, groupPosition, Anchor.LEFT, Anchor.TOP, boxColor, boxPadding, boxRadius);
    }

    /**
     * Display a message on screen, generally for errors
     * @param b surface to draw things
     */
    public void displayMessage(SpriteBatch b) {
        text.drawText(b, font, message, textPosition, Anchor.CENTER, Anchor.CENTER, boxColor, boxPadding, boxRadius);
    }


}
