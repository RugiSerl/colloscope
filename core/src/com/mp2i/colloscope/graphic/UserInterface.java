package com.mp2i.colloscope.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.Colles;
import com.mp2i.colloscope.graphic.components.ColleDisplay;
import com.mp2i.colloscope.graphic.components.Colors;
import com.mp2i.colloscope.graphic.components.DisclaimerWindow;
import com.mp2i.colloscope.graphic.components.NetworkInfoWindow;
import com.mp2i.colloscope.graphic.utils.Anchor;
import com.mp2i.colloscope.graphic.components.Button;
import com.mp2i.colloscope.graphic.components.SettingsWindow;
import com.mp2i.colloscope.graphic.components.myTexture;
import com.mp2i.colloscope.graphic.utils.Rect;
import com.mp2i.colloscope.graphic.utils.Vector2;
import com.mp2i.colloscope.graphic.utils.text;
import com.mp2i.colloscope.internet;
import com.mp2i.colloscope.persistent;

import java.util.Objects;

public class UserInterface {

    private Colles[] CollesToDisplay = {null, null, null};
    private final ColleDisplay[] colleDisplay = {null, null, null};
    private int weekOffset = 0;
    private String groupMembers;
    //Error message to display, leave "" to no error
    String message = "";
    //main font used to display text
    BitmapFont font;
    //scale of the interface on which relies most of the ui components
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
    Button settings;
    SettingsWindow settingsWindow;
    NetworkInfoWindow networkInfoWindow;
    DisclaimerWindow disclaimerWindow;
    // VERY IMPORTANT: here, the array contains 1 ELEMENT, in order to give the value "by adress" to the settingsWindow
    int[] groupNumber = {-1};
    // exact same thing here
    private boolean[] refreshRequested = {true};


    Sprite easterEggImg;


    // group number
    //position of the text displaying the group number
    private Vector2 groupPosition;
    // whether or not the Colles should be updated to the group number





