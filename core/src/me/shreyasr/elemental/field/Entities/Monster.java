package me.shreyasr.elemental.field.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.shreyasr.elemental.Element;
import me.shreyasr.elemental.Game;
import me.shreyasr.elemental.field.entities.Effects.Effect;
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
        FIRE_3(MonsterSprite.FIRE_3, Element.FIRE),
        FIRE_4(MonsterSprite.FIRE_4, Element.FIRE),
        FIRE_5(MonsterSprite.FIRE_5, Element.FIRE),

        COLD_3(MonsterSprite.COLD_3, Element.COLD),
        COLD_4(MonsterSprite.COLD_4, Element.COLD),
        COLD_5(MonsterSprite.COLD_5, Element.COLD),

        WATER_3(MonsterSprite.WATER_3, Element.WATER),
        WATER_4(MonsterSprite.WATER_4, Element.WATER),
        WATER_5(MonsterSprite.WATER_5, Element.WATER),

        LIGHTNING_3(MonsterSprite.LIGHTNING_3, Element.LIGHTNING),
        LIGHTNING_4(MonsterSprite.LIGHTNING_4, Element.LIGHTNING),
        LIGHTNING_5(MonsterSprite.LIGHTNING_5, Element.LIGHTNING),

        EARTH_3(MonsterSprite.EARTH_3, Element.EARTH),
        EARTH_4(MonsterSprite.EARTH_4, Element.EARTH),
        EARTH_5(MonsterSprite.EARTH_5, Element.EARTH);

        public static Type get(Element e, int size) {
            Gdx.app.error("size", size+"");
            switch(size) {
                case 3:
                    switch(e) {
                        case FIRE: return FIRE_3;
                        case COLD: return COLD_3;
                        case WATER: return WATER_3;
                        case LIGHTNING: return LIGHTNING_3;
                        case EARTH: return EARTH_3;
                    }
                case 4:
                    switch(e) {
                        case FIRE: return FIRE_4;
                        case COLD: return COLD_4;
                        case WATER: return WATER_4;
                        case LIGHTNING: return LIGHTNING_4;
                        case EARTH: return EARTH_4;
                    }
                default:
                    switch(e) {
                        case FIRE: return FIRE_5;
                        case COLD: return COLD_5;
                        case WATER: return WATER_5;
                        case LIGHTNING: return LIGHTNING_5;
                        case EARTH: return EARTH_5;
                    }
            }
            return FIRE_3;
        }

        public final MonsterSprite sprite;
        public final Element element;

        Type(MonsterSprite sprite, Element element) {
            this.sprite = sprite;
            this.element = element;
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
    public double health;
    public double attackStrength;
    public ArrayList<Effect> effects = new ArrayList<Effect>();

    public Monster(Monster.Type type, Orientation orientation, double speed, int lane) {
        this.type = type;
        this.orientation = orientation;
        this.speed = speed;
        this.lane = lane;
        this.sprite = type.sprite;
    }

    public enum Status { PASS, SEND, DIE }

    public Status update(List<Monster> monsters) {
        Iterator<Effect> e = effects.iterator();
        while (e.hasNext()) {
            Effect eff = e.next();
            boolean t = eff.subtractDuration(0.03333);
            if (t) {
                e.remove();
            }
        }
        double newY = y + speedMod()*Game.LANE_LENGTH*0.001*0.005;
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
            Game.damage(attackMod());
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
        if(a.type.counter(type.element))
            health-=a.baseDamage * 2 * defMod();
        else
            health-=a.baseDamage;
        if(health < 0)
            die();
    }

    @Override
    public void die() {

    }
    public double speedMod(){
        double base = speed;
        for(Effect e : effects)
            base = e.getSpeed(base);
        return base;
    }
    public double attackMod(){
        double base = attackStrength;
        for(Effect e : effects)
            base = e.getAttack(attackStrength);
        return base;
    }
    public double defMod(){
        double base = 1;
        for(Effect e : effects)
            base = e.getAttack(attackStrength);
        return base;
    }
    public void attack(Entity e){
        e.takeDamage(new Attack(
                type.element,
                attackMod()
        ));
    }
    public void addEffect(Effect e){
        effects.add(e);
    }
}
































