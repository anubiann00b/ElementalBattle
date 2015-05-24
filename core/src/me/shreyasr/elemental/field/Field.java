package me.shreyasr.elemental.field;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.shreyasr.elemental.Game;

public class Field {

    public static final int LANE_WIDTH = Game.WIDTH/6;
    public static final int LANE_START = Game.HEIGHT-LANE_WIDTH*5;
    Lane[] lanes = new Lane[6];

    public Field() {
        for (int i=0;i<6;i++) {
            lanes[i] = new Lane();
        }
    }

    public void addMoster(Monster m, int lane) {
        lanes[lane].addMonster(m);
    }

    public void update() {
        for (Lane l : lanes) {
            l.update();
        }
    }
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    public void render(SpriteBatch batch) {
        for (int i=0;i<6;i++) {
            lanes[i].render(batch, LANE_WIDTH*i, LANE_START);

            batch.end();
            shapeRenderer.setAutoShapeType(true);
            shapeRenderer.begin();
            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
            shapeRenderer.rect(LANE_WIDTH*i, LANE_START, LANE_WIDTH, Game.HEIGHT-LANE_START);
            shapeRenderer.end();
            batch.begin();
        }
    }
}
