package me.shreyasr.elemental.field.entities;

import com.badlogic.gdx.utils.TimeUtils;

import java.nio.ByteBuffer;

import me.shreyasr.elemental.Element;
import me.shreyasr.elemental.Game;
import me.shreyasr.elemental.graphics.Sprite;

public class Monster implements me.shreyasr.elemental.field.entities.Entity{

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

    public Sprite sprite;
    public Type type;
    public Orientation orientation;

    public int xOff = (int) (Math.random()* Game.LANE_WIDTH);
    public double y = -1;
    public long endTime = 0;
    public int lane = -1;
    public final double speed;
    public Element element;
    public double health;
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
    @Override
    public void takeDamage(Attack a){
        if(a.type.counter(element))
            health-=a.baseDamage * 2;
        else
            health-=a.baseDamage;
        if(health < 0)
            die();
    }
    @Override
    public void die(){

    }
}
































