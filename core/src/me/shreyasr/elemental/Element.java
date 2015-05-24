package me.shreyasr.elemental;

/**
 * Created by Benjamin on 5/23/2015.
 */
public enum Element {
    WATER,
    FIRE,
    ICE,
    EARTH,
    LIGHTNING,
    HOLY,
    ARCANE
    public boolean counter(Element e){
        if(this == HOLY || this==ARCANE || e == HOLY || e==ARCANE)
            return false;
        return this.ordinal() - e.ordinal() == -1;
    }
}
