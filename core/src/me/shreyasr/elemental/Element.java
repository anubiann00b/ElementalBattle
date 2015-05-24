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
    ARCANE;

    /**
     * Checks counters
     * @param e element to check against
     * @return true if this type counters e.
     */
    public boolean counter(Element e){
        if(this == HOLY || this==ARCANE || e == HOLY || e==ARCANE)
            return false;
        if(this == LIGHTNING)
            return e == WATER;
        return this.ordinal() - e.ordinal() == -1;
    }
}