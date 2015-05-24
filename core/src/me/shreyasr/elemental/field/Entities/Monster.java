package me.shreyasr.elemental.field.entities;

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
        FIRE_3(MonsterSprite.FIRE_3, Element.FIRE);

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
    public Element element = Element.FIRE;
    public double health;
    public double attackStrength;
    public ArrayList<Effect> effects = new ArrayList<Effect>();

    public Monster(Monster.Type type, Orientation orientation, double speed, int lane) {
        this.type = type;
        this.orientation = orientation;
        this.speed = sp-eed;
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
        double newY = y + speedMod()*Game.LANE_LENGTH*0.001*0.01;
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
    public void attack(Entity e){
        e.takeDamage(new Attack(
                element,
                attackMod()
        ));
    }
    public void addEffect(Effect e){
        e.applyEffect(this);
        effects.add(e);
    }
}
































