package com.mp2i.colloscope.graphic.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mp2i.colloscope.graphic.components.Anchor;


public class text {

    public static GlyphLayout layout;

    /**
     * Generate a font using TTF generator
     * @param fontPath internal path of the font file
     * @param size size of the text
     * @return Bitmap-font generated
     */
    public static BitmapFont loadFont(String fontPath, int size) {

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        parameter.characters += "—€«»ÀàÂâÆæÇçÉéÈèÊêËëÎîÏïÔôŒœÙùÛûÜüŸÿ°";
        parameter.shadowOffsetX = 1;
        parameter.shadowOffsetY = 1;
        parameter.magFilter = com.badlogic.gdx.graphics.Texture.TextureFilter.Linear;
        parameter.minFilter = com.badlogic.gdx.graphics.Texture.TextureFilter.Linear;
        parameter.shadowColor = new Color(0, 0, 0, 1f);
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        // kind of unrelated object, initialized here to avoid having to load it later
        layout = new GlyphLayout(); //dont do this every frame! Store it as member

        return font;

    }

    public static void drawText(SpriteBatch b, BitmapFont font, String text, Vector2 position) {
        font.draw(b, text, position.x, position.y);
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
    public static Rect drawText(SpriteBatch b, BitmapFont font, String text, Vector2 position, Anchor horizontalAnchor, Anchor verticalAnchor, Color boxColor, float boxPadding, float boxRadius) {
        return drawText(b, font, text, position, horizontalAnchor, verticalAnchor, boxColor, boxPadding, boxRadius, new Rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
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
    public static Rect drawText(SpriteBatch b, BitmapFont font, String text, Vector2 position, Anchor horizontalAnchor, Anchor verticalAnchor, Color boxColor, float boxPadding, float boxRadius, Rect containingRect) {
        // Calculating text Size
        layout.setText(font, text);
        float textWidth = layout.width;
        float textHeight = layout.height;

        Rect rect = new Rect(position.x, position.y, textWidth, textHeight);

        rect.setToAnchor(rect.position, horizontalAnchor, verticalAnchor, containingRect);

        rect.draw(b, boxColor, boxPadding, boxRadius);
        drawText(b, font, text, rect.position);

        //very important to remove instance of rect, preventing memory leaks !
        System.gc();

        return rect;
    }



}
