package me.shreyasr.elemental.field;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.shreyasr.elemental.Game;

public class Field {

    Lane[] lanes = new Lane[6];

    public Field() {
        for (int i=0;i<6;i++) {
            lanes[i] = new Lane();
        }
    }

    public void addMonster(Monster m, int lane) {
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
            lanes[i].render(batch, Game.LANE_WIDTH*i);

            batch.end();
            shapeRenderer.setAutoShapeType(true);
            shapeRenderer.begin();
            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.set(ShapeRenderer.ShapeType.Line);
            shapeRenderer.rect(Game.LANE_WIDTH*i, Game.LANE_START, Game.LANE_WIDTH, Game.HEIGHT-Game.LANE_START);
            shapeRenderer.end();
            batch.begin();
        }
    }
}
