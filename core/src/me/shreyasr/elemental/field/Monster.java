package me.shreyasr.elemental.field;

import com.badlogic.gdx.utils.TimeUtils;

import me.shreyasr.elemental.Game;
import me.shreyasr.elemental.graphics.Sprite;

public class Monster {

    public enum Type {
        FIRE_3(Sprite.FIRE_3);

        public final Sprite sprite;

        Type(Sprite sprite) {
            this.sprite = sprite;
        }
    }

    public enum Orientation { GOOD, EVIL }

    Sprite sprite;
    Orientation orientation;

    int xOff = (int) (Math.random()* Game.LANE_WIDTH);
    double y = -1;
    double speed = 1;
    long endTime = 0;

    public Monster(Monster.Type type, Orientation orientation) {
        this.orientation = orientation;
        this.sprite = type.sprite;
    }

    public boolean update() {
        y += speed*Game.LANE_LENGTH*0.001*0.01;
        if (y>0) {
            endTime = TimeUtils.millis();
            return true;
        }
        return false;
    }
}
