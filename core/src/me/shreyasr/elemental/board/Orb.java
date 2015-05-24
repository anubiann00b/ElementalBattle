package me.shreyasr.elemental.board;

import com.badlogic.gdx.graphics.g2d.Sprite;

import me.shreyasr.elemental.Element;
import me.shreyasr.elemental.Game;

public class Orb {

    public final Sprite sprite;
    public final Element element;
    public boolean dragging = false;

    public Orb(Element element) {
        this.element = element;
        sprite = new Sprite(element.tex);
        sprite.setSize(Game.LANE_WIDTH, Game.LANE_WIDTH);
    }

    public boolean counter(Orb o) {
        Element e = o.element;
        if(element == Element.HOLY || element==Element.ARCANE || e == Element.HOLY || e==Element.ARCANE)
            return false;
        if(element == Element.LIGHTNING)
            return e == Element.WATER;
        return element.ordinal() - e.ordinal() == -1;
    }
}