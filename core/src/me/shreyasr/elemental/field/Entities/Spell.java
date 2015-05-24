package me.shreyasr.elemental.field.entities;

import com.badlogic.gdx.utils.TimeUtils;

import me.shreyasr.elemental.Element;
import me.shreyasr.elemental.Game;

/**
 * Created by Benjamin on 5/24/2015.
 */
public class Spell extends Monster{
    public Spell(Monster.Type type, Orientation orientation, double speed) {
        super( type, orientation,  speed);
    }
    @Override
    public boolean update() {
        double newY = y + speed*Game.LANE_LENGTH*0.001*0.01;
        for(Monster m : lane.monsters){
            if(m.y > y && m.y < newY){
                newY = m.y-0.01;
                if(m.element.counter(this.element))
                    this.die();
                continue;
            }
            else if(m.y < y && m.y > newY) {
                newY = m.y + 0.01;
                if(m.element.counter(this.element))
                    this.die();
            }
        }
        if(newY < 0){
            newY = 0.01;
            Game.damage(this.attackStregth);
        }
        y = newY;
        if (y>0) {
            endTime = TimeUtils.millis();
            return true;
        }
        return false;
    }
}
