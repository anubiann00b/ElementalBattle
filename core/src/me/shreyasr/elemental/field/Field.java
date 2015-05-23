package me.shreyasr.elemental.field;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Field {

    public static final int LANE_WIDTH = 120;
    public static final int LANE_START = 0;
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

    public void render(SpriteBatch batch) {
        for (int i=0;i<6;i++) {
            lanes[i].render(batch, LANE_START+LANE_WIDTH*i, 0);
        }
    }
}
