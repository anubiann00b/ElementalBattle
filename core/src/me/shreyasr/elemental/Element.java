package me.shreyasr.elemental;

import com.badlogic.gdx.graphics.Texture;

public enum Element {

    WATER("orb_water.png"),
    FIRE("orb_fire.png"),
    COLD("orb_cold.png"),
    EARTH("orb_earth.png"),
    LIGHTNING("orb_lightning.png"),
    HOLY("orb_holy.png"),
    ARCANE("orb_arcane.png");

    public Texture tex;

    Element(String file) {
        tex = new Texture(file);
    }

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