package me.shreyasr.elemental.field;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import me.shreyasr.elemental.Game;
import me.shreyasr.elemental.field.entities.Monster;

public class Field {

    Lane[] lanes = new Lane[6];
    public Field() {
        for (int i=0;i<6;i++) {
            lanes[i] = new Lane(i);
        }
    }

    public void addMonster(Monster m) {
        lanes[m.lane].addMonster(m);
    }

    public void update() {
        for (Lane l : lanes) {
            l.update();
        }
    }
    ShapeRenderer shapeRenderer = new ShapeRenderer();
    public void render(SpriteBatch batch) {
        for (int i=0;i<6;i++) {
            lanes[i].render(batch, Game.LANE_WIDTH * i);
        }
    }
}
