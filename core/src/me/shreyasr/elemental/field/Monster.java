package me.shreyasr.elemental.field;

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
    int y = -1;
    int speed = 1;

    public Monster(Monster.Type type, Orientation orientation) {
        this.orientation = orientation;
        this.sprite = type.sprite;
    }

    public void update() {
        y += speed*Game.LANE_LENGTH*0.001;
    }
}
