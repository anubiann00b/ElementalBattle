package me.shreyasr.elemental.field;

import com.badlogic.gdx.utils.TimeUtils;

import java.nio.ByteBuffer;

import me.shreyasr.elemental.Game;
import me.shreyasr.elemental.graphics.Sprite;

public class Monster {

    public byte[] serialize() {
        return ByteBuffer.allocate(24)
                .putLong(endTime)
                .putInt(type.ordinal())
                .putDouble(speed)
                .putInt(lane)
                .array();
    }

    public static Monster deserialize(byte[] arr) {
        ByteBuffer buffer = ByteBuffer.wrap(arr);
        return new Monster(Type.values()[buffer.getInt(8)], Orientation.EVIL, buffer.getDouble(12));
    }

    public enum Type {
        FIRE_3(Sprite.FIRE_3);

        public final Sprite sprite;

        Type(Sprite sprite) {
            this.sprite = sprite;
        }
    }

    public enum Orientation { GOOD, EVIL }

    Sprite sprite;
    Type type;
    Orientation orientation;

    int xOff = (int) (Math.random()* Game.LANE_WIDTH);
    double y = -1;
    long endTime = 0;
    int lane = -1;
    final double speed;

    public Monster(Monster.Type type, Orientation orientation, double speed) {
        this.type = type;
        this.orientation = orientation;
        this.speed = speed;
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
