package me.shreyasr.elemental.field;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

import me.shreyasr.elemental.Game;

public class Lane {

    private List<Monster> monsters = new ArrayList<Monster>();

    public void addMonster(Monster m) {
        monsters.add(m);
    }

    public void update() {
        for (Monster m : monsters) {
            m.update();
        }
    }

    public void render(SpriteBatch batch, int lanePos, int laneStart) {
        for (Monster m : monsters) {
            batch.draw(m.sprite.animation.getKeyFrame(Game.DELTA), m.xOff+lanePos, m.y+laneStart);
        }
    }
}
