package me.shreyasr.elemental.field.entities;

import com.badlogic.gdx.utils.TimeUtils;

import java.util.List;

import me.shreyasr.elemental.Game;

public class Spell extends Monster {

    public Spell(Monster.Type type, Orientation orientation, double speed, int lane) {
        super(type, orientation, speed, lane);
    }

    @Override
    public Status update(List<Monster> monsters) {
        double newY = y + speed*Game.LANE_LENGTH*0.001*0.01;
        for(Monster m : monsters){
            if(m.y > y && m.y < newY){
                newY = m.y-0.01;
                if(m.element.counter(this.element))
                    this.die();
                continue;
            } else if(m.y < y && m.y > newY) {
                newY = m.y + 0.01;
                if(m.element.counter(this.element))
                    this.die();
            }
        }
        if(newY<-1 && orientation == Orientation.EVIL){
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
}
