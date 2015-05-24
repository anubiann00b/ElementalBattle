package me.shreyasr.elemental.field.entities;

import me.shreyasr.elemental.Element;

public class Attack {

    public Element type;
    public double baseDamage;

    public Attack(Element type, double baseDamage){
        this.type = type;
        this.baseDamage = baseDamage;
    }
}
