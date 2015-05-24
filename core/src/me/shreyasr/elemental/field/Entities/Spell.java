package me.shreyasr.elemental.field.entities;

import com.badlogic.gdx.utils.TimeUtils;

import java.util.List;

import me.shreyasr.elemental.Element;
import me.shreyasr.elemental.Game;
import me.shreyasr.elemental.field.entities.Effects.Effect;

/**
 * Created by Benjamin on 5/24/2015.
 */
public class Spell{
    double baseDamage;
    Element type;
    Effect effect;
    public Attack baseAttack(){
        return new Attack(type,baseDamage);
    }
    public void damage(){
        Game.damage(baseDamage);
    }
    public Effect eff(){
        return effect.clone();
    }

}
