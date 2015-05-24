package me.shreyasr.elemental.field.entities;

import me.shreyasr.elemental.Element;
import me.shreyasr.elemental.Game;
import me.shreyasr.elemental.field.entities.Effects.Effect;

public class Spell {

    int baseDamage;
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
