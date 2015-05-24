package me.shreyasr.elemental.field.Entities;

import me.shreyasr.elemental.Element;

/**
 * Created by Benjamin on 5/23/2015.
 */
public class Attack {
    public Element type;
    public double baseDamage;
    public Attack(Element type, double baseDamage){
        this.type = type;
        this.baseDamage = baseDamage;
    }
}