    public UserInterface() {
        //important to init before rendering shapes
        Rect.initShapeRenderer();
        this.loadInterface();
        if (persistent.isFirstLaunch()) {
            disclaimerWindow = new DisclaimerWindow(boxPadding, boxRadius, scale);

        }

        if (disclaimerWindow == null) {
            // Check update at startup
            try {
                if (!internet.IsLastVersion()) {
                    this.networkInfoWindow = new NetworkInfoWindow("La version n'est pas à jour", boxRadius, boxPadding, scale);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }





    }

    private void checkForUpdates() {
        try {
            if (!internet.IsLastVersion()) {
                this.networkInfoWindow = new NetworkInfoWindow("La version n'est pas à jour", boxPadding, boxRadius, scale );
            } else {
                this.networkInfoWindow = new NetworkInfoWindow("La version est pas à jour", boxPadding, boxRadius, scale );

            }
        } catch (Exception e) {
            this.networkInfoWindow = new NetworkInfoWindow("impossible de vérifier la version", boxPadding, boxRadius, scale );
        }
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

        font.dispose();
        settings.dispose();
        if (settingsWindow != null) settingsWindow.dispose();
        if (networkInfoWindow != null) networkInfoWindow.dispose();

    }

    /**
     * Load members
     */
    private void loadInterface() {
        scale = Math.min((float) Gdx.graphics.getWidth(), (float)Gdx.graphics.getHeight())  / 20;
        textSize = (int) (scale*0.9f);

        Texture tex = new myTexture("easter_egg.jpg");
        easterEggImg = new Sprite(tex);
        easterEggImg.setColor(1, 1, 1, 0.4f);


        font = text.loadFont("VarelaRound-Regular.ttf", textSize, Colors.shadowColor);
        font.setColor(Colors.textColor);
        textPosition = new Vector2(0, 0);

        for (int i = 0; i < colleDisplay.length; i++) {
            colleDisplay[i] = new ColleDisplay(font);
        }

        boxColor = Colors.boxColor;
        this.boxPadding = scale / 1.5f;
        this.boxRadius = scale / 5;
        this.groupPosition = new Vector2(this.boxPadding*2, this.boxPadding);

        if (this.settingsWindow != null ) this.settingsWindow = new SettingsWindow(groupNumber, scale, boxRadius, boxPadding, refreshRequested );
        if (this.networkInfoWindow != null ) this.networkInfoWindow = new NetworkInfoWindow(this.networkInfoWindow.messageText, boxPadding, boxRadius, scale );
        if (this.disclaimerWindow != null ) disclaimerWindow = new DisclaimerWindow(boxPadding, boxRadius, scale);



        settings = new Button("settings.png", new Vector2(), scale, Anchor.RIGHT, Anchor.TOP);
    }

    /**
     * Set the colles to be displayed on the interface
     * @param c Colles object
     */
    public void setColles(Colles[] c) {
        this.CollesToDisplay = c;
        this.refreshRequested[0] = false;
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
        this.groupNumber[0] = group;

    }

    public int getWeekOffset() {
        return this.weekOffset;
    }

    /**
     * @return the current group number selected
     */
    public int getGroupNumber() {
        return this.groupNumber[0];
    }

    /**
     * @return whether the interface needs to be refreshed
     */
    public boolean needsToBeRefreshed() {
        return this.refreshRequested[0];
    }


    /**
     * Main function called every frame to display the interface
     * @param batch surface to draw things
     */
    public void update(SpriteBatch batch) {

        //this prevent everything to be updated if the warning windows is still here
        if (this.disclaimerWindow == null) {
            if (groupNumber[0] == 15) {
                this.easterEgg(batch);
            }


            if (Objects.equals(message, "")) {
                this.displayColles(batch);
                this.handleInput(batch);

            } else {
                this.displayMessage(batch);
            }
        }



        this.updateWindows(batch);

    }

    //update all the windows of the interface
    private void updateWindows(SpriteBatch batch) {

        //start by updating input in order
        //the order of input() is the opposite of render()
        if (this.networkInfoWindow != null) {
            this.networkInfoWindow.updateInput();
        }


        if (this.settingsWindow != null) {
            this.settingsWindow.updateInput();
        }


        //render the windows
        if (this.settingsWindow != null) {
            this.settingsWindow.update(batch, font);

            if (this.settingsWindow.refreshNewVersion.isClicked()) {
                this.checkForUpdates();
            }

            if (this.settingsWindow.hidden) {
                this.settingsWindow = null;
            }
        }

        if (this.networkInfoWindow != null) {
            this.networkInfoWindow.update(batch, font);
            if (this.networkInfoWindow.hidden) {
                this.networkInfoWindow = null;
            }
        }

        if (this.disclaimerWindow != null) {
            this.disclaimerWindow.update(batch, font);
            if (this.disclaimerWindow.hidden) {
                this.disclaimerWindow = null;
            }
        }
    }

    private void displayColles(SpriteBatch batch) {
        //update all displays
        for (int i = 0; i<colleDisplay.length; i++) {
            colleDisplay[i].update(batch, CollesToDisplay[i], (i-1+weekOffset),groupNumber[0], groupMembers, new Vector2((i- colleDisplay.length/2)*Gdx.graphics.getWidth(), 0), scale, boxColor, boxPadding, boxRadius);

        }
        // Check if the user has swiped
        if (colleDisplay[1].getAction() != ColleDisplay.Action.NONE) {

            this.refreshRequested[0] = true;

            if (colleDisplay[1].getAction() == ColleDisplay.Action.RIGHT) {
                this.weekOffset--;
                for (ColleDisplay c: colleDisplay) c.position.x = c.position.x - Gdx.graphics.getWidth();

            }else if (colleDisplay[1].getAction() == ColleDisplay.Action.LEFT) {
                this.weekOffset++;
                for (ColleDisplay c: colleDisplay) c.position.x = c.position.x + Gdx.graphics.getWidth() ;

            }





        }

    }


    // Pour Gaëtan
    public void easterEgg(SpriteBatch batch) {
        easterEggImg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        easterEggImg.draw(batch);
    }


    /**
     * Handle the input of the user
     * @param batch surface to draw things
     */
    public void handleInput(SpriteBatch batch) {

        settings.update(batch);

        if (settings.isClicked()) {
            //create window
            this.settingsWindow = new SettingsWindow(groupNumber, scale, boxRadius, boxPadding, refreshRequested);
        }



    }


    /**
     * Display a message on screen, generally for errors
     * @param b surface to draw things
     */
    public void displayMessage(SpriteBatch b) {
        text.drawText(b, font, message, textPosition, Anchor.CENTER, Anchor.CENTER, boxColor, boxPadding, boxRadius, 0, Colors.boxBorderColor);

    }


}
