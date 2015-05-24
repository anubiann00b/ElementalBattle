package me.shreyasr.elemental.field.entities;

import com.badlogic.gdx.utils.TimeUtils;

import java.nio.ByteBuffer;
import java.util.List;

import me.shreyasr.elemental.Element;
import me.shreyasr.elemental.Game;
import me.shreyasr.elemental.graphics.MonsterSprite;

public class Monster implements Entity {

    public byte[] serialize() {
        return ByteBuffer.allocate(24)
                .putLong(endTime)
                .putInt(type.ordinal())
                .putDouble(-speed)
                .putInt(lane)
                .array();
    }

    public static Monster deserialize(byte[] arr) {
        ByteBuffer buffer = ByteBuffer.wrap(arr);
        Monster m = new Monster(Type.values()[buffer.getInt(8)],
                Orientation.EVIL,
                buffer.getDouble(12),
                buffer.getInt(20));
        m.y = 0;
        return m;
    }

    public enum Type {
        FIRE_3(MonsterSprite.FIRE_3);

        public final MonsterSprite sprite;

        Type(MonsterSprite sprite) {
            this.sprite = sprite;
        }
    }

    public enum Orientation { GOOD, EVIL }

    public MonsterSprite sprite;
    public Type type;
    public Orientation orientation;

    public int xOff = (int) (Math.random()* Game.LANE_WIDTH);
    public double y = -1;
    public long endTime = 0;
    public int lane;
    public final double speed;
    public Element element = Element.FIRE;
    public double health;
    public double attackStrength;

    public Monster(Monster.Type type, Orientation orientation, double speed, int lane) {
        this.type = type;
        this.orientation = orientation;
        this.speed = speed;
        this.lane = lane;
        this.sprite = type.sprite;
    }

    public enum Status { PASS, SEND, DIE }

    public Status update(List<Monster> monsters) {
        double newY = y + speed*Game.LANE_LENGTH*0.001*0.01;
        for(Monster m : monsters) {
            if(m.y > y && m.y < newY) {
                newY = m.y-0.01;
                this.attack(m);
                break;
            }
            else if(m.y < y && m.y > newY) {
                newY = m.y + 0.01;
                this.attack(m);
                break;
            }
        }
        if(newY<-1 && orientation == Orientation.EVIL) {
            Game.damage(this.attackStrength);
            return Status.DIE;
        }
        y = newY;
        if (y>0 && orientation == Orientation.GOOD) {
            endTime = TimeUtils.millis();
            return Status.SEND;
        }
        return Status.PASS;
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
    public void die() {

    }

    public void attack(Entity e){
        e.takeDamage(new Attack(
                element,
                attackStrength
        ));
    }
}
































