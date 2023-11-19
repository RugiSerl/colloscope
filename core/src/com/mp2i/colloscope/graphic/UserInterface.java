package com.mp2i.colloscope.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.Colles;
import com.mp2i.colloscope.graphic.components.Anchor;
import com.mp2i.colloscope.graphic.components.Button;
import com.mp2i.colloscope.graphic.components.Window;
import com.mp2i.colloscope.graphic.components.myTexture;
import com.mp2i.colloscope.graphic.utils.Rect;
import com.mp2i.colloscope.graphic.utils.Vector2;
import com.mp2i.colloscope.graphic.utils.text;

import java.util.Objects;

public class UserInterface {

    private Colles CollesToDisplay;
    private String groupMembers;
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
    float boxPadding;
    //radius of the edges of the box (rounded box)
    float boxRadius;
    //button (left arrow) to substract one from group number
    Button nextGroup;
    //button (right arrow) to add one from group number
    Button previousGroup;
    Button settings;
    Window settingsWindow;

    myTexture easterEggImg;


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
        scale = Math.min((float) Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight())  / 20;
        textSize = (int) scale;

        easterEggImg = new myTexture("easter_egg.jpg");

        font = text.loadFont("VarelaRound-Regular.ttf", textSize);
        textPosition = new Vector2(0, 0);

        boxColor = new Color(1.0f, 1.0f, 1.0f, 0.1f);
        this.boxPadding = scale / 1.5f;
        this.boxRadius = scale / 2;
        this.groupPosition = new Vector2(this.boxPadding*2, this.boxPadding);

        if (this.settingsWindow != null ) this.settingsWindow = new Window(new Vector2(0, 0), new Vector2(scale*10, scale*10), new Color(0.5f, 0.5f, 0.5f, 1.0f), boxPadding, boxRadius);

        previousGroup = new Button("left.png", new Vector2(), new Vector2(scale*4, scale*4), Anchor.LEFT, Anchor.BOTTOM);
        nextGroup = new Button("right.png", new Vector2(scale*4, 0), new Vector2(scale*4, scale*4), Anchor.LEFT, Anchor.BOTTOM);
        settings = new Button("settings.png", new Vector2(), new Vector2(scale*4, scale*4), Anchor.RIGHT, Anchor.TOP);
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

    public void setGroupMembers(String[] members) {
        this.groupMembers = "";
        for (String member : members) {
            this.groupMembers += member + ", ";
        }

        //remove the two last characters
        this.groupMembers = this.groupMembers.substring(0, this.groupMembers.length() - 2);

    }

    public void setGroupNumber(int group) {
        this.groupNumber = group;

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



        if (groupNumber == 15) {
            this.easterEgg(batch);
        }


        if (Objects.equals(message, "")) {
            this.displayColles(batch);
            this.handleInput(batch);

        } else {
            this.displayMessage(batch);
        }
        //textPosition.rotate(0.1f);
        if (this.settingsWindow != null) {
            this.settingsWindow.update(batch);
            if (this.settingsWindow.hidden) {
                this.settingsWindow = null;
            }
        }


    }

    public void easterEgg(SpriteBatch batch) {

        this.easterEggImg.draw(batch, new Rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
    }


    /**
     * Handle the input of the user
     * @param batch surface to draw things
     */
    public void handleInput(SpriteBatch batch) {
        nextGroup.update(batch);
        previousGroup.update(batch);
        settings.update(batch);

        if (settings.isClicked()) {
            //create window
            this.settingsWindow = new Window(new Vector2(0, 0), new Vector2(scale*10, scale*10), new Color(0.5f, 0.5f, 0.5f, 1.0f), boxPadding, boxRadius);
        }

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

            String txt = String.format(
                            "Colle %s\n" +
                            "Matière:  %s\n" +
                            "Professeur: %s\n" +
                            "Salle: %s",
                    CollesToDisplay.colles.get(i).creneau, CollesToDisplay.colles.get(i).matiere, CollesToDisplay.colles.get(i).nom, CollesToDisplay.colles.get(i).salle);

            displayposition.y = (i + 1 - CollesToDisplay.amount / 2f) * scale * 6;
            text.drawText(b, font, txt, displayposition, Anchor.CENTER, Anchor.CENTER, boxColor, boxPadding, boxRadius);

        }


        text.drawText(b, font, "groupe n°"+groupNumber+ " " + groupMembers, groupPosition, Anchor.LEFT, Anchor.TOP, boxColor, boxPadding, boxRadius);
    }

    /**
     * Display a message on screen, generally for errors
     * @param b surface to draw things
     */
    public void displayMessage(SpriteBatch b) {
        text.drawText(b, font, message, textPosition, Anchor.CENTER, Anchor.CENTER, boxColor, boxPadding, boxRadius);
    }


}
