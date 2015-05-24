package me.shreyasr.elemental.field.entities;

import com.badlogic.gdx.utils.TimeUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import me.shreyasr.elemental.Element;
import me.shreyasr.elemental.Game;
import me.shreyasr.elemental.field.Lane;
import me.shreyasr.elemental.graphics.MonsterSprite;

public class Monster implements Entity {

    public byte[] serialize() {
        return ByteBuffer.allocate(24)
                .putLong(endTime)
                .putInt(type.ordinal())
                .putDouble(speed)
                .putInt(lane.laneNum)
                .array();
    }

    public static Monster deserialize(byte[] arr) {
        ByteBuffer buffer = ByteBuffer.wrap(arr);
        return new Monster(Type.values()[buffer.getInt(8)], Orientation.EVIL, buffer.getDouble(12));
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
    public Lane lane;
    public final double speed;
    public Element element;
    public double health;
    public double attackStregth;
    public ArrayList<Effect> effects = new ArrayList<Effect>;
    public Monster(Monster.Type type, Orientation orientation, double speed) {
        this.type = type;
        this.orientation = orientation;
        this.speed = speed;
        this.sprite = type.sprite;
    }

    public boolean update() {
        Iterator<Effect> e = effects.iterator();
        while(e.hasNext()){
            Effect eff = e.next();
            boolean t = eff.subtractDuration(0.03333);
            if(t) {
                eff.unapplyEffect(this);
                e.remove();
            }
        }
        double newY = y + speed*Game.LANE_LENGTH*0.001*0.01;
        for(Monster m : lane.monsters){
            if(m.y > y && m.y < newY){
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
        if(newY < -1 && orientation == Orientation.EVIL){
            Game.damage(this.attackStregth);
            return true;
        }
        y = newY;
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
    public void attack(Entity e){
        e.takeDamage(new Attack(
                element,
                attackStregth
        ));
    }
    public void addEffect(Effect e){
        e.applyEffect(this);
        effects.add(e);
    }
}
































