package com.mp2i.colloscope.graphic.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;


public class text {

    public static GlyphLayout layout;

    /**
     * Generate a font using TTF generator
     * @param fontPath internal path of the font file
     * @param size size of the text
     * @return Bitmap-font generated
     */
    public static BitmapFont loadFont(String fontPath, int size, Color shadowColor) {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.characters += "—€«»ÀàÂâÆæÇçÉéÈèÊêËëÎîÏïÔôŒœÙùÛûÜüŸÿ°";
        parameter.shadowOffsetX = 1;
        parameter.shadowOffsetY = 1;
        parameter.magFilter = com.badlogic.gdx.graphics.Texture.TextureFilter.Linear;
        parameter.minFilter = com.badlogic.gdx.graphics.Texture.TextureFilter.Linear;
        parameter.shadowColor = shadowColor;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        // kind of unrelated object, initialized here to avoid having to load it later
        layout = new GlyphLayout(); //dont do this every frame! Store it as member

        return font;

    }

    public static void drawText(SpriteBatch b, BitmapFont font, String text, Vector2 position) {
        font.draw(b, text, position.x, position.y);
    }

    public static void drawTextButStretched(SpriteBatch b, BitmapFont font, String text, Vector2 position, Anchor horizontalAnchor, Anchor verticalAnchor, Color boxColor, float boxPadding, float boxRadius, Rect containingRect, float borderWidth, Color borderColor, float maxWidth) {
        // Calculating text Size
        layout.setText(font, text);
        if (layout.width > maxWidth) {
            float amount = (maxWidth - layout.width)/maxWidth*0.7f ;
            font.getData().scale(amount);
            drawText(b, font, text, position, horizontalAnchor, verticalAnchor, boxColor, boxPadding, boxRadius, containingRect, borderWidth, borderColor);
            font.getData().scale(-amount);

        } else {
            drawText(b, font, text, position, horizontalAnchor, verticalAnchor, boxColor, boxPadding, boxRadius, containingRect, borderWidth, borderColor);
        }


    }

    /**
     * draw the text with additionnal options
     * @param b surface to draw things
     * @param font font of the text
     * @param text text to draw
     * @param position position of the text
     * @param horizontalAnchor horizontal origin
     * @param verticalAnchor vertical origin
     * @param boxColor color of the box the text is drawn over
     * @param boxPadding padding of this box
     * @param boxRadius corner radius of this box
     */
    public static Rect drawText(SpriteBatch b, BitmapFont font, String text, Vector2 position, Anchor horizontalAnchor, Anchor verticalAnchor, Color boxColor, float boxPadding, float boxRadius, float borderWidth, Color borderColor) {
        return drawText(b,
                font,
                text,
                position,
                horizontalAnchor,
                verticalAnchor,
                boxColor,
                boxPadding,
                boxRadius,
                new Rect(0,
                         0,
                         Gdx.graphics.getWidth(),
                         Gdx.graphics.getHeight()),
                borderWidth,
                borderColor);
    }

    /**
     * draw the text with additionnal options
     * @param b surface to draw things
     * @param font font of the text
     * @param text text to draw
     * @param position position of the text
     * @param horizontalAnchor horizontal origin
     * @param verticalAnchor vertical origin
     * @param boxColor color of the box the text is drawn over
     * @param boxPadding padding of this box
     * @param boxRadius corner radius of this box
     * @param containingRect rect in which is drawn the text
     */
    public static Rect drawText(SpriteBatch b, BitmapFont font, String text, Vector2 position, Anchor horizontalAnchor, Anchor verticalAnchor, Color boxColor, float boxPadding, float boxRadius, Rect containingRect, float borderWidth, Color borderColor) {
        // Calculating text Size
        layout.setText(font, text);
        float textWidth = layout.width;
        float textHeight = layout.height;

        Rect rect = new Rect(position.x, position.y, textWidth, textHeight);

        rect.setToAnchor(rect.position, horizontalAnchor, verticalAnchor, containingRect);
        rect.position.y += rect.size.y;
        rect.draw(b, boxColor, boxPadding, boxRadius, borderWidth, borderColor);


        drawText(b, font, text, rect.position);


        return rect;
    }



}
