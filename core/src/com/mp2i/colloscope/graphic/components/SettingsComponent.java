package com.mp2i.colloscope.graphic.components;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mp2i.colloscope.graphic.utils.Rect;
import com.mp2i.colloscope.graphic.utils.Vector2;

public class SettingsComponent {
    InputComponent inputComponent;
    TextLabel label;
    String name;
    boolean valueSet = false;
    Boolean ButtonValue;
    Rect size;



    public SettingsComponent(String settingName, InputComponent.inputType type, Vector2 position, float scale, String buttonTexturePath, BitmapFont font, Anchor horizontalAnchor, Anchor verticalAnchor) {

        label = new TextLabel(settingName, position, font, Anchor.LEFT, Anchor.TOP);

        switch (type) {
            case Button:
                this.inputComponent = new Button(buttonTexturePath, new Vector2(), new Vector2(scale*4, scale*4), Anchor.RIGHT, Anchor.TOP);
                break;
        }

        this.size = new Rect(position, new Vector2(this.label.size.x + this.inputComponent.getDimensions().size.x, Math.max(this.label.size.y, this.inputComponent.getDimensions().size.y)));




    }

    public void giveButtonValue(Boolean value) {
        valueSet = true;

        this.inputComponent.giveValue(value);


    }



    public void update(SpriteBatch batch, Rect containingRect) {
        assert valueSet;
        inputComponent.update(batch, containingRect);
        //TODO : this has to change
        this.ButtonValue = inputComponent.getValue();


    }

}
