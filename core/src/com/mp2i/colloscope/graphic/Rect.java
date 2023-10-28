package com.mp2i.colloscope.graphic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Rect {
    public Vector2 position;
    public Vector2 size;
    ShapeRenderer shapeRenderer;

    private void initShapeRenderer() {
        shapeRenderer = new ShapeRenderer();
    }

    public Rect(float x, float y, float width, float height) {
        this.position = new Vector2(x, y);
        this.size = new Vector2(width, height);
        initShapeRenderer();

    }

    public Rect(Vector2 position, Vector2 size) {
        this.position = position;
        this.size = size;
        initShapeRenderer();
    }

    public Rect() {
        this.position = new Vector2();
        this.size = new Vector2();
        initShapeRenderer();

    }

    /**
     * draw the rectangle onto a surface
     * @param batch surface to draw things
     * @param color color of the rectangle
     */
    public void draw(SpriteBatch batch, Color color) {
        batch.end();

        Gdx.gl.glEnable(GL20.GL_BLEND); // needed to get the box transparency
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(this.position.x, this.position.y, this.size.x, this.size.y);
        shapeRenderer.end();
        batch.begin();
    }

    /**
     * draw the rectangle with rounded corners
     * @param batch surface to draw things
     * @param color color of the rectangle
     * @param padding length of the border added
     * @param radius radius of the corner
     */
    public void draw(SpriteBatch batch, Color color, float padding, float radius) {
        batch.end();
        this.addPadding(padding);

        Gdx.gl.glEnable(GL20.GL_BLEND); // needed to get the box transparency
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        //shapeRenderer.rect(position.x, position.y, size.x, -size.y);

        //Central rectangle
        shapeRenderer.rect(position.x + radius, position.y - radius, size.x - 2*radius, -size.y + 2*radius);

        // Four side rectangles, in clockwise order
        shapeRenderer.rect(position.x + radius, position.y, size.x - 2*radius, -radius);
        shapeRenderer.rect(position.x + size.x - radius, position.y - radius, radius, -(size.y - 2*radius));
        shapeRenderer.rect(position.x + radius, position.y - size.y + radius, size.x - 2*radius, -radius);
        shapeRenderer.rect(position.x, position.y - radius, radius, -(size.y - 2*radius));

        // Four arches, clockwise too
        shapeRenderer.arc(position.x + radius, position.y - radius, radius, 90f, 90f);
        shapeRenderer.arc(position.x + size.x - radius, position.y - radius, radius, 0f, 90f);
        shapeRenderer.arc(position.x + size.x - radius, position.y - size.y + radius, radius, 270f, 90f);
        shapeRenderer.arc(position.x + radius, position.y - size.y + radius, radius, 180f, 90f);


        shapeRenderer.end();
        this.addPadding(-padding);
        batch.begin();
    }

    /**
     * Add inner padding to the rect, making the borders farther from its center
     * @param padding length of the border added
     */
    public void addPadding(float padding) {
        this.position.add(-padding, padding);
        this.size.add(padding*2);
    }

    /**
     * Set the rect coordinate to match with anchor type, if the rect origin must be from the left, top, bottom, ..
     * @param position position of the rect
     * @param horizontalAnchor horizontal origin
     * @param verticalAnchor vertical origin
     */
    public void setToAnchor(Vector2 position, Anchor horizontalAnchor, Anchor verticalAnchor) {

        switch (horizontalAnchor) {
            case LEFT:
                break;
            case RIGHT:
                this.position.x = Gdx.graphics.getWidth() - position.x - this.size.x;
                break;
            case CENTER:
                this.position.x = (float) Gdx.graphics.getWidth() / 2 + position.x - this.size.x / 2;
                break;
        }

        switch (verticalAnchor) {
            case BOTTOM:
                break;
            case TOP:
                this.position.y = Gdx.graphics.getHeight() - position.y - this.size.y;
                break;
            case CENTER:
                this.position.y = (float) Gdx.graphics.getHeight() / 2 + position.y - this.size.y / 2;
                break;
        }


    }

    @Override
    public String toString() {
        return "{" + this.position.x + " " + this.position.y + " " + this.size.x + " " + this.size.y + "}";
    }

    /**
     * Detect if a point is colliding with the rectangle
     * @param x point horizontal coordinate
     * @param y point vertical coordinate
     * @return boolean
     */
    public boolean detectPointCollision(float x, float y) {
        return (x >= this.position.x && x <= this.position.x + this.size.x && y >= this.position.y && y <= this.position.y + this.size.y);
    }

}
